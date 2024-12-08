package furnitureFactory.core;

import furnitureFactory.entities.factories.AdvancedFactory;
import furnitureFactory.entities.factories.Factory;
import furnitureFactory.entities.factories.OrdinaryFactory;
import furnitureFactory.entities.wood.OakWood;
import furnitureFactory.entities.wood.Wood;
import furnitureFactory.entities.workshops.DeckingWorkshop;
import furnitureFactory.entities.workshops.TableWorkshop;
import furnitureFactory.entities.workshops.Workshop;
import furnitureFactory.repositories.WoodRepository;
import furnitureFactory.repositories.WoodRepositoryImpl;
import furnitureFactory.repositories.WorkshopRepository;
import furnitureFactory.repositories.WorkshopRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static furnitureFactory.common.ConstantMessages.*;
import static furnitureFactory.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private WoodRepository woodRepository;
    private WorkshopRepository workshopRepository;
    private Collection<Factory> factories;

    public ControllerImpl() {
        this.woodRepository = new WoodRepositoryImpl();
        this.workshopRepository = new WorkshopRepositoryImpl();
        this.factories = new ArrayList<>();
    }

    @Override
    public String buildFactory(String factoryType, String factoryName) {
        Factory factory;
        switch (factoryType) {
            case "OrdinaryFactory" -> factory = new OrdinaryFactory(factoryName);
            case "AdvancedFactory" -> factory = new AdvancedFactory(factoryName);
            default -> throw new IllegalArgumentException(INVALID_FACTORY_TYPE);
        }

        Factory factory2 = factories.stream().filter(factory1 -> factory1.getName()
                .equals(factoryName)).findFirst().orElse(null);

        if (factory2 != null) {
            throw new NullPointerException(FACTORY_EXISTS);
        }

        factories.add(factory);

        return SUCCESSFULLY_BUILD_FACTORY_TYPE.formatted(factoryType, factoryName);
    }

    @Override
    public Factory getFactoryByName(String factoryName) {
        return factories.stream().filter(factory -> factory.getName().equals(factoryName))
                .findFirst().orElse(null);
    }


    @Override
    public String buildWorkshop(String workshopType, int woodCapacity) {

        Workshop workshop;
        switch (workshopType) {
            case "TableWorkshop" -> workshop = new TableWorkshop(woodCapacity);
            case "DeckingWorkshop" -> workshop = new DeckingWorkshop(woodCapacity);
            default -> throw new IllegalArgumentException(INVALID_WORKSHOP_TYPE);
        }

        workshopRepository.add(workshop);

        return SUCCESSFULLY_BUILD_WORKSHOP_TYPE.formatted(workshopType);
    }

    @Override
    public String addWorkshopToFactory(String factoryName, String workshopType) {
        Factory factory = getFactoryByName(factoryName);
        Workshop workshop = workshopRepository.findByType(workshopType);

        if (workshop == null) {
            throw new IllegalArgumentException(NO_WORKSHOP_FOUND.formatted(workshopType));
        }

        if (factory.getWorkshops().contains(workshop)) {
            throw new IllegalArgumentException(WORKSHOP_EXISTS);
        }

        if (factory.getClass().getSimpleName().equals("OrdinaryFactory")
                && workshop.getClass().getSimpleName().equals("TableWorkshop")) {
            factory.addWorkshop(workshop);
        } else if (factory.getClass().getSimpleName().equals("OrdinaryFactory")
                && workshop.getClass().getSimpleName().equals("WardrobeWorkshop")) {
            factory.addWorkshop(workshop);
        } else if (factory.getClass().getSimpleName().equals("AdvancedFactory")
                && workshop.getClass().getSimpleName().equals("DeckingWorkshop")) {
            factory.addWorkshop(workshop);
        } else {
            return NON_SUPPORTED_WORKSHOP;
        }

        return SUCCESSFULLY_ADDED_WORKSHOP_IN_FACTORY.formatted(workshopType, factoryName);
    }


    @Override
    public String buyWoodForFactory(String woodType) {
        if (!woodType.equals("OakWood")) {
            throw new IllegalArgumentException(INVALID_WOOD_TYPE);
        }

        Wood wood;
        wood = new OakWood();
        woodRepository.add(wood);

        return SUCCESSFULLY_BOUGHT_WOOD_FOR_FACTORY.formatted(woodType);
    }

    @Override
    public String addWoodToWorkshop(String factoryName, String workshopType, String woodType) {
        Factory factory = getFactoryByName(factoryName);
        Workshop workshop = factory.getWorkshops().stream().filter(f -> f.getClass().getSimpleName()
                        .equals(workshopType))
                .findFirst().orElse(null);

        if (workshop == null) {
            throw new NullPointerException(NO_WORKSHOP_ADDED);
        }

        Wood wood = woodRepository.findByType(woodType);
        if (wood == null) {
            throw new NullPointerException(String.format(NO_WOOD_FOUND, woodType));
        }
        workshop.addWood(wood);
        woodRepository.remove(wood);
        return SUCCESSFULLY_ADDED_WOOD_IN_WORKSHOP.formatted(woodType, workshopType);
    }

    @Override
    public String produceFurniture(String factoryName) {
        int producedFurniture = 0;
        Factory factory = getFactoryByName(factoryName);
        Collection<Workshop> workshops = factory.getWorkshops();
        if (workshops.isEmpty()) {
            throw new NullPointerException(THERE_ARE_NO_WORKSHOPS.formatted(factoryName));
        }

        Iterator<Workshop> workshopIterator = factory.getWorkshops().iterator();

        while (workshopIterator.hasNext()) {
            Workshop workshop = workshopIterator.next();

            if (workshop.getWoodQuantity() <= 0 || workshop.getWoodQuantity() < workshop.getWoodQuantityReduceFactor()) {
                workshopIterator.remove();
                factory.getRemovedWorkshops().add(workshop);
                workshopRepository.remove(workshop);
                System.out.printf(WORKSHOP_STOPPED_WORKING + "%n", workshop.getClass().getSimpleName());
                break;
            }
            workshop.produce();
            producedFurniture++;
        }


        String result = "";
        if (producedFurniture == 0) {
            result = String.format(FACTORY_DO_NOT_PRODUCED, factory.getName());
        } else {
            result = String.format(SUCCESSFUL_PRODUCTION, producedFurniture, factory.getName());
        }

        return result;
    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();

        for (Factory factory : factories) {
            sb.append(String.format("Production by %s factory:", factory.getName())).append("\n");
            Collection<Workshop> workshops = factory.getWorkshops();
            if (workshops.isEmpty() && factory.getRemovedWorkshops().isEmpty()) {
                sb.append("No workshops were added to produce furniture.");
            }

            getAllWorkshopsInfo(factory.getWorkshops(), sb);
            getAllWorkshopsInfo(factory.getRemovedWorkshops(), sb);
        }


        return sb.toString().trim();
    }

    private static void getAllWorkshopsInfo(Collection<Workshop> factory, StringBuilder sb) {
        for (Workshop workshop : factory) {
            sb.append(String.format("  %s: %d furniture produced\n",
                    workshop.getClass().getSimpleName(),
                    workshop.getProducedFurnitureCount()));
        }
    }
}

