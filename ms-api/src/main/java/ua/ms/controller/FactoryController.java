package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ms.entity.dto.view.FactoryView;
import ua.ms.service.FactoryService;
import ua.ms.util.exception.FactoryNotFoundException;

import java.util.Optional;

@RestController
@RequestMapping("/factories")
@RequiredArgsConstructor
@Tag(name = "Factory entity controller")
public class FactoryController {
    private final FactoryService factoryService;

    @GetMapping("/{id}")
    public FactoryView findById(@PathVariable long id) {
        Optional<FactoryView> byId = factoryService.findById(id, FactoryView.class);
        if (byId.isEmpty()) throw new FactoryNotFoundException("Factory with id[%d] wasn't found");
        return byId.get();
    }
}
