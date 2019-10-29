package pw.react.backend.reactbackend.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import pw.react.backend.reactbackend.ReactBackendApplication;
import pw.react.backend.reactbackend.models.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReactBackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testGetAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
                HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response.getBody());
    }
    @Test
    public void testGetUserById() {
        User user = restTemplate.getForObject(getRootUrl() + "/users/1", User.class);
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetUserByLogin() {
        User user = restTemplate.getForObject(getRootUrl() + "/searchbylogin/martaKing", User.class);
        Assert.assertNotNull(user);
    }

    @Test
    public void testUpdatePost() {
        int id = 1;
        User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        user.setFirstName("Marticka");
        user.setLastName("Kingova");
        restTemplate.put(getRootUrl() + "/users/" + id, user);
        User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        Assert.assertNotNull(updatedUser);
    }
    @Test
    public void testDeletePost() {
        int id = 2;
        User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        Assert.assertNotNull(user);
        restTemplate.delete(getRootUrl() + "/users/" + id);
        try {
            user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}