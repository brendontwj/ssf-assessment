package vttp2022.ssfassessment.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp2022.ssfassessment.model.NewsArticles;

@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private String URL = "https://min-api.cryptocompare.com/data/v2/news/";

    public Optional<NewsArticles> getArticles() {
        String apikey = System.getenv("CRYPTO_API_KEY");
        String newsUrl = UriComponentsBuilder.fromUriString(URL)
            .queryParam("lang", "EN")
            .toUriString();

        logger.info("URL is " + newsUrl);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "text/html");
            headers.set("authorization", apikey);
            HttpEntity request = new HttpEntity<>(headers);
            resp = template.exchange(
                newsUrl, HttpMethod.GET, request, String.class, 1
            );
            // logger.info(resp.getBody());
            if(resp.getBody().contains("Error")){
                logger.info("resp body contains Error");
                return Optional.empty();
            }
            NewsArticles articles = NewsArticles.createJsonNewsArticles(resp.getBody());
            // logger.info("Articles made >>>>>> " + articles.getData().toString());
            logger.info("returning list of articles");
            return Optional.of(articles);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
