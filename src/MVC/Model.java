package MVC;

public class Model {
    private int[][] playingField;
    private boolean[][] openedPlayingField;//false default
    private boolean[][] flaggedCells;
    private int fieldWidth;//ширина?
    private int fieldHeight;
    private int minesCount = 0;
    private int openedMinesCount = 0;

    public Model(int x, int y) {
        fieldWidth = x;
        fieldHeight = y;
        playingField = new int[fieldHeight][fieldWidth];
        openedPlayingField = new boolean[fieldHeight][fieldWidth];
        flaggedCells = new boolean[fieldHeight][fieldWidth];
    }

    public void setOpenedMinesCount(int input) {
        openedMinesCount = input;
    }

    public int getOpenedMinesCount() {
        return openedMinesCount;
    }

    public void setMinesCount(int inputMinesCount) {
        minesCount = inputMinesCount;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public boolean checkInputXY(int x, int y) {
        return ((x < fieldWidth) && (y < fieldHeight)) && ((x >= 0) && (y >= 0));
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int x) {
        fieldWidth = x;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int y) {
        fieldHeight = y;
    }

    public void setCellValue(int x, int y, int value) {
        try {
            playingField[y][x] = value;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getCellValue(int x, int y) {
        int tmp;
        try {
            tmp = playingField[y][x];//первые скобки берем какая строка т.е. у и тд
        } catch (IndexOutOfBoundsException e) {
            tmp = 0;//поменять значение при выходе за массив
        }
        return tmp;
    }

    public void setCellState(int x, int y, boolean f) {
        if (checkInputXY(x, y)) {
            openedPlayingField[y][x] = f;
        }
    }

    public boolean getCellState(int x, int y) {
        if (checkInputXY(x, y)) {
            return openedPlayingField[y][x];
        } else
            return false;
    }

    public void setFlag(int x, int y, boolean mode) {
        flaggedCells[y][x] = mode;
    }

    public boolean getFlag(int x, int y) {
        return flaggedCells[y][x];
    }
}