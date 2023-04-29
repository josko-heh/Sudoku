module hr.tvz.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.xml;

	opens hr.tvz.sudoku.components.board to javafx.fxml;
    opens hr.tvz.sudoku.startup to javafx.fxml;
    
    exports hr.tvz.sudoku.components.board;
    exports hr.tvz.sudoku.startup;
}