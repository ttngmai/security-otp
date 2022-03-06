package com.example.securityotp.security.service;

import com.example.securityotp.entity.Account;
import com.example.securityotp.repository.AccountQueryRepository;
import com.example.securityotp.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final AccountQueryRepository accountQueryRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        StringBuilder messageBuilder = new StringBuilder();

        if (exception instanceof AuthenticationServiceException) {
            messageBuilder.append("존재하지 않는 사용자입니다.");

        } else if (exception instanceof BadCredentialsException) {
            messageBuilder.append("아이디 또는 비밀번호가 틀립니다.");

            Account account = accountQueryRepository.findByUsername(username);

            if (account == null) {
                account.loginFail();
                messageBuilder.append(" 남은 횟수: ")
                        .append(account.loginRetryRemaining());
            }

        } else if (exception instanceof LockedException) {
            messageBuilder.append("잠긴 계정입니다.");

        } else if (exception instanceof DisabledException) {
            messageBuilder.append("비활성화된 계정입니다.");

        } else if (exception instanceof AccountExpiredException) {
            messageBuilder.append("만료된 계정입니다.");

        } else if (exception instanceof CredentialsExpiredException) {
            messageBuilder.append("비밀번호가 만료되었습니다.");

        }

        request.setAttribute("globalError", messageBuilder.toString());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/login");
        dispatcher.forward(request, response);
    }
}
