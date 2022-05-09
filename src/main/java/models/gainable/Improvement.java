package models.gainable;

import models.maprelated.Hex;
import models.units.Unit;
import models.units.Worker;

public class Improvement implements Construction {
    private int leftTurns;
    private String name;
    private Hex hex;
    private Unit worker;
    public Improvement(String name,Unit unit,Hex hex)
    {
        this.name=name;
        this.hex=hex;
        this.worker=unit;
    }

    public Unit getWorker()
    {
        return worker;
    }
    @Override
    public void setLeftTurns(int leftTurns) {
        this.leftTurns = leftTurns;
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
    public void build() {
        zeroMpWorker();
        switch(name)
        {
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

    private void makeCamp()
    {
        hex.addImprovement(this);
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Furs||Ivory"))
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
    private void makeFarm()
    {
        hex.getCity().increaseFood(1);
        hex.addImprovement(this);
        if(hex.getFeature().getName().equals("Marsh||Jungle||Forest"))
        {
            hex.setFeature(null);
        }
        if(hex.getResource()!=null&&hex.getResource().getName().equals("Wheat"))
        {
            hex.getCity().increaseFood(hex.getResource().getFood());
        }
        

    }

    private void makeMine()
    {
        hex.getCity().increaseProduction(1);
        hex.addImprovement(this);
        if(hex.getFeature().getName().equals("Marsh||Jungle||Forest"))
        {
            hex.setFeature(null);
        }
       
        if(hex.getResource()!=null)
        {
            if(hex.getResource().getName().equals("Gems||Gold||Silver"))
            {
                hex.getCity().increaseGold(hex.getResource().getGold());
            }
            if(hex.getResource().getName().equals("Iron||Coal"))
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
            if(resource.equals("Silk||Sugar||Cotton||Incense||Dyes"))
            {
                hex.getCity().increaseGold(hex.getResource().getGold());
            }
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
        worker.setBackUpMp(worker.getMP());
        worker.setMP(0);
        
    }

    
 
}
