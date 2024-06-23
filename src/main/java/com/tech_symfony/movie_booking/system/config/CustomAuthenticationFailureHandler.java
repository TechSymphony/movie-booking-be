package com.tech_symfony.movie_booking.system.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

//Sử dụng để phải hồi lỗi đăng nhập của người dùng,
//chẳng hạn như người dùng chưa xác nhận email thì từ chối đăng nhập
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String redirectUrl = "/auth/unverified";
        response.sendRedirect(redirectUrl);
    }
}
