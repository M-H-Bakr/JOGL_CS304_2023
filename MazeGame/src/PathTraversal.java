import java.util.*;

class Cell {
    int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

@SuppressWarnings("ClassEscapesDefinedScope")
public class PathTraversal {
    public static List<Cell> findPath(String[][] matrix) {
        // For simpler reading:
        int rows = matrix.length;
        int cols = matrix[0].length;
        // Valid moves:
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        //For the Algorithm:
        Stack<Cell> stack = new Stack<>();
        Cell start = new Cell(1, 1);
        boolean[][] visited = new boolean[rows][cols];

        //initialization:
        stack.push(start);
        Map<Cell, Cell> parent = new HashMap<>();
        parent.put(start, null);

        // Now the Algorithm (BFS):
        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int x = current.x;
            int y = current.y;
            // Path found, reconstruct the path
            if (matrix[x][y].equals("E")) {
                List<Cell> path = new ArrayList<>();
                Cell curr = current;
                while (curr != null) {
                    path.addFirst(curr); // Adding cells in reverse order.
                    curr = parent.get(curr); // Get the previous cell in position.
                }
                return path;
            }

            // Mark Cell pop-ed from the stack as Visited:
            visited[x][y] = true;

            // Next valid moves:
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (newX >= 0 && newY >= 0 && newX < rows && newY < cols &&
                        (matrix[newX][newY].equals("S") ||
                                matrix[newX][newY].equals("E"))
                        && !visited[newX][newY]) { // Check for unVisited valid cells.
                    Cell newCell = new Cell(newX, newY);
                    stack.push(newCell);
                    parent.put(newCell, current);
                    // Keep track of parent cells for reconstructing path.
                }
            }
        }
        return null;
    }
    public static void validateMove(String[][] maze,
                                    Cell current, booleanVal collided, booleanVal win) {
        if (maze[current.x][current.y].equals("W")) {
            //Collided with a wall:
            collided.exp = true;
        } else if (maze[current.x][current.y].equals("E")) {
            //Captured the star:
            win.exp = true;
        } else {
            //Valid move so far:
            collided.exp = false;
            win.exp = false;
        }
    }

    public static List<Cell> emptyCells(String[][] matrix) {
        //Finding empty cells marked with "S":
        List<Cell> answer = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].equals("S")) {
                    answer.add(new Cell(i, j));
                }
            }
        }
        return answer;
    }

    public static void deepCopy(String[][] MatA, String[][] MatB) {
        //Copy a matrix A into a matrix B of the same size:
        for (int i = 0; i < MatA.length; i++) {
            System.arraycopy(MatA[i], 0, MatB[i], 0, MatB[0].length);
        }
    }

    public static String[][] joinWorlds(String[][] MatA, String[][] MatB) {
        //Merge players' worlds into a single world to find positions:
        String[][] ans = new String[MatA.length][MatB.length];
        for (int i = 0; i < MatA.length; i++) {
            for (int j = 0; j < MatB[0].length; j++) {
                if (MatA[i][j].equals("P") || MatB[i][j].equals("P")) {
                    ans[i][j] = "P";
                } else {
                    ans[i][j] = MatA[i][j];
                }
            }
        }
        return ans;
    }
}
