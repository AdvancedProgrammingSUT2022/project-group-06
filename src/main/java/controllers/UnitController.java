package controllers;

import enums.FeatureNames;
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
    private static final Hex[][] hex = getWorld().getHex();
    private static final ArrayList<Movement> unfinishedMovements = new ArrayList<Movement>();

    public static void setCurrentPlayer(Player player) {
        GameController.setCurrentPlayer(player);
    }

    public static boolean hasMilitary(int x, int y) {
        return GameController.getWorld().getHex()[x][y].getMilitaryUnit() != null;
    }

    public static boolean hasCivilian(int x, int y) {
        return GameController.getCiviliansByLocation(x, y) != null;
    }

    public static boolean canMove(Unit unit, Hex destination) {
        return Math.abs(unit.getCurrentHex().getX() - destination.getX() + unit.getCurrentHex().getY() - destination.getY()) == 1;
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

    private static void makeVisible(int x, int y, int[][] tempDirection, int i) {
        if (!isOutOfBounds(x + tempDirection[i][0], y + tempDirection[i][1])) {
            hex[x + tempDirection[i][0]][y + tempDirection[i][1]].setState(HexState.Visible,GameController.getCurrentPlayer());
        }
    }

    private static void changeView(int[][] direction, int x, int y){
        for (int j = 0; j < direction.length; j++) {
            makeVisible(x, y, direction, j);
            int[][] tempDirection = getDirection(y + direction[j][1]);
            for (int i = 0; i < tempDirection.length; i++) {
              makeVisible(x + direction[j][0], y + direction[j][1], tempDirection, i);
            }
        }
    }

    private static void setRevealedTiles() {
        for (int i = 0; i < getWorld().getHexInWidth(); i++) {
            for (int j = 0; j < getWorld().getHexInHeight(); j++) {
            //    if (hex[i][j].getState(GameController.getCurrentPlayer()) == null)
            //        System.out.println("is null");
                if (hex[i][j].getState(GameController.getCurrentPlayer()).equals(HexState.Visible)) {
                    hex[i][j].setState(HexState.Revealed, GameController.getCurrentPlayer());
                    GameController.getCurrentPlayer().addToRevealedHexes(hex[i][j]);
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
        if (!GameController.getCurrentPlayer().getAchievedTechnologies().get("TheWheel"))
            return "you don't have required technology for building roads";
        if (hex[x][y].hasRoad())
            return "this hex already has road";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Mountain))
            return "you can't construct road on mountain";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Ocean))
            return "you can't construct road on ocean";
        if (hex[x][y].getFeature().getName().equals(FeatureNames.Ice))
            return "you can't build road on ice";
        Improvement road = new Improvement("Road", selectedUnit, hex[x][y]);
        road.setLeftTurns(3);
        GameController.getCurrentPlayer().addUnfinishedProject(road);
        return "the road will be constructed in 3 turns";
    }

    public static String constructRailRoad(int x, int y) {
        if (GameController.isOutOfBounds(x, y))
            return "chosen position is not valid";
        if (!selectedUnit.getName().equals("Worker"))
            return "you should choose a worker unit";
        if (!GameController.getCurrentPlayer().getAchievedTechnologies().get("Road"))
            return "you don't have required technology for building roads";
        if (hex[x][y].hasRailRoad())
            return "this hex already has railroad";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Mountain))
            return "you can't construct railroad on mountain";
        if (hex[x][y].getTerrain().getName().equals(TerrainNames.Ocean))
            return "you can't construct railroad on ocean";
        if (hex[x][y].getFeature().getName().equals(FeatureNames.Ice))
            return "you can't build railroad on ice";

        Improvement railroad = new Improvement("RailRoad", selectedUnit, hex[x][y]);
        railroad.setLeftTurns(3);
        GameController.getCurrentPlayer().addUnfinishedProject(railroad);
        return "the railroad will be constructed in 3 turns";
    }

    public static void makeUnit(String name, Hex hex,String type) {
        String theType=InitializeGameInfo.unitInfo.get(name).split(" ")[7];
        if(theType.equals("Settler")){
            Settler newSettler=new Settler(name, hex, GameController.getCurrentPlayer());
            newSettler.build(type);
            hex.setCivilianUnit(newSettler);
        } else if(theType.equals("Worker")){
            Worker newWorker=new Worker(name, hex,GameController.getCurrentPlayer());
            newWorker.build(type);
        } else if(theType.equals("Ranged")){
            Ranged newRanged=new Ranged(name, hex, GameController.getCurrentPlayer());
            newRanged.build(type);
        } else if(theType.equals("Siege")){
            Siege newSiege=new Siege(name, hex, GameController.getCurrentPlayer());
            newSiege.build(type);
        }else if(theType.equals("Melee")){
            Melee newMelee=new Melee(name, hex, GameController.getCurrentPlayer());
            newMelee.build(type);
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
        selectedUnit.setFirstFortify(false);
        return "fortified successfully";
    }

    public static String garrison(){
        //todo: move unit to capital city and move errors
        if(selectedUnit == null || selectedUnit instanceof Civilian) {
            return "you did not select a military unit";
        }if(selectedUnit.getCurrentHex().getCapital() == null){
            return "there is no capital";
        }if(selectedUnit.getCurrentHex().getOwner() != GameController.getCurrentPlayer()){
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
    public static String deleteUnitAction(Unit unit){
        unit.getOwner().removeUnit( unit);
        unit.getCurrentHex().setMilitaryUnit(null);
        selectedUnit = null;
        GameController.getCurrentPlayer().increaseGold(unit.getCost()/10);
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
        if(selectedUnit.getCurrentHex().getImprovement().size() == 0) return "there is no improvement";
        selectedUnit.getCurrentHex().setPillaged(true);
        if(!selectedUnit.getCurrentHex().getImprovement().isEmpty()) reverseImprovement();
        selectedUnit.setMP(0);
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

    private static int[] getDirectionIndex(int[][] direction, int dx, int dy, Unit unit) {
        if (dx != 0) dx = dx / Math.abs(dx);
        if (dy != 0) dy = dy / Math.abs(dy);
        for (int[] ints : direction) {
            if (ints[0] == dx && ints[1] == dy
                    && !isOutOfBounds(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1])
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Mountain")
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Ocean")
                    && !isHexOccupied(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1]))
                return ints;
        }
        for (int[] ints : direction) {
            if ((ints[0] == dx || ints[1] == dy)
                    && !isOutOfBounds(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1])
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Mountain")
                    && !hex[unit.getCurrentHex().getX() + ints[0]][unit.getCurrentHex().getY() + ints[1]].getTerrain().getName().equals("Ocean")
                    && !isHexOccupied(unit.getCurrentHex().getX() + ints[0], unit.getCurrentHex().getY() + ints[1]))
                return ints;
        }
        return null;
    }

    public static boolean checkForRiverOnWay(Movement movement, Hex nextHex) {
        int dx = nextHex.getX() - movement.getCurrentHex().getX();
        int dy = nextHex.getY() - movement.getCurrentHex().getY();

        if (dx == -1 && dy == -1 && movement.getCurrentHex().getHasRiver()[0])
            return true;
        if (dx == 0 && dy == -1 && movement.getCurrentHex().getHasRiver()[1])
            return true;
        if (dx == -1 && dy == 1 && movement.getCurrentHex().getHasRiver()[2])
            return true;
        if (dx == 0 && dy == 1 && movement.getCurrentHex().getHasRiver()[3])
            return true;
        return false;
    }

    public static String moveUnit(Movement movement) {

        Unit unit = movement.getUnit();
        Hex nextHex = getNextHex(movement);
        if (nextHex == null || (nextHex.getOwner() != null && nextHex.getOwner() != GameController.getCurrentPlayer())) {
            forceEndMovement(movement);
            return "the unit can't go further";
        }
        int x = nextHex.getX();
        int y = nextHex.getY();

        if ((unit.getCurrentHex().hasRoad() || unit.getCurrentHex().hasRailRoad())
                && (hex[x][y].hasRoad() || hex[x][y].hasRailRoad())
                && (int) (hex[x][y].getTerrain().getMovePoint() * 0.2) >= 0)
            unit.decreaseMP((int) (hex[x][y].getTerrain().getMovePoint() * 0.2));
        else if (checkForRiverOnWay(movement, nextHex))
            unit.setMP(0);
        else if (hex[x][y].getTerrain().getMovePoint() >= 0)
            unit.decreaseMP(hex[x][y].getTerrain().getMovePoint());
        else {
            unfinishedMovements.remove(movement);
            return "the unit doesn't have enough move points";
        }
        //TODO: tartibe revealed ina ro ok kon
        setRevealedTiles();
        changeView(GameController.getDirection(y),x, y);
        if (unit.getState().equals(UnitState.Fortified))
            unit.setState(UnitState.Active);

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

        return "unit is on its way";
    }

    public static String startMovement(int x, int y) {
        if (selectedUnit == null)
            return "you have to choose a unit first";
        else if (isHexOccupied(x, y))
            return "Destination hex already has a unit of this type";
        else if (!canMoveThrough(x, y))
            return "The unit can't go through chosen destination hex";
        else if (selectedUnit instanceof Civilian && hex[x][y].getMilitaryUnit() != null && hex[x][y].getOwner() != getCurrentPlayer())
            return "a noncombat unit can't move to a tile with enemy military unit";

        Movement movement = new Movement(selectedUnit, selectedUnit.getCurrentHex(), hex[x][y]);
        unfinishedMovements.add(movement);

        return moveUnit(movement);
    }


    public static Hex getNextHex(Movement movement) {
        Unit unit = movement.getUnit();
        int[] direction;
        int[][] oddDirection = new int[][]{{-1, 0}, {0, -1}, {1, -1}, {1, 0}, {0, 1}, {1, 1}};
        int[][] evenDirection = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, 0}, {-1, 1}, {0, 1}};
        int deltaX = movement.getDestination().getX() - unit.getCurrentHex().getX();
        int deltaY = movement.getDestination().getY() - unit.getCurrentHex().getY();

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