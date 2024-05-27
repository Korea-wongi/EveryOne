package vo;

public class ImgVo {

   private int i_pidx;     //상품 번호참조
    private String i_name; //상품 이미지 이름
    private String i_path; //상품 이미지 경로
   
    
    public ImgVo() {}


   public ImgVo(int i_pidx, String i_name, String i_path) {
      
      this.i_pidx = i_pidx;
      this.i_name = i_name;
      this.i_path = i_path;
   }


   public int getI_pidx() {
      return i_pidx;
   }


   public void setI_pidx(int i_pidx) {
      this.i_pidx = i_pidx;
   }


   public String getI_name() {
      return i_name;
   }


   public void setI_name(String i_name) {
      this.i_name = i_name;
   }


   public String getI_path() {
      return i_path;
   }


   public void setI_path(String i_path) {
      this.i_path = i_path;
   }


    
    
}