package controller;


import model.Patient;
import model.PatientModel;
import view.PatientTableView;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PatientTableViewController {
    private PatientTableView controlledTableView;
    private PatientModel controlledModel;

    private int rowsOnPage;
    private int currentPage = 1;

    public PatientTableViewController(PatientTableView controlledTableView, PatientModel controlledModel, int rowsOnPage) {
        this.controlledTableView = controlledTableView;
        this.controlledModel = controlledModel;
        this.rowsOnPage = rowsOnPage;
    }

    public void toFirstPage() {
        currentPage = 1;
        updateTableView();
    }

    public void toLastPage() {
        currentPage = controlledModel.getPatients().size() / rowsOnPage + 1;
        updateTableView();
    }

    public void nextPage() {
        if (currentPage <= controlledModel.getPatients().size() / rowsOnPage) {
            currentPage++;
            updateTableView();
        }
    }

    public void prevPage() {
        if (currentPage > 1) {
            currentPage--;
            updateTableView();
        }
    }

    private void clearTableView() {
        DefaultTableModel tableViewModel = (DefaultTableModel) controlledTableView.getTable().getModel();
        tableViewModel.setRowCount(0);
    }

    public void updateTableView() {
        clearTableView();
        DefaultTableModel tableViewModel = (DefaultTableModel) controlledTableView.getTable().getModel();

        int startIdx = (currentPage-1)*rowsOnPage;
        int endIdx = Math.min(currentPage * rowsOnPage, controlledModel.getPatients().size());

        List<Patient> tablesArray = controlledModel.getPatients().subList(startIdx, endIdx);
        for (Patient tableEntry : tablesArray) tableViewModel.addRow(tableEntry.toObject());

        updateTableStatus(tablesArray.size());
    }

    private void updateTableStatus(int entriesOnPage) {
        int pagesAmount = controlledModel.getPatients().size() / rowsOnPage + 1;
        controlledTableView.getTableStatus().setText("" +
                "Номер страницы: " + currentPage + "   " +
                "Всего страниц: " + pagesAmount + "   " +
                "Записей на странице: " + entriesOnPage + "   " +
                "Всего записей: " + controlledModel.getPatients().size()
        );
    }

    public void updateModel(ArrayList<Patient> newTableArray) { controlledModel.setPatients(newTableArray); }
}
