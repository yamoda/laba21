package model;

import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class ParserHandler extends DefaultHandler {
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    private Stack elementStack = new Stack();
    private Stack objectStack = new Stack();

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.elementStack.push(qName);

        if ("Patient".equals(qName)) {
            Patient newPatient = new Patient();
            this.objectStack.push(newPatient);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();

        if (value.length() == 0) return;

        if ("fullName".equals(currentElement())) {
            Patient newPatient = (Patient) this.objectStack.peek();
            newPatient.setFullName(value);
        }
        else if ("address".equals(currentElement())) {
            Patient newPatient = (Patient) this.objectStack.peek();
            newPatient.setAddress(value);
        }
        else if ("birthDate".equals(currentElement())) {
            String[] parsedDate = value.split("/");
            int year = Integer.parseInt(parsedDate[2]);
            int month = Integer.parseInt(parsedDate[1]);
            int day = Integer.parseInt(parsedDate[0]);

            Patient newPatient = (Patient) this.objectStack.peek();
            newPatient.setBirthDate(new Date(year, month, day));
        }
        else if ("appointmentDate".equals(currentElement())) {
            String[] parsedDate = value.split("/");
            int year = Integer.parseInt(parsedDate[2]);
            int month = Integer.parseInt(parsedDate[1]);
            int day = Integer.parseInt(parsedDate[0]);

            Patient newPatient = (Patient) this.objectStack.peek();
            newPatient.setAppointDate(new Date(year, month, day));
        }
        else if ("doctorName".equals(currentElement())) {
            Patient newPatient = (Patient) this.objectStack.peek();
            newPatient.setDoctorName(value);
        }
        else if ("conclusion".equals(currentElement())) {
            Patient newPatient = (Patient) this.objectStack.peek();
            newPatient.setConclusion(value);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.elementStack.pop();

        if ("Patient".equals(qName)) {
            Patient object = (Patient) this.objectStack.pop();
            this.patients.add(object);
        }
    }

    private String currentElement() {
        return (String) this.elementStack.peek();
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }
}
