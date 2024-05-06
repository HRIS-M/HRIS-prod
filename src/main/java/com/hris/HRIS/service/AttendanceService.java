package com.hris.HRIS.service;

import com.hris.HRIS.model.AttendanceModel;
import com.hris.HRIS.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public AttendanceModel createAttendance(AttendanceModel attendanceModel) {
        return attendanceRepository.save(attendanceModel);
    }

    public List<AttendanceModel> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public AttendanceModel updateAttendance(String id, AttendanceModel updatedAttendanceModel) {
        AttendanceModel existingAttendanceModel = attendanceRepository.findById(id).orElse(null);
        if (existingAttendanceModel != null) {
            // Update the existing record with the new data
            existingAttendanceModel.setName(updatedAttendanceModel.getName());
            existingAttendanceModel.setEmail(updatedAttendanceModel.getEmail());
            // Update other fields as needed

            // Save the updated record
            return attendanceRepository.save(existingAttendanceModel);
        } else {
            return null; // Return null if the record with the given ID is not found
        }
    }

    // Method to delete an attendance record by ID
    public boolean deleteAttendance(String id) {
        AttendanceModel existingAttendanceModel = attendanceRepository.findById(id).orElse(null);
        if (existingAttendanceModel != null) {
            attendanceRepository.deleteById(id);
            return true;
        } else {
            return false; // Return false if the record with the given ID is not found
        }
    }

    // Calculate late minutes

    public AttendanceModel getAttendanceById(String id) {
        // Implement method to fetch attendance by id from repository or database
        return null; // Example implementation; replace with actual logic
    }
    public long calculateLateMinutes(AttendanceModel attendanceModel, Date expectedInTime) {
        if (attendanceModel == null || attendanceModel.getRecordInTime() == null) {
            return 0;
        }
        Date inTime = new Date(attendanceModel.getRecordInTime());
        long diffInMillies = expectedInTime.getTime() - inTime.getTime();
        return Math.max(0, TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS));
    }
    // Calculate early departures
    public long calculateEarlyDepartureMinutes(AttendanceModel attendanceModel, Date expectedOutTime) {
        if (attendanceModel == null || attendanceModel.getRecordOutTime() == null) {
            return 0;
        }
        Date outTime = new Date(attendanceModel.getRecordOutTime());
        long diffInMillies = expectedOutTime.getTime() - outTime.getTime();
        return Math.max(0, TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS));
    }
}
