package vo;

import java.sql.Date;

// 구매내역 저장 후 사용하는 VO역할 클래스
public class BuyVo {
   // 변수선언
    private int b_idx; 		//구매 인덱스
    private String b_oidx; 	//주문 번호
    private String b_name;  //상품 이름
    private int b_price; 	//상품 가격
    private String b_pmid;   //판매자 명
    private Date b_date;    //구매 일자
    // 기본생성자
    public BuyVo() {
       
   }

    // 전체 생성자
    public BuyVo(int b_idx, String b_oidx, String b_name, int b_price, String b_pmid) {
		this.b_idx = b_idx;
		this.b_oidx = b_oidx;
		this.b_name = b_name;
		this.b_price = b_price;
		this.b_pmid = b_pmid;
	}
    
    
   
   
   public BuyVo(int b_idx, String b_oidx, String b_name, int b_price) {
		super();
		this.b_idx = b_idx;
		this.b_oidx = b_oidx;
		this.b_name = b_name;
		this.b_price = b_price;
	}

// getter, setter 메소드
   public int getB_idx() {
      return b_idx;
   }
   

   public Date getB_date() {
	return b_date;
}

public void setB_date(Date b_date) {
	this.b_date = b_date;
}

public String getB_pmid() {
	return b_pmid;
   }

   public void setB_pmid(String b_pmid) {
	this.b_pmid = b_pmid;
   }

   public void setB_idx(int b_idx) {
      this.b_idx = b_idx;
   }

   public String getB_oidx() {
      return b_oidx;
   }

   public void setB_oidx(String b_oidx) {
      this.b_oidx = b_oidx;
   }

   public String getB_name() {
      return b_name;
   }

   public void setB_name(String b_name) {
      this.b_name = b_name;
   }


   public int getB_price() {
      return b_price;
   }

   public void setB_price(int b_price) {
      this.b_price = b_price;
   }

}