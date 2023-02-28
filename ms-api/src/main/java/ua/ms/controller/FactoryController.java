package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.Factory;
import ua.ms.entity.dto.FactoryDto;
import ua.ms.entity.dto.view.FactoryView;
import ua.ms.service.FactoryService;
import ua.ms.util.exception.FactoryNotFoundException;
import ua.ms.util.exception.FactoryValidationException;
import ua.ms.util.mapper.FactoryMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/factories")
@RequiredArgsConstructor
@Tag(name = "Factory entity controller")
public class FactoryController {
    private final FactoryService factoryService;
    private final FactoryMapper factoryMapper;

    @GetMapping("/{id}")
    public FactoryView findById(@PathVariable long id) {
        Optional<FactoryView> byId = factoryService.findById(id, FactoryView.class);
        if (byId.isEmpty()) throw new FactoryNotFoundException("Factory with id[%d] wasn't found");
        return byId.get();
    }

    @GetMapping("/all")
    public List<FactoryView> findAll() {
        return factoryService.findAll(FactoryView.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FactoryDto create(@Valid @RequestBody FactoryDto factoryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new FactoryValidationException(bindingResult.getAllErrors().toString());
        Factory saved = factoryService.save(factoryDto);
        return factoryMapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    public FactoryDto delete(@PathVariable long id) {
        Factory deletedFactory = factoryService.deleteById(id);
        return factoryMapper.toDto(deletedFactory);
    }

    @PatchMapping("/{id}")
    public FactoryDto update(@PathVariable long id,
                             @Valid @RequestBody FactoryDto factoryDto) {
        Factory updatedFactory = factoryService.update(id, factoryDto);
        return factoryMapper.toDto(updatedFactory);
    }
}
