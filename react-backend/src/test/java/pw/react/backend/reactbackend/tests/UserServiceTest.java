package pw.react.backend.reactbackend.tests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.UserService;

import java.util.Date;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User("peter", "Alex","Hard", new Date(1998,1,20),true, null);
        user.setId(1L);
        Mockito.when(userRepository.findByLogin(user.getLogin()))
                .thenReturn(user);
    }

    @Test
    public void testSearchingUserByLogin() {
        String login = "peter";
        User found = userRepository.findByLogin(login);

        Assertions.assertThat(found.getLogin()).isEqualTo(login);
    }


}
