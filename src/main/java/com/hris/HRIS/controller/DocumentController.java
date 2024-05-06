package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.DocumentModel;
import com.hris.HRIS.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/document")
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveDocument(@RequestBody DocumentModel documentModel) {
        documentRepository.save(documentModel);

        ApiResponse apiResponse = new ApiResponse("Document saved successfully");
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/get/all")
    public List<DocumentModel> getAllDocuments() {
        return documentRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DocumentModel> getDocumentById(@PathVariable String id) {
        Optional<DocumentModel> documentModelOptional = documentRepository.findById(id);

        return documentModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/admin/{adminId}")
    public ResponseEntity<List<DocumentModel>> getAllDocumentsByAdminId(@PathVariable String adminId) {
        Optional<List<DocumentModel>> documentModelOptional = documentRepository.findAllByAdminId(adminId);

        return ResponseEntity.ok(documentModelOptional.orElse(null));
    }

    @GetMapping("/get/organization/{organizationId}")
    public ResponseEntity<List<DocumentModel>> getAllDocumentsByOrganizationId(@PathVariable String organizationId) {
        Optional<List<DocumentModel>> documentModelOptional = documentRepository.findAllByOrganizationId(organizationId);

        return ResponseEntity.ok(documentModelOptional.orElse(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteDocumentById(@PathVariable String id) {
        documentRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Document deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/all/admin/{adminId}")
    public ResponseEntity<ApiResponse> deleteAllDocumentsByAdminId(@PathVariable String adminId) {
        documentRepository.deleteAllByAdminId(adminId);

        ApiResponse apiResponse = new ApiResponse("All documents deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/all/organization/{organizationId}")
    public ResponseEntity<ApiResponse> deleteAllDocumentsByOrganizationId(@PathVariable String organizationId) {
        documentRepository.deleteAllByOrganizationId(organizationId);

        ApiResponse apiResponse = new ApiResponse("All documents deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateDocument(@PathVariable String id, @RequestBody DocumentModel documentModel) {
        Optional<DocumentModel> documentModelOptional = documentRepository.findById(id);

        if (documentModelOptional.isPresent()) {
            DocumentModel existingDocument = documentModelOptional.get();
            existingDocument.setContent(documentModel.getContent());
            existingDocument.setTimestamp(documentModel.getTimestamp());
            documentRepository.save(existingDocument);

            ApiResponse apiResponse = new ApiResponse("Document updated successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }
}
