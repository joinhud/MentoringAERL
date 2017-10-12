package com.epam.aerl.mentoring.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonDeserialize(as = ResponseErrorWithMessage.class)
public interface ResponseError extends Serializable {
}
