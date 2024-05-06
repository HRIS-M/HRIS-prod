package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.NotificationModel;
import com.hris.HRIS.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> addNotification(@RequestBody NotificationModel notificationModel){
        notificationRepository.save(notificationModel);

        ApiResponse response = new ApiResponse("notification added");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all")
    public List<NotificationModel> getAllNotifications(){
        return notificationRepository.findAll();
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable String id, @RequestBody NotificationModel notificationModel){
        Optional<NotificationModel> optionalNotificationModel = notificationRepository.findById(id);

        if (optionalNotificationModel.isPresent()){
            NotificationModel existModel = optionalNotificationModel.get();
            existModel.setStatus(!existModel.getStatus());

            notificationRepository.save(existModel);
        }

        ApiResponse response = new ApiResponse("status changed");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteNotification(@PathVariable String id){
        notificationRepository.deleteById(id);

        ApiResponse response = new ApiResponse("notification deleted");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/all/id/{id}")
    public ResponseEntity<ApiResponse> deleteAllNotificationsByUserId(@PathVariable String id){
        notificationRepository.deleteAllByUserId(id);

        ApiResponse response = new ApiResponse("All notifications deleted for "+ id +" user");
        return ResponseEntity.ok(response);
    }
}
