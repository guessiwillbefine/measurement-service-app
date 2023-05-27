package ua.ms.service.mq.impl.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.ms.util.ApplicationConstants;

import java.io.Serializable;

import static java.lang.String.format;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class MailAlertDto implements Serializable {
    private long sensorId;
    private String userMail;
    private String sensorModel;
    private String machineModel;
    private String machineName;
    private double value;
    private double criticalValue;

    public Message asMessage() {
        return new Message();
    }
    @Getter
    @Setter
    private class Message {
        private final long sensorId;
        private final String userMail;
        private final String text;

        private Message() {
            this.sensorId = MailAlertDto.this.sensorId;
            this.userMail = MailAlertDto.this.userMail;
            this.text = format(ApplicationConstants.Mail.MESSAGE_TEMPLATE,
                    sensorModel, machineModel, machineName,
                    sensorModel, value, criticalValue);
        }
    }

}
