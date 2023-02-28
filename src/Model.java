public class Model {
    private int[][] playingField;
    private boolean[][] openedPlayingField;//false default
    private boolean[][] flaggedCells;
    private int fieldWidth;//ширина?
    private int fieldHeight;

    Model(int x, int y) {
        fieldWidth = x;
        fieldHeight = y;
        playingField = new int[fieldHeight][fieldWidth];
        openedPlayingField = new boolean[fieldHeight][fieldWidth];
        flaggedCells = new boolean[fieldHeight][fieldWidth];
    }

    Model() {
        fieldWidth = 0;
        fieldHeight = 0;
    }

    boolean checkInputXY(int x, int y) {
        return ((x < fieldWidth) && (y < fieldHeight)) && ((x >= 0) && (y >= 0));
    }

    int getFieldWidth() {
        return fieldWidth;
    }

    void setFieldWidth(int x) {
        fieldWidth = x;
    }

    int getFieldHeight() {
        return fieldHeight;
    }

    void setFieldHeight(int y) {
        fieldHeight = y;
    }

    void setCellValue(int x, int y, int value) {
        try {
            playingField[y][x] = value;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    int getCellValue(int x, int y) {
        int tmp;
        try {
            tmp = playingField[y][x];//первые скобки берем какая строка т.е. у и тд
        } catch (IndexOutOfBoundsException e) {
            tmp = 0;//поменять значение при выходе за массив
        }
        return tmp;
    }

    void setCellState(int x, int y, boolean f) {
        if (checkInputXY(x, y)) {
            openedPlayingField[y][x] = f;
        }
    }

    boolean getCellState(int x, int y) {
        if (checkInputXY(x, y)) {
            return openedPlayingField[y][x];
        } else
            return false;
    }

    void setFlag(int x, int y, boolean mode) {
        flaggedCells[y][x] = mode;
    }

    boolean getFlag(int x, int y) {
        return flaggedCells[y][x];
    }
}