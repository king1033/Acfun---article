package acfun.com.article.entity;

/**
 *
 */
public class FirstImage {
    private String src;
    private String text;

    public void setSrc(String src){
        this.src = src;
    }

    public String getSrc(){
        return src;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setData(String src, String text){
        setSrc(src);
        setText(text);
    }
}
