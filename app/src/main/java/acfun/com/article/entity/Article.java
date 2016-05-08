package acfun.com.article.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Article {

    /**
     * success : true
     * msg : 操作成功
     * status : 200
     * data : {"fullArticle":{"user":{"userId":613827,"username":"九折坂二人","userImg":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201512/141907402i6eisps.jpg"},"tags":["\u201c萌护士\u201d"],"txt":"","contentId":2719603,"isArticle":1,"channelId":110,"isRecommend":0,"comments":31,"title":"\u201c萌护士\u201d手绘漫画实习日记 网友点赞！","cover":"http://cdn.aixifan.com/dotnet/20120923/style/image/cover-night.png","stows":38,"views":5562,"danmakuSize":0,"releaseDate":1462443028000,"viewOnly":0,"toplevel":0,"tudouDomain":0}}
     */

    private boolean success;
    private String msg;
    private int status;
    /**
     * fullArticle : {"user":{"userId":613827,"username":"九折坂二人","userImg":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201512/141907402i6eisps.jpg"},"tags":["\u201c萌护士\u201d"],"txt":"","contentId":2719603,"isArticle":1,"channelId":110,"isRecommend":0,"comments":31,"title":"\u201c萌护士\u201d手绘漫画实习日记 网友点赞！","cover":"http://cdn.aixifan.com/dotnet/20120923/style/image/cover-night.png","stows":38,"views":5562,"danmakuSize":0,"releaseDate":1462443028000,"viewOnly":0,"toplevel":0,"tudouDomain":0}
     */

    private Data data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /**
         * user : {"userId":613827,"username":"九折坂二人","userImg":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201512/141907402i6eisps.jpg"}
         * tags : ["\u201c萌护士\u201d"]
         * txt :
         * contentId : 2719603
         * isArticle : 1
         * channelId : 110
         * isRecommend : 0
         * comments : 31
         * title : “萌护士”手绘漫画实习日记 网友点赞！
         * cover : http://cdn.aixifan.com/dotnet/20120923/style/image/cover-night.png
         * stows : 38
         * views : 5562
         * danmakuSize : 0
         * releaseDate : 1462443028000
         * viewOnly : 0
         * toplevel : 0
         * tudouDomain : 0
         */

        private FullArticle fullArticle;

        public FullArticle getFullArticle() {
            return fullArticle;
        }

        public void setFullArticle(FullArticle fullArticle) {
            this.fullArticle = fullArticle;
        }

        public static class FullArticle {
            /**
             * userId : 613827
             * username : 九折坂二人
             * userImg : http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201512/141907402i6eisps.jpg
             */

            private User user;
            private String txt;
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
            private List<String> tags;

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
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

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
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
    }
}
