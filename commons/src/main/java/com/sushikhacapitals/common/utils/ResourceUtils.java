package com.sushikhacapitals.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ResourceUtils {
    public static File loadResource(String path) throws IOException {
        Resource resource = new FileSystemResource(path);
        if (!resource.exists()) {
            resource = new ClassPathResource(path);
        }
        log.info(String.format("Loading resource %s", resource.getURI()));
        return resource.getFile();
    }

    public static InputStream loadResourceAsStream(String path) throws IOException {
        InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(path);
        if (inputStream == null)
            inputStream = new FileInputStream(loadResource(path));
        return inputStream;
    }
}
