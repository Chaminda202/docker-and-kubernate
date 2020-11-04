package com.spring.redditclone.repository;

import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.Subreddit;
import com.spring.redditclone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubredditId(Long id);
    List<Post> findAllByUserUsername(String username);
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findAllByUser(User user);
}
