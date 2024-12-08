package furnitureFactory.repositories;

import furnitureFactory.entities.workshops.Workshop;

import java.util.ArrayList;
import java.util.Collection;

import static furnitureFactory.common.ExceptionMessages.WORKSHOP_EXISTS_IN_REPOSITORY;
import static furnitureFactory.common.ExceptionMessages.WORKSHOP_WOOD_QUANTITY_BELOW_OR_EQUAL_ZERO;

public class WorkshopRepositoryImpl implements WorkshopRepository{

    private Collection<Workshop> workshops;

    public WorkshopRepositoryImpl() {
        this.workshops = new ArrayList<>();
    }



    @Override
    public void add(Workshop workshop) {
        /*if (workshops.contains(workshop)){
            throw new IllegalArgumentException(WORKSHOP_EXISTS_IN_REPOSITORY);
        }*/
        for (Workshop w : workshops) {
                  if (w.getClass().getSimpleName().equals(workshop.getClass().getSimpleName())) {
                     throw new IllegalArgumentException(WORKSHOP_EXISTS_IN_REPOSITORY);
                  }
              }
        if (workshop.getWoodQuantity() <= 0){
            throw new IllegalArgumentException(WORKSHOP_WOOD_QUANTITY_BELOW_OR_EQUAL_ZERO);
        }
        workshops.add(workshop);
    }

    @Override
    public boolean remove(Workshop workshop) {
        if(workshops.isEmpty()) {
            throw new NullPointerException("There are no workshops in repository.");
        }
        return workshops.remove(workshop);
    }

    @Override
    public Workshop findByType(String type) {
        return workshops.stream().filter(workshop -> workshop.getClass().getSimpleName().equals(type))
                .findFirst().orElse(null);
    }

    public boolean contains(Workshop workshop){
        return workshops.contains(workshop);
    }
}
