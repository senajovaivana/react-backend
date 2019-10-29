package pw.react.backend.reactbackend.services.interfaces;

import pw.react.backend.reactbackend.models.User;

import java.util.List;

public interface IUserService {
    List<User> findAllUsers();
    User findUserById(long id);
    User findUserByLogin(String login);
    void deleteUserById(long id);

}
