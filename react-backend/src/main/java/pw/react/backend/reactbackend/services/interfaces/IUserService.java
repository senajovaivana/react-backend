package pw.react.backend.reactbackend.services.interfaces;

import pw.react.backend.reactbackend.models.User;

import java.io.IOException;

public interface IUserService {
    User registerNewUserAccount(User userAccount) throws IOException;
    boolean loginExist(String login);
}
