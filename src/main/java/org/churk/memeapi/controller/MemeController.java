package org.churk.memeapi.controller;

import lombok.AllArgsConstructor;
import org.churk.memeapi.model.Quote;
import org.churk.memeapi.model.RedditPost;
import org.churk.memeapi.model.Shitpost;
import org.churk.memeapi.service.MemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meme")
@AllArgsConstructor
public class MemeController {
    private final MemeService memeService;

    /**
     * Get a Reddit post from the subreddit.
     *
     * @param subreddit The subreddit to get the post from.
     * @param count     The number of posts to get.
     * @return The Reddit post.
     */
    @PostMapping("/reddit/{subreddit}/{count}")
    public ResponseEntity<List<RedditPost>> getRedditPost(@PathVariable("subreddit") String subreddit, @PathVariable("count") int count) {
        List<RedditPost> redditPosts = memeService.getRedditPost(subreddit, count);
        return redditPosts.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(redditPosts);
    }

    /**
     * Get a shitpost from the search.
     *
     * @param search The search to get the shitpost from.
     * @return The shitpost.
     */
    @PostMapping("/shitpost/{search}")
    public ResponseEntity<Shitpost> getShitPost(@PathVariable("search") String search) {
        return ResponseEntity.ok(memeService.getShitPost(search));
    }

    @PostMapping("/shitpost")
    public ResponseEntity<Shitpost> getShitPost() {
        return ResponseEntity.ok(memeService.getShitPost());
    }

    /**
     * Get a shitpost quote.
     *
     * @return The shitpost quote.
     */
    @PostMapping("/shitpost/quote")
    public ResponseEntity<Quote> getShitPostQuote() {
        return ResponseEntity.ok(memeService.getShitPostQuote());
    }
}
