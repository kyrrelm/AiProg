package core.gac;

public class Interpreter {

    public static boolean violates(String constraint, Object[] variables) {
        String[] bits = constraint.split(" ");
        if (bits[0].equals("x") && bits[1].equals("!=") && bits[2].equals("y")){
            return variables[0].equals(variables[1]);
        }
        System.out.println("Interpreter failed");
        return false;
    }
}
