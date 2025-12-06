package com.github.hitzaki.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.github.hitzaki.common.RateLimit;
import com.github.hitzaki.common.Result;
import com.github.hitzaki.dto.ContactDTO;
import com.github.hitzaki.entity.Contact;
import com.github.hitzaki.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@Validated
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/submit")
    @RateLimit(0.2) // 1 request every 5 seconds
    public Result<Void> submit(@RequestBody @Valid ContactDTO contactDTO) {
        contactService.submit(contactDTO);
        return Result.success();
    }

    @PostMapping("/list")
    @SaCheckRole("admin")
    public Result<List<Contact>> list() {
        return Result.success(contactService.list());
    }

    @PostMapping("/complete")
    @SaCheckRole("admin")
    public Result<Void> complete(@RequestBody Contact contact) {
        contactService.complete(contact.getId());
        return Result.success();
    }
}

