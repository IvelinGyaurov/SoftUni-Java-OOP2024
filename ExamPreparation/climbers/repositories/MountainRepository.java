package climbers.repositories;

import climbers.models.mountain.Mountain;

import java.util.*;

public class MountainRepository implements Repository<Mountain> {

    private Collection<Mountain> mountains;

    public MountainRepository() {
        this.mountains = new ArrayList<>();
    }

    @Override
    public Collection<Mountain> getCollection() {
        return Collections.unmodifiableCollection(mountains);
    }

    @Override
    public void add(Mountain entity) {
        mountains.add(entity);
    }

    @Override
    public boolean remove(Mountain entity) {
        return mountains.remove(entity);
    }

    @Override
    public Mountain byName(String name) {
        return mountains.stream().filter(mountain -> mountain.getName().equals(name)).findFirst().orElse(null);
    }


}
