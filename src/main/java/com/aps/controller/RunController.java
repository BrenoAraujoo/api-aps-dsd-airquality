package com.aps.controller;

import com.aps.model.Run;
import com.aps.repository.RunRepository;
import com.aps.service.RunService;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/races")
public class RunController {

    @Autowired
    private RunRepository runRepository;

    @Autowired
    private RunService runService;

    @GetMapping
    public List<Run> listaAll(){
        return runRepository.findAll();
    }

    @GetMapping("/{id}")
    public Run findById(@PathVariable Long id){
        return runService.findOrFail(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Run save(@RequestBody Run run){
        return runService.save(run);
    }

    public Run update(@RequestBody Run run, @PathVariable Long id){
        var actualRun = runService.findOrFail(id);
        BeanUtils.copyProperties(run, actualRun,"id");
        return runService.save(actualRun);

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        runService.remove(id);
    }
}
