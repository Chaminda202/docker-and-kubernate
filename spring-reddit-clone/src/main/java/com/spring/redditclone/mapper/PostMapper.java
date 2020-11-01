package com.spring.redditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.spring.redditclone.enumeration.VoteType;
import com.spring.redditclone.model.PostRequest;
import com.spring.redditclone.model.PostResponse;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.Subreddit;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.model.entity.Vote;
import com.spring.redditclone.repository.CommentRepository;
import com.spring.redditclone.repository.VoteRepository;
import com.spring.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mappings({
            @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
            @Mapping(target = "description", source = "postRequest.description"),
            @Mapping(target = "voteCount", constant = "0"),
            @Mapping(target = "subreddit", source = "subreddit"),
            @Mapping(target = "user", source = "user")
    })
   public abstract Post mapToPostEntity(PostRequest postRequest, Subreddit subreddit, User user);

    @Mappings({
            @Mapping(target = "id", source = "postId"),
            @Mapping(target = "userName", source = "user.username"),
            @Mapping(target = "subredditName", source = "subreddit.name"),
            @Mapping(target = "commentCount", expression = "java(commentCount(post))"),
            @Mapping(target = "duration", expression = "java(getDuration(post))"),
            @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))"),
            @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))"),
    })
    public abstract PostResponse mapToPostResponseDto(Post post);

    Integer commentCount(Post post) {
        return this.commentRepository.findAllByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (this.authService.isLoggedIn()) {
            return this.voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                    this.authService.getCurrentUser())
                    .filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
