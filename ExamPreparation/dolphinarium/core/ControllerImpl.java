package dolphinarium.core;

import dolphinarium.entities.dolphins.BottleNoseDolphin;
import dolphinarium.entities.dolphins.Dolphin;
import dolphinarium.entities.dolphins.SpinnerDolphin;
import dolphinarium.entities.dolphins.SpottedDolphin;
import dolphinarium.entities.foods.Food;
import dolphinarium.entities.foods.Herring;
import dolphinarium.entities.foods.Mackerel;
import dolphinarium.entities.foods.Squid;
import dolphinarium.entities.pools.DeepWaterPool;
import dolphinarium.entities.pools.Pool;
import dolphinarium.entities.pools.ShallowWaterPool;
import dolphinarium.repositories.FoodRepository;
import dolphinarium.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dolphinarium.common.ConstantMessages.*;
import static dolphinarium.common.ExceptionMessages.*;

public class ControllerImpl implements Controller{

   /* private FoodRepository foodRepository;
    private Collection<Pool> pools;

    public ControllerImpl() {
        this.foodRepository = new FoodRepositoryImpl();
        this.pools = new ArrayList<>();
    }

    @Override
    public String addPool(String poolType, String poolName) {
        Pool pool = switch (poolType){
            case "DeepWaterPool" -> new DeepWaterPool(poolName);
            case "ShallowWaterPool" -> new ShallowWaterPool(poolName);
            default -> throw new NullPointerException(INVALID_POOL_TYPE);
        };

        Pool pool2 = pools.stream().filter(pool1 -> pool1.getName()
                .equals(poolName)).findFirst().orElse(null);

        if (pool2 != null){
            throw new NullPointerException(POOL_EXISTS);
        }

        pools.add(pool);

        return SUCCESSFULLY_ADDED_POOL_TYPE.formatted(poolType,poolName);
    }

    @Override
    public String buyFood(String foodType) {
        Food food = switch (foodType){
            case "Squid" -> new Squid();
            case "Herring" -> new Herring();
            case "Mackerel" -> new Mackerel();
            default -> throw new IllegalArgumentException (INVALID_FOOD_TYPE);
        };

        foodRepository.add(food);

        return SUCCESSFULLY_BOUGHT_FOOD_TYPE.formatted(foodType);
    }

    @Override
    public String addFoodToPool(String poolName, String foodType) {
        Food byType = foodRepository.findByType(foodType);

        if (byType == null){
            throw new IllegalArgumentException (NO_FOOD_FOUND.formatted(foodType));
        }
        Pool pool = pools.stream().filter(pool1 -> pool1.getName().equals(poolName)).findFirst().orElse(null);
        pool.addFood(byType);
        foodRepository.remove(byType);

        return SUCCESSFULLY_ADDED_FOOD_IN_POOL.formatted(foodType,poolName);
    }

    @Override
    public String addDolphin(String poolName, String dolphinType, String dolphinName, int energy) {
        Pool poolByName = getPool(poolName);

    if (poolByName.getDolphins().stream().anyMatch(dolphin -> dolphin.getName().equals(dolphinName))) {
        throw new IllegalArgumentException(DOLPHIN_EXISTS);
    }
    Dolphin dolphin;
    switch (dolphinType) {
        case "BottleNoseDolphin":
            dolphin = new BottleNoseDolphin(dolphinName, energy);
            break;
        case "SpottedDolphin":
            dolphin = new SpottedDolphin(dolphinName, energy);
            break;
        case "SpinnerDolphin":
            dolphin = new SpinnerDolphin(dolphinName, energy);
            break;
        default:
            throw new IllegalArgumentException(INVALID_DOLPHIN_TYPE);
    }
    if (poolByName.getClass().getSimpleName().equals("DeepWaterPool")
            && dolphinType.equals("BottleNoseDolphin") || dolphinType.equals("SpottedDolphin")) {
        poolByName.addDolphin(dolphin);
    } else if (poolByName.getClass().getSimpleName().equals("ShallowWaterPool")
            && dolphinType.equals("SpinnerDolphin")) {
        poolByName.addDolphin(dolphin);
    } else {
        return POOL_NOT_SUITABLE;
    }
    return String.format(SUCCESSFULLY_ADDED_DOLPHIN_IN_POOL, dolphinType, dolphinName, poolName);
    }

    @Override
    public String feedDolphins(String poolName, String food) {
        Pool pool = pools.stream().filter(pool1 -> pool1.getName().equals(poolName)).findFirst().orElse(null);
        if (pool == null){
            throw new IllegalArgumentException(NO_FOOD_OF_TYPE_ADDED_TO_POOL);
        }

        pools.remove(food);
        Collection<Dolphin> dolphins = pool.getDolphins();
        Food byType = foodRepository.findByType(food);
        dolphins.forEach(d -> d.eat(byType));



        return String.format(DOLPHINS_FED, dolphins.size(), poolName);
    }

    @Override
    public String playWithDolphins(String poolName) {
        Pool pool = pools.stream().filter(pool1 -> pool1.getName().equals(poolName)).findFirst().orElse(null);
        if (pool == null){
            throw new IllegalArgumentException(NO_DOLPHINS);
        }
        int exhaustedDolphins = 0;
        for (Dolphin dolphin : pool.getDolphins()) {
            dolphin.jump();
            if (dolphin.getEnergy() <= 0) {
                exhaustedDolphins++;
                pool.getDolphins().remove(dolphin);
            }
        }

        return String.format(DOLPHINS_PLAY, poolName, exhaustedDolphins);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();

        for (Pool pool : pools) {
            sb.append(DOLPHINS_FINAL.formatted(pool)).append("\n");
            Collection<Dolphin> dolphins = pool.getDolphins();
            if (dolphins.isEmpty()){
                sb.append(NONE);
            } else {
                for (Dolphin dolphin : dolphins){
                    sb.append(pool.getDolphins().stream()
                            .map(d -> d.getName() + " - " + d.getEnergy())
                            .collect(Collectors.joining(DELIMITER)));
                }
            }

        }
        return sb.toString().trim();
    }*/
    private FoodRepository foodRepository;
    private Map<String, Pool> pools;

