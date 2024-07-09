package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ShiftModel;
import com.hris.HRIS.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/get/{id}")
    public ResponseEntity<ShiftModel> getShiftById(@PathVariable String id){
        Optional<ShiftModel> shiftModelOptional = shiftRepository.findById(id);
        return shiftModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateShift(@PathVariable String id, @RequestBody ShiftModel shiftModel){
        Optional<ShiftModel> shiftModelOptional = shiftRepository.findById(id);

        if(shiftModelOptional.isPresent()){
            ShiftModel existingShift = shiftModelOptional.get();
            existingShift.setLongName(shiftModel.getLongName());
            existingShift.setName(shiftModel.getName());
            existingShift.setStartTime(shiftModel.getStartTime());
            existingShift.setEndTime(shiftModel.getEndTime());
            existingShift.setDuration(shiftModel.getDuration());
            existingShift.setEarliestInTime(shiftModel.getEarliestInTime());
            existingShift.setLatestOutTime(shiftModel.getLatestOutTime());
            existingShift.setFirstHalfDuration(shiftModel.getFirstHalfDuration());
            existingShift.setSecondHalfDuration(shiftModel.getSecondHalfDuration());
            existingShift.setShiftNature(shiftModel.getShiftNature());
            existingShift.setOffShift(shiftModel.getOffShift());
            existingShift.setDeductingHours(shiftModel.getDeductingHours());
            existingShift.setMinPreOTHours(shiftModel.getMinPreOTHours());
            existingShift.setMinPostOTHours(shiftModel.getMinPostOTHours());
            existingShift.setMaxPreOTHours(shiftModel.getMaxPreOTHours());
            existingShift.setMaxPostOTHours(shiftModel.getMaxPostOTHours());
            existingShift.setDescription(shiftModel.getDescription());

            shiftRepository.save(existingShift);
        }

        ApiResponse apiResponse = new ApiResponse("Shift updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteShift(@PathVariable String id){
        shiftRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Shift deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
