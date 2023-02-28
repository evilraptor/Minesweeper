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
        for (int tmpY = 0; tmpY < fieldHeight; tmpY++) {
            for (int tmpX = 0; tmpX < fieldWidth; tmpX++) {
                if (controllerModel.getCellValue(tmpX, tmpY) != -1) {
                    int closeMinesCounter = 0;
                    for (int deltaY = -1; deltaY <= 1; deltaY++) {
                        for (int deltaX = -1; deltaX <= 1; deltaX++)
                            if (controllerModel.getCellValue(tmpX + deltaX, tmpY + deltaY) == -1) closeMinesCounter++;
                    }
                    controllerModel.setCellValue(tmpX, tmpY, closeMinesCounter);
                }
            }
        }
    }

    void openCloseCells(int inputX, int inputY) {
        if (controllerModel.getCellValue(inputX, inputY) != 0) {
            return;
        } else {
            for (int i = 0; i < (fieldHeight + fieldWidth) / 2; i++) {
                for (int tmpY = 0; tmpY < fieldHeight; tmpY++) {
                    for (int tmpX = 0; tmpX < fieldWidth; tmpX++) {
                        if (controllerModel.getCellState(tmpX, tmpY))
                            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                                for (int deltaX = -1; deltaX <= 1; deltaX++) {
                                    if (controllerModel.getCellValue(tmpX + deltaX, tmpY + deltaY) == 0)//если открыли 0 то открываем ячейки вокруг
                                        controllerModel.setCellState(tmpX + deltaX, tmpY + deltaY, true);
                                    else if (controllerModel.getCellValue(tmpX + deltaX, tmpY + deltaY) != -1) {
                                        for (int deltaY2 = -1; deltaY2 <= 1; deltaY2++) {
                                            for (int deltaX2 = -1; deltaX2 <= 1; deltaX2++) {
                                                if ((controllerModel.getCellState(tmpX + deltaX2, tmpY + deltaY2)) || (controllerModel.getCellValue(tmpX + deltaX2, tmpY + deltaY2) == 0))
                                                    controllerModel.setCellState(tmpX + deltaX2, tmpY + deltaY2, true);
                                            }
                                        }
                                    }
                                }
                            }
                    }
                }
            }
//выставляем у открытых областей границы
            for (int tmpY = 0; tmpY < fieldHeight; tmpY++) {
                for (int tmpX = 0; tmpX < fieldWidth; tmpX++) {
                    if ((controllerModel.getCellValue(tmpX, tmpY) > 0) && (!controllerModel.getCellState(tmpX, tmpY))) {
                        for (int deltaY = -1; deltaY <= 1; deltaY++) {
                            for (int deltaX = -1; deltaX <= 1; deltaX++) {
                                if ((controllerModel.getCellValue(tmpX + deltaX, tmpY + deltaY) == 0) && (controllerModel.getCellState(tmpX + deltaX, tmpY + deltaY))) {
                                    controllerModel.setCellState(tmpX, tmpY, true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void makeFlagOnFieldOpposite(int inputX, int inputY) {
        if (controllerModel.getFlag(inputX, inputY)) controllerModel.setFlag(inputX, inputY, false);
        else controllerModel.setFlag(inputX, inputY, true);
        printOpenedField();
    }

    void printFullyOpenedField() {
        for (int y = 0; y < fieldHeight; y++) {
            for (int x = 0; x < fieldWidth; x++) {
                int tmp = controllerModel.getCellValue(x, y);
                if (tmp != -1)
                    System.out.print(" " + tmp);
                else System.out.print(" X");
            }
            System.out.print("\n");
        }
    }

    void printOpenedField() {
        for (int y = 0; y < fieldHeight; y++) {
            for (int x = 0; x < fieldWidth; x++) {
                if (controllerModel.getCellState(x, y)) {
                    int tmp = controllerModel.getCellValue(x, y);
                    if (tmp != -1)
                        System.out.print(" " + tmp);
                    else System.out.print(" X");
                } else if (controllerModel.getFlag(x, y)) {
                    System.out.print(" ►");
                } else {
                    System.out.print(" ?");//-48 -79
                }
            }
            System.out.print("\n");
        }
    }

    String makeChoice(int x, int y) {
        if (!controllerModel.checkInputXY(x, y)) {
            System.out.println("bad input (x||y) out of range");
            return "bad input (x||y) out of range";
        }
        if (controllerModel.getCellState(x, y)) {
            System.out.println("bad input cell is opened");
            return "bad input cell is opened";
        }
        if (controllerModel.getCellValue(x, y) == -1) {
            System.out.println("there was a mine...");
            return "there was a mine...";
        } else {
            controllerModel.setCellState(x, y, true);
            openCloseCells(x, y);
            printOpenedField();
        }
        return "";
    }

    String makeChoice() {//для графической поменять потом
        return "b";
    }
}
