package com.taskmanagement.core.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class TaskRequest {

    @NotBlank
    @Size(max = 250)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    @JsonProperty("due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
    private Date dueDate;
}
