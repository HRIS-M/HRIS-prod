package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ShiftModel;
import com.hris.HRIS.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shifts")
public class ShiftController {

    @Autowired
    ShiftRepository shiftRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveShift(@RequestBody ShiftModel shiftModel){
        shiftRepository.save(shiftModel);

        ApiResponse apiResponse = new ApiResponse("Shift saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<ShiftModel> getAllShifts(){
        return shiftRepository.findAll();
    }
}
