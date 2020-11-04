package com.spring.redditclone.repository;

import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.model.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
