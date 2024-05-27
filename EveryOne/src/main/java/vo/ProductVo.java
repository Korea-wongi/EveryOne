package vo;

import java.sql.Date;

public class ProductVo {
   private int p_idx;            // 상품 인덱스
   private String p_mid;		 // 관리자 아이디
   private String p_category;    // 상품 분류
   private String p_name;        // 상품 이름
   private int p_price;          // 상품 가격
   private String p_color;       // 상품 색상
   private String p_size;        // 상품 사이즈
   private String p_content;     // 상품 설명
   
   /* 상품별 기온 추천   */
   private int p_codytemp;

   private ImgVo imgVo; //이미지 정보를 저장하는 ImgVo객체

   
   public ProductVo() {}
   
   public ProductVo(int p_idx, String p_category, String p_name, int p_price, String p_color, String p_size,
			String p_content, int p_codytemp) {
	this.p_idx = p_idx;
	this.p_category = p_category;
	this.p_name = p_name;
	this.p_price = p_price;
	this.p_color = p_color;
	this.p_size = p_size;
	this.p_content = p_content;
	this.p_codytemp = p_codytemp;
	}
	
	public ProductVo(int p_idx, String p_mid, String p_category, String p_name, int p_price, String p_color, String p_size,
				String p_content, int p_codytemp) {
	this.p_idx = p_idx;
	this.p_mid = p_mid;
	this.p_category = p_category;
	this.p_name = p_name;
	this.p_price = p_price;
	this.p_color = p_color;
	this.p_size = p_size;
	this.p_content = p_content;
	this.p_codytemp = p_codytemp;
	}
   

    public ProductVo(int p_idx, String p_mid, String p_category, String p_name, int p_price, String p_color, String p_size,
		String p_content) {
	this.p_idx = p_idx;
	this.p_mid = p_mid;
	this.p_category = p_category;
	this.p_name = p_name;
	this.p_price = p_price;
	this.p_color = p_color;
	this.p_size = p_size;
	this.p_content = p_content;
    }
    

	public ProductVo(int p_idx, String p_category, String p_name, int p_price, String p_color, String p_size,
			String p_content) {
		this.p_idx = p_idx;
		this.p_category = p_category;
		this.p_name = p_name;
		this.p_price = p_price;
		this.p_color = p_color;
		this.p_size = p_size;
		this.p_content = p_content;
	}
	
	

	public int getP_codytemp() {
		return p_codytemp;
	}

	public void setP_codytemp(int p_codytemp) {
		this.p_codytemp = p_codytemp;
	}

	public String getP_mid() {
		return p_mid;
	}

	public void setP_mid(String p_mid) {
		this.p_mid = p_mid;
	}

	public ImgVo getImgVo() {
    	return imgVo;
	}
	
	
	public void setImgVo(ImgVo imgVo) {
		this.imgVo = imgVo;
	}
	
	
	public int getP_idx() {
	      return p_idx;
   }


   public void setP_idx(int p_idx) {
      this.p_idx = p_idx;
   }


   public String getP_category() {
      return p_category;
   }


   public void setP_category(String p_category) {
      this.p_category = p_category;
   }


   public String getP_name() {
      return p_name;
   }


   public void setP_name(String p_name) {
      this.p_name = p_name;
   }


   public int getP_price() {
      return p_price;
   }


   public void setP_price(int p_price) {
      this.p_price = p_price;
   }


   public String getP_color() {
      return p_color;
   }


   public void setP_color(String p_color) {
      this.p_color = p_color;
   }


   public String getP_size() {
      return p_size;
   }


   public void setP_size(String p_size) {
      this.p_size = p_size;
   }


   public String getP_content() {
      return p_content;
   }


   public void setP_content(String p_content) {
      this.p_content = p_content;
   }
}