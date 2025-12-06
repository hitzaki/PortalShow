package com.github.hitzaki.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.hitzaki.common.BusinessException;
import com.github.hitzaki.common.ErrorCode;
import com.github.hitzaki.dto.ContactDTO;
import com.github.hitzaki.entity.Contact;
import com.github.hitzaki.mapper.ContactMapper;
import com.github.hitzaki.service.ContactService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(ContactDTO contactDTO) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactDTO, contact);
        contact.setStatus("待处理");
        this.save(contact);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void complete(Long id) {
        Contact contact = this.getById(id);
        if (contact == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        contact.setStatus("已处理");
        this.updateById(contact);
    }
}

