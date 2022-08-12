package vttp2022.ssfassessment.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import vttp2022.ssfassessment.model.Article;

@Service
public class ArticlesRedis implements ArticlesRepo{
    private static final Logger logger = LoggerFactory.getLogger(ArticlesRedis.class);

    @Autowired
    RedisTemplate<String, Article> template;

    @Override
    public void save(Article article) {
        template.opsForValue().set(article.getId(), article);
        logger.info("Saving article named >>>>> " + article.getTitle());
    }

    @Override
    public Article findById(String id) {
        Article article = (Article) template.opsForValue().get(id);
        return article;
    }
}
