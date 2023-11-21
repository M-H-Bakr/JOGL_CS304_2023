import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class TwoCircles extends JFrame {
    static GLCanvas glcanvas = null;

    public static void main(String[] args) {
        final TwoCircles app = new TwoCircles();
// show what we've done
        SwingUtilities.invokeLater(
                () -> {
                    app.setVisible(true);
                    glcanvas.requestFocusInWindow();
                }
        );
    }

    public TwoCircles() {
//set the JFrame title
        super("Two Circles");
//kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CircleHelper mmd = new CircleHelper();
//only three JOGL lines of code ... and here they are
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(mmd);
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
class CircleHelper implements GLEventListener, MouseMotionListener {
    GLCanvas glc;
    int xc,yc = 0;
    float R,G,B = 0;

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        /*Eq to switch from JFrame with 0 <= x <= width, and 0 <= y <= height
        to GLCanvas with -x` <= xc <= x`, and -y` <= yc <= y` is:
        xc = (2*x*x`/width)-x`, and yc = y`-(2*y*y`/height);
         */
        xc = (int)(2*x*500/width)-500;
        yc = (int)(500-(2*y*500/height));
        double distance = Math.sqrt(Math.pow(xc,2)+Math.pow(yc,2));
        if(distance >= 350){
            R = 0; G = 1; B = 0;
        } else if(distance<=50){
            R = 0; G = 0; B = 1;
        }
        else{
            R = 1; G = 0; B = 0;
        }
        glc.repaint();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(0, 0, 0, 0.0f);
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
        gl.glColor3f(0, 1, 0);
        gl.glBegin(GL.GL_POINTS);
        for(int i=0;i<360;i++){
            gl.glVertex2i((int) (200*Math.cos(i)), (int) (200*Math.sin(i)));
        }
        gl.glEnd();
        gl.glColor3f(R, G, B);
        gl.glBegin(GL.GL_POINTS);
        for(int i=0;i<360;i++){
            gl.glVertex2i((int) (150*Math.cos(i))+xc, (int) (150*Math.sin(i))+yc);
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    public void setGLCanvas(GLCanvas glcanvas) {
    this.glc = glcanvas;
    }
}