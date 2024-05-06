package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ChannelModel;
import com.hris.HRIS.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController {
    @Autowired
    ChannelRepository channelRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveChannel(@RequestBody ChannelModel channelModel) {
        channelRepository.save(channelModel);

        ApiResponse apiResponse = new ApiResponse("Channel saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<ChannelModel> getAllChannel() {
        return channelRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<ChannelModel> getChannelById(@PathVariable String id) {
        Optional<ChannelModel> channelModelOptional = channelRepository.findById(id);

        return channelModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/dep/{id}")
    public ResponseEntity<List<ChannelModel>> getChannelByDepartmentId(@PathVariable String id) {
        Optional<List<ChannelModel>> channelModelOptional = channelRepository.findAllByDepartmentId(id);

        return channelModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateChannel(@PathVariable String id, ChannelModel channelModel) {
        Optional<ChannelModel> channelModelOptional = channelRepository.findById(id);

        if (channelModelOptional.isPresent()) {
            channelModel.setName(channelModel.getName());
            channelModel.setDescription(channelModel.getDescription());
            channelModel.setDepartmentId(channelModel.getDepartmentId());
            channelModel.setPhoto(channelModel.getPhoto());

            channelRepository.save(channelModel);

            ApiResponse apiResponse = new ApiResponse("Channel updated successfully");
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteChannel(@PathVariable String id) {
        channelRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Channel deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
