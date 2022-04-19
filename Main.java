import controllers.Game;

public class Main 
{
    public static void main(String[] args)
    {
        Game game=new Game();
        game.run();  
        System.out.println(Game.terrainInfo.get("Ocean"));

    }
    
       
}
