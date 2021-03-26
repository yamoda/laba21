package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PatientTableView extends JPanel {
    private JTable applicationTable;

    private JButton firstPageButton;
    private JButton lastPageButton;
    private JButton nextPageButton;
    private JButton prevPageButton;

    private JLabel tableStatus;

    public PatientTableView() {
        setLayout(new BorderLayout());

        createUI();
        createTable();
    }

    private void createUI() {
        JPanel uiPanel = new JPanel(new GridLayout(3, 1));
        JPanel pagesNumberPanel = new JPanel(new GridLayout(1, 2));
        JPanel nextPrevPanel = new JPanel(new GridLayout(1, 2));

        tableStatus = new JLabel("" +
                "Номер страницы: 1  " +
                "Всего страниц: 1  " +
                "Записей на странице: 0  " +
                "Всего записей: 0"
        );
        uiPanel.add(tableStatus);

        firstPageButton = new JButton("Первая");
        lastPageButton = new JButton("Последняя");

        nextPageButton = new JButton("->");
        prevPageButton = new JButton("<-");

        pagesNumberPanel.add(firstPageButton);
        pagesNumberPanel.add(lastPageButton);
        nextPrevPanel.add(prevPageButton);
        nextPrevPanel.add(nextPageButton);

        uiPanel.add(pagesNumberPanel);
        uiPanel.add(nextPrevPanel);
        add(uiPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        Vector<String> columnNames = new Vector<String>();
        columnNames.add("ФИО пациента");
        columnNames.add("Адрес прописки");
        columnNames.add("Дата рождения");
        columnNames.add("Дата приема");
        columnNames.add("Фио врача");
        columnNames.add("Заключение");

        JPanel tablePanel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        applicationTable = new JTable(tableModel);
        applicationTable.setFont(new Font("Times Roman", Font.PLAIN, 15));
        applicationTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

        applicationTable.getTableHeader().setPreferredSize(new Dimension(100, 50));
        applicationTable.setRowHeight(50);

        tablePanel.add(applicationTable.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(applicationTable, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);
    }

    public JTable getTable() { return applicationTable; }

    public JLabel getTableStatus() { return tableStatus; }

    public JButton getFirstPageButton() { return firstPageButton; }

    public JButton getLastPageButton() { return lastPageButton; }

    public JButton getNextPageButton() { return nextPageButton; }

    public JButton getPrevPageButton() { return prevPageButton; }
}
