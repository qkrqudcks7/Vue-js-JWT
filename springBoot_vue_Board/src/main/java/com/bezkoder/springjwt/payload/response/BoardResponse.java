package com.bezkoder.springjwt.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponse {

    private Long id;
    private String writer;
    private String subject;
    private String text;
    private Long viewCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    private LocalDateTime localDateTime;
}
