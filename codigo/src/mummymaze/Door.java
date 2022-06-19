package mummymaze;

public class Door extends Entity {

    public Door(int line, int column) {
        super(line, column);
    }

    public void changeDoorState(char[][] matrix) {
        if (matrix[line][column] == '"')
            matrix[line][column] = ')';

        else if (matrix[line][column] == ')')
            matrix[line][column] = '"';

        if (matrix[line][column] == '=')
            matrix[line][column] = '_';

        else if (matrix[line][column] == '_')
            matrix[line][column] = '=';
    }
}
