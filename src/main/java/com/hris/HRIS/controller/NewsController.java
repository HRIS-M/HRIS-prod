package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.NewsModel;
import com.hris.HRIS.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveNews(@RequestBody NewsModel newsModel){
        newsRepository.save(newsModel);

        ApiResponse response = new ApiResponse("News saved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all")
    public List<NewsModel> getAllNews(){
        return newsRepository.findAll();
    }
}
