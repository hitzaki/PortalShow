package com.github.hitzaki.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("admin_user")
public class AdminUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password; // Plain text for demo (should be hashed in production)
    private String role; // admin, editor
}

