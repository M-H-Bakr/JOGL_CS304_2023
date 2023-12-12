import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class gameFrame extends JFrame implements WindowListener {
    public static gameFrame game;

    static JLabel timer = new JLabel(), playerName = new JLabel(), lives = new JLabel(), score = new JLabel(),
            player2Name = new JLabel(), player2lives = new JLabel(), player2score = new JLabel();

    gameFrame() {
        GLCanvas glcanvas = new GLCanvas();
        frameHelper listener = new frameHelper();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(timer);
        panel.add(playerName);
        panel.add(lives);
        panel.add(score);
        panel.add(player2Name);
        panel.add(player2lives);
        panel.add(player2score);
        add(panel, BorderLayout.NORTH);
        setTitle("The Maze");
        setSize(1000, 1000);
        addWindowListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
        Variables.game_Animator.add(glcanvas);
        Variables.game_Animator.start();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        //Re-initialize everything used:
        Variables.re_Initialize();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

@SuppressWarnings({"ThrowablePrintedToSystemOut", "CallToPrintStackTrace"})
class frameHelper implements GLEventListener, KeyListener, MouseListener {
    double xCanvas = 0, yCanvas = 0;
    long score = 0, score2 = 0;
    int color = 1;
    int rotate = 0;
    String[] textureNames = {"black-square-clipart-8.png", "blue-square-png-13.png", "red-square-png-14.png", "volume_318-757784.png",
            "mute-png-mute-icon-1600.png", "Vector-Pause-Button-PNG.png", "R (2).png", "white-circle.png", "R.png", "colours-05.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];

    frameHelper() {
        //initialize first round:
        PathTraversal.deepCopy(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze), Variables.player1_Maze_Copy);
        Variables.c_Player1.x = 1;
        Variables.c_Player1.y = 1;
        if (Variables.selectedAI) {
            PathTraversal.deepCopy(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze), Variables.AI_Maze_Copy);
            Variables.c_AI.x = 1;
            Variables.c_AI.y = 1;
            Variables.path_AI = PathTraversal.findPath(Variables.AI_Maze_Copy);
            Variables.AI_Step = 0;
            Variables.randomizer = 0;
        } else if (Variables.Num_Players == 2) {
            PathTraversal.deepCopy(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze), Variables.player2_Maze_Copy);
            Variables.c_Player2.x = 1;
            Variables.c_Player2.y = 1;
        }
        Variables.spaces = PathTraversal.emptyCells(Variables.player1_Maze_Copy);
        Variables.start_Time = System.currentTimeMillis() + 2500;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Check if game is paused:
        if (!Variables.stopped && Variables.levelSelection > -1) {
            //Player1 moves if he has lives:
            if (Variables.player1_Lives != 0) {
                switch (e.getKeyCode()) {
                    //Move left:
                    case KeyEvent.VK_A:
                        if (Variables.c_Player1.x > 0) {
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "S";
                            Variables.c_Player1.x--;
                            PathTraversal.validateMove(Variables.player1_Maze_Copy, Variables.c_Player1,
                                    Variables.player1_Collided, Variables.player1_Win);
                            if (Variables.player1_Collided.exp) {
                                Variables.c_Player1.x++;
                            }
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "P";
                        }
                        break;
                    //Move up:
                    case KeyEvent.VK_W:
                        if (Variables.c_Player1.y > 0) {
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "S";
                            Variables.c_Player1.y--;
                            PathTraversal.validateMove(Variables.player1_Maze_Copy, Variables.c_Player1,
                                    Variables.player1_Collided, Variables.player1_Win);
                            if (Variables.player1_Collided.exp) {
                                Variables.c_Player1.y++;
                            }
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "P";
                        }
                        break;
                    //Move right:
                    case KeyEvent.VK_D:
                        if (Variables.c_Player1.x < Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze).length - 1) {
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "S";
                            Variables.c_Player1.x++;
                            PathTraversal.validateMove(Variables.player1_Maze_Copy, Variables.c_Player1,
                                    Variables.player1_Collided, Variables.player1_Win);
                            if (Variables.player1_Collided.exp) {
                                Variables.c_Player1.x--;
                            }
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "P";
                        }
                        break;
                    //Move down:
                    case KeyEvent.VK_S:
                        if (Variables.c_Player1.y < Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze)[0].length - 1) {
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "S";
                            Variables.c_Player1.y++;
                            PathTraversal.validateMove(Variables.player1_Maze_Copy, Variables.c_Player1,
                                    Variables.player1_Collided, Variables.player1_Win);
                            if (Variables.player1_Collided.exp) {
                                Variables.c_Player1.y--;
                            }
                            Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "P";
                        }
                        break;
                }
            }
            //Player2 moves if existent:
            if (Variables.player2_Lives != 0) {
                switch (e.getKeyCode()) {
                    //Move left:
                    case KeyEvent.VK_J:
                        if (Variables.c_Player2.x > 0) {
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "S";
                            Variables.c_Player2.x--;
                            PathTraversal.validateMove(Variables.player2_Maze_Copy, Variables.c_Player2,
                                    Variables.player2_Collided, Variables.player2_Win);
                            if (Variables.player2_Collided.exp) {
                                Variables.c_Player2.x++;
                            }
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "P";
                        }
                        break;
                    //Move up:
                    case KeyEvent.VK_I:
                        if (Variables.c_Player2.y > 0) {
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "S";
                            Variables.c_Player2.y--;
                            PathTraversal.validateMove(Variables.player2_Maze_Copy, Variables.c_Player2,
                                    Variables.player2_Collided, Variables.player2_Win);
                            if (Variables.player2_Collided.exp) {
                                Variables.c_Player2.y++;
                            }
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "P";
                        }
                        break;
                    //Move right:
                    case KeyEvent.VK_L:
                        if (Variables.c_Player2.x < Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze).length - 1) {
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "S";
                            Variables.c_Player2.x++;
                            PathTraversal.validateMove(Variables.player2_Maze_Copy, Variables.c_Player2,
                                    Variables.player2_Collided, Variables.player2_Win);
                            if (Variables.player2_Collided.exp) {
                                Variables.c_Player2.x--;
                            }
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "P";
                        }
                        break;
                    //Move down:
                    case KeyEvent.VK_K:
                        if (Variables.c_Player2.y < Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze)[0].length - 1) {
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "S";
                            Variables.c_Player2.y++;
                            PathTraversal.validateMove(Variables.player2_Maze_Copy, Variables.c_Player2,
                                    Variables.player2_Collided, Variables.player2_Win);
                            if (Variables.player2_Collided.exp) {
                                Variables.c_Player2.y--;
                            }
                            Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "P";
                        }
                        break;
                }
            }
            if (Variables.player1_Collided.exp || Variables.player2_Collided.exp) {
                //A collision happened:
                color = 2;
                if (Variables.player1_Collided.exp && Variables.player1_Lives > 0) {
                    //PLayer1 loses a life:
                    Variables.player1_Lives--;
                }
                if (Variables.player2_Collided.exp && Variables.player2_Lives > 0) {
                    //Player2 loses a life (That is... he is existent):
                    Variables.player2_Lives--;
                }
                //Players lost because of lives:
                if (Variables.player1_Lives == 0 && Variables.Num_Players == 1) {
                    //Close the game it's a loss for a single player or Vs AI:
                    Variables.re_Initialize();
                    JOptionPane.showMessageDialog(null, "Losing game.",
                            "Losing Game", JOptionPane.INFORMATION_MESSAGE);
                    gameFrame.game.dispose();
                } else if (Variables.Num_Players == 2) {
                    if (Variables.player1_Lives == 0 && Variables.player2_Lives == 0) {
                        //Close the game as Both players are unalive:
                        Variables.re_Initialize();
                        JOptionPane.showMessageDialog(null, "Losing game.",
                                "Losing Game", JOptionPane.INFORMATION_MESSAGE);
                        gameFrame.game.dispose();
                    } else if (Variables.player1_Lives == 0) {
                        //Turns into a single player for player2:
                        Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "S";
                    } else if (Variables.player2_Lives == 0) {
                        //Turns into a single player for player1:
                        Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "S";
                    }
                }
            } else {
                color = 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Single player:
        if (Variables.player1_Win.exp && Variables.current_Maze == 2) {
            //Game levels successfully passed by player1, save game data and re-initialize everything used:
            if (Variables.player1 == null || Variables.player1.isEmpty()) {
                Variables.player1 = "#N/A";
            }
            score += (Variables.max_Minutes * 60 - Variables.minutes * 60 - Variables.seconds) * 100 + Variables.player1_Lives * 100L;
            switch (Variables.levelSelection) {
                case 0:
                    if ((Variables.HighScores_Easy.get(Variables.player1) != null && Variables.HighScores_Easy.get(Variables.player1) < score)
                            || Variables.HighScores_Easy.get(Variables.player1) == null) {
                        Variables.HighScores_Easy.put(Variables.player1, score);
                    }
                    break;
                case 1:
                    if ((Variables.HighScores_Normal.get(Variables.player1) != null && Variables.HighScores_Normal.get(Variables.player1) < score)
                            || Variables.HighScores_Normal.get(Variables.player1) == null) {
                        Variables.HighScores_Normal.put(Variables.player1, score);
                    }
                    break;
                case 2:
                    if ((Variables.HighScores_Hard.get(Variables.player1) != null && Variables.HighScores_Hard.get(Variables.player1) < score)
                            || Variables.HighScores_Hard.get(Variables.player1) == null) {
                        Variables.HighScores_Hard.put(Variables.player1, score);
                    }
                    break;
            }
            Variables.re_Initialize();
            JOptionPane.showMessageDialog(null, "Winning Game!"
                    , "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            gameFrame.game.dispose();
        } else if ((Variables.player1_Win.exp || Variables.player2_Win.exp)
                && Variables.current_Maze < 2 && ((Variables.c_Player1.x != 1 && Variables.c_Player1.y != 1)
                || (Variables.c_Player2.x != 1 && Variables.c_Player2.y != 1))) {
            //Only current round passed initialize for next round:
            Variables.game_Animator.stop();
            Variables.current_Maze++;
            Variables.c_Player1.x = 1;
            Variables.c_Player1.y = 1;
            if (Variables.player1_Win.exp) {
                //Player1 Won:
                score += (Variables.max_Minutes * 60 - Variables.minutes * 60 - Variables.seconds) * 100 + Variables.player1_Lives * 100L;
            } else if (Variables.player2_Win.exp) {
                //Player2 Won:
                score2 += (Variables.max_Minutes * 60 - Variables.minutes * 60 - Variables.seconds) * 100 + Variables.player2_Lives * 100L;
            }
            Variables.spaces = PathTraversal.emptyCells(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze));
            PathTraversal.deepCopy(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze), Variables.player1_Maze_Copy);
            if (Variables.Num_Players == 2) {
                //Initialize player2 next game:
                Variables.c_Player2.x = 1;
                Variables.c_Player2.y = 1;
                PathTraversal.deepCopy(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze), Variables.player2_Maze_Copy);
            } else if (Variables.selectedAI) {
                //Initialize AI next game:
                Variables.c_AI.x = 1;
                Variables.c_AI.y = 1;
                PathTraversal.deepCopy(Variables.Mazes.get(Variables.levelSelection).get(Variables.current_Maze), Variables.AI_Maze_Copy);
                Variables.path_AI = PathTraversal.findPath(Variables.AI_Maze_Copy);
                Variables.AI_Step = 0;
            }
            Variables.player1_Win.exp = false;
            Variables.player2_Win.exp = false;
            Variables.game_Animator.start();
        } else if (Variables.player2_Win.exp && Variables.current_Maze == 2) {
            //Game levels successfully passed by player2, save game data and re-initialize everything used:
            if (Variables.player2 == null || Variables.player2.isEmpty()) {
                Variables.player2 = "#N/A";
            }
            score2 += (Variables.max_Minutes * 60 - Variables.minutes * 60 - Variables.seconds) * 100 + Variables.player2_Lives * 100L;
            switch (Variables.levelSelection) {
                case 0:
                    if ((Variables.HighScores_Easy.get(Variables.player2) != null && Variables.HighScores_Easy.get(Variables.player2) < score)
                            || Variables.HighScores_Easy.get(Variables.player2) == null) {
                        Variables.HighScores_Easy.put(Variables.player2, score);
                    }
                    break;
                case 1:
                    if ((Variables.HighScores_Normal.get(Variables.player2) != null && Variables.HighScores_Normal.get(Variables.player2) < score)
                            || Variables.HighScores_Normal.get(Variables.player2) == null) {
                        Variables.HighScores_Normal.put(Variables.player2, score);
                    }
                    break;
                case 2:
                    if ((Variables.HighScores_Hard.get(Variables.player2) != null && Variables.HighScores_Hard.get(Variables.player2) < score)
                            || Variables.HighScores_Hard.get(Variables.player2) == null) {
                        Variables.HighScores_Hard.put(Variables.player2, score);
                    }
                    break;
            }
            Variables.re_Initialize();
            JOptionPane.showMessageDialog(null, "Winning Game!"
                    , "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            gameFrame.game.dispose();
        }
    }

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
        //The Game:
        //info about player1:
        gameFrame.lives.setText("Lives: " + Variables.player1_Lives + ".");
        gameFrame.score.setText("Score: " + score + ".");
        if (Variables.player1 == null || Variables.player1.isEmpty()) {
            gameFrame.playerName.setText("Player1 Name: #N/A.");
        } else {
            gameFrame.playerName.setText("Player1 Name: " + Variables.player1 + ".");
        }
        //Player1:
        if (Variables.levelSelection > -1) {
            for (int i = 0; i < Variables.player1_Maze_Copy.length; i++) {
                for (int j = 0; j < Variables.player1_Maze_Copy[0].length; j++) {
                    switch (Variables.player1_Maze_Copy[i][j]) {
                        case "W" -> drawAssets(gl, color, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.1);
                        case "P" -> drawAssets(gl, 7, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.08);
                        case "E" -> drawStar(gl, rotate, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.067);
                    }
                }
            }
        }
        if (Variables.Num_Players == 2) {
            if (Variables.player1_Lives == 0) {
                //Turns into a single player for player2:
                Variables.player1_Maze_Copy[Variables.c_Player1.x][Variables.c_Player1.y] = "S";
            } else if (Variables.player2_Lives == 0) {
                //Turns into a single player for player1:
                Variables.player2_Maze_Copy[Variables.c_Player2.x][Variables.c_Player2.y] = "S";
            }
            //Player2 info:
            gameFrame.player2lives.setText("Lives: " + Variables.player2_Lives + ".");
            gameFrame.player2score.setText("Score: " + score2 + ".");
            if (Variables.player2 == null || Variables.player2.isEmpty()) {
                gameFrame.player2Name.setText("Player2 Name: #N/A.");
            } else {
                gameFrame.player2Name.setText("Player2 Name: " + Variables.player2 + ".");
            }
            //Player2:
            for (int i = 0; i < Variables.player2_Maze_Copy.length; i++) {
                for (int j = 0; j < Variables.player2_Maze_Copy[0].length; j++) {
                    switch (Variables.player2_Maze_Copy[i][j]) {
                        case "W" -> drawAssets(gl, color, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.1);
                        case "P" -> drawAssets(gl, 9, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.08);
                        case "E" -> drawStar(gl, rotate, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.067);
                    }
                }
            }
        } else if (Variables.selectedAI) {
            //AI:
            for (int i = 0; i < Variables.AI_Maze_Copy.length; i++) {
                for (int j = 0; j < Variables.AI_Maze_Copy[0].length; j++) {
                    switch (Variables.AI_Maze_Copy[i][j]) {
                        case "W" -> drawAssets(gl, color, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.1);
                        case "P" -> drawAssets(gl, 9, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.08);
                        case "E" -> drawStar(gl, rotate, -0.75 + ((double) i / 6), 0.65 - ((double) j / 6), 0.067);
                    }
                }
            }
            //AI play time (Plays once ever second):
            if (Variables.seconds % 2 == 0 && !Variables.AI_Moved) {
                Variables.AI_Moved = true;
                Variables.AI_Maze_Copy[Variables.c_AI.x][Variables.c_AI.y] = "S";
                Variables.c_AI.x = Variables.path_AI.get(Variables.AI_Step).x;
                Variables.c_AI.y = Variables.path_AI.get(Variables.AI_Step).y;
                Variables.AI_Maze_Copy[Variables.c_AI.x][Variables.c_AI.y] = "P";
                if (Variables.AI_Step < Variables.path_AI.size()) {
                    Variables.AI_Step++;
                }
            } else if (Variables.seconds % 2 != 0 && Variables.AI_Moved) {
                Variables.AI_Moved = false;
                Variables.AI_Maze_Copy[Variables.c_AI.x][Variables.c_AI.y] = "S";
                Variables.c_AI.x = Variables.path_AI.get(Variables.AI_Step).x;
                Variables.c_AI.y = Variables.path_AI.get(Variables.AI_Step).y;
                Variables.AI_Maze_Copy[Variables.c_AI.x][Variables.c_AI.y] = "P";
                if (Variables.AI_Step < Variables.path_AI.size()) {
                    Variables.AI_Step++;
                }
            }
        }
        //Generate random traps:
        if (Variables.Num_Players == 2) {
            //Join worlds and switch the condition every 2 seconds:
            if (Variables.seconds % 2 == 0 && !Variables.trap_Generated) {
                Variables.spaces = PathTraversal.emptyCells(PathTraversal.joinWorlds(Variables.player1_Maze_Copy, Variables.player2_Maze_Copy));
                Variables.randomizer = (int) ((Math.random()) * Variables.spaces.size());
                Variables.trap_Generated = true;
                Variables.player1_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "W";
                Variables.player2_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "W";
            } else if (Variables.seconds % 2 == 1 && Variables.trap_Generated) {
                Variables.player1_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "S";
                Variables.player2_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "S";
                Variables.trap_Generated = false;
            }
        } else if (!Variables.selectedAI && Variables.levelSelection > -1) {
            //Search spaces available in single mode:
            if (Variables.seconds % 2 == 0 && !Variables.trap_Generated) {
                Variables.spaces = PathTraversal.emptyCells(Variables.player1_Maze_Copy);
                Variables.randomizer = (int) ((Math.random()) * Variables.spaces.size());
                Variables.trap_Generated = true;
                Variables.player1_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "W";
            } else if (Variables.seconds % 2 == 1 && Variables.trap_Generated) {
                Variables.player1_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "S";
                Variables.trap_Generated = false;
            }
        } else {
            if (Variables.seconds % 2 == 0 && !Variables.trap_Generated && Variables.levelSelection > -1) {
                //Join worlds and search spaces such that the AI has already played (Nothing can block it):
                Variables.spaces = PathTraversal.emptyCells(PathTraversal.joinWorlds(Variables.player1_Maze_Copy, Variables.AI_Maze_Copy));
                Variables.randomizer = (int) ((Math.random()) * Variables.spaces.size());
                Variables.trap_Generated = true;
                Variables.player1_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "W";
                if (!Variables.AI_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y].equals("P")) {
                    Variables.AI_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "W";
                }
            } else if (Variables.seconds % 2 == 1 && Variables.trap_Generated) {
                Variables.player1_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "S";
                if (!Variables.AI_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y].equals("P")) {
                    Variables.AI_Maze_Copy[Variables.spaces.get(Variables.randomizer).x][Variables.spaces.get(Variables.randomizer).y] = "S";
                }
                Variables.trap_Generated = false;
            }
        }
        //Sound:
        if (!Variables.muted) {
            //Unmuted:
            drawAssets(gl, 3, -0.5, 0.85, 0.1);
        } else {
            //Muted:
            drawAssets(gl, 4, -0.5, 0.85, 0.1);
        }
        //Stop button and Timer calculations:
        if (!Variables.stopped) {
            //Stop:
            drawAssets(gl, 5, -0.75, 0.85, 0.08);
            Variables.time_Passed = System.currentTimeMillis() - Variables.start_Time;
            Variables.seconds = Variables.time_Passed / 1000 + Variables.elapsed;
        } else {
            //Resume:
            drawAssets(gl, 6, -0.75, 0.85, 0.08);
            Variables.seconds = Variables.elapsed;
            Variables.game_Animator.stop();
        }
        if (Variables.seconds == 60) {
            Variables.seconds = 0;
            Variables.start_Time = System.currentTimeMillis();
            Variables.minutes++;
            Variables.elapsed = 0;
        }
        //Set the timer label:
        gameFrame.timer.setText("Timer: " + Variables.minutes + " Minutes, And " + Variables.seconds + " Seconds.");
        //rotate the star:
        rotate = (1 + rotate) % 360;
        //lost against AI or because of time:
        if ((Variables.minutes == Variables.max_Minutes || (Variables.selectedAI && Variables.path_AI.size() == Variables.AI_Step))
                && Variables.levelSelection > -1) {
            //Close the game:
            JOptionPane.showMessageDialog(null, "Losing game.", "Info", JOptionPane.INFORMATION_MESSAGE);
            gameFrame.game.dispose();
            Variables.re_Initialize();
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

    public void drawStar(GL gl, int angle, double x, double y, double scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[8]);

        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);
        gl.glRotated(angle, 0, 0, 1);
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
        //Stop:
        if (xCanvas <= -0.66 && xCanvas >= -0.84 && yCanvas <= 0.93 && yCanvas >= 0.75) {
            if (!Variables.stopped) {
                Variables.stopped = true;
                Variables.elapsed = Variables.seconds;
                Variables.muted = true;
                Variables.clipTime = Variables.clip.getMicrosecondPosition();
                Variables.clip.stop();
            } else {
                Variables.start_Time = System.currentTimeMillis();
                Variables.stopped = false;
                Variables.game_Animator.start();
            }
        } else if (xCanvas <= -0.38 && x >= -0.6 && yCanvas >= 0.76 && yCanvas <= 0.93) {
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
