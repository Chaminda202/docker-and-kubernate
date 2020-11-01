package com.spring.redditclone.repository;

import com.spring.redditclone.model.entity.Comment;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);
}
