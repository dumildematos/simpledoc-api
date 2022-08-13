package com.tcc.simpledocapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Template {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000000000)
    private String content;
    @Column(length = 1000000000)
    private String cover;
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private String price;

    @ManyToMany
    @JoinColumn(nullable = true)
    private Collection<Category> category;
}
