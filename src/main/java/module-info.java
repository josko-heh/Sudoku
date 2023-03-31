module hr.tvz.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.apache.commons.lang3;

	opens hr.tvz.sudoku.generator to javafx.fxml;
    opens hr.tvz.sudoku.startup to javafx.fxml;
    
    exports hr.tvz.sudoku.generator;
    exports hr.tvz.sudoku.startup;
}