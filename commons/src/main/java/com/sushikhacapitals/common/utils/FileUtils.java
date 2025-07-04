package com.sushikhacapitals.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileUtils {

    //todo: take from properties
    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024; // 10MB
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("png", "jpeg", "jpg");

    public static Byte[] convertMultipartFileToBytes(MultipartFile file){
        try {
            log.info("File size {}",file.getSize());
            // Check file size

            if (file.getSize() > MAX_FILE_SIZE_BYTES) {
                throw new IllegalArgumentException("File size exceeds the maximum allowed size (10MB).");
            }

            // Check file extension
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
            log.info("Checking ext for file {}",fileExtension.toLowerCase());
            if (!SUPPORTED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
                throw new IllegalArgumentException("Unsupported file extension. Supported extensions: png, jpeg, jpg, pdf.");
            }
            log.info("Converting to bytes...");

            byte[] byteArray = file.getBytes();
            Byte[] byteObjectArray = new Byte[byteArray.length];
            for (int i = 0; i < byteArray.length; i++) {
                byteObjectArray[i] = byteArray[i];
            }
            return byteObjectArray;
        }catch (Exception ex){
            log.error("Exception: {}",ex.getMessage());
        }
        return null;
    }

    public static byte[] convertMultipartImageToByte(MultipartFile file){
        try {
            log.info("File size {}",file.getSize());
            // Check file size

            if (file.getSize() > MAX_FILE_SIZE_BYTES) {
                throw new IllegalArgumentException("File size exceeds the maximum allowed size (10MB).");
            }

            // Check file extension
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
            log.info("Checking ext for file {}",fileExtension.toLowerCase());
            if (!SUPPORTED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
                throw new IllegalArgumentException("Unsupported file extension. Supported extensions: png, jpeg, jpg, pdf.");
            }
            log.info("Converting to bytes...");

            byte[] byteArray = file.getBytes();
            return byteArray;
        }catch (Exception ex){
            log.error("Exception: {}",ex.getMessage());
        }
        return null;
    }

    public static String determineImageType(byte[] imageData) {
        if (imageData.length >= 2 && imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
            return "image/jpeg"; // JPEG format
        } else if (imageData.length >= 4 && imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50 &&
                imageData[2] == (byte) 0x4E && imageData[3] == (byte) 0x47) {
            return "image/png"; // PNG format
        } else if (imageData.length >= 2 && imageData[0] == (byte) 0x47 && imageData[1] == (byte) 0x49) {
            return "image/gif"; // GIF format
        } else if (imageData.length >= 2 && imageData[0] == (byte) 0x42 && imageData[1] == (byte) 0x4D) {
            return "image/bmp"; // BMP format
        } else {
            // Add more checks for other image types if needed
            return null; // Unknown image type
        }
    }

    public static MultipartFile convertByteArrayToMultipartFile(Byte[] byteArrayLogoWrapper, String fileName, String contentType) {
        byte[] byteArray = ArrayUtils.toPrimitive(byteArrayLogoWrapper);
        return new CustomMultipartFile(byteArray, fileName, contentType);
    }

}
