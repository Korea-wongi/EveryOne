package vo;

// 장바구니 역할 VO
public class CartVo {
   // 변수 선언
    private int c_idx; 		//카트 인덱스
    private int c_pidx; 	//상품 번호
    private String c_mid;   //멤버 아이디
    private String c_name;  //상품 이름
    private int c_amount;   //상품 구매 수량
    private int c_price; 	//상품 가격
    
    private String c_pcontent; // vo 만 추가
    private String c_iname;    // vo 만 추가
    private String c_pmid;    // vo 만 추가
    
    // 기본 생성자
    public CartVo() {
      
   }

    // 전체 생성자
    public CartVo(int c_idx, int c_pidx, String c_mid, String c_name, int c_amount, int c_price) {
      super();
      this.c_idx = c_idx;
      this.c_pidx = c_pidx;
      this.c_mid = c_mid;
      this.c_name = c_name;
      this.c_amount = c_amount;
      this.c_price = c_price;
    }

    
   
   public String getC_pmid() {
		return c_pmid;
	}

	public void setC_pmid(String c_pmid) {
		this.c_pmid = c_pmid;
	}

public String getC_iname() {
		return c_iname;
	}

	public void setC_iname(String c_iname) {
		this.c_iname = c_iname;
	}

	public String getC_pcontent() {
 		return c_pcontent;
 	}

 	public void setC_pcontent(String c_pcontent) {
 		this.c_pcontent = c_pcontent;
 	}
   
   // getter, setter 메소드
   public int getC_idx() {
      return c_idx;
   }

   public void setC_idx(int c_idx) {
      this.c_idx = c_idx;
   }

   public int getC_pidx() {
      return c_pidx;
   }

   public void setC_pidx(int c_pidx) {
      this.c_pidx = c_pidx;
   }

   public String getC_mid() {
      return c_mid;
   }

   public void setC_mid(String c_mid) {
      this.c_mid = c_mid;
   }

   public String getC_name() {
      return c_name;
   }

   public void setC_name(String c_name) {
      this.c_name = c_name;
   }

   public int getC_amount() {
      return c_amount;
   }

   public void setC_amount(int c_amount) {
      this.c_amount = c_amount;
   }

   public int getC_price() {
      return c_price;
   }

   public void setC_price(int c_price) {
      this.c_price = c_price;
   }

}