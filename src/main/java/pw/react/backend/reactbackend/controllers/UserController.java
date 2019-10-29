package pw.react.backend.reactbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.models.UserUI;
import pw.react.backend.reactbackend.services.UserLoginCheckService;
import pw.react.backend.reactbackend.services.UserRequestsService;

import javax.validation.Valid;
import java.sql.Date;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserRequestsService userRequestsService;

    @Autowired
    private UserLoginCheckService loginCheckService;

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

    @GetMapping("/users")
    public List<UserUI> findAll(){
        List<User> users = userRequestsService.findAllUsers();
        List<UserUI> usersUI = new ArrayList<>();
        for (User u : users) {
            usersUI.add(new UserUI(u.getId(), u.getLogin(),u.getFirstName(),u.getLastName(),u.getDateOfBirth(),u.isActive()));
        }
        return usersUI;
    }

    @RequestMapping("/users/{id}")
    public UserUI search(@PathVariable long id) throws ResourceNotFoundException {
        User u = userRequestsService.findUserById(id);
        return new UserUI(u.getId(),u.getLogin(),u.getFirstName(),u.getLastName(),u.getDateOfBirth(),u.isActive());
    }

    @RequestMapping("/searchbylogin/{login}")
    public UserUI fetchDataByLogin(@PathVariable String login) throws ResourceNotFoundException {
        if (!loginCheckService.loginExist(login)) {
            throw new ResourceNotFoundException(
                    "User with the login - " + login + " does not exist.");
        }
        User u = userRequestsService.findUserByLogin(login);
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
        userRequestsService.deleteUserById(id);
    }
}