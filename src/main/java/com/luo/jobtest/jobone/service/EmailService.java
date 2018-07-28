package com.luo.jobtest.jobone.service;

import com.luo.jobtest.jobone.entity.Email;

import java.util.List;

public interface EmailService {

    void batchInsert(List<Email> listMail);
}
