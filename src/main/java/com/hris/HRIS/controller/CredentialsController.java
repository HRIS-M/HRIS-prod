package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CredentialsModel;
import com.hris.HRIS.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialsController {

    @Autowired
    CredentialsRepository credentialsRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveCredentials(@RequestBody CredentialsModel credentialsModel) {
        credentialsRepository.save(credentialsModel);

        ApiResponse apiResponse = new ApiResponse("Credentials saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<CredentialsModel> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<CredentialsModel> getCredentialsByEmail(@PathVariable String email) {
        Optional<CredentialsModel> credentialsModelOptional = credentialsRepository.findByEmail(email);

        return credentialsModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteCredentialsByEmail(@PathVariable String email) {
        credentialsRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Credentials deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/password/{email}")
    public ResponseEntity<ApiResponse> updateCredentialsByEmail(@PathVariable String email, @RequestBody CredentialsModel credentialsModel) {
        Optional<CredentialsModel> credentialsModelOptional = credentialsRepository.findByEmail(email);

        if (credentialsModelOptional.isPresent()) {
            CredentialsModel existingCredentials = credentialsModelOptional.get();
            existingCredentials.setPassword(credentialsModel.getPassword());

            credentialsRepository.save(existingCredentials);
        }

        ApiResponse apiResponse = new ApiResponse("Credentials updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/level/{email}")
    public ResponseEntity<ApiResponse> updateLevelByEmail(@PathVariable String email, @RequestBody CredentialsModel credentialsModel) {
        Optional<CredentialsModel> credentialsModelOptional = credentialsRepository.findByEmail(email);

        if (credentialsModelOptional.isPresent()) {
            CredentialsModel existingCredentials = credentialsModelOptional.get();
            existingCredentials.setLevel(credentialsModel.getLevel());

            credentialsRepository.save(existingCredentials);
        }

        ApiResponse apiResponse = new ApiResponse("Credentials updated successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
