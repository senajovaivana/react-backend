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
import pw.react.backend.reactbackend.services.UserRequestsService;

import java.util.Date;

@RunWith(SpringRunner.class)
public class UserRequestsServiceTest {

    @TestConfiguration
    static class UserRequestsServiceTestContextConfiguration {

        @Bean
        public UserRequestsService userRequestsService() {
            return new UserRequestsService();
        }
    }

    @Autowired
    private UserRequestsService userRequestsService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user1 = new User("peter", "Alex","Hard", new Date(1998,1,20),true);
        user1.setId((long)1);
        userRepository.save(user1);
        User user2 = new User("alec", "Marek","Lak", new Date(1998,1,20),true);
        user2.setId((long)2);
        userRepository.save(user2);
        User user3 = new User("kolot", "Kolo","Mart", new Date(1998,1,20),true);
        user3.setId((long)3);
        userRepository.save(user3);

        Mockito.when(userRepository.findById(user1.getId()))
                .thenReturn(java.util.Optional.of(user1));
        Mockito.when(userRepository.findById(user2.getId()))
                .thenReturn(java.util.Optional.of(user2));
        Mockito.when(userRepository.findById(user3.getId()))
                .thenReturn(java.util.Optional.of(user3));
    }

    @Test
    public void testOfFindingAllUsers() {
        User found = userRequestsService.findUserById(1);
        Assertions.assertThat(found.getId()).isEqualTo(1);

        User found2 = userRequestsService.findUserById(2);
        Assertions.assertThat(found2.getId()).isEqualTo(2);

        User found3 = userRequestsService.findUserById(3);
        Assertions.assertThat(found3.getId()).isEqualTo(3);
    }
}
