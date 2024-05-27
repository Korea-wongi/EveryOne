package vo;

import java.sql.Date;

public class NewsVo {
   
   private int n_idx;			// 공지 인덱스
   private String n_title;		// 공지 제목
   private String n_content;	// 공지 내용
   private Date n_date;			// 공지 날짜
   private int n_cnt;			// 공지 조회수
   private String n_sfile;		// 공지 파일
   private String n_mid;		// 멤버 아이디
   
   //기본생성자
   public NewsVo() {  }

   //전체 기본생성자
   public NewsVo(int n_idx, String n_title, String n_content, Date n_date, int n_cnt, String n_sfile, String n_mid) {
      
      this.n_idx = n_idx;
      this.n_title = n_title;
      this.n_content = n_content;
      this.n_date = n_date;
      this.n_cnt = n_cnt;
      this.n_sfile = n_sfile;
      this.n_mid = n_mid;
   }

   
   public int getN_idx() {
      return n_idx;
   }

   public void setN_idx(int n_idx) {
      this.n_idx = n_idx;
   }

   public String getN_title() {
      return n_title;
   }

   public void setN_title(String n_title) {
      this.n_title = n_title;
   }

   public String getN_content() {
      return n_content;
   }

   public void setN_content(String n_content) {
      this.n_content = n_content;
   }

   public Date getN_date() {
      return n_date;
   }

   public void setN_date(Date n_date) {
      this.n_date = n_date;
   }

   public int getN_cnt() {
      return n_cnt;
   }

   public void setN_cnt(int n_cnt) {
      this.n_cnt = n_cnt;
   }

   public String getN_sfile() {
      return n_sfile;
   }

   public void setN_sfile(String n_sfile) {
      this.n_sfile = n_sfile;
   }

   public String getN_mid() {
      return n_mid;
   }

   public void setN_mid(String n_mid) {
      this.n_mid = n_mid;
   }
}