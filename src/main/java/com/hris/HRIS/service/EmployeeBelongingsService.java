package com.hris.HRIS.service;

import com.hris.HRIS.repository.EmployeeBelongingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeBelongingsService {
    @Autowired
    EmployeeBelongingsRepository employeeBelongingsRepository;


}
