package org.churk.memeapi.client;

import org.churk.memeapi.model.News;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "newsClient", url = "https://newsapi.org/v2")
public interface NewsClient {
    @GetMapping("/everything?sortBy=popularity")
    News getNewsByCategory(@RequestParam("q") String category,
                           @RequestParam("apiKey") String apiKey,
                           @RequestParam("language") String language,
                           @RequestParam("from") String from);
}

