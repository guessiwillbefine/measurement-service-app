package ua.ms;

import ua.ms.entity.factory.Factory;
import ua.ms.entity.factory.dto.FactoryDto;
import ua.ms.entity.factory.dto.view.FactoryView;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.machine.MachineActivity;
import ua.ms.entity.machine.MachineType;
import ua.ms.entity.machine.dto.MachineDto;
import ua.ms.entity.machine.dto.view.MachineView;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.measure.MeasureSystem;
import ua.ms.entity.measure.dto.MeasureDto;
import ua.ms.entity.measure.dto.view.MeasureView;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.entity.sensor.dto.view.SensorView;
import ua.ms.entity.user.Role;
import ua.ms.entity.user.Status;
import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.AuthenticationCredentialsDto;
import ua.ms.entity.user.dto.UserDto;
import ua.ms.entity.user.dto.view.UserView;
import ua.ms.service.mq.impl.mail.MailAlertDto;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.entity.work_shift.dto.WorkShiftDto;
import ua.ms.entity.work_shift.dto.view.WorkShiftView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class TestConstants {
    public static final AuthenticationCredentialsDto USER_CREDENTIALS = AuthenticationCredentialsDto.builder()
            .username("username")
            .password("password").build();
    public static final AuthenticationCredentialsDto INVALID_USER_CREDENTIALS = AuthenticationCredentialsDto.builder()
            .username("")
            .password("").build();

    public static final Factory FACTORY_ENTITY = Factory.builder()
            .employees(Collections.emptyList())
            .name("someFactoryName")
            .id(1L).build();
    public static final FactoryDto FACTORY_DTO = FactoryDto.builder()
            .name("someFactoryName")
            .id(1L).build();
    public static final MachineDto MACHINE_DTO = MachineDto.builder()
            .id(1L).name("name").model("ZXC993-EZ").type(MachineType.MANIPULATOR)
            .factoryId(FACTORY_DTO.getId()).build();
    public static final Machine MACHINE_ENTITY = Machine.builder().id(1L)
            .model("ZXC993-EZ").type(MachineType.MANIPULATOR)
            .factory(FACTORY_ENTITY).activity(MachineActivity.INACTIVE).build();
    public static final Machine ACTIVE_MACHINE_ENTITY = Machine.builder().id(1L)
            .model("ZXC993-EZ").type(MachineType.MANIPULATOR)
            .factory(FACTORY_ENTITY).activity(MachineActivity.ACTIVE).build();
    public static final Sensor SENSOR_ENTITY = Sensor.builder()
            .id(1L).name("someSensorName").criticalValue(20.0)
            .machine(MACHINE_ENTITY).build();

    public static final SensorDto SENSOR_DTO = SensorDto.builder()
            .id(1L).name("someSensorName")
            .build();

    public static final Measure MEASURE_ENTITY = Measure.builder()
            .id(1L).value(37.1).sensor(SENSOR_ENTITY)
            .createdAt(LocalDateTime.now())
            .build();

    public static final MeasureDto MEASURE_DTO = MeasureDto.builder()
            .id(1L).value(37.1).sensorId(SENSOR_ENTITY.getId())
            .createdAt(LocalDateTime.now())
            .build();
    public static final User USER_ENTITY = User.builder()
            .id(1L).username("username")
            .email("test@gmail.com").firstName("name")
            .lastName("sname").status(Status.ACTIVE)
            .role(Role.ADMIN).password("password")
            .factory(FACTORY_ENTITY).build();

    public static final User USER_ENTITY_FOR_REGISTRATION = User.builder()
            .username(USER_CREDENTIALS.getUsername())
            .password(USER_CREDENTIALS.getPassword())
            .role(Role.ADMIN)
            .status(Status.ACTIVE)
            .build();
    public static final UserDto USER_DTO = UserDto.builder()
            .id(1L).username("username")
            .email("test@gmail.com").firstName("name")
            .lastName("sname").status(Status.ACTIVE)
            .factoryId(MACHINE_DTO.getFactoryId())
            .role(Role.ADMIN).build();

    public static final MailAlertDto MAIL_ALERT_DTO =
            new MailAlertDto(1L,"TestSensor1", "ZXC993-F", "machine1", "sd", 10.0, 24.0);

    public static final WorkShift WORK_SHIFT_ENTITY = WorkShift.builder()
            .id(1L).worker(USER_ENTITY)
            .machine(MACHINE_ENTITY)
            .build();
  
    public static final WorkShiftDto WORK_SHIFT_DTO = WorkShiftDto.builder()
            .workerId(USER_ENTITY.getId()).machineId(MACHINE_ENTITY.getId()).build();

    public static final UserView USER_VIEW = new UserView() {
        @Override
        public long getId() {
            return USER_ENTITY.getId();
        }

        @Override
        public String getFirstName() {
            return USER_ENTITY.getFirstName();
        }

        @Override
        public String getLastName() {
            return USER_ENTITY.getLastName();
        }

        @Override
        public String getUsername() {
            return USER_ENTITY.getUsername();
        }

        @Override
        public String getEmail() {
            return USER_ENTITY.getEmail();
        }

        @Override
        public Role getRole() {
            return USER_ENTITY.getRole();
        }

        @Override
        public Status getStatus() {
            return USER_ENTITY.getStatus();
        }

    };
    public static final FactoryView FACTORY_VIEW = new FactoryView() {
        @Override
        public Long getId() {
            return 1L;
        }

        @Override
        public String getName() {
            return "someFactoryName";
        }

        @Override
        public List<UserView> getEmployees() {
            return List.of(USER_VIEW, USER_VIEW, USER_VIEW, USER_VIEW, USER_VIEW, USER_VIEW);
        }

        @Override
        public List<MachineView> getMachines() {
            return Collections.emptyList();
        }
    };

    public static final MachineView MACHINE_VIEW = new MachineView() {
        @Override
        public Long getId() {
            return MACHINE_ENTITY.getId();
        }

        @Override
        public String getName() {
            return MACHINE_ENTITY.getName();
        }

        @Override
        public String getModel() {
            return MACHINE_ENTITY.getModel();
        }

        @Override
        public MachineType getType() {
            return MACHINE_ENTITY.getType();
        }

        @Override
        public List<SensorView> getSensors() {
            return new ArrayList<>();
        }

        @Override
        public MachineActivity getActivity() {
            return MachineActivity.INACTIVE;
        }

        @Override
        public Factory getFactory() {
            return MACHINE_ENTITY.getFactory();
        }
    };

    public static final MeasureView MEASURE_VIEW = new MeasureView() {
        @Override
        public Long getId() {
            return MEASURE_ENTITY.getId();
        }

        @Override
        public double getValue() {
            return MEASURE_ENTITY.getValue();
        }

        @Override
        public SensorView getSensor() {
            return SENSOR_VIEW;
        }

        @Override
        public LocalDateTime getCreatedAt() {
            return MEASURE_ENTITY.getCreatedAt();
        }

        @Override
        public boolean isCritical() {
            return  MEASURE_ENTITY.getValue() > SENSOR_ENTITY.getCriticalValue();
        }
    };

    public static final SensorView SENSOR_VIEW = new SensorView() {
        @Override
        public Long getId() {
            return SENSOR_ENTITY.getId();
        }

        @Override
        public String getName() {
            return SENSOR_ENTITY.getName();
        }

        @Override
        public Double getCriticalValue() {
            return SENSOR_ENTITY.getCriticalValue();
        }

        @Override
        public MeasureSystem getMeasureSystem() {
            return SENSOR_ENTITY.getMeasureSystem();
        }
    };

    public static final WorkShiftView WORK_SHIFT_VIEW = new WorkShiftView() {
        @Override
        public Long getId() {
            return WORK_SHIFT_ENTITY.getId();
        }

        @Override
        public MachineView getMachine() {
            return MACHINE_VIEW;
        }

        @Override
        public UserView getWorker() {
            return USER_VIEW;
        }

        @Override
        public LocalDateTime getStartedAt() {
            return null;
        }

        @Override
        public LocalDateTime getEndedIn() {
            return null;
        }
    };


}
