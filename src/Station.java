import java.awt.*;

public class Station
{
    public int x, y;
    private final Color color;
    private final String id;
    private int type;

    public Station(int x, int y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;

        //Set the color of the station for the GUI.
        if(this.type == 1)
        {
            this.color = Color.black;
        }
        else if(this.type == 2)
        {
            this.color = Color.blue;
        }
        else if(this.type == 3)
        {
            this.color = Color.GRAY;
        }
        else
        {
            this.color = Color.white;
        }

        this.id = color.toString();
    }

    public Station(Station station)
    {
        this.x = station.x;
        this.y = station.y;
        this.color = station.getColor();
        this.id = station.getId();
    }

    public void changePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean stationAffinity(Station compareTo)
    {
        if(this.type != compareTo.type)
        {
            return true;
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

}
