/*
 * Copyright (C) 2017 Diana Botez <dia.botez at gmail.com> - All Rights Reserved
 *
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Althering the content of this licence under any circumstances is
 * strictly forbidden.
 * This application is part of a project developed during ERASMUS+ mobility
 * at University of Zaragoza, Spain.
 * This application is open-source and is distributed WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 */
package operationplanning.commonFiles;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @abstract This class contains the medical teams. This is a singleton class.
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class PatientsList {

    /**
     * This method adds a patient to the data base. If the patient ID is already
     * in the data base, the new surgery will be added to patient's medical
     * history.
     *
     * @param ID the ID of the patient
     * @param surgery the surgery that needs to be performed
     * @param pathology the pathology for which the surgery is made
     * @param doctorInCharge the doctor who will make the surgery
     * @param doctorTeamLeader the medical lead of the team
     * @param admission
     */
    public void addNewSurgery(int ID, String surgery, String pathology, 
            MedicIdentifiers doctorInCharge, MedicIdentifiers doctorTeamLeader, 
            Date admission) {

        addSurgeryForPatient(ID, surgery, pathology, doctorInCharge, doctorTeamLeader, admission);
    }

    /**
     * This method saves the patient's personal data if it is a new patient or
     * updates them if the ID was found in the dataBase.
     *
     * @param id the id of the patient to update the details
     * @param lastName the last name of the patient
     * @param firstName the first name of the patient
     * @param patientSex the sex of the patient
     * @param birthDate the birth date of the patient
     * @param obs remarks about the patient
     */
    public void updatePatientInfo(String id, String lastName, String firstName,
            Utils.PatientGender patientSex, Date birthDate, String obs) {
        java.sql.Date d = null;
        if (birthDate != null) {
            DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = outputFormatter.format(birthDate);
            d = java.sql.Date.valueOf(dateString);
        }
        new DatabaseQueries().updatePatientInfo(id, lastName, firstName, patientSex, d, obs);
    }
    
    
    /**
     * This method saves the patient's personal data if it is a new patient or
     * updates them if the ID was found in the dataBase.
     *
     * @param lastName the last name of the patient
     * @param firstName the first name of the patient
     * @param patientSex the sex of the patient
     * @param birthDate the birth date of the patient
     * @param obs remarks about the patient
     */
    public boolean addPatientInfo(String lastName, String firstName,
            Utils.PatientGender patientSex, Date birthDate, String obs) {
        java.sql.Date d = null;
        if (birthDate != null) {
            DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = outputFormatter.format(birthDate);
            d = java.sql.Date.valueOf(dateString);
        }
        return new DatabaseQueries().addNewPatient(lastName, firstName, patientSex, d, obs);
    }

    public boolean addSurgeryForPatient(int id, String surgery, String pathology,
            MedicIdentifiers doctorInCharge, MedicIdentifiers doctorTeamLeader, Date admission) {
        DatabaseQueries db = new DatabaseQueries();
        String patientId = "P" + id;
        String srgId = db.getSurgeryId(surgery);
        String ptgId = db.getPathologyId(pathology);

        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = outputFormatter.format(admission);
        java.sql.Date d = java.sql.Date.valueOf(dateString);

        return db.addUpdateSurgeryToPatient(patientId, srgId, ptgId, "D" + doctorInCharge.IDnumber,
                "D" + doctorTeamLeader.IDnumber, d, "N", null);
    }

    /**
     * Updates the details of the selected surgery for the selected patient.
     *
     * @param patientID the Id of the patient
     * @param indexSurgery the index of desired surgery to modify data
     * @param pathology the new pathology for the selected surgery
     * @param doctorInCharge the new doctor in charge for the selected surgery
     * @param doctorTeamLeader the coordinator oof the new doctor in charge
     * @param isScheduled the scheduled status o the surgery
     * @param surgeryDate the surgery date if the patient's surgery has been
     * scheduled
     */
    public void updateSurgeryDetailsForPatient(int patientID, String surgery, String pathology,
            MedicIdentifiers doctorInCharge, MedicIdentifiers doctorTeamLeader,
            Boolean isScheduled, Date surgeryDate) {

        DatabaseQueries db = new DatabaseQueries();
        String patientId = "P" + patientID;
        String srgId = db.getSurgeryId(surgery);
        String ptgId = db.getPathologyId(pathology);

        if (surgeryDate != null && isScheduled) {
            DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = outputFormatter.format(surgeryDate);
            java.sql.Date d = java.sql.Date.valueOf(dateString);

            db.updateSurgeryScheduledStatus(patientId, srgId, ptgId,
                    "D" + doctorInCharge.IDnumber,
                    "D" + doctorTeamLeader.IDnumber, "Y", d);
        } else {
            db.updateSurgeryScheduledStatus(patientId, srgId, ptgId,
                    "D" + doctorInCharge.IDnumber,
                    "D" + doctorTeamLeader.IDnumber, "N", null);
        }
    }

    /**
     * Removes a surgery from a patient's medical history.
     *
     * @param patientID the patient ID
     * @param surgery the surgery name in patient's medical history
     */
    public void removeSurgeryForPatient(int patientID, String surgery) {
        DatabaseQueries db = new DatabaseQueries();
        String patientId = "P" + patientID;
        String srgId = db.getSurgeryId(surgery);

        db.deleteSurgeryFromPatientHistory(patientId, srgId);
    }

    // TODO: what if the surgery with the removed doctor was already scheduled
    // AND the new medic in charge has other surgeries in that day?
    /**
     * This method updates the doctorInCharge field in patient's medical history
     * to all those patient who had as a doctor, the medic who has just be
     * deleted from the dataBase.
     *
     * @param removedDoctorID the medic who has been deleted from the dataBase
     * @param currentMedicID the new medic in charge for the affected patients
     */
    public void updateDoctorInChargeIDForPatients(int removedDoctorID, int currentMedicID) {
        new DatabaseQueries().updateDoctorInChargeForAssignedPatients(
                "D" + removedDoctorID, "D" + currentMedicID);
    }

    /**
     * Update the coordinator of the given doctor in charge.
     *
     * @param newCoordinator the new coordinator to be updated
     * @param medic the doctor in charge that has the new coordinator
     */
    public void updateMedicLeaderForPatients(MedicIdentifiers newCoordinator, MedicIdentifiers medic) {
        new DatabaseQueries().updateTeamLeaderForAssignedPatients(
                "D" + medic.coordinatorID, "D" + newCoordinator.IDnumber);
    }

    /**
     * Gets the ordered waiting list of the doctor or team.
     *
     * @param doctorID "-1" to get all patients from the list or the id of
     * doctor in charge / teamLeader
     * @param teamList if is the composed waiting list of the team (doctorID
     * needs to be the ID of a coordinator) or just for the specified doctor
     * @param includeCompleted true for including patients with completed
     * surgery
     *
     * @return empty vector if the doctor's ID is invalid (no doctor or
     * coordinator with this ID have been found) or the waiting list for the
     * specified parameters
     */
    public Vector<PatientIdentifiers> getDoctorOrTeamPatientList(int doctorID, boolean teamList, boolean includeCompleted) {
        Vector<PatientIdentifiers> patientsList = new Vector<>();
        Vector<Vector<String>> patientsDetails;

        if (doctorID == -1) {
            patientsDetails = new DatabaseQueries().getPatientsDetailsForDoctorOrTeam("-1", false, includeCompleted);
        } else {
            patientsDetails = new DatabaseQueries().getPatientsDetailsForDoctorOrTeam("D" + doctorID, teamList, includeCompleted);
        }

        if (patientsDetails == null) {
            return patientsList;
        }

        int found;
        Calendar c = Calendar.getInstance();
        String[] dateElements;

        for (Vector<String> entry : patientsDetails) {
            found = -1;
            PatientIdentifiers patient = null;
            for (PatientIdentifiers patientIdentifiers : patientsList) {
                if (patientIdentifiers.patientID == Integer.parseInt(entry.get(0).replace("P", ""))) {
                    found = patientsList.indexOf(patientIdentifiers);
                    patient = patientIdentifiers;
                    break;
                }
            }

            if (found == -1) {
                Date birthDate = null;
                if (!entry.get(4).equals("")) {
                    dateElements = entry.get(4).split("-");
                    c.set(Calendar.YEAR, Integer.parseInt(dateElements[0]));
                    c.set(Calendar.MONTH, Integer.parseInt(dateElements[1]) - 1); // 0-11
                    c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateElements[2]));
                    birthDate = c.getTime();
                }
                patient = new PatientIdentifiers(Integer.parseInt(entry.get(0).replace("P", "")), entry.get(2), entry.get(1),
                        entry.get(3).equals("M") ? Utils.PatientGender.MALE
                        : entry.get(3).equals("F") ? Utils.PatientGender.FEMALE : Utils.PatientGender.OTHER,
                        birthDate, entry.get(5));
            }

            //add new surgery details
            patient.medicalHistory.surgery.add(entry.get(6));
            patient.medicalHistory.pathology.add(entry.get(7));
            patient.medicalHistory.doctorInCharge.add(medicInst.getDoctorByID(
                    Integer.parseInt(entry.get(8).replace("D", ""))));
            patient.medicalHistory.doctorTeamLeader.add(medicInst.getDoctorByID(
                    Integer.parseInt(entry.get(9).replace("D", ""))));
            if (!entry.get(10).equals("")) {
                dateElements = entry.get(10).split("-");
                c.set(Calendar.YEAR, Integer.parseInt(dateElements[0]));
                c.set(Calendar.MONTH, Integer.parseInt(dateElements[1]) - 1); // 0-11
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateElements[2]));
                patient.medicalHistory.admissionDate.add(c.getTime());
            } else {
                patient.medicalHistory.admissionDate.add(new Date());
            }
            patient.medicalHistory.scheduled.add(entry.get(11).equals("Y"));
            patient.medicalHistory.complited.add(entry.get(12).equals("Y"));
            Date scheduledDate = null;
            if (!entry.get(13).equals("")) {
                dateElements = entry.get(13).split("-");
                c.set(Calendar.YEAR, Integer.parseInt(dateElements[0]));
                c.set(Calendar.MONTH, Integer.parseInt(dateElements[1]) - 1); // 0-11
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateElements[2]));
                scheduledDate = c.getTime();
            }
            patient.medicalHistory.scheduledDate.add(scheduledDate);

            if (found == -1) {
                patientsList.add(patient);
            } else {
                patientsList.setElementAt(patient, found);
            }
        }

        return patientsList;
    }

    /**
     * This method returns the patient with the given ID.
     *
     * @param patientID the ID of the desired patient
     * @param withCompleted true if the desired result should also contain the
     * completed surgeries
     *
     * @return the patient if found or null if there is no patient found with
     * this ID
     */
    public PatientIdentifiers getPatientWithID(int patientID, boolean withCompleted) {
        return new DatabaseQueries().getPatientDetails("P" + patientID, withCompleted);
    }

    /**
     *
     */
    public PatientsList() {
        medicInst = new MedicalTeams();
//        loadInitialListOfPatients();
    }

    /**
     * This method loads an initial list of patients
     */
    private void loadInitialListOfPatients() {
        try {
            File fXmlFile = new File("./dataBase.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();//optional, but recommended
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("patient");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String patientID = eElement.getAttribute("id");
                    String patientLastName = eElement.getElementsByTagName("lastName").item(0).getTextContent();
                    String patientFirstName = eElement.getElementsByTagName("firstName").item(0).getTextContent();
                    String patientSurgery = eElement.getElementsByTagName("surgery").item(0).getTextContent();
                    String patientPathology = eElement.getElementsByTagName("pathology").item(0).getTextContent();
                    String surgeryAvg = ((Element) eElement.getElementsByTagName("surgery").item(0)).getAttribute("avg");
                    String surgeryStd = ((Element) eElement.getElementsByTagName("surgery").item(0)).getAttribute("std");
                    String doctorID = eElement.getElementsByTagName("doctorID").item(0).getTextContent();
                    String coordinatorID = eElement.getElementsByTagName("coordinatorID").item(0).getTextContent();

                    MedicIdentifiers doctorInCharge = medicInst.getDoctorByID(Integer.parseInt(doctorID));
                    MedicIdentifiers doctorLeader = medicInst.getDoctorByID(Integer.parseInt(coordinatorID));

                    //check ID and do not add the patient with a invalid ID
                    //also, show an error message with the pacient id
                    if (doctorInCharge != null && doctorLeader != null) {
                        doctorLeader = medicInst.getDoctorByID(doctorInCharge.coordinatorID);
                    } else if (doctorInCharge == null && doctorLeader != null) {
                        doctorInCharge = doctorLeader;
                        doctorLeader = medicInst.getDoctorByID(doctorInCharge.coordinatorID);
                    } else if (doctorInCharge != null && doctorLeader == null) {
                        doctorLeader = medicInst.getDoctorByID(doctorInCharge.coordinatorID);
                    } else {
                        //doctorInCharge == null AND doctorLeader == null
                        // => this patient cannot be added
                        //TODO: what to do with this patient?????
                        JOptionPane.showMessageDialog(null, "Doctor in charge and his coordinator are not in the database."
                                + " This patient cannot be added in the data base. Please add it manually to another doctor."
                                + "\n\nPatient ID: " + patientID + "\nPatient name: " + patientLastName + " " + patientFirstName,
                                "Error. Cannot add patient",
                                JOptionPane.ERROR_MESSAGE);

                        continue;
                    }

                    Element patientSexElement = (Element) eElement.getElementsByTagName("sex").item(0);
                    Element patientRemarksElement = (Element) eElement.getElementsByTagName("remarks").item(0);
                    Element patientBirthDateElement = (Element) eElement.getElementsByTagName("birthDate").item(0);
                    Element patientAdmissionDateElement = (Element) eElement.getElementsByTagName("admissionDate").item(0);
                    Utils.PatientGender patientSex = Utils.PatientGender.OTHER;
                    Date patientBirthDate = null;
                    Date patientAdmissionDate = new Date();
                    String patientRemarks = "";

                    if (patientSexElement != null) {
                        try {
                            patientSex = Utils.PatientGender.valueOf(patientSexElement.getTextContent());
                        } catch (IllegalArgumentException e) {
                            patientSex = Utils.PatientGender.OTHER;
                        }
                    }
                    if (patientRemarksElement != null) {
                        patientRemarks = patientRemarksElement.getTextContent();
                    }
                    if (patientBirthDateElement != null) {
                        String dayString = patientBirthDateElement.getAttribute("day");
                        String monthString = patientBirthDateElement.getAttribute("month");
                        String yearString = patientBirthDateElement.getAttribute("year");

                        try {
                            int day = Integer.parseInt(dayString);
                            int month = Integer.parseInt(monthString);
                            int year = Integer.parseInt(yearString);

                            Calendar c = Calendar.getInstance();
                            c.set(year, month - 1, day);
                            patientBirthDate = c.getTime();

                        } catch (NumberFormatException e) {
                            patientBirthDate = null;
                        }
                    }
                    if (patientAdmissionDateElement != null) {
                        String dayString = patientAdmissionDateElement.getAttribute("day");
                        String monthString = patientAdmissionDateElement.getAttribute("month");
                        String yearString = patientAdmissionDateElement.getAttribute("year");

                        try {
                            int day = Integer.parseInt(dayString);
                            int month = Integer.parseInt(monthString);
                            int year = Integer.parseInt(yearString);

                            Calendar c = Calendar.getInstance();
                            c.set(year, month - 1, day);
                            patientAdmissionDate = c.getTime();

                        } catch (NumberFormatException e) {
                            patientAdmissionDate = new Date();
                        }
                    }

                    addNewSurgery(Integer.parseInt(patientID), 
                            patientSurgery, patientPathology, doctorInCharge, doctorLeader, patientAdmissionDate);
                    new SurgeriesList().addNewSurgery(patientSurgery, patientPathology, surgeryAvg, surgeryStd);

                    Element scheduledElement = (Element) eElement.getElementsByTagName("scheduled").item(0);
                    if (scheduledElement != null) {
                        String scheduledString = scheduledElement.getTextContent();
                        if (scheduledString.equals("true")) {
                            Element scheduledDateElement = (Element) eElement.getElementsByTagName("scheduledDate").item(0);
                            Element scheduledCompletedElement = (Element) eElement.getElementsByTagName("completed").item(0);

                            String scheduledDayString = scheduledDateElement.getAttribute("day");
                            String scheduledMonthString = scheduledDateElement.getAttribute("month");
                            String scheduledYearString = scheduledDateElement.getAttribute("year");
                            Date scheduledDate = null;
                            try {
                                int day = Integer.parseInt(scheduledDayString);
                                int month = Integer.parseInt(scheduledMonthString);
                                int year = Integer.parseInt(scheduledYearString);

                                Calendar c = Calendar.getInstance();
                                c.set(year, month - 1, day);
                                scheduledDate = c.getTime();

                            } catch (NumberFormatException e) {
                                scheduledDate = null;
                            }
                            String scheduledCompleted = scheduledCompletedElement.getTextContent();

                            updateSurgeryDetailsForPatient(Integer.parseInt(patientID), patientSurgery, patientPathology,
                                    doctorInCharge, doctorLeader, true, scheduledDate);
//                                    , scheduledCompleted.equals("true"));

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Private instance of the <code>MedicalTeams</code> class.
     */
    private static MedicalTeams medicInst;

}
