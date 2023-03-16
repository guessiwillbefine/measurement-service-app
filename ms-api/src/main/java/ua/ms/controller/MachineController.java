package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.Machine;
import ua.ms.entity.MachineType;
import ua.ms.entity.dto.MachineDto;
import ua.ms.entity.dto.view.MachineView;
import ua.ms.service.MachineService;
import ua.ms.util.exception.MachineNotFoundException;
import ua.ms.util.exception.MachineValidationException;
import ua.ms.util.mapper.Mapper;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
@Tag(name = "Machine entity controller")
public class MachineController {
    private final MachineService machineService;
    private final Mapper<Machine, MachineDto> mapper;

    @GetMapping("/{id}")
    public MachineView findById(@PathVariable Long id) {
        Optional<MachineView> byId = machineService.findById(id, MachineView.class);
        if (byId.isPresent()) return byId.get();
        throw new MachineNotFoundException(format("Machine with id[%d] not found", id));
    }

    @GetMapping("/types")
    public MachineType[] getAllTypes() {
        return MachineType.values();
    }

    @GetMapping("/all") //todo to modify method to include section in search param
    public List<MachineView> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return machineService.findAll(PageRequest.of(page, size), MachineView.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MachineDto save(@RequestBody @Valid MachineDto machineDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MachineValidationException(bindingResult.getAllErrors().toString());
        }
        return mapper.toDto(machineService.save(machineDto));
    }

    @PatchMapping("/{id}")
    public MachineDto update(@PathVariable Long id,
                             @RequestBody @Valid MachineDto machineDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new MachineValidationException(bindingResult.getAllErrors().toString());
        return machineService.update(id, machineDto);
    }

    @DeleteMapping("/{id}")
    public MachineDto delete(@PathVariable Long id) {
        return machineService.delete(id);
    }
}