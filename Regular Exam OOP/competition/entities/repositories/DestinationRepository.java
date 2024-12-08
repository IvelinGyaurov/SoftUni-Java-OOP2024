package competition.entities.repositories;

import competition.entities.destination.Destination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DestinationRepository implements Repository<Destination> {

    private Collection<Destination> destinations;

    public DestinationRepository() {
        this.destinations = new ArrayList<>();
    }

    @Override
    public Collection<Destination> getCollection() {
        return Collections.unmodifiableCollection(destinations);
    }

    @Override
    public void add(Destination destination) {
        destinations.add(destination);
    }

    @Override
    public boolean remove(Destination destination) {
        return destinations.remove(destination);
    }

    @Override
    public Destination byName(String name) {
        return destinations.stream().filter(destination -> destination.getName().equals(name))
                .findFirst().orElse(null);
    }
}
