package com.github.hitzaki.service;

import com.github.hitzaki.dto.LoginDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
}

