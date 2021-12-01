package com.wurd.bd.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.wurd.bd.Utils.CommonUtils;
import com.wurd.bd.constants.Config;
import com.wurd.bd.exception.CommonException;
import com.wurd.bd.tool.PinyinTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Autowired
    private Config config;

    private CloseableHttpClient client = HttpClients.createDefault();

    private Gson gson = new Gson();

    private String paddle_t = "paddle";

    @PostMapping("/get/policy")
    String getTestFromPic_policy(MultipartFile file, String flag, String type) throws CommonException {
        try {
            check(file);
            String fileName = file.getOriginalFilename();
            fileName = PinyinTool.toPinyin(fileName);
            InputStream fileStream = file.getInputStream();
            fileName = System.currentTimeMillis() + "_" + fileName;
            String outFilePath = config.getEasyOcrPicturePath() + File.separator + fileName;
            CommonUtils.writeFile2Disk(fileStream, outFilePath);
            String uri = null;
            if (paddle_t.equalsIgnoreCase(type)) {
                uri = "http://127.0.0.1:5001/paddle-ocr?path="
                        + URLEncoder.encode(outFilePath, StandardCharsets.UTF_8.toString());
            } else {
                uri = "http://127.0.0.1:5000/easy-ocr?path="
                        + URLEncoder.encode(outFilePath, StandardCharsets.UTF_8.toString());
            }
            HttpUriRequest get = new HttpGet(uri);
            HttpResponse response = client.execute(get);
            int status = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            if (HttpStatus.SC_OK == status) {
                JsonArray json = gson.fromJson(result, JsonArray.class);
                if ("1".equals(flag)) {
                    JsonArray array = formatResult(type, json);
                    return array.toString();
                } else {
                    return json.toString();
                }
            } else {
                log.error(result);
                throw new CommonException("Ocr failed");
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @PostMapping("/get")
    String getTestFromPic(MultipartFile file, String flag, String type) throws CommonException {
        try {
            check(file);
            String fileName = file.getOriginalFilename();
            fileName = PinyinTool.toPinyin(fileName);
            InputStream fileStream = file.getInputStream();
            fileName = System.currentTimeMillis() + "_" + fileName;
            String outFilePath = config.getEasyOcrPicturePath() + File.separator + fileName;
            CommonUtils.writeFile2Disk(fileStream, outFilePath);
            Runtime runtime = Runtime.getRuntime();
            String pythonPath = "";
            if (paddle_t.equalsIgnoreCase(type)) {
                pythonPath = config.getPaddleOcrPythonPath();
            } else {
                pythonPath = config.getEasyOcrPythonPath();
            }
            String[] command = {"python", pythonPath, outFilePath};
            Process process = runtime.exec(command);

            StringBuilder result = new StringBuilder();
            StringBuilder error = new StringBuilder();
            processReturn(process, result, error);
            int state = process.waitFor();
            if (state == 0) {
                JsonArray json = gson.fromJson(result.toString(), JsonArray.class);
                if ("1".equals(flag)) {
                    JsonArray array = formatResult(type, json);
                    return array.toString();
                } else {
                    return json.toString();
                }
            } else {
                log.error(error.toString());
                throw new CommonException("Ocr failed");
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    private JsonArray formatResult(String type, JsonArray json) {
        JsonArray array = new JsonArray();
        if (paddle_t.equalsIgnoreCase(type)) {
            JsonArray arr = json.get(0).getAsJsonObject().get("data").getAsJsonArray();
            for (int i = 0; i < arr.size(); i++) {
                JsonElement s = arr.get(i).getAsJsonObject().get("text");
                if (s != null && StringUtils.isNotEmpty(s.getAsString())) {
                    array.add(s);
                }
            }
        } else {
            for (int i = 0; i < json.size(); i++) {
                JsonElement s = json.get(i).getAsJsonArray().get(1);
                if (s != null && StringUtils.isNotEmpty(s.getAsString())) {
                    array.add(s);
                }
            }
        }
        return array;
    }

    private void processReturn(Process process, StringBuilder result, StringBuilder error) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    inputStream = process.getInputStream();
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream errorStream = null;
                try {
                    errorStream = process.getErrorStream();
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        error.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (errorStream != null) {
                        try {
                            errorStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private void check(MultipartFile file) throws CommonException {
        if (file == null) {
            throw new CommonException("Need one jpg/jpeg/png file");
        }
        String fileName = file.getOriginalFilename();
        String n = fileName.toLowerCase();
        if (!(n.endsWith(".png")
                || n.endsWith(".jpg")
                || n.endsWith(".jpeg")
                || n.endsWith(".png"))) {
            throw new CommonException("Need one jpg/jpeg/png file");
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new CommonException("Can not greater that 2M");
        }
    }

}
