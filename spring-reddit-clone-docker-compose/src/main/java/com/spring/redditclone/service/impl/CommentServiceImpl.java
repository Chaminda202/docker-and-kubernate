package com.spring.redditclone.service.impl;

import com.spring.redditclone.exception.RecordNotExistException;
import com.spring.redditclone.mapper.CommentMapper;
import com.spring.redditclone.model.CommentDto;
import com.spring.redditclone.model.NotificationEmail;
import com.spring.redditclone.model.entity.Comment;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.repository.CommentRepository;
import com.spring.redditclone.repository.PostRepository;
import com.spring.redditclone.repository.UserRepository;
import com.spring.redditclone.service.AuthService;
import com.spring.redditclone.service.CommentService;
import com.spring.redditclone.service.MailContentBuilder;
import com.spring.redditclone.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final AuthService authService;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Post post = this.postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new RecordNotExistException("Post Entity not found by id:" + commentDto.getId()));
        User currentUser = this.authService.getCurrentUser();
        // No need to pass username in request payload
        /*User user = this.userRepository.findByUsername(commentDto.getUsername())
                .orElseThrow(() -> new RecordNotExistException("User Entity not found by username:" + commentDto.getUsername()));*/
        Comment comment = this.commentMapper.mapToCommentEntity(commentDto, post, currentUser);

        //Send the email to person who has published the post
        String postUrl = "http://localhost:8070/api/posts/" + commentDto.getPostId();
        String message = commentDto.getUsername() + " has posted comment on your post. Click here to " + postUrl;
        NotificationEmail notificationEmail = NotificationEmail.builder()
                .subject("Commented on your post")
                .recipient(post.getUser().getEmail())
                .body(this.mailContentBuilder.build(message))
                .build();
        this.mailService.sendMail(notificationEmail);
        return this.commentMapper.mapToCommentDto(
                this.commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsByPost(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new RecordNotExistException("Post Entity not found by id:" + id));
        return this.commentRepository.findAllByPost(post)
                .stream()
                .map(this.commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getCommentsByUser(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotExistException("User Entity not found by username: " + username));
        return this.commentRepository.findAllByUser(user)
                .stream()
                .map(this.commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }
}
