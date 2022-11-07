package com.aps.controller;

import com.aps.model.User;
import com.aps.repository.UserRepository;
import com.aps.service.UserService;
import java.util.List;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> listAll(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id){
        return userService.findOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id){
        var actualUser = userService.findOrFail(id);
        BeanUtils.copyProperties(user,actualUser,"id");
        return userService.save(actualUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userService.remove(id);
    }

}
