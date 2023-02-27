import java.util.Scanner;

public class Game {
    private Controller controller;

    Game() {
        Scanner in = new Scanner(System.in);
        System.out.println("enter x and y values of the field...");
        int xInputValue = in.nextInt();
        int yInputValue = in.nextInt();
        controller = new Controller(xInputValue, yInputValue);

        //controller.printField();
        System.out.println("enter count of mines...");
        int countOfMines = in.nextInt();
        controller.placeMinesOnField(countOfMines);
        controller.printFullyOpenedField();
        controller.printOpenedField();
        in.close();
    }

    Game(int xInputValue, int yInputValue) {
        controller = new Controller(xInputValue, yInputValue);

    }


}
