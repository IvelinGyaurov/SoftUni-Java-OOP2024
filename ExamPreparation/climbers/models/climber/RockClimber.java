package climbers.models.climber;

import climbers.models.roster.Roster;

public class RockClimber extends BaseClimber{
    public RockClimber(String name) {
        super(name, 120);
    }

    public void decrease(){
        double newStrength = getStrength() - 60;
        if (newStrength < 0){
            setStrength(0);
        }
        setStrength(newStrength);
    }
}
