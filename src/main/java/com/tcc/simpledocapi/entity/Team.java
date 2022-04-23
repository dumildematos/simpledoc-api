package com.tcc.simpledocapi.entity;

import com.tcc.simpledocapi.enums.ShareType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Team {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    @Column(unique = false, nullable = false, length = 100000)
    private String banner;

    @Enumerated(STRING)
    private ShareType type;

    @ManyToMany
    private Collection<Document> documents = new ArrayList<>();

    @Transient
    private Collection<Contributor> contributors = new ArrayList<>();
    /*@ManyToMany
    private Collection<Contributor> contributos = new ArrayList<>();*/

}
