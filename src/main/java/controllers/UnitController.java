package controllers;

import enums.HexState;
import enums.TerrainNames;
import enums.UnitState;
import models.Player;
import models.gainable.Improvement;
import models.maprelated.Hex;
import models.maprelated.Movement;
import models.units.Civilian;
import models.units.Melee;
import models.units.Military;
import models.units.Ranged;
import models.units.Settler;
import models.units.Siege;
import models.units.Unit;
import models.units.Worker;

import java.util.ArrayList;

import java.util.Objects;

import static controllers.GameController.*;

public class UnitController {

    private static Unit selectedUnit;
    private static Hex[][] hex = getWorld().getHex();
    private static Player currentPlayer = GameController.getCurrentPlayer();
    private static ArrayList<Movement> unfinishedMovements = new ArrayList<Movement>();


    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static boolean hasMilitary(int x, int y) {
        // TODO: ask traneh why??
        return GameController.getWorld().getHex()[x][y].getMilitaryUnit() != null;
/*
        return GameController.getMilitaryByLocation(x, y) != null;
*/
    }

    public static boolean hasCivilian(int x, int y) {
        return GameController.getCiviliansByLocation(x, y) != null;
    }

    public static boolean canMove(Unit unit, Hex destination) {
        return Math.abs(unit.getCurrentHex().getX() - destination.getX() + unit.getCurrentHex().getY() - destination.getY()) == 1;
    }


    private static int[] getDirectionIndex(int[][] direction, int dx, int dy, Unit unit) {
        if (dx != 0) dx = dx / Math.abs(dx);
        if (dy != 0) dy = dy / Math.abs(dy);
        for (int[] ints : direction) {
            if (ints[0] == dx && ints[1] == dy
                    && !isOutOfBounds(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1])
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Mountain")
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Ocean"))
                return ints;
        }
        for (int[] ints : direction) {
            if ((ints[0] == dx || ints[1] == dy)
                    && !isOutOfBounds(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1])
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Mountain")
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Ocean"))
                return ints;
        }
        return null;
    }

    public static boolean canMoveThrough(int x, int y) {
        return !hex[x][y].getTerrain().getName().equals("Mountain") && !hex[x][y].getTerrain().getName().equals("Ocean");
    }

    public static boolean isHexOccupied(int destinationX, int destinationY) {
        Unit unit = selectedUnit;
        return (unit instanceof Military && hex[destinationX][destinationY].getMilitaryUnit() != null)
                || (unit instanceof Civilian && hex[destinationX][destinationY].getCivilianUnit() != null);
    }

    public static Unit getSelectedUnit() {
        return selectedUnit;
    }

    public static void setSelectedUnit(Unit selectedUnit) {
        UnitController.selectedUnit = selectedUnit;
    }


