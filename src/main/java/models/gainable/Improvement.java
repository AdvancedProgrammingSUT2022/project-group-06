package models.gainable;

import controllers.GameController;
import models.maprelated.Hex;
import models.units.Unit;
import models.units.Worker;

public class Improvement implements Construction {
    private int leftTurns;
    private int defaultLeftTurn;
    private String name;
    private Hex hex;
    private Unit worker;
    public Improvement(String name,Unit unit,Hex hex)
    {
        this.name=name;
        this.hex=hex;
        this.worker=unit;
    }
    public int getDefaultLeftTurn()
    {
        return defaultLeftTurn;
    }
    public void setDefaultLeftTurn(int amount)
    {
        defaultLeftTurn=amount;

    }

  
    public Unit getWorker()
    {
        return worker;
    }
    @Override
    public void setLeftTurns(int leftTurns) {
        this.leftTurns = leftTurns;
        defaultLeftTurn=leftTurns;
    }

    @Override
    public int getLeftTurns() {
        return this.leftTurns;
    }

    @Override
    public void decreaseLeftTurns() {
        this.leftTurns -= 1;
    }

    @Override
    public void build(String type) {
        zeroMpWorker();
        String temp = "the process of " + "making a "+name+" improvement" + " on the hex: x=" + hex.getX() + " y=" + hex.getY() + " finished successfullly";
        GameController.getCurrentPlayer().addNotifications(temp);
        GameController.getCurrentPlayer().setNotificationsTurns(GameController.getTurn());
        switch(name)
        {   case "remove jungle":
            case "remove forest":
            case "remove marsh":
                deleteFeature();
                break;
            case "remove road":
                removeRoad();
                break;
            case "repair":
                repair();
                break;
            case "Farm":
                makeFarm();
                break;
            case "Mine":
                makeMine();
                break;
            case "Post":
                makePost();
                break;
            case "Pasture":
                makePasture();
                break;
            case "Lumber":
                makeLumber();
                break;
            case "Camp":
                makeCamp();
                break;
            case "Plantation":
                makePlantation();
                break;
            case "Quarry":
                makeQuarry();
                break;
            case "Factory":
                makeFactory();;
                break;


        }
    }

    public static void reverseCamp(Hex hex)
    {
        if(hex.getResource()!=null&&hex.getResource().getName().matches("Furs||Ivory"))
        {
            hex.getCity().decreaseGold(hex.getResource().getGold());
        }
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Deer"))
        {
            hex.getCity().decreaseFood(hex.getResource().getFood());
        }
    }
    private void makeCamp()
    {
        hex.addImprovement(this);
        if(hex.getResource()!=null&&hex.getResource().getName().matches("Furs||Ivory"))
        {
            hex.getCity().increaseGold(hex.getResource().getGold());
        }
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Deer"))
        {
            hex.getCity().increaseFood(hex.getResource().getFood());
        }
    }
    
    private void makePost()
    {
        hex.getCity().increaseGold(1);
        hex.addImprovement(this);

    }

    private void repair() {
        hex.setPillaged(false);
    }

    private void removeRoad() {
        hex.setHasRailRoad(false);
        hex.setHasRoad(false);
    }

    private void deleteFeature() {
        hex.setFeature(null);
    }

    public static void reverseFarm(Hex hex)
    {
        hex.getCity().decreaseFood(1);
        
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Wheat"))
        {
            hex.getCity().decreaseFood(hex.getResource().getFood());
        }

    }
    private void makeFarm()
    {
        hex.getCity().increaseFood(1);
        hex.addImprovement(this);
        if(hex.getFeature().getName().matches("Marsh||Jungle||Forest"))
        {
            hex.setFeature(null);
        }
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Wheat"))
        {
            hex.getCity().increaseFood(hex.getResource().getFood());
        }


    }

    public static void reverseMine(Hex hex)
    {
        hex.getCity().decreaseProduction(1);
        
        if(hex.getResource()!=null)
        {
            if(hex.getResource().getName().matches("Gems||Gold||Silver"))
            {
                hex.getCity().decreaseGold(hex.getResource().getGold());
            }
            if(hex.getResource().getName().matches("Iron||Coal"))
            {
                hex.getCity().decreaseProduction(hex.getResource().getProduction());
            }
        }
    }
    private void makeMine()
    {
        hex.getCity().increaseProduction(1);
        hex.addImprovement(this);
        if(hex.getFeature().getName().matches("Marsh||Jungle||Forest"))
        {
            hex.setFeature(null);
        }

        if(hex.getResource()!=null)
        {
            if(hex.getResource().getName().matches("Gems||Gold||Silver"))
            {
                hex.getCity().increaseGold(hex.getResource().getGold());
            }
            if(hex.getResource().getName().matches("Iron||Coal"))
            {
                hex.getCity().increaseProduction(hex.getResource().getProduction());
            }
        }


    }

    private void makeLumber()
    {
        hex.getCity().increaseProduction(1);
        hex.addImprovement(this);

    }

    public static void reversePasture(Hex hex)
    {
        if(hex.getResource()!=null)
        {
            if(hex.getResource().getName().equals("Horses"))
            {
                hex.getCity().decreaseProduction(hex.getResource().getProduction());
            }
            if(hex.getResource().getName().equals("Sheep"))
            {
                hex.getCity().decreaseFood(hex.getResource().getFood());
            }
        }
    }
    private void makePasture()
    {
        hex.addImprovement(this);
        if(hex.getResource()!=null)
        {
            if(hex.getResource().getName().equals("Horses"))
            {
                hex.getCity().increaseProduction(hex.getResource().getProduction());
            }
            if(hex.getResource().getName().equals("Sheep"))
            {
                hex.getCity().increaseFood(hex.getResource().getFood());
            }
        }
    }

    public static void reversePlantation(Hex hex)
    {
        
        if(hex.getResource()!=null)
        {
            String resource=hex.getResource().getName();
            if(resource.equals("Banana"))
            {
                hex.getCity().decreaseFood(hex.getResource().getFood());
            }
            if(resource.matches("Silk||Sugar||Cotton||Incense||Dyes"))
            {
                hex.getCity().decreaseGold(hex.getResource().getGold());
            }
        }
    }

    private void makePlantation()
    {
        hex.addImprovement(this);
        if(hex.getResource()!=null)
        {
            String resource=hex.getResource().getName();
            if(resource.equals("Banana"))
            {
                hex.getCity().increaseFood(hex.getResource().getFood());
            }
            if(resource.matches("Silk||Sugar||Cotton||Incense||Dyes"))
            {
                hex.getCity().increaseGold(hex.getResource().getGold());
            }
        }
    }

    public static void reverseQuarry(Hex hex)
    {
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Marble"))
        {
            hex.getCity().decreaseGold(hex.getResource().getGold());
        }
    }

    private void makeQuarry()
    {
        hex.addImprovement(this);

        if(hex.getResource()!=null&&hex.getResource().getName().equals("Marble"))
        {
            hex.getCity().increaseGold(hex.getResource().getGold());
        }
    }
    private void makeFactory()
    {
        hex.addImprovement(this);
        hex.getCity().increaseProduction(2);
    }

    private void makeRoad() {
        hex.setHasRoad(true);
    }
   @Override
    public Hex getHex()
    {
        return hex;
    }
    @Override
    public String getName()
    {
        return name;
    }
    public void setHex(Hex newHex)
    {
        this.hex=newHex;
    }
    @Override
    public void zeroMpWorker() {
        worker.setMP(0);
    }



}
