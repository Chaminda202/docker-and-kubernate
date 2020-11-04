package com.spring.redditclone.service.impl;

import com.spring.redditclone.exception.RecordNotExistException;
import com.spring.redditclone.mapper.PostMapper;
import com.spring.redditclone.model.PostRequest;
import com.spring.redditclone.model.PostResponse;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.Subreddit;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.repository.PostRepository;
import com.spring.redditclone.repository.SubredditRepository;
import com.spring.redditclone.repository.UserRepository;
import com.spring.redditclone.service.AuthService;
import com.spring.redditclone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthService authService;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;

    @Override
    public PostResponse createPost(PostRequest request) {
        User user = this.authService.getCurrentUser();
        Subreddit subreddit = this.subredditRepository
                .findByName(request.getSubredditName())
                .orElseThrow(() -> new RecordNotExistException("Subreddit Entity not found by name: " + request.getSubredditName()));
        Post post = this.postMapper.mapToPostEntity(request, subreddit, user);
        return this.postMapper
                .mapToPostResponseDto(this.postRepository
                        .save(post));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getAllPosts() {
        return this.postRepository.findAll()
                .stream()
                .map(this.postMapper::mapToPostResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponse getPost(Long id) {
        return this.postRepository.findById(id)
                .map(this.postMapper::mapToPostResponseDto)
                .orElseThrow(() -> new RecordNotExistException("Subreddit Entity not found by id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getPostsBySubreddit(Long id) {
        /*return this.postRepository.findAllBySubredditId(id)
                .stream()
                .map(this.postMapper::mapToPostResponseDto)
                .collect(Collectors.toList());*/

        Subreddit subreddit = this.subredditRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotExistException("Subreddit Entity not found by id: " + id));

        return this.postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(this.postMapper::mapToPostResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getPostsByUsername(String username) {
        /*return this.postRepository.findAllByUserUsername(username)
                .stream()
                .map(this.postMapper::mapToPostResponseDto)
                .collect(Collectors.toList());*/

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotExistException("User Entity not found by username: " + username));

        return this.postRepository.findAllByUser(user)
                .stream()
                .map(this.postMapper::mapToPostResponseDto)
                .collect(Collectors.toList());

    }
}
