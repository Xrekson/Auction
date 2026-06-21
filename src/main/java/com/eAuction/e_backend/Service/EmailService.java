package com.eAuction.e_backend.Service;

public interface EmailService {
    void sendOtpEmail(String toEmail, String name, String otp) throws Exception;
}
