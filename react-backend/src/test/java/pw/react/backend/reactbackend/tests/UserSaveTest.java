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

import java.util.Date;

@RunWith(SpringRunner.class)
public class UserSaveTest {

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user1 = new User("peter", "Alex","Hard", new Date(1998,1,20),true, null);
        user1.setId(1L);
        userRepository.save(user1);
        User user2 = new User("alec", "Marek","Lak", new Date(1998,1,20),true, null);
        user2.setId(2L);
        userRepository.save(user2);
        User user3 = new User("kolot", "Kolo","Mart", new Date(1998,1,20),true, null);
        user3.setId(3L);
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
        User found = userRepository.findById(1L).orElseGet(User::new);
        Assertions.assertThat(found.getId()).isEqualTo(1);

        User found2 = userRepository.findById(2L).orElseGet(User::new);
        Assertions.assertThat(found2.getId()).isEqualTo(2);

        User found3 = userRepository.findById(3L).orElseGet(User::new);
        Assertions.assertThat(found3.getId()).isEqualTo(3);
    }
}
