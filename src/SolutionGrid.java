import java.awt.*;
import javax.swing.*;

public class SolutionGrid extends JPanel {

    private int length, width, height;
    private Factory factory;

    public SolutionGrid(int length, int width, int height, Factory factory) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.factory = factory;
    }

    @Override
    public Dimension getPreferredSize ()
    {
        return new Dimension(1000, 800);
    }

    @Override
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        for(int x = 0; x < length; x++)
        {
            for(int y = 0; y < width; y++)
            {
                g.drawRect(x  * height, y * height, height, height);
                if(factory.floor[x][y] != null)
                {
                    g.setColor(factory.floor[x][y].getColor());
                }
            }
        }
    }

}