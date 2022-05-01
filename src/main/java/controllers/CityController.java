package controllers;

import java.util.ArrayList;

import models.Player;
import models.gainable.Technology;
import models.maprelated.City;
import models.maprelated.Hex;
import models.units.Civilian;
import models.units.Military;
import models.units.Settler;
import models.units.Unit;


public class CityController 
{
    private static ArrayList<Hex> toBuyTiles=new ArrayList<Hex>();

  
    public static String selectUnit(String type)
    {
        
        if(!type.equals("Civilian")&&!type.equals("Military"))
        {
            return "invalid unittype";
        }

        if(GameController.getSelectedHex()==null)
        {
            return "select a tile first";
        }
        if(type.equals("Civilian"))
        {
            GameController.setSelectedUnit(GameController.getSelectedHex().getCivilianUnit());
        }
        if(type.equals("Military"))
        {
            GameController.setSelectedUnit(GameController.getSelectedHex().getMilitaryUnit());
        }
        
        GameController.setSelectedHex(null);
        return "unit selected successfully";
    }


    public static String showResources()
    {
        if(GameController.getSelectedCity()==null)
        {
            return "select a city first";
        }
        StringBuilder cityResources=new StringBuilder();
        
        for(Hex hex:GameController.getSelectedCity().getHexs())
        {
            if(hex.getResource().getType().equals("Strategic"))
            {
                cityResources.append("hex: "+"x- "+hex.getX()+" y- "+hex.getY()+" --> "+hex.getResource().getName()+"\n");
            }

        }

        if(cityResources.toString().isEmpty())
        {
            return "this city has no resources";
        }


        GameController.setSelectedCity(null);
        return cityResources.toString();


    }
    public static String showUnemployed()
    {
        if(GameController.getSelectedCity()==null)
        {
            return "select a city first";
        }
        StringBuilder notWorking=new StringBuilder();
        for(Civilian temp:Civilian.geiCivilians())
        {
            if(temp.getIsWorking()==false)
            {
                notWorking.append(temp.getName()+" "+temp.getCurrentHex());
            }
        }


        return "";
    }

    public static String selectHex(int x, int y)
    {
        if(x<0||y<0||x>9||y>9)
        {
            return "invalid x or y";
        }

        GameController.setSelectedHex(GameController.getWorld().getHex()[x][y]);

        return "tile selected";
    }

    
    public static String selectCity(String name)
    {
        for(City temp:City.getCities())
        {
            if(temp.getName().equals(name))
            {
                GameController.setSelectedCity(temp);
                return "city selected successfuly";
            }
        }

        return "no city with this name exists";
    }


    public static String makeUnit(Player currentPlayer,String type,String name)
    {

        if(!type.equals("Civilian")&&!type.equals("Military"))
        {
            return "invalid unit type";
        }
        if(GameController.getSelectedHex()==null)
        {
            return "select a tile first";
        }
        City theCity=null;
        for(City temp:City.getCities())
        {
            if(temp.getHexs().contains(GameController.getSelectedHex()))
            {
                theCity=temp;
                break;
            }
        }
        // if(theCity==null||!(theCity.getOwner().equals(currentPlayer)))
        // {
        //     return "this tile does not belong to you";
        // }
//        GameController.setSelectedCity(theCity);
//        if(type.equals("Military")&&GameController.getSelectedCity().getMilitaryUnit()!=null)
//        {
//            return "you can't have two military units in a city";
//        }
//        if(type.equals("Civilian")&&GameController.getSelectedCity().getCivilianUnit()!=null)
//        {
//            return "you can't have two civilian units in a city";
//        }
        
        if(!InitializeGameInfo.unitInfo.containsKey(name))
        {
            return "invalid unit name";
        }
        
        
        Unit newUnit=new Unit(name,GameController.getSelectedHex());
        if(currentPlayer.getGold()<newUnit.getCost())
        {
            return "you don't have enough money";
        }



        if(type.equals("Civilian"))
        {
            Civilian newCivilian=new Civilian(name,GameController.getSelectedHex());
            GameController.getSelectedHex().setCivilianUnit(newCivilian);
            GameController.addALlCivilians(newCivilian);
        }
        if(type.equals("Military"))
        {
            Military newMilitary=new Military(name,GameController.getSelectedHex());
            GameController.getSelectedHex().setMilitaryUnit(newMilitary);
            GameController.addAllMilitary(newMilitary);
        }

    GameController.setSelectedCity(null);
        GameController.setSelectedHex(null);
        
        return "unit created successfully";
    }


