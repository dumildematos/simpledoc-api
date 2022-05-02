package com.tcc.simpledocapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

import static javax.persistence.CascadeType.ALL;
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
    private String content;
    private String cover;
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private String price;

    @ManyToMany
    @JoinColumn(nullable = true)
    private Collection<Category> category;
}
