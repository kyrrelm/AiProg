package aStar.navigationTask;

import javax.swing.*;

/**
 * Created by Kyrre on 27.09.2014.
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
        if(this.type != Type.START && this.type != Type.END){
            this.type = type;
            setVisual();
        }
    }

    private void setVisual() {
        switch (type){
            case BARRIER:{
                setText("#");
                break;
            }
            case PATH:{
                setText("P");
                break;
            }
            case DEFAULT:{
                setText("");
                break;
            }
            case START:{
                setText("S");
                break;
            }
            case END:{
                setText("E");
                break;
            }
            default:{
                setText("");
            }
        }
    }
}
