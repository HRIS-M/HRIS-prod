package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ViewsModel;
import com.hris.HRIS.repository.ViewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/views")
public class ViewsController {
    @Autowired
    ViewsRepository viewsRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveViews(@RequestBody ViewsModel viewsModel) {
        viewsRepository.save(viewsModel);

        ApiResponse apiResponse = new ApiResponse("Views saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/message/{id}")
    public ResponseEntity<List<ViewsModel>> getViewsByMessageId(@PathVariable String id) {
        Optional<List<ViewsModel>> viewsModelOptional = viewsRepository.findAllByMessageId(id);

        return viewsModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
