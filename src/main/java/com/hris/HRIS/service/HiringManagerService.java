package com.hris.HRIS.service;

import com.hris.HRIS.model.ApplyJobModel;
import com.hris.HRIS.model.HiringManagerModel;
import com.hris.HRIS.repository.ApplyJobRepository;
import com.hris.HRIS.repository.HiringManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class HiringManagerService {

    @Autowired
    private ApplyJobRepository applyJobRepository;

    @Autowired
    private HiringManagerRepository hiringManagerRepository;

    public void saveDetails(String hiringManagerName, float technicalScore, float generalScore, float totalScore, float satisfactionScore, String meetingStartTime, String meetingEndTime, float costPerHire) {
        List<ApplyJobModel> applicants = applyJobRepository.findAll();

        for (ApplyJobModel applicant : applicants) {
            HiringManagerModel hiringManager = new HiringManagerModel();
            hiringManager.setApplicant_name(applicant.getName());
            hiringManager.setApplicant_email(applicant.getEmail());
            hiringManager.setHiring_manager_name(hiringManagerName);
            hiringManager.setHiring_manager_email(applicant.getManager_email());
            hiringManager.setDate(applicant.getMeeting_date()); // Setting meeting date from ApplyJobModel
            hiringManager.setMeeting_start_time(meetingStartTime);
            hiringManager.setMeeting_end_time(meetingEndTime);
            hiringManager.setTechnical_score(technicalScore);
            hiringManager.setGeneral_score(generalScore);
            hiringManager.setTotal_score(totalScore);
            hiringManager.setSatisfaction_score(satisfactionScore);
            hiringManager.setCost_per_hire(costPerHire);
            hiringManager.setLink_valid(false); // Invalidating the link

            hiringManagerRepository.save(hiringManager);
        }
    }

    public void updateHiringManagerDetails(String hiringManagerId, String hiringManagerName, float technicalScore, float generalScore, float totalScore, float satisfactionScore, String meetingStartTime, String meetingEndTime, float costPerHire) {
        Optional<HiringManagerModel> optionalHiringManager = hiringManagerRepository.findById(hiringManagerId);

        if (optionalHiringManager.isPresent()) {
            HiringManagerModel hiringManager = optionalHiringManager.get();

            // Update fields with provided values
            hiringManager.setHiring_manager_name(hiringManagerName);
            hiringManager.setTechnical_score(technicalScore);
            hiringManager.setGeneral_score(generalScore);
            hiringManager.setTotal_score(totalScore);
            hiringManager.setSatisfaction_score(satisfactionScore);
            hiringManager.setMeeting_start_time(meetingStartTime);
            hiringManager.setMeeting_end_time(meetingEndTime);
            hiringManager.setCost_per_hire(costPerHire);

            // Set link_valid to true after updating fields
            hiringManager.setLink_valid(true);

            // Save the updated HiringManagerModel
            hiringManagerRepository.save(hiringManager);
        } else {
            // Handle case where hiring manager with given ID is not found
            throw new IllegalArgumentException("Hiring manager not found with ID: " + hiringManagerId);
        }
    }

    //calculate average cost per hire
    public double calculateAverageCostPerHire() {
        List<HiringManagerModel> hiringManagers = hiringManagerRepository.findAll();

        if (hiringManagers.isEmpty()) {
            return 0;
        }

        double totalCost = 0;
        for (HiringManagerModel manager : hiringManagers) {
            totalCost += manager.getCost_per_hire();
        }

        return totalCost / hiringManagers.size();
    }


    //calculate average time to recruit
    public double calculateAverageTimeToRecruit() {
        List<HiringManagerModel> hiringManagers = hiringManagerRepository.findAll();

        if (hiringManagers.isEmpty()) {
            return 0;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        double totalTime = 0;

        for (HiringManagerModel manager : hiringManagers) {
            LocalDateTime startTime = LocalDateTime.parse(manager.getDate() + " " + manager.getMeeting_start_time(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(manager.getDate() + " " + manager.getMeeting_end_time(), formatter);
            totalTime += Duration.between(startTime, endTime).toMinutes();
        }

        return totalTime / hiringManagers.size();
    }


    //calculate average satisfaction score
    public double calculateAverageSatisfactionScore() {
        List<HiringManagerModel> hiringManagers = hiringManagerRepository.findAll();

        if (hiringManagers.isEmpty()) {
            return 0;
        }

        double totalSatisfactionScore = 0;
        for (HiringManagerModel manager : hiringManagers) {
            totalSatisfactionScore += manager.getSatisfaction_score();
        }

        return totalSatisfactionScore / hiringManagers.size();
    }

}
