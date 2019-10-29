package pw.react.backend.reactbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.interfaces.IUserService;

import java.util.List;

@Service
public class UserRequestsService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User findUserById(long id) throws ResourceNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User with the ID - " + id + " does not exist."));
    }

    @Override
    public User findUserByLogin(String login) throws ResourceNotFoundException {
        return repository.findByLogin(login);
    }

    @Override
    public void deleteUserById(long id) throws ResourceNotFoundException {
        User u = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "User for deleting with ID - " + id + " does not exist."));
        repository.delete(u);
    }
}
