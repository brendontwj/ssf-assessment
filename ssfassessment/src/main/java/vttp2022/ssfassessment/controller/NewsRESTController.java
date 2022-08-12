package vttp2022.ssfassessment.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import vttp2022.ssfassessment.model.Article;
import vttp2022.ssfassessment.service.ArticlesRedis;

@RestController
public class NewsRESTController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    ArticlesRedis redisSvc;
    
    @GetMapping("/news/{id}")
    public String getArticle(@PathVariable(value = "id") String id, Model model) {
        logger.info("inside get article codeblock");
        List<Article> listOfArticles = new LinkedList<>();
        listOfArticles.add(redisSvc.findById(id));
        model.addAttribute("articles", listOfArticles);
        return "index";
    }
}
