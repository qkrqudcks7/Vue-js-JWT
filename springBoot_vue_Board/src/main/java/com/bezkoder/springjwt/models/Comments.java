package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Comments {

    @Id @GeneratedValue
    @Column(name = "comments_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id" , nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime localDateTime;
}
