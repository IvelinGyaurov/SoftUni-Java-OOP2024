package climbers.repositories;

import climbers.models.climber.Climber;
import climbers.models.climbing.ClimbingImpl;

import java.util.*;

public class ClimberRepository implements Repository<Climber> {

    private Collection<Climber> climbers;

    public ClimberRepository() {
        this.climbers = new ArrayList<>();
    }

    @Override
    public Collection<Climber> getCollection() {
        return Collections.unmodifiableCollection(climbers);
    }

    @Override
    public void add(Climber entity) {
        climbers.add(entity);
    }

    @Override
    public boolean remove(Climber entity) {
        return climbers.remove(entity);
    }

    @Override
    public Climber byName(String name) {
        return climbers.stream().filter(climber -> climber.getName().equals(name)).findFirst().orElse(null);
    }


}
