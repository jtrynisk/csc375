import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactoryGUI
{
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
    GridLayout mainLayout = new GridLayout(5,2);

    public FactoryGUI()
    {
        final JPanel mainPanel = new JPanel();
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
        mainFrame.setSize(800, 1000);
        mainFrame.setLayout(mainLayout);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JPanel floorPanel = new JPanel();
                BorderLayout floorLayout = new BorderLayout(Integer.parseInt(lengthField.getText()), Integer.parseInt(widthField.getText()));

                JPanel[][] floorGrid = new JPanel[Integer.parseInt(lengthField.getText())][Integer.parseInt(widthField.getText())];



            }
        });

    }


}
