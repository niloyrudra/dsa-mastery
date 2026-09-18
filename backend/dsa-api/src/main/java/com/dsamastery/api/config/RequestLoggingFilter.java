package com.dsamastery.api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @PostConstruct
    public void init() {
        log.info("RequestLoggingFilter INITIALIZED");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        log.info("RequestLoggingFilter doFilter called for: {}", request.getClass().getName());
        
        if (request instanceof HttpServletRequest httpRequest) {
            String contentType = httpRequest.getContentType();
            String method = httpRequest.getMethod();
            String uri = httpRequest.getRequestURI();
            log.info("RequestLoggingFilter: {} {} Content-Type: {}", method, uri, contentType);
            
            if (contentType != null && contentType.contains("application/json")) {
                // Read raw bytes from input stream
                InputStream inputStream = httpRequest.getInputStream();
                byte[] bodyBytes = inputStream.readAllBytes();
                
                String body = new String(bodyBytes, StandardCharsets.UTF_8);
                log.info("RAW REQUEST BYTES: length={}, bytes={}", bodyBytes.length, bytesToHex(bodyBytes));
                log.info("RAW REQUEST BODY: [{}] (char length: {})", body, body.length());
                
                // Wrap request to allow re-reading
                CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(httpRequest, bodyBytes);
                chain.doFilter(wrappedRequest, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    private static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
        private final byte[] cachedBody;

        public CachedBodyHttpServletRequest(HttpServletRequest request, byte[] cachedBody) {
            super(request);
            this.cachedBody = cachedBody;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new ServletInputStream() {
                private final ByteArrayInputStream buffer = new ByteArrayInputStream(cachedBody);

                @Override
                public boolean isFinished() {
                    return buffer.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int read() throws IOException {
                    return buffer.read();
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
        }
    }
}