package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.measure.dto.MeasureDto;
import ua.ms.entity.measure.dto.view.MeasureView;
import ua.ms.service.MeasureService;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.mapper.Mapper;

import java.util.List;

@RestController
@RequestMapping("/measures")
@RequiredArgsConstructor
@Tag(name = "Measure entity controller")
public class MeasureController {
    private final MeasureService measureService;
    private final Mapper<Measure, MeasureDto> mapper;

    @GetMapping("/{id}")
    public List<MeasureView> getBySensorId(@PathVariable("id") long id) {
        return measureService.findBySensorId(id, MeasureView.class);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public MeasureDto create(@RequestBody @Valid MeasureDto measureDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new EntityValidationException(bindingResult);
        System.out.println(measureDto);
        Measure createdMeasure = measureService.create(mapper.toEntity(measureDto));
        return mapper.toDto(createdMeasure);
    }

    @DeleteMapping("/{id}")
    public List<MeasureDto> delete(@PathVariable("id") long id) {
        return listOfMeasuresToDto(measureService.delete(id));
    }

    private List<MeasureDto> listOfMeasuresToDto(List<Measure> measures){
        return measures.stream().map(mapper::toDto).toList();
    }
}