    public ControllerImpl() {
        this.foodRepository = new FoodRepositoryImpl();
        this.pools = new LinkedHashMap<>();
    }

    @Override
    public String addPool(String poolType, String poolName) {
        Pool pool = switch (poolType) {
            case "DeepWaterPool" -> new DeepWaterPool(poolName);
            case "ShallowWaterPool" -> new ShallowWaterPool(poolName);
            default -> throw new NullPointerException(INVALID_POOL_TYPE);
        };

        if (pools.containsKey(pool.getName())) {
            throw new NullPointerException(POOL_EXISTS);
        }

        pools.put(pool.getName(), pool);

        return String.format(SUCCESSFULLY_ADDED_POOL_TYPE, poolType, poolName);
    }

    @Override
    public String buyFood(String foodType) {
        Food food = switch (foodType) {
            case "Herring" -> new Herring();
            case "Mackerel" -> new Mackerel();
            case "Squid" -> new Squid();
            default -> throw new IllegalArgumentException(INVALID_FOOD_TYPE);
        };
        foodRepository.add(food);
        return String.format(SUCCESSFULLY_BOUGHT_FOOD_TYPE, foodType);
    }

    @Override
    public String addFoodToPool(String poolName, String foodType) {
        Food food = foodRepository.findByType(foodType);
        if (food == null) {
            throw new IllegalArgumentException(String.format(NO_FOOD_FOUND, foodType));
        }

        Pool pool = pools.get(poolName);
        pool.addFood(food);
        return String.format(SUCCESSFULLY_ADDED_FOOD_IN_POOL, foodType, poolName);
    }

    @Override
    public String addDolphin(String poolName, String dolphinType, String dolphinName, int energy) {
        Dolphin dolphin = switch (dolphinType) {
            case "BottleNoseDolphin" -> new BottleNoseDolphin(dolphinName, energy);
            case "SpottedDolphin" -> new SpottedDolphin(dolphinName, energy);
            case "SpinnerDolphin" -> new SpinnerDolphin(dolphinName, energy);
            default -> throw new IllegalArgumentException(INVALID_DOLPHIN_TYPE);
        };

        boolean dolphinNameMatches = pools.values().stream()
                .anyMatch(p -> p.getDolphins()
                        .stream()
                        .anyMatch(d -> d.getName().equals(dolphinName)));

        if (dolphinNameMatches) {
            throw new IllegalArgumentException(DOLPHIN_EXISTS);
        }

        Pool pool = pools.get(poolName);
        String poolType = pool.getClass().getSimpleName();

        boolean canLiveInPool = true;

        if (dolphinType.equals("BottleNoseDolphin") && !poolType.equals("DeepWaterPool")) {
            canLiveInPool = false;
        } else if (dolphinType.equals("SpinnerDolphin") && !poolType.equals("ShallowWaterPool")) {
            canLiveInPool = false;
        }

        if (!canLiveInPool) {
            return POOL_NOT_SUITABLE;
        }

        pool.addDolphin(dolphin);

        return String.format(SUCCESSFULLY_ADDED_DOLPHIN_IN_POOL, dolphinType, dolphinName, poolName);
    }

    @Override
    public String feedDolphins(String poolName, String foodType) {
        Pool pool = pools.get(poolName);
        Food food = pool.getFoods().stream()
                .filter(f -> f.getClass().getSimpleName().equals(foodType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NO_FOOD_OF_TYPE_ADDED_TO_POOL));

        pool.getFoods().remove(food);

        Collection<Dolphin> dolphins = pool.getDolphins();
        dolphins.forEach(d -> d.eat(food));
        return String.format(DOLPHINS_FED, dolphins.size(), poolName);
    }

    @Override
    public String playWithDolphins(String poolName) {
        Pool pool = pools.get(poolName);

        Collection<Dolphin> dolphins = pool.getDolphins();
        if (dolphins.isEmpty()) {
            throw new IllegalArgumentException(NO_DOLPHINS);
        }

        int exhaustedDolphins = 0;
        for (Dolphin dolphin : dolphins) {
            dolphin.jump();
            if (dolphin.getEnergy() <= 0) {
                exhaustedDolphins++;
            }
        }
        pool.getDolphins().removeIf(d -> d.getEnergy() <= 0);
        return String.format(DOLPHINS_PLAY, poolName, exhaustedDolphins);
    }

    @Override
    public String getStatistics() {
        return pools.values()
                .stream()
                .map(this::formatPoolStatistics)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String formatPoolStatistics(Pool pool) {
        String dolphinInfo = pool.getDolphins().stream()
                .map(d -> d.getName() + " - " + d.getEnergy())
                .collect(Collectors.joining(DELIMITER));

        dolphinInfo = dolphinInfo.isEmpty() ? NONE : dolphinInfo;

        return String.format(DOLPHINS_FINAL, pool.getName()) + System.lineSeparator() + dolphinInfo;
    }
}
