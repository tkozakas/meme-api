package org.churk.memeapi.controller;

import lombok.AllArgsConstructor;
import org.churk.memeapi.model.Article;
import org.churk.memeapi.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class NewsController {
    private final NewsService newsService;

    /**
     * Get news articles from the category.
     *
     * @param category The category to get the news from.
     * @return The news articles.
     */
    @PostMapping("/{category}")
    public ResponseEntity<List<Article>> getNews(@PathVariable("category") String category) {
        List<Article> news = newsService.getNews(category);
        return news.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(news);
    }
}
