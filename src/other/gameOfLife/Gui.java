package other.gameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kyrre on 07.11.2014.
 */
public class Gui extends JFrame {

    private final int dim;
    private final World world;
    private JPanel p = new JPanel();
    public Gui(int size) {
        super("Game of Life");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        dim = (int) (screenDim.getHeight()-100);
        setSize(dim, dim);
        setLocation((int) (screenDim.getWidth()-dim-100),0);
        setResizable(true);

        world = new World(size);
        p.setLayout(new GridLayout(size, size));
        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                p.add(new CubeView(world.getCube(x, y)));
            }
        }
        add(p, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setText(start.getText() == "Start" ? "Stop" : "Start");
                world.toggleRun();
            }
        });

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setText("Start");
                world.reset();

            }
        });
        buttonPanel.add(start);
        buttonPanel.add(reset);
        add(buttonPanel, BorderLayout.PAGE_END);

        setVisible(true);
        setAlwaysOnTop(true);
        setAlwaysOnTop(false);

    }

    public static void main(String[] args) {
        new Gui(30);
    }
}
