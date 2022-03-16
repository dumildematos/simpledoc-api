package com.tcc.simpledocapi.service.document.form;

import com.tcc.simpledocapi.enums.ShareType;
import lombok.Data;

@Data
public class DocumentAddForm {
    private String name;
    private String content;
    private ShareType type;
    private Long teamId;
}
