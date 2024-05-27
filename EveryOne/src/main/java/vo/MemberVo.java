package vo;

import java.sql.Date;

public class MemberVo {
   
   private String m_id;				// 멤버 아이디
   private String m_pass;			// 멤버 비밀번호
   private String m_name;			// 멤버 이름
   private String m_email;			// 멤버 이메일
   private String m_hp;   			// 멤버 전화번호
   private Date m_birth;			// 멤버 생년월일
   private String m_gender; 		// 멤버 성별
   private String m_address1;		// 멤버 필수주소
   private Date m_regdate; 			// 멤버 가입일자
   private String m_admin;			// 멤버 관리,회원
   
   public MemberVo() {  }


   public MemberVo(String m_id, String m_pass, String m_name, String m_email, String m_hp, Date m_birth, String m_gender,
		String m_address1, Date m_regdate, String m_admin) {
	this.m_id = m_id;
	this.m_pass = m_pass;
	this.m_name = m_name;
	this.m_email = m_email;
	this.m_hp = m_hp;
	this.m_birth = m_birth;
	this.m_gender = m_gender;
	this.m_address1 = m_address1;
	this.m_regdate = m_regdate;
	this.m_admin = m_admin;
   }


   public String getM_id() {
      return m_id;
   }


   public void setM_id(String m_id) {
      this.m_id = m_id;
   }


   public String getM_pass() {
      return m_pass;
   }


   public void setM_pass(String m_pass) {
      this.m_pass = m_pass;
   }


   public String getM_name() {
      return m_name;
   }


   public void setM_name(String m_name) {
      this.m_name = m_name;
   }


   public String getM_email() {
      return m_email;
   }


   public void setM_email(String m_email) {
      this.m_email = m_email;
   }


   public Date getM_birth() {
      return m_birth;
   }


   public void setM_birth(Date m_birth) {
      this.m_birth = m_birth;
   }


   public String getM_gender() {
      return m_gender;
   }


   public void setM_gender(String m_gender) {
      this.m_gender = m_gender;
   }


   public String getM_address1() {
      return m_address1;
   }


   public void setM_address1(String m_address1) {
      this.m_address1 = m_address1;
   }

   public Date getM_regdate() {
      return m_regdate;
   }


   public void setM_regdate(Date m_regdate) {
      this.m_regdate = m_regdate;
   }


   public String getM_admin() {
      return m_admin;
   }


   public void setM_admin(String m_admin) {
      this.m_admin = m_admin;
   }


   public String getM_hp() {
      return m_hp;
   }


   public void setM_hp(String m_hp) {
      this.m_hp = m_hp;
   }

}