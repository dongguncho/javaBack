package com.example.javaback.controller;

import com.example.javaback.dto.ApiResponse;
import com.example.javaback.entity.User;
import com.example.javaback.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 관련 API")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/profile")
    @Operation(summary = "내 프로필 조회", description = "현재 로그인한 사용자의 프로필을 조회합니다")
    public ResponseEntity<ApiResponse<User>> getProfile(@RequestAttribute("user") User user) {
        return ResponseEntity.ok(ApiResponse.success(user, "프로필 조회가 완료되었습니다"));
    }
    
    @GetMapping
    @Operation(summary = "모든 사용자 조회", description = "모든 사용자 목록을 조회합니다")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(ApiResponse.success(users, "사용자 목록 조회가 완료되었습니다"));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "특정 사용자 조회", description = "특정 사용자의 정보를 조회합니다")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(user, "사용자 조회가 완료되었습니다"));
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        
        User user = userService.updateUser(id, name, email);
        return ResponseEntity.ok(ApiResponse.success(user, "사용자 정보 수정이 완료되었습니다"));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "사용자 삭제가 완료되었습니다"));
    }
} 