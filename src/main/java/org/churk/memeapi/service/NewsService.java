package org.churk.memeapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.churk.memeapi.client.NewsClient;
import org.churk.memeapi.configuration.NewsProperties;
import org.churk.memeapi.model.Article;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NewsService {
    private final NewsProperties newsProperties;
    private final NewsClient newsClient;

    public List<Article> getNews(String category) {
        LocalDateTime from = LocalDateTime.now().minusDays(2);
        return newsClient.getNewsByCategory(
                        "q=+" + category,
                        newsProperties.getApiKey(),
                        newsProperties.getLanguage(),
                        from.format(DateTimeFormatter.ISO_LOCAL_DATE)
                ).articles().stream()
                .limit(10)
                .toList();
    }
}
