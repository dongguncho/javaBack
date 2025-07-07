package com.example.javaback.service;

import com.example.javaback.entity.User;
import com.example.javaback.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    public Map<String, Object> register(String name, String email, String password) {
        User user = userService.createUser(name, email, password);
        
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        
        response.put("user", userData);
        return response;
    }
    
    public Map<String, Object> login(String email, String password) {
        User user = userService.findByEmail(email);
        
        if (user == null) {
            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다");
        }
        
        if (!user.getIsActive()) {
            throw new RuntimeException("비활성화된 계정입니다");
        }
        
        if (!userService.validatePassword(user, password)) {
            throw new RuntimeException("이메일 또는 비밀번호가 잘못되었습니다");
        }
        
        String token = jwtUtil.generateToken(user.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("access_token", token);
        
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", user.getId());
        userData.put("name", user.getName());
        userData.put("email", user.getEmail());
        
        response.put("user", userData);
        return response;
    }
    
    public User validateToken(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("유효하지 않은 토큰입니다");
        }
        
        String userId = jwtUtil.getUserIdFromToken(token);
        User user = userService.findById(userId);
        
        if (!user.getIsActive()) {
            throw new RuntimeException("비활성화된 계정입니다");
        }
        
        return user;
    }
} 