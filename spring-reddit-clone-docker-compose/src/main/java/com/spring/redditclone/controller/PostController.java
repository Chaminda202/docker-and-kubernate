package com.spring.redditclone.controller;

import com.spring.redditclone.model.PostRequest;
import com.spring.redditclone.model.PostResponse;
import com.spring.redditclone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        PostResponse response = this.postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> postList = this.postService.getAllPosts();
        return ResponseEntity.ok().body(postList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id) {
        PostResponse response = this.postService.getPost(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable("id") Long id) {
        List<PostResponse> responseList = this.postService.getPostsBySubreddit(id);
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping(value = "/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable("username") String username) {
        List<PostResponse> responseList = this.postService.getPostsByUsername(username);
        return ResponseEntity.ok().body(responseList);
    }
}
