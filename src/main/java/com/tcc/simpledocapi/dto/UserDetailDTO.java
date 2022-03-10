package com.tcc.simpledocapi.dto;

import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.enums.AuthorizationProvider;
import lombok.Data;

import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.EnumType.STRING;

@Data
public class UserDetailDTO {

    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String avatar;
    private LocalDate birthdate;

    @Enumerated(STRING)
    private AuthorizationProvider authProvider;

    private Collection<Role> roles = new ArrayList<>();

}
