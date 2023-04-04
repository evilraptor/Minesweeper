package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

public class Controller {
    private int fieldWidth;//ширина?
    private int fieldHeight;
    private Model controllerModel;
    private View controllerView;
    private int countOfMines;
    private boolean puttingFlagMode;
    private boolean enableButtons = true;
    private ImageIcon[] containerOfIcons;//0-8 это цифры 9 флаг 10 мина

    public Controller(int xInputValue, int yInputValue, int inputCountOfMines, boolean inputGraphicFlag) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        countOfMines = inputCountOfMines;
        controllerModel = new Model(fieldWidth, fieldHeight);
        controllerModel.setMinesCount(countOfMines);
        if (inputGraphicFlag) {
            controllerView = new View(fieldWidth, fieldHeight, inputCountOfMines);
            containerOfIcons = controllerView.getContainerOfIcons();
            setMouseListenerOnCells();
            controllerView.createFlagButton();
            setMouseListenerOnFlagButton();
        }
    }

    public Controller() {
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

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public boolean checkIsGameEnded() {
        if (checkIsAllFieldOpened() && checkIsAllMinesFlagged()) {
            System.out.println("You won!!!");
            return true;
        } else return false;
    }

    public int getFlagsCount() {
        return controllerModel.getMinesCount();
    }

    public void setModel(Model inputModel) {
        controllerModel = inputModel;
    }

    public boolean checkIsAllMinesFlagged() {
        return controllerModel.getOpenedMinesCount() == controllerModel.getMinesCount();
    }

    public boolean checkIsAnyMineTouched() {
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                if ((controllerModel.getCellValue(i, j) == -1) && (controllerModel.getCellState(i, j))) return true;
            }
        }
        return false;
    }

    public boolean checkIsAllFieldOpened() {
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                if ((controllerModel.getCellValue(i, j) != -1) && (!controllerModel.getCellState(i, j))) return false;
            }
        }
        return true;
    }

    public void placeMinesOnField(int countOfMines) {
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

    public void placeCountsOfCloseMines() {
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

    public void openCloseCells(int inputX, int inputY) {
        if (controllerModel.getCellValue(inputX, inputY) == 0) {
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
    public boolean makeFlagOnFieldOpposite(int inputX, int inputY) {
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

    public void printFullyOpenedField() {
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

    public void printOpenedField() {
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
//A
    /**
     * @return output:1)"bad input (x||y) out of range"/2)"bad input cell is already opened"/3)"there was a mine...",4)"Ok".
     * (1)checkInputXY(x, y); (2)getCellState(x, y); (3)getCellValue(x, y) == -1; (4)setCellState(x, y, true)...else return "Ok";
     */
    public String openCell(int x, int y) {
        if (!controllerModel.checkInputXY(x, y)) {
            System.out.println("bad input (x||y) out of range");
            return "bad input (x||y) out of range";
        }
        if (controllerModel.getCellState(x, y)) {
            System.out.println("bad input cell is already opened");
            return "bad input cell is opened";
        }
        if (controllerModel.getCellValue(x, y) == -1) {
            controllerModel.setCellState(x, y, true);
            System.out.println("there was a mine...\nGame over");
            return "Game over";
        } else {
            controllerModel.setCellState(x, y, true);
            openCloseCells(x, y);
            printOpenedField();
            return "Ok";
        }
    }
    //TODO переписать с учетом графики юпд написать просто новые функции

    public void setMouseListenerOnCells() {
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldHeight; j++) {
                //controllerView.cells[i][j].setIcon();
                controllerView.cells[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (enableButtons) {
                            Point windowPoint = controllerView.getWindowPoint();
                            int xPoint = (int) ((e.getLocationOnScreen().getX() - windowPoint.getX()) / 100.0);
                            int yPoint = (int) ((e.getLocationOnScreen().getY() - 30 - windowPoint.getY()) / 100.0);
                            System.out.println("in points " + xPoint + " " + yPoint);

                            if (!puttingFlagMode) {
                                if (openCell(xPoint, yPoint).equals("Game over"))
                                    controllerView.cells[xPoint][yPoint].setIcon(containerOfIcons[10]);
                                else //if ((openCell(xPoint, yPoint) == "Ok"))//||(openCell(xPoint, yPoint) == "bad input cell is already opened")) {
                                {
                                    controllerView.cells[xPoint][yPoint].setIcon(containerOfIcons[controllerModel.getCellValue(xPoint, yPoint)]);
                                    for (int i = 0; i < fieldWidth; i++) {
                                        for (int j = 0; j < fieldHeight; j++) {
                                            //System.out.println("a");
                                            if (controllerModel.getCellState(i, j)) {
                                                //System.out.println("b");
                                                controllerView.cells[i][j].setIcon(containerOfIcons[controllerModel.getCellValue(i, j)]);
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (!controllerModel.getFlag(xPoint, yPoint)) {
                                    controllerView.cells[xPoint][yPoint].setIcon(containerOfIcons[9]);
                                    controllerModel.setFlag(xPoint, yPoint, true);
                                    if (controllerModel.getCellValue(xPoint, yPoint) == -1)
                                        controllerModel.setOpenedMinesCount(controllerModel.getOpenedMinesCount() + 1);
                                } else {
                                    controllerView.cells[xPoint][yPoint].setIcon(null);
                                    controllerModel.setFlag(xPoint, yPoint, false);
                                    if (controllerModel.getCellValue(xPoint, yPoint) == -1)
                                        controllerModel.setOpenedMinesCount(controllerModel.getOpenedMinesCount() - 1);
                                }
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
            }


        }
    }

    public void setMouseListenerOnFlagButton() {
        controllerView.flagButton.doClick();
        controllerView.flagButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (puttingFlagMode) {
                    controllerView.flagButton.setIcon(containerOfIcons[11]);
                    puttingFlagMode = false;
                } else {
                    controllerView.flagButton.setIcon(containerOfIcons[9]);
                    puttingFlagMode = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void stopGraphicInput() {
        enableButtons = false;
    }
}