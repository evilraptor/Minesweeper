import java.io.*;
import java.util.Scanner;

public class Game {
    private Controller gameController;
    private int countOfMines;

    int checkForCommand(String input) {//Exit, About, New Game, High Scores.
        if (input == "Exit") {
            return -1;
        } else if (input == "About") {
            System.out.println("Commands: Exit, About, New Game, High Scores.");
            return 0;
        } else if (input == "New Game") {
            return 1;
        } else if (input == "High Scores") {
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
        } else return 0;
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
        //controller.printFullyOpenedField();
        gameController.printOpenedField();
        //in.close();
    }

    int gameCycle() {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("enter opening cell coordinate or command...");
            String tmp = in.nextLine();
            int flag = checkForCommand(tmp);
            if (flag != 0) return flag;
            String input[] = tmp.split(" ");
            int tmpX = Integer.parseInt(input[0]);//in.nextInt();
            int tmpY = Integer.parseInt(input[1]);//in.nextInt();
            tmp = gameController.makeChoice(tmpX, tmpY);
            if ((tmp.equals("game if over")) || (tmp.equals("you won"))) {
                break;
            }
            System.out.println(tmp);
        }
        in.close();
        return 0;
    }//-1 exit 0 okk 1 new game

    void startNewGame() {
    }

    Game() {
        getStartingGameInput();
        int flag = gameCycle();
        if (flag == -1) return;
        else if (flag == 1) new Game();
    }

    Game(int xInputValue, int yInputValue) {
        gameController = new Controller(xInputValue, yInputValue);

    }


}
