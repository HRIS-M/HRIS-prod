package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.HolidayModel;
import com.hris.HRIS.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/holiday")
public class HolidayController {

    @Autowired
    HolidayRepository holidayRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveEvent(@RequestBody HolidayModel holidayModel) {

        holidayRepository.save(holidayModel);
        ApiResponse apiResponse = new ApiResponse("Event saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all")
    public List<HolidayModel> getAllEvents() {
        return holidayRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HolidayModel> getEventById(@PathVariable String id) {
        Optional<HolidayModel> holiday = holidayRepository.findById(id);
        return holiday.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEvent(@PathVariable String id) {
        holidayRepository.deleteById(id);
        ApiResponse apiResponse = new ApiResponse("Event deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable String id, @RequestBody HolidayModel holidayModel) {

        Optional<HolidayModel> optionalHolidayModel = holidayRepository.findById(id);

        if (optionalHolidayModel.isPresent()) {
            HolidayModel holiday = optionalHolidayModel.get();
            holiday.setMeta(holidayModel.getMeta());
            holiday.setTitle(holidayModel.getTitle());
            holiday.setStart(holidayModel.getStart());
            holiday.setAllDay(holidayModel.isAllDay());
            holiday.setDraggable(holidayModel.isDraggable());
            holiday.setColor(holidayModel.getColor());
            holiday.setResizable(holidayModel.getResizable());
            holiday.setActions(holidayModel.getActions());

            holidayRepository.save(holiday);
        }

        ApiResponse apiResponse = new ApiResponse("Event updated successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
