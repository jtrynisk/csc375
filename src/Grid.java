import java.awt.*;
import javax.swing.*;

public class Grid extends JPanel {

    private int length, width, height;

    public Grid(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
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
            }
        }
    }

}