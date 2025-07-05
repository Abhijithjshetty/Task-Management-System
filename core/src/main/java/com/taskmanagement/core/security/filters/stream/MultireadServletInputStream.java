package com.taskmanagement.core.security.filters.stream;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MultireadServletInputStream extends ServletInputStream {

    private InputStream inputStream;

    public MultireadServletInputStream(byte[] requestBody) {
        this.inputStream = new ByteArrayInputStream(requestBody);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public boolean isFinished() {
        try {
            return inputStream.available() == 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }
}