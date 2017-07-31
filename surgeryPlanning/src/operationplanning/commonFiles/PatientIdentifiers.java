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

import java.util.Date;

/**
 * This class contains the personal information about a patient.
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class PatientIdentifiers {

    /**
     * The Id of the patient
     */
    public int patientID;

    /**
     * The last name of the patient
     */
    public String lastName;

    /**
     * The first name of the patient
     */
    public String firstName;

    /**
     * The sex of the patient
     */
    public Utils.PatientGender gender;

    /**
     * The birth date of the patient
     */
    public Date birthDate;

    /**
     * Observations about the patient
     */
    public String observations;

    /**
     * The medical history of the patient
     */
    public PatientMedicalHistory medicalHistory;

    /**
     * Creates a new patient with the given informations.
     *
     * @param ID the ID of the patient
     * @param lastName the last name of the patient
     * @param firstName the first name of the patient
     * @param patientSex the sex of the patient
     * @param birthDate the birth date of the patient
     * @param obs observations about the patient
     */
    public PatientIdentifiers(int ID, String lastName, String firstName, Utils.PatientGender patientSex, Date birthDate, String obs) {
        this.patientID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = patientSex;
        this.birthDate = birthDate;
        this.observations = obs;
        medicalHistory = new PatientMedicalHistory();
    }

    /**
     * Creates a new patient with the given informations.
     *
     * @param ID the ID of the patient
     * @param lastName the last name of the patient
     * @param firstName the first name of the patient
     * @param patientSex the sex of the patient
     * @param birthDate the birth date of the patient
     * @param obs observations about the patient
     * @param surgery the name of the surgery that the patient needs to have
     * @param pathology the name of the pathology the patient has
     * @param doctorInCharge the <code>MedicIdentifiers</code> for the doctor in
     * charge of the surgery
     * @param doctorTeamLeader the <code>MedicIdentifiers</code> for the
     * coordinator of the doctor in charge
     * @param admission the date of admission (it should be the current date for
     * new surgeries)
     */
    public PatientIdentifiers(int ID, String lastName, String firstName, Utils.PatientGender patientSex, Date birthDate, String obs,
            String surgery, String pathology, MedicIdentifiers doctorInCharge, MedicIdentifiers doctorTeamLeader, Date admission) {
        this.patientID = ID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = patientSex;
        this.birthDate = birthDate;
        this.observations = obs;
        medicalHistory = new PatientMedicalHistory(surgery, pathology, doctorInCharge, doctorTeamLeader, admission);
    }
}
