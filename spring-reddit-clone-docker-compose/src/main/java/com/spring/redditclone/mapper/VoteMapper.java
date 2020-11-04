package com.spring.redditclone.mapper;

import com.spring.redditclone.model.VoteDto;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.User;
import com.spring.redditclone.model.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mappings({
            @Mapping(target = "voteId", ignore = true),
            @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())"),
            @Mapping(target = "post", source = "post"),
            @Mapping(target = "user", source = "user")
    })
    Vote mapToVoteEntity(VoteDto voteDto, Post post, User user);

    @Mappings({
            @Mapping(target = "postId", source = "post.postId"),
            @Mapping(target = "username", source = "user.username")
    })
    VoteDto mapToVoteDto(Vote vote);
}
