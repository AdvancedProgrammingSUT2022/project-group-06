package views;

import java.util.Scanner;

public class Menu 
{

    public void run(Scanner scanner)
    {
        String command=new String("");
        while(true)
        {
            command=scanner.nextLine();
                
            if(checkCommand(command,scanner))
            {
                break;
            }
                
                
        }
    }

    public boolean checkCommand(String command,Scanner scannner)
    {
        return false;
    }
   
}
