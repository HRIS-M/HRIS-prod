package com.hris.HRIS.service;

import com.hris.HRIS.controller.CourseAssignmentController;
import com.hris.HRIS.controller.CourseLearningMaterialController;
import com.hris.HRIS.controller.CourseModuleController;
import com.hris.HRIS.controller.QuizController;
import com.hris.HRIS.model.*;
import com.hris.HRIS.repository.CourseLearningMaterialRepository;
import com.hris.HRIS.repository.CourseModuleRepository;
import com.hris.HRIS.repository.CourseRepository;
import com.hris.HRIS.repository.EmployeeCourseProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LMSAutomateService {

    @Autowired
    CourseModuleRepository courseModuleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseModuleController courseModuleController;

    @Autowired
    CourseLearningMaterialController courseLearningMaterialController;

    @Autowired
    CourseAssignmentController courseAssignmentController;

    @Autowired
    QuizController quizController;

    @Autowired
    EmployeeCourseProgressRepository employeeCourseProgressRepository;

    @Async
    @Scheduled(fixedRate = 300000) // execute in every 300000 milliseconds = 5 minutes
    public void indexCourceModules(){
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")) +
                " Process: LMSAutomateService - index course modules and items - working...");

        for(CourseModel courseModel : courseRepository.findAll()){

            List<String> courseModulesIndexes = new ArrayList<>();

            for(CourseModuleModal courseModuleModal : courseModuleController.getAllCourseModulesByCourseId(courseModel.getId())){
                if(courseModuleModal.getStatus().equals("Available")){
                    courseModulesIndexes.add(courseModuleModal.getId());
                    indexModuleItems(courseModuleModal);
                }
            }

            courseModel.setCourseModulesIndexes(courseModulesIndexes);
            courseRepository.save(courseModel);
        }
    }

    @Async
    public void indexModuleItems(CourseModuleModal courseModuleModal){
        List<String> moduleItemsIndexes = new ArrayList<>();

        // Look for non-assignments related module items(files, presentations, videos, etc...)
        for(CourseLearningMaterialModal courseLearningMaterialModal : courseLearningMaterialController.getAllLearningMaterialsByModuleId(courseModuleModal.getId())){
            if((courseLearningMaterialModal.getStatus().equals("Available") || courseLearningMaterialModal.getStatus().equals("Published"))){
                if(courseLearningMaterialModal.getAssignmentId() != null){
                    if(courseLearningMaterialModal.getAssignmentId().equals("")){
                        moduleItemsIndexes.add(courseLearningMaterialModal.getId());
                    }
                }else{
                    moduleItemsIndexes.add(courseLearningMaterialModal.getId());
                }
            }
        }

        // Look for module related assignments.
        for(CourseAssignmentModel courseAssignmentModel : courseAssignmentController.getAllAssignmentsByModuleId(courseModuleModal.getId())){
            if(courseAssignmentModel.getStatus().equals("Available") || courseAssignmentModel.getStatus().equals("Published")){
                moduleItemsIndexes.add(courseAssignmentModel.getId());
            }
        }

        // Look for module related quizes.
        for(QuizModel quizModel : quizController.getAllQuizesByModuleId(courseModuleModal.getId())){
            if(quizModel.getStatus().equals("Available") || quizModel.getStatus().equals("Published")){
                moduleItemsIndexes.add(quizModel.getId());
            }
        }

        courseModuleModal.setModuleItemsIndexes(moduleItemsIndexes);
        courseModuleRepository.save(courseModuleModal);
    }

    public void calculateEmployeeCourseCompletionProgress(String employeeEmail, String courseId){
        List<EmployeeCourseProgressModel> employeeCourseProgressModels = employeeCourseProgressRepository.findAllByEmployeeEmail(employeeEmail);

        Optional<EmployeeCourseProgressModel> existingEmployeeCourseProgressModel = employeeCourseProgressModels.stream()
                .filter(model -> courseId.equals(model.getCourseId()))
                .findFirst();

        if(existingEmployeeCourseProgressModel.isPresent()){
            Optional<CourseModel> existingCourseModel = courseRepository.findById(courseId);
            List<String> courseModuleItems = new ArrayList<>();
            List<String> employeeCompletedModuleItems = existingEmployeeCourseProgressModel.get().getCompletedCourseModulesItems();

            if(existingCourseModel.isPresent()){
                for(String courseModuleIndex : existingCourseModel.get().getCourseModulesIndexes()){
                    Optional<CourseModuleModal> existingCourseModule = courseModuleRepository.findById(courseModuleIndex);
                    existingCourseModule.ifPresent(courseModuleModal -> courseModuleItems.addAll(courseModuleModal.getModuleItemsIndexes()));
                }

                employeeCompletedModuleItems.retainAll(courseModuleItems);

                if(employeeCompletedModuleItems.size() > 0){
                    if(employeeCompletedModuleItems.size() >= courseModuleItems.size()){
                        existingEmployeeCourseProgressModel.get().setCompletedCourseProgress(100.0);
                    }else{
                        existingEmployeeCourseProgressModel.get().setCompletedCourseProgress(
                                ((double) employeeCompletedModuleItems.size()/courseModuleItems.size())*100.0
                        );
                    }

                    employeeCourseProgressRepository.save(existingEmployeeCourseProgressModel.get());
                }
            }
        }
    }
}
