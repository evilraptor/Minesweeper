package Major;

import MVC.Controller;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Game {
    //TODO а может если делать нечего сделать считывание по графике входного сразу
    //private int fieldWidth;//ширина? private int fieldHeight;
    private Controller gameController = null;
    private int countOfMines;
    private boolean puttingFlagMode;
    private boolean graphicFlag;

    int checkForCommand(String input) throws IOException {//Exit, About, New Game, High Scores.
        switch (input) {
            case "Exit" -> {
                return -1;
            }
            case "About" -> {
                System.out.println("Commands: Exit, About, New Game, High Scores, Put Flags, Open Cell.");
                return 2;
            }
            case "New Game" -> {
                startNewGame();
                return 1;
            }
            case "High Scores" -> {
                printBestScores();
                return 2;
            }
            default -> {
                return 0;
            }
        }
    }//-1 exit 0 okk 1 new game 2 again

    String getStartingGameInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("Your name: ");
        String name = in.nextLine();
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
        gameController = new Controller(xInputValue, yInputValue, countOfMines, graphicFlag);
        gameController.placeMinesOnField(countOfMines);
        gameController.printFullyOpenedField();/////////
        gameController.printOpenedField();
        return name;
        //in.close();
    }

    //TODO дописать под графику игровой цикл без этого цикл не видит вход
    int cmdGameCycle() throws IOException {
        int flagCount = gameController.getFlagsCount();

        while (true) {
            int flag;
            if (!graphicFlag) {
                System.out.println("\nCount of mines: " + flagCount);
                String[] input = new String[1];
                while (input.length == 1) {
                    input = getCycleInput(puttingFlagMode);
                    flag = checkForCommand(input[input.length - 1]);
                    if ((flag != 0) || (flag != 2)) return flag;
                }
//TODO дописать про команды всякие
                //String tmp;// = Arrays.toString(input);
                int tmpX = Integer.parseInt(input[0]);//in.nextInt();
                int tmpY = Integer.parseInt(input[1]);//in.nextInt();
                if (!puttingFlagMode) {
                    /*tmp = */
                    gameController.openCell(tmpX, tmpY);
                } else {
                    if (gameController.makeFlagOnFieldOpposite(tmpX, tmpY)) {
                        flagCount--;
                    } else {
                        flagCount++;
                    }
                }

                if ((gameController.checkIsAnyMineTouched()) || ((gameController.checkIsGameEnded()))) {
                    break;
                }
            } else {
                if ((gameController.checkIsGameEnded()) || (gameController.checkIsAnyMineTouched())) break;
            }


            //System.out.println(tmp);
        }
        if (gameController.checkIsGameEnded())
            return -1;
        //in.close();
        return 0;
    }//-1 exit 0 okk 1 new game

    void startNewGame() throws IOException {
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
            System.out.println("Game mode was changed on " + tmp);
        }
        return output;
    }

    void playMinesweeper() throws IOException {
        String name = getStartingGameInput();
        long startTime = System.nanoTime();
        int flag = cmdGameCycle();
        Date end = new Date();
        long endTime = System.nanoTime();
        if (graphicFlag) endGameWithGraphic();
        /*if (flag == -1)*/
        System.out.println("That's all.");
        double deltaTime = (double) (endTime - startTime) / 1000000 / 1000;
        System.out.println("Time: " + deltaTime);
        writeResultToLeaderBoard(name, deltaTime);
        //else if (flag == 1) new Game();
    }

    void endGameWithGraphic() {
        gameController.stopGraphicInput();
    }

    void writeResultToLeaderBoard(String name, double deltaTime) throws IOException {
        File file = new File("LeaderBoard.txt");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            fileOutputStream.write((name + " " + deltaTime).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileOutputStream.close();
    }

    void printBestScores() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("LeaderBoard.txt");

        int i;

        while ((i = fileInputStream.read()) != -1) {

            System.out.print((char) i);
        }
    }
}
