package vttp2022.ssfassessment.service;

import vttp2022.ssfassessment.model.Article;

public interface ArticlesRepo {
    public void save(Article article);

    public Article findById(final String id);
}
