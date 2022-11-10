package com.aps.service;


import com.aps.exceptions.EntityInUseException;
import com.aps.exceptions.EntityNotFoundException;
import com.aps.model.User;
import com.aps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static final String MSG_USER_NOT_FOUND = "User with id %d not found";
    @Autowired
    private UserRepository userRepository;

    public User save(User user){
    return userRepository.save(user);
    }

    public void remove (Long id){
        var user = findOrFail(id);
        try {
        userRepository.deleteById(user.getId());

        }catch (DataIntegrityViolationException e){
            throw new EntityInUseException(
                    String.format("User with id %d can't be removed",id)
            );
        }
    }

    public User findOrFail(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(
                String.format(MSG_USER_NOT_FOUND, id)
        ));
    }
}
