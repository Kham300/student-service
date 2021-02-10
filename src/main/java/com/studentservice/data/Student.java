package com.studentservice.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Student extends AbstractBaseEntity {

    @NotEmpty
    @Size(max = 128)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(max = 128)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 128)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 128)
    @Column(name = "patronymic")
    private String patronymic;

    @ApiModelProperty(hidden = true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonManagedReference
    private Set<Course> courses = new HashSet<>();

    @ApiModelProperty(hidden = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    @JsonManagedReference(value = "student-mark")
    private Set<Mark> marks = new HashSet<>();
}
