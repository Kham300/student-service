package com.studentservice.data;

import lombok.Getter;

@Getter
public enum GradeValue {
    A(5), B(4), C(3), D(2), E(1);

    private int value;

    GradeValue(int i) {
        this.value = i;
    }
}
