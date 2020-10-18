package edu.mum.cs.onlinemarketplace.email;

public interface EmailService {
    void sendSimpleMessage(String to,String subject,String text);
}
