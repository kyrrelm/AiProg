package problems.navigationTask;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kyrre on 27.09.2014.
 * Gui elements representing each node in the navigation task,
 * e.i. the squares on the grid.
 */
public class Square extends JButton {

    public enum Type {
        BARRIER,
        START,
        END,
        PATH,
        OLD_PATH,
        DEFAULT
    }

    private final int x;
    private final int y;
    private Type type;

    public Square(int x, int y) {
        super("");
        this.type = Type.DEFAULT;
        this.x = x;
        this.y = y;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        if (type == Type.DEFAULT){
            if (this.type == Type.PATH || this.type == Type.OLD_PATH){
                this.type = Type.OLD_PATH;
                setVisual();
                return;
            }
        }
        if(this.type != Type.START && this.type != Type.END){
            this.type = type;
            setVisual();
        }
    }

    private void setVisual() {
        switch (type){
            case BARRIER:{
                setText("");
                setBackground(Color.BLACK);
                setOpaque(true);
                break;
            }
            case PATH:{
                setText("");
                setBackground(Color.YELLOW);
                setOpaque(true);
                break;
            }
            case DEFAULT:{
                setText("");
                break;
            }
            case START:{
                setText("");
                setBackground(Color.BLUE);
                setOpaque(true);
                break;
            }
            case OLD_PATH:{
                setText("");
                setBackground(Color.LIGHT_GRAY);
                setOpaque(true);
                break;
            }
            case END:{
                setText("");
                setBackground(Color.RED);
                setOpaque(true);
                break;
            }
            default:{
                setText("");
            }
        }
    }
}
