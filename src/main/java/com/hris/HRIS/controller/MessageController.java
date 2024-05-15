package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.MessageModel;
import com.hris.HRIS.repository.MessageRepository;
import com.hris.HRIS.service.SystemAutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SystemAutomateService systemAutomateService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveMessage(@RequestBody MessageModel messageModel) {
        messageRepository.save(messageModel);

        systemAutomateService.addMessagesToChat(messageModel);

        ApiResponse apiResponse = new ApiResponse("Message saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MessageModel> getMessageById(@PathVariable String id) {
        Optional<MessageModel> messageModelOptional = messageRepository.findById(id);
        return ResponseEntity.ok(messageModelOptional.orElse(null));
    }

    @GetMapping("/uid/{id}")
    public ResponseEntity<List<MessageModel>> findAllByUserId(@PathVariable String id) {
        Optional<List<MessageModel>> messageModelOptional = messageRepository.findAllByUserId(id);
        return ResponseEntity.ok(messageModelOptional.orElse(null));
    }

    @GetMapping("/cid/{id}")
    public ResponseEntity<List<MessageModel>> findAllByChannelId(@PathVariable String id) {
        Optional<List<MessageModel>> messageModelOptional = messageRepository.findAllByChannelId(id);
        return ResponseEntity.ok(messageModelOptional.orElse(null));
    }

    @GetMapping("/chatid/{id}")
    public ResponseEntity<List<MessageModel>> findAllByChatId(@PathVariable String id) {
        Optional<List<MessageModel>> messageModelOptional = messageRepository.findAllByChatId(id);
        return ResponseEntity.ok(messageModelOptional.orElse(null));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteMessageById(@PathVariable String id) {
        messageRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Message deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable String id, @RequestBody MessageModel messageModel) {
        Optional<MessageModel> messageModelOptional = messageRepository.findById(id);
        if (messageModelOptional.isPresent()) {
            MessageModel message = messageModelOptional.get();
            message.setStatus(messageModel.getStatus());
            messageRepository.save(message);
        }

        systemAutomateService.updateMessageStatus(messageModel.getChatId(), messageModel.getId(), messageModel.getStatus());

        ApiResponse apiResponse = new ApiResponse("Message status updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        // Logic to store the image file and obtain its URL
        // Example: Save the image to Google Drive, AWS S3, etc., and return the URL
        String imageUrl = "https://example.com/image.jpg"; // Replace with actual image URL
        return "{\"imageUrl\": \"" + imageUrl + "\"}";
    }
}
