public class Model {
    private int[][] playingField;
    private boolean[][] openedPlayingField;//false default
    private int fieldWidth;//ширина?
    private int fieldHeight;

    Model(int x, int y) {
        fieldWidth = x;
        fieldHeight = y;
        playingField = new int[fieldWidth][fieldHeight];
        openedPlayingField = new boolean[fieldWidth][fieldWidth];
    }

    Model() {
        fieldWidth = 0;
        fieldHeight = 0;
    }

    boolean checkInputXY(int x, int y) {
        boolean flag = true;
        if (((x >= fieldWidth) || (y >= fieldHeight))||((x >= fieldWidth) || (y >= fieldHeight))) flag = false;
        return flag;

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

    void setCellState(int x, int y,boolean f) {
        if (checkInputXY(x, y)) {
            openedPlayingField[x][y] = f;
        }
    }

    boolean getCellState(int x, int y) {
        if (checkInputXY(x, y)) {
            return openedPlayingField[x][y];
        } else
            return false;
    }
}