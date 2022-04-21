package views;

import controllers.InitializeGameInfo;
import controllers.GameController;

import java.util.Scanner;

public class GameMenu  extends Menu {
    public void run(Scanner scanner){
        InitializeGameInfo.run();
        GameController.printWorld();
        String command;
        while(true){
            command = scanner.nextLine();
            if(command.equals("show map")){
                System.out.println(GameController.printWorld());
            }
        }
    }

}
