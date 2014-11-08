package other.gameOfLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Kyrre on 07.11.2014.
 */
public class CubeView extends JButton {
    private final Cube cube;
    private static boolean mousePressed;

    public CubeView(final Cube cube) {
        this.cube = cube;
        cube.addOnChangeListener(new OnChangeListener() {
            @Override
            public void onChange() {
                change();
            }
        });
        change();

        setRolloverEnabled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (mousePressed){
                    cube.setAlive(!cube.isAlive());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mousePressed = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mousePressed = true;
                cube.setAlive(!cube.isAlive());
            }
        });
    }

    protected void change() {
        if (cube.isAlive()){
            setBackground(Color.YELLOW);
        }else {
            setBackground(Color.WHITE);
        }
    }
}
