package com.hris.HRIS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.AttendanceModel;
import com.hris.HRIS.repository.AttendanceRepository;
import com.hris.HRIS.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeePayItemController employeePayItemController;

    // Endpoint to create a new attendance record
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAttendance(@RequestBody AttendanceModel attendanceModel) {
        AttendanceModel createdAttendance = attendanceService.createAttendance(attendanceModel);

        ApiResponse response = new ApiResponse("Attendance created");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/depart/{id}")
    public ResponseEntity<ApiResponse> departAttendance(@PathVariable String id, @RequestBody AttendanceModel attendanceModel){
        Optional<AttendanceModel> optionalAttendanceModel = attendanceRepository.findById(id);

        if (optionalAttendanceModel.isPresent()){
            AttendanceModel newModel = optionalAttendanceModel.get();

            newModel.setRecordOutTime(attendanceModel.getRecordOutTime());
            newModel.setLateMinutes(attendanceModel.getLateMinutes());

            attendanceRepository.save(newModel);
        }

        ApiResponse response = new ApiResponse("Attendance departed");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/assign/shift/{id}")
    public ResponseEntity<ApiResponse> assignShift(@PathVariable String id, @RequestBody String shift){
        Optional<AttendanceModel> optionalAttendanceModel = attendanceRepository.findById(id);

        if (optionalAttendanceModel.isPresent()){
            AttendanceModel newModel = optionalAttendanceModel.get();

            newModel.setWorkShift(shift);

            attendanceRepository.save(newModel);
        }

        ApiResponse response = new ApiResponse("Shift assigned");
        return ResponseEntity.ok(response);
    }

    // Endpoint to retrieve all attendance records
    @GetMapping("/get/all")
    public ResponseEntity<List<AttendanceModel>> getAllAttendance() {
        List<AttendanceModel> allAttendance = attendanceService.getAllAttendance();
        return new ResponseEntity<>(allAttendance, HttpStatus.OK);
    }

    // Endpoint to retrieve a specific attendance record by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<AttendanceModel> getAttendanceById(@PathVariable String id) {
        Optional<AttendanceModel> attendanceModelOptional = attendanceRepository.findById(id);

        return attendanceModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // Endpoint to update an existing attendance record
    @PutMapping("/update/{id}")
    public ResponseEntity<AttendanceModel> updateAttendance(@PathVariable("id") String id, @RequestBody AttendanceModel attendanceModel) {
        AttendanceModel updatedAttendance = attendanceService.updateAttendance(id, attendanceModel);
        if (updatedAttendance != null) {
            return new ResponseEntity<>(updatedAttendance, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete an attendance record by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable("id") String id) {
        boolean deleted;
        deleted = attendanceService.deleteAttendance(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Rest API to calculate late minutes
    @GetMapping("/calculate-late-minutes/{id}")
    public ResponseEntity<Long> calculateLateMinutes(@PathVariable String id,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date expectedInTime) {
        AttendanceModel attendanceModel = attendanceService.getAttendanceById(id);
        if (attendanceModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long lateMinutes = attendanceService.calculateLateMinutes(attendanceModel, expectedInTime);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode lateMinuteRecord = objectMapper.createObjectNode();
        // TODO: Temporary added a fixed value as the total hours allowed.
        lateMinuteRecord.put("totalHoursAllowed", 240);
        lateMinuteRecord.put("lateMinutes", lateMinutes);

        employeePayItemController.addLateMinuteDeductions(attendanceModel.getEmail(), lateMinuteRecord.toString());

        return new ResponseEntity<>(lateMinutes, HttpStatus.OK);
    }


    // Rest API to calculate early departures
    @GetMapping("/calculate-early-departure-minutes/{id}")
    public ResponseEntity<Long> calculateEarlyDepartureMinutes(@PathVariable String id,
                                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date expectedOutTime) {
        AttendanceModel attendanceModel = attendanceService.getAttendanceById(id);
        if (attendanceModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long earlyDepartureMinutes = attendanceService.calculateEarlyDepartureMinutes(attendanceModel, expectedOutTime);
        return new ResponseEntity<>(earlyDepartureMinutes, HttpStatus.OK);
    }

    public List<AttendanceModel> getAllAttendanceRecordsByEmailAndDateRange(String email, String startDate, String endDate){
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date start = inputFormatter.parse(startDate);
            Date end = inputFormatter.parse(endDate);

            List<AttendanceModel> records = attendanceRepository.findByEmail(email);

            return records.stream()
                    .filter(record -> isWithinRange(record.getRecordInTime(), start, end))
                    .collect(Collectors.toList());

        }catch (ParseException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    private boolean isWithinRange(String recordDate, Date start, Date end) {
        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat altFormatter = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)");

        Date recordDateFormatted;
        try {
            recordDateFormatted = isoFormatter.parse(recordDate);
        } catch (ParseException e) {
            try {
                recordDateFormatted = altFormatter.parse(recordDate);
            } catch (Exception exception){
                return false;
            }
        }

        if (recordDate == null) {
            return false;
        }
        return !recordDateFormatted.before(start) && !recordDateFormatted.after(end);
    }
}

