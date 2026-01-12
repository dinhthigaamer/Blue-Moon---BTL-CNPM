package com.example.project.integration.mail;

import com.example.project.resident.entity.Resident;
import com.example.project.resident.repository.ResidentRepository;
import com.example.project.household.entity.Household;
import com.example.project.FeePayment.entity.FeePayment;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ReceiptService {
    private final ResidentRepository residentRepository;
    private JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.mail.fromName:Support}")
    private String fromName;

    public ReceiptService(ResidentRepository residentRepository, JavaMailSender mailSender) {
        this.residentRepository = residentRepository;
        this.mailSender = mailSender;
    }

    public void sendReceipt(Household household, Integer month, Integer year, List<FeePayment> payments) {
        // Kiểm tra chủ hộ với số cccd có tồn tại không
        Resident resident = residentRepository.findByCccd(household.getOwnerCccd())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Chủ hộ không tìm thấy"));

        // Kiểm tra email có tồn tại không
        String email = resident.getEmail();
        if (email == null || email.trim().isEmpty()) {
            throw new ApiException(ErrorCode.NOT_FOUND, "Email của chủ hộ không tồn tại");
        }

        String to = email;
        String subject = "Biên lai phí tháng " + month + "/" + year;

        // Tạo nội dung mail
        StringBuilder sb = new StringBuilder();
        sb.append("<h3>Biên lai phí tháng ").append(month).append("/").append(year).append("</h3>");
        sb.append("<table border='1' cellpadding='5' cellspacing='0'>");
        sb.append("<tr><th>Loại phí</th><th>Lượng sử dụng</th><th>Đơn giá</th><th>Chú thích</th><th>Số tiền (VND)</th></tr>");

        BigDecimal total = BigDecimal.ZERO;
        for (FeePayment fp : payments) {
            sb.append("<tr>")
                    .append("<td>").append(fp.getName()).append("</td>")
                    .append("<td>").append(fp.getUsageAmount()).append("</td>")
                    .append("<td>").append(fp.getFee().getPricePerUnit()).append("</td>")
                    .append("<td>").append(fp.getFee().getNote()).append("</td>")
                    .append("<td>").append(fp.getAmount()).append("</td>")
                    .append("</tr>");
            if (fp.getAmount() == null) {
                throw new ApiException(ErrorCode.AMOUNT_NULL_ERROR,
                        "Khoản thu có id " + fp.getId() + " đang rỗng trường Amount. Vui lòng thêm tổng tiền vào ");
            }
            total = total.add(fp.getAmount());
        }

        sb.append("<tr>")
                .append("<td colspan='4'><strong>Tổng cộng</strong></td>")
                .append("<td><strong>").append(total).append("</strong></td>")
                .append("</tr>");
        sb.append("</table>");

        // Gửi email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(sb.toString(), true); // true = HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new ApiException(ErrorCode.MAIL_FORMAT_ERROR, "Lỗi định dạng email");
        } catch (Exception e) {
            throw new ApiException(ErrorCode.MAIL_SEND_FAILED, "Gửi email thất bại");
        }
    }
}
