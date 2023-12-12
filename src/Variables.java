import com.sun.opengl.util.FPSAnimator;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("ClassEscapesDefinedScope")
public class Variables {
    public static File file = new File("src/Slush Invaders Tundra soundtrack.wav");
    public static AudioInputStream audioStream;

    static {
        try {
            audioStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Clip clip;

    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static long clipTime;
    public static FPSAnimator menu_Animator = new FPSAnimator(200);
    public static int current_Maze = 0;
    public static String[][] Maze1 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "S", "S", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "S", "S", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "S", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "S", "S", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "W", "S", "S", "S", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "E", "W"}
    };
    public static String[][] Maze2 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "S", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "S", "S", "S", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "W", "S", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "W", "S", "S", "S", "W", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "S", "W", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "S", "W", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "S", "S", "E"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
    };
    public static String[][] Maze3 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "S", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "S", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "S", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "S", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "S", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "S", "W", "W", "W", "W", "W", "W"},
            {"W", "W", "W", "S", "S", "S", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "W", "E", "W", "W", "W", "W"}
    };
    public static String[][] Maze4 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "S", "S", "S", "S", "S", "W", "W", "W"},
            {"W", "S", "W", "S", "S", "W", "S", "W", "W", "W"},
            {"W", "S", "S", "W", "S", "W", "W", "W", "W", "W"},
            {"W", "S", "W", "S", "S", "W", "W", "W", "W", "W"},
            {"W", "S", "S", "S", "S", "S", "S", "S", "W", "W"},
            {"W", "W", "W", "S", "S", "S", "W", "W", "W", "W"},
            {"W", "W", "S", "W", "W", "S", "W", "W", "W", "W"},
            {"E", "S", "S", "S", "S", "S", "W", "W", "W", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
    };
    public static String[][] Maze5 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "S", "S", "S", "S", "S", "W", "W", "W"},
            {"W", "S", "W", "S", "W", "S", "W", "W", "W", "W"},
            {"W", "W", "S", "S", "S", "W", "S", "S", "S", "W"},
            {"W", "S", "S", "W", "S", "S", "S", "W", "S", "W"},
            {"W", "S", "W", "S", "S", "S", "W", "S", "S", "W"},
            {"W", "S", "S", "S", "S", "W", "S", "S", "W", "W"},
            {"W", "W", "S", "W", "S", "W", "S", "W", "S", "E"},
            {"W", "S", "W", "W", "S", "W", "S", "S", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
    };
    public static String[][] Maze6 = {
            {"W", "W", "W", "W", "W", "W", "W", "E", "W", "W"},
            {"W", "P", "W", "S", "S", "S", "W", "S", "W", "W"},
            {"W", "S", "W", "S", "W", "W", "W", "S", "W", "W"},
            {"W", "S", "W", "S", "S", "S", "W", "S", "W", "W"},
            {"W", "S", "S", "S", "W", "S", "W", "S", "W", "W"},
            {"W", "W", "S", "W", "S", "S", "W", "S", "S", "W"},
            {"W", "W", "S", "S", "W", "S", "S", "W", "S", "W"},
            {"W", "W", "S", "W", "W", "S", "S", "S", "S", "W"},
            {"W", "W", "S", "S", "S", "W", "S", "W", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
    };
    public static String[][] Maze8 = {
            {"W", "W", "W", "E", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "W", "S", "S", "S", "S", "S", "W", "W"},
            {"W", "S", "W", "W", "S", "W", "W", "S", "W", "W"},
            {"W", "S", "W", "S", "W", "S", "S", "S", "W", "W"},
            {"W", "S", "S", "S", "S", "W", "S", "W", "S", "W"},
            {"W", "S", "W", "S", "S", "W", "S", "S", "S", "W"},
            {"W", "S", "S", "W", "S", "S", "W", "W", "S", "W"},
            {"W", "W", "S", "W", "S", "S", "S", "W", "S", "W"},
            {"W", "W", "S", "S", "W", "W", "S", "S", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
    };
    public static String[][] Maze9 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "S", "S", "W", "W", "S", "S", "W", "W"},
            {"W", "W", "W", "S", "S", "S", "S", "W", "S", "W"},
            {"E", "S", "W", "W", "W", "S", "S", "S", "W", "W"},
            {"W", "S", "S", "S", "S", "W", "W", "S", "S", "W"},
            {"W", "W", "W", "S", "S", "S", "W", "W", "S", "W"},
            {"W", "S", "W", "W", "S", "S", "W", "S", "S", "W"},
            {"W", "S", "S", "S", "W", "S", "S", "S", "W", "W"},
            {"W", "S", "W", "S", "S", "S", "S", "S", "W", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
    };
    public static String[][] Maze7 = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "P", "S", "S", "S", "S", "S", "S", "S", "W"},
            {"W", "W", "W", "S", "S", "W", "W", "S", "S", "W"},
            {"W", "S", "S", "S", "S", "S", "S", "S", "S", "W"},
            {"W", "S", "W", "S", "W", "S", "W", "S", "W", "W"},
            {"W", "S", "S", "S", "S", "S", "S", "S", "S", "W"},
            {"W", "W", "S", "W", "S", "W", "S", "W", "S", "W"},
            {"W", "S", "S", "S", "S", "S", "S", "S", "S", "W"},
            {"W", "S", "W", "S", "W", "W", "S", "W", "S", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "E", "W"}
    };
    public static ArrayList<String[][]> easy = new ArrayList<>();
    public static ArrayList<String[][]> normal = new ArrayList<>();
    public static ArrayList<String[][]> hard = new ArrayList<>();
    public static List<List<String[][]>> Mazes = new ArrayList<>();
    public static String[][] player1_Maze_Copy = new String[10][10];
    public static String[][] player2_Maze_Copy = new String[10][10];
    public static String[][] AI_Maze_Copy = new String[10][10];
    public static FPSAnimator game_Animator = new FPSAnimator(200);
    public static int levelSelection = -1;
    public static boolean selected_Mode = false;
    public static int Num_Players = 0;
    public static String player1 = "";
    public static String player2 = "";
    public static Cell c_Player1 = new Cell(0, 0);
    public static Cell c_Player2 = new Cell(0, 0);
    public static Cell c_AI = new Cell(0, 0);
    public static booleanVal player1_Collided = new booleanVal(false);
    public static booleanVal player2_Collided = new booleanVal(false);
    public static booleanVal player1_Win = new booleanVal(false);
    public static booleanVal player2_Win = new booleanVal(false);
    public static int player1_Lives = 0;
    public static int player2_Lives = 0;
    public static long player1_Score = 0;
    public static long player2_Score = 0;
    public static Map<String, Long> HighScores_Easy = new HashMap<>();
    public static Map<String, Long> HighScores_Normal = new HashMap<>();
    public static Map<String, Long> HighScores_Hard = new HashMap<>();
    public static boolean selectedAI = false;
    public static boolean AI_Moved = false;
    public static List<Cell> path_AI = new ArrayList<>();
    public static boolean muted = false;
    public static boolean stopped = false;
    public static long seconds = 0;
    public static long minutes = 0;
    public static long max_Minutes = 0;
    public static long start_Time = 0;
    public static long elapsed = 0;
    public static long time_Passed = 0;
    public static boolean menu_disabled = false;
    public static List<Cell> spaces = new ArrayList<>();
    public static boolean trap_Generated = false;
    public static int randomizer = 0;
    public static int AI_Step = 0;
    public static void re_Initialize() {
        AI_Step = 0;
        randomizer = 0;
        current_Maze = 0;
        levelSelection = -1;
        selected_Mode = false;
        Num_Players = 0;
        player1 = "";
        player2 = "";
        c_Player1 = new Cell(0, 0);
        player1_Win.exp = false;
        player1_Score = 0;
        player1_Lives = 0;
        stopped = false;
        seconds = 0;
        minutes = 0;
        max_Minutes = 0;
        start_Time = 0;
        elapsed = 0;
        time_Passed = 0;
        menu_disabled = false;
        menu_Animator.start();
        game_Animator.stop();
        player2_Win.exp = false;
        player2_Score = 0;
        selectedAI = false;
        AI_Moved = false;
        c_Player2 = new Cell(0, 0);
        player1_Collided.exp = false;
        player2_Collided.exp = false;
        spaces = new ArrayList<>();
        trap_Generated = false;
        c_AI = new Cell(0, 0);
        path_AI = new ArrayList<>();
        AI_Maze_Copy = new String[10][10];
        player1_Maze_Copy = new String[10][10];
        player2_Maze_Copy = new String[10][10];
    }
}

class booleanVal {
    boolean exp;

    booleanVal(boolean exp) {
        this.exp = exp;
    }
}
