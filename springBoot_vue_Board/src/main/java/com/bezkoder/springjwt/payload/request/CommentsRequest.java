package com.bezkoder.springjwt.payload.request;

import com.bezkoder.springjwt.models.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentsRequest {

    private Long id;

    @NotBlank
    private String comment;
}
