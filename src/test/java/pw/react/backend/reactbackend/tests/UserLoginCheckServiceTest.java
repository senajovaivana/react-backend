package pw.react.backend.reactbackend.tests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pw.react.backend.reactbackend.models.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.UserLoginCheckService;

import java.util.Date;

@RunWith(SpringRunner.class)
public class UserLoginCheckServiceTest {

    @TestConfiguration
    static class UserLoginCheckServiceTestContextConfiguration {

        @Bean
        public UserLoginCheckService userLoginCheckService() {
            return new UserLoginCheckService();
        }
    }

    @Autowired
    private UserLoginCheckService userLoginCheckService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User("peter", "Alex","Hard", new Date(1998,1,20),true);

        Mockito.when(userRepository.findByLogin(user.getLogin()))
                .thenReturn(user);
    }

    @Test
    public void whenValidLogin_thenUserShouldBeFound() {
        String login = "peter";
        User found = userLoginCheckService.getUserByLogin(login);

        Assertions.assertThat(found.getLogin()).isEqualTo(login);
    }
}
