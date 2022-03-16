package com.tcc.simpledocapi.service.team.form;

import com.tcc.simpledocapi.enums.ShareType;
import lombok.Data;

@Data
public class CreateTeamForm {
    private String name;
    private String description;
    private String banner;
    private ShareType type;
}
