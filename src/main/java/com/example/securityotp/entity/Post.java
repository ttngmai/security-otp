package com.example.securityotp.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "POST")
@SequenceGenerator(name = "POST_SEQ_GEN", sequenceName = "POST_SEQ", initialValue = 1, allocationSize = 1)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQ_GEN")
    private Long id;

    private String title;
    private String content;
}
