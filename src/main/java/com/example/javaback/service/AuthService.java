package com.example.javaback.service;

import com.example.javaback.dto.LoginResponse;
import com.example.javaback.dto.RegisterResponse;
import com.example.javaback.dto.UserResponse;
import com.example.javaback.entity.User;
import com.example.javaback.exception.InvalidCredentialsException;
import com.example.javaback.exception.UnauthorizedException;
import com.example.javaback.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    public RegisterResponse register(String name, String email, String password) {
        User user = userService.createUser(name, email, password);
        return new RegisterResponse(UserResponse.from(user));
    }

    public LoginResponse login(String email, String password) {
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 잘못되었습니다");
        }

        if (!user.getIsActive()) {
            throw new UnauthorizedException("비활성화된 계정입니다");
        }

        if (!userService.validatePassword(user, password)) {
            throw new InvalidCredentialsException("이메일 또는 비밀번호가 잘못되었습니다");
        }

        String token = jwtUtil.generateToken(user.getId());
        return new LoginResponse(token, UserResponse.from(user));
    }
    
    public User validateToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다");
        }

        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userService.findById(userId);

        if (!user.getIsActive()) {
            throw new UnauthorizedException("비활성화된 계정입니다");
        }

        return user;
    }
} 