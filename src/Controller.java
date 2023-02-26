public class Controller {
    private int fieldWidth;//ширина?
    private int fieldLength;

    Controller(int x, int y) {
        fieldWidth = x;
        fieldLength = y;
    }

    void placeMinesOnField(int countOfMines, Model fieldSource) {
        int mineValue = -1;
        for (int i = 0; i < countOfMines; ) {
            int mineXCoordinate = (int) Math.random() * fieldWidth;
            int mineYCoordinate = (int) Math.random() * fieldLength;
            int flagMineFreeCell = fieldSource.getCellValue(mineXCoordinate, mineYCoordinate);
            if (flagMineFreeCell != -1) {
                fieldSource.changeCellValue(mineXCoordinate, mineYCoordinate, mineValue);
                i++;
            }
        }
        fieldSource.placeCountsOfCloseMines();
    }

}
