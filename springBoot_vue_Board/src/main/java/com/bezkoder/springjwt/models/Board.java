package com.bezkoder.springjwt.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String subject;

    private String text;

    private Long viewCount;

    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    public void plusViewCount(){
        this.viewCount++;
    }
}
