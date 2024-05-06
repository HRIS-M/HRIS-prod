package com.hris.HRIS.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hris.HRIS.dto.ApiResponse;
import com.hris.HRIS.model.ChannelModel;
import com.hris.HRIS.model.DepartmentModel;
import com.hris.HRIS.model.EmployeeModel;
import com.hris.HRIS.repository.ChannelRepository;
import com.hris.HRIS.repository.DepartmentRepository;
import com.hris.HRIS.repository.EmployeeRepository;
import com.hris.HRIS.service.SystemAutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    SystemAutomateService systemAutomateService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveEmployee(@RequestPart("photo") MultipartFile photo,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("email") String email,
                                                    @RequestParam("phone") String phone,
                                                    @RequestParam("address") String address,
                                                    @RequestParam("organizationId") String organizationId,
                                                    @RequestParam("departmentId") String departmentId,
                                                    @RequestParam("jobData") String jobData,
                                                    @RequestParam("gender") String gender,
                                                    @RequestParam("dob") String dob,
                                                    @RequestParam("nic") String nic,
                                                    @RequestParam("status") String status,
                                                    @RequestParam("level") String level
    ) throws IOException {

//        Integer.parseInt(jobData);
        ObjectMapper objectMapper = new ObjectMapper();
        Object fixedJobData = objectMapper.readValue(jobData, Object.class);

        EmployeeModel newEmployee = new EmployeeModel();
        newEmployee.setName(name);
        newEmployee.setEmail(email);
        newEmployee.setPhone(phone);
        newEmployee.setAddress(address);
        newEmployee.setOrganizationId(organizationId);
        newEmployee.setDepartmentId(departmentId);
        newEmployee.setJobData(fixedJobData);
        newEmployee.setGender(gender);
        newEmployee.setDob(dob);
        newEmployee.setNic(nic);
        newEmployee.setPhoto(photo.getBytes());
        newEmployee.setStatus(status);
        newEmployee.setLevel(Integer.parseInt(level));

        EmployeeModel emp = employeeRepository.save(newEmployee);

        systemAutomateService.updateOrganizationEmployees(newEmployee);
        systemAutomateService.assignChannelsToEmployee(emp.getId());
        systemAutomateService.CreateCredentials(newEmployee);

        ApiResponse apiResponse = new ApiResponse("Employee saved successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/active/set-status/{id}")
    public ResponseEntity<ApiResponse> setActivityStatus(@PathVariable String id, @RequestBody EmployeeModel employeeModel) {
        Optional<EmployeeModel> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            EmployeeModel existsEmployeeModel = optionalEmployee.get();

            Boolean status = existsEmployeeModel.getActivityStatus();
            if (status != null) {
                existsEmployeeModel.setActivityStatus(!status);
                existsEmployeeModel.setLastSeen(employeeModel.getLastSeen());
            } else {
                existsEmployeeModel.setActivityStatus(true);
                existsEmployeeModel.setLastSeen(employeeModel.getLastSeen());
            }
            employeeRepository.save(existsEmployeeModel);
        }

        ApiResponse response = new ApiResponse("Set active status successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all")
    public List<EmployeeModel> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/get/id/{id}")
    public ResponseEntity<EmployeeModel> getEmployeeById(@PathVariable String id) {
        Optional<EmployeeModel> employeeModelOptional = employeeRepository.findById(id);

        return employeeModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<EmployeeModel> getEmployeeByEmail(@PathVariable String email) {
        Optional<EmployeeModel> employeeModelOptional = employeeRepository.findOneByEmail(email);

        return employeeModelOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable String id,
                                                      @RequestPart(value = "photo", required = false) MultipartFile photo,
                                                      @RequestParam("name") String name,
                                                      @RequestParam("email") String email,
                                                      @RequestParam("phone") String phone,
                                                      @RequestParam("address") String address,
                                                      @RequestParam("organizationId") String organizationId,
                                                      @RequestParam("departmentId") String departmentId,
                                                      @RequestParam("gender") String gender,
                                                      @RequestParam("dob") String dob,
                                                      @RequestParam("nic") String nic,
                                                      @RequestParam("status") String status,
                                                      @RequestParam("level") String level) throws IOException {

        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(id);

        if (optionalEmployeeModel.isPresent()) {
            EmployeeModel existModel = optionalEmployeeModel.get();

            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setName(name);
            employeeModel.setEmail(email);
            employeeModel.setPhone(phone);
            employeeModel.setAddress(address);
            employeeModel.setOrganizationId(organizationId);
            employeeModel.setDepartmentId(departmentId);
            employeeModel.setChannels(existModel.getChannels());
            employeeModel.setJobData(existModel.getJobData());
            employeeModel.setGender(gender);
            if (photo != null) {
                employeeModel.setPhoto(photo.getBytes());
            } else {
                employeeModel.setPhoto(existModel.getPhoto());
            }
            employeeModel.setDob(dob);
            employeeModel.setNic(nic);
            employeeModel.setStatus(status);
            employeeModel.setLevel(Integer.parseInt(level));
            systemAutomateService.updateEmployeeAndUpdateOrganization(id, employeeModel);
        }

        ApiResponse apiResponse = new ApiResponse("Employee updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update/full/id/{id}")
    public ResponseEntity<ApiResponse> updateEmployeeFull(@PathVariable String id,
                                                          @RequestPart(value = "photo", required = false) MultipartFile photo,
                                                          @RequestParam("name") String name,
                                                          @RequestParam("email") String email,
                                                          @RequestParam("phone") String phone,
                                                          @RequestParam("address") String address,
                                                          @RequestParam("organizationId") String organizationId,
                                                          @RequestParam("departmentId") String departmentId,
                                                          @RequestParam("jobData") String jobData,
                                                          @RequestParam("gender") String gender,
                                                          @RequestParam("dob") String dob,
                                                          @RequestParam("nic") String nic,
                                                          @RequestParam("status") String status,
                                                          @RequestParam(value = "level", required = false) String level
    ) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Object fixedJobData = objectMapper.readValue(jobData, Object.class);

        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(id);

        if (optionalEmployeeModel.isPresent()){
            EmployeeModel newEmployee = optionalEmployeeModel.get();
            newEmployee.setName(name);
            newEmployee.setEmail(email);
            newEmployee.setPhone(phone);
            newEmployee.setAddress(address);
            newEmployee.setOrganizationId(organizationId);
            newEmployee.setDepartmentId(departmentId);
            newEmployee.setJobData(fixedJobData);
            newEmployee.setChannels(newEmployee.getChannels());
            newEmployee.setGender(gender);
            newEmployee.setDob(dob);
            newEmployee.setNic(nic);
            if (photo != null) {
                newEmployee.setPhoto(photo.getBytes());
            } else {
                newEmployee.setPhoto(newEmployee.getPhoto());
            }
            newEmployee.setStatus(status);
            if (level != null && !level.equals("null")){
                newEmployee.setLevel(Integer.parseInt(level));
            }
            else {
                newEmployee.setLevel(newEmployee.getLevel());
            }

            updateChannels(newEmployee);

            EmployeeModel emp = employeeRepository.save(newEmployee);

            systemAutomateService.updateOrganizationSingleEmployeeData(emp);
        }

        ApiResponse apiResponse = new ApiResponse("Employee saved successfully");
        return ResponseEntity.ok(apiResponse);
    }
    private void updateChannels(EmployeeModel employee) {
        if (employee.getChannels() == null) {
            employee.setChannels(new ArrayList<>());
            Optional<List<ChannelModel>> channelModel = channelRepository.findAllByDepartmentId(employee.getDepartmentId());
            channelModel.ifPresent(channels -> employee.getChannels().addAll(channels));
        } else {
            Optional<List<ChannelModel>> channelModel = channelRepository.findAllByDepartmentId(employee.getDepartmentId());
            channelModel.ifPresent(channels -> {
                String departmentId = channels.get(0).getDepartmentId(); // Assuming channels is not empty
                employee.getChannels().replaceAll(c -> c.getDepartmentId().equals(departmentId) ? channels.get(0) : c);
            });
        }
    }

    @PutMapping("/update/email/{email}")
    public ResponseEntity<ApiResponse> updateEmployeeByEmail(@PathVariable String email, @RequestBody EmployeeModel employeeModel) {
        systemAutomateService.updateEmployeeAndUpdateOrganizationByEmail(email, employeeModel);

        ApiResponse apiResponse = new ApiResponse("Employee updated successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable String id) {

        systemAutomateService.DeleteCredentials(id);
        systemAutomateService.deleteEmployeeAndUpdateOrganization(id);

        ApiResponse apiResponse = new ApiResponse("Employee deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/delete/email/{email}")
    public ResponseEntity<ApiResponse> deleteEmployeeByEmail(@PathVariable String email) {
        systemAutomateService.deleteEmployeeAndUpdateOrganizationByEmail(email);

        ApiResponse apiResponse = new ApiResponse("Employee deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
