package com.wurd.bd.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Config {
    @Value("${easyOcr.python.path}")
    private String easyOcrPythonPath;

    @Value("${paddleOcr.python.path}")
    private String paddleOcrPythonPath;

    @Value("${ocr.picture.path}")
    private String easyOcrPicturePath;
}
