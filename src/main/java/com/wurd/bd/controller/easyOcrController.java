package com.wurd.bd.controller;

import com.google.gson.JsonArray;
import com.wurd.bd.Utils.CommonUtils;
import com.wurd.bd.constants.Config;
import com.wurd.bd.exception.CommonException;
import com.wurd.bd.tool.GsonTool;
import com.wurd.bd.tool.PinyinTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/easyOcr")
public class easyOcrController {

    @Autowired
    private Config config;

    @PostMapping("/get")
    String getTestFromPic(MultipartFile file) throws CommonException {
        InputStream execStream = null;
        try {
            if (file == null) {
                return null;
            }
            String fileName = file.getOriginalFilename();
            fileName = PinyinTool.toPinyin(fileName);
            InputStream fileStream = file.getInputStream();
            fileName = System.currentTimeMillis() + "_" + fileName;
            String outFilePath = config.getEasyOcrPicturePath() + File.separator + fileName;
            CommonUtils.writeFile2Disk(fileStream, outFilePath);
            Runtime runtime = Runtime.getRuntime();
            String[] command = {"python", config.getEasyOcrPythonPath(), outFilePath};
            Process process = runtime.exec(command);
            //可能导致死锁
            int state = process.waitFor();
            StringBuilder result = new StringBuilder();
            if (state == 0) {
                execStream = process.getInputStream();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(execStream, StandardCharsets.UTF_8));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                JsonArray json = GsonTool.get().fromJson(result.toString(), JsonArray.class);
                return json.toString();
            }else{
                execStream = process.getErrorStream();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(execStream, StandardCharsets.UTF_8));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                log.error(result.toString());
                throw new CommonException("Ocr failed");
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        } finally {
            try {
                if (execStream != null) {
                    execStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
