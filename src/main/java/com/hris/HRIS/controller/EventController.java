package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.EventModel;
import com.hris.HRIS.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveEvent(@RequestBody EventModel eventModel) {
        EventModel savedEvent = eventService.saveEvent(eventModel);
        ApiResponse apiResponse = new ApiResponse("Event saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<EventModel> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EventModel> getEventById(@PathVariable String id) {
        Optional<EventModel> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        ApiResponse apiResponse = new ApiResponse("Event deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable String id, @RequestBody EventModel eventModel) {
        EventModel updatedEvent = eventService.updateEvent(id, eventModel);
        ApiResponse apiResponse = new ApiResponse("Event updated successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
