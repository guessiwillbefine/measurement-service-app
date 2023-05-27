package ua.ms.consumer.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class AlertDto implements Serializable {

    /** sensor id where error occurred */
    private long sensorId;

    private String userMail;

    private String text;
}