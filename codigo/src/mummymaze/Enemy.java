package mummymaze;

public class Enemy extends Entity {
    private final int enemySteps;
    private final char enemyType;
    private MummyMazeState state;

    public Enemy(int line, int column, MummyMazeState state, char enemyType) {
        super(line, column);
        this.state = state;
        this.enemyType = enemyType;

        if (enemyType == 'E')
            enemySteps = 1;
        else
            enemySteps = 2;
    }

    public char getEnemyType() {
        return enemyType;
    }

    public void move(char[][] matrix) {

        int lineHero = state.getLineHero();
        int columnHero = state.getColumnHero();

        for (int i = 0; i < enemySteps; i++) {

            if (columnHero < column) {

                if (matrix[line][column - 1] != '|' && matrix[line][column - 1] != '"') {

                    areActuatorsOnTile(matrix);

                    column -= 2;

                    if (matrix[line][column] == 'C')
                        state.changeDoorState();

                    if (enemyType == 'E')
                        matrix[line][column] = 'E';

                    else
                        matrix[line][column] = 'M';

                } else {

                    lineHero = moveBetweenLines(matrix, lineHero);
                }

            } else if (columnHero > column) {

                if (matrix[line][column + 1] != '|' && matrix[line][column + 1] != '"') {

                    areActuatorsOnTile(matrix);

                    column += 2;

                    if (matrix[line][column] == 'C')
                        state.changeDoorState();

                    if (enemyType == 'E')
                        matrix[line][column] = 'E';

                    else
                        matrix[line][column] = 'M';

                } else {

                    lineHero = moveBetweenLines(matrix, lineHero);
                }

            } else {

                lineHero = moveBetweenLines(matrix, lineHero);
            }

            if (lineHero == line && columnHero == column) {
                state.setHeroDead(true);
                return;
            }
        }
    }

    public int moveBetweenLines(char[][] matrix, int lineHero) {

        if (lineHero < line) {

            if (matrix[line - 1][column] != '-' && matrix[line - 1][column] != '=') {

                areActuatorsOnTile(matrix);

                line -= 2;

                if (matrix[line][column] == 'C')
                    state.changeDoorState();

                if (enemyType == 'E')
                    matrix[line][column] = 'E';

                else
                    matrix[line][column] = 'M';
            }

        } else {

            if (lineHero > line) {

                if (matrix[line + 1][column] != '-' && matrix[line + 1][column] != '=') {

                    areActuatorsOnTile(matrix);

                    line += 2;

                    if (matrix[line][column] == 'C')
                        state.changeDoorState();

                    if (enemyType == 'E')
                        matrix[line][column] = 'E';

                    else
                        matrix[line][column] = 'M';
                }
            }
        }

        return lineHero;
    }

    public void moveMummyRed(char[][] matrix) {

        int lineHero = state.getLineHero();
        int columnHero = state.getColumnHero();

        for (int i = 0; i < 2; i++) {

            if (lineHero < line) {

                if (matrix[line - 1][column] != '-' && matrix[line - 1][column] != '=') {

                    areActuatorsOnTile(matrix);

                    line -= 2;

                    if (matrix[line][column] == 'C')
                        state.changeDoorState();

                    matrix[line][column] = 'V';

                } else {

                    columnHero = moveBetweenColumns(matrix, columnHero);
                }

            } else if (lineHero > line) {

                if (matrix[line + 1][column] != '-' && matrix[line + 1][column] != '=') {

                    areActuatorsOnTile(matrix);

                    line += 2;

                    if (matrix[line][column] == 'C')
                        state.changeDoorState();

                    matrix[line][column] = 'V';

                } else {

                    columnHero = moveBetweenColumns(matrix, columnHero);
                }

            } else {

                columnHero = moveBetweenColumns(matrix, columnHero);
            }

            if (lineHero == line && columnHero == column) {
                state.setHeroDead(true);
                return;
            }
        }
    }

    public int moveBetweenColumns(char[][] matrix, int columnHero) {

        if (columnHero < column) {

            if (matrix[line][column - 1] != '|' && matrix[line][column - 1] != '"') {

                areActuatorsOnTile(matrix);

                column -= 2;

                if (matrix[line][column] == 'C')
                    state.changeDoorState();

                matrix[line][column] = 'V';
            }

        } else {

            if (columnHero > column) {

                if (matrix[line][column + 1] != '|' && matrix[line][column + 1] != '"') {

                    areActuatorsOnTile(matrix);

                    column += 2;

                    if (matrix[line][column] == 'C')
                        state.changeDoorState();

                    matrix[line][column] = 'V';
                }
            }
        }

        return columnHero;
    }

    public void areActuatorsOnTile(char[][] matrix) {

        matrix[line][column] = '.';

        if (state.isTrapOnTile(matrix, line, column))
            matrix[line][column] = 'A';

        if (line == state.getLineKey() && column == state.getColumnKey())
            matrix[line][column] = 'C';
    }
}