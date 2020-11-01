package com.spring.redditclone.controller;

import com.spring.redditclone.model.CommentDto;
import com.spring.redditclone.service.CommentService;
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
@RequestMapping(value = "/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    public ResponseEntity<CommentDto> createPost(@RequestBody CommentDto commentDto) {
        CommentDto response = this.commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(value = "/by-post/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable("id") Long id) {
        List<CommentDto> responseList = this.commentService.getCommentsByPost(id);
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping(value = "/by-user/{username}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable("username") String username) {
        List<CommentDto> responseList = this.commentService.getCommentsByUser(username);
        return ResponseEntity.ok().body(responseList);
    }
}
