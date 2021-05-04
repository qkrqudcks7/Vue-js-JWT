package com.bezkoder.springjwt.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardRequest {

    @NotBlank
    private String subject;

    @NotBlank
    private String text;
}
