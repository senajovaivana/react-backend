package pw.react.backend.reactbackend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@EnableJpaRepositories(basePackages = {"pw.react.backend.reactbackend.repository"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactBackendApplicationTests {

	@Test
	public void contextLoads() {

	}

}
