package com.aps.service;


import com.aps.EntityInUseException;
import com.aps.EntityNotFoundException;
import com.aps.model.Run;
import com.aps.repository.RunRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RunService {

    public static final String MSG_RACE_NOT_FOUND = "Race with id %d not found";
    public static final String MSG_RACE_IN_USE = "Race with id %d can't be removed";
    @Autowired
    private RunRepository runRepository;

    @Autowired
    private UserService userService;

    public Run save(Run run){

        var userId = run.getUser().getId();
        var user = userService.findOrFail(userId);
        BeanUtils.copyProperties(user, run.getUser());
        return runRepository.save(run);
    }

    public void remove(Long id){
        var run = findOrFail(id);
        try {
            runRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format(MSG_RACE_IN_USE,id)
            );
        }
    }

    public Run findOrFail(Long id){
        return runRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                String.format(MSG_RACE_NOT_FOUND,id)
        ));
    }

}
