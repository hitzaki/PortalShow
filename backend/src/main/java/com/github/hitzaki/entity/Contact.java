package com.github.hitzaki.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("contact")
public class Contact implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String message;
    private String status; // '待处理', '已处理'
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}

