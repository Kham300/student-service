package com.studentservice.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "marks", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "mark_date", "student_id"}))
public class Mark extends AbstractBaseEntity {

    @NotNull
    @Column(name = "mark_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate voteDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "mark_value", nullable = false)
    private GradeValue value;

    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student-mark")
    private Student student;


    @ApiModelProperty(hidden = true)
    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference(value = "course-marks")
    private Course course;
}
