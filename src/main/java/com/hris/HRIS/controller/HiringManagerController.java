package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.HiringManagerModel;
import com.hris.HRIS.repository.HiringManagerRepository;
import com.hris.HRIS.service.HiringManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/hiringManager")
public class HiringManagerController {

    @Autowired
    private HiringManagerRepository hiringManagerRepository;

    @Autowired
    private HiringManagerService hiringManagerService;

    @GetMapping("/all/details")
    public List<HiringManagerModel> getAllDetails(){

        return hiringManagerRepository.findAll();

    }

    //save details
    @PostMapping("/save/details")
    public ResponseEntity<ApiResponse> saveDetails(@RequestBody Map<String, Object> request) {
        String hiringManagerName = (String) request.get("hiringManagerName");
        float technicalScore = ((Number) request.get("technicalScore")).floatValue();
        float generalScore = ((Number) request.get("generalScore")).floatValue();
        float totalScore = ((Number) request.get("totalScore")).floatValue();
        float satisfactionScore = ((Number) request.get("satisfactionScore")).floatValue();
        String meetingStartTime = (String) request.get("meetingStartTime");
        String meetingEndTime = (String) request.get("meetingEndTime");
        float costPerHire = ((Number) request.get("costPerHire")).floatValue();

        hiringManagerService.saveDetails(hiringManagerName, technicalScore, generalScore, totalScore, satisfactionScore, meetingStartTime, meetingEndTime, costPerHire);

        ApiResponse apiResponse = new ApiResponse("Saved Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    //edit details
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateHiringManagerDetails(@PathVariable String id, @RequestBody HiringManagerModel hiringManagerModel){

        hiringManagerService.updateHiringManagerDetails(id, hiringManagerModel.getHiring_manager_name(), hiringManagerModel.getTechnical_score(), hiringManagerModel.getGeneral_score(), hiringManagerModel.getTotal_score(), hiringManagerModel.getSatisfaction_score(), hiringManagerModel.getMeeting_start_time(), hiringManagerModel.getMeeting_end_time(), hiringManagerModel.getCost_per_hire());

        ApiResponse apiResponse = new ApiResponse("Updated Successfully");
        return ResponseEntity.ok(apiResponse);
    }

    //delete details
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteHiringManagerDetails(@PathVariable String id){

        hiringManagerRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Deleted Successfully");
        return ResponseEntity.ok(apiResponse);
    }
    
}
