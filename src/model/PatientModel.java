package model;

import java.util.ArrayList;

public class PatientModel {
    ArrayList<Patient> patients;

    public PatientModel() { patients = new ArrayList<Patient>(); }

    public ArrayList<Patient> getPatients() { return patients; }

    public void setPatients(ArrayList<Patient> patients) { this.patients = patients; }
}
