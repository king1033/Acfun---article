package acfun.com.article.entity;

import cn.bmob.v3.BmobObject;

/**
 *
 */
public class FirstImage extends BmobObject{
    private String imgUrl;
    private String imgText;
    private String contentId;

    public void setImgUrl(String imageUrl){
        this.imgUrl = imageUrl;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public String getImgText(){
        return imgText;
    }

    public void setContentId(String contentId){
        this.contentId = contentId;
    }

    public String getContentId() {
        return contentId;
    }
}
