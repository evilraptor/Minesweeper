import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private Controller gameController;
    private int countOfMines;

    int checkForCommand(String input) {//Exit, About, New Game, High Scores.
        switch (input) {
            case "Exit" -> {
                return -1;
            }
            case "About" -> {
                System.out.println("Commands: Exit, About, New Game, High Scores, Put Flags, Open Cell.");
                return 0;
            }
            case "New Game" -> {
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
        gameController = new Controller(xInputValue, yInputValue);
        System.out.println("enter count of mines...");
        countOfMines = in.nextInt();
        gameController.placeMinesOnField(countOfMines);
        gameController.printFullyOpenedField();/////////
        gameController.printOpenedField();
        //in.close();
    }

    int gameCycle() {
        boolean puttingFlagMode = false;
        int flagCount = gameController.getFlagsCount();

        while (true) {
            System.out.println("\nCount of mines: " + flagCount);
            String[] input = getCycleInput(puttingFlagMode);

            int flag = checkForCommand(input[input.length - 1]);
            if (flag != 0) return flag;

            String tmp = Arrays.toString(input);
            int tmpX = Integer.parseInt(input[0]);//in.nextInt();
            int tmpY = Integer.parseInt(input[1]);//in.nextInt();
            if (!puttingFlagMode) {
                tmp = gameController.openCell(tmpX, tmpY);
            } else {
                gameController.makeFlagOnFieldOpposite(tmpX, tmpY);
                flagCount++;
            }

            if ((tmp.equals("Game over")) || ((gameController.checkIsGameEnded()) || (flagCount == 0))) {
                break;
            }
            //System.out.println(tmp);
        }
        //in.close();
        return 0;
    }//-1 exit 0 okk 1 new game

    Controller startNewGame() {
        return new Controller();
    }

    String[] getCycleInput(boolean puttingFlagMode) {
        Scanner in = new Scanner(System.in);
        System.out.print("Now mode is ");
        if (puttingFlagMode) System.out.print("Put Flag\n");
        else System.out.print("Open Cell\n");
        System.out.println("Enter opening cell coordinate or command. Or change mode on Open Cell/Put Flag...");
        String tmp = in.nextLine();
        if ((tmp.equals("Open Cell")) && (puttingFlagMode)) {
            puttingFlagMode = false;
        } else if ((tmp.equals("Put Flag")) && (!puttingFlagMode)) {
            puttingFlagMode = true;
        }
        String[] outputTmp = tmp.split(" ");
        int arrayTmpLength = outputTmp.length;
        String[] output = new String[arrayTmpLength + 1];
        System.arraycopy(outputTmp, 0, output, 0, arrayTmpLength);//for (int i=0;i<arrayTmpLength;i++)output[i]=outputTmp[i];
        output[arrayTmpLength] = tmp;
        return output;
    }

    void playMinesweeper() {
        getStartingGameInput();
        int flag = gameCycle();
        if (flag == -1) return;
        else if (flag == 1) new Game();
    }

}
