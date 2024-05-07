package org.xjudge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionModel {
    private int statusCode;
    private String message;
    private String timeStamp;
    private String description;
}
