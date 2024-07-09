package com.hris.HRIS.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hris.HRIS.model.*;
import com.hris.HRIS.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class SystemAutomateService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ExitListRepository exitListRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    CredentialsRepository credentialsRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    EncryptionService encryptionService;

    public void UpdateEmployeeJobDataExit(ExitListModel exitListModel) {
        Optional<EmployeeModel> employee =  employeeRepository.findOneByEmail(exitListModel.getEmail());

        if (employee.isPresent()) {
            EmployeeModel existingEmployee = employee.get();
            existingEmployee.setJobData(exitListModel.getJobData());
            employeeRepository.save(existingEmployee);
        }
    }

    public void UpdateEmployeeJobDataPromotion(PromotionModel promotionModel){
        Optional<EmployeeModel> employee =  employeeRepository.findOneByEmail(promotionModel.getEmail());

        if (employee.isPresent()) {
            EmployeeModel existingEmployee = employee.get();
            existingEmployee.setJobData(promotionModel.getJobData());
            employeeRepository.save(existingEmployee);
        }
    }

    public void UpdateEmployeeJobDataTransfer(TransferModel transferModel){
        Optional<EmployeeModel> employee =  employeeRepository.findOneByEmail(transferModel.getEmail());

        if (employee.isPresent()) {
            EmployeeModel existingEmployee = employee.get();
            existingEmployee.setJobData(transferModel.getJobData());
            employeeRepository.save(existingEmployee);
        }
    }

    public void CreateCredentials(EmployeeModel employeeModel) throws Exception {
        Optional<OrganizationModel> optionalOrganizationModel = organizationRepository.findById(employeeModel.getOrganizationId());
        if (optionalOrganizationModel.isPresent()){
            OrganizationModel organizationModel = optionalOrganizationModel.get();

            String orgName = organizationModel.getOrganizationName();
            String email = employeeModel.getEmail();
            String password = String.valueOf(random_Password(10));
            String encryptedPassword = encryptionService.encryptPassword(password);
            String name = employeeModel.getName().split(" ")[0];
            String para = "Thank you for registering with "+orgName+".\n\nUse following credentials to login to the system at first time.\nEmail: "+email+"\nPassword: "+password+"\n\n";
            String tag = "Best Regards,\n"+orgName+" Team.\n\n";
            String footer = "Powered by SparkC";

            CredentialsModel credentialsModel = new CredentialsModel();
            credentialsModel.setEmail(employeeModel.getEmail());
            credentialsModel.setPassword(encryptedPassword);
            credentialsModel.setLevel("2");
            credentialsRepository.save(credentialsModel);

            emailService.sendSimpleEmail(employeeModel.getEmail(),"Login Credentials","Hello "+name+",\n"+para+tag+footer);
        }
    }

    public void updateCredentialLevel(String empId, EmployeeModel employeeModel){
        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(empId);
        if (optionalEmployeeModel.isPresent()){
            EmployeeModel employee = optionalEmployeeModel.get();

            Optional<CredentialsModel> optionalCredentialsModel = credentialsRepository.findByEmail(employee.getEmail());
            if (optionalCredentialsModel.isPresent()){
                CredentialsModel credentialsModel = optionalCredentialsModel.get();
                credentialsModel.setLevel(String.valueOf(employeeModel.getLevel()));
                credentialsRepository.save(credentialsModel);
            }
        }
    }
    static char[] random_Password(int len)
    {
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String values = Capital_chars + Small_chars +
                numbers + symbols;

        // Using random method
        Random rndm_method = new Random();

        char[] password = new char[len];

        for (int i = 0; i < len; i++)
        {
            password[i] = values.charAt(rndm_method.nextInt(values.length()));
        }
        return password;
    }

    public void DeleteCredentials(String id){
        Optional<EmployeeModel> optionalEmployeeModel = employeeRepository.findById(id);

        if (optionalEmployeeModel.isPresent()){
            EmployeeModel employeeModel = optionalEmployeeModel.get();
            credentialsRepository.deleteByEmail(employeeModel.getEmail());
        }
    }

    public Float CalculateGratuity(ExitListModel exitListModel) {
        String date1 = exitListModel.getDoj();
        String date2 = exitListModel.getDoe();

        LocalDate localDate1 = LocalDate.parse(date1);
        LocalDate localDate2 = LocalDate.parse(date2);

        Period period = Period.between(localDate1, localDate2);
        int years = period.getYears();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jobDataMap = objectMapper.convertValue(exitListModel.getJobData(), Map.class);
        Object salaryObject = jobDataMap.get("salary");

        float baseSalary = Float.parseFloat(salaryObject.toString());

//        Gratuity Formula: Gratuity (G) = n*b*15/26

        return years * baseSalary * 15 / 26;
    }

    public void addExitList(EmployeeExitModel employeeExitModel) {
        ExitListModel exitListModel = new ExitListModel();

        exitListModel.setName(employeeExitModel.getName());
        exitListModel.setEmail(employeeExitModel.getEmail());
        exitListModel.setOrganizationId(employeeExitModel.getOrganizationId());
        exitListModel.setPhone(employeeExitModel.getPhone());
        exitListModel.setTelephone(employeeExitModel.getTelephone());
        exitListModel.setAddress(employeeExitModel.getAddress());
        exitListModel.setJobData(employeeExitModel.getJobData());
        exitListModel.setDoe(employeeExitModel.getDoe());
        exitListModel.setDoj(employeeExitModel.getDoj());
        exitListModel.setStatus(employeeExitModel.getStatus());
        exitListModel.setDateOfRetirement(employeeExitModel.getDateOfRetirement());
        exitListModel.setExitReason(employeeExitModel.getExitReason());
        exitListModel.setDateOfContractEnd(employeeExitModel.getDateOfContractEnd());
        exitListModel.setPhoto(employeeExitModel.getPhoto());
        exitListRepository.save(exitListModel);

        emailService.sendSimpleEmail(employeeExitModel.getEmail(), "Employee Exit", "Your exit request has been approved");
    }

    public void updateOrganizationDepartments(DepartmentModel departmentModel){
        Optional<OrganizationModel> organization =  organizationRepository.findById(departmentModel.getOrganizationId());

        if (organization.isPresent()) {
            OrganizationModel existingOrganization = organization.get();
            // Check if departments list is null, and initialize it if necessary
            if (existingOrganization.getDepartments() == null) {
                existingOrganization.setDepartments(new ArrayList<>());
            }

            // Add the new department to the list
            existingOrganization.getDepartments().add(departmentModel);

            organizationRepository.save(existingOrganization);
        }
    }

    public void updateOrganizationEmployees(EmployeeModel employeeModel){
        Optional<OrganizationModel> organization =  organizationRepository.findById(employeeModel.getOrganizationId());

        if (organization.isPresent()) {
            OrganizationModel existingOrganization = organization.get();
            // Check if employees list is null, and initialize it if necessary
            if (existingOrganization.getEmployees() == null) {
                existingOrganization.setEmployees(new ArrayList<>());
            }

            // Add the new employee to the list
            existingOrganization.getEmployees().add(employeeModel);

            organizationRepository.save(existingOrganization);
        }
    }

    public void deleteDepartmentAndUpdateOrganization(String departmentId) {
        Optional<DepartmentModel> departmentOptional = departmentRepository.findById(departmentId);

        if (departmentOptional.isPresent()) {
            DepartmentModel departmentToDelete = departmentOptional.get();
            String organizationId = departmentToDelete.getOrganizationId();

            // Delete the department
            departmentRepository.deleteById(departmentId);

            // Update the organization
            Optional<OrganizationModel> organizationOptional = organizationRepository.findById(organizationId);

            if (organizationOptional.isPresent()) {
                OrganizationModel existingOrganization = organizationOptional.get();

                // Check if departments list is null, and initialize it if necessary
                if (existingOrganization.getDepartments() == null) {
                    existingOrganization.setDepartments(new ArrayList<>());
                } else {
                    // Remove the deleted department from the list
                    existingOrganization.getDepartments().removeIf(d -> d.getId().equals(departmentId));
                }

                organizationRepository.save(existingOrganization);
            }
        }
    }

    public void deleteEmployeeAndUpdateOrganization(String employeeId) {
        Optional<EmployeeModel> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isPresent()) {
            EmployeeModel employeeToDelete = employeeOptional.get();
            String organizationId = employeeToDelete.getOrganizationId();

            // Delete the employee
            employeeRepository.deleteById(employeeId);

            // Update the organization
            Optional<OrganizationModel> organizationOptional = organizationRepository.findById(organizationId);

            if (organizationOptional.isPresent()) {
                OrganizationModel existingOrganization = organizationOptional.get();

                // Check if employees list is null, and initialize it if necessary
                if (existingOrganization.getEmployees() == null) {
                    existingOrganization.setEmployees(new ArrayList<>());
                } else {
                    // Remove the deleted employee from the list
                    existingOrganization.getEmployees().removeIf(e -> e.getId().equals(employeeId));
                }

                organizationRepository.save(existingOrganization);
            }
        }
    }

    public void deleteEmployeeAndUpdateOrganizationByEmail(String employeeEmail) {
        Optional<EmployeeModel> employeeOptional = employeeRepository.findOneByEmail(employeeEmail);

        if (employeeOptional.isPresent()) {
            EmployeeModel employeeToDelete = employeeOptional.get();
            String organizationId = employeeToDelete.getOrganizationId();

            // Delete the employee
            employeeRepository.deleteById(employeeToDelete.getId());

            // Update the organization
            Optional<OrganizationModel> organizationOptional = organizationRepository.findById(organizationId);

            if (organizationOptional.isPresent()) {
                OrganizationModel existingOrganization = organizationOptional.get();

                // Check if employees list is null, and initialize it if necessary
                if (existingOrganization.getEmployees() == null) {
                    existingOrganization.setEmployees(new ArrayList<>());
                } else {
                    // Remove the deleted employee from the list
                    existingOrganization.getEmployees().removeIf(e -> e.getEmail().equals(employeeEmail));
                }

                organizationRepository.save(existingOrganization);
            }
        }
    }

    public void updateDepartmentAndUpdateOrganization(String id, DepartmentModel updatedDepartment) {
        Optional<DepartmentModel> existingDepartmentOptional = departmentRepository.findById(id);

        if (existingDepartmentOptional.isPresent()) {
            DepartmentModel existingDepartment = existingDepartmentOptional.get();
            String organizationId = existingDepartment.getOrganizationId();

            // Update the department
            existingDepartment.setName(updatedDepartment.getName());
            existingDepartment.setDescription(updatedDepartment.getDescription());

            departmentRepository.save(existingDepartment);

            // Update the organization
            Optional<OrganizationModel> organizationOptional = organizationRepository.findById(organizationId);

            if (organizationOptional.isPresent()) {
                OrganizationModel existingOrganization = organizationOptional.get();

                // Check if departments list is null, and initialize it if necessary
                if (existingOrganization.getDepartments() == null) {
                    existingOrganization.setDepartments(new ArrayList<>());
                } else {
                    // Find and replace the existing department with the updated one
                    existingOrganization.getDepartments()
                            .replaceAll(d -> d.getId().equals(updatedDepartment.getId()) ? updatedDepartment : d);
                }

                organizationRepository.save(existingOrganization);
            }
        }
    }

    public void updateEmployeeAndUpdateOrganization(String id, EmployeeModel updatedEmployee) {
        Optional<EmployeeModel> existingEmployeeOptional = employeeRepository.findById(id);

        if (existingEmployeeOptional.isPresent()) {
            EmployeeModel existingEmployee = existingEmployeeOptional.get();
            String organizationId = existingEmployee.getOrganizationId();

            // Update the employee
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPhone(updatedEmployee.getPhone());
            existingEmployee.setTelephone(updatedEmployee.getTelephone());
            existingEmployee.setAddress(updatedEmployee.getAddress());
            existingEmployee.setJobData(updatedEmployee.getJobData());
            existingEmployee.setDepartmentId(updatedEmployee.getDepartmentId());
            existingEmployee.setChannels(updatedEmployee.getChannels());
            existingEmployee.setDob(updatedEmployee.getDob());
            existingEmployee.setNic(updatedEmployee.getNic());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setPhoto(updatedEmployee.getPhoto());
            existingEmployee.setStatus(updatedEmployee.getStatus());
            existingEmployee.setMaritalStatus(updatedEmployee.getMaritalStatus());
            existingEmployee.setNationality(updatedEmployee.getNationality());
            existingEmployee.setReligion(updatedEmployee.getReligion());
            existingEmployee.setDateOfRetirement(updatedEmployee.getDateOfRetirement());
            existingEmployee.setDateOfExit(updatedEmployee.getDateOfExit());
            existingEmployee.setExitReason(updatedEmployee.getExitReason());
            existingEmployee.setDateOfContractEnd(updatedEmployee.getDateOfContractEnd());

            employeeRepository.save(existingEmployee);

            // Update the organization
            Optional<OrganizationModel> organizationOptional = organizationRepository.findById(organizationId);

            if (organizationOptional.isPresent()) {
                OrganizationModel existingOrganization = organizationOptional.get();

                // Check if employees list is null, and initialize it if necessary
                if (existingOrganization.getEmployees() == null) {
                    existingOrganization.setEmployees(new ArrayList<>());
                } else {
                    // Find and replace the existing employee with the updated one
                    existingOrganization.getEmployees()
                            .replaceAll(e -> e.getId().equals(updatedEmployee.getId()) ? updatedEmployee : e);
                }

                organizationRepository.save(existingOrganization);
            }
        }
    }

    public void updateOrganizationSingleEmployeeData(EmployeeModel updatedEmployee) {
        // Update the organization
        Optional<OrganizationModel> organizationOptional = organizationRepository.findById(updatedEmployee.getOrganizationId());

        if (organizationOptional.isPresent()) {
            OrganizationModel existingOrganization = organizationOptional.get();

            // Check if employees list is null, and initialize it if necessary
            if (existingOrganization.getEmployees() == null) {
                existingOrganization.setEmployees(new ArrayList<>());
            } else {
                // Find and replace the existing employee with the updated one
                existingOrganization.getEmployees()
                        .replaceAll(e -> e.getId().equals(updatedEmployee.getId()) ? updatedEmployee : e);
            }

            organizationRepository.save(existingOrganization);
        }
    }

    public void updateEmployeeAndUpdateOrganizationByEmail(String email, EmployeeModel updatedEmployee) {
        Optional<EmployeeModel> existingEmployeeOptional = employeeRepository.findOneByEmail(email);

        if (existingEmployeeOptional.isPresent()) {
            EmployeeModel existingEmployee = existingEmployeeOptional.get();
            String organizationId = existingEmployee.getOrganizationId();

            // Update the employee
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPhone(updatedEmployee.getPhone());
            existingEmployee.setTelephone(updatedEmployee.getTelephone());
            existingEmployee.setAddress(updatedEmployee.getAddress());
            existingEmployee.setJobData(updatedEmployee.getJobData());
            existingEmployee.setDepartmentId(updatedEmployee.getDepartmentId());
            existingEmployee.setChannels(updatedEmployee.getChannels());
            existingEmployee.setDob(updatedEmployee.getDob());
            existingEmployee.setNic(updatedEmployee.getNic());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setPhoto(updatedEmployee.getPhoto());
            existingEmployee.setStatus(updatedEmployee.getStatus());
            existingEmployee.setMaritalStatus(updatedEmployee.getMaritalStatus());
            existingEmployee.setNationality(updatedEmployee.getNationality());
            existingEmployee.setReligion(updatedEmployee.getReligion());
            existingEmployee.setDateOfRetirement(updatedEmployee.getDateOfRetirement());
            existingEmployee.setDateOfExit(updatedEmployee.getDateOfExit());
            existingEmployee.setExitReason(updatedEmployee.getExitReason());
            existingEmployee.setDateOfContractEnd(updatedEmployee.getDateOfContractEnd());

            employeeRepository.save(existingEmployee);

            // Update the organization
            Optional<OrganizationModel> organizationOptional = organizationRepository.findById(organizationId);

            if (organizationOptional.isPresent()) {
                OrganizationModel existingOrganization = organizationOptional.get();

                // Check if employees list is null, and initialize it if necessary
                if (existingOrganization.getEmployees() == null) {
                    existingOrganization.setEmployees(new ArrayList<>());
                } else {
                    // Find and replace the existing employee with the updated one
                    existingOrganization.getEmployees()
                            .replaceAll(e -> e.getEmail().equals(updatedEmployee.getEmail()) ? updatedEmployee : e);
                }
            }
        }
    }

    public void createDefaultChannels(DepartmentModel departmentModel){
        ChannelModel channelModel = new ChannelModel();
        channelModel.setDepartmentId(departmentModel.getId());
        channelModel.setName(departmentModel.getName() + "Official Channel");
        channelModel.setDescription("This is official channel for " + departmentModel.getName() + ". Please do not try to edit this channel.");
        channelModel.setPhoto("");

        channelRepository.save(channelModel);
    }

    public void deleteChannelsWhenDeleteDepartment(String departmentId){
        Optional<List<ChannelModel>> channelModel = channelRepository.findAllByDepartmentId(departmentId);

        channelModel.ifPresent(channelModels -> channelRepository.deleteAll(channelModels));
    }

    public void assignChannelsToEmployee(String employeeId) {
        Optional<EmployeeModel> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            EmployeeModel employeeModel = employee.get();

            Optional<List<ChannelModel>> channelModel = channelRepository.findAllByDepartmentId(employeeModel.getDepartmentId());

            employeeModel.setChannels(channelModel.get());

            employeeRepository.save(employeeModel);
        }
    }

    public void addMessagesToChat(MessageModel messages) {
        Optional<ChatModel> chat =  chatRepository.findById(messages.getChatId());

        if (chat.isPresent()) {
            ChatModel existingChat = chat.get();

            if (existingChat.getMessages() == null) {
                existingChat.setMessages(new ArrayList<>());
            }

            existingChat.getMessages().add(messages);

            chatRepository.save(existingChat);
        } else {
            ChatModel chatModel = new ChatModel();
            chatModel.setId(messages.getChatId());
            chatModel.setMessages(List.of(messages));
            chatRepository.save(chatModel);
        }
    }

    public void updateMessageStatus(String chatId, String messageId, String status) {
        Optional<ChatModel> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
            ChatModel existingChat = chat.get();
            Optional<MessageModel> message = existingChat.getMessages().stream().filter(m -> m.getId().equals(messageId)).findFirst();
            if (message.isPresent()) {
                message.get().setStatus(status);
                chatRepository.save(existingChat);
            }
        }
    }
}
