package ua.ms.service.mq.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class MailAlertDto implements Serializable {
    private String userMail;
    private String sensorModel;
    private String machineModel;
    private String machineName;
    private int value;
    private int criticalValue;
}
