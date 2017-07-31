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

import java.util.Random;
import java.util.Vector;

/**
 * @abstract This class is the interface for managing the medical teams in
 * database.
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class MedicalTeams {

    private static DatabaseQueries db;

    public boolean changeMedicalTeamName(String oldName, String newName) {
        String teamId = new DatabaseQueries().getTeamIdByTeamName(oldName);
        return setMedicalTeamName(Integer.parseInt(teamId.replace("TM", "")), newName);
    }

    public boolean setMedicalTeamName(int teamId, String newName) {
        return new DatabaseQueries().setTeamName("TM" + teamId, newName);
    }

    public MedicIdentifiers getCoordinator(int teamId) {
        Vector<String> coordinatorDetails = new DatabaseQueries().
                getTeamLeaderDetailsByTeamId("TM" + teamId);
        if (coordinatorDetails == null) {
            return null;
        }

        MedicIdentifiers coordinator = new MedicIdentifiers();
        coordinator.IDnumber = Integer.parseInt(coordinatorDetails.get(0).replace("D", ""));
        coordinator.lastName = coordinatorDetails.get(1);
        coordinator.firstName = coordinatorDetails.get(2);
        coordinator.coordinatorID = Integer.parseInt(coordinatorDetails.get(3).replace("D", ""));
        coordinator.department = coordinatorDetails.get(4);

        return coordinator;
    }

    /**
     * Return the name of the team for the specified coordinator.
     *
     * @param coordinatorID the ID of the team's coordinator
     *
     * @return the team name or null if the given ID is not for a coordinator
     */
    public String getTeamNameByTeamLeaderId(int coordinatorID) {
        return new DatabaseQueries().getTeamNameByTeamLeaderId("D" + coordinatorID);
    }

    /**
     * Gets the medical team of the given coordinator, without the coordinator
     * in the resulting vector.
     *
     * @param teamLeaderId
     * @return
     */
    public Vector<MedicIdentifiers> getMedicalTeamByTeamId(int teamId) {
        Vector<Vector<String>> teamMemberDetails = new DatabaseQueries().getTeamMembersDetails("TM" + teamId);
        if (teamMemberDetails == null) {
            return new Vector<>();
        }
        Vector<MedicIdentifiers> team = new Vector<>();

        for (Vector<String> memberDetails : teamMemberDetails) {
            MedicIdentifiers doctor = new MedicIdentifiers();
            doctor.IDnumber = Integer.parseInt(memberDetails.get(0).replace("D", ""));
            doctor.lastName = memberDetails.get(1);
            doctor.firstName = memberDetails.get(2);
            doctor.coordinatorID = Integer.parseInt(memberDetails.get(3).replace("D", ""));
            doctor.department = memberDetails.get(4);

            team.add(doctor);
        }
        return team;
    }

    public Vector<Vector<String>> getAllTeamDetails() {//int getTotalNumberOfTeams() {
        return new DatabaseQueries().getAllTeamsDetails();
    }

    public boolean changeTeamCoordinator(MedicIdentifiers newCoordinator, MedicIdentifiers oldCoordinator) {
        DatabaseQueries db = new DatabaseQueries();

        if (checkCoordinator(oldCoordinator)
                && checkDoctor(newCoordinator) && !checkCoordinator(newCoordinator)) {
            //the oldCoordinator is a coordinator and the newCoordinator is just a doctor, not a coordinator
            boolean ans = db.changeTeamLeader("D" + oldCoordinator.IDnumber, "D" + newCoordinator.IDnumber);
            if (ans) {
                new PatientsList().updateMedicLeaderForPatients(newCoordinator, newCoordinator);
                new PatientsList().updateMedicLeaderForPatients(newCoordinator, oldCoordinator);
                return true;
            }
        }
        return false;
    }

    public boolean moveMemberToOtherTeam(MedicIdentifiers newCoordinator, MedicIdentifiers member) {
        DatabaseQueries db = new DatabaseQueries();

        if (newCoordinator == null || member == null) {
            return false;
        }

        if (!checkDoctor(member)) {
            return false;
        }
        boolean isCoordinator = checkCoordinator(member);
        if (isCoordinator && db.getNumberOfTeamMembers("D" + member.coordinatorID) > 1) {
            return false;
        }

        //doctor can be moved
        boolean ans = db.moveDoctorIntoOtherTeam("D" + member.IDnumber, "D" + newCoordinator.IDnumber);
        if (ans) {
            if (isCoordinator) {
                //remove teamLeaderId for team
                db.removeTeamLeaderId("D" + member.IDnumber);
            }

            //reset the data din patients <= has to be changed after adding the patients
            new PatientsList().updateMedicLeaderForPatients(newCoordinator, member);
            return true;
        }
        return false;
    }

    /**
     * Returns the index of the team with this coordinator in the TeamLeader
     * vector.
     *
     * @param coordinator the doctor to be searched in the Data Base
     * @return the index or -1 if not found
     */
    public int getTeamIndexWithThisCoordinator(MedicIdentifiers coordinator) {
        DatabaseQueries db = new DatabaseQueries();
        String teamId = db.getTeamIdByTeamLeaderId("D" + coordinator.IDnumber);
        if (teamId == null) {
            return -1;
        }
        return Integer.parseInt(teamId.replace("TM", ""));
    }

    public Vector<MedicIdentifiers> getAllTeamLeaders() {
        Vector<Vector<String>> coordinators = db.getAllTeamLeaders();
        if (coordinators == null) {
            return null;
        }

        Vector<MedicIdentifiers> leaders = new Vector<>();
        for (Vector<String> coordinator : coordinators) {
            MedicIdentifiers leader = new MedicIdentifiers();
            leader.IDnumber = Integer.parseInt(coordinator.get(0).replace("D", ""));
            leader.lastName = coordinator.get(1);
            leader.firstName = coordinator.get(2);
            leader.coordinatorID = Integer.parseInt(coordinator.get(3).replace("D", ""));
            leader.department = coordinator.get(4);

            leaders.add(leader);
        }

        return leaders;
    }

    /**
     * Checks if there is any doctor in the database with the same ID number and
     * it is not a coordinator.
     *
     * @param member
     * @return
     */
    public boolean existsTeamWithThisMember(MedicIdentifiers member) {
        if (!checkDoctor(member)) {
            return false;
        }
        if (checkCoordinator(member)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the instance of the doctor with the specified name.
     *
     * @param fullName the full name of the doctor
     *
     * @return null if the last name was not found as a doctor or TeamLeader or
     * the MedicIdentifiers for the specified name
     */
    public MedicIdentifiers getDoctorByFullName(String fullName) {
        MedicIdentifiers doctor = new MedicIdentifiers();
        String name = fullName.replace("Team ", "");
        name = name.replace("Equipo ", "");
        Vector<String> doctorDetails = db.getDoctorDetailsByFullName(name);
        if (doctorDetails == null) {
            return null;
        }

        doctor.IDnumber = Integer.parseInt(doctorDetails.get(0).replace("D", ""));
        doctor.lastName = doctorDetails.get(1);
        doctor.firstName = doctorDetails.get(2);
        doctor.coordinatorID = Integer.parseInt(doctorDetails.get(3).replace("D", ""));
        doctor.department = doctorDetails.get(4);

        return doctor;
    }

    /**
     * Returns the instance of the doctor with the specified id.
     *
     * @param id
     * @return null if the last name was not found as a doctor or TeamLeader or
     * the MedicIdentifiers for the specified name
     */
    public MedicIdentifiers getDoctorByID(int id) {
        MedicIdentifiers doctor = new MedicIdentifiers();
        Vector<String> doctorDetails = db.getDoctorDetailsById("D" + id);
        if (doctorDetails == null) {
            return null;
        }
        doctor.IDnumber = id;
        doctor.lastName = doctorDetails.get(1);
        doctor.firstName = doctorDetails.get(2);
        doctor.coordinatorID = Integer.parseInt(doctorDetails.get(3).replace("D", ""));
        doctor.department = doctorDetails.get(4);

        return doctor;
    }

    /**
     * Removes a certain medical team from the dataBase only if it's members
     * were removed before.
     *
     * @param teamIndex the index of the team to be removed
     *
     * @return true if the team has been successfully removed and false
     * otherwise
     */
    public boolean removeEmptyMedicalTeamFromDataBase(int teamIndex) {
        return new DatabaseQueries().removeEmptyTeam("TM" + teamIndex);
    }

    /**
     * Removes a certain medic member from the dataBase. If this medic has any
     * patients, his patients will be moved to his coordinator.
     *
     * Check <code>updateDoctorInChargeIDForPatients</code> from
     * <class>PatientsList</class>.
     *
     * @param doctor the medic to be removed
     *
     * @return true if the medic was deleted successfully and false if medic not
     * found
     */
    public boolean removeMedicFromDatabase(MedicIdentifiers doctor) {
        if (!checkDoctor(doctor)) {
            return false;
        }
        boolean isCoordinator = checkCoordinator(doctor);
        if (isCoordinator) {
            return false;
        }

        //delete doctor
        if (db.deleteDoctor("D" + doctor.IDnumber)) {
            new PatientsList().updateDoctorInChargeIDForPatients(doctor.IDnumber, 
                    doctor.coordinatorID);
            return true;
        }
        return false;
    }

    /**
     * This method creates a new team with the given coordinator.
     *
     * @param coordinator the coordinator to be added to a new team
     *
     * @return false if the coordinator already exists in the database and true
     * if the adding was successful
     */
    public boolean addNewMedicalTeam(MedicIdentifiers coordinator, String teamName) {
        if (checkDoctor(coordinator)) {
            return false;
        } else if (checkTeamName(teamName)) {
            return false;
        }

        String teamId = db.getTeamId(teamName, "D" + coordinator.IDnumber);
        String departmentId = db.getDetaprtmentId(coordinator.department);

        if (teamId != null && departmentId != null) {
            return db.addNewDoctor(coordinator, teamId, departmentId);
        }
        return false;
    }

    /**
     * Adds a new member to an existing team.
     *
     * @param coordinator the team leader of the team
     * @param member the new member in the team
     *
     * @return true if the member was added successfully and false if the
     * coordinator was not found or at least one of them is null
     */
    public boolean addNewMedicToMedicalTeam(MedicIdentifiers coordinator, MedicIdentifiers member) {
        if (coordinator == null || member == null) {
            return false;
        }

        if (checkDoctor(member)) {
            return false;
        }
        if (!checkCoordinator(coordinator)) {
            return false;
        }

        String teamId = db.getCoordinatorTeamId("D" + coordinator.IDnumber);
        String departmentId = db.getDetaprtmentId(member.department);
        if (teamId != null && departmentId != null) {
            return db.addNewDoctor(member, teamId, departmentId);
        }
        return false;
    }

    /**
     * Adds a new member to an existing team.
     *
     * @param coordinatorID the id of the team's coordinator
     * @param member the new member in the team
     *
     * @return true if the member was added successfully and false if the
     * coordinator was not found or at least one of them is null
     */
    public boolean addNewMedicToMedicalTeam(int coordinatorID, MedicIdentifiers member) {
        MedicIdentifiers coordinator = getDoctorByID(coordinatorID);
        if (coordinator == null || member == null) {
            return false;
        }
        return addNewMedicToMedicalTeam(coordinator, member);
    }

    /**
     * Checks if the doctor exists in the database.
     *
     * @param doctor the doctor to be checked
     *
     * @return true if the doctor exists in the database and false otherwise
     */
    public boolean checkDoctor(MedicIdentifiers doctor) {
        if (db.checkIfDoctorExists("D" + doctor.IDnumber)) {
            return true;
        }
        return false;
    }

    public String generateDoctorId() {
        String doctorId = null;
        do {
            doctorId = "D" + (new Random().nextInt(100) + 1);//1-1000
        } while (db.checkIfDoctorExists(doctorId));

        return doctorId;
    }

    public boolean checkTeamName(String name) {
        if (db.checkIfTeamExists(name)) {
            return true;
        }
        return false;
    }

    public boolean checkCoordinator(MedicIdentifiers doctor) {
        if (db.checkIfDoctorIsTeamLead("D" + doctor.IDnumber)) {
            return true;
        }
        return false;
    }

    /**
     * Constructor.
     */
    public MedicalTeams() {
        db = new DatabaseQueries();
    }
}
