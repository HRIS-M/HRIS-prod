package com.hris.HRIS.controller;

import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.PayItemModel;
import com.hris.HRIS.repository.EmployeePayItemRepository;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.repository.PayItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/payitem")
public class PayItemController {
    @Autowired
    PayItemRepository payItemRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private EmployeePayItemRepository employeePayItemRepository;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> savePayItem(@RequestBody PayItemModel payItemModel) {
        payItemModel.setId(null);
        payItemRepository.save(payItemModel);

        ApiResponse apiResponse = new ApiResponse("Pay item added successfully.");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/all/organizationId/{organizationId}")
    public List<PayItemModel> getAllPayItems(@PathVariable String organizationId){

        List<PayItemModel> payitemsList = payItemRepository.findAllByOrganizationId(organizationId);

        for (PayItemModel payItemModel : payitemsList) {
            payItemModel.setIsDeletable(isPayItemDeletable(payItemModel.getId()));
        }

        return payitemsList;
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<PayItemModel> getPayItemById(@PathVariable String id){
        Optional<PayItemModel> payItemModelOptional = payItemRepository.findById(id);

        return payItemModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/is-deletable/id/{id}")
    public Boolean isPayItemDeletable(@PathVariable String id){

        if(employeePayItemRepository.findAllByPayItemId(id).size() > 0){
            return false;
        }else {
            return true;
        }
    }

    @GetMapping("/get/name/{itemName}")
    public ResponseEntity<PayItemModel> getPayItemByName(@PathVariable String itemName){
        Optional<PayItemModel> payItemModelOptional = payItemRepository.findOneByItemName(itemName);

        return payItemModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updatePayItem(@PathVariable String id, @RequestBody PayItemModel payItemModel){
        Optional<PayItemModel> payItemModelOptional = payItemRepository.findById(id);

        if(payItemModelOptional.isPresent()){
            PayItemModel existingPayItem = payItemModelOptional.get();
            existingPayItem.setItemName(payItemModel.getItemName());
            existingPayItem.setDescription(payItemModel.getDescription());
            existingPayItem.setItemType(payItemModel.getItemType());
            existingPayItem.setPaymentType(payItemModel.getPaymentType());
            existingPayItem.setStatus(payItemModel.getStatus());

            payItemRepository.save(existingPayItem);
        }

        ApiResponse apiResponse = new ApiResponse("Pay item updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deletePayItem(@PathVariable String id){
        payItemRepository.deleteById(id);

        ApiResponse apiResponse = new ApiResponse("Pay item deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
