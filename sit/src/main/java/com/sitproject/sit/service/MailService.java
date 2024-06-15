package com.sit.sit_server.service;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.MultipartBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    // Injecting Mailgun API key from application properties
    @Value("${mailgun.api.key}")
    private String MAILGUN_API_KEY;

    // Injecting Mailgun domain from application properties
    @Value("${mailgun.domain}")
    private String MAILGUN_DOMAIN;

    // Method to send email
    public void sendEmail(List<String> to, String subject, String body) {
        // Looping through each recipient
        for (String recipient : to) {
            HttpResponse<String> request = null;

            try {
                // Building the request body with necessary parameters
                MultipartBody requestBody = Unirest.post("https://api.mailgun.net/v3/" + MAILGUN_DOMAIN + "/messages")
                        .basicAuth("api", MAILGUN_API_KEY)
                        .field("from", "Your Name <noreply@" + MAILGUN_DOMAIN + ">")
                        .field("to", recipient)
                        .field("subject", subject)
                        .field("text", body);

                // Sending the request and storing the response
                request = requestBody.asString();
            } catch (UnirestException e) {
                // Printing the stack trace if an exception occurs
                e.printStackTrace();
            }

            // Checking if the request object is not null
            if (request != null) {
                // Printing the response status code
                System.out.println("Response status code: " + request.getStatus());
                // Checking if the email was sent successfully
                if (request.getStatus() == 200) {
                    System.out.println("Email sent successfully to " + recipient);
                } else {
                    System.out.println("Failed to send email to " + recipient);
                }
            } else {
                System.out.println("Request object is null");
            }
        }
    }
}

