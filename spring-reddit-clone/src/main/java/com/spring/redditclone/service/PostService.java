package com.spring.redditclone.service;

import com.spring.redditclone.model.PostRequest;
import com.spring.redditclone.model.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest request);
    List<PostResponse> getAllPosts();
    PostResponse getPost(Long id);
    List<PostResponse> getPostsBySubreddit(Long id);
    List<PostResponse> getPostsByUsername(String username);
}
