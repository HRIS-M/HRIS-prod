package com.hris.HRIS.service;

import com.hris.HRIS.model.ApplyJobModel;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    //Send meeting details through email
    public void sendMeetingDetails(ApplyJobModel applyJob, String userEmail, String managerEmail, boolean notify) {
        Properties properties = System.getProperties();
        //properties.setProperty("mail.smtp.host", SMTP_HOST);

        Session session = Session.getDefaultInstance(properties);

        try {
            //For Hiring manager
            if (notify) {
                sendEmail(session, managerEmail, "Meeting Notification for Manager",
                        "Meeting Details:\n" +
                                "Date: " + applyJob.getMeeting_date() + "\n" +
                                "Time: " + applyJob.getMeeting_time() + "\n" +
                                "Link: " + applyJob.getMeeting_link());
            }

            //For applicant
            sendEmail(session, userEmail, "Meeting Details",
                    "Dear " + applyJob.getName() + ",\n\n" +
                            "Your meeting is scheduled on " + applyJob.getMeeting_date() + " at " +
                            applyJob.getMeeting_time() + ".\nMeeting Link: " + applyJob.getMeeting_link());

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private void sendEmail(Session session, String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        //message.setFrom(new InternetAddress(FROM_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    public void sendHireNotification(String to, String candidateName) {

        String subject = "Congratulations, You're Hired!";
        String body = "Dear " + candidateName + ",\n\nCongratulations! You have been hired by our company.\n\nBest regards,\nHR Team";
        sendSimpleEmail(to, subject, body);
    }

    public void sendRejectionNotification(String to, String candidateName) {
        String subject = "Application Status";
        String body = "Dear " + candidateName + ",\n\nWe appreciate your interest in our company. Unfortunately, we have decided to move forward with other candidates at this time. We wish you the best of luck in your future endeavors.\n\nBest regards,\nHR Team";
        sendSimpleEmail(to, subject, body);
    }
}

