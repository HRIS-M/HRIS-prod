package com.hris.HRIS.service;

import com.hris.HRIS.model.AttendanceModel;
import com.hris.HRIS.model.LeaveModel;
import com.hris.HRIS.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LeaveService {

    @Autowired
    LeaveRepository leaveRepository;
    public LeaveModel createLeave(LeaveModel leaveModel) {
        return leaveRepository.save(leaveModel);
    }

    public List<LeaveModel> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public LeaveModel updateLeave(String id, LeaveModel updateLeaveModel) {
        LeaveModel existingLeaveModel = leaveRepository.findById(id).orElse(null);
        if (existingLeaveModel != null) {
            // Update the existing record with the new data

            existingLeaveModel.setLeaveType(updateLeaveModel.getLeaveType());
            existingLeaveModel.setReason(updateLeaveModel.getReason());
            existingLeaveModel.setLeaveStartDate(updateLeaveModel.getLeaveStartDate());
            existingLeaveModel.setLeaveEndDate(updateLeaveModel.getLeaveEndDate());
            // Update other fields as needed

            // Save the updated record
            return leaveRepository.save(existingLeaveModel);
        } else {
            return null; // Return null if the record with the given ID is not found
        }
    }

    public boolean deleteLeave(String id) {
        return false;
    }
}
