package com.tcc.simpledocapi.service.user.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForm {
    private String firstname;
    private String lastname;
    private String username;
    private CharSequence birthday;
    private String password;
    private String country;
    private String phonenumber;
    private String role;
}
