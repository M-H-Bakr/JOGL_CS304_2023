import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class XOGame extends JFrame implements MouseListener {
    static String[] theGame = new String[9];
    static GLCanvas glcanvas = null;
    JButton btn = new JButton("new");

    public static void main(String[] args) {
        XOGame app = new XOGame();
        SwingUtilities.invokeLater(
                () -> {
                    app.setVisible(true);
                    glcanvas.requestFocusInWindow();
                }
        );

    }

    public static boolean gameWon(String s, String[] game) {
        return (s.equals(game[0]) && s.equals(game[1]) && s.equals(game[2])) ||
                (s.equals(game[3]) && s.equals(game[4]) && s.equals(game[5])) ||
                (s.equals(game[6]) && s.equals(game[7]) && s.equals(game[8])) ||
                (s.equals(game[0]) && s.equals(game[3]) && s.equals(game[6])) ||
                (s.equals(game[1]) && s.equals(game[4]) && s.equals(game[7])) ||
                (s.equals(game[2]) && s.equals(game[5]) && s.equals(game[8])) ||
                (s.equals(game[0]) && s.equals(game[4]) && s.equals(game[8])) ||
                (s.equals(game[2]) && s.equals(game[4]) && s.equals(game[6]));
    }

    XOGame() {
        super("XO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XOHelper XO = new XOHelper();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(XO);
        glcanvas.addMouseListener(XO);
        XO.setGLCanvas(glcanvas);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(1000, 1000);
        JPanel btnPanel = new JPanel();
        btn.addMouseListener(this);
        btnPanel.add(btn, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
        addMouseListener(this);
        centerWindow(this);
    }

    public void centerWindow(Component frame) {
        Dimension screenSize
                = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        frame.setLocation(
                (screenSize.width - frameSize.width) >> 1,
                (screenSize.height - frameSize.height) >> 1
        );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(btn)) {
            XOHelper.indicator = true;
            for (int i = 0; i < 9; i++) {
                theGame[i] = null;
            }
        }
        glcanvas.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

class XOHelper implements GLEventListener, MouseListener {
    GLCanvas glc;
    static boolean indicator = true;
    int xHigh = 0, xLow = 0, yHigh = 0, yLow = 0, xc = 0, yc = 0;

    public void ranges(int parameter) {
        switch (parameter) {
            case 0:
                xHigh = 235;
                xLow = 85;
                yHigh = 235;
                yLow = 85;
                break;
            case 1:
                xHigh = 235 - 160;
                xLow = 85 - 160;
                yHigh = 235;
                yLow = 85;
                break;
            case 2:
                xHigh = 235 - 320;
                xLow = 85 - 320;
                yHigh = 235;
                yLow = 85;
                break;
            case 3:
                xHigh = 235;
                xLow = 85;
                yHigh = 235 - 160;
                yLow = 85 - 160;
                break;
            case 4:
                xHigh = 235 - 160;
                xLow = 85 - 160;
                yHigh = 235 - 160;
                yLow = 85 - 160;
                break;
            case 5:
                xHigh = 235 - 320;
                xLow = 85 - 320;
                yHigh = 235 - 160;
                yLow = 85 - 160;
                break;
            case 6:
                xHigh = 235;
                xLow = 85;
                yHigh = 235 - 320;
                yLow = 85 - 320;
                break;
            case 7:
                xHigh = 235 - 160;
                xLow = 85 - 160;
                yHigh = 235 - 320;
                yLow = 85 - 320;
                break;
            case 8:
                xHigh = 235 - 320;
                xLow = 85 - 320;
                yHigh = 235 - 320;
                yLow = 85 - 320;
                break;
        }
    }

    public void setGLCanvas(GLCanvas glcanvas) {
        this.glc = glcanvas;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        /*Eq to switch from JFrame with 0 <= x <= width, and 0 <= y <= height
        to GLCanvas with -x` <= xc <= x`, and -y` <= yc <= y` is:
        xc = (2*x*x`/width)-x`, and yc = y`-(2*y*y`/height);
         */
        xc = (int) (2 * x * 500 / width) - 500;
        yc = (int) (500 - (2 * y * 500 / height));
        if (!(XOGame.gameWon("X", XOGame.theGame) || XOGame.gameWon("O", XOGame.theGame))) {
            if (xc < 235 && xc > 85 && yc < 235 && yc > 85) {
                if (XOGame.theGame[0] == null) {
                    indicator = !indicator;
                    XOGame.theGame[0] = indicator ? "O" : "X";
                }
            } else if (xc < 235 - 160 && xc > 85 - 160 && yc < 235 && yc > 85) {
                if (XOGame.theGame[1] == null) {
                    indicator = !indicator;
                    XOGame.theGame[1] = indicator ? "O" : "X";
                }
            } else if (xc < 235 - 320 && xc > 85 - 320 && yc < 235 && yc > 85) {
                if (XOGame.theGame[2] == null) {
                    indicator = !indicator;
                    XOGame.theGame[2] = indicator ? "O" : "X";
                }
            } else if (xc < 235 && xc > 85 && yc < 235 - 160 && yc > 85 - 160) {
                if (XOGame.theGame[3] == null) {
                    indicator = !indicator;
                    XOGame.theGame[3] = indicator ? "O" : "X";
                }
            } else if (xc < 235 - 160 && xc > 85 - 160 && yc < 235 - 160 && yc > 85 - 160) {
                if (XOGame.theGame[4] == null) {
                    indicator = !indicator;
                    XOGame.theGame[4] = indicator ? "O" : "X";
                }
            } else if (xc < 235 - 320 && xc > 85 - 320 && yc < 235 - 160 && yc > 85 - 160) {
                if (XOGame.theGame[5] == null) {
                    indicator = !indicator;
                    XOGame.theGame[5] = indicator ? "O" : "X";
                }
            } else if (xc < 235 && xc > 85 && yc < 235 - 320 && yc > 85 - 320) {
                if (XOGame.theGame[6] == null) {
                    indicator = !indicator;
                    XOGame.theGame[6] = indicator ? "O" : "X";
                }
            } else if (xc < 235 - 160 && xc > 85 - 160 && yc < 235 - 320 && yc > 85 - 320) {
                if (XOGame.theGame[7] == null) {
                    indicator = !indicator;
                    XOGame.theGame[7] = indicator ? "O" : "X";
                }
            } else if (xc < 235 - 320 && xc > 85 - 320 && yc < 235 - 320 && yc > 85 - 320) {
                if (XOGame.theGame[8] == null)
                    indicator = !indicator;
                {
                    XOGame.theGame[8] = indicator ? "O" : "X";
                }
            }
            glc.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (XOGame.gameWon("X", XOGame.theGame) || XOGame.gameWon("O", XOGame.theGame)) {
            Object[] options = {"Yes", "No"};
            int choice;
            if (XOGame.gameWon("X", XOGame.theGame)) {
                choice = JOptionPane.showOptionDialog(null,
                        "Winner is X\nStart a new game?", "Congrats!",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
            } else {
                choice = JOptionPane.showOptionDialog(null,
                        "Winner is O\nStart a new game?", "Congrats!",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
            }
            if (choice == JOptionPane.YES_OPTION) {
                for (int i = 0; i < 9; i++) {
                    XOGame.theGame[i] = null;
                }
                glc.repaint();
                indicator = true;
            }/*else {
             System.exit(0);
         }*/
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0, 0, 0.0f, 0.0f);
        gl.glViewport(0, 0, 100, 100);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-500, 500, -500, 500, -1, 1);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glPointSize(1.0f);
        gl.glColor3f(1, 1, 1);
        gl.glBegin(gl.GL_LINES);
        gl.glVertex2i(250, 250);
        gl.glVertex2i(-250, 250);
        gl.glVertex2i(-250, 250);
        gl.glVertex2i(-250, -250);
        gl.glVertex2i(-250, -250);
        gl.glVertex2i(250, -250);
        gl.glVertex2i(250, -250);
        gl.glVertex2i(250, 250);
        for (int i = 0; i <= 320; i += 160) {
            for (int j = 0; j <= 320; j += 160) {
                gl.glVertex2i(235 - i, 235 - j);
                gl.glVertex2i(85 - i, 235 - j);
                gl.glVertex2i(85 - i, 235 - j);
                gl.glVertex2i(85 - i, 85 - j);
                gl.glVertex2i(85 - i, 85 - j);
                gl.glVertex2i(235 - i, 85 - j);
                gl.glVertex2i(235 - i, 85 - j);
                gl.glVertex2i(235 - i, 235 - j);
            }
        }
        gl.glEnd();
        for (int i = 0; i < 9; i++) {
            if (XOGame.theGame[i] != null) {
                ranges(i);
                if (XOGame.theGame[i].equals("X")) {
                    gl.glBegin(gl.GL_LINES);
                    gl.glVertex2i(xHigh - 15, yHigh - 15);
                    gl.glVertex2i(xLow + 15, yLow + 15);
                    gl.glVertex2i(xLow + 15, yHigh - 15);
                    gl.glVertex2i(xHigh - 15, yLow + 15);
                    gl.glEnd();
                } else {
                    gl.glBegin(gl.GL_LINES);
                    for (int j = 0; j < 360; j++) {
                        gl.glVertex2i((int) (65 * Math.cos(Math.toRadians(j))
                                        + (xHigh + xLow) / 2),
                                (int) (65 * Math.sin(Math.toRadians(j))
                                        + (yHigh + yLow) / 2));
                    }
                    gl.glEnd();
                }
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
}