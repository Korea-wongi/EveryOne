package vo;

import java.sql.Date;

public class ReviewVo {
   private int r_idx;         	// 리뷰 인덱스
   private String r_title;      // 리뷰 제목
   private String r_content;    // 리뷰 내용
   private Date r_date;      	// 리뷰 날짜
   private String r_sfile;      // 리뷰 파일
   private String r_mid;      	// 멤버 아이디
   private int r_pidx;          // 상품 인덱스
   
   public ReviewVo() {
      
   }
   
   //실제사용
   public ReviewVo(int r_idx, String r_title, String r_content, Date r_date, String r_sfile, String r_mid,
		int r_pidx) {
	this.r_idx = r_idx;
	this.r_title = r_title;
	this.r_content = r_content;
	this.r_date = r_date;
	this.r_sfile = r_sfile;
	this.r_mid = r_mid;
	this.r_pidx = r_pidx;
}

public int getR_idx() {
      return r_idx;
   }

   public void setR_idx(int r_idx) {
      this.r_idx = r_idx;
   }

   public String getR_title() {
      return r_title;
   }

   public void setR_title(String r_title) {
      this.r_title = r_title;
   }

   public String getR_content() {
      return r_content;
   }

   public void setR_content(String r_content) {
      this.r_content = r_content;
   }

   public Date getR_date() {
      return r_date;
   }

   public void setR_date(Date r_date) {
      this.r_date = r_date;
   }

   public String getR_sfile() {
      return r_sfile;
   }

   public void setR_sfile(String r_sfile) {
      this.r_sfile = r_sfile;
   }

   public String getR_mid() {
      return r_mid;
   }

   public void setR_mid(String r_mid) {
      this.r_mid = r_mid;
   }

   public int getR_pidx() {
      return r_pidx;
   }

   public void setR_pidx(int r_pidx) {
      this.r_pidx = r_pidx;
   }
}
