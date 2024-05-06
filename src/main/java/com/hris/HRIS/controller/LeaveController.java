package com.hris.HRIS.controller;

import com.hris.HRIS.model.AttendanceModel;
import com.hris.HRIS.model.LeaveModel;
import com.hris.HRIS.repository.LeaveRepository;
import com.hris.HRIS.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveRepository leaveRepository;

    // Endpoint to create a new leave
    @PostMapping("/create")
    public ResponseEntity<LeaveModel> createLeave(@RequestBody LeaveModel leaveModel) {
        LeaveModel createdLeave = leaveService.createLeave(leaveModel);
        return new ResponseEntity<>(createdLeave, HttpStatus.CREATED);
    }

    // Endpoint to retrieve all leaves
    @GetMapping("/get/all")
    public ResponseEntity<List<LeaveModel>> getAllLeaves() {
        List<LeaveModel> allLeaves = leaveService.getAllLeaves();
        return new ResponseEntity<>(allLeaves, HttpStatus.OK);
    }

    // Endpoint to retrieve a specific leave by ID

    @GetMapping("/get/{id}")
    public ResponseEntity<LeaveModel> getLeaveById(@PathVariable String id) {
        Optional<LeaveModel> leabeModelOptional = leaveRepository.findById(id);

        return leabeModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint to update an existing leave
//    @PutMapping("/update/{id}")
//    public ResponseEntity<LeaveModel> updateLeave(@PathVariable String id, @RequestBody LeaveModel leaveModel) {
//        LeaveModel updatedLeave = leaveService.updateLeave(id, leaveModel);
//        if (updatedLeave != null) {
//            return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    // Endpoint to delete a leave by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable("id") String id) {
        boolean deleted = leaveService.deleteLeave(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
