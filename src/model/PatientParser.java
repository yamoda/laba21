package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;

public class PatientParser {
    public void parseAndWrite(String rootTag, String nodeTag, ArrayList<Patient> parsedModelArray, String writePath) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document writtenDocument = documentBuilder.newDocument();

            Element rootElement = writtenDocument.createElement(rootTag);
            writtenDocument.appendChild(rootElement);


            for (Patient currentEntry : parsedModelArray) {
                String fullName = currentEntry.getFullName();
                String address = currentEntry.getAddress();
                String birthDate = currentEntry.getBirthDate().toString();
                String appointmentDate = currentEntry.getAppointDate().toString();
                String doctorName = currentEntry.getDoctorName();
                String conclusion = currentEntry.getConclusion();

                rootElement.appendChild(getEntry(writtenDocument, nodeTag, fullName, address, birthDate, appointmentDate, doctorName, conclusion));
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource writtenSource = new DOMSource(writtenDocument);

            StreamResult streamResult = new StreamResult(new File(writePath));
            transformer.transform(writtenSource, streamResult);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    private Node getEntry(Document writtenDocument, String nodeTag, String fullName, String address, String birthDate,
                          String appointmentDate, String doctorName, String conclusion) {

        Element newEntry = writtenDocument.createElement(nodeTag);

        newEntry.appendChild(getEntryElements(writtenDocument, newEntry, "fullName", fullName));
        newEntry.appendChild(getEntryElements(writtenDocument, newEntry, "address", address));
        newEntry.appendChild(getEntryElements(writtenDocument, newEntry, "birthDate", birthDate));
        newEntry.appendChild(getEntryElements(writtenDocument, newEntry, "appointmentDate", appointmentDate));
        newEntry.appendChild(getEntryElements(writtenDocument, newEntry, "doctorName", doctorName));
        newEntry.appendChild(getEntryElements(writtenDocument, newEntry, "conclusion", conclusion));

        return newEntry;
    }

    private Node getEntryElements(Document writtenDocument, Element element, String name, String value) {
        Element node = writtenDocument.createElement(name);
        node.appendChild(writtenDocument.createTextNode(value));

        return node;
    }

    public ArrayList<Patient> readAndParse(String parsePath) {
        ArrayList<Patient> patients=null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            var documentModelReader = factory.newSAXParser();
            var writerHandler = new ParserHandler();

            documentModelReader.parse(parsePath, writerHandler);
            patients = writerHandler.getPatients();
        }
        catch (Exception e) { e.printStackTrace(); }

        return patients;
    }
}
