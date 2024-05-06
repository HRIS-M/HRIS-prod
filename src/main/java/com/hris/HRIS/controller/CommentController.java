package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.CommentModel;
import com.hris.HRIS.repository.CommentRepository;
import com.hris.HRIS.service.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MultimediaService multimediaService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveComment(@RequestBody CommentModel commentModel){
        CommentModel savedComment = commentRepository.save(commentModel);

        multimediaService.saveComment(savedComment.getMultimediaId(), savedComment.getId());

        ApiResponse apiResponse = new ApiResponse("Comment saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<CommentModel> getAllComments(){
        return commentRepository.findAll();
    }

    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<CommentModel>> getAllCommentsByUser(@PathVariable String id){
        Optional<List<CommentModel>> commentModelOptional = commentRepository.findAllByUserId(id);
        return ResponseEntity.ok(commentModelOptional.orElse(null));
    }

    @GetMapping("/get/message/{id}")
    public ResponseEntity<List<CommentModel>> getAllCommentsByMessage(@PathVariable String id){
        Optional<List<CommentModel>> commentModelOptional = commentRepository.findAllByMessageId(id);
        return ResponseEntity.ok(commentModelOptional.orElse(null));
    }

    @GetMapping("/get/multimedia/{id}")
    public ResponseEntity<List<CommentModel>> getAllCommentsByMultimedia(@PathVariable String id){
        Optional<List<CommentModel>> commentModelOptional = commentRepository.findAllByMultimediaId(id);
        return ResponseEntity.ok(commentModelOptional.orElse(null));
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable String id, @RequestBody CommentModel commentModel){
        Optional<CommentModel> commentModelOptional = commentRepository.findById(id);

        if(commentModelOptional.isPresent()){
            CommentModel existingCommentInfo = commentModelOptional.get();
            existingCommentInfo.setComment(commentModel.getComment());
            commentRepository.save(existingCommentInfo);
        }

        ApiResponse apiResponse = new ApiResponse("Comment updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable String id){
        Optional<CommentModel> commentModelOptional = commentRepository.findById(id);

        if (commentModelOptional.isPresent()){
            CommentModel existingCommentInfo = commentModelOptional.get();

            multimediaService.deleteComment(existingCommentInfo.getMultimediaId(), existingCommentInfo.getId());

            commentRepository.deleteById(id);
        }

        ApiResponse apiResponse = new ApiResponse("Comment deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

}
