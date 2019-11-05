package pw.react.backend.reactbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.react.backend.reactbackend.models.User;

import javax.transaction.Transactional;

@org.springframework.stereotype.Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
        User findByLogin(String login);
}
