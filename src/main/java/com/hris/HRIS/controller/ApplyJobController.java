package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ApplyJobModel;
import com.hris.HRIS.repository.ApplyJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/jobApply")
public class ApplyJobController {

    @Autowired
    ApplyJobRepository applyJobRepository;

    //save details
    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveApplyJob(@RequestParam("") MultipartFile file, ApplyJobModel applyJobModel) throws IOException {

        // Check if the file is not empty
        if (file.isEmpty()) {

            ApiResponse apiResponse = new ApiResponse("Please select a file to upload");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        // Check if the file content type is PDF
        else if (!file.getContentType().equalsIgnoreCase("application/pdf")) {

            ApiResponse apiResponse = new ApiResponse("Only PDF files are allowed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        // Check file size
        else if (file.getSize() > DataSize.ofMegabytes(20).toBytes()) {

            ApiResponse apiResponse = new ApiResponse("File size exceeds the maximum allowed size");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }

        // Check if file is uploaded
        if (!file.isEmpty()) {
            // Set the file data as byte array
            applyJobModel.setCv(file.getBytes());
        }

        applyJobRepository.save(applyJobModel);

        ApiResponse apiResponse = new ApiResponse("Apply Job Successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
