import javax.swing.*;
import java.awt.*;

public class Akari{
    JFrame frame = new JFrame("Akari");
    JPanel boardPanel = new JPanel();

    ImageIcon img = new ImageIcon("Ikonka.png");

    final String[] options = {"Plansze/Plansza łatwa 1.csv", "Plansze/Plansza łatwa 2.csv", "Plansze/Plansza łatwa 3.csv",
            "Plansze/Plansza trudna 1.csv", "Plansze/Plansza trudna 2.csv", "Plansze/Plansza trudna 3.csv"};

    public Akari(int board) {
        /** Generating game grids from .csv */
        Seed gameSeed = new Seed(options[board]);
        int dimensions = gameSeed.getDimensions();

        /** JFrame configuration */
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(90, 130, 60));
        ImageIcon img = new ImageIcon("Plansze/Ikonka1.png");
        frame.setIconImage(img.getImage());

        /** Niepotrzebne teraz, ale może się przydać :) */
        boardPanel.setBackground(Color.cyan);
        boardPanel.setPreferredSize(new Dimension(800, 680));
        boardPanel.setLayout(new GridLayout(dimensions, dimensions));

        /** Grid generation - a grid stores game session's data and is used
         *  as a reference which rules to abide by
         * */
        Grid gameGrid = new Grid(gameSeed);

        /** Button addition and frame elements intialization */
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                boardPanel.add(gameGrid.squares[i][j]);
            }
        }
        frame.getContentPane().add(boardPanel, BorderLayout.SOUTH);
        frame.pack();
    }
}
