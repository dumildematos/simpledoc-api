package com.tcc.simpledocapi.entity;

import com.tcc.simpledocapi.enums.ContributorPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.AUTO;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contributor {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private Long teamId;
    private Long documentId;
    private String avatar;

    @Enumerated(STRING)
    private ContributorPermission role;

}
