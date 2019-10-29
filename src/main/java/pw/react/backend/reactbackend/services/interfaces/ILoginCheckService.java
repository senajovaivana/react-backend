package pw.react.backend.reactbackend.services.interfaces;

import pw.react.backend.reactbackend.models.User;

public interface ILoginCheckService {
    User registerNewUserAccount(User userAccount);
    boolean loginExist(String login);
    User getUserByLogin(String login);
}