    private static void changeView(int x, int y) {
        int[][] oddDirection = new int[][]{{0, 0}, {-1, 0}, {0, -1}, {1, -1}, {1, 0}, {0, 1}, {1, 1}};
        int[][] evenDirection = new int[][]{{0, 0}, {-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {-1, 1}, {0, 1}};
        int[][] direction;

        if (y % 2 == 0) direction = evenDirection;
        else direction = oddDirection;

        for (int j = 0; j < 7; j++) {
            x = x + direction[j][0];
            y = y + direction[j][1];
            for (int i = 0; i < 7; i++) {
                if (!GameController.isOutOfBounds(x + direction[i][0], y + direction[i][1])) {
                    hex[x + direction[i][0]][y + direction[i][1]].setState(HexState.Visible, currentPlayer);
                    currentPlayer.addToRevealedHexes(hex[x + direction[i][0]][y + direction[i][1]]);
                }
            }
        }
    }

    private static void setRevealedTiles() {
        for (int i = 0; i < getWorld().getHexInWidth(); i++) {
            for (int j = 0; j < getWorld().getHexInHeight(); j++) {
               // System.out.println(i + " " + j);
                if (currentPlayer == null)
                    System.out.println("cp is null");
                if (hex[i][j].getState(currentPlayer) == null)
                    System.out.println("is null");
                if (hex[i][j].getState(currentPlayer).equals(HexState.Visible)) {
                    hex[i][j].setState(HexState.Revealed, currentPlayer);
                    currentPlayer.addToRevealedHexes(hex[i][j]);
                }
            }
        }
    }

    public static String constructRoad(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            return "chosen position is not valid";
        if (selectedUnit == null)
            return "you should choose a unit first";
        if (!selectedUnit.getName().equals("Worker"))
            return "you should choose a worker unit";
        if (!currentPlayer.getAchievedTechnologies().get("TheWheel"))
            return "you don't have required technology for building roads";
        if (hex[x][y].hasRoad())
            return "this hex already has road";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Mountain))
            return "you can't construct road on mountain";
        if (hex[x][y].getTerrain().equals(TerrainNames.Ocean))
            return "you can't construct road on ocean";
        //Todo: can't build roads on ice
        Improvement road = new Improvement("Road", selectedUnit, hex[x][y]);
        road.setLeftTurns(3);
        currentPlayer.addUnfinishedProject(road);
        return "the road will be constructed in 3 turns";
    }

    public static String constructRailRoad(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            return "chosen position is not valid";
        if (!selectedUnit.getName().equals("Worker"))
            return "you should choose a worker unit";
        if (!currentPlayer.getAchievedTechnologies().get("Road"))
            return "you don't have required technology for building roads";
        if (hex[x][y].hasRailRoad())
            return "this hex already has railroad";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Mountain))
            return "you can't construct railroad on mountain";
        if (hex[x][y].getTerrain().equals(TerrainNames.Ocean))
            return "you can't construct railroad on ocean";
        //Todo: can't build roads on ice
        //todo: construct road in 3 turns
        return "the railroad will be constructed in 3 turns";
    }

    public static void makeUnit(String name, Hex hex) {
        String type=InitializeGameInfo.unitInfo.get(name).split(" ")[7];
        if(type.equals("Settler")){
            Settler newSettler=new Settler(name, hex, currentPlayer);
            newSettler.build();
        } else if(type.equals("Worker")){
            Worker newWorker=new Worker(name, hex,currentPlayer);
            newWorker.build();
        } else if(type.equals("Archery")){
            Ranged newRanged=new Ranged(name, hex, currentPlayer);
            newRanged.build();
        } else if(type.equals("Siege")){
            Siege newSiege=new Siege(name, hex, currentPlayer);
            newSiege.build();
        }else if(type.equals("Melee")){
            Melee newMelee=new Melee(name, hex, currentPlayer);
            newMelee.build();
        }else{
            int temp=Integer.parseInt(InitializeGameInfo.unitInfo.get(name));
            if(temp==0)
            {
                Melee newMelee=new Melee(name, hex, currentPlayer);
                newMelee.build();
            }else{
                Ranged newRanged=new Ranged(name, hex, currentPlayer);
                newRanged.build();
            }
        }

    }
    public static String setUpSiegeForRangeAttack(){
        if(selectedUnit == null) return "you did not select a unit";
        if(! (selectedUnit instanceof Siege)) return "selected unit is not a siege";
        ((Siege)selectedUnit).setReadyToAttack(true);
        return "siege is ready now";
    }
    public static String fortify(){
        if(selectedUnit == null || selectedUnit instanceof Civilian) return "you did not select a military unit";
        if(Objects.equals(selectedUnit.getCombatType(), "Mounted")) return "a Mounted unit can not fortify";
        if(Objects.equals(selectedUnit.getCombatType(), "Armored")) return "a Armored unit can not fortify";
        selectedUnit.setState(UnitState.Fortified);
        return "fortified successfully";
    }

    public static String garrison(){
        if(selectedUnit == null || selectedUnit instanceof Civilian) {
            return "you did not select a military unit";
        }if(selectedUnit.getCurrentHex().getCapital() == null){
            return "there is no capital";
        }if(selectedUnit.getCurrentHex().getOwner() != currentPlayer){
            return ("this is not your city");
        }
        return "garrisoned successfully";
    }

    public static String alert(){
        selectedUnit.setState(UnitState.Alert);
        return "alerted successfully";
    }
    public static String deleteUnit(Unit unit){
        unit.getOwner().removeUnit( unit);
        unit.getCurrentHex().setMilitaryUnit(null);
        selectedUnit = null;
        return "unit deleted";
    }
    public static String sleepUnit(){
        if(selectedUnit.getState() == UnitState.Sleep){
            return "unit is already sleep";
        }
        selectedUnit.setState(UnitState.Sleep);
        return "successfully sleep";
    }
    public static String wakeUpUnit(){
        if(selectedUnit.getState() == UnitState.Sleep || selectedUnit.getState() == UnitState.Alert){
            selectedUnit.setState(UnitState.Active);
            return "successfully waked up";
        }
        return "unit is already awake";
    }
    public static String pillage(){
        if(selectedUnit == null) return "select a military unit first";
        if(selectedUnit instanceof Civilian)return "selected unit is a civilian";
        selectedUnit.getCurrentHex().setPillaged(true);
        
        if(!selectedUnit.getCurrentHex().getImprovement().isEmpty()) reverseImprovement();
        

        

        return "pillaged successfully";
    }

    private static void reverseImprovement()
    {
        for(Improvement temp:selectedUnit.getCurrentHex().getImprovement())
        {
            String type=temp.getName();
            switch(type)
            {
                case "Camp":
                    Improvement.reverseCamp(selectedUnit.getCurrentHex());
                    break;
                case "Farm":    
                    Improvement.reverseFarm(selectedUnit.getCurrentHex());
                    break;
                case "Mine":    
                    Improvement.reverseMine(selectedUnit.getCurrentHex());
                    break;
                case "Pasture":   
                    Improvement.reversePasture(selectedUnit.getCurrentHex());
                    break;
                case "Plantation":
                    Improvement.reversePlantation(selectedUnit.getCurrentHex());
                    break;
                case "Quarry":
                    Improvement.reverseQuarry(selectedUnit.getCurrentHex());
                    break;
            }
        }
    }

    public static void forceEndMovement(Movement movement) {
        unfinishedMovements.remove(movement);
    }

    public static void moveUnit(Movement movement) {
        Unit unit = movement.getUnit();
        Hex nextHex = getNextHex(movement.getDestination().getX(), movement.getDestination().getY());
        if (nextHex == null || nextHex.getOwner() != currentPlayer) {
            forceEndMovement(movement);
            return;
        }
        int x = nextHex.getX();
        int y = nextHex.getY();

        setRevealedTiles();
        changeView(x, y);
        if (unit.getState().equals(UnitState.Fortified))
            unit.setState(UnitState.Active);

        if ((unit.getCurrentHex().hasRoad() || unit.getCurrentHex().hasRailRoad())
                && (hex[x][y].hasRoad() || hex[x][y].hasRailRoad()))
            unit.decreaseMP((int) (hex[x][y].getTerrain().getMovePoint() * 0.2));
        else
            unit.decreaseMP(hex[x][y].getTerrain().getMovePoint());

        if (unit instanceof Civilian) {
            unit.getCurrentHex().setCivilianUnit(null);
            hex[x][y].setCivilianUnit((Civilian) unit);
        } else {
            unit.getCurrentHex().setMilitaryUnit(null);
            hex[x][y].setMilitaryUnit((Military) unit);
        }
        unit.changeCurrentHex(hex[x][y]);
        if (nextHex.getX() == movement.getDestination().getX() && nextHex.getY() == movement.getDestination().getY())
            unfinishedMovements.remove(movement);
    }

    public static String startMovement(int x, int y) {
        if (selectedUnit == null)
            return "you have to choose a unit first";
        else if (isHexOccupied(x, y))
            return "Destination hex already has a unit of this type";
        else if (!canMoveThrough(x, y))
            return "The unit can't go through chosen destination hex";

        Movement movement = new Movement(selectedUnit, selectedUnit.getCurrentHex(), hex[x][y]);
        unfinishedMovements.add(movement);

        moveUnit(movement);
        return "unit is on its way";
    }


    public static Hex getNextHex(int finalX, int finalY) {
        Unit unit = selectedUnit;
        int[] direction;
        int[][] oddDirection = new int[][]{{-1, 0}, {0, -1}, {1, -1}, {1, 0}, {0, 1}, {1, 1}};
        int[][] evenDirection = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {-1, 1}, {0, 1}};
        int deltaX = finalX - unit.getCurrentHex().getX();
        int deltaY = finalY - unit.getCurrentHex().getY();

        if (unit.getCurrentHex().getY() % 2 == 0) {
            direction = getDirectionIndex(evenDirection, deltaX, deltaY, unit);
        } else {
            direction = getDirectionIndex(oddDirection, deltaX, deltaY, unit);
        }
        assert direction != null;
        return hex[unit.getCurrentHex().getX() + direction[0]][unit.getCurrentHex().getY() + direction[1]];
    }

    public static void changeTurn() {
        for (Movement unfinishedMovement : unfinishedMovements)
            moveUnit(unfinishedMovement);
    }
}