package vttp2022.ssfassessment.model;

import java.util.LinkedList;
import java.util.List;

public class ArticleSaveObj {
    private List<String> articleList = new LinkedList<String>();

    public List<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<String> articleList) {
        this.articleList = articleList;
    } 
}
