package com.taskmanagement.core.security.filters.wrapper;

import com.taskmanagement.core.security.filters.stream.MultireadServletInputStream;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class MultireadRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] requestBody;
//    private final Map<String, String[]> parameterMap;



    public MultireadRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // Read the request body and store it
        this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
//        this.parameterMap = parseFormParameters();

    }

    @Override
    public ServletRequest getRequest() {
        return super.getRequest();
    }

    private Map<String, String[]> parseFormParameters() {
        Map<String, String[]> parameters = new HashMap<>(super.getParameterMap());
        // Parse additional parameters from the request body if it's a form request
        if (super.getContentType() != null && super.getContentType().startsWith("application/x-www-form-urlencoded")) {
            String requestBodyString = new String(this.requestBody, StandardCharsets.UTF_8);
            String[] pairs = requestBodyString.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";
                parameters.computeIfAbsent(key, k -> new String[]{});
                parameters.computeIfPresent(key, (k, v) -> {
                    String[] newArray = new String[v.length + 1];
                    System.arraycopy(v, 0, newArray, 0, v.length);
                    newArray[v.length] = value;
                    return newArray;
                });
            }
        }
        return parameters;
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        // You can customize this method to handle parameters as needed
        return super.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        // You can customize this method to handle parameters as needed
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        // You can customize this method to handle parameters as needed
        return super.getParameterValues(name);
    }

    private byte[] readInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toByteArray();
    }


    @Override
    public BufferedReader getReader() throws IOException {
        // Provide a new reader for each call
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.requestBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // Provide a new input stream for each call
        return new MultireadServletInputStream(this.requestBody);
    }

}