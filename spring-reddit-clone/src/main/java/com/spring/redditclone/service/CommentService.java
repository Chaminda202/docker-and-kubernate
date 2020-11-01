package com.spring.redditclone.service;

import com.spring.redditclone.model.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    List<CommentDto> getCommentsByPost(Long id);
    List<CommentDto> getCommentsByUser(String username);
}
