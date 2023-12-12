import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("InfiniteLoopStatement")
public class Menu extends JFrame {
    public static void main(String[] args) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException, InterruptedException {
        //Put the mazes in place.
        Variables.easy.add(Variables.Maze1);
        Variables.easy.add(Variables.Maze2);
        Variables.easy.add(Variables.Maze3);
        Variables.normal.add(Variables.Maze4);
        Variables.normal.add(Variables.Maze5);
        Variables.normal.add(Variables.Maze6);
        Variables.hard.add(Variables.Maze7);
        Variables.hard.add(Variables.Maze8);
        Variables.hard.add(Variables.Maze9);
        Variables.Mazes.add(Variables.easy);
        Variables.Mazes.add(Variables.normal);
        Variables.Mazes.add(Variables.hard);
        new Menu();
        Thread.sleep(4000); //Let the frame take time to bind so the song begins normally.
        Variables.clip.open(Variables.audioStream);
        Variables.clip.start();
        //loop the clip:
        while (true) {
            if (Variables.clip.getMicrosecondPosition() == Variables.clip.getMicrosecondLength()) {
                Variables.audioStream = AudioSystem.getAudioInputStream(Variables.file);
                Variables.clip = AudioSystem.getClip();
                Variables.clip.open(Variables.audioStream);
                Variables.clip.start();
            }
        }
    }

    Menu() {
        GLCanvas glcanvas = new GLCanvas();
        GLMenu listener = new GLMenu();
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        Variables.menu_Animator.add(glcanvas);
        Variables.menu_Animator.start();
        glcanvas.requestFocus();
    }
}

@SuppressWarnings({"ThrowablePrintedToSystemOut", "CallToPrintStackTrace"})
class GLMenu implements GLEventListener, MouseListener {

    //Canvas Coordinates:
    double xCanvas = 0, yCanvas = 0;
    //Scores and info:
    StringBuilder players_Scores = new StringBuilder();
    LinkedHashMap<String, Long> Scores;

    //Textures:
    String[] textureNames = {"black-square-clipart-8.png", "R (1).png",
            "InShot_20231205_163202992.png", "InShot_20231205_163220184.png",
            "InShot_20231205_163234143.png", "InShot_20231205_163426471.png",
            "volume_318-757784.png", "mute-png-mute-icon-1600.png",
            "InShot_20231205_163350314.png", "InShot_20231205_163400478.png",
            "InShot_20231205_163410601.png", "InShot_20231208_113813061.png",
            "InShot_20231208_113826914.png", "InShot_20231208_113836297.png",
            "InShot_20231212_150551743.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture("src" +
                        "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels()
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        //Background:
        drawAssets(gl, 0, 0, 0, 1.18);
        //Maze-Image:
        drawAssets(gl, 1, -0.3, 0, 0.5);
        if (!Variables.selected_Mode) {
            //Single
            drawAssets(gl, 2, 0.5, 0.3, 0.15);
            //Multi
            drawAssets(gl, 3, 0.5, 0.1, 0.15);
            //Vs AI
            drawAssets(gl, 4, 0.5, -0.1, 0.15);
        } else {
            //Easy
            drawAssets(gl, 8, 0.5, 0.3, 0.15);
            //Medium
            drawAssets(gl, 9, 0.5, 0.1, 0.15);
            //Hard
            drawAssets(gl, 10, 0.5, -0.1, 0.15);
        }
        //Back:
        drawAssets(gl, 14, 0.5, 0.7, 0.1);
        //Instructions:
        drawAssets(gl, 5, 0.5, -0.3, 0.15);
        //HighScores ُEasy:
        drawAssets(gl, 11, -0.3, -0.7, 0.15);
        //HighScores ُNormal:
        drawAssets(gl, 12, 0, -0.7, 0.15);
        //HighScores ُHard:
        drawAssets(gl, 13, 0.3, -0.7, 0.15);
        //Sound:
        if (!Variables.muted) {
            //Unmuted:
            drawAssets(gl, 6, -0.5, 0.7, 0.1);
        } else {
            //Muted:
            drawAssets(gl, 7, -0.5, 0.7, 0.1);
        }
        //Game Began.
        if (Variables.levelSelection > -1) {
            Variables.menu_disabled = true;
            Variables.menu_Animator.stop();
        }
    }

