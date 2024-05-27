package vo;

public class WishVo {
	
	private int w_idx; //좋아요에 대한 인덱스
	private String w_mid; //좋아요를 누른 멤버 ID
	private String w_prod; //상품명
	
	public WishVo() {  }

	public int getW_idx() {
		return w_idx;
	}

	public void setW_idx(int w_idx) {
		this.w_idx = w_idx;
	}

	public String getW_mid() {
		return w_mid;
	}

	public void setW_mid(String w_mid) {
		this.w_mid = w_mid;
	}

	public String getW_prod() {
		return w_prod;
	}

	public void setW_prod(String w_prod) {
		this.w_prod = w_prod;
	}

	
	

}
