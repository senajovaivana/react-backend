package pw.react.backend.reactbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.react.backend.reactbackend.exceptions.UnauthorizedException;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserService loginCheckService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/inicialize", headers = "skorupa-header")
    public ResponseEntity<String> inicialize(@RequestHeader("skorupa-header") String param) throws IOException {
        User u1 = new User("loginPeter", "peter", "kral", new Date(1996, 2, 10));
        loginCheckService.registerNewUserAccount(setImageToUser(u1, "images/user.png"));
        User u2 = new User("maria2", "maria", "long", new Date(1996,2,10));
        loginCheckService.registerNewUserAccount(setImageToUser(u2, "images/icons8-user-50.png"));
        User u3 = new User("loginEva", "eva", "keat", new Date(1996,2,10));
        loginCheckService.registerNewUserAccount(setImageToUser(u3, "images/user.png"));
        return  ResponseEntity.ok().body("Users are created");
    }

    @GetMapping(value = "/inicialize")
    public ResponseEntity<String> inicializeWithoutHeader(){
        throw new UnauthorizedException("You are not authorized to inicialize users. Missing header.");
    }

    @PostMapping(value = "", headers = "skorupa-header")
    public void createUser(@RequestHeader("skorupa-header") @Valid @RequestBody User user) throws IOException {
        new ResponseEntity<>(loginCheckService.registerNewUserAccount(user), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public void createUserWithoutHeader(@Valid @RequestBody User user) {
        throw new UnauthorizedException("You are not authorized to create new user. Missing header.");
    }

    @GetMapping(value = "", headers = "skorupa-header")
    public ResponseEntity<List<User>> findAll(@RequestHeader("skorupa-header") List<User> u) {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<User>> findAllWithoutHeader() {
        throw new UnauthorizedException("You are not authorized to fetch all users. Missing header.");
    }

    @RequestMapping(value = "/{id}", headers = "skorupa-header",  method = RequestMethod.GET)
    public ResponseEntity<User> search(@RequestHeader("skorupa-header") User user,
                                       @PathVariable Long id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id)) {
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            throw new ResourceNotFoundException("User with id " + id + " does not exist");
        }
        User u = userRepository.findById(id).orElseGet(User::new);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> searchWithoutHeader() throws ResourceNotFoundException {
        throw new UnauthorizedException("You are not authorized to fetch user by id. Missing header.");
    }

    @RequestMapping(value = "/login/{login}", headers = "skorupa-header", method = RequestMethod.GET)
    public ResponseEntity<User> fetchDataByLogin(@RequestHeader("skorupa-header") User user,
                                                 @PathVariable String login) throws ResourceNotFoundException {
        if (!loginCheckService.loginExist(login)) {
            throw new ResourceNotFoundException(
                    "User with the login - " + login + " does not exist.");
        }
        return new ResponseEntity<>(userRepository.findByLogin(login), HttpStatus.OK);
    }

    @RequestMapping(value = "/login/{login}", method = RequestMethod.GET)
    public ResponseEntity<User> fetchDataByLoginWithoutHeader(@PathVariable String login) {
        throw new UnauthorizedException("You are not authorized to fetch user by login. Missing header.");
    }

    @DeleteMapping(value = "/{id}", headers = "skorupa-header")
    public ResponseEntity<String> delete(@RequestHeader("skorupa-header") String param,
                                         @PathVariable Long id) throws ResourceNotFoundException {
        if (!userRepository.existsById(id))
            return new ResponseEntity<>("User with id " + id + " does not exist", HttpStatus.NOT_FOUND);
        deleteUser(id);
        return new ResponseEntity<>("User was deleted", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteWithoutHeader(@PathVariable Long id) {
        throw new UnauthorizedException("You are not authorized to delete user. Missing header.");
    }

    @PostMapping(value = "/update", headers = "skorupa-header")
    public ResponseEntity<String> update(@RequestHeader("skorupa-header") String param,
                                         @RequestBody User user) throws IOException {
        long id = user.getId();
        if (!userRepository.existsById(id))
            return new ResponseEntity<>("User with id " + id + " does not exist", HttpStatus.NOT_FOUND);
        deleteUser(id);
        loginCheckService.registerNewUserAccount(user);
        return new ResponseEntity<>("User was updated", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<String> updateWithoutHeader(@RequestBody User user) {
        throw new UnauthorizedException("You are not authorized to update user. Missing header.");
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    public void showImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        if (!userRepository.existsById(id))
            response.setStatus(HttpStatus.NOT_FOUND.value());
        User user = userRepository.findById(id).orElseGet(User::new);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(user.getPic());
        response.getOutputStream().close();
    }

    private void deleteUser(Long id) throws ResourceNotFoundException {
        userRepository.deleteById(id);
    }

    private User setImageToUser(User user, String pathToImage) throws IOException {
        ClassPathResource backImgFile = new ClassPathResource(pathToImage);
        byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
        backImgFile.getInputStream().read(arrayPic);
        user.setPic(arrayPic);
        return user;
    }
}