import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GraphicsQuiz2 extends JFrame {

    public static void main(String[] args) {
        new GraphicsQuiz2();
    }


    public GraphicsQuiz2() {
        GLCanvas glcanvas;
        Animator animator;

        Quiz2 listener = new Quiz2();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
        setTitle("Quiz2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}

class Quiz2 implements GLEventListener {
    int x1 = 0, y1 = 0, angle, xDirection = 10, yDirection = 10, angle2;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1, 1, 1.0f, 1.0f);
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
        //face
        gl.glColor3f(1, 1, 0);
        gl.glPushMatrix();
        gl.glTranslated(x1, +y1, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(Math.toRadians(i))),
                    (int) (100 * Math.sin(Math.toRadians(i))));
        }
        gl.glEnd();
        gl.glPopMatrix();
        //mouth
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
        gl.glTranslated(x1, -40 + y1, 0);
        gl.glScaled(0.45, 0.45, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(Math.toRadians(i))),
                    (int) (100 * Math.sin(Math.toRadians(i))));
        }
        gl.glEnd();
        gl.glPopMatrix();
        //right eye
        gl.glColor3f(1, 1, 1);
        gl.glPushMatrix();
        gl.glTranslated(40 + x1, 45 + y1, 0);
        gl.glScaled(0.4, 0.4, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(Math.toRadians(i))),
                    (int) (100 * Math.sin(Math.toRadians(i))));
        }
        gl.glEnd();
        gl.glPopMatrix();
        //left eye
        gl.glPushMatrix();
        gl.glTranslated(-40 + x1, 45 + y1, 0);
        gl.glScaled(0.4, 0.4, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(Math.toRadians(i))),
                    (int) (100 * Math.sin(Math.toRadians(i))));
        }
        gl.glEnd();
        gl.glPopMatrix();
        //left pupil
        angle2 += 10;
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
        gl.glTranslated(-40 + x1 + 15 * Math.cos(Math.toRadians(angle2 + 180)), 45 + y1 + 15 * Math.sin(Math.toRadians(angle2 + 180)), 0);
        gl.glScaled(0.2, 0.2, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(Math.toRadians(i))),
                    (int) (100 * Math.sin(Math.toRadians(i))));
        }
        gl.glEnd();
        gl.glPopMatrix();
        //right pupil
        gl.glColor3f(0, 0, 0);
        gl.glPushMatrix();
        gl.glTranslated(40 + x1 + 15 * Math.cos(Math.toRadians(-angle2)), 45 + y1 + 15 * Math.sin(Math.toRadians(-angle2)), 0);
        gl.glScaled(0.2, 0.2, 0);
        gl.glBegin(gl.GL_POLYGON);
        for (int i = 0; i < 360; i++) {
            gl.glVertex2i((int) (100 * Math.cos(Math.toRadians(i))),
                    (int) (100 * Math.sin(Math.toRadians(i))));
        }
        gl.glEnd();
        gl.glPopMatrix();
        //if (collision) : given angle a random value between 0 and 90 (inclusive).
        if (x1 + 100 >= 500) {
            angle = new Random().nextInt(0, 91);
            xDirection = -10;
        }
        if (y1 + 100 >= 500) {
            angle = new Random().nextInt(0, 91);
            yDirection = -10;
        }
        if (x1 - 100 <= -500) {
            angle = new Random().nextInt(0, 91);
            xDirection = 10;
        }
        if (y1 - 100 <= -500) {
            angle = new Random().nextInt(0, 91);
            yDirection = 10;
        }
        x1 += (int) (xDirection * Math.cos(Math.toRadians(angle)));
        y1 += (int) (yDirection * Math.sin(Math.toRadians(angle)));
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
}
