package ua.ms.entity.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.sensor.Sensor;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class MeasuresSocketDto {

    private MachineMessage machineMessage;

    public record MachineMessage(List<SensorMessage> sensors) {}

    public record SensorMessage(Long id, MeasureMessage measure) {
        public static SensorMessage fromEntity(Sensor sensor, Measure measure) {
            return new SensorMessage(sensor.getId(),
                    MeasureMessage.fromEntity(measure));
        }
    }

    public record MeasureMessage(Double value, Boolean isCritical){
        public static MeasureMessage fromEntity(Measure measure) {
            return new MeasureMessage(measure.getValue(), measure.isCriticalSafe());
        }
    }
}