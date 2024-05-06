package com.hris.HRIS.controller;


import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CredentialsModel;
import com.hris.HRIS.model.OpenUserCredentialsModel;
import com.hris.HRIS.repository.OpenUserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/openUserCredentials")
public class OpenUserCredentialsController {

    @Autowired
    OpenUserCredentialsRepository openUserCredentialsRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveOpenUserCredentials(OpenUserCredentialsModel openUserCredentialsModel) {

        openUserCredentialsRepository.save(openUserCredentialsModel);

            ApiResponse apiResponse = new ApiResponse("Credentials saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/gel/all")
    public List<OpenUserCredentialsModel> getAllCredentials() {

        return openUserCredentialsRepository.findAll();
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<OpenUserCredentialsModel> getOpenUserCredentialsByEmail(@PathVariable String email) {
        Optional<OpenUserCredentialsModel> openUserCredentialsModelOptional = openUserCredentialsRepository.findByEmail(email);

        return openUserCredentialsModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteOpenUserCredentialsByEmail(@PathVariable String email) {
        openUserCredentialsRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Credentials deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/password/{email}")
    public ResponseEntity<ApiResponse> updateOpenUserCredentialsByEmail(@PathVariable String email, @RequestBody OpenUserCredentialsModel openUserCredentialsModel) {
        Optional<OpenUserCredentialsModel> openUserCredentialsModelOptional = openUserCredentialsRepository.findByEmail(email);

        if (openUserCredentialsModelOptional.isPresent()) {
            OpenUserCredentialsModel existingCredentials = openUserCredentialsModelOptional.get();
            existingCredentials.setPassword(openUserCredentialsModel.getPassword());

            openUserCredentialsRepository.save(existingCredentials);
        }

        ApiResponse apiResponse = new ApiResponse("Credentials updated successfully");
        return ResponseEntity.ok(apiResponse);
    }


}
