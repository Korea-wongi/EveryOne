package vo;

import java.sql.Date;

public class QnaVo {

   private int q_idx;			// qna 인덱스
    private String q_title;		// qna 제목
    private String q_content;	// qna 내용
    private int q_group;		// qna 주글
    private int q_level;		// qna 들여쓰기??
    private Date q_date;		// qna 날짜
    private int q_cnt;          // qna 조회수
    private String q_sfile;		// qna 파일
    private String q_mid;		// 멤버 아이디
    private int q_bidx;			// 구매 인덱스
    
    
    public QnaVo() {}


   public QnaVo(int q_idx, String q_title, String q_content, int q_group, int q_level, Date q_date, int q_cnt,
         String q_sfile, String q_mid, int q_bidx) {
      
      this.q_idx = q_idx;
      this.q_title = q_title;
      this.q_content = q_content;
      this.q_group = q_group;
      this.q_level = q_level;
      this.q_date = q_date;
      this.q_cnt = q_cnt;
      this.q_sfile = q_sfile;
      this.q_mid = q_mid;
      this.q_bidx = q_bidx;
   }


   public QnaVo(int q_idx, String q_title, String q_content, int q_group, int q_level, int q_cnt, String q_sfile,
         String q_mid, int q_bidx) {
      
      this.q_idx = q_idx;
      this.q_title = q_title;
      this.q_content = q_content;
      this.q_group = q_group;
      this.q_level = q_level;
      this.q_cnt = q_cnt;
      this.q_sfile = q_sfile;
      this.q_mid = q_mid;
      this.q_bidx = q_bidx;
   }


   public int getQ_idx() {
      return q_idx;
   }


   public void setQ_idx(int q_idx) {
      this.q_idx = q_idx;
   }


   public String getQ_title() {
      return q_title;
   }


   public void setQ_title(String q_title) {
      this.q_title = q_title;
   }


   public String getQ_content() {
      return q_content;
   }


   public void setQ_content(String q_content) {
      this.q_content = q_content;
   }


   public int getQ_group() {
      return q_group;
   }


   public void setQ_group(int q_group) {
      this.q_group = q_group;
   }


   public int getQ_level() {
      return q_level;
   }


   public void setQ_level(int q_level) {
      this.q_level = q_level;
   }


   public Date getQ_date() {
      return q_date;
   }


   public void setQ_date(Date q_date) {
      this.q_date = q_date;
   }


   public int getQ_cnt() {
      return q_cnt;
   }


   public void setQ_cnt(int q_cnt) {
      this.q_cnt = q_cnt;
   }


   public String getQ_sfile() {
      return q_sfile;
   }


   public void setQ_sfile(String q_sfile) {
      this.q_sfile = q_sfile;
   }


   public String getQ_mid() {
      return q_mid;
   }


   public void setQ_mid(String q_mid) {
      this.q_mid = q_mid;
   }


   public int getQ_bidx() {
      return q_bidx;
   }


   public void setQ_bidx(int q_bidx) {
      this.q_bidx = q_bidx;
   };
    
   
   
}