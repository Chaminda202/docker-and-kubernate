package com.spring.redditclone.service;

import com.spring.redditclone.model.SubredditDto;

import java.util.List;

public interface SubredditService {
    SubredditDto createSubreddit(SubredditDto subredditDto);
    List<SubredditDto> getAll();
    SubredditDto getById(Long id);
}
