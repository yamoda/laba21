package view;


import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class RemovalView {
    private JFrame removalWindow;

    private JTextField fullNameField;
    private JTextField doctorNameField;

    private JSpinner birthDateField;

    private JButton removalButton;
    private JLabel removalInfo;

    public RemovalView() {
        removalWindow = new JFrame();
        constructUI();
    }

    private void constructUI() {
        GridLayout layoutUI = new GridLayout(0, 1);
        Font mainFont = new Font("Times Roman", Font.PLAIN,23);
        JPanel panelUI = new JPanel(layoutUI);

        fullNameField = new JTextField();
        fullNameField.setFont(mainFont);

        doctorNameField = new JTextField();
        doctorNameField.setFont(mainFont);

        SimpleDateFormat model = new SimpleDateFormat("dd/MM/yyyy");
        birthDateField = new JSpinner(new SpinnerDateModel());
        birthDateField.setFont(mainFont);
        birthDateField.setEditor(new JSpinner.DateEditor(birthDateField, model.toPattern()));

        birthDateField = new JSpinner(new SpinnerDateModel());
        birthDateField.setFont(mainFont);
        birthDateField.setEditor(new JSpinner.DateEditor(birthDateField, model.toPattern()));

        JPanel fullNamePanel = new JPanel(new GridLayout(1, 2));
        fullNamePanel.add(new JLabel("Фио: "));
        fullNamePanel.add(fullNameField);
        panelUI.add(fullNamePanel);

        JPanel doctorNamePanel = new JPanel(new GridLayout(1, 2));
        doctorNamePanel.add(new JLabel("Фио врача: "));
        doctorNamePanel.add(doctorNameField);
        panelUI.add(doctorNamePanel);

        JPanel birthDatePanel = new JPanel(new GridLayout(1, 2));
        birthDatePanel.add(new JLabel("Дата рождения: "));
        birthDatePanel.add(birthDateField);
        panelUI.add(birthDatePanel);

        removalButton = new JButton("Удалить");
        panelUI.add(removalButton);

        removalInfo = new JLabel("");
        removalInfo.setFont(new Font("Times Roman", Font.PLAIN,30));

        removalWindow.add(panelUI, BorderLayout.NORTH);
        removalWindow.add(removalInfo, BorderLayout.CENTER);
    }

    public JFrame getFrame() { return removalWindow; }

    public JButton getRemovalButton() { return removalButton; }

    public JTextField getFullNameField() { return fullNameField; }

    public JTextField getDoctorNameField() { return doctorNameField; }

    public JSpinner getBirthDateField() { return birthDateField; }

    public JLabel getRemovalInfo() { return removalInfo; }
}

