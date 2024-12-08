package climbers.models.climbing;

import climbers.models.climber.Climber;
import climbers.models.mountain.Mountain;
import climbers.models.roster.Roster;

import java.util.Collection;
import java.util.Iterator;

public class ClimbingImpl implements Climbing {
    @Override
    public void conqueringPeaks(Mountain mountain, Collection<Climber> climbers) {
        Collection<String> peaks = mountain.getPeaksList();
        for (Climber climber : climbers) {
            while (climber.canClimb() && peaks.iterator().hasNext()) {
                climber.climb();
                String currentPeak = peaks.iterator().next();
                climber.getRoster().getPeaks().add(currentPeak);
                peaks.remove(currentPeak);
            }
        }
    }
}
