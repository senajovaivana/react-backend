package pw.react.backend.reactbackend.tests;

import org.junit.Assert;
import org.junit.Before;
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
    public void  testGetAllUsers() {
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
                HttpMethod.GET, getEntity(), String.class);
        Assert.assertNotNull(response.getBody());
    }

    private HttpEntity<String> getEntity() {
        HttpEntity<String> entity = new HttpEntity<String>(null, getHeaders());
        return entity;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("skorupa-header", "");
        return headers;
    }
    @Test
    public void testGetUserById() {
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users/1", HttpMethod.GET,
                getEntity(), String.class);
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserByLogin() {
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users/login/martaKing", HttpMethod.GET,
                getEntity(), String.class);
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testUpdatePost() {
        int id = 1;
        User user = restTemplate.exchange(getRootUrl() + "/users/" + id, HttpMethod.GET, getEntity(), User.class).getBody();
        user.setFirstName("Marticka");
        user.setLastName("Kingova");
        HttpEntity<User> requestUpdate = new HttpEntity<>(user, getHeaders());
        restTemplate.exchange(getRootUrl() + "/users/" + id, HttpMethod.PUT, requestUpdate, Void.class);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users/" + id, HttpMethod.GET,
                getEntity(), String.class);
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testDeletePost() {
        int id = 2;
        User user = restTemplate.exchange(getRootUrl() + "/users/" + id, HttpMethod.GET, getEntity(), User.class).getBody();
        Assert.assertNotNull(user);
        restTemplate.delete(getRootUrl() + "/users/" + id);
        try {
            user = restTemplate.exchange(getRootUrl() + "/users/" + id, HttpMethod.GET, getEntity(), User.class).getBody();
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}