package com.github.hitzaki.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.hitzaki.dto.ContactDTO;
import com.github.hitzaki.entity.Contact;

public interface ContactService extends IService<Contact> {
    void submit(ContactDTO contactDTO);
    void complete(Long id);
}

