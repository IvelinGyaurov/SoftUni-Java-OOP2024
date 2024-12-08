package handball.entities.team;

public class Bulgaria extends BaseTeam{
    //OUTDOOR
    public Bulgaria(String name, String country, int advantage) {
        super(name, country, advantage);
    }

    @Override
    public void play() {
        int newAdvantage = getAdvantage() + 115;
        setAdvantage(newAdvantage);
    }

}
