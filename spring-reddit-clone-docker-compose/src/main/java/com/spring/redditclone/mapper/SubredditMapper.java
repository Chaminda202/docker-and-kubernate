package com.spring.redditclone.mapper;

import com.spring.redditclone.model.SubredditDto;
import com.spring.redditclone.model.entity.Post;
import com.spring.redditclone.model.entity.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Subreddit mapToEntity(SubredditDto subredditDto);

    /*@Mapping(target = "numberOfPosts", expression = "java(mapPostsSize(subreddit.getPosts()))")
    SubredditDto mapToDto(Subreddit subreddit);*/

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target="numberOfPosts", expression = "java(mapPostsSize(subreddit.getPosts()))")
    })
    SubredditDto mapToDto(Subreddit subreddit);

    default Integer mapPostsSize(List<Post> posts) {
        return posts.size();
    }
}
