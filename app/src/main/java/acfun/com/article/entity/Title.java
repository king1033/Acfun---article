package acfun.com.article.entity;

/**
 *
 */
public class Title {

    /**
     * userId : 27
     * username : 小匆菌
     * userImg : http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/15111245v27v.jpg
     */

    private User user;
    /**
     * user : {"userId":27,"username":"小匆菌","userImg":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/15111245v27v.jpg"}
     * tags : []
     * description : 传统艺术与现代科技的结合「初音歌舞伎」
     * contentId : 2721598
     * isArticle : 1
     * channelId : 74
     * isRecommend : 0
     * comments : 0
     * title : 传统艺术与现代科技的结合「初音歌舞伎」
     * cover : http://cdn.aixifan.com/dotnet/20120923/style/image/cover-day.png
     * stows : 0
     * views : 0
     * danmakuSize : 0
     * releaseDate : 1462518536000
     * viewOnly : 0
     * toplevel : 0
     * tudouDomain : 0
     */

    private String description;
    private int contentId;
    private int isArticle;
    private int channelId;
    private int isRecommend;
    private int comments;
    private String title;
    private String cover;
    private int stows;
    private int views;
    private int danmakuSize;
    private long releaseDate;
    private int viewOnly;
    private int toplevel;
    private int tudouDomain;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getIsArticle() {
        return isArticle;
    }

    public void setIsArticle(int isArticle) {
        this.isArticle = isArticle;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getStows() {
        return stows;
    }

    public void setStows(int stows) {
        this.stows = stows;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDanmakuSize() {
        return danmakuSize;
    }

    public void setDanmakuSize(int danmakuSize) {
        this.danmakuSize = danmakuSize;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getViewOnly() {
        return viewOnly;
    }

    public void setViewOnly(int viewOnly) {
        this.viewOnly = viewOnly;
    }

    public int getToplevel() {
        return toplevel;
    }

    public void setToplevel(int toplevel) {
        this.toplevel = toplevel;
    }

    public int getTudouDomain() {
        return tudouDomain;
    }

    public void setTudouDomain(int tudouDomain) {
        this.tudouDomain = tudouDomain;
    }

    public static class User {
        private int userId;
        private String username;
        private String userImg;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }
    }
}
