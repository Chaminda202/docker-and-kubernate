package com.spring.redditclone.controller;

import com.spring.redditclone.model.VoteDto;
import com.spring.redditclone.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/votes")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    @PostMapping
    public ResponseEntity<VoteDto> createPost(@RequestBody VoteDto voteDto) {
        VoteDto response = this.voteService.vote(voteDto);
        return ResponseEntity.ok()
                .body(response);
    }
}
