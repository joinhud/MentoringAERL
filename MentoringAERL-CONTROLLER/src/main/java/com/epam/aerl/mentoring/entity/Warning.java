package com.epam.aerl.mentoring.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize(as = FindStudentsByIdsWarning.class)
public interface Warning extends Serializable {
}
