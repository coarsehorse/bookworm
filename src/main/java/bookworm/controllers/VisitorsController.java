package bookworm.controllers;

import bookworm.models.Visitor;
import bookworm.repositories.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VisitorsController {

    @Autowired
    private VisitorRepository visitorRepository;

    @GetMapping("/visitors")
    public List<Visitor> getVisitors() {
        return visitorRepository.findAll();
    }

    @PostMapping("/visitors")
    public List<Visitor> addVisitors(@Valid @RequestBody List<Visitor> visitors) {
        return visitorRepository.saveAll(visitors.stream()
                .peek(visitor -> visitor.setFullNameLowC(visitor
                        .getFullName()
                        .toLowerCase()
                        .trim()
                        .replaceAll("\\s+", "_")))
                .collect(Collectors.toList()));
    }
}
