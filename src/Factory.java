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
                Station swapStation = spotsFilled.remove(ThreadLocalRandom.current().nextInt(spotsFilled.size()));
                floor[x][y] = swapStation;
                floor[swapStation.x][swapStation.y] = null;
                swapStation.x = x;
                swapStation.y = y;
                spotsFilled.add(swapStation);
            }
            else
            {
                //Intentionally left blank as the random spot was full already.
            }
        }
    }

    public int fitness()
    {
        int fitness = -1;

        //Go through each spot on the floor and see if it is next to something it doesn't like.
        for(int i = 0; i < length; i++)
        {
            for (int j = 0; j < width; j++)
            {
                //We have a station compare to the left.
                if(floor[i][j] != null)
                {
                    try{
                        if(floor[i][j].getType() == floor[i -1][j - 1].getType())
                        {
                            fitness += 0;
                        }
                        else
                        {
                            //Not next to it's own type increment
                            fitness++;
                        }
                    }catch(ArrayIndexOutOfBoundsException e)
                    {
                        //Nothing we are checking the leftmost row against things to the left
                        //They obviously will be happy there so drop the exception.
                    }
                }
            }
        }

        return fitness;
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
