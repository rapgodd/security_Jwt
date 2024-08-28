package com.giyeon.security12.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giyeon.security12.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

@Component
public class EmailValidationFilter extends OncePerRequestFilter {

    @Autowired
    private ObjectMapper objectMapper;
    // 필터 생성자에 ObjectMapper를 직접 초기화
    public EmailValidationFilter() {
        this.objectMapper = new ObjectMapper();
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청의 본문을 문자열로 읽습니다.
        String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        if (!body.isEmpty()) {
            // 요청 본문을 User 객체로 변환합니다.
            User user = objectMapper.readValue(body, User.class);
            String username = user.getEmail();

            // 이메일이 @cbnu.ac.kr로 끝나는지 확인합니다.
            if (username != null && username.endsWith("@cbnu.ac.kr")) {
                // 이 경우에만 다음 필터로 요청을 전달합니다.
                // 여기서 새롭게 HttpServletRequestWrapper를 통해 원래 요청을 수정한 뒤 전달합니다.
                filterChain.doFilter(new CustomHttpServletRequestWrapper(request, body), response);
                return;
            }
        }

        // 이메일이 @cbnu.ac.kr로 끝나지 않거나 본문이 비어있으면 인증 실패로 처리합니다.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid email domain");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/register");
    }

    // 요청 본문을 저장하고 재사용할 수 있도록 하는 HttpServletRequestWrapper
    private static class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final String body;

        public CustomHttpServletRequestWrapper(HttpServletRequest request, String body) {
            super(request);
            this.body = body;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new StringReader(body));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
}
