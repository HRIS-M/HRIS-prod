package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.model.TransferModel;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.repository.TransferRepository;
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
@RequestMapping("api/v1/transfer")
public class TransferController {
    String approvedLetter;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LettersGenerationService lettersGenerationService;

    @Autowired
    EmailService emailService;

    @Autowired
    SystemAutomateService systemAutomateService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveLetter(@RequestBody TransferModel transferModel) {
        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(transferModel.getUserId());

        TransferModel newTransferModel = new TransferModel();

        if (optionalEmployeeModel.isPresent()){
            EmployeeModel employeeModel = optionalEmployeeModel.get();

            newTransferModel.setUserId(employeeModel.getId());
            newTransferModel.setOrganizationId(transferModel.getOrganizationId());
            newTransferModel.setTimestamp(transferModel.getTimestamp());
            newTransferModel.setName(employeeModel.getName());
            newTransferModel.setEmail(employeeModel.getEmail());
            newTransferModel.setPhone(employeeModel.getPhone());
            newTransferModel.setJobData(employeeModel.getJobData());
            newTransferModel.setPhoto(employeeModel.getPhoto());
            newTransferModel.setReason(transferModel.getReason());
            newTransferModel.setApproved("pending");

            transferRepository.save(newTransferModel);
        }

        String receivedLetter = lettersGenerationService.generateReceivedTransferLetter(newTransferModel);

        emailService.sendSimpleEmail(transferModel.getEmail(), "Transfer Request", "We received your transfer request. Please find more information in the platform.\n\nBest Regards,\nHR Department");

        ApiResponse apiResponse = new ApiResponse(receivedLetter);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<TransferModel> getAllLetters() {
        return transferRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<TransferModel> getLetterById(@PathVariable String id) {
        Optional<TransferModel> transferModelOptional = transferRepository.findById(id);

        return transferModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<TransferModel> getLetterByEmail(@PathVariable String email) {
        Optional<TransferModel> transferModelOptional = transferRepository.findByEmail(email);

        return transferModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteLetter(@PathVariable String id) {
        transferRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Request deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteLetterByEmail(@PathVariable String email) {
        transferRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Request deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/reason/{id}")
    public ResponseEntity<ApiResponse> updateReason(@PathVariable String id, @RequestBody TransferModel transferModel) {
        Optional<TransferModel> transferModelOptional = transferRepository.findById(id);

        if (transferModelOptional.isPresent()){
            TransferModel newTransfer = transferModelOptional.get();

            newTransfer.setReason(transferModel.getReason());

            transferRepository.save(newTransfer);
        }

        ApiResponse response = new ApiResponse("Reason updated");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateLetter(@PathVariable String id, @RequestBody TransferModel transferModel) {
        Optional<TransferModel> transferModelOptional = transferRepository.findById(id);

        if (transferModelOptional.isPresent()) {
            TransferModel existingLetter = transferModelOptional.get();
            existingLetter.setUserId(transferModel.getUserId());
            existingLetter.setTimestamp(transferModel.getTimestamp());
            existingLetter.setName(transferModel.getName());
            existingLetter.setEmail(transferModel.getEmail());
            existingLetter.setPhone(transferModel.getPhone());
            existingLetter.setJobData(transferModel.getJobData());
            existingLetter.setPhoto(transferModel.getPhoto());
            existingLetter.setReason(transferModel.getReason());
            existingLetter.setApproved(transferModel.getApproved());

            transferRepository.save(existingLetter);
        }

        ApiResponse apiResponse = new ApiResponse("Employee updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/email/{email}")
    public ResponseEntity<ApiResponse> updateLetterByEmail(@PathVariable String email, @RequestBody TransferModel transferModel) {
        Optional<TransferModel> transferModelOptional = transferRepository.findByEmail(email);

        if (transferModelOptional.isPresent()) {
            TransferModel existingLetter = transferModelOptional.get();
            existingLetter.setUserId(transferModel.getUserId());
            existingLetter.setTimestamp(transferModel.getTimestamp());
            existingLetter.setName(transferModel.getName());
            existingLetter.setEmail(transferModel.getEmail());
            existingLetter.setPhone(transferModel.getPhone());
            existingLetter.setJobData(transferModel.getJobData());
            existingLetter.setPhoto(transferModel.getPhoto());
            existingLetter.setReason(transferModel.getReason());
            existingLetter.setApproved(transferModel.getApproved());

            transferRepository.save(existingLetter);
        }

        ApiResponse apiResponse = new ApiResponse("Employee updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/status/id/{id}")
    public ResponseEntity<ApiResponse> approveLetter(@PathVariable String id, @RequestBody TransferModel transferModel) {
        Optional<TransferModel> transferModelOptional = transferRepository.findById(id);

        if (transferModelOptional.isPresent()) {
            TransferModel existingLetter = transferModelOptional.get();
            existingLetter.setApproved(transferModel.getApproved());
            if (transferModel.getJobData() != null){
                existingLetter.setJobData(transferModel.getJobData());
                transferRepository.save(existingLetter);
            }
            else {
                transferRepository.save(existingLetter);
            }

            if (Objects.equals(transferModel.getApproved(), "approved")){
                systemAutomateService.UpdateEmployeeJobDataTransfer(existingLetter);

                approvedLetter = lettersGenerationService.generateApprovedTransferLetter(existingLetter);

                emailService.sendSimpleEmail(existingLetter.getEmail(), "Transfer Request", "Congratulations!\nWe approved your transfer request. Please find more information in platform.\n\nBest Regards,\nHR Department");
            } else if (Objects.equals(transferModel.getApproved(),"declined")){
                approvedLetter = lettersGenerationService.generateRejectedTransferLetter(existingLetter);

                emailService.sendSimpleEmail(existingLetter.getEmail(), "Transfer Request", "Our Apologies!\nWe declined your transfer request. Please find more information in platform.\n\nBest Regards,\nHR Department");
            }

        }

        ApiResponse apiResponse = new ApiResponse(approvedLetter);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/approve/email/{email}")
    public ResponseEntity<ApiResponse> approveLetterByEmail(@PathVariable String email) {
        Optional<TransferModel> transferModelOptional = transferRepository.findByEmail(email);

        if (transferModelOptional.isPresent()) {
            TransferModel existingLetter = transferModelOptional.get();
            existingLetter.setApproved("");

            transferRepository.save(existingLetter);

            systemAutomateService.UpdateEmployeeJobDataTransfer(existingLetter);

            approvedLetter = lettersGenerationService.generateApprovedTransferLetter(existingLetter);
            emailService.sendSimpleEmail(existingLetter.getEmail(), "Transfer Request", "Congratulations!\nWe approved your transfer request. Please find your letter in platform.\n\nBest Regards,\nHR Department");
        }

        ApiResponse apiResponse = new ApiResponse(approvedLetter);
        return ResponseEntity.ok(apiResponse);
    }
}
