package mummymaze;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MummyMazeState extends State implements Cloneable {

    public static final int SIZE = 3;
    private final char[][] matrix;
    private static long timesHeroDied;
    private static int lineExit;
    private static int columnExit;
    private static int lineKey;
    private static int columnKey;
    private static boolean keyInMaze;

    private boolean heroDead;
    private boolean trapOnTile;

    private int lineHero;
    private int columnHero;

    private LinkedList<Enemy> enemies;
    private LinkedList<Door> doors;
    private LinkedList<Trap> traps;

    public MummyMazeState(char[][] matrix) {
        this.matrix = new char[matrix.length][matrix.length];
        enemies = new LinkedList<>();
        doors = new LinkedList<>();
        traps = new LinkedList<>();
        timesHeroDied = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];

                if (this.matrix[i][j] == 'H') {
                    lineHero = i;
                    columnHero = j;
                }

                if (this.matrix[i][j] == 'S') {
                    lineExit = i;
                    columnExit = j;
                }

                if (this.matrix[i][j] == 'M')
                    enemies.add(new Enemy(i, j, this, this.matrix[i][j]));

                if (this.matrix[i][j] == 'V')
                    enemies.add(new Enemy(i, j, this, this.matrix[i][j]));

                if (this.matrix[i][j] == 'E')
                    enemies.add(new Enemy(i, j, this, this.matrix[i][j]));

                if (this.matrix[i][j] == 'C') {
                    lineKey = i;
                    columnKey = j;
                    keyInMaze = true;
                }

                if (this.matrix[i][j] == 'A') {
                    traps.add(new Trap(i, j));
                }

                if (isKeyInMaze()) {
                    if (matrix[i][j] == '"' || matrix[i][j] == ')' || matrix[i][j] == '=' || matrix[i][j] == '_')
                        doors.add(new Door(i, j));
                }
            }
        }
    }

    public boolean isHeroDead() {
        return heroDead;
    }

    public void setHeroDead(boolean heroDead) {
        this.heroDead = heroDead;
    }

    public boolean isKeyInMaze() {
        return keyInMaze;
    }

    public int getLineHero() {
        return lineHero;
    }

    public int getColumnHero() {
        return columnHero;
    }

    public int getLineExit() {
        return lineExit;
    }

    public int getColumnExit() {
        return columnExit;
    }

    public static int getLineKey() {
        return lineKey;
    }

    public static int getColumnKey() {
        return columnKey;
    }

    public LinkedList<Enemy> getEnemies() {
        return new LinkedList<>(enemies);
    }

    @Override
    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp() {
        return (lineHero == 1 && matrix[lineHero - 1][columnHero] == 'S') || (lineHero > 1 && (matrix[lineHero - 2][columnHero] == '.' || matrix[lineHero - 2][columnHero] == 'C') && matrix[lineHero - 1][columnHero] != '-' && matrix[lineHero - 1][columnHero] != '=');
    }

    public boolean canMoveRight() {
        return (columnHero == 11 && matrix[lineHero][columnHero + 1] == 'S') || (columnHero < 11 && (matrix[lineHero][columnHero + 2] == '.' || matrix[lineHero][columnHero + 2] == 'C') && matrix[lineHero][columnHero + 1] != '|' && matrix[lineHero][columnHero + 1] != '"');
    }

    public boolean canMoveDown() {
        return (lineHero == 11 && matrix[lineHero + 1][columnHero] == 'S') || (lineHero < 11 && (matrix[lineHero + 2][columnHero] == '.' || matrix[lineHero + 2][columnHero] == 'C') && matrix[lineHero + 1][columnHero] != '-' && matrix[lineHero + 1][columnHero] != '=');
    }

    public boolean canMoveLeft() {
        return (columnHero == 1 && matrix[lineHero][columnHero - 1] == 'S') || (columnHero > 1 && (matrix[lineHero][columnHero - 2] == '.' || matrix[lineHero][columnHero - 2] == 'C') && matrix[lineHero][columnHero - 1] != '|' && matrix[lineHero][columnHero - 1] != '"');
    }

    /* In the next four methods we don't verify if the actions are valid.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.*/

    public void moveUp() {

        matrix[lineHero][columnHero] = '.';

        if (lineHero == lineKey && columnHero == columnKey)
            matrix[lineHero][columnHero] = 'C';

        if (matrix[lineHero - 1][columnHero] == 'S') {
            lineHero--;
            matrix[lineHero][columnHero] = 'H';
            return;
        }

        lineHero -= 2;

        if (matrix[lineHero][columnHero] == 'C')
            changeDoorState();

        matrix[lineHero][columnHero] = 'H';

        moveEnemies();
    }

    public void moveRight() {

        matrix[lineHero][columnHero] = '.';

        if (lineHero == lineKey && columnHero == columnKey)
            matrix[lineHero][columnHero] = 'C';

        if (matrix[lineHero][columnHero + 1] == 'S') {
            columnHero++;
            matrix[lineHero][columnHero] = 'H';
            return;
        }

        columnHero += 2;

        if (matrix[lineHero][columnHero] == 'C')
            changeDoorState();

        matrix[lineHero][columnHero] = 'H';

        moveEnemies();
    }

    public void moveDown() {

        matrix[lineHero][columnHero] = '.';

        if (lineHero == lineKey && columnHero == columnKey)
            matrix[lineHero][columnHero] = 'C';

        if (matrix[lineHero + 1][columnHero] == 'S') {
            lineHero++;
            matrix[lineHero][columnHero] = 'H';
            return;
        }

        lineHero += 2;

        if (matrix[lineHero][columnHero] == 'C')
            changeDoorState();

        matrix[lineHero][columnHero] = 'H';

        moveEnemies();
    }

    public void moveLeft() {

        matrix[lineHero][columnHero] = '.';

        if (lineHero == lineKey && columnHero == columnKey)
            matrix[lineHero][columnHero] = 'C';

        if (matrix[lineHero][columnHero - 1] == 'S') {
            columnHero--;
            matrix[lineHero][columnHero] = 'H';
            return;
        }

        columnHero -= 2;

        if (matrix[lineHero][columnHero] == 'C')
            changeDoorState();

        matrix[lineHero][columnHero] = 'H';

        moveEnemies();
    }

    public void dontMove() {
        matrix[lineHero][columnHero] = 'H';

        moveEnemies();
    }

    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.getEnemyType() == 'V')
                enemy.moveMummyRed(matrix);
            else
                enemy.move(matrix);
        }

        for (int i = 0; i < enemies.size(); i++) {

            Enemy enemyI = enemies.get(i);
            int lineI = enemyI.getLine();
            int columnI = enemyI.getColumn();

            for (int j = 0; j < enemies.size(); j++) {

                Enemy enemyJ = enemies.get(j);
                int lineJ = enemyJ.getLine();
                int columnJ = enemyJ.getColumn();

                if (i != j) {
                    if (lineI == lineJ && columnI == columnJ) {
                        if (enemyI.getEnemyType() == enemyJ.getEnemyType()) {
                            enemies.remove(i);

                        } else if (enemyI.getEnemyType() != 'E') {
                            enemies.remove(j);
                            matrix[lineI][columnI] = enemyI.getEnemyType();

                        } else {
                            enemies.remove(i);
                            matrix[lineJ][columnJ] = enemyJ.getEnemyType();
                        }
                    }
                }
            }
        }
    }

    public void changeDoorState() {
        for (Door door : doors) {
            door.changeDoorState(matrix);
        }
    }

    public boolean isTrapOnTile(char[][] matrix, int line, int column) {
        for (Trap trap : traps) {
            return trap.getLine() == line && trap.getColumn() == column;
        }

        return false;
    }

    public char[][] getMatrix() {
        return matrix;
    }

    public double computeEnemyDistanceToGoal() {
        double distance = 0;

        if (lineHero == lineExit-1 && columnHero == columnExit || lineHero == lineExit+1 && columnHero == columnExit || lineHero == lineExit && columnHero == columnExit-1 || lineHero == lineExit && columnHero == columnExit+1) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (this.matrix[i][j] == 'M' || this.matrix[i][j] == 'E' || this.matrix[i][j] == 'V') {
                        if (lineHero == lineExit-1 && columnHero == columnExit) {
                            distance = Math.abs(i - lineExit + 1) + Math.abs(j - columnExit);
                            break;
                        }

                        if (lineHero == lineExit+1 && columnHero == columnExit) {
                            distance = Math.abs(i - lineExit - 1) + Math.abs(j - columnExit);
                            break;
                        }

                        if (lineHero == lineExit && columnHero == columnExit-1) {
                            distance = Math.abs(i - lineExit) + Math.abs(j - columnExit + 1);
                            break;
                        }

                        if (lineHero == lineExit && columnHero == columnExit+1) {
                            distance = Math.abs(i - lineExit) + Math.abs(j - columnExit - 1);
                            break;
                        }
                    }
                }
            }
        }

        return distance/2;
    }

    public double computeHeroDistancesToGoalWhenHeDies() {
        double distance = 0;

        if (isHeroDead()) {
            distance = Math.abs(lineHero - lineExit) + Math.abs(columnHero - columnExit);
        }

        return distance/2;
    }

    public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }

    public int getTileValue(int line, int column) {
        if (!isValidPosition(line, column)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }

    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MummyMazeState)) {
            return false;
        }

        MummyMazeState o = (MummyMazeState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public MummyMazeState clone() {
        return new MummyMazeState(matrix);
    }

    //Listeners
    private transient ArrayList<MummyMazeListener> listeners = new ArrayList<MummyMazeListener>(3);

    public synchronized void removeListener(MummyMazeListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(MummyMazeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(MummyMazeEvent pe) {
        for (MummyMazeListener listener : listeners) {
            listener.puzzleChanged(null);
        }
    }
}