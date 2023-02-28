import java.io.*;
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
        Scanner in = new Scanner(System.in);
        boolean puttingFlagMode = false;
        while (true) {
            System.out.print("Now mode is ");
            if (puttingFlagMode) System.out.print("Put Flag\n");
            else System.out.print("Open Cell\n");
            System.out.println("Enter opening cell coordinate or command. Or change mode on Open Cell/Put Flag...");
            String tmp = in.nextLine();
            if ((tmp.equals("Open Cell")) && (puttingFlagMode)) {
                puttingFlagMode = false;
                continue;
            } else if ((tmp.equals("Put Flag")) && (!puttingFlagMode)) {
                puttingFlagMode = true;
                continue;
            }

            int flag = checkForCommand(tmp);
            if (flag != 0) return flag;
            String[] input = tmp.split(" ");
            int tmpX = Integer.parseInt(input[0]);//in.nextInt();
            int tmpY = Integer.parseInt(input[1]);//in.nextInt();
            if (!puttingFlagMode) {
                tmp = gameController.makeChoice(tmpX, tmpY);
            } else {
                gameController.makeFlagOnFieldOpposite(tmpX, tmpY);
            }
            if ((tmp.equals("game if over")) || (tmp.equals("you won"))) {
                break;
            }
            //System.out.println(tmp);
        }
        in.close();
        return 0;
    }//-1 exit 0 okk 1 new game

    void startNewGame() {
    }


    void playMinesweeper() {
        getStartingGameInput();
        int flag = gameCycle();
        if (flag == -1) return;
        else if (flag == 1) new Game();
    }

}
