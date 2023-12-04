package co.yorku.nutrifit.ui.helper;

import javax.swing.table.DefaultTableModel;

/*

    This class extends DefaultTableModel
    And overrides the isCellEditable method so
    users cannot edit tables in the Journal Views

 */
public class NutrifitTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
