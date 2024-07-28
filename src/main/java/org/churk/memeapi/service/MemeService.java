package org.churk.memeapi.service;

import lombok.AllArgsConstructor;
import org.churk.memeapi.client.RedditClient;
import org.churk.memeapi.client.ShitpostingClient;
import org.churk.memeapi.model.Quote;
import org.churk.memeapi.model.RedditPost;
import org.churk.memeapi.model.Shitpost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MemeService {
    private final RedditClient redditClient;
    private final ShitpostingClient shitpostingClient;

    public List<RedditPost> getRedditPost(String subreddit, int count) {
        return redditClient.getRedditMemes(subreddit, count).getMemes();
    }

    public Shitpost getShitPost(String search) {
        return shitpostingClient.getShitpost(search);
    }

    public Shitpost getShitPost() {
        return shitpostingClient.getShitpost();
    }

    public Quote getShitPostQuote() {
        return shitpostingClient.getQuote();
    }
}
