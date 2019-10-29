package pw.react.backend.reactbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class LoginCheckService implements ILoginCheckService {
    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public User registerNewUserAccount(User userAccount) throws ResourceNotFoundException {
        if (loginExist(userAccount.getLogin())) {
            throw new ResourceNotFoundException(
                    "There is an account with that email address: " + userAccount.getLogin());
        }
        return repository.save(userAccount);
    }

    @Override
    public boolean loginExist(String login) {
        return getUserByLogin(login) != null;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = repository.findByLogin(login);
        return user;
    }
}