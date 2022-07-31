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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    @Column(length = 100000)
    private String content;
    private LocalDateTime createdAt;
    private String creator;

    @Enumerated(STRING)
    private ShareType type;

    @OneToMany
    private Collection<Contributor> contributors = new ArrayList<>();

}