    public void drawAssets(GL gl, int i, double x, double y, double scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);
        gl.glScaled(scale, scale, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        /* Eq to switch from JFrame with 0 <= x <= width, and 0 <= y <= height
        to GLCanvas with -x` <= xc <= x`, and -y` <= yc <= y` is:
        xc = (2*x*x`/width)-x`, and yc = y`-(2*y*y`/height). */
        xCanvas = (2 * x / width) - 1;
        yCanvas = 1 - (2 * y / height);
        if (Variables.menu_disabled) {
            xCanvas = 0;
            yCanvas = 0;
        } else if (xCanvas <= 0.58 && xCanvas >= 0.41 && yCanvas <= 0.73 && yCanvas >= 0.65) {
            //Back:
            Variables.menu_Animator.stop();
            Variables.game_Animator.start();
            Variables.re_Initialize();
        } else if (xCanvas <= 0.66 && xCanvas >= 0.36 && yCanvas >= 0.25 && yCanvas <= 0.33) {
            //Modes:
            if (!Variables.selected_Mode) {
                Variables.selected_Mode = true;
                Variables.Num_Players = 1;
            } else {
                //Levels:
                Variables.levelSelection = 0;
                Variables.player1_Lives = 3;
                Variables.max_Minutes = 3;
                if (Variables.Num_Players == 2) {
                    Variables.player2_Lives = 3;
                    Variables.player1 = JOptionPane.showInputDialog("Player1:");
                    Variables.player2 = JOptionPane.showInputDialog("Player2:");
                } else if (Variables.Num_Players == 1) {
                    Variables.player1 = JOptionPane.showInputDialog("Player1:");
                }
                gameFrame.game = new gameFrame();
                Variables.menu_disabled = true;
            }
        } else if (xCanvas <= 0.66 && xCanvas >= 0.36 && yCanvas >= 0.066 && yCanvas <= 0.133) {
            if (!Variables.selected_Mode) {
                //Modes:
                Variables.selected_Mode = true;
                Variables.Num_Players = 2;
            } else {
                //Levels:
                Variables.levelSelection = 1;
                Variables.player1_Lives = 2;
                Variables.max_Minutes = 2;
                if (Variables.Num_Players == 2) {
                    Variables.player1 = JOptionPane.showInputDialog("Player1:");
                    Variables.player2 = JOptionPane.showInputDialog("Player2:");
                    Variables.player2_Lives = 2;
                } else if (Variables.Num_Players == 1) {
                    Variables.player1 = JOptionPane.showInputDialog("Player1:");
                }
                gameFrame.game = new gameFrame();
                Variables.menu_disabled = true;
            }
        } else if (xCanvas <= 0.66 && xCanvas >= 0.36 && yCanvas >= -0.133 && yCanvas <= -0.066) {
            if (!Variables.selected_Mode) {
                //Modes:
                Variables.selected_Mode = true;
                Variables.selectedAI = true;
                Variables.Num_Players = 1;
            } else {
                //Levels:
                Variables.levelSelection = 2;
                Variables.player1_Lives = 1;
                Variables.max_Minutes = 1;
                if (Variables.Num_Players == 2) {
                    Variables.player1 = JOptionPane.showInputDialog("Player1:");
                    Variables.player2 = JOptionPane.showInputDialog("Player2:");
                    Variables.player2_Lives = 1;
                } else if (Variables.Num_Players == 1) {
                    Variables.player1 = JOptionPane.showInputDialog("Player1:");
                }
                gameFrame.game = new gameFrame();
                Variables.menu_disabled = true;
            }
        } else if (xCanvas <= 0.66 && xCanvas >= 0.36 && yCanvas >= -0.33 && yCanvas <= -0.25) {
            //Instructions:
            JOptionPane.showMessageDialog(null, """
                            The_Maze is a 2D game consisting of 3 level selections:
                                                        
                            Easy, Normal, and Hard with 3 different rounds for each.
                                                        
                            Three modes: Single, Multi, and Vs AI.
                                                        
                            In the beginning you get trapped in the initial position.
                                                        
                            The Ultimate objective (Winning) is to capture the star at the end of the maze.
                                                        
                            Losing conditions: Wasted lives by collision with walls, Reaching max time,
                            Or not being the first to capture the star (Applies only in Vs AI and Multi).
                                                        
                            If a player died in a multiplayer game the maze becomes unsafe (Red)
                            Hence making it hard to know when a collision happens for the other player
                            the maze also becomes unsafe if a collision happened until the player who
                            collided moves correctly, also in multi player:
                            The player ending the last round first is considered the winner.
                                                        
                            A wall randomly appears every 2 seconds as a trap
                            (Not necessarily at the first step when playing VS AI)
                            so be careful.
                                
                            You're given for the modes: Easy, Medium, Hard...
                            Max time (in minutes until the game ends) as follows:
                            3, 2, and 1, also lives (until the game ends)
                            3, 2 and 1 all respectively.
                                                        
                            Score is calculated for each round as follows:
                            Seconds remaining of total time * 100 + Lives remaining * 100.
                                 
                            When playing against AI it moves a step every second.
                            In order to win Against it: you have to be as fast and as sharp...
                            As the AI moves correctly every time so it will eventually win
                            if you didn't do it first.
                                                        
                            Controls: for player1 WASD, for player2  IJKL that is Up Left Down Right respectively.
                                         
                                                        
                            Happy Gaming!
                                                        
                            Game Created by: Muhammad H. Bakr, Kareem Amin, Omar Goda, and Muhammad Ashraf."""
                    ,
                    "INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE);
        } else if (xCanvas <= -0.4 && x >= -0.6 && yCanvas >= 0.6 && yCanvas <= 0.8) {
            //Mute:
            if (!Variables.muted) {
                Variables.muted = true;
                Variables.clipTime = Variables.clip.getMicrosecondPosition();
                Variables.clip.stop();
            } else {
                //Unmute:
                Variables.muted = false;
                Variables.clip.setMicrosecondPosition(Variables.clipTime);
                Variables.clip.start();
            }
        } else if (yCanvas <= -0.62 && yCanvas >= -0.78 && xCanvas <= -0.17 && xCanvas >= -0.42) {
            //HighScores Easy:
            Scores = Variables.HighScores_Easy.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Long> entry : Scores.entrySet()) {
                players_Scores.append("Player: ").append(entry.getKey()).append(", Score: ").append(entry.getValue()).append(".\n");
            }
            JOptionPane.showMessageDialog(null, players_Scores, "High Scores", JOptionPane.INFORMATION_MESSAGE);
            while (!players_Scores.isEmpty()) {
                players_Scores.deleteCharAt(0);
            }
        } else if (yCanvas <= -0.62 && yCanvas >= -0.78 && xCanvas >= -0.12 && xCanvas <= 0.12) {
            //HighScores Normal:
            Scores = Variables.HighScores_Normal.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Long> entry : Scores.entrySet()) {
                players_Scores.append("Player: ").append(entry.getKey()).append(", Score: ").append(entry.getValue()).append(".\n");
            }
            JOptionPane.showMessageDialog(null, players_Scores, "High Scores", JOptionPane.INFORMATION_MESSAGE);
            while (!players_Scores.isEmpty()) {
                players_Scores.deleteCharAt(0);
            }
        } else if (yCanvas <= -0.62 && yCanvas >= -0.78 && xCanvas >= 0.16 && xCanvas <= 0.42) {
            //HighScores Hard:
            Scores = Variables.HighScores_Hard.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            for (Map.Entry<String, Long> entry : Scores.entrySet()) {
                players_Scores.append("Player: ").append(entry.getKey()).append(", Score: ").append(entry.getValue()).append(".\n");
            }
            JOptionPane.showMessageDialog(null, players_Scores, "High Scores", JOptionPane.INFORMATION_MESSAGE);
            while (!players_Scores.isEmpty()) {
                players_Scores.deleteCharAt(0);
            }
        }
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
