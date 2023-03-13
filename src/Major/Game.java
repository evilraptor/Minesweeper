package Major;

import MVC.Controller;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    //TODO а может если делать нечего сделать считывание по графике входного сразу
    private int fieldWidth;//ширина?
    private int fieldHeight;
    private Controller gameController = null;
    private int countOfMines;
    private boolean puttingFlagMode = false;
    private boolean graphicFlag;

    int checkForCommand(String input) {//Exit, About, New Major.Game, High Scores.
        switch (input) {
            case "Exit" -> {
                return -1;
            }
            case "About" -> {
                System.out.println("Commands: Exit, About, New Major.Game, High Scores, Put Flags, Open Cell.");
                return 0;
            }
            case "New Major.Game" -> {
                startNewGame();
                return 1;
            }
            case "High Scores" -> {
                //FileWriter writer = new FileWriter("notes3.txt", true); запись с добавлением
                try (FileReader reader = new FileReader("Leaderboard.txt")) {
                    // читаем посимвольно
                    int c;
                    while ((c = reader.read()) != -1) {
                        System.out.print((char) c);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                return 0;
            }
            default -> {
                return 0;
            }
        }
    }//-1 exit 0 okk 1 new game

    void getStartingGameInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("enter x and y values of the field...");
        int xInputValue = in.nextInt();
        int yInputValue = in.nextInt();
        System.out.println("enter count of mines...");
        countOfMines = in.nextInt();
        in.nextLine();
        while (true) {
            System.out.println("Use graphic? (Y/N)");
            String tmp = in.nextLine();
            if (tmp.equals("Y")) {
                graphicFlag = true;
                break;
            } else if (tmp.equals("N")) {
                graphicFlag = false;
                break;
            }
        }
        gameController = new Controller(xInputValue, yInputValue, countOfMines,graphicFlag);
        gameController.placeMinesOnField(countOfMines);
        gameController.printFullyOpenedField();/////////
        gameController.printOpenedField();
        if(graphicFlag)gameController.setMouseListenerOnCells();
        //in.close();
    }

    int cmdGameCycle() {
        int flagCount = gameController.getFlagsCount();

        while (true) {
            System.out.println("\nCount of mines: " + flagCount);
            String[] input = new String[1];
            int flag;
            while (input.length == 1) {
                input = getCycleInput(puttingFlagMode);
                flag = checkForCommand(input[input.length - 1]);
                if (flag != 0) return flag;
            }

            String tmp = Arrays.toString(input);
            /////написать про смену режима
            int tmpX = Integer.parseInt(input[0]);//in.nextInt();
            int tmpY = Integer.parseInt(input[1]);//in.nextInt();
            if (!puttingFlagMode) {
                tmp = gameController.openCell(tmpX, tmpY);
            } else {
                if (gameController.makeFlagOnFieldOpposite(tmpX, tmpY)) {
                    flagCount--;
                } else {
                    flagCount++;
                }
            }

            if ((tmp.equals("Major.Game over")) || ((gameController.checkIsGameEnded()))) {
                break;
            }
            //System.out.println(tmp);
        }
        if (gameController.checkIsGameEnded())
            return -1;
        //in.close();
        return 0;
    }//-1 exit 0 okk 1 new game

    void startNewGame() {
        gameController = null;
        countOfMines = 0;
        playMinesweeper();
    }

    String[] getCycleInput(boolean puttingFlagMode) {
        Scanner in = new Scanner(System.in);
        System.out.print("Now mode is ");
        if (puttingFlagMode) System.out.print("Put Flag\n");
        else System.out.print("Open Cell\n");
        System.out.println("Enter opening cell coordinate or command. Or change mode on Open Cell/Put Flag...");
        String tmp = in.nextLine();
        if ((tmp.equals("Open Cell")) && (puttingFlagMode)) {
            this.puttingFlagMode = false;
        } else if ((tmp.equals("Put Flag")) && (!puttingFlagMode)) {
            this.puttingFlagMode = true;
        }
        String[] outputTmp = tmp.split(" ");
        int arrayTmpLength = outputTmp.length;
        String[] output = new String[arrayTmpLength + 1];
        System.arraycopy(outputTmp, 0, output, 0, arrayTmpLength);//for (int i=0;i<arrayTmpLength;i++)output[i]=outputTmp[i];
        output[arrayTmpLength] = tmp;
        if (tmp.equals("Open Cell") || tmp.equals("Put Flag")) {
            output = new String[1];
            output[0] = tmp;
            System.out.println("Major.Game mode was changed on " + tmp);
        }
        return output;
    }

    void playMinesweeper() {
        getStartingGameInput();
        int flag = cmdGameCycle();
        /*if (flag == -1)*/
        System.out.println("That's all.");
        //else if (flag == 1) new Major.Game();
    }

}
