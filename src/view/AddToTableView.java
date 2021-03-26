package view;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;

public class AddToTableView {
    private JFrame additionWindow;

    private JTextField fullNameField;
    private JTextField addressField;

    private JSpinner birthDateField;
    private JSpinner appointmentDateField;

    private JTextField doctorFullNameField;
    private JTextField conclusionField;

    private JButton inputButton;

    public AddToTableView() {
        additionWindow = new JFrame();
        constructUI();
    }

    private void constructUI() {
        GridLayout layoutUI = new GridLayout(0, 1);
        layoutUI.setVgap(5);

        Font mainFont = new Font("Times Roman", Font.PLAIN,23);
        JPanel panelUI = new JPanel(layoutUI);

        fullNameField = new JTextField();
        fullNameField.setFont(mainFont);

        addressField = new JTextField();
        addressField.setFont(mainFont);

        SimpleDateFormat model = new SimpleDateFormat("dd/MM/yyyy");
        birthDateField = new JSpinner(new SpinnerDateModel());
        birthDateField.setFont(mainFont);
        birthDateField.setEditor(new JSpinner.DateEditor(birthDateField, model.toPattern()));

        appointmentDateField = new JSpinner(new SpinnerDateModel());
        appointmentDateField.setFont(mainFont);
        appointmentDateField.setEditor(new JSpinner.DateEditor(appointmentDateField, model.toPattern()));

        doctorFullNameField = new JTextField();
        doctorFullNameField.setFont(mainFont);

        conclusionField = new JTextField();
        conclusionField.setFont(mainFont);

        JPanel fullNamePanel = new JPanel(new GridLayout(1, 2));
        fullNamePanel.add(new JLabel("ФИО: "));
        fullNamePanel.add(fullNameField);
        panelUI.add(fullNamePanel);

        JPanel addressPanel = new JPanel(new GridLayout(1, 2));
        addressPanel.add(new JLabel("Адрес: "));
        addressPanel.add(addressField);
        panelUI.add(addressPanel);

        JPanel birthDatePanel = new JPanel(new GridLayout(1, 2));
        birthDatePanel.add(new JLabel("Дата рождения: "));
        birthDatePanel.add(birthDateField);
        panelUI.add(birthDatePanel);

        JPanel appointmentDatePanel = new JPanel(new GridLayout(1, 2));
        appointmentDatePanel.add(new JLabel("Дата приема: "));
        appointmentDatePanel.add(appointmentDateField);
        panelUI.add(appointmentDatePanel);

        JPanel doctorPanel = new JPanel(new GridLayout(1, 2));
        doctorPanel.add(new JLabel("ФИО врача: "));
        doctorPanel.add(doctorFullNameField);
        panelUI.add(doctorPanel);

        JPanel conclusionPanel = new JPanel(new GridLayout(1, 2));
        conclusionPanel.add(new JLabel("Заключение: "));
        conclusionPanel.add(conclusionField);
        panelUI.add(conclusionPanel);

        inputButton = new JButton("Ввести");
        panelUI.add(inputButton);

        additionWindow.add(panelUI);
    }

    public JFrame getFrame() { return additionWindow; }

    public JSpinner getBirthDateField() { return birthDateField; }

    public JSpinner getAppointmentDateField() { return appointmentDateField; }

    public JTextField getFullNameField() { return fullNameField; }

    public JTextField getAddressField() { return addressField; }

    public JTextField getDoctorFullNameField() { return doctorFullNameField; }

    public JTextField getConclusionField() { return conclusionField; }

    public JButton getInputButton() { return inputButton; }
}
