package com.hris.HRIS.service;

import com.hris.HRIS.ThymeleafConfig;
import com.hris.HRIS.model.OnboardingPlanModel;
import com.hris.HRIS.model.PromotionModel;
import com.hris.HRIS.model.TransferModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class LettersGenerationService {

    @Autowired
    TemplateEngine templateEngine;

    public String generateReceivedTransferLetter(TransferModel transferModel) {
        // Create a Thymeleaf context and add variables to be used in the template
        Context context = new Context();
        context.setVariable("name", transferModel.getName());
        context.setVariable("email", transferModel.getEmail());
        context.setVariable("phone", transferModel.getPhone());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("received-letter-template", context);
    }

    public String generateApprovedTransferLetter(TransferModel transferModel) {
        Context context = new Context();
        context.setVariable("name", transferModel.getName());
        context.setVariable("email", transferModel.getEmail());
        context.setVariable("phone", transferModel.getPhone());
        context.setVariable("jobData", transferModel.getJobData());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("approved-transfer-letter-template", context);
    }

    public String generateRejectedTransferLetter(TransferModel transferModel) {
        Context context = new Context();
        context.setVariable("name", transferModel.getName());
        context.setVariable("email", transferModel.getEmail());
        context.setVariable("phone", transferModel.getPhone());
        context.setVariable("jobData", transferModel.getJobData());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("rejected-transfer-letter-template", context);
    }

    public String generateReceivedPromotionLetter(PromotionModel promotionModel) {
        Context context = new Context();
        context.setVariable("name", promotionModel.getName());
        context.setVariable("email", promotionModel.getEmail());
        context.setVariable("phone", promotionModel.getPhone());
        context.setVariable("jobData", promotionModel.getJobData());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("received-promotion-letter-template", context);
    }

    public String generateApprovedPromotionLetter(PromotionModel promotionModel) {
        Context context = new Context();
        context.setVariable("name", promotionModel.getName());
        context.setVariable("email", promotionModel.getEmail());
        context.setVariable("phone", promotionModel.getPhone());
        context.setVariable("jobData", promotionModel.getJobData());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("approved-promotion-letter-template", context);
    }

    public String generateRejectedPromotionLetter(PromotionModel promotionModel) {
        Context context = new Context();
        context.setVariable("name", promotionModel.getName());
        context.setVariable("email", promotionModel.getEmail());
        context.setVariable("phone", promotionModel.getPhone());
        context.setVariable("jobData", promotionModel.getJobData());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("rejected-promotion-letter-template", context);
    }

    public String generateOnboardingPlan(OnboardingPlanModel onboardingPlanModel) {
        Context context = new Context();
        context.setVariable("id", onboardingPlanModel.getId());
        context.setVariable("description", onboardingPlanModel.getDescription());
        context.setVariable("taskDate", onboardingPlanModel.getTaskDate());

        // Process the Thymeleaf template and return the HTML content as a string
        return templateEngine.process("onboarding-plan-template", context);
    }

}
