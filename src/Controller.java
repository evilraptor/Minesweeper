public class Controller {
    private int fieldWidth;//ширина?
    private int fieldHeight;
    private Model controllerModel;
    private int countOfMines;

    Controller(int xInputValue, int yInputValue) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        countOfMines = 0;
        controllerModel = new Model(xInputValue, yInputValue);
    }

    Controller() {
        countOfMines = 0;
        controllerModel = new Model();
    }

    void setModel(Model inputModel) {
        controllerModel = inputModel;
    }

    void placeMinesOnField(int countOfMines) {
        int mineValue = -1;
        for (int i = 0; i < countOfMines; ) {
            int mineXCoordinate = (int) (Math.random() * controllerModel.getFieldWidth());//fieldWidth
            int mineYCoordinate = (int) (Math.random() * controllerModel.getFieldHeight());//fieldHeight
            int flagMineFreeCell = controllerModel.getCellValue(mineXCoordinate, mineYCoordinate);
            if (flagMineFreeCell != -1) {
                controllerModel.setCellValue(mineXCoordinate, mineYCoordinate, mineValue);
                i++;
            }
        }
        placeCountsOfCloseMines();
    }

    void placeCountsOfCloseMines() {
        for (int tmpX = 0; tmpX < fieldWidth; tmpX++) {
            for (int tmpY = 0; tmpY < fieldHeight; tmpY++) {
                if (controllerModel.getCellValue(tmpX, tmpY) != -1) {
                    int closeMinesCounter = 0;
                    for (int deltaX = -1; deltaX <= 1; deltaX++) {
                        for (int deltaY = -1; deltaY <= 1; deltaY++)
                            if (controllerModel.getCellValue(tmpX + deltaX, tmpY + deltaY) == -1) closeMinesCounter++;
                    }
                    controllerModel.setCellValue(tmpX, tmpY, closeMinesCounter);
                }
            }
        }
    }

    void printFullyOpenedField() {
        for (int x = 0; x < fieldWidth; x++) {
            for (int y = 0; y < fieldHeight; y++) {
                int tmp = controllerModel.getCellValue(x, y);
                if (tmp != -1)
                    System.out.print(" " + tmp);
                else System.out.print(" X");
            }
            System.out.print("\n");
        }
    }

    void printOpenedField() {
        for (int x = 0; x < fieldWidth; x++) {
            for (int y = 0; y < fieldHeight; y++) {
                if (controllerModel.getCellState(x, y)) {
                    int tmp = controllerModel.getCellValue(x, y);
                    if (tmp != -1)
                        System.out.print(" " + tmp);
                    else System.out.print(" X");
                } else {
                    System.out.print(" _");//-48 -79
                }
            }
            System.out.print("\n");
        }
    }

    String makeChoice(int x, int y) {
        if (!controllerModel.checkInputXY(x, y)) {
            return "bad input (x||y) out of range";
        }
        if (controllerModel.getCellState(x, y)) {
            return "bad input cell is opened";
        }

        if (controllerModel.getCellValue(x, y) == -1) {
            return "there was a mine...";
        } else {
            controllerModel.setCellState(x, y, true);
            printOpenedField();

        }
        return "";
    }

    String makeChoice() {//для графической поменять потом
        return "b";
    }
}
