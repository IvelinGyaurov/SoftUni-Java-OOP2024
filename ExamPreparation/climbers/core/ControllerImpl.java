package climbers.core;

import climbers.common.ConstantMessages;
import climbers.models.climber.Climber;
import climbers.models.climber.RockClimber;
import climbers.models.climber.WallClimber;
import climbers.models.climbing.Climbing;
import climbers.models.climbing.ClimbingImpl;
import climbers.models.mountain.Mountain;
import climbers.models.mountain.MountainImpl;
import climbers.repositories.ClimberRepository;
import climbers.repositories.MountainRepository;
import climbers.repositories.Repository;

import java.util.Arrays;
import java.util.Collection;

import static climbers.common.ConstantMessages.*;
import static climbers.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Climber> climberRepository;
    private Repository<Mountain> mountainRepository;
    private int mountainPeaksReached;

    public ControllerImpl() {
        this.climberRepository = new ClimberRepository();
        this.mountainRepository = new MountainRepository();
    }

    @Override
    public String addClimber(String type, String climberName) {
        Climber climber = switch (type) {
            case "RockClimber" -> new RockClimber(climberName);
            case "WallClimber" -> new WallClimber(climberName);
            default -> throw new IllegalArgumentException(CLIMBER_INVALID_TYPE);
        };

        this.climberRepository.add(climber);
        return CLIMBER_ADDED.formatted(type, climberName);
    }

    @Override
    public String addMountain(String mountainName, String... peaks) {
        /*Mountain mountain = new MountainImpl(mountainName);
        mountain.getPeaksList().add(Arrays.toString(peaks));
        this.mountainRepository.add(mountain);*/

        Mountain mountain = new MountainImpl(mountainName);
        for (String peak : peaks) {
            mountain.getPeaksList().add(peak);
        }
        this.mountainRepository.add(mountain);
        return MOUNTAIN_ADDED.formatted(mountainName);
    }

    @Override
    public String removeClimber(String climberName) {
        Climber climber = this.climberRepository.byName(climberName);
        if (climber == null) {
            throw new IllegalArgumentException(CLIMBER_DOES_NOT_EXIST.formatted(climberName));
        }

        this.climberRepository.remove(climber);
        return CLIMBER_REMOVE.formatted(climberName);
    }

    @Override
    public String startClimbing(String mountainName) {
        Collection<Climber> climbers = this.climberRepository.getCollection();
        if (climbers.isEmpty()) {
            throw new IllegalArgumentException(THERE_ARE_NO_CLIMBERS);
        }
        Mountain mountain = this.mountainRepository.byName(mountainName);
        Climbing climbing = new ClimbingImpl();
        climbing.conqueringPeaks(mountain, climbers);
        long removed = climbers.stream().filter(climber -> climber.getStrength() == 0).count();
        mountainPeaksReached++;
        return PEAK_CLIMBING.formatted(mountainName, removed);
    }

    @Override
    public String getStatistics() {
        StringBuilder build = new StringBuilder();
        build.append(String.format(ConstantMessages.FINAL_MOUNTAIN_COUNT, mountainPeaksReached));
        build.append(System.lineSeparator());
        build.append(ConstantMessages.FINAL_CLIMBERS_STATISTICS);

        Collection<Climber> climbers = climberRepository.getCollection();
        for (Climber climber : climbers) {
            build.append(System.lineSeparator());
            build.append(String.format(ConstantMessages.FINAL_CLIMBER_NAME, climber.getName()));
            build.append(System.lineSeparator());
            build.append(String.format(ConstantMessages.FINAL_CLIMBER_STRENGTH, climber.getStrength()));
            build.append(System.lineSeparator());
            if (climber.getRoster().getPeaks().isEmpty()) {
                build.append(String.format(ConstantMessages.FINAL_CLIMBER_PEAKS, "None"));

            } else {
                build.append(String.format(ConstantMessages.FINAL_CLIMBER_PEAKS,
                        String.join(ConstantMessages.FINAL_CLIMBER_FINDINGS_DELIMITER, climber.getRoster().getPeaks())));
            }
        }
        return build.toString();
    }
}
