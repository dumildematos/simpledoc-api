package com.tcc.simpledocapi.service.team.form;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTeamForm {
    private String name;
    private String description;
}
