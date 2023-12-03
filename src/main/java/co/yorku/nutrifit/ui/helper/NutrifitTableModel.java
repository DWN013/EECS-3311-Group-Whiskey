package co.yorku.nutrifit.ui.helper;

import javax.swing.table.DefaultTableModel;

public class NutrifitTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
