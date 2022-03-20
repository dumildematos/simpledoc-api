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
    private String username;

    @JsonProperty(access = WRITE_ONLY)
    private String password;
    private String firstname;
    private String lastname;
    private String avatar;
    private LocalDate birthdate;

    @Enumerated(STRING)
    private AuthorizationProvider authProvider;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany
    private Collection<Team> teams = new ArrayList<>();

    @OneToMany
    private Collection<Template> templates = new ArrayList<>();

}
