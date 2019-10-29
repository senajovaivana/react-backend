package pw.react.backend.reactbackend.unitTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.LoginCheckService;

import java.util.Date;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginCheckService userService;

    // write test cases here
    @Before
    public void setUp() {
        User user = new User("alex2", "Alex","Hard", new Date(1998,1,20),true);

        Mockito.when(userRepository.findByLogin(user.getLogin()))
                .thenReturn(user);
    }

    @Test
    public void whenValidLogin_thenUserShouldBeFound() {
        String login = "alex2";
        User found = userService.getUserByLogin(login);

        //assertThat(found.getLogin())
             //   .isEqualTo(login);
    }
}
