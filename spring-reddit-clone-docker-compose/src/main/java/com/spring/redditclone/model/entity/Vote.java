package com.spring.redditclone.model.entity;

import com.spring.redditclone.enumeration.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "VOTE")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    @Enumerated(value = EnumType.ORDINAL)
    private VoteType voteType;
    private Instant createdDate;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
