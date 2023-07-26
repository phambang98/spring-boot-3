package com.example.core.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String FILE_CONFIG = "file.properties";

    public static final String UPLOAD_BATCH_INPUT = getParameterFile("UPLOAD_BATCH_INPUT") + File.separator;

    public static final String UPLOAD_BATCH_OUTPUT = getParameterFile("UPLOAD_BATCH_OUTPUT") + File.separator;


    public static final String UPLOAD_FILE_CHAT = getParameterFile("UPLOAD_FILE_CHAT") + File.separator;

    private static Properties properties;

    private static FileUtils instance;

    private static InputStream inputStream;

    public FileUtils() {
        try {
            properties = new Properties();
            inputStream = new ClassPathResource(FILE_CONFIG).getInputStream();
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error("Can't load defaults configs", e);
        }
    }

    public static synchronized FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    private static String getParameterFile(String directory) {
        directory = getInstance().properties.getProperty(directory);
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            new File(directory).mkdirs();
        }
        return directory;
    }

    public static boolean existsFile(String filePathString) {
        File file = new File(filePathString);
        if (file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }
}
