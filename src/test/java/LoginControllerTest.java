import controllers.LoginController;
import controllers.UnitController;
import controllers.UserController;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    @Mock
    UserController userController2;
    @Mock
    HashMap<String, User> users;
    @Mock
    User user;

    @Test
    public void testInvalidCommandOfLogin() {
        LoginController loginController = new LoginController();
        String res = loginController.login("user login -u mastmai");
        Assertions.assertEquals("invalid command!", res);
    }

    @Test
    public void testInvalidLogin() {
        LoginController loginController = new LoginController();
        String res = loginController.login("user login -u mastmai -p 002");
        Assertions.assertEquals("Username and password didn't match!", res);
        UserController.getUsers().clear();
    }

    @Test
    public void testValidLogin() {
        String pass = "002";
        when(user.getPassword()).thenReturn(pass);
        LoginController loginController = new LoginController();
        UserController.getUsers().put("mastmali", user);
        String res = loginController.login("user login -u mastmali -p 002");
        Assertions.assertEquals("user logged in successfully!", res);
    }

    @Test
    public void testInvalidCommandOfCreateUser() {
        LoginController loginController = new LoginController();
        String res = loginController.createUser("user create -u mastmai -p 002");
        Assertions.assertEquals("invalid command!", res);
    }

    @Test
    public void testInvalidCreateUser() {
        UserController.getUsers().put("ali", user);
        LoginController loginController = new LoginController();
        String res = loginController.createUser("user login -u ali -p 002 -n hell");
        Assertions.assertTrue(res.startsWith("user with username "));
        UserController.getUsers().clear();
    }

    @Test
    public void testInvalidCreateUser2() {
        UserController userController = new UserController();
        userController.getNicknames().add("hell");
        LoginController loginController = new LoginController();
        String res = loginController.createUser("user login -u ali -p 002 -n hell");
        Assertions.assertTrue(res.startsWith("user with nickname "));
    }

    @Test
    public void testValidCreateUser() {
        LoginController loginController = new LoginController();
        String res = loginController.createUser("user login -u mastmali -p 002 -n java");
        Assertions.assertEquals("user created successfuly", res);
    }

    @Test
    public void changeMenu() {
        LoginController loginController = new LoginController();
        Assertions.assertFalse(loginController.changeMenu("sfasd"));
        Assertions.assertTrue(loginController.changeMenu("Main Menu"));
    }
}
