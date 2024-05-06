package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.model.PromotionModel;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.repository.PromotionRepository;
import com.hris.HRIS.service.EmailService;
import com.hris.HRIS.service.LettersGenerationService;
import com.hris.HRIS.service.SystemAutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/promotion")
public class PromotionController {

    String approvedLetter;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LettersGenerationService lettersGenerationService;

    @Autowired
    EmailService emailService;

    @Autowired
    SystemAutomateService systemAutomateService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveLetter(@RequestBody PromotionModel promotionModel){
        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(promotionModel.getUserId());

        PromotionModel newPromotionModel = new PromotionModel();

        if (optionalEmployeeModel.isPresent()){
            EmployeeModel employeeModel = optionalEmployeeModel.get();

            newPromotionModel.setUserId(employeeModel.getId());
            newPromotionModel.setOrganizationId(promotionModel.getOrganizationId());
            newPromotionModel.setTimestamp(promotionModel.getTimestamp());
            newPromotionModel.setName(employeeModel.getName());
            newPromotionModel.setEmail(employeeModel.getEmail());
            newPromotionModel.setPhone(employeeModel.getPhone());
            newPromotionModel.setJobData(employeeModel.getJobData());
            newPromotionModel.setPhoto(employeeModel.getPhoto());
            newPromotionModel.setReason(promotionModel.getReason());
            newPromotionModel.setApproved("pending");

            promotionRepository.save(newPromotionModel);
        }

        String receivedLetter = lettersGenerationService.generateReceivedPromotionLetter(newPromotionModel);

        emailService.sendSimpleEmail(promotionModel.getEmail(), "Promotion Request", "We received your promotion request. Please find your letter in platform.\n\nBest Regards,\nHR Department");

        ApiResponse apiResponse = new ApiResponse(receivedLetter);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<PromotionModel> getAllLetters(){
        return promotionRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<PromotionModel> getLetterById(@PathVariable String id){
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findById(id);

        return promotionModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<PromotionModel> getLetterByEmail(@PathVariable String email){
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findByEmail(email);

        return promotionModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteLetter(@PathVariable String id){
        promotionRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Request deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteLetterByEmail(@PathVariable String email){
        promotionRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Request deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/reason/{id}")
    public ResponseEntity<ApiResponse> updateReason(@PathVariable String id, @RequestBody PromotionModel promotionModel) {
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findById(id);

        if (promotionModelOptional.isPresent()){
            PromotionModel newPromotionModel = promotionModelOptional.get();

            newPromotionModel.setReason(promotionModel.getReason());

            promotionRepository.save(newPromotionModel);
        }

        ApiResponse response = new ApiResponse("Reason updated");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateLetterById(@PathVariable String id, @RequestBody PromotionModel promotionModel){
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findById(id);

        if(promotionModelOptional.isPresent()){
            PromotionModel existingLetter = promotionModelOptional.get();
            existingLetter.setUserId(promotionModel.getUserId());
            existingLetter.setTimestamp(promotionModel.getTimestamp());
            existingLetter.setName(promotionModel.getName());
            existingLetter.setEmail(promotionModel.getEmail());
            existingLetter.setPhone(promotionModel.getPhone());
            existingLetter.setJobData(promotionModel.getJobData());
            existingLetter.setPhoto(promotionModel.getPhoto());
            existingLetter.setReason(promotionModel.getReason());
            existingLetter.setApproved(promotionModel.getApproved());

            promotionRepository.save(existingLetter);
        }

        ApiResponse apiResponse = new ApiResponse("Employee approved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/email/{email}")
    public ResponseEntity<ApiResponse> updateLetterByEmail(@PathVariable String email, @RequestBody PromotionModel promotionModel){
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findByEmail(email);

        if(promotionModelOptional.isPresent()){
            PromotionModel existingLetter = promotionModelOptional.get();
            existingLetter.setUserId(promotionModel.getUserId());
            existingLetter.setTimestamp(promotionModel.getTimestamp());
            existingLetter.setName(promotionModel.getName());
            existingLetter.setEmail(promotionModel.getEmail());
            existingLetter.setPhone(promotionModel.getPhone());
            existingLetter.setJobData(promotionModel.getJobData());
            existingLetter.setPhoto(promotionModel.getPhoto());
            existingLetter.setReason(promotionModel.getReason());
            existingLetter.setApproved(promotionModel.getApproved());

            promotionRepository.save(existingLetter);
        }

        ApiResponse apiResponse = new ApiResponse("Employee approved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/status/id/{id}")
    public ResponseEntity<ApiResponse> approveLetter(@PathVariable String id, @RequestBody PromotionModel promotionModel){
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findById(id);

        if(promotionModelOptional.isPresent()){
            PromotionModel existingLetter = promotionModelOptional.get();
            existingLetter.setApproved(promotionModel.getApproved());

            if (promotionModel.getJobData() != null){
                existingLetter.setJobData(promotionModel.getJobData());
                promotionRepository.save(existingLetter);
            }
            else {
                promotionRepository.save(existingLetter);
            }

            if (Objects.equals(promotionModel.getApproved(), "approved")){
                systemAutomateService.UpdateEmployeeJobDataPromotion(existingLetter);

                approvedLetter = lettersGenerationService.generateApprovedPromotionLetter(existingLetter);

                emailService.sendSimpleEmail(existingLetter.getEmail(), "Promotion Request", "Congratulations!\nWe approved your promotion request. Please find more information in platform.\n\nBest Regards,\nHR Department");
            } else if (Objects.equals(promotionModel.getApproved(),"declined")){
                approvedLetter = lettersGenerationService.generateRejectedPromotionLetter(existingLetter);

                emailService.sendSimpleEmail(existingLetter.getEmail(), "Promotion Request", "Our Apologies!\nWe declined your promotion request. Please find more information in platform.\n\nBest Regards,\nHR Department");
            }
        }

        ApiResponse apiResponse = new ApiResponse(approvedLetter);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/approve/id/{id}")
    public ResponseEntity<ApiResponse> approveLetter(@PathVariable String id){
        Optional<PromotionModel> promotionModelOptional = promotionRepository.findById(id);

        if(promotionModelOptional.isPresent()){
            PromotionModel existingLetter = promotionModelOptional.get();
            existingLetter.setApproved("");

            promotionRepository.save(existingLetter);

            systemAutomateService.UpdateEmployeeJobDataPromotion(existingLetter);

            approvedLetter = lettersGenerationService.generateApprovedPromotionLetter(existingLetter);
            emailService.sendSimpleEmail(existingLetter.getEmail(), "Promotion Request", "Congratulations!\nWe approved your promotion request. Please find your letter in platform.\n\nBest Regards,\nHR Department");
        }

        ApiResponse apiResponse = new ApiResponse(approvedLetter);
        return ResponseEntity.ok(apiResponse);
    }
}
