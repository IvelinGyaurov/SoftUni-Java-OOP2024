package climbers.models.climber;

import climbers.models.roster.Roster;

public class WallClimber extends BaseClimber{
    public WallClimber(String name) {
        super(name, 90);
    }

    public void decrease(){
        double newStrength = getStrength() - 30;
        if (newStrength < 0){
            setStrength(0);
        }
        setStrength(newStrength);
    }
}
