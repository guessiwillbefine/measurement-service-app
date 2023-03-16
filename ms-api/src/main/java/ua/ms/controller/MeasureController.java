package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.Measure;
import ua.ms.entity.dto.MeasureDto;
import ua.ms.entity.dto.view.MeasureView;
import ua.ms.service.MeasureService;
import ua.ms.util.exception.MeasureValidationException;
import ua.ms.util.mapper.impl.MeasureMapper;

import java.util.List;

@RestController
@RequestMapping("/measures")
@RequiredArgsConstructor
@Tag(name = "Measure entity controller")
public class MeasureController {
    private final MeasureService measureService;
    private final MeasureMapper measureMapper;

    @GetMapping("/{id}")
    public List<MeasureView> getBySensorId(@PathVariable("id") long id) {
        return measureService.findBySensorId(id, MeasureView.class);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public MeasureDto create(@RequestBody @Valid MeasureDto measureDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new MeasureValidationException(bindingResult.getAllErrors().toString());

        Measure createdMeasure = measureService.create(measureMapper.toEntity(measureDto));
        return measureMapper.toDto(createdMeasure);
    }

    @DeleteMapping("/{id}")
    public List<MeasureDto> delete(@PathVariable("id") long id) {
        return listOfMeasuresToDto(measureService.delete(id));
    }

    private List<MeasureDto> listOfMeasuresToDto(List<Measure> measures){
        return measures.stream().map(measureMapper::toDto).toList();
    }
}
