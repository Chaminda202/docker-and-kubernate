package com.spring.redditclone.service.impl;

import com.spring.redditclone.exception.RecordNotExistException;
import com.spring.redditclone.mapper.SubredditMapper;
import com.spring.redditclone.model.SubredditDto;
import com.spring.redditclone.model.entity.Subreddit;
import com.spring.redditclone.repository.SubredditRepository;
import com.spring.redditclone.service.SubredditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubredditServiceImpl implements SubredditService {
    private final SubredditMapper subredditMapper;
    private final SubredditRepository subredditRepository;

    @Override
    public SubredditDto createSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = this.subredditRepository
                .save(this.subredditMapper.mapToEntity(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SubredditDto> getAll() {
        return this.subredditRepository
                .findAll()
                .stream()
                .map(this.subredditMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public SubredditDto getById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new RecordNotExistException("No subreddit found with ID - " + id));
        return subredditMapper.mapToDto(subreddit);
    }
}
