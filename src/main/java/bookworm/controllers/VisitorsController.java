package bookworm.controllers;

import bookworm.models.Visitor;
import bookworm.repositories.VisitorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