    public static ArrayList<Hex> getToBuyTiles()
    {
        return toBuyTiles;
    }



    public static String buildCity(Player currentPlayer,String name)
    {

        if(GameController.getSelectedUnit()==null||(GameController.getSelectedUnit() instanceof Settler))
        {
            return "choose a settler first";
        }

        for(City temp:City.getCities())
        {
            if(temp.getHexs().contains(GameController.getSelectedUnit().getCurrentHex()))
            {
                return "this hex is already part of a city";
            }
            if(temp.getName().equals(name))
            {
                return "a city with this name already exists";
            }
        }

        
    
        City newCity=new City(currentPlayer,name,GameController.getSelectedUnit().getCurrentHex());
        City.addCities(newCity);
        GameController.setSelectedUnit(null);

        return "new city created successfully";
    }



    public static String presaleTiles()
    {
        
        if(GameController.getSelectedCity()==null)
        {
            return null;
        }

        StringBuilder availableHexs=new StringBuilder();

        int counter=0;

        for(Hex temp:GameController.getSelectedCity().getHexs())
        {
            Hex[][] hex=GameController.getWorld().getHex();
            int x=temp.getX();
            int y=temp.getY();
            ArrayList<Hex> sixNeighborHexs=new ArrayList<Hex>();

            if(y%2!=0)
            {
                if(x+1<10&&hex[x+1][y].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x+1][y]);
                }
                    
                if(x-1>=0&&hex[x-1][y].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x-1][y]);
                }
                if(y-1>=0&&hex[x][y-1].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x][y-1]);
                    if(x+1<10&&hex[x+1][y-1].getOwner()==null)
                    {
                        sixNeighborHexs.add(hex[x+1][y-1]);
                    }                 
                }
                if(y+1<10&&hex[x][y+1].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x][y+1]);
                    if(x+1<10&&hex[x][y+1].getOwner()==null)
                    {
                        sixNeighborHexs.add(hex[x+1][y+1]);
                    }
                }  
            }
            if(y%2==0)
            {
                if(x+1<10&&hex[x+1][y].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x+1][y]);
                }
                    
                if(x-1>=0&&hex[x-1][y].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x-1][y]);
                }
                if(y-1>=0&&hex[x][y-1].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x][y-1]);
                    if(x-1>=0&&hex[x-1][y-1].getOwner()==null)
                    {
                        sixNeighborHexs.add(hex[x-1][y-1]);
                    }
                }
                if(y+1<10&&hex[x][y+1].getOwner()==null)
                {
                    sixNeighborHexs.add(hex[x][y+1]);
                    if(x-1>=0&&hex[x-1][y+1].getOwner()==null)
                    {
                        sixNeighborHexs.add(hex[x-1][y+1]);
                    }
                }  
            }

            for(Hex tempHex:sixNeighborHexs)
            {
                counter++;
                toBuyTiles.add(tempHex);
                availableHexs.append(counter+")"+" x: "+tempHex.getX()+" y: "+tempHex.getY()+"\n");
            }


        }

        if(availableHexs.length()==0)
        {
            return "there are no tiles around your city";
        }


        return availableHexs.toString();


    }
    public static String buyHex(Player buyer, int count)
    {
        int price=GameController.getSelectedCity().getHexs().size()*5;
        if(buyer.getGold()<price)
        {
            return "you don't have enough money";
        }
        if(GameController.getSelectedHex().getOwner()!=null)
        {
            return "this hex has an owner";
        }

        buyer.decreaseGold(price);
        GameController.getSelectedCity().addHex(toBuyTiles.get(count-1));
       toBuyTiles.get(count-1).setOwner(buyer);

        return "new hex added to your city successfully";
        
    }
    

}
