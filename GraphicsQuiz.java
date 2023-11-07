import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GraphicsQuiz extends JFrame {
    static GLCanvas glcanvas = null;

    public static void main(String[] args) {
        GraphicsQuiz app = new GraphicsQuiz();
        SwingUtilities.invokeLater(
                () -> {
                    app.setVisible(true);
                    glcanvas.requestFocusInWindow();
                }
        );
    }

    GraphicsQuiz() {
        //set the JFrame title
        super("Geometric Shapes");
//kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Quiz1 mmd = new Quiz1();
//only three JOGL lines of code ... and here they are
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(mmd);
        glcanvas.addKeyListener(mmd);
//we'll want this for our repaint requests
        mmd.setGLCanvas(glcanvas);
//add the GLCanvas just like we would any Component
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(1000, 1000);
//center the JFrame on the screen
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
}

class Quiz1 implements GLEventListener, KeyListener {
    GLCanvas glc;
    int x1, y1 = 0;

    public void setGLCanvas(GLCanvas glcanvas) {
        this.glc = glcanvas;
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
        gl.glPointSize(6.0f);
        gl.glColor3f(1, 1, 1);
        gl.glBegin(gl.GL_LINES);
        for (int i = -500; i <= 500; i += 20) {
            gl.glVertex2i(i, 500);
            gl.glVertex2i(i, -500);
        }
        gl.glEnd();
        gl.glBegin(gl.GL_LINES);
        for (int i = -500; i <= 500; i += 20) {
            gl.glVertex2i(500, i);
            gl.glVertex2i(-500, i);
        }
        gl.glEnd();
        gl.glColor3f(1, 0, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(i) + 100 + x1), (int) (100 * Math.sin(i) + 100 + y1));
        }
        gl.glEnd();
        gl.glColor3f(0, 1, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(i) - 45 + x1), (int) (100 * Math.sin(i) + y1));
        }
        gl.glEnd();
        gl.glColor3f(0, 0, 1);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(i) - 50 + x1), (int) (100 * Math.sin(i) + 150 + y1));
        }
        gl.glEnd();
        gl.glColor3f(1, 1, 0);
        gl.glBegin(gl.GL_POLYGON);
        gl.glVertex2i(197 + x1, 125 + y1);
        gl.glVertex2i(250 + x1, -250 + y1);
        gl.glVertex2i(-71 + x1, -97 + y1);
        gl.glEnd();
        if (x1 - 145 > 500) {
            x1 = -750;
        } else if (x1 + 250 < -500) {
            x1 = 645;
        }
        if (y1 + 250 > 500) {
            y1 = 250;
        } else if (y1 - 250 < -500) {
            y1 = -250;
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            y1 += 10;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            y1 -= 10;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            x1 -= 10;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x1 += 10;
        }
        glc.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
