package mail;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public final class sendMail {
	
	// 발신자의 SMTP 패스워드
	public String sendEmail(String to) throws Exception {

		String authenCode1 = UUID.randomUUID().toString();
		//String authenCode = null;
		final String host = "smtp 서버명";		  // SMTP 서버명
		final String user = "이메일";        	  // 발신자의 이메일 계정
		final String password =  "2차패스워드"; 
		
		/* Property 객체에 SMTP 서버 정보 설정 */
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.trust", host);
        props.setProperty("mail.smtp.socketFactory.port", "465"); //SSL 소켓팩토리로 안전한 소켓 생성 시 사용할 포트번호
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL 소켓팩토리의 클래스 설정
        props.setProperty("mail.smtp.socketFactory.fallback", "false"); //소켓 팩토리 실패할 경우 다른 소켓 팩토리 사용여부 설정
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		
		/* SMTP 서버 정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스를 생성*/
		Session session = Session.getDefaultInstance(props, new Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(user, password);
		    }
		});
		
		/* Message 객체에 수신자와 내용, 제목의 메시지를 작성 */
		try {
            // 인증번호 생성 //임시비밀번호 생성하는 메서드
            //authenCode = makeAuthenticationCode();
			
			MimeMessage message = new MimeMessage(session);
		    
		    // 발신자 설정
		    message.setFrom(new InternetAddress(user, "SHOP"));

		    // 수신자 메일주소 설정
		    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

		    // 메일제목 설정
		    message.setSubject("SHOP :: 임시 비밀번호 메일입니다.");

		    // 메일 내용 설정
		    message.setText("비밀번호 변경 인증번호는 [ "+ authenCode1 + " ] 입니다.");

		    // Send the message
		    Transport.send(message);

		    System.out.println(" MailSend : Email sent successfully.");
		} catch (MessagingException e) {
		    e.printStackTrace();
		}
		
		System.out.println(" MailSend : sendEmail() 종료");
		//return authenCode;
		return authenCode1;
	}
	
	
}
