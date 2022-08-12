package vttp2022.ssfassessment.service;

import java.util.LinkedList;
import java.util.List;

import vttp2022.ssfassessment.model.Article;

public interface ArticlesRepo {
    public void save(List<Article> listOfArticles);

    public Article findById(final String id);
}
