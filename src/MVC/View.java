package MVC;
//TODO учесть везде что когда мы берем координату он скрин есть еще и плашка сверху размером 30пх

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//TODO был цикл конструкторов нужно здесь убрать и все закинуть из вью в контроль
public class View {
    private int fieldWidth;//ширина?
    private int fieldHeight;
    JFrame frame = new JFrame("Minesweeper");
    public JButton[][] cells;
    public JButton flagButton;

    //MVC.Controller viewController;
    private boolean puttingFlagMode = false;
    private ImageIcon[] containerOfIcons = new ImageIcon[12];//0-8 это цифры 9 флаг 10 мина

    public View(int xInputValue, int yInputValue, int inputCountOfMines) {
        createContainerOfIcons();
        //ImageIcon icon = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\Mine.png");

        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        cells = new JButton[fieldWidth][fieldHeight];

        int tmpX;
        int tmpY;
        for (int i = 0; i < fieldWidth; i++) {
            tmpX = i;
            for (int j = 0; j < fieldHeight; j++) {
                tmpY = j;
                cells[i][j] = new JButton();
                cells[i][j].setBounds(i * 100, j * 100, 100, 100);
                frame.add(cells[i][j]);
            }
        }
        frame.setSize((fieldWidth + 2) * 100, (fieldHeight) * 100 + 60);//30 плашка сверху и 30 пустота снизу
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void createContainerOfIcons() {
        containerOfIcons[0] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\0.png");
        containerOfIcons[1] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\1.png");
        containerOfIcons[2] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\2.png");
        containerOfIcons[3] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\3.png");
        containerOfIcons[4] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\4.png");
        containerOfIcons[5] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\5.png");
        containerOfIcons[6] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\6.png");
        containerOfIcons[7] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\7.png");
        containerOfIcons[8] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\8.png");
        containerOfIcons[9] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\Flag.png");
        containerOfIcons[10] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\Mine.png");
        containerOfIcons[11] = new ImageIcon("C:\\Users\\EvilRaptor\\IdeaProjects\\Minesweeper\\src\\Icons\\FlagInGray.png");
    }

    ImageIcon[] getContainerOfIcons() {
        return containerOfIcons;
    }
Point getWindowPoint(){
    return frame.getLocation();
}
    void createFlagButton() {
        flagButton = new JButton();
        flagButton.setBounds((fieldWidth + 1) * 100, 50, 70, 70);
        flagButton.setIcon(containerOfIcons[11]);
        frame.add(flagButton);
    }
}
