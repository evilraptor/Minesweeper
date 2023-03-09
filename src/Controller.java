import java.util.Scanner;

public class Controller {
    private int fieldWidth;//ширина?
    private int fieldHeight;
    private Model controllerModel;
    private View view;
    private int countOfMines = 0;
    private boolean graphicFlag;

    Controller(int xInputValue, int yInputValue, int inputCountOfMines,boolean inputGraphicFlag) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        countOfMines = inputCountOfMines;
        controllerModel = new Model(fieldWidth, fieldHeight);
        controllerModel.setMinesCount(countOfMines);
        view=new View(fieldWidth,fieldHeight);
    }

    Controller() {
        System.out.println("enter x and y values of the field...");
        Scanner in = new Scanner(System.in);
        String tmp = in.nextLine();
        String[] input = tmp.split(" ");
        fieldWidth = Integer.parseInt(input[0]);//in.nextInt();
        fieldHeight = Integer.parseInt(input[1]);//in.nextInt();
        countOfMines = 0;
        //controllerModel.setMinesCount(0);
        controllerModel = new Model(fieldWidth, fieldHeight);
        controllerModel.setMinesCount(countOfMines);
        //in.close();
    }//надо бы изменить чтобы еще мины вводились

    int getFieldWidth() {
        return fieldWidth;
    }

    int getFieldHeight() {
        return fieldHeight;
    }

    boolean checkIsGameEnded() {
        if (checkIsAllFieldOpened() && checkIsAllMinesOpened()) {
            System.out.println("You won!!!");
            return true;
        } else return false;
    }

    int getFlagsCount() {
        return controllerModel.getMinesCount();
    }

    void setModel(Model inputModel) {
        controllerModel = inputModel;
    }

    boolean checkIsAllMinesOpened() {
        return controllerModel.getOpenedMinesCount() == controllerModel.getMinesCount();
    }

    boolean checkIsAllFieldOpened() {
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                if ((controllerModel.getCellValue(i, j) != -1) && (!controllerModel.getCellState(i, j))) return false;
            }
        }
        return true;
    }

    void placeMinesOnField(int countOfMines) {
        controllerModel.setMinesCount(countOfMines);
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
        int generationMinesCount = 0;
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                if (controllerModel.getCellValue(i, j) == -1) generationMinesCount++;
            }
        }
        if (generationMinesCount != controllerModel.getMinesCount()) {
            System.out.println("wrong generation. Trying again...");
            placeMinesOnField(countOfMines);
        }
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


    /**
     * @return if unset flag on field returns false and so if the cell was free, set it and return true.
     */
    boolean makeFlagOnFieldOpposite(int inputX, int inputY) {
        if (controllerModel.getFlag(inputX, inputY)) {
            controllerModel.setFlag(inputX, inputY, false);
            printOpenedField();
            controllerModel.setOpenedMinesCount(controllerModel.getOpenedMinesCount() - 1);
            return false;
        } else {
            controllerModel.setFlag(inputX, inputY, true);
            controllerModel.setOpenedMinesCount(controllerModel.getOpenedMinesCount() + 1);
            printOpenedField();
            return true;
        }
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

    /**
     * @return output:1)"bad input (x||y) out of range"/2)"bad input cell is already opened"/3)"there was a mine...",4)"Ok".
     * (1)checkInputXY(x, y); (2)getCellState(x, y); (3)getCellValue(x, y) == -1; (4)setCellState(x, y, true)...else return "Ok";
     */
    String openCell(int x, int y) {
        if (!controllerModel.checkInputXY(x, y)) {
            System.out.println("bad input (x||y) out of range");
            return "bad input (x||y) out of range";
        }
        if (controllerModel.getCellState(x, y)) {
            System.out.println("bad input cell is already opened");
            return "bad input cell is opened";
        }
        if (controllerModel.getCellValue(x, y) == -1) {
            System.out.println("there was a mine...\nGame over");
            return "Game over";
        } else {
            controllerModel.setCellState(x, y, true);
            openCloseCells(x, y);
            printOpenedField();
            return "Ok";
        }
    }

    String openCell() {//для графической поменять потом
        return "b";
    }//для графики наверное
}
