package com.netfly.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/api/projects")
public class ProjectController {

    private final ProjectRepository repository;

    @Autowired
    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Project post(@RequestBody(required = false) Project project) {
        verifyCorrectPayload(project);

        return repository.save(project);
    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project get(@PathVariable("id") Integer id) {
        verifyProjectExists(id);

        return repository.findOne(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Project put(@PathVariable("id") Integer id, @RequestBody(required = false) Project project) {
        verifyProjectExists(id);
        verifyCorrectPayload(project);

        project.setId(id);
        return repository.save(project);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        verifyProjectExists(id);

        repository.delete(id);
    }

    private void verifyProjectExists(Integer id) {
        if (!repository.exists(id)) {
            throw new RuntimeException(String.format("Project with id=%d was not found", id));
        }
    }

    private void verifyCorrectPayload(Project project) {
        if (Objects.isNull(project)) {
            throw new RuntimeException("Project cannot be null");
        }

        if (!Objects.isNull(project.getId())) {
            throw new RuntimeException("Id field must be generated");
        }
    }

}
