package com.wurd.bd.Utils;

import com.wurd.bd.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
public class CommonUtils {
    public static void writeFile2Disk(InputStream fileStream, String outFilePath) throws CommonException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(outFilePath);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileStream.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            log.error("Operate file failed", e);
            throw new CommonException("Operate file failed");
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
