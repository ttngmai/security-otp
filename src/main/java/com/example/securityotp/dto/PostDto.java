package com.example.securityotp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    @NotBlank(message = "공백 불가!")
    private String content;
}
