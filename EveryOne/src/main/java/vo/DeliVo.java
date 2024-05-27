package vo;

public class DeliVo {

   private int d_idx;             //배송 인덱스
    private String d_mid;         //멤버 아이디
    private String d_name;        //배송 수령자
    private String d_address1;    //배송 필수주소
    private String d_address2;    //배송 상세주소
    private String d_hp;          //배송 수령인 전화번호   
    
    
    public DeliVo() {}


   public DeliVo(int d_idx, String d_mid, String d_name, String d_address1, String d_address2, String d_hp) {
      this.d_idx = d_idx;
      this.d_mid = d_mid;
      this.d_name = d_name;
      this.d_address1 = d_address1;
      this.d_address2 = d_address2;
      this.d_hp = d_hp;
   }


   public int getD_idx() {
      return d_idx;
   }


   public void setD_idx(int d_idx) {
      this.d_idx = d_idx;
   }


   public String getD_mid() {
      return d_mid;
   }


   public void setD_mid(String d_mid) {
      this.d_mid = d_mid;
   }


   public String getD_name() {
      return d_name;
   }


   public void setD_name(String d_name) {
      this.d_name = d_name;
   }


   public String getD_address1() {
      return d_address1;
   }


   public void setD_address1(String d_address1) {
      this.d_address1 = d_address1;
   }


   public String getD_address2() {
      return d_address2;
   }


   public void setD_address2(String d_address2) {
      this.d_address2 = d_address2;
   }


   public String getD_hp() {
      return d_hp;
   }


   public void setD_hp(String d_hp) {
      this.d_hp = d_hp;
   }
    
}