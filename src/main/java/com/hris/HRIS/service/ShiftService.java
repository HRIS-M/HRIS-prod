package com.hris.HRIS.service;

import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.model.ShiftModel;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShiftService {

    @Autowired
    ShiftRepository shiftRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    public void assignSiftToEmployee(String employeeId, ShiftModel shift) {
        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(employeeId);

        if (optionalEmployeeModel.isPresent()) {
            EmployeeModel employeeModel = optionalEmployeeModel.get();

            if(employeeModel.getWorkShift() == null) {
                employeeModel.setWorkShift(new ArrayList<>());
            }

            employeeModel.getWorkShift().add(shift);

            employeeRepository.save(employeeModel);
        }
    }

    public void updateSiftToEmployee(String employeeId, ShiftModel shift) {
        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(employeeId);

        if (optionalEmployeeModel.isPresent()) {
            EmployeeModel employeeModel = optionalEmployeeModel.get();

            if(employeeModel.getWorkShift() == null) {
                employeeModel.setWorkShift(new ArrayList<>());
            } else {
                employeeModel.getWorkShift()
                        .replaceAll(s -> s.getId().equals(shift.getId()) ? shift : s);
            }

            employeeRepository.save(employeeModel);
        }
    }
}
