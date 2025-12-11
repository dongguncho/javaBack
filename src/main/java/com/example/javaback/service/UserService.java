package com.example.javaback.service;

import com.example.javaback.entity.User;
import com.example.javaback.exception.DuplicateEmailException;
import com.example.javaback.exception.UnauthorizedException;
import com.example.javaback.exception.UserNotFoundException;
import com.example.javaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public User createUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다");
        }
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        
        return userRepository.save(user);
    }
    
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
    
    @Transactional(readOnly = true)
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
    }
    
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User updateUser(String id, String name, String email) {
        User user = findById(id);
        
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) {
                throw new DuplicateEmailException("이미 사용 중인 이메일입니다");
            }
            user.setEmail(email);
        }
        
        if (name != null) {
            user.setName(name);
        }
        
        return userRepository.save(user);
    }
    
    public void deleteUser(String id) {
        User user = findById(id);

        if ("admin@example.com".equals(user.getEmail())) {
            throw new UnauthorizedException("관리자 계정은 삭제할 수 없습니다");
        }

        userRepository.deleteById(id);
    }
    
    public boolean validatePassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
} 