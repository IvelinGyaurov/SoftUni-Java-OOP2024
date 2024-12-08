package furnitureFactory.entities.factories;

import furnitureFactory.entities.workshops.BaseWorkshop;
import furnitureFactory.entities.workshops.Workshop;
import furnitureFactory.repositories.WorkshopRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static furnitureFactory.common.ExceptionMessages.FACTORY_NAME_NULL_OR_EMPTY;

public abstract class BaseFactory implements Factory{

    private String name;
    private Collection<Workshop> workshops;
    private Collection<Workshop> removedWorkshops;

    public BaseFactory(String name) {
        this.setName(name);
        this.workshops = new ArrayList<>();
        this.removedWorkshops = new ArrayList<>();

    }

    public void setName(String name) {
        if (name == null || name.isEmpty() || name.isBlank()){
            throw new NullPointerException(FACTORY_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addWorkshop(Workshop workshop) {
        workshops.add(workshop);
    }

    @Override
    public Collection<Workshop> getWorkshops() {
        return workshops;
    }

    @Override
    public Collection<Workshop> getRemovedWorkshops() {
        return removedWorkshops;
    }
}
