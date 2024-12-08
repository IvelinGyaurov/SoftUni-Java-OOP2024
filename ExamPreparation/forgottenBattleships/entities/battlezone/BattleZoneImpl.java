package forgottenBattleships.entities.battlezone;

import forgottenBattleships.entities.battleship.Battleship;

import java.util.ArrayList;
import java.util.Collection;

import static forgottenBattleships.common.ExceptionMessages.*;

public class BattleZoneImpl implements BattleZone {

    private String name;
    private int capacity;
    private Collection<Battleship> ships;


    public BattleZoneImpl(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.ships = new ArrayList<>();
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(BATTLE_ZONE_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public void addBattleship(Battleship ship) {
        if (capacity <= ships.size()){
            throw new IllegalArgumentException(NOT_ENOUGH_CAPACITY);
        }
        if (ship.getHealth() <= 0){
            throw new IllegalArgumentException(SHIP_HEALTH_NULL_OR_EMPTY);
        }
        ships.add(ship);
    }

    @Override
    public Battleship getBattleshipByName(String battleshipName) {
        return ships.stream().filter(ship -> ship.getName().equals(battleshipName))
                .findFirst().orElse(null);
    }

    @Override
    public void removeBattleShip(Battleship ship) {
        ships.remove(ship);
    }

    @Override
    public Collection<Battleship> getShips() {
        return ships;
    }
}
