package vttp2022.ssfassessment.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import vttp2022.ssfassessment.model.Article;

@Component
public class ArticleConverter implements Converter<String, Article> {

    @Override
    public Article convert(String source) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
