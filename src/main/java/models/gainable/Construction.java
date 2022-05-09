package models.gainable;

public interface Construction {

    void setLeftTurns(int turns);

    void decreaseLeftTurns();

    int getLeftTurns();

    void build(); //new the object and remove it from list
}
