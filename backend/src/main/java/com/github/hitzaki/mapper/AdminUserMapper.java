package com.github.hitzaki.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.hitzaki.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {
}

