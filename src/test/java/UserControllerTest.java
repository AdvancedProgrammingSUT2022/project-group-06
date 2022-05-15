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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    static
    User user;

    @Test
    public void testChangePassWhenInvalidPass(){
        UserController.loggedInUser = user;
        when(user.getPassword()).thenReturn("ho");
        String res = UserController.changePassword("hi","hello");
        Assertions.assertEquals("current password is invalid", res);
    }

    @Test
    public void testChangePassWhenSamePass(){
        UserController.loggedInUser = user;
        when(user.getPassword()).thenReturn("hi");
        String res = UserController.changePassword("hi","hi");
        Assertions.assertEquals("current pass is equal to new pass", res);
    }

    @Test
    public void testChangePass(){
        UserController.loggedInUser = user;
        when(user.getPassword()).thenReturn("hi");
        String res = UserController.changePassword("hi","hello");
        Assertions.assertEquals("password changed successfully!", res);
    }
    @Test
    public void changeNickNameErrorTest(){
        UserController.setNicknames(new ArrayList<String>(List.of("hello")));
        UserController.loggedInUser = user;
        String res = UserController.changeNickname("hello");
        Assertions.assertTrue(res.startsWith("user with nickname"));
        user.setNickName(null);
    }
    @Test
    public void changeNickName(){
        UserController.loggedInUser = user;
        String res = UserController.changeNickname("hello");
        verify(user).setNickName("hello");
        Assertions.assertEquals("nickname changed successfully!", res);
    }

    @Test
    public void logoutTest(){
        UserController.loggedInUser = user;
        Assertions.assertEquals("logout successfully", UserController.logout());
        Assertions.assertNull(UserController.loggedInUser);
    }
    @Test
    public void logoutTestWithNoLogin(){
        UserController.loggedInUser = null;
//        System.out.println( UserController.loggedInUser.getNickName());
        Assertions.assertEquals("you have not logged in yet", UserController.logout());
    }
    @Test
    public void createUserTest(){
       /* UserController userController = new UserController();
        userController.setUsers(new HashMap<>());
        userController.s
        userController.createUser("asy","123","asemaneh");
        verify( userController.getNicknames().add("asemaneh"));*/
    }
    public void saveUserTest(){
    }
    public void importSavedUsersTest(){
    }
}
