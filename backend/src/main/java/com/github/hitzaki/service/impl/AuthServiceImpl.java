package com.github.hitzaki.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.hitzaki.common.BusinessException;
import com.github.hitzaki.common.ErrorCode;
import com.github.hitzaki.dto.LoginDTO;
import com.github.hitzaki.entity.AdminUser;
import com.github.hitzaki.mapper.AdminUserMapper;
import com.github.hitzaki.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String login(LoginDTO loginDTO) {
        AdminUser user = adminUserMapper.selectOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, loginDTO.getUsername()));

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        StpUtil.login(user.getId());
        return StpUtil.getTokenValue();
    }
}
