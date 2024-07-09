package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.model.OpenUserCredentialsModel;
import com.hris.HRIS.model.OpenUserModel;
import com.hris.HRIS.repository.OpenUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/openUser")
public class OpenUserController {

    @Autowired
    OpenUserRepository openUserRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveOpenUser(OpenUserModel openUserModel) {

        openUserRepository.save(openUserModel);

        ApiResponse apiResponse = new ApiResponse("User saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<OpenUserModel> getAllUsers() {

        return openUserRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<OpenUserModel> getOpenUserById(@PathVariable String id){
        Optional<OpenUserModel> openUserModelOptional = openUserRepository.findById(id);

        return openUserModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<OpenUserModel> getOpenUserByEmail(@PathVariable String email){
        Optional<OpenUserModel> openUserModelOptional = openUserRepository.findOneByEmail(email);

        return openUserModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateOpenUser(@PathVariable String id, @RequestBody OpenUserModel openUserModel) {
        Optional<OpenUserModel> openUserModelOptional = openUserRepository.findById(id);

        if (openUserModelOptional.isPresent()) {
            OpenUserModel existingOpenUser = openUserModelOptional.get();
            existingOpenUser.setName(openUserModel.getName());
            existingOpenUser.setEmail(openUserModel.getEmail());
            existingOpenUser.setPhone_number(openUserModel.getPhone_number());

            openUserRepository.save(existingOpenUser);
        }

        ApiResponse apiResponse = new ApiResponse("User updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/email/{email}")
    public ResponseEntity<ApiResponse> updateUserByEmail(@PathVariable String email, @RequestBody OpenUserModel openUserModel){
        Optional<OpenUserModel> openUserModelOptional = openUserRepository.findOneByEmail(email);

        if(openUserModelOptional.isPresent()){
            OpenUserModel existingUser = openUserModelOptional.get();
            existingUser.setName(openUserModel.getName());
            existingUser.setEmail(openUserModel.getEmail());
            existingUser.setPhone_number(openUserModel.getPhone_number());

            openUserRepository.save(existingUser);
        }

        ApiResponse apiResponse = new ApiResponse("User updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteOpenUser(@PathVariable String id){
        openUserRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("User deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteOpenUserByEmail(@PathVariable String email) {
        openUserRepository.deleteByEmail(email);

        ApiResponse apiResponse = new ApiResponse("User deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }




}
