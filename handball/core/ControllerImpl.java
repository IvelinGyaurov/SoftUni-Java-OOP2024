package handball.core;

import handball.entities.equipment.ElbowPad;
import handball.entities.equipment.Equipment;
import handball.entities.equipment.Kneepad;
import handball.entities.gameplay.Gameplay;
import handball.entities.gameplay.Indoor;
import handball.entities.gameplay.Outdoor;
import handball.entities.team.Bulgaria;
import handball.entities.team.Germany;
import handball.entities.team.Team;
import handball.repositories.EquipmentRepository;
import handball.repositories.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static handball.common.ConstantMessages.*;
import static handball.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository equipment;
    private Map<String, Gameplay> gameplays;

    public ControllerImpl() {
        this.equipment = new EquipmentRepository();
        this.gameplays = new LinkedHashMap<>();
    }


    @Override
    public String addGameplay(String gameplayType, String gameplayName) {
        Gameplay gameplay = switch (gameplayType) {
            case "Outdoor" -> new Outdoor(gameplayName);
            case "Indoor" -> new Indoor(gameplayName);
            default -> throw new NullPointerException(INVALID_GAMEPLAY_TYPE);
        };

        gameplays.put(gameplayName, gameplay);
        return SUCCESSFULLY_ADDED_GAMEPLAY_TYPE.formatted(gameplayType);
    }

    @Override
    public String addEquipment(String equipmentType) {
        Equipment equipment1 = switch (equipmentType) {
            case "Kneepad" -> new Kneepad();
            case "ElbowPad" -> new ElbowPad();
            default -> throw new IllegalArgumentException(INVALID_EQUIPMENT_TYPE);
        };

        equipment.add(equipment1);
        return SUCCESSFULLY_ADDED_EQUIPMENT_TYPE.formatted(equipmentType);
    }

    @Override
    public String equipmentRequirement(String gameplayName, String equipmentType) {
        Equipment equipment1 = equipment.findByType(equipmentType);
        if (equipment1 == null) {
            throw new IllegalArgumentException(String.format(NO_EQUIPMENT_FOUND, equipmentType));
        }
        equipment.remove(equipment1);
        this.gameplays.get(gameplayName).addEquipment(equipment1);
        return SUCCESSFULLY_ADDED_TEAM_IN_GAMEPLAY.formatted(equipmentType, gameplayName);
    }

    @Override
    public String addTeam(String gameplayName, String teamType, String teamName, String country, int advantage) {
        Team team = switch (teamType) {
            case "Bulgaria" -> new Bulgaria(teamName, country, advantage);
            case "Germany" -> new Germany(teamName, country, advantage);
            default -> throw new IllegalArgumentException(INVALID_TEAM_TYPE);
        };

        Gameplay gameplay = gameplays.get(gameplayName);
        boolean b = gameplay.getClass().getSimpleName().equals("Outdoor") && teamType.equals("Bulgaria") ||
                gameplay.getClass().getSimpleName().equals("Indoor") && teamType.equals("Germany");
        if (!b) {
            return GAMEPLAY_NOT_SUITABLE;
        }
        gameplays.get(gameplayName).addTeam(team);

        return SUCCESSFULLY_ADDED_TEAM_IN_GAMEPLAY.formatted(teamName, gameplayName);
    }

    @Override
    public String playInGameplay(String gameplayName) {
        Gameplay gameplay = gameplays.get(gameplayName);
        gameplay.teamsInGameplay();
        return TEAMS_PLAYED.formatted(gameplay.getTeam().size());
    }

    @Override
    public String percentAdvantage(String gameplayName) {
        return ADVANTAGE_GAMEPLAY.formatted(gameplayName,
                this.gameplays.get(gameplayName).getTeam().stream().mapToInt(Team::getAdvantage).sum());
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        gameplays.values().forEach(sb::append);
        return sb.toString().trim();
    }
}
