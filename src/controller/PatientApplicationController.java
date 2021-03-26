package controller;

import model.Date;
import model.Patient;
import model.PatientModel;
import model.PatientParser;
import view.AddToTableView;
import view.PatientApplicationView;
import view.RemovalView;
import view.SearchingView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class PatientApplicationController {
    private PatientApplicationView view;
    private PatientModel model;

    private PatientTableViewController mainPatientTableController;

    public PatientApplicationController(PatientModel model, PatientApplicationView view) {
        this.model = model;
        this.view = view;

        final int windowWidth = 1200;
        final int windowHeight = 800;

        this.view.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.view.getFrame().setSize(windowWidth, windowHeight);
        this.view.getFrame().setVisible(true);

        mainPatientTableController = new PatientTableViewController(view.getMainTable(), model, 10);
        initController();
    }

    private void initController() {
        view.getSaveButton().addActionListener(e -> save());
        view.getOpenButton().addActionListener(e -> load());

        view.getNewEntryButton().addActionListener(e -> initAdditionDialog());
        view.getSearchButton().addActionListener(e -> initSearchingDialog());
        view.getDeleteButton().addActionListener(e -> initRemovalDialog());

        view.getSaveItem().addActionListener(e -> save());
        view.getOpenItem().addActionListener(e -> load());

        view.getNewEntryItem().addActionListener(e -> initAdditionDialog());
        view.getSearchItem().addActionListener(e -> initSearchingDialog());
        view.getDeleteItem().addActionListener(e -> initRemovalDialog());

        view.getMainTable().getFirstPageButton().addActionListener(e -> mainPatientTableController.toFirstPage());
        view.getMainTable().getLastPageButton().addActionListener(e -> mainPatientTableController.toLastPage());
        view.getMainTable().getNextPageButton().addActionListener(e -> mainPatientTableController.nextPage());
        view.getMainTable().getPrevPageButton().addActionListener(e -> mainPatientTableController.prevPage());
    }

    private void save() {
        JFrame dialogFrame = null;
        FileDialog fileDialog = new FileDialog(dialogFrame, "save", FileDialog.SAVE);

        fileDialog.setFile("saved.xml");
        fileDialog.setVisible(true);

        String savePath = fileDialog.getDirectory() + fileDialog.getFile();

        if(!savePath.equals("nullnull")) {
            PatientParser xmlModelWriter = new PatientParser();
            ArrayList<Patient> entriesArray = model.getPatients();
            xmlModelWriter.parseAndWrite("Patients", "Patient", entriesArray, savePath);
        }
    }

    private void load() {
        JFrame dialogFrame = null;
        FileDialog fileDialog = new FileDialog(dialogFrame, "open", FileDialog.LOAD);
        fileDialog.setVisible(true);

        String loadPath = fileDialog.getDirectory() + fileDialog.getFile();

        if(!loadPath.equals("nullnull")) {
            PatientParser xmlDocumentReader = new PatientParser();
            ArrayList<Patient> patients = xmlDocumentReader.readAndParse(loadPath);
            model.setPatients(patients);
            mainPatientTableController.updateModel(patients);
            mainPatientTableController.updateTableView();
        }
    }

    private void initAdditionDialog() {
        final int windowWidth = 870;
        final int windowHeight = 500;

        var additionDialog = new AddToTableView();
        additionDialog.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        additionDialog.getFrame().setSize(windowWidth, windowHeight);
        additionDialog.getFrame().setVisible(true);

        additionDialog.getInputButton().addActionListener(e -> {
            var fullName = additionDialog.getFullNameField().getText();
            var address = additionDialog.getAddressField().getText();
            var doctorName = additionDialog.getDoctorFullNameField().getText();
            var conclusion = additionDialog.getConclusionField().getText();

            java.util.Date aDate = (java.util.Date) additionDialog.getAppointmentDateField().getValue();
            java.util.Date bDate = (java.util.Date) additionDialog.getBirthDateField().getValue();

            Date appointmentDate = new Date(aDate);
            Date birthDate = new Date(bDate);

            if(!fullName.isEmpty() && !address.isEmpty() && !doctorName.isEmpty() && !conclusion.isEmpty()) {
                ArrayList<Patient> patientModel = model.getPatients();
                patientModel.add(new Patient(fullName, address, birthDate, appointmentDate, doctorName, conclusion));

                model.setPatients(patientModel);
                mainPatientTableController.updateModel(patientModel);
                mainPatientTableController.updateTableView();

                additionDialog.getFrame().dispose();
            }
            else JOptionPane.showMessageDialog(null, "Все строки должны быть заполнены корректно");
        });
    }

    private void initSearchingDialog() {
        final int windowWidth = 870;
        final int windowHeight = 500;

        var searchingDialog = new SearchingView();
        searchingDialog.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        searchingDialog.getFrame().setSize(windowWidth, windowHeight);
        searchingDialog.getFrame().setVisible(true);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1900);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);

        java.util.Date date = cal.getTime();
        searchingDialog.getBirthDateField().setValue(date);

        searchingDialog.getSearchButton().addActionListener(e -> {
            var fullName = searchingDialog.getFullNameField().getText();
            var doctorName = searchingDialog.getDoctorNameField().getText();

            java.util.Date bDate = (java.util.Date) searchingDialog.getBirthDateField().getValue();
            Date birthDate = new Date(bDate);

            ArrayList<Patient> foundArray = (ArrayList<Patient>) find(fullName, doctorName, birthDate);
            PatientTableViewController foundTableController = new PatientTableViewController(searchingDialog.getMatchesTable(), new PatientModel(), 3);
            foundTableController.updateModel(foundArray);
            foundTableController.updateTableView();

            searchingDialog.getMatchesTable().getFirstPageButton().addActionListener(event -> foundTableController.toFirstPage());
            searchingDialog.getMatchesTable().getLastPageButton().addActionListener(event -> foundTableController.toLastPage());
            searchingDialog.getMatchesTable().getNextPageButton().addActionListener(event -> foundTableController.nextPage());
            searchingDialog.getMatchesTable().getPrevPageButton().addActionListener(event -> foundTableController.prevPage());
        });
    }

    private void initRemovalDialog() {
        final int windowWidth = 870;
        final int windowHeight = 300;

        var removalDialog = new RemovalView();
        removalDialog.getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removalDialog.getFrame().setSize(windowWidth, windowHeight);
        removalDialog.getFrame().setVisible(true);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1900);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 0);

        java.util.Date date = cal.getTime();
        removalDialog.getBirthDateField().setValue(date);

        removalDialog.getRemovalButton().addActionListener(e -> {
            var fullName = removalDialog.getFullNameField().getText();
            var doctorName = removalDialog.getDoctorNameField().getText();

            java.util.Date bDate = (java.util.Date) removalDialog.getBirthDateField().getValue();
            Date birthDate = new Date(bDate);

            ArrayList<Patient> toDelete = (ArrayList<Patient>) find(fullName, doctorName, birthDate);
            model.getPatients().removeAll(toDelete);

            mainPatientTableController.updateModel(model.getPatients());
            mainPatientTableController.updateTableView();

            if(toDelete.size() == 0) removalDialog.getRemovalInfo().setText("Ничего не было удалено");
            else removalDialog.getRemovalInfo().setText("Было удалено записей: "+toDelete.size());
        });
    }

    private boolean stringMatch(String firstString, String secondString) {
        String[] firstNameByPosition = firstString.split(" ");
        String[] secondNameByPosition = secondString.split(" ");

        for (int i=0; i<secondNameByPosition.length; i++) if (!firstNameByPosition[i].equals(secondNameByPosition[i])) return false;
        return true;
    }

    private List<Patient> find(String fullName, String doctorName, Date birthDate) {
            String strIgnoreValue = "";
            String birthDateIgnoreString = "31/11/1899";

            var foundArray = model.getPatients()
                    .stream()
                    .filter(patient -> stringMatch(patient.getFullName(), fullName) || fullName.equals(strIgnoreValue))
                    .filter(patient -> stringMatch(patient.getDoctorName(), doctorName) || doctorName.equals(strIgnoreValue))
                    .filter(patient -> patient.getBirthDate().toString().equals(birthDate.toString()) || birthDate.toString().equals(birthDateIgnoreString))
                    .collect(Collectors.toList());

            return foundArray;
    }
}
