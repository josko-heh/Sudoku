module hr.tvz.sudoku {
    requires javafx.controls;
    requires javafx.fxml;
            
    opens hr.tvz.sudoku.controller to javafx.fxml;
    opens hr.tvz.sudoku.startup to javafx.fxml;
    
    exports hr.tvz.sudoku.controller;
    exports hr.tvz.sudoku.startup;
}