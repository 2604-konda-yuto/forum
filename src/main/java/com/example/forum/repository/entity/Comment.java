package com.example.forum.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String content;

    @Column
    private int messageId;

    @Column(name = "created_date", insertable = false,updatable = false)
    private Date created_date;

    @Column(name = "updated_date", insertable = false,updatable = false)
    private Date updated_date;
}