package pw.react.backend.reactbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.interfaces.IUserService;
import sun.misc.IOUtils;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public User registerNewUserAccount(User userAccount) throws ResourceNotFoundException, IOException {
        if (loginExist(userAccount.getLogin())) {
            throw new ResourceNotFoundException(
                    "There is already an account with that email address: " + userAccount.getLogin());
        }
        return repository.save(userAccount);
    }

    @Override
    public boolean loginExist(String login) {
        return repository.findByLogin(login) != null;
    }
}