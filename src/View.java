import javax.swing.*;

public class View {
    private int fieldWidth;//ширина?
    private int fieldHeight;
    JFrame frame = new JFrame("Minesweeper");
    private JPanel panel = new JPanel();
    //private JButton[][] fieldCells;

    View(int xInputValue, int yInputValue) {
        fieldWidth = xInputValue;
        fieldHeight = yInputValue;
        //fieldCells = new JButton[fieldHeight][fieldWidth];

        panel.setBounds(0, 0, fieldWidth * 100, fieldHeight * 100);
        frame.add(panel);

        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                // fieldCells[i][j].setBounds(i * 100, j * 100, 100, 100);
                JButton tmp = new JButton();
                tmp.setBounds(i * 100, j * 100, 100, 100);
                panel.add(tmp);
            }
        }

        frame.setSize(fieldWidth * 100, fieldHeight * 100);
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
