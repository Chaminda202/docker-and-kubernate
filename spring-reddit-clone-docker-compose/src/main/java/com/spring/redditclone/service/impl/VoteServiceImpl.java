package com.spring.redditclone.service.impl;

import com.spring.redditclone.enumeration.VoteType;
import com.spring.redditclone.exception.RecordNotExistException;
import com.spring.redditclone.exception.SpringRedditException;
import com.spring.redditclone.mapper.VoteMapper;
import com.spring.redditclone.model.VoteDto;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.model.entity.Vote;
import com.spring.redditclone.repository.PostRepository;
import com.spring.redditclone.repository.VoteRepository;
import com.spring.redditclone.service.AuthService;
import com.spring.redditclone.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final AuthService authService;
    private final PostRepository postRepository;

    /***
     * User can vote multiple times as wish, every time inserts new record to vote table not update matching record
     * @param voteDto
     * @return
     */
    @Override
    public VoteDto vote(VoteDto voteDto) {
        User user = this.authService.getCurrentUser();
        Post post = this.postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new RecordNotExistException("Post Entity not found by id:" + voteDto.getPostId()));
        Optional<Vote> latestVoteForPost = this.voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
        // Check latest vote as same as current vote
        if(latestVoteForPost.isPresent() && latestVoteForPost.get().getVoteType() == voteDto.getVoteType()) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + " for this post");
        } else {
            Vote vote = this.voteMapper.mapToVoteEntity(voteDto, post, user);
            //Update the total vote count for the post
            post.setVoteCount(VoteType.UPVOTE == voteDto.getVoteType() ? post.getVoteCount() + 1 : post.getVoteCount() -1);
            this.postRepository.save(post);
            return this.voteMapper.mapToVoteDto(
                    this.voteRepository.save(vote)
            );
        }
    }
}
