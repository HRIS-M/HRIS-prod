package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.LikeModel;
import com.hris.HRIS.repository.LikeRepository;
import com.hris.HRIS.service.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    MultimediaService multimediaService;

    @PostMapping("/toggle")
    public ResponseEntity<ApiResponse> saveLike(@RequestBody LikeModel likeModel) {
        Optional<LikeModel> existsLikeModel = likeRepository.findById(likeModel.getId());

        if (existsLikeModel.isPresent()) {
            likeRepository.deleteById(likeModel.getId());
        } else {
            likeRepository.save(likeModel);
        }

        String id = likeModel.getId();
        String multimediaId = likeModel.getMultimediaId();

        multimediaService.toggleLike(multimediaId, id);

        ApiResponse apiResponse = new ApiResponse("Like saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("get/all")
    public List<LikeModel> getAllLikes() {
        return likeRepository.findAll();
    }

    @GetMapping("/get/message/{id}")
    public ResponseEntity<List<LikeModel>> getAllLikesByMessage(@PathVariable String id) {
        Optional<List<LikeModel>> likeModelOptional = likeRepository.findAllByMessageId(id);

        return ResponseEntity.ok(likeModelOptional.orElse(null));
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<LikeModel>> getAllLikesByUser(@PathVariable String id) {
        Optional<List<LikeModel>> likeModelOptional = likeRepository.findAllByUserId(id);

        return ResponseEntity.ok(likeModelOptional.orElse(null));
    }

    @GetMapping("/get/multimedia/{id}")
    public ResponseEntity<List<LikeModel>> getAllLikesByMultimedia(@PathVariable String id) {
        Optional<List<LikeModel>> likeModelOptional = likeRepository.findAllByMultimediaId(id);

        return ResponseEntity.ok(likeModelOptional.orElse(null));
    }

    @GetMapping("/get/likes/message/{id}")
    public ResponseEntity<ApiResponse> getLikesByMessage(@PathVariable String id) {
        Optional<List<LikeModel>> likeModelOptional = likeRepository.findAllByMessageId(id);

        if (likeModelOptional.isPresent()) {
            ApiResponse apiResponse = new ApiResponse(""+likeModelOptional.get().size());
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.ok(null);
    }

    @GetMapping("/get/likes/user/{id}")
    public ResponseEntity<ApiResponse> getLikesByUser(@PathVariable String id) {
        Optional<List<LikeModel>> likeModelOptional = likeRepository.findAllByUserId(id);

        if (likeModelOptional.isPresent()) {
            ApiResponse apiResponse = new ApiResponse(""+likeModelOptional.get().size());
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.ok(null);
    }

    @GetMapping("/get/likes/multimedia/{id}")
    public ResponseEntity<ApiResponse> getLikesByMultimedia(@PathVariable String id) {
        Optional<List<LikeModel>> likeModelOptional = likeRepository.findAllByMultimediaId(id);

        if (likeModelOptional.isPresent()) {
            ApiResponse apiResponse = new ApiResponse(""+likeModelOptional.get().size());
            return ResponseEntity.ok(apiResponse);
        }

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLike(@PathVariable String id) {
        likeRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Like deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
