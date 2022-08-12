package vttp2022.ssfassessment.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.ssfassessment.model.Article;
import vttp2022.ssfassessment.service.ArticlesRedis;

@RestController
@RequestMapping(path = "/news", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsRESTController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    ArticlesRedis redisSvc;
    
    @GetMapping("/{id}")
    public ResponseEntity getArticleById(@PathVariable(value = "id") String id, Model model) {
        logger.info("inside get article codeblock");
        List<Article> listOfArticles = new LinkedList<>();
        Article tempArticle = redisSvc.findById(id);
        if(tempArticle.equals(null)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find news article " + id);
        }
        listOfArticles.add(redisSvc.findById(id));
        return ResponseEntity.ok(listOfArticles.get(0));
    }
}
