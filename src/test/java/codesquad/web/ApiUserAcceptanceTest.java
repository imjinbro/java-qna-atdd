package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import support.test.AcceptanceTest;

import java.util.Arrays;

public class ApiUserAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);

    @Test
    public void create() throws Exception {
        UserDto newUser = createUserDto("testuser1");
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();  
        
        UserDto dbUser = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
        assertThat(dbUser, is(newUser));
    }

    private String createUserName(int length) {
        char[] name = new char[length];
        Arrays.fill(name, '*');
        return String.valueOf(name);
    }

    @Test
    public void create_invalid_request_body_min() throws Exception {
        UserDto newUser = createUserDto(createUserName(2));
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void create_invalid_request_body_max() throws Exception {
        UserDto newUser = createUserDto(createUserName(21));
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void show() throws Exception {
        UserDto newUser = createUserDto("colin");
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();
        log.debug("redirect location(hateoas) : {}", location);

        response = basicAuthTemplate(newUser.toUser()).getForEntity(location, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("user info : {}", response.getBody());
    }

    @Test
    public void show_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser2");
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();

        response = basicAuthTemplate(defaultUser()).getForEntity(location, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private UserDto createUserDto(String userId) {
        return new UserDto(userId, "password", "name", "javajigi@slipp.net");
    }

    @Test
    public void update() throws Exception {
        UserDto newUser = createUserDto("testuser3");
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath();  
        
        User loginUser = findByUserId(newUser.getUserId());
        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2", "javajigi@slipp.net2");
        basicAuthTemplate(loginUser).put(location, updateUser);
        
        UserDto dbUser = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
        assertThat(dbUser, is(updateUser));
    }
    
    @Test
    public void update_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser4");
        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        String location = response.getHeaders().getLocation().getPath(); 
        
        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2", "javajigi@slipp.net2");
        basicAuthTemplate(defaultUser()).put(location, updateUser);
        
        UserDto dbUser = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
        assertThat(dbUser, is(newUser));
    }
}
