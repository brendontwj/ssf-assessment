package vttp2022.ssfassessment.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class NewsArticles {
    private static final Logger logger = LoggerFactory.getLogger(NewsArticles.class);

    private String Type;
    private String Message;
    private JsonArray Promoted;
    private JsonArray Data;
    private String[] ArticlesToSave;

    public String[] getArticlesToSave() {
        return ArticlesToSave;
    }

    public void setArticlesToSave(String[] articlesToSave) {
        ArticlesToSave = articlesToSave;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public JsonArray getPromoted() {
        return Promoted;
    }

    public void setPromoted(JsonArray jsonArray) {
        Promoted = jsonArray;
    }

    public JsonArray getData() {
        return Data;
    }

    public void setData(JsonArray jsonArray) {
        Data = jsonArray;
    }

    public static NewsArticles createJsonNewsArticles(String json) throws IOException {
        logger.info("Inside create News Article Json block");
        NewsArticles articles = new NewsArticles();

        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            // logger.info("object read >>>> " + o.toString());
            if(o.containsKey("Message")){
                logger.info("Inside NewsArticles if block");
                articles.setType(o.get("Type").toString());
                articles.setMessage(o.get("Message").toString());
                articles.setPromoted(o.getJsonArray("Promoted"));
                articles.setData(o.getJsonArray("Data"));
            } 
            logger.info(articles.toString());
            return articles;
        }
    }
}
