package com.github.hitzaki.controller;

import com.github.hitzaki.common.RateLimit;
import com.github.hitzaki.common.Result;
import com.github.hitzaki.dto.LoginDTO;
import com.github.hitzaki.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @RateLimit(0.5) // 1 request every 2 seconds
    public Result<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        return Result.success(token);
    }
}

