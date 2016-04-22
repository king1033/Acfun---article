package acfun.com.article.entity;

import java.util.List;

/**
 *
 */
public class Pages {

    public int totalpage;

    public int totalcount;

    private List<ArticleTitle> articleTitles;

    public List<ArticleTitle> getArticleTitles() {
        return articleTitles;
    }

    public void setArticleTitles(List<ArticleTitle> articleTitles ) {
        this.articleTitles = articleTitles;
    }
}
