package vo;

import java.sql.Date;

public class PayVo {

   private String p_idx;	// 결제 인덱스
   private String p_mid;	// 멤버 아이디
   private int p_price; 	// 상품 가격
   private Date p_date;		// 결제 날짜
   
   public PayVo() {}
   
   public PayVo(String p_idx, String p_mid, int p_price) {
      super();
      this.p_idx = p_idx;
      this.p_mid = p_mid;
      this.p_price = p_price;
   }

   public String getP_idx() {
      return p_idx;
   }

   public void setP_idx(String p_idx) {
      this.p_idx = p_idx;
   }

   public String getP_mid() {
      return p_mid;
   }

   public void setP_mid(String p_mid) {
      this.p_mid = p_mid;
   }

   public int getP_price() {
      return p_price;
   }

   public void setP_price(int p_price) {
      this.p_price = p_price;
   }

   public Date getP_date() {
      return p_date;
   }

   public void setP_date(Date p_date) {
      this.p_date = p_date;
   }
   
   
   
}