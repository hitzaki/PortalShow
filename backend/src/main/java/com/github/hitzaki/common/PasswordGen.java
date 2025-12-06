package com.github.hitzaki.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGen {
    public static void main(String[] args) {
        // 在这里修改你想生成的密码
        String password = "123456";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);

        System.out.println("原始密码: " + password);
        System.out.println("BCrypt加密后: " + encodedPassword);
    }
}

