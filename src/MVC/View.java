package MVC;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//TODO был цикл конструкторов нужно здесь убрать и все закинуть из вью в контроль
public class View {
    private int fieldWidth;//ширина?
    private int fieldHeight;
    JFrame frame = new JFrame("Minesweeper");
    public JButton[][] cells;

    //MVC.Controller viewController;
    private boolean puttingFlagMode = false;

    public View(int xInputValue, int yInputValue, int inputCountOfMines) {
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
                cells[i][j].setBounds(i * 100, (j + 1) * 100, 100, 100);
        /*        cells[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!puttingFlagMode) {
                            //if(viewController.openCell(tmpX, tmpY)=="Major.Game over") cells[tmpX][tmpY].setDisabledSelectedIcon(icon);
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
*/
                frame.add(cells[i][j]);
            }
        }
        frame.setSize((fieldWidth + 2) * 100, (fieldHeight + 2) * 100);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
