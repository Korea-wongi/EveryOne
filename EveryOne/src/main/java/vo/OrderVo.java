package vo;

public class OrderVo {

   private String o_idx;			// 주문 인덱스
   private String o_mid;		// 멤버 아이디
   private String o_name;		// 배송 수령자
   private String o_address1;	// 배송 필수주소
   private String o_hp;			// 배송 수령인 전화번호
   private String o_request;	// 배송 요청사항
   
   /* 변수만 추가  */
   private int o_price;
   
   //기본생성자
   public OrderVo() {  }

   public OrderVo(String o_idx, String o_mid, String o_name, String o_address1, String o_hp, String o_request) {
	super();
	this.o_idx = o_idx;
	this.o_mid = o_mid;
	this.o_name = o_name;
	this.o_address1 = o_address1;
	this.o_hp = o_hp;
	this.o_request = o_request;
   }

   

   public int getO_price() {
	return o_price;
	}
	
	public void setO_price(int o_price) {
		this.o_price = o_price;
	}

	public String getO_idx() {
	return o_idx;
   }

   public void setO_idx(String o_idx) {
	this.o_idx = o_idx;
   }

   public String getO_mid() {
      return o_mid;
   }

   public void setO_mid(String o_mid) {
      this.o_mid = o_mid;
   }

   public String getO_name() {
      return o_name;
   }

   public void setO_name(String o_name) {
      this.o_name = o_name;
   }

   public String getO_address1() {
      return o_address1;
   }

   public void setO_address1(String o_address1) {
      this.o_address1 = o_address1;
   }

   public String getO_hp() {
      return o_hp;
   }

   public void setO_hp(String o_hp) {
      this.o_hp = o_hp;
   }

   public String getO_request() {
      return o_request;
   }

   public void setO_request(String o_request) {
      this.o_request = o_request;
   }
   
}