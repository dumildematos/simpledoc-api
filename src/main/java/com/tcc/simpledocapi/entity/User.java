package com.tcc.simpledocapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tcc.simpledocapi.enums.AuthorizationProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column(unique = true)
    private String username;

    //@JsonProperty(access = WRITE_ONLY)
    @Column(length = 10000000)
    private String password;
    private String firstname;
    private String lastname;
    @Column(length = 10000000)
    private String avatar;
    private LocalDate birthdate;
    private String country;
    private String phonenumber;


    @Enumerated(STRING)
    private AuthorizationProvider authProvider;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL)
    private Collection<Team> teams = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = ALL)
    private Collection<Team> invitedTeams = new ArrayList<>();

    @OneToMany
    private Collection<Template> templates = new ArrayList<>();

}
