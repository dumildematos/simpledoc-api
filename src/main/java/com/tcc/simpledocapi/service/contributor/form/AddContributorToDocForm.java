package com.tcc.simpledocapi.service.contributor.form;

import com.tcc.simpledocapi.enums.ContributorPermission;
import lombok.Data;

import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Data
public class AddContributorToDocForm {
    private String username;
    @Enumerated(STRING)
    private ContributorPermission role;
    private Long documentId;
}
