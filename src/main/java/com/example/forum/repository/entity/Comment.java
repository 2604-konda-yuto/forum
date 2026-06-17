package com.example.forum.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @Column(insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(insertable = false)
    private String content;

    @Column(insertable = false)
    private int message_id;
}