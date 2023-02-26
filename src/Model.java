public class Model {
    private int[][] playingField;
    private int fieldWidth;//ширина?
    private int fieldLength;

    Model(int x, int y) {
        fieldWidth = x;
        fieldLength = y;
        playingField = new int[fieldWidth][fieldLength];
    }

    Model() {
        fieldWidth = 0;
        fieldLength = 0;
    }

    int getFieldWidth() {
        return fieldWidth;
    }

    int getFieldLength() {
        return fieldLength;
    }

    void changeCellValue(int x, int y, int value) {
        try {
            playingField[x][y] = value;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    int getCellValue(int x, int y) {
        int tmp;
        try {
            tmp = playingField[x][y];
        } catch (IndexOutOfBoundsException e) {
            tmp = 0;//поменять значение при выходе за массив
        }
        return tmp;
    }
    void placeCountsOfCloseMines() {
        for (int tmpX = 0; tmpX < fieldWidth; tmpX++) {
            for (int tmpY = 0; tmpY < fieldLength; tmpY++) {
                if (getCellValue(tmpX, tmpY) != -1) {
                    int closeMinesCounter = 0;
                    for (int deltaX = -1; deltaX <= 1; deltaX++) {
                        for (int deltaY = -1; deltaY <= 1; deltaY++)
                            if (getCellValue(tmpX + deltaX, tmpY + deltaY) == -1) closeMinesCounter++;
                    }
                    changeCellValue(tmpX,tmpY,closeMinesCounter);
                }
            }
        }
    }
}