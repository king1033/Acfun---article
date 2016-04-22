package acfun.com.article.entity;

/**
 *
 */
public class ArticleTitle {

    private String userName;
    private int stows;
    private int comments;
    private int views;
    private String title;
    private int contentId;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setStows(int stows){
        this.stows = stows;
    }

    public int getStows(){
        return this.stows;
    }

    public void setComments(int comments){
        this.comments = comments;
    }

    public int getComments(){
        return this.comments;
    }

    public void setViews(int views){
        this.views = views;
    }

    public int getViews(){
        return this.views;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setContentId(int contentId){
        this.contentId = contentId;
    }

    public int getContentId(){
        return this.contentId;
    }
}
