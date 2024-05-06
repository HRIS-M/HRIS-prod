package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ChatModel;
import com.hris.HRIS.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    ChatRepository chatRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveChat(ChatModel chatModel) {
        chatRepository.save(chatModel);

        ApiResponse apiResponse = new ApiResponse("Chat saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<ChatModel> getAllChat() {
        return chatRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<ChatModel> getChatById(@PathVariable String id) {
        Optional<ChatModel> chatModelOptional = chatRepository.findById(id);

        return chatModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable String id) {
        chatRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Chat deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
