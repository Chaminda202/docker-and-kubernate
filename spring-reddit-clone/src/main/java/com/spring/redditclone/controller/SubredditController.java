package com.spring.redditclone.controller;

import com.spring.redditclone.model.SubredditDto;
import com.spring.redditclone.service.SubredditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/api/subreddits")
@RequiredArgsConstructor
@Slf4j
public class SubredditController {
    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        SubredditDto response = this.subredditService.createSubreddit(subredditDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        List<SubredditDto> subredditDtoList = this.subredditService.getAll();
        return ResponseEntity.ok().body(subredditDtoList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable("id") Long id) {
        SubredditDto response = this.subredditService.getById(id);
        return ResponseEntity.ok().body(response);
    }
}
