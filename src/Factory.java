import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Factory
{
    public final Station[][] floor;
    private final int length, width;
    private final int totalStation;
    private volatile ArrayList<Station> spotsFilled = new ArrayList<>();

    public Factory(int length, int width, int stations)
    {
        this.length = length;
        this.width = width;
        this.totalStation = stations;
        Station[][] floorToFill = new Station[length][width];
        int addedStations = 0;

        //Check to make sure you aren't adding more stations than spots
        if(this.length * this.width + 1 != totalStation)
        {
            while(addedStations < totalStation)
            {
                int randomX = ThreadLocalRandom.current().nextInt(length);
                int randomY = ThreadLocalRandom.current().nextInt(width);

                if(floorToFill[randomX][randomY] == null)
                {
                    Station newStation = new Station(randomX, randomY, ThreadLocalRandom.current().nextInt(3) + 1);
                    floorToFill[randomX][randomY] = newStation;
                    addedStations++;
                    spotsFilled.add(newStation);
                    System.out.println("Added new station " + newStation.getId());
                }
                else
                {
                    //Intentionally left blank
                }
            }
        }
        else
        {
            System.out.println("Sorry too many stations");
            System.exit(1);
        }
        floor = floorToFill;
    }

    public Factory(Factory factory)
    {
        floor = factory.floor;
        spotsFilled = factory.getSpotsFilled();
        this.length = factory.getLength();
        this.width = factory.getWidth();
        this.totalStation = factory.getTotalStation();
    }

    public void mutation()
    {
        int mutation = ThreadLocalRandom.current().nextInt(totalStation);

        //mutate the amount of spots randomly decided.
        for(int i = 0; i < mutation; i++)
        {
            int x = ThreadLocalRandom.current().nextInt(length);
            int y = ThreadLocalRandom.current().nextInt(width);

            if(floor[x][y] != null)
            {
                
            }
        }
    }





    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getTotalStation() {
        return totalStation;
    }

    public ArrayList<Station> getSpotsFilled() {
        return spotsFilled;
    }

    public void setSpotsFilled(ArrayList<Station> spotsFilled) {
        this.spotsFilled = spotsFilled;
    }
}
