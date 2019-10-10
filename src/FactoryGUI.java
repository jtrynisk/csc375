import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactoryGUI
{
    JPanel mainPanel = new JPanel();
    JFrame mainFrame = new JFrame();
    JButton submitButton = new JButton("Submit");
    JButton clearButton = new JButton("Clear");
    JLabel lengthLabel = new JLabel("Length of Factory");
    JTextField lengthField = new JTextField("Amount");
    JLabel widthLabel = new JLabel("Width of Factory");
    JTextField widthField = new JTextField("Amount");
    JLabel stationLabel = new JLabel("Amount of Stations");
    JTextField stationField = new JTextField("Amount");
    JLabel threadLabel = new JLabel("Amount of Threads");
    JTextField threadField = new JTextField("Amount");
    GridLayout mainLayout = new GridLayout(5,2,10,10);

    public FactoryGUI()
    {
        this.createMainWindow();
    }

    public void createMainWindow()
    {
        mainPanel.setLayout(mainLayout);
        mainPanel.add(stationLabel);
        mainPanel.add(stationField);
        mainPanel.add(lengthLabel);
        mainPanel.add(lengthField);
        mainPanel.add(widthLabel);
        mainPanel.add(widthField);
        mainPanel.add(threadLabel);
        mainPanel.add(threadField);
        mainPanel.add(submitButton);
        mainPanel.add(clearButton);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setSize(1000, 800);
        mainFrame.setLayout(mainLayout);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                createAndShowFactoryFloor();
            }
        });
    }

    private void createAndShowFactoryFloor()
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                int length = Integer.parseInt(lengthField.getText());
                int width = Integer.parseInt(widthField.getText());
                int height = 600 / length;

                Grid grid = new Grid(length, width, height);
                JFrame factoryFrame = new JFrame("Factory Floor");

                factoryFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                factoryFrame.getContentPane().add(grid);
                factoryFrame.pack();
                factoryFrame.setVisible(true);

            }
        });
    }

}
