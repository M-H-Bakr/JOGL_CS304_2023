import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Graphics_Sec2 extends JFrame implements ActionListener, KeyListener {

    public static void main(String[]args){
        Graphics_Sec2 graphicsSec2 = new Graphics_Sec2();
        graphicsSec2.setVisible(true);
    }
    private final JLabel view = new JLabel();
    private final JButton[] btns = new JButton[20];

    Graphics_Sec2() {
        super("Calculator");
        setSize(500, 500);
        this.setLayout(new GridLayout(2, 1));
        setLocationRelativeTo(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout layout = new GridLayout(5, 4);
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(500, 500);
        add(view);
        for (int i = 0; i < 10; i++) {
            btns[i] = new JButton(i + "");
            panel.add(btns[i]);
        }
        btns[10] = new JButton("+");
        btns[11] = new JButton("-");
        btns[12] = new JButton("*");
        btns[13] = new JButton("/");
        btns[14] = new JButton("=");
        btns[15] = new JButton(".");
        btns[16] = new JButton("DEL");
        btns[17] = new JButton("AC");
        btns[18] = new JButton("Negative");
        btns[19] = new JButton("Exp");

        for (int i = 10; i < btns.length; i++) {
            panel.add(btns[i]);
        }
        add(panel);
        for (JButton btn : btns) {
            btn.addActionListener(this);
            btn.addKeyListener(this);
        }
        addKeyListener(this);
        view.setFocusable(true);
        view.addKeyListener(this);
    }

    public static String evaluate(String exp) {
        if (exp.isEmpty()) {
            return "Empty Input";
        }
        String[] helper = exp.split("[*/+^-]");
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<String> operations = new ArrayList<>();
        for (String s : helper) {
            if (s.isEmpty()) {
                return "Syntax Error";
            }
            if (s.charAt(0) == 'N') {
                s = "-" + s.substring(1);
            }
            if(s.contains("Math Error")||s.contains("Empty Input")||
                    s.contains("Syntax Error")||s.contains("Invalid Exp")){
                return "Invalid Exp";
            }
            values.add(Double.parseDouble(s));
        }
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '*') {
                operations.add("*");
            } else if (exp.charAt(i) == '/') {
                operations.add("/");
            } else if (exp.charAt(i) == '-') {
                operations.add("-");
            } else if (exp.charAt(i) == '+') {
                operations.add("+");
            } else if (exp.charAt(i) == '^') {
                operations.add("^");
            }
        }
        if (operations.contains(exp.charAt(0) + "") || operations.size() >= values.size()) {
            return "Syntax Error";
        }
        if (operations.isEmpty()) {
            if (values.get(0) == values.get(0).intValue()) {
                return values.get(0).intValue() + "";
            } else {
                return values.get(0).toString();
            }
        }

        while (!operations.isEmpty()) {
            while (operations.contains("^")) {
                double x1 = values.remove(operations.indexOf("^"));
                double x2 = values.remove(operations.indexOf("^"));
                if (x1 == 0 && x2 == 0) {
                    return "Math Error";
                }
                values.add(operations.indexOf("^"), Math.pow(x1, x2));
                operations.remove("^");
            }
            while (operations.contains("*")) {
                if (!operations.contains("/") ||
                        operations.indexOf("*") < operations.indexOf("/")) {
                    double x1 = values.remove(operations.indexOf("*"));
                    double x2 = values.remove(operations.indexOf("*"));
                    values.add(operations.indexOf("*"), x1 * x2);
                    operations.remove("*");
                } else {
                    if (duplicate(values, operations)) return "Math Error";
                }
            }
            while (operations.contains("/")) {
                if (duplicate(values, operations)) return "Math Error";
            }
            while (operations.contains("+")) {
                if (!operations.contains("-") ||
                        operations.indexOf("+") < operations.indexOf("-")) {
                    double x1 = values.remove(operations.indexOf("+"));
                    double x2 = values.remove(operations.indexOf("+"));
                    values.add(operations.indexOf("+"), x1 + x2);
                    operations.remove("+");
                } else {
                    double x1 = values.remove(operations.indexOf("-"));
                    double x2 = values.remove(operations.indexOf("-"));
                    values.add(operations.indexOf("-"), x1 - x2);
                    operations.remove("-");
                }
            }
            while (operations.contains("-")) {
                double x1 = values.remove(operations.indexOf("-"));
                double x2 = values.remove(operations.indexOf("-"));
                values.add(operations.indexOf("-"), x1 - x2);
                operations.remove("-");
            }
        }
        if (values.get(0) == values.get(0).intValue()) {
            return values.get(0).intValue() + "";
        } else {
            return values.get(0).toString();
        }
    }

    private static boolean duplicate(ArrayList<Double> values, ArrayList<String> operations) {
        double x1 = values.remove(operations.indexOf("/"));
        double x2 = values.remove(operations.indexOf("/"));
        if (x2 == 0) {
            return true;
        }
        values.add(operations.indexOf("/"), x1 / x2);
        operations.remove("/");
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btns[0])) {
            view.setText(view.getText() + "0");
        } else if (e.getSource().equals(btns[1])) {
            view.setText(view.getText() + "1");
        } else if (e.getSource().equals(btns[2])) {
            view.setText(view.getText() + "2");
        } else if (e.getSource().equals(btns[3])) {
            view.setText(view.getText() + "3");
        } else if (e.getSource().equals(btns[4])) {
            view.setText(view.getText() + "4");
        } else if (e.getSource().equals(btns[5])) {
            view.setText(view.getText() + "5");
        } else if (e.getSource().equals(btns[6])) {
            view.setText(view.getText() + "6");
        } else if (e.getSource().equals(btns[7])) {
            view.setText(view.getText() + "7");
        } else if (e.getSource().equals(btns[8])) {
            view.setText(view.getText() + "8");
        } else if (e.getSource().equals(btns[9])) {
            view.setText(view.getText() + "9");
        } else if (e.getSource().equals(btns[10])) {
            view.setText(view.getText() + "+");
        } else if (e.getSource().equals(btns[11])) {
            view.setText(view.getText() + "-");
        } else if (e.getSource().equals(btns[12])) {
            view.setText(view.getText() + "*");
        } else if (e.getSource().equals(btns[13])) {
            view.setText(view.getText() + "/");
        } else if (e.getSource().equals(btns[14])) {
            view.setText(evaluate(view.getText()));
        } else if (e.getSource().equals(btns[15])) {
            view.setText(view.getText() + ".");
        } else if (e.getSource().equals(btns[16])) {
            if (!view.getText().isEmpty()) {
                view.setText(view.getText().substring(0, view.getText().length() - 1));
            }
        } else if (e.getSource().equals(btns[17])) {
            if (!view.getText().isEmpty()) {
                view.setText("");
            }
        } else if (e.getSource().equals(btns[18])) {
            view.setText(view.getText() + "N");
        } else if (e.getSource().equals(btns[19])) {
            view.setText(view.getText() + "^");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_0) {
            view.setText(view.getText() + "0");
        } else if (e.getKeyCode() == KeyEvent.VK_1) {
            view.setText(view.getText() + "1");
        } else if (e.getKeyCode() == KeyEvent.VK_2) {
            view.setText(view.getText() + "2");
        } else if (e.getKeyCode() == KeyEvent.VK_3) {
            view.setText(view.getText() + "3");
        } else if (e.getKeyCode() == KeyEvent.VK_4) {
            view.setText(view.getText() + "4");
        } else if (e.getKeyCode() == KeyEvent.VK_5) {
            view.setText(view.getText() + "5");
        } else if (e.getKeyCode() == KeyEvent.VK_6) {
            view.setText(view.getText() + "6");
        } else if (e.getKeyCode() == KeyEvent.VK_7) {
            view.setText(view.getText() + "7");
        } else if (e.getKeyCode() == KeyEvent.VK_8) {
            view.setText(view.getText() + "8");
        } else if (e.getKeyCode() == KeyEvent.VK_9) {
            view.setText(view.getText() + "9");
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            view.setText(view.getText() + "*");
        } else if (e.getKeyCode() == KeyEvent.VK_SLASH) {
            view.setText(view.getText() + "/");
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            view.setText(view.getText() + "+");
        } else if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            view.setText(evaluate(view.getText()));
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            view.setText(view.getText() + "-");
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (!view.getText().isEmpty()) {
                view.setText(view.getText().substring(0, view.getText().length() - 1));
            }
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
            if (!view.getText().isEmpty()) {
                view.setText("");
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DECIMAL) {
            view.setText(view.getText() + ".");
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            view.setText(view.getText() + "^");
        } else if (e.getKeyCode() == KeyEvent.VK_N) {
            view.setText(view.getText() + "N");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}