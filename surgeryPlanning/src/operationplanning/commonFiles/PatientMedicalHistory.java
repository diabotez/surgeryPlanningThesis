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
import java.util.Vector;

/**
 * This class contains the medical history of a certain patient. It is a field
 * in <code>PatientIdentifiers</code> class.
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class PatientMedicalHistory {

    /**
     * The surgery name. It is also used to find out the average duration time.
     */
    public Vector<String> surgery;
    
    /**
     * The pathology for which he is operated
     */
    public Vector<String> pathology;
    
    /**
     * The doctor in charge for the operation
     */
    public Vector<MedicIdentifiers> doctorInCharge;
    
    /**
     * The identifier of the medical leader of the team
     */
    public Vector<MedicIdentifiers> doctorTeamLeader;

    /**
     * The date of establishing the need of the surgery and adding the patient
     * in the waiting list for this surgery
     */
    public Vector<Date> admissionDate;
    
    /**
     * If the surgery has been scheduled or not
     */
    public Vector<Boolean> scheduled;
    
    /**
     * If the surgery has been done or not
     */
    public Vector<Boolean> complited;
    
    /**
     * The date in which the surgery had been done
     */
    public Vector<Date> scheduledDate;

    public PatientMedicalHistory() {
        this.surgery = new Vector<>();
        this.pathology = new Vector<>();
        this.doctorInCharge = new Vector<>();
        this.doctorTeamLeader = new Vector<>();
        this.admissionDate = new Vector<>();
        this.scheduled = new Vector<>();
        this.complited = new Vector<>();
        this.scheduledDate = new Vector<>();
    }

    public PatientMedicalHistory(String surgery, String pathology,
            MedicIdentifiers doctorInCharge, MedicIdentifiers doctorTeamLeader, Date admission) {
        this.surgery = new Vector<>();
        this.pathology = new Vector<>();
        this.doctorInCharge = new Vector<>();
        this.doctorTeamLeader = new Vector<>();
        this.admissionDate = new Vector<>();
        this.scheduled = new Vector<>();
        this.complited = new Vector<>();
        this.scheduledDate = new Vector<>();

        this.surgery.add(surgery);
        this.pathology.add(pathology);
        this.doctorInCharge.add(doctorInCharge);
        this.doctorTeamLeader.add(doctorTeamLeader);
        this.admissionDate.add(admission);

        this.scheduled.add(Boolean.FALSE);
        this.complited.add(Boolean.FALSE);
        this.scheduledDate.add(null);
    }
}
