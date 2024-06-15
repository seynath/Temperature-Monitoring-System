package com.sitproject.sit.service;

import kong.unirest.HttpResponse;
import kong.unirest.MultipartBody;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Value("${mailgun.api.key}")
    private String MAILGUN_API_KEY;

    @Value("${mailgun.domain}")
    private String MAILGUN_DOMAIN;

    public void sendEmail(List<String> to, String subject, String body) {
        for (String recipient : to) {
            HttpResponse<String> request = null;

            try {
                // Logging the API key and domain for verification
                System.out.println("MAILGUN_API_KEY: " + MAILGUN_API_KEY);
                System.out.println("MAILGUN_DOMAIN: " + MAILGUN_DOMAIN);

                MultipartBody requestBody = Unirest.post("https://api.mailgun.net/v3/" + MAILGUN_DOMAIN + "/messages")
                        .basicAuth("api", MAILGUN_API_KEY)
                        .field("from", "Your Name <noreply@" + MAILGUN_DOMAIN + ">")
                        .field("to", recipient)
                        .field("subject", subject)
                        .field("text", body);

                request = requestBody.asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }

            if (request != null) {
                System.out.println("Response status code: " + request.getStatus());
                System.out.println("Response body: " + request.getBody());
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
