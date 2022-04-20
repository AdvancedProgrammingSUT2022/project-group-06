import java.util.Scanner;

import controllers.UserController;
import views.LoginMenu;

public class Main 
{
    public static void main(String[] args)
    {
        Scanner scanner=new Scanner(System.in);
        LoginMenu loginMenu=new LoginMenu();
        UserController.importSavedUsers();
        loginMenu.run(scanner);
        
    }
    
       
}
