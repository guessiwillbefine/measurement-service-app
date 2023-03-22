package ua.ms;

import ua.ms.entity.factory.Factory;
import ua.ms.entity.factory.dto.FactoryDto;
import ua.ms.entity.factory.dto.view.FactoryView;
import ua.ms.entity.machine.Machine;
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

import java.time.LocalDateTime;
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
            .model("ZXC993-EZ").type(MachineType.MANIPULATOR).factory(FACTORY_ENTITY).build();
    public static final Sensor SENSOR_ENTITY = Sensor.builder()
            .id(1L).name("someSensorName")
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
        public List<MeasureView> getMeasures() {
            return Collections.emptyList();
        }

        @Override
        public MeasureSystem getMeasureSystem() {
            return SENSOR_ENTITY.getMeasureSystem();
        }
    };


}
