package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.entity.sensor.dto.view.SensorView;
import ua.ms.service.SensorService;
import ua.ms.util.exception.EntityNotFoundException;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.mapper.Mapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sensors")
@Tag(name = "Sensor entity controller")
public class SensorController {
    private final SensorService sensorService;
    private final Mapper<Sensor,SensorDto> mapper;

    @GetMapping()
    public List<SensorDto> getAll(@RequestParam(value = "page") int page,
                                   @RequestParam(value = "size") int size) {

        Pageable paginationParams = PageRequest.of(page, size);
        return sensorService.findAll(paginationParams, SensorDto.class);
    }

    @GetMapping("/{id}")
    public SensorDto find(@PathVariable("id") long id) {
        Optional<SensorDto> byId = sensorService.findOne(id, SensorDto.class);
        if (byId.isEmpty()) throw new EntityNotFoundException("Sensor with this id is not found");
        return byId.get();
    }

    @PatchMapping("/{id}")
    public SensorDto update(@PathVariable("id") long id,
                            @RequestBody @Valid SensorDto sensorDto,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new EntityValidationException(bindingResult);

        Sensor updatedSensor = sensorService.update(id, sensorDto);
        return mapper.toDto(updatedSensor);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public SensorDto create(@RequestBody @Valid SensorDto sensorDto,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new EntityValidationException(bindingResult);

        Sensor sensorToCreate = mapper.toEntity(sensorDto);
        Sensor createdSensor = sensorService.create(sensorToCreate);
        return mapper.toDto(createdSensor);
    }

    @DeleteMapping("/{id}")
    public SensorDto delete(@PathVariable("id") long id) {
        return mapper.toDto(sensorService.delete(id));
    }
}
