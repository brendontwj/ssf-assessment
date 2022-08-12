package vttp2022.ssfassessment.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonParser;
import vttp2022.ssfassessment.model.Article;
import vttp2022.ssfassessment.model.ArticleSaveObj;
import vttp2022.ssfassessment.model.NewsArticles;
import vttp2022.ssfassessment.service.ArticlesRedis;
import vttp2022.ssfassessment.service.NewsService;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    ArticlesRedis redisSvc;

    private static List<Article> staticList;

    @GetMapping("/")
    public String showLanguagePage(Model model) {
        logger.info("Showing news page");
        Optional<NewsArticles> allNews = newsService.getArticles();
        // logger.info(allNews.get().toString());
        if(allNews.isEmpty()) {
            logger.info("inside news empty block");
            return "api";
        } else {
            List<Article> listOfArticles = NewsController.createListOfArticles(allNews.get());
            staticList = listOfArticles;
            // logger.info(listOfArticles.toString());
            logger.info("adding attributes to model");
            model.addAttribute("articles", listOfArticles);
            model.addAttribute("NewsArticlesObj", new ArticleSaveObj());
            return "index";
        }
    }

    @PostMapping("/articles")
    public String saveArticles(@ModelAttribute ArticleSaveObj articlesToSave, Model model) {
        logger.info("staticList >>> " + staticList.size());
        logger.info("staticList >>> " + staticList.toString());
        logger.info("saveobj >>>>>> " + articlesToSave.getArticleList().toString());
        List<String> saveArticlesList = articlesToSave.getArticleList();
        for(String articleId:saveArticlesList) {
            for(int i = 0; i < staticList.size(); i++) {
                if(staticList.get(i).getId().equals(articleId)) {
                    redisSvc.save(staticList.get(i));
                }
            }
        }
        Optional<NewsArticles> allNews = newsService.getArticles();
        // logger.info(allNews.get().toString());
        if(allNews.isEmpty()) {
            logger.info("inside news empty block");
            return "api";
        } else {
            List<Article> listOfArticles = NewsController.createListOfArticles(allNews.get());
            staticList = listOfArticles;
            // logger.info(listOfArticles.toString());
            logger.info("adding attributes to model");
            model.addAttribute("articles", listOfArticles);
            model.addAttribute("NewsArticlesObj", new ArticleSaveObj());
            return "index";
        }
    }

    private static List<Article> createListOfArticles(NewsArticles articles) {
        List<Article> listOfArticles = new LinkedList<>();
        // logger.info(articles.getData().toString());
        JsonArray tempArr = articles.getData();
        for(int i = 0; i < tempArr.size(); i++) {
            Article tempArticle = new Article();
            JsonObject o = (JsonObject) tempArr.get(i);
            tempArticle.setId(o.getString("id"));
            tempArticle.setPublished_on(o.getJsonNumber("published_on").intValue());
            tempArticle.setTitle(o.getString("title"));
            tempArticle.setUrl(o.getString("url"));
            tempArticle.setImageurl(o.getString("imageurl"));
            tempArticle.setBody(o.getString("body"));
            tempArticle.setTags(o.getString("tags"));
            tempArticle.setCategories(o.getString("categories"));
            listOfArticles.add(tempArticle);
        }
        return listOfArticles;
    }
}
