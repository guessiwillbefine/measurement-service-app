package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.work_shift.WorkShift;
import ua.ms.entity.work_shift.dto.WorkShiftDto;
import ua.ms.entity.work_shift.dto.view.WorkShiftView;
import ua.ms.service.WorkShiftService;
import ua.ms.util.exception.EntityValidationException;
import ua.ms.util.mapper.impl.WorkShiftMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/work_shifts")
@Tag(name = "work-shift entity controller")
public class WorkShiftController {
    private final WorkShiftService workShiftService;
    private final WorkShiftMapper mapper;

    @GetMapping()
    public List<WorkShiftView> findAll(@RequestParam("page") int page,
                                       @RequestParam("size") int size) {
        Pageable paginationParams = PageRequest.of(page, size);
        return workShiftService.getAll(paginationParams, WorkShiftView.class);
    }

    @GetMapping("/worker/{id}")
    public List<WorkShiftView> findByWorker(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @PathVariable("id") Long id) {
        Pageable paginationParams = PageRequest.of(page, size);
        return workShiftService.getAllByWorker(id, paginationParams, WorkShiftView.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public WorkShiftDto create(@RequestBody @Valid WorkShiftDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new EntityValidationException(bindingResult);

        WorkShift workShift = mapper.toEntity(dto);
        WorkShift createdWorkShift = workShiftService.save(workShift);
        return mapper.toDto(createdWorkShift);
    }

    @PatchMapping()
    public WorkShiftDto update(@RequestBody @Valid WorkShiftDto dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            throw new EntityValidationException(bindingResult);

        WorkShift workShift = mapper.toEntity(dto);
        WorkShift updatedWorkShift = workShiftService.update(workShift);
        return mapper.toDto(updatedWorkShift);
    }

    @DeleteMapping("/{id}")
    public WorkShiftDto delete(@PathVariable("id") Long id) {
        WorkShift deletedWorkShift = workShiftService.delete(id);
        return mapper.toDto(deletedWorkShift);
    }
}
