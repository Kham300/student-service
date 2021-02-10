package com.studentservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentservice.data.GradeValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkDto implements Serializable {
    private static final long serialVersionUID = 7625430520909715490L;

    @NotNull
    @ApiModelProperty(value = "Mark date", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate markDate;

    @NotNull
    @ApiModelProperty(value = "Grade value", required = true)
    private GradeValue value;

    @NotNull
    @ApiModelProperty(value = "Student id", required = true)
    private Integer studentId;

    @NotNull
    @ApiModelProperty(value = "Course id", required = true)
    private Integer courseId;
}
