package competition.core;

import competition.entities.car.*;
import competition.entities.destination.Destination;
import competition.entities.destination.Lake;
import competition.entities.destination.Mountain;
import competition.entities.destination.SeaSide;
import competition.entities.repositories.DestinationRepository;
import competition.entities.repositories.Repository;

import java.util.Collection;

import static competition.common.ConstantMessages.*;
import static competition.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Destination> destinations;

    public ControllerImpl() {
        this.destinations = new DestinationRepository();
    }

    @Override
    public String addDestination(String destinationType, String destinationName) {
        Destination destination = switch (destinationType) {
            case "Lake" -> new Lake(destinationName);
            case "Mountain" -> new Mountain(destinationName);
            case "SeaSide" -> new SeaSide(destinationName);
            default -> throw new IllegalArgumentException(INVALID_DESTINATION);
        };

        Destination destination2 = destinations.getCollection().stream().filter(destination1 -> destination1.getName().equals(destinationName))
                .findFirst().orElse(null);

        if (destination2 != null) {
            throw new IllegalArgumentException(EXISTING_DESTINATION);
        }

        destinations.add(destination);

        return DESTINATION_ADDED.formatted(destinationType, destinationName);
    }

    @Override
    public String addCar(String destinationName, String carBrand, String carModel) {
        Destination destination = destinations.byName(destinationName);
        if (destination == null) {
            throw new NullPointerException(NON_EXISTING_DESTINATION);
        }

        Destination destination1 = destinations.byName(destinationName);
        Car car = switch (carBrand) {
            case "Tesla" -> new Tesla(carModel);
            case "VW" -> new VW(carModel);
            case "Hyundai" -> new Hyundai(carModel);
            case "Dacia" -> new Dacia(carModel);
            default -> throw new IllegalArgumentException(INVALID_CAR);
        };
        for (Car destination1Car : destination1.getCars()) {
            if (destination1Car.getModel().equals(car.getModel())){
                throw new IllegalArgumentException(EXISTING_CAR_BRAND_AND_MODEL);
            }
        }

        destination1.getCars().add(car);

        return CAR_ADDED.formatted(carBrand, carModel);
    }

    @Override
    public String reachDestination(String destinationName) {
        int retiredCars = 0;
        Destination destination = destinations.byName(destinationName);
        for (Car car : destination.getCars()) {
            while (car.getBatteryCapacity() >= 15 && car.getMileage() < destination.getDistance()){
                car.drive();
                if (car.getMileage() >= destination.getDistance()){
                    break;
                }
                if (car.getBatteryCapacity() < 15){
                    retiredCars++;
                }
            }
        }

        return VOYAGE_OVER.formatted(destinationName, retiredCars);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Destination destination : destinations.getCollection()) {
            sb.append(CARS_TOOK_PART.formatted(destination.getName())).append("\n");
            for (Car car : destination.getCars()) {
                sb.append(FINAL_CAR_INFO.formatted(car.getClass().getSimpleName(),car.getModel(),car.getBatteryCapacity(),car.getMileage())).append("\n");
            }
        }
        return sb.toString().trim();
    }
}
