package com.sequencero.app.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AddSequenceDto {
    @NotEmpty
    @Size(min = 2, max = 100, message = "enter sequence name")
    private String name;

    @NotEmpty
    private String[] body;

    @NotEmpty
    private boolean seqIsPublic;

    @NotEmpty
    private String createdBy;
}
