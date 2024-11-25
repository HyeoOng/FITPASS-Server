package com.ssafy.fitpass.auth;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender; // JavaMailSender은 메일 전송을 위한 스프링의 인터페이스

    // 생성자 주입을 통해 JavaMailSender 객체를 초기화
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 회원 가입 시 사용자가 제공한 이메일로 인증 코드를 전송하는 메서드입니다.
     *
     * @param toEmail (사용자 입력 이메일)
     * @param verificationCode (인증 코드)
     */
    public void sendVerificationCode(String toEmail, String verificationCode) {
        // SimpleMailMessage는 간단한 텍스트 형식의 이메일을 전송할 때 사용
        SimpleMailMessage message = new SimpleMailMessage();
        // 수신자 이메일 주소 설정
        message.setTo(toEmail);
        // 이메일 제목 설정
        message.setSubject("FITPASS 가입 인증 코드");
        // 이메일 본문 텍스트 설정
        message.setText("안녕하세요!\n\n회원가입을 위한 인증 코드는 다음과 같습니다:\n\n"
                + verificationCode + "\n\n감사합니다.");
        try {
            mailSender.send(message);
            System.out.println("이메일 전송 성공: " + toEmail);
        } catch (Exception e) {
            System.err.println("이메일 전송 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

