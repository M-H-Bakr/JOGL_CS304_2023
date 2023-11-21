import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Geometry extends JFrame {
    static GLCanvas glcanvas = null;

    public static void main(String[] args) {
        final Geometry app = new Geometry();
// show what we've done
        SwingUtilities.invokeLater(
                () -> {
                    app.setVisible(true);
                    glcanvas.requestFocusInWindow();
                }
        );
    }

    public Geometry() {
//set the JFrame title
        super("Geometric Shapes");
//kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GeometryHelper mmd = new GeometryHelper();
//only three JOGL lines of code ... and here they are
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(mmd);
        glcanvas.addMouseListener(mmd);
        glcanvas.addMouseMotionListener(mmd);
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

class GeometryHelper implements GLEventListener, MouseListener, MouseMotionListener {
    GLCanvas glc;
    int x1, y1, x2, y2 = 0;

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
        x2 = (int) (2 * x * 500 / width) - 500;
        y2 = (int) (500 - (2 * y * 500 / height));
        glc.repaint();
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
        gl.glColor3f(1, 0, 0);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2i(x1, y1);
        gl.glVertex2i(x1, y2);
        gl.glVertex2i(x1, y2);
        gl.glVertex2i(x2, y2);
        gl.glVertex2i(x2, y2);
        gl.glVertex2i(x2, y1);
        gl.glVertex2i(x2, y1);
        gl.glVertex2i(x1, y1);
        gl.glEnd();
        gl.glBegin(gl.GL_POINTS);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) ((x1 - x2) / 2 * Math.cos(i) + (x1 + x2) / 2),
                    (int) ((y1 - y2) / 2 * Math.sin(i) + (y1 + y2) / 2));
        }
        gl.glEnd();
        if (y1 < y2) {
            gl.glBegin(gl.GL_LINES);
            gl.glVertex2i(x2, y2);
            gl.glVertex2i(x1, y2);
            gl.glVertex2i(x1, y2);
            gl.glVertex2i((x1 + x2) / 2, 2 * y2 - y1);
            gl.glVertex2i((x1 + x2) / 2, 2 * y2 - y1);
            gl.glVertex2i(x2, y2);
            gl.glEnd();
        } else {
            gl.glBegin(gl.GL_LINES);
            gl.glVertex2i(x2, y1);
            gl.glVertex2i(x1, y1);
            gl.glVertex2i(x1, y1);
            gl.glVertex2i((x1 + x2) / 2, 2 * y1 - y2);
            gl.glVertex2i((x1 + x2) / 2, 2 * y1 - y2);
            gl.glVertex2i(x2, y1);
            gl.glEnd();
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        /*Eq to switch from JFrame with 0 <= x <= width, and 0 <= y <= height
        to GLCanvas with -x` <= xc <= x`, and -y` <= yc <= y` is:
        xc = (2*x*x`/width)-x`, and yc = y`-(2*y*y`/height);
         */
        x1 = (int) (2 * x * 500 / width) - 500;
        y1 = (int) (500 - (2 * y * 500 / height));
        glc.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void setGLCanvas(GLCanvas glcanvas) {
        this.glc = glcanvas;
    }
}