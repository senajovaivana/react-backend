package pw.react.backend.reactbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.models.UserUI;
import pw.react.backend.reactbackend.services.LoginCheckService;

import javax.validation.Valid;
import java.sql.Date;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private LoginCheckService loginCheckService;

    @GetMapping("/inicialize")
    public String inicialize(){
     createUser(new User("loginPeter", "peter", "kral", new Date(1996,2,10)));
     createUser(new User("maria2", "maria", "long", new Date(1996,2,10)));
     createUser(new User("loginEva", "eva", "keat", new Date(1996,2,10)));
     //createUser(new User("loginEva", "eva", "keat", new Date(1996,2,10)));
        return "Users are created";
    }

    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user) {
        loginCheckService.registerNewUserAccount(user);
    }

    @GetMapping("/findall")
    public List<UserUI> findAll(){
        List<User> users = repository.findAll();
        List<UserUI> usersUI = new ArrayList<>();
        for (User u : users) {
            usersUI.add(new UserUI(u.getId(), u.getLogin(),u.getFirstName(),u.getLastName(),u.getDateOfBirth(),u.isActive()));
        }
        return usersUI;
    }

    @RequestMapping("/search/{id}")
    public UserUI search(@PathVariable long id) throws ResourceNotFoundException {
        User u = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with the ID - " + id + " does not exist."));
        return new UserUI(u.getId(),u.getLogin(),u.getFirstName(),u.getLastName(),u.getDateOfBirth(),u.isActive());
    }

    @RequestMapping("/searchbylogin/{login}")
    public UserUI fetchDataByLogin(@PathVariable String login) throws ResourceNotFoundException {
        if (!loginCheckService.loginExist(login)) {
            throw new ResourceNotFoundException(
                    "User with the login - " + login + " does not exist.");
        }
        User u = repository.findByLogin(login);
        return new UserUI(u.getId(),u.getLogin(),u.getFirstName(),u.getLastName(),u.getDateOfBirth(),u.isActive());
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable long id) throws ResourceNotFoundException {
        deleteUser(id);
        return "User was deleted";
    }

    @PostMapping("/update")
    public String update(@RequestBody User user){
        deleteUser(user.getId());
        createUser(user);
        return "User was updated";
    }

    private void deleteUser(long id) throws ResourceNotFoundException {
        User u = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User for deleting with ID - " + id + " does not exist."));
        repository.delete(u);
    }
}