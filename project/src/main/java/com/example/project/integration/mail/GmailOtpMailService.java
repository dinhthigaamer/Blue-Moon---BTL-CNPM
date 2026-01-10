package com.example.project.integration.mail;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class GmailOtpMailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.mail.fromName:Support}")
    private String fromName;

    public GmailOtpMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp, int expireMinutes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(from, fromName);
            helper.setTo(toEmail);
            helper.setSubject("[BlueMoon] Password reset OTP");

            String html = buildOtpHtml(otp, expireMinutes);
            helper.setText(html, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new ApiException(ErrorCode.MAIL_FORMAT_ERROR, "Lỗi định dạng email");
        } catch (MailException e) {
            throw new ApiException(ErrorCode.MAIL_SEND_FAILED, "Gửi email thất bại");
        } catch (Exception e) {
            throw new ApiException(ErrorCode.MAIL_SEND_FAILED, "Gửi email thất bại");
        }
    }

    private String buildOtpHtml(String otp, int expireMinutes) {
        return ""
                + "<div style='font-family:Arial,sans-serif;max-width:520px'>"
                + "  <h2 style='margin:0 0 12px'>Reset your password</h2>"
                + "  <p style='margin:0 0 12px'>Use this OTP to reset your password:</p>"
                + "  <div style='font-size:28px;font-weight:bold;letter-spacing:4px;"
                + "              padding:12px 16px;border:1px solid #ddd;display:inline-block'>"
                +        otp
                + "  </div>"
                + "  <p style='margin:12px 0 0'>This OTP expires in <b>" + expireMinutes + " minutes</b>.</p>"
                + "  <p style='margin:12px 0 0;color:#777'>If you didn't request this, please ignore this email.</p>"
                + "</div>";
    }
}
