/*
 * Copyright (C) 2017 diana - All Rights Reserved
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

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author diana
 */
public class DatabaseQueries {

    private static DatabaseConnection dbInstance = DatabaseConnection.getInstance();
    private Connection dbConnection;
    private Statement stmt = null;
    private ResultSet rs = null;

    public DatabaseQueries() {
        try {
            dbConnection = dbInstance.getDbConnection();
            stmt = dbConnection.createStatement();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

// <editor-fold defaultstate="collapsed" desc="user data manipulation">
    /**
     * Checks if a user exists in the database.
     *
     * @param user the username to check
     *
     * @return true if the user exists in the database and false otherwise
     */
    public boolean checkIfUserNameExists(String user) {
        String query;
        try {
            /**
             * Check if there is any other user with this username.
             */
            query = "SELECT * FROM users WHERE userName='" + user + "'";
            rs = stmt.executeQuery(query);
            String userName = null;
            while (rs.next()) {
                userName = rs.getString("userName");
            }
            if (userName != null) {
                //there is another user with this username
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    public boolean addNewUser(String user, String encryptedPass, Utils.UserType type) {
        String query;
        if (checkIfUserNameExists(user)) {
            //user already exists. It cannot be added again
            return false;
        }

        try {
            query = "INSERT INTO users (userName, hashedPassword, userType) VALUES "
                    + "('" + user + "', '" + encryptedPass + "', '" + type.name() + "')";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateUserType(String user, Utils.UserType newType) {
        String query;

        try {
            query = "UPDATE users SET userType='" + newType.name() + "' "
                    + "WHERE userName = '" + user + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateUserPassword(String user, String encryptedPass) {
        String query;

        try {
            query = "UPDATE users SET hashedPassword='" + encryptedPass + "' "
                    + "WHERE userName = '" + user + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteUser(String user) {
        String query;
        if (!checkIfUserNameExists(user)) {
            return false;
        }

        try {
            query = "DELETE FROM users WHERE userName='" + user + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public Utils.UserType getUserTypeForCredentials(String user, String encryptedPassword) {
        String query;
        if (user == null || encryptedPassword == null) {
            return Utils.UserType.UNKNOWN_TYPE;
        }

        try {
            query = "SELECT userType FROM users WHERE"
                    + " userName='" + user + "'"
                    + " AND hashedPassword='" + encryptedPassword + "'";
            rs = stmt.executeQuery(query);
            String userType = null;
            while (rs.next()) {
                userType = rs.getString("userType");
            }
            if (userType == null) {
                return Utils.UserType.UNKNOWN_TYPE;
            }
            return Utils.UserType.valueOf(userType);
        } catch (SQLException ex) {
            return Utils.UserType.UNKNOWN_TYPE;
        }
    }

    /**
     * Checks if the given username and encrypted password are in the database
     * for a user.
     *
     * @param user the username to check
     * @param encryptedPassword the encrypted password for the given username
     *
     * @return true if found the given user with the given password or false
     * otherwise
     */
    public boolean checkUserCredentials(String user, String encryptedPassword) {
        String query;

        try {
            query = "SELECT userName FROM users WHERE "
                    + "userName='" + user + "' "
                    + "AND hashedPassword='" + encryptedPassword + "'";
            rs = stmt.executeQuery(query);
            String userName = null;
            while (rs.next()) {
                userName = rs.getString("userName");
            }
            if (userName != null) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }
// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="teams data manipulation">
    /**
     *
     * @param id
     * @return
     */
    public boolean checkIfTeamIdExists(String id) {
        String query;
        try {
            query = "SELECT teamId FROM medicalTeams WHERE teamId='" + id + "'";
            rs = stmt.executeQuery(query);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }
            if (teamId != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean checkIfTeamExists(String name) {
        String query;
        String auxName = name.trim();
        try {
            query = "SELECT teamName FROM medicalTeams WHERE teamName='" + auxName + "'";
            rs = stmt.executeQuery(query);
            String teamName = null;
            while (rs.next()) {
                teamName = rs.getString("teamName");
            }
            if (teamName != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     *
     * @param name
     * @param leaderId
     * @return
     */
    public String getTeamId(String name, String leaderId) {
        String query1, query2;
        try {
            query1 = "SELECT teamId FROM medicalTeams WHERE "
                    + "teamName='" + name + "'";
            rs = stmt.executeQuery(query1);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }
            if (teamId != null) {
                return teamId;
            }
            //it is null so it doesn't exist
            do {
                teamId = "TM" + (new Random().nextInt(100) + 1);//1-1000
            } while (checkIfTeamIdExists(teamId));
            query2 = "INSERT INTO medicalTeams (teamId, teamName, teamLeaderId) VALUES "
                    + "('" + teamId + "', '" + name + "', '" + leaderId + "') ";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return null;
            }

            return teamId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String getTeamNameByTeamLeaderId(String leaderId) {
        String query;
        try {
            query = "SELECT teamName FROM medicalTeams WHERE "
                    + "teamLeaderId='" + leaderId + "'";
            rs = stmt.executeQuery(query);
            String teamName = null;
            while (rs.next()) {
                teamName = rs.getString("teamName");
            }

            return teamName;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public Vector<String> getAllTeamNames() {
        String query;
        Vector<String> teamNames = new Vector<>();

        try {
            query = "SELECT teamName FROM medicalTeams ";
            rs = stmt.executeQuery(query);
            String teamName = null;
            while (rs.next()) {
                teamName = rs.getString("teamName");
                if (teamName != null) {
                    teamNames.add(teamName);
                }
            }
            if (teamNames.isEmpty()) {
                return null;
            }
            return teamNames;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String getTeamIdByTeamLeaderId(String leaderId) {
        String query;
        try {
            query = "SELECT teamId FROM medicalTeams WHERE "
                    + "teamLeaderId='" + leaderId + "'";
            rs = stmt.executeQuery(query);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }

            return teamId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String getTeamLeaderIdByTeamId(String teamId) {
        String query;
        try {
            query = "SELECT teamLeaderId FROM medicalTeams WHERE "
                    + "teamId='" + teamId + "'";
            rs = stmt.executeQuery(query);
            String teamLeaderId = null;
            while (rs.next()) {
                teamLeaderId = rs.getString("teamLeaderId");
            }

            return teamLeaderId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String getTeamIdByTeamName(String teamName) {
        String query;
        try {
            query = "SELECT teamId FROM medicalTeams WHERE "
                    + "teamName='" + teamName + "'";
            rs = stmt.executeQuery(query);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }

            return teamId;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean setTeamName(String teamId, String newName) {
        String query;
        try {
            query = "UPDATE medicalTeams SET teamName='" + newName + "' "
                    + "WHERE teamId='" + teamId + "'";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean removeEmptyTeam(String teamId) {
        String query;
        try {
            query = "DELETE FROM medicalTeams "
                    + "WHERE teamId='" + teamId + "' "
                    + "AND teamLeaderId='D0'";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean removeTeamLeaderId(String leaderId) {
        String query;
        try {
            query = "UPDATE medicalTeams SET teamLeaderId='D0'"
                    + "WHERE teamLeaderId='" + leaderId + "'";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Gets the number of doctors with the given coordinator id.
     *
     * @param leaderId
     *
     * @return
     */
    public int getNumberOfTeamMembers(String leaderId) {
        String query;
        int members = 0;

        try {
            query = "SELECT coordinatorId FROM doctors WHERE "
                    + "coordinatorId='" + leaderId + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                members++;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return members;
    }

    /**
     * The result vector will contain vectors with the following template:
     * <code>teamId, teamName, teamLeaderId<code/>.
     *
     * @return the result vector if team details found or null otherwise
     */
    public Vector<Vector<String>> getAllTeamsDetails() {
        String query;
        Vector<Vector<String>> teamsDetails = new Vector<>();

        try {
            query = "SELECT * FROM medicalTeams";
            rs = stmt.executeQuery(query);
            String teamId = null;
            String teamName = null;
            String teamLeaderId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
                teamName = rs.getString("teamName");
                teamLeaderId = rs.getString("teamLeaderId");

                if (teamId != null && teamName != null && teamLeaderId != null) {
                    Vector<String> details = new Vector<>();
                    details.add(teamId);
                    details.add(teamName);
                    details.add(teamLeaderId);

                    teamsDetails.add(details);
                }
            }
            if (teamsDetails.isEmpty()) {
                return null;
            }

            return teamsDetails;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="departments data manipulation">
    /**
     *
     * @param id
     * @return
     */
    public boolean checkIfDepartmentIdExists(String id) {
        String query;
        try {
            query = "SELECT * FROM departments WHERE departmentId='" + id + "'";
            rs = stmt.executeQuery(query);
            String teamName = null;
            while (rs.next()) {
                teamName = rs.getString("departmentId");
            }
            if (teamName != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     *
     * @param name
     * @return
     */
    public String getDetaprtmentId(String name) {
        String query1, query2;
        try {
            query1 = "SELECT departmentId FROM departments WHERE "
                    + "departmentName='" + name + "'";
            rs = stmt.executeQuery(query1);
            String departmentId = null;
            while (rs.next()) {
                departmentId = rs.getString("departmentId");
            }
            if (departmentId != null) {
                return departmentId;
            }
            //it is null so it doesn't exist
            do {
                departmentId = "DPT" + (new Random().nextInt(1000) + 1);//1-1000
            } while (checkIfDepartmentIdExists(departmentId));
            query2 = "INSERT INTO departments (departmentId, departmentName) VALUES "
                    + "('" + departmentId + "', '" + name + "') ";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return null;
            }

            return departmentId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="doctors data manipulation">
    /**
     * Checks if a user exists in the database.
     *
     * @param id the doctor ID
     *
     * @return true if the user exists in the database and false otherwise
     */
    public boolean checkIfDoctorExists(String id) {
        String query;
        try {
            query = "SELECT * FROM doctors WHERE doctorId='" + id + "'";
            rs = stmt.executeQuery(query);
            String doctorId = null;
            while (rs.next()) {
                doctorId = rs.getString("doctorId");
            }
            if (doctorId != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean checkIfDoctorIsTeamLead(String id) {
        String query;
        try {
            query = "SELECT * FROM doctors WHERE doctorId='" + id + "'";
            rs = stmt.executeQuery(query);
            String doctorId = null;
            String coordinatorId = null;
            while (rs.next()) {
                doctorId = rs.getString("doctorId");
                coordinatorId = rs.getString("coordinatorId");
            }
            if (doctorId == null || coordinatorId == null) {
                return false;
            }
            if (doctorId.equals(coordinatorId)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     * Gets a vector with the details of all teamLeaders found. Each team leader
     * will have the following details:
     * <code>id, lastName, firstName, coordinatorId, departmentName</code>
     *
     * @return a vector with all team leaders found or null otherwise
     */
    public Vector<Vector<String>> getAllTeamLeaders() {
        String query;
        Vector<Vector<String>> result = new Vector<>();
        try {
            query = "SELECT "
                    + "doctors.doctorId AS doctorId, "
                    + "doctors.lastName AS lastName, "
                    + "doctors.firstName AS firstName, "
                    + "doctors.coordinatorId AS coordinatorId, "
                    + "departments.departmentName AS departmentName "
                    + "FROM doctors, departments "
                    + "WHERE departments.departmentId=doctors.departmentId "
                    + "AND doctors.doctorId=doctors.coordinatorId";

            rs = stmt.executeQuery(query);
            String doctorId = null;
            String lastName = null;
            String firstName = null;
            String coordinatorId = null;
            String departmentName = null;
            while (rs.next()) {
                doctorId = rs.getString("doctorId");
                lastName = rs.getString("lastName");
                firstName = rs.getString("firstName");
                coordinatorId = rs.getString("coordinatorId");
                departmentName = rs.getString("departmentName");

                if (doctorId != null && lastName != null && firstName != null
                        && coordinatorId != null && departmentName != null) {
                    Vector<String> entry = new Vector<>();
                    entry.add(doctorId);
                    entry.add(lastName);
                    entry.add(firstName);
                    entry.add(coordinatorId);
                    entry.add(departmentName);

                    result.add(entry);
                }
            }

            if (result.isEmpty()) {
                return null;
            }

            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets a vector with the details of all team members found. Each member
     * will have the following details:
     * <code>id, lastName, firstName, coordinatorId, departmentName</code>
     *
     * @param teamLeaderId
     * @return
     */
    public Vector<Vector<String>> getTeamMembersDetails(String teamId) {
        String query;
        Vector<Vector<String>> result = new Vector<>();
        try {
            query = "SELECT "
                    + "doctors.doctorId AS doctorId, "
                    + "doctors.lastName AS lastName, "
                    + "doctors.firstName AS firstName, "
                    + "doctors.coordinatorId AS coordinatorId, "
                    + "departments.departmentName AS departmentName "
                    + "FROM doctors, departments "
                    + "WHERE departments.departmentId = doctors.departmentId "
                    + "AND doctors.doctorId != doctors.coordinatorId "
                    + "AND doctors.teamId='" + teamId + "'";

            rs = stmt.executeQuery(query);
            String doctorId = null;
            String lastName = null;
            String firstName = null;
            String coordinatorId = null;
            String departmentName = null;
            while (rs.next()) {
                doctorId = rs.getString("doctorId");
                lastName = rs.getString("lastName");
                firstName = rs.getString("firstName");
                coordinatorId = rs.getString("coordinatorId");
                departmentName = rs.getString("departmentName");

                if (doctorId != null && lastName != null && firstName != null
                        && coordinatorId != null && departmentName != null) {
                    Vector<String> entry = new Vector<>();
                    entry.add(doctorId);
                    entry.add(lastName);
                    entry.add(firstName);
                    entry.add(coordinatorId);
                    entry.add(departmentName);

                    result.add(entry);
                }
            }

            if (result.isEmpty()) {
                return null;
            }

            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean moveDoctorIntoOtherTeam(String doctorId, String newTeamLeaderId) {
        String query1, query2;
        try {
            query1 = "SELECT teamId FROM doctors WHERE "
                    + "doctorId='" + newTeamLeaderId + "'";
            rs = stmt.executeQuery(query1);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }
            if (teamId == null) {
                return false;
            }

            query2 = "UPDATE doctors "
                    + "SET "
                    + "doctors.coordinatorId = '" + newTeamLeaderId + "',"
                    + "doctors.teamId = '" + teamId + "' "
                    + "WHERE "
                    + "doctors.doctorId = '" + doctorId + "'";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not moved succeccfully
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean changeTeamLeader(String oldTeamLeaderId, String newTeamLeaderId) {
        String query1, query2;
        try {
            query1 = "UPDATE doctors SET "
                    + "doctors.coordinatorId = '" + newTeamLeaderId + "' WHERE "
                    + "doctors.coordinatorId = '" + oldTeamLeaderId + "'";
            int rowsEffected = stmt.executeUpdate(query1);
            if (rowsEffected == 0) {
                // not updated succeccfully
                return false;
            }

            query2 = "UPDATE medicalTeams "
                    + "SET "
                    + "medicalTeams.teamLeaderId = '" + newTeamLeaderId + "' "
                    + "WHERE "
                    + "medicalTeams.teamLeaderId = '" + oldTeamLeaderId + "'";

            rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not updated succeccfully
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Gets the details of the doctor with the specified id in the following
     * order:
     * <code>id, lastName, firstName, coordinatorId, departmentName</code>
     *
     * @param id the id of the doctor to search
     *
     * @return a vector with the details of the doctor if the id was found or
     * null otherwise
     */
    public Vector<String> getDoctorDetailsById(String id) {
        if (!checkIfDoctorExists(id)) {
            return null;
        }
        String query;
        try {
            query = "SELECT "
                    + "doctors.lastName AS lastName, "
                    + "doctors.firstName AS firstName, "
                    + "doctors.coordinatorId AS coordinatorId, "
                    + "departments.departmentName AS departmentName "
                    + "FROM doctors, departments "
                    + "WHERE departments.departmentId=doctors.departmentId "
                    + "AND doctors.doctorId='" + id + "'";

            rs = stmt.executeQuery(query);
            Vector<String> result = new Vector<>();
            String lastName = null;
            String firstName = null;
            String coordinatorId = null;
            String departmentName = null;
            while (rs.next()) {
                lastName = rs.getString("lastName");
                firstName = rs.getString("firstName");
                coordinatorId = rs.getString("coordinatorId");
                departmentName = rs.getString("departmentName");
            }

            if (lastName == null || firstName == null
                    || coordinatorId == null || departmentName == null) {
                return null;
            }
            result.add(id);
            result.add(lastName);
            result.add(firstName);
            result.add(coordinatorId);
            result.add(departmentName);

            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets the details of the doctor with the specified id in the following
     * order:
     * <code>id, lastName, firstName, coordinatorId, departmentName</code>
     *
     * @param fullName the full name of the doctor to search
     *
     * @return a vector with the details of the doctor if the id was found or
     * null otherwise
     */
    public Vector<String> getDoctorDetailsByFullName(String fullName) {
        String query;
        try {
            query = "SELECT "
                    + "doctors.doctorId	AS doctorId, "
                    + "doctors.firstName AS firstName, "
                    + "doctors.lastName	AS lastName, "
                    + "doctors.coordinatorId AS coordinatorId, "
                    + "departments.departmentName AS departmentName "
                    + "FROM doctors, departments "
                    + "WHERE doctors.departmentId = departments.departmentId AND "
                    + "CONCAT(doctors.lastName,' ', doctors.firstName) ='" + fullName + "'";

            rs = stmt.executeQuery(query);
            Vector<String> result = new Vector<>();
            String doctorId = null;
            String lastName = null;
            String firstName = null;
            String coordinatorId = null;
            String departmentName = null;
            while (rs.next()) {
                doctorId = rs.getString("doctorId");
                lastName = rs.getString("lastName");
                firstName = rs.getString("firstName");
                coordinatorId = rs.getString("coordinatorId");
                departmentName = rs.getString("departmentName");
            }

            if (doctorId == null || lastName == null || firstName == null
                    || coordinatorId == null || departmentName == null) {
                return null;
            }
            result.add(doctorId);
            result.add(lastName);
            result.add(firstName);
            result.add(coordinatorId);
            result.add(departmentName);

            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets the details of the doctor with the specified id in the following
     * order:
     * <code>id, lastName, firstName, coordinatorId, departmentName</code>
     *
     * @param id the id of the medical team
     *
     * @return a vector with the details of the doctor if the id was found or
     * null otherwise
     */
    public Vector<String> getTeamLeaderDetailsByTeamId(String id) {
        String query;
        try {
            query = "SELECT "
                    + "doctors.doctorId AS doctorId, "
                    + "doctors.lastName AS lastName, "
                    + "doctors.firstName AS firstName, "
                    + "doctors.coordinatorId AS coordinatorId, "
                    + "departments.departmentName AS departmentName "
                    + "FROM doctors, departments "
                    + "WHERE departments.departmentId = doctors.departmentId "
                    + "AND doctors.doctorId = doctors.coordinatorId "
                    + "AND doctors.teamId = '" + id + "'";

            rs = stmt.executeQuery(query);
            Vector<String> result = new Vector<>();
            String doctorId = null;
            String lastName = null;
            String firstName = null;
            String coordinatorId = null;
            String departmentName = null;
            while (rs.next()) {
                doctorId = rs.getString("doctorId");
                lastName = rs.getString("lastName");
                firstName = rs.getString("firstName");
                coordinatorId = rs.getString("coordinatorId");
                departmentName = rs.getString("departmentName");
            }

            if (doctorId == null || lastName == null || firstName == null
                    || coordinatorId == null || departmentName == null) {
                return null;
            }
            result.add(doctorId);
            result.add(lastName);
            result.add(firstName);
            result.add(coordinatorId);
            result.add(departmentName);

            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param leaderId
     * @return
     */
    public String getCoordinatorTeamId(String leaderId) {
        String query;

        try {
            query = "SELECT teamId FROM medicalTeams WHERE "
                    + "teamLeaderId='" + leaderId + "'";
            rs = stmt.executeQuery(query);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }

            return teamId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param doctor
     * @param teamId
     * @param departmentId
     * @return
     */
    public boolean addNewDoctor(MedicIdentifiers doctor, String teamId, String departmentId) {
        String query;

        try {
            query = "INSERT INTO doctors (doctorId, firstName, lastName, coordinatorId, "
                    + "departmentId, teamId) VALUES " + "('D" + doctor.IDnumber + "', '" + doctor.firstName + "','"
                    + doctor.lastName + "', 'D" + doctor.coordinatorID + "', '" + departmentId + "', '" + teamId + "')";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean deleteDoctor(String id) {
        String query;

        try {
            query = "DELETE FROM doctors WHERE doctorId='" + id + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not deleted succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="surgeries data manipulation">
    /**
     *
     * @param tableName
     * @param id
     * @return
     */
    public boolean checkIfSurgeryIdExists(String tableName, String id) {
        String query;
        try {
            query = "SELECT * FROM " + tableName + " WHERE surgeryId='" + id + "'";
            rs = stmt.executeQuery(query);
            String surgeryId = null;
            while (rs.next()) {
                surgeryId = rs.getString("surgeryId");
            }
            if (surgeryId != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     *
     * @param tableName
     * @param id
     * @return
     */
    public boolean checkIfPathologyIdExists(String tableName, String id) {
        String query;
        try {
            query = "SELECT * FROM " + tableName + " WHERE pathologyId='" + id + "'";
            rs = stmt.executeQuery(query);
            String pathologyId = null;
            while (rs.next()) {
                pathologyId = rs.getString("pathologyId");
            }
            if (pathologyId != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     *
     * @param name
     * @return
     */
    public String getSurgeryId(String name) {
        String query1, query2;
        try {
            query1 = "SELECT surgeryId FROM surgeries WHERE "
                    + "surgeryName='" + name + "'";
            rs = stmt.executeQuery(query1);
            String surgeryId = null;
            while (rs.next()) {
                surgeryId = rs.getString("surgeryId");
            }
            if (surgeryId != null) {
                return surgeryId;
            }
            //it is null so it doesn't exist
            do {
                surgeryId = "SRG" + (new Random().nextInt(10000) + 1);//1-1000
            } while (checkIfSurgeryIdExists("surgeries", surgeryId));
            query2 = "INSERT INTO surgeries (surgeryId, surgeryName) VALUES "
                    + "('" + surgeryId + "', '" + name + "') ";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return null;
            }

            return surgeryId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public String getPathologyId(String name) {
        String query1, query2;
        try {
            query1 = "SELECT pathologyId FROM pathologies WHERE "
                    + "pathologyName='" + name + "'";
            rs = stmt.executeQuery(query1);
            String pathologyId = null;
            while (rs.next()) {
                pathologyId = rs.getString("pathologyId");
            }
            if (pathologyId != null) {
                return pathologyId;
            }
            //it is null so it doesn't exist
            do {
                pathologyId = "PTG" + (new Random().nextInt(10000) + 1);//1-1000
            } while (checkIfPathologyIdExists("pathologies", pathologyId));
            query2 = "INSERT INTO pathologies (pathologyId, pathologyName) VALUES "
                    + "('" + pathologyId + "', '" + name + "') ";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return null;
            }

            return pathologyId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public int getSurgeryAvgDuration(String id) {
        String query1;
        try {
            query1 = "SELECT avgDuration FROM surgeryDetails WHERE "
                    + "surgeryId='" + id + "'";
            rs = stmt.executeQuery(query1);
            String avgDuration = null;
            while (rs.next()) {
                avgDuration = rs.getString("avgDuration");
            }
            if (avgDuration != null) {
                return Integer.parseInt(avgDuration);
            }

            return 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public int getSurgeryStdDeviation(String id) {
        String query1;
        try {
            query1 = "SELECT stdDeviation FROM surgeryDetails WHERE "
                    + "surgeryId='" + id + "'";
            rs = stmt.executeQuery(query1);
            String stdDeviation = null;
            while (rs.next()) {
                stdDeviation = rs.getString("stdDeviation");
            }
            if (stdDeviation != null) {
                return Integer.parseInt(stdDeviation);
            }

            return 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * The result vector will contain vectors with the following template:
     * <code>surgeryName, pathologyName, averageDuration, standardDeviation<code/>.
     * The last two elements are also Strings. Conversion to Int might be
     * required.
     *
     * @return the result vector if surgeries details found or null otherwise
     */
    public Vector<Vector<String>> getAllSurgeriesDetails() {
        String query;
        Vector<Vector<String>> results = new Vector<>();
        try {
            query = "SELECT "
                    + "surgeries.surgeryName AS surgeryName, "
                    + "pathologies.pathologyName AS  pathologyName, "
                    + "surgeryDetails.avgDuration AS avgDuration, "
                    + "surgeryDetails.stdDeviation AS stdDeviation "
                    + "FROM surgeries, pathologies, surgeryDetails "
                    + "WHERE surgeries.surgeryId = surgeryDetails.surgeryId AND "
                    + "pathologies.pathologyId = surgeryDetails.pathologyId";
            rs = stmt.executeQuery(query);
            String surgeryName = null;
            String pathologyName = null;
            String avgDuration = null;
            String stdDeviation = null;
            while (rs.next()) {
                surgeryName = rs.getString("surgeryName");
                pathologyName = rs.getString("pathologyName");
                avgDuration = rs.getString("avgDuration");
                stdDeviation = rs.getString("stdDeviation");
                if (surgeryName != null && pathologyName != null
                        && avgDuration != null && stdDeviation != null) {
                    Vector<String> entry = new Vector<>();
                    entry.add(surgeryName);
                    entry.add(pathologyName);
                    entry.add(avgDuration);
                    entry.add(stdDeviation);

                    results.add(entry);
                }
            }

            if (!results.isEmpty()) {
                return results;
            }

            return null;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Add a new surgery to database if the surgery name doesn't exists.
     *
     * @param surgeryName
     * @param pathologyName
     * @param avgD
     * @param stdDev
     * @return
     */
    public boolean addNewSurgery(String surgeryName, String pathologyName, int avgD, int stdDev) {
        String query;
        String surgeryId = getSurgeryId(surgeryName);
        String pathologyId = getPathologyId(pathologyName);

        if (checkIfSurgeryIdExists("surgeryDetails", surgeryId)) {
            //the details for this surgery already exists
            return false;
        }

        try {
            query = "INSERT INTO surgeryDetails (surgeryId, pathologyId, avgDuration, stdDeviation) "
                    + "VALUES ('" + surgeryId + "', '" + pathologyId + "', " + avgD + ", " + stdDev + ")";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param surgeryName
     * @param pathologyName
     * @param avgD
     * @param stdDev
     * @return
     */
    public boolean updateSurgery(String surgeryName, String pathologyName, int avgD, int stdDev) {
        String query;
        String surgeryId = getSurgeryId(surgeryName);

        if (!checkIfSurgeryIdExists("surgeryDetails", surgeryId)) {
            //this surgery does not exist
            return false;
        }

        String pathologyId = getPathologyId(pathologyName);

        try {
            query = "UPDATE surgeryDetails SET pathologyId='" + pathologyId
                    + "', avgDuration=" + avgD + ", stdDeviation=" + stdDev
                    + " WHERE surgeryId='" + surgeryId + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Checks if there are any patients assigned with this surgery that needs to
     * be scheduled or are scheduled after the current date.
     *
     * @param id the given surgery to look for
     *
     * @return true if any patient found with the given surgery after the
     * current date or false otherwise
     */
    public boolean isSurgeryAssignedToPatient(String id) {
        String query;
        try {
            query = "SELECT COUNT(patientId) AS noOfSurgeries \nFROM "
                    + "patientDetails \nWHERE "
                    + "(patientDetails.scheduled = 'N'\n"
                    + "OR (patientDetails.scheduled = 'Y' \n"
                    + "AND patientDetails.scheduledDate >= CURRENT_DATE() ) )\n"
                    + "AND patientDetails.surgeryId='" + id + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int noOfSurgeries = rs.getInt("noOfSurgeries");
                if (noOfSurgeries != 0) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return true;
        }
    }

    public boolean deleteSurgery(String surgeryName) {
        String query;
        String surgeryId = getSurgeryId(surgeryName);

        if (!checkIfSurgeryIdExists("surgeryDetails", surgeryId)) {
            //this surgery does not exist
            return false;
        }
        if (isSurgeryAssignedToPatient(surgeryId)) {
            return false;
        }

        try {
            query = "DELETE FROM surgeryDetails WHERE surgeryId='" + surgeryId + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="operating rooms manipulation">
    /**
     *
     * @param id
     * @return
     */
    public boolean checkIfOrIdExists(String id) {
        String query;
        try {
            query = "SELECT orId FROM operationTheater WHERE orId='" + id + "'";
            rs = stmt.executeQuery(query);
            String teamName = null;
            while (rs.next()) {
                teamName = rs.getString("orId");
            }
            if (teamName != null) {
                //there is another doctor with this id
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    public boolean isOrFree(String id, Date date) {
        String query;
        try {
            query = "SELECT orId FROM operatingRoomDetails WHERE "
                    + "orId='" + id + "' "
                    + "AND scheduleDate='" + date.toString() + "'";
            rs = stmt.executeQuery(query);
            String orId = null;
            while (rs.next()) {
                orId = rs.getString("orId");
            }
            if (orId != null) {
                return false;
            }
            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean isTeamAvailable(String id, Date date) {
        String query;
        try {
            query = "SELECT teamId FROM operatingRoomDetails WHERE "
                    + "teamId='" + id + "' "
                    + "AND scheduleDate='" + date.toString() + "'";
            rs = stmt.executeQuery(query);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }
            if (teamId != null) {
                return false;
            }
            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Gets a vector with all the dates in which the room is scheduled.
     *
     * @param id the room id to search the dates
     *
     * @return a vector with available dates in string format if any, or null
     * otherwise
     */
    public Vector<String> getScheduledDatesForOr(String id) {
        String query;
        Vector<String> dates = new Vector<>();

        try {
            query = "SELECT scheduleDate FROM operatingRoomDetails "
                    + "WHERE operatingRoomDetails.orId='" + id + "' "
                    + "AND operatingRoomDetails.scheduleDate >= CURRENT_DATE() "
                    + "ORDER BY operatingRoomDetails.scheduleDate";
            rs = stmt.executeQuery(query);
            Date date = null;
            while (rs.next()) {
                date = rs.getDate("scheduleDate");
                if (date != null) {
                    dates.add(date.toString());
                }
            }
            if (dates.isEmpty()) {
                return null;
            }
            return dates;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets a vector with all the dates in which the room is scheduled.
     *
     * @param teamId the team id to search the dates
     *
     * @return a vector with available dates in string format if any, or null
     * otherwise
     */
    public Vector<String> getScheduledDatesForTeam(String teamId) {
        String query;
        Vector<String> dates = new Vector<>();

        try {
            query = "SELECT scheduleDate FROM operatingRoomDetails "
                    + "WHERE operatingRoomDetails.teamId='" + teamId + "' "
                    + "AND operatingRoomDetails.scheduleDate >= CURRENT_DATE() "
                    + "ORDER BY operatingRoomDetails.scheduleDate";
            rs = stmt.executeQuery(query);
            Date date = null;
            while (rs.next()) {
                date = rs.getDate("scheduleDate");
                if (date != null) {
                    dates.add(date.toString());
                }
            }
            if (dates.isEmpty()) {
                return null;
            }
            return dates;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets the number of days in which the team is scheduled into a operating
     * room, but WITHOUT the days in which the team has already scheduled
     * patients
     *
     * @param id
     * @return
     */
    public int getNumberOfAvailableDaysAssigned(String id) {
        String query1, query2;
        int assignedDatesCount = 0;
        int scheduledDatesCount = 0;
        try {
            query1 = "SELECT COUNT(teamId) AS assignedDatesCount\n"
                    + "FROM operatingRoomDetails WHERE\n"
                    + "operatingRoomDetails.scheduleDate >= CURRENT_DATE()\n"
                    + "AND teamId='" + id + "'";
            rs = stmt.executeQuery(query1);
            while (rs.next()) {
                assignedDatesCount = rs.getInt("assignedDatesCount");
            }

            query2 = "SELECT \n"
                    + "COUNT(DISTINCT patientDetails.scheduledDate) AS scheduledDatesCount\n"
                    + "FROM patientDetails, medicalTeams \n"
                    + "WHERE\n"
                    + "patientDetails.scheduledDate >= CURRENT_DATE()\n"
                    + "AND patientDetails.teamLeaderId = medicalTeams.teamLeaderId\n"
                    + "AND teamId='" + id + "'";
            rs = stmt.executeQuery(query2);
            while (rs.next()) {
                scheduledDatesCount = rs.getInt("scheduledDatesCount");
            }

            return assignedDatesCount - scheduledDatesCount;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * Gets the teamId of the team that had the specified OR scheduled in the
     * given date
     *
     * @param roomId the room id to look for
     * @param date the scheduled date
     *
     * @return the team id if found or null otherwise
     */
    public String getTeamIdForScheduledRoom(String roomId, String date) {
        String query1;
        try {
            query1 = "SELECT teamId FROM operatingRoomDetails WHERE "
                    + "orId='" + roomId + "' "
                    + "AND scheduleDate='" + date + "'";
            rs = stmt.executeQuery(query1);
            String teamId = null;
            while (rs.next()) {
                teamId = rs.getString("teamId");
            }
            return teamId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets the teamId of the team that had the specified OR scheduled in the
     * given date
     *
     * @param teamId the room id to look for
     * @param date the scheduled date
     *
     * @return the team id if found or null otherwise
     */
    public String getRoomNameForScheduledTeam(String teamId, String date) {
        String query1;
        try {
            query1 = "SELECT orName FROM operatingRoomDetails, "
                    + "operationTheater WHERE "
                    + "teamId='" + teamId + "' "
                    + "AND scheduleDate='" + date + "' \n"
                    + "AND operatingRoomDetails.orId = operationTheater.orId";
            rs = stmt.executeQuery(query1);
            String roomName = null;
            while (rs.next()) {
                roomName = rs.getString("orName");
            }
            return roomName;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String getOrIdByName(String name) {
        String query1;
        try {
            query1 = "SELECT orId FROM operationTheater WHERE "
                    + "orName='" + name + "'";
            rs = stmt.executeQuery(query1);
            String orId = null;
            while (rs.next()) {
                orId = rs.getString("orId");
            }
            return orId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param name
     * @param orType
     * @return
     */
    public String getOrId(String name, String orType) {
        String query1, query2;
        try {
            query1 = "SELECT orId FROM operationTheater WHERE "
                    + "orName='" + name + "' AND orType='"
                    + orType + "'";
            rs = stmt.executeQuery(query1);
            String orId = null;
            while (rs.next()) {
                orId = rs.getString("orId");
            }
            if (orId != null) {
                return orId;
            }
            //it is null so it doesn't exist
            do {
                orId = "OR" + (new Random().nextInt(50) + 1);//1-50
            } while (checkIfOrIdExists(orId));
            query2 = "INSERT INTO operationTheater (orId, orName, orType) VALUES "
                    + "('" + orId + "', '" + name + "', '" + orType + "') ";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return null;
            }

            return orId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean addOrSchedule(String orId, Date date, Time start, Time end, String teamId) {
        String query;
        if (!isOrFree(orId, date)) {
            return false;
        }
        try {
            query = "INSERT INTO operatingRoomDetails (orId, scheduleDate, startTime, endTime, teamId) VALUES "
                    + "('" + orId + "', '" + date + "', '" + start + "', '" + end + "', '" + teamId + "')";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public String getOrNameById(String id) {
        String query;
        try {
            query = "SELECT orName FROM operationTheater WHERE "
                    + "orId='" + id + "'";
            rs = stmt.executeQuery(query);
            String orName = null;
            while (rs.next()) {
                orName = rs.getString("orName");
            }
            return orName;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String getOrTypeById(String id) {
        String query;
        try {
            query = "SELECT orType FROM operationTheater WHERE "
                    + "orId='" + id + "'";
            rs = stmt.executeQuery(query);
            String orType = null;
            while (rs.next()) {
                orType = rs.getString("orType");
            }
            return orType;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Gets all the names of the current available ORs.
     *
     * @return a vector with names if any room found or null otherwise
     */
    public Vector<String> getAllOrNames() {
        String query;
        Vector<String> names = new Vector<>();
        try {
            query = "SELECT orName FROM operationTheater ";
            rs = stmt.executeQuery(query);
            String orName = null;
            while (rs.next()) {
                orName = rs.getString("orName");
                if (orName != null) {
                    names.add(orName);
                }
            }
            if (names.isEmpty()) {
                return null;
            }
            return names;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean setOrNameById(String id, String newName) {
        String query;

        try {
            query = "UPDATE operationTheater SET orName='" + newName + "' "
                    + "WHERE orId = '" + id + "'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Vector<String> getIdForAllOrs() {
        String query;
        Vector<String> ids = new Vector<>();

        try {
            query = "SELECT orId FROM operationTheater ";
            rs = stmt.executeQuery(query);
            String id = null;
            while (rs.next()) {
                id = rs.getString("orId");
                if (id != null) {
                    ids.add(id);
                }
            }
            if (ids.isEmpty()) {
                return null;
            }

            return ids;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int getTotalNumberOfOrs() {
        String query;
        int ors = 0;

        try {
            query = "SELECT orId FROM operationTheater ";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                ors++;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ors;
    }

    /**
     * The returning vector has elements with the following template:
     * <code> orName, date </code>.
     *
     * @param teamId
     * @return
     */
    public Vector<Vector<String>> getAvailableDatesToScheduleForTeam(String teamId) {
        String query1, query;
        Vector<Vector<String>> availableDates = new Vector<>();
        Vector<String> scheduledDates = new Vector<>();

        try {
            query1 = "SELECT\n"
                    + "DISTINCT pd.scheduledDate\n"
                    + "FROM patientDetails pd\n"
                    + "INNER JOIN medicalTeams mt ON\n"
                    + "pd.teamLeaderId = mt.teamLeaderId\n"
                    + "WHERE mt.teamId = '" + teamId + "'\n"
                    + "AND pd.scheduledDate IS NOT NULL\n"
                    + "AND pd.scheduledDate >= CURRENT_DATE() \n"
                    + "AND pd.completed = 'N'\n"
                    + "ORDER BY pd.scheduledDate";
            rs = stmt.executeQuery(query1);
            String scheduledDate = null;
            while (rs.next()) {
                scheduledDate = rs.getString("scheduledDate");
                if (scheduledDate != null) {
                    scheduledDates.add(scheduledDate);
                }
            }

            query = "SELECT\n"
                    + "operationTheater.orName AS orName,\n"
                    + "operatingRoomDetails.scheduleDate AS scheduleDate,\n"
                    + "TIMESTAMPDIFF(MINUTE, operatingRoomDetails.startTime, operatingRoomDetails.endTime) AS MinuteDiff\n"
                    + "FROM operationTheater, operatingRoomDetails\n"
                    + "WHERE operationTheater.orId = operatingRoomDetails.orId\n"
                    + "AND operatingRoomDetails.scheduleDate >= CURRENT_DATE() \n"
                    + "AND operatingRoomDetails.teamId='" + teamId + "'\n"
                    + "ORDER BY operatingRoomDetails.scheduleDate";
            rs = stmt.executeQuery(query);
            String orName = null;
            Date date = null;
            String minuteDiff = "";
            while (rs.next()) {
                orName = rs.getString("orName");
                date = rs.getDate("scheduleDate");
                minuteDiff = rs.getString("MinuteDiff");
                if (orName != null && date != null) {
                    if (scheduledDates.contains(date.toString())) {
                        continue;
                    }
                    Vector<String> entry = new Vector<>();
                    entry.add(orName);
                    entry.add(date.toString());
                    entry.add(minuteDiff);

                    availableDates.add(entry);
                }
            }
            return availableDates;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Vector<>();
    }

    public Vector<Integer> getTotalTimeForAllAvailableDatesToScheduleForTeam(String teamId) {
        String query;
        Vector<Integer> availableDates = new Vector<>();

        try {
            query = "SELECT \n"
                    + "TIMESTAMPDIFF(MINUTE, StartTime, EndTime) AS MinuteDiff \n"
                    + "FROM operatingRoomDetails\n"
                    + "WHERE operatingRoomDetails.teamId='" + teamId + "'\n"
                    + "AND operatingRoomDetails.scheduleDate >= CURRENT_DATE() \n"
                    + "ORDER BY operatingRoomDetails.scheduleDate";
            rs = stmt.executeQuery(query);
            int time = 0;
            while (rs.next()) {
                time = rs.getInt("MinuteDiff");
                if (time != 0) {
                    availableDates.add(time);
                }
            }
            return availableDates;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Vector<>();
    }

    public int getTotalTimeForScheduledTeam(String teamId, String date) {
        String query;
        int totalDayTime = 0;

        try {
            query = "SELECT \n"
                    + "TIMESTAMPDIFF(MINUTE, StartTime, EndTime) AS MinuteDiff \n"
                    + "FROM operatingRoomDetails\n"
                    + "WHERE operatingRoomDetails.teamId='" + teamId + "'\n"
                    + "AND operatingRoomDetails.scheduleDate = '" + date + "'\n";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                totalDayTime = rs.getInt("MinuteDiff");
            }
            return totalDayTime;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public boolean deleteOr(String orId) {
        String query, query1, query2;
        if (getTotalNumberOfOrs() == 1) {
            return false;
        }

        try {
            query = "SELECT COUNT(orId) AS entries \n"
                    + "FROM operatingRoomDetails\n"
                    + "WHERE scheduleDate >= CURRENT_DATE()\n"
                    + "AND orId='" + orId + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int nr = rs.getInt("entries");
                if (nr > 0) {
                    return false;
                }
            }

            query1 = "DELETE FROM operatingRoomDetails WHERE orId='" + orId + "'";
            int rowsEffected = stmt.executeUpdate(query1);

            query2 = "DELETE FROM operationTheater WHERE orId='" + orId + "'";
            rowsEffected += stmt.executeUpdate(query2);

            if (rowsEffected < 1) {
                // not removed succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean removeBookingDateForRoom(String roomId, Date date) {
        String query;
        try {
            query = "DELETE FROM operatingRoomDetails "
                    + "WHERE orId='" + roomId + "' "
                    + "AND scheduleDate='" + date.toString() + "'";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // removing was not succeccfull
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean removeRecordsForTeam(String teamId) {
        String query;
        try {
            query = "DELETE FROM operatingRoomDetails "
                    + "WHERE teamId='" + teamId + "'";
            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // updating was not succeccfull
                return false;
            }

            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Vector<ORData> getRoomScheduledDetails(String id) {
        Vector<ORData> scheduledDetails = new Vector<>();
        String query;
        try {
            query = "SELECT "
                    + "operatingRoomDetails.orId AS orId, "
                    + "operatingRoomDetails.scheduleDate AS scheduleDate, "
                    + "operatingRoomDetails.startTime AS startTime, "
                    + "operatingRoomDetails.endTime AS endTime, "
                    + "medicalTeams.teamName AS teamName "
                    + "FROM operatingRoomDetails, medicalTeams "
                    + "WHERE operatingRoomDetails.teamId=medicalTeams.teamId "
                    + "AND operatingRoomDetails.orId='" + id + "' "
                    + "AND operatingRoomDetails.scheduleDate >= CURRENT_DATE() \n"
                    + "ORDER BY operatingRoomDetails.scheduleDate";
            rs = stmt.executeQuery(query);
            String orId = null;
            Date scheduleDate;
            Time startTime;
            Time endTime;
            String teamName;

            while (rs.next()) {
                orId = rs.getString("orId");
                scheduleDate = rs.getDate("scheduleDate");
                startTime = rs.getTime("startTime");
                endTime = rs.getTime("endTime");
                teamName = rs.getString("teamName");

                if (orId != null && scheduleDate != null && startTime != null
                        && endTime != null && teamName != null) {
                    ORData entry = new ORData(scheduleDate, startTime.toString(), endTime.toString(), teamName);
                    scheduledDetails.add(entry);
                }
            }

            if (scheduledDetails.isEmpty()) {
                return null;
            }

            return scheduledDetails;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="patients data manipulation">
    public boolean checkIfPatientIdExists(String id) {
        String query;
        try {
            query = "SELECT patientId "
                    + "FROM patients WHERE patientId='" + id + "'";
            rs = stmt.executeQuery(query);
            String patientId = null;
            while (rs.next()) {
                patientId = rs.getString("patientId");
            }
            if (patientId != null) {
                //there is another user with this username
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return false;
    }

    /**
     * Gets a list will all the patient IDs that satisfy the given parameters.
     *
     * @param lastName
     * @param firstName
     * @param birthDate
     * @return a vector with patient IDs if found any or null otherwise
     */
    public Vector<String> getPatientByNameAndBirthdate(String lastName, String firstName, String birthDate) {
        String query;
        Vector<String> patientIds = new Vector<>();
        try {
            query = "SELECT patientId FROM patients WHERE \n";
            if (lastName != null) {
                query += "lastName='" + lastName + "'";
            }
            if (lastName != null && (firstName != null || birthDate != null)) {
                query += " AND ";
            }
            if (firstName != null) {
                query += "firstName='" + firstName + "'";
            }
            if (firstName != null && birthDate != null) {
                query += " AND ";
            }
            if (birthDate != null) {
                query += "dob='" + birthDate + "'";
            }
            rs = stmt.executeQuery(query);
            String patientId = null;
            while (rs.next()) {
                patientId = rs.getString("patientId");
                if (patientId != null) {
                    patientIds.add(patientId);
                }
            }
            if (patientIds.isEmpty()) {
                return null;
            }
            return patientIds;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private String getPatientId(String lastName, String firstName, Date birthDate) {
        String query1, query2;
        try {
            query1 = "SELECT patientId FROM patients WHERE "
                    + "lastName='" + lastName
                    + "' AND firstName='" + firstName + "'";
            if (birthDate != null) {
                query1 += " AND dob='" + birthDate + "' ";
            }
            rs = stmt.executeQuery(query1);
            String patientId = null;
            while (rs.next()) {
                patientId = rs.getString("patientId");
            }
            if (patientId != null) {
                return patientId;
            }

            //it is null so it doesn't exist
            do {
                patientId = "P" + (new Random().nextInt(10000000) + 1);//1-10M
            } while (checkIfPatientIdExists(patientId));
            query2 = "INSERT INTO patients (patientId, lastName, firstName, sex, dob) "
                    + "VALUES ('" + patientId + "', '"
                    + lastName + "', '" + firstName + "', 'O', '"
                    + birthDate + "') ";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return null;
            }

            return patientId;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public boolean addNewPatient(String lastName, String firstName,
            Utils.PatientGender patientSex, Date birthDate, String observations) {

        String query1, query2;
        try {
            query1 = "SELECT patientId FROM patients WHERE "
                    + "lastName='" + lastName
                    + "' AND firstName='" + firstName + "'";
            if (birthDate != null) {
                query1 += " AND dob='" + birthDate + "' ";
            }
            rs = stmt.executeQuery(query1);
            String patientId = null;
            while (rs.next()) {
                patientId = rs.getString("patientId");
            }
            if (patientId != null) {
                return false;
            }

            //it is null so it doesn't exist
            do {
                patientId = "P" + (new Random().nextInt(10000000) + 1);//1-10M
            } while (checkIfPatientIdExists(patientId));
            query2 = "INSERT INTO patients (patientId, lastName, firstName, sex, dob, remarks) "
                    + "VALUES ('" + patientId + "', '"
                    + lastName + "', '" + firstName + "', ";
            switch (patientSex.name()) {
                case "MALE":
                    query2 += "'M'";
                    break;
                case "FEMALE":
                    query2 += "'F'";
                    break;
                default:
                    query2 += "'O'";
                    break;
            }
            if (birthDate == null) {
                query2 += ", '9999-12-31' ";
            } else {
                query2 += ", '" + birthDate + "' ";
            }
            if (observations == null || "".equals(observations)) {
                query2 += ", 'No remarks!' )";
            } else {
                query2 += ", '" + observations + "')";
            }
            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updatePatientInfo(String id, String lastName, String firstName,
            Utils.PatientGender patientSex, Date birthDate, String observations) {

        String query;

        if (id == null) {
            return false;
        }
        try {
            query = "UPDATE patients SET \n"
                    + "lastName='" + lastName + "', "
                    + "firstName='" + firstName + "', ";
            switch (patientSex.name()) {
                case "MALE":
                    query += "sex='M', ";
                    break;
                case "FEMALE":
                    query += "sex='F', ";
                    break;
                default:
                    query += "sex='O', ";
                    break;
            }
            if (birthDate == null) {
                query += "dob='9999-12-31', ";
            } else {
                query += "dob='" + birthDate + "', ";
            }
            if (observations == null) {
                query += "remarks='No remarks!' ";
            } else {
                query += "remarks='" + observations + "' ";
            }
            query += "WHERE patientId='" + id + "' ";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Add the surgery if the patient doesn't already have such a surgery
     * uncompleted.
     *
     * @param patientId
     * @param srgId
     * @param ptgId
     * @param doctorId
     * @param coordinatorId
     * @param admision
     * @param scheduled
     * @param scheduledDate
     * @return
     */
    public boolean addUpdateSurgeryToPatient(String patientId, String srgId, String ptgId,
            String doctorId, String coordinatorId, Date admision, String scheduled, Date scheduledDate) {

        String query1, query2;
        try {
            query1 = "SELECT patientId FROM patientDetails "
                    + "WHERE "
                    + "patientId='" + patientId + "' "
                    + "AND surgeryId='" + srgId + "' "
                    + "AND completed='N'";
            rs = stmt.executeQuery(query1);
            String id = null;
            while (rs.next()) {
                id = rs.getString("patientId");
            }
            if (id != null) {
                //patient already has such a surgery uncompleted
                //update details
                query2 = "UPDATE patientDetails SET "
                        + "pathologyId='" + ptgId + "', "
                        + "doctorId='" + doctorId + "', "
                        + "teamLeaderId='" + coordinatorId + "', "
                        + "scheduled='" + scheduled + "'";
                if (scheduledDate != null) {
                    query2 += ", scheduledDate='" + scheduledDate.toString() + "' ";
                }
                query2 += " WHERE patientId='" + patientId + "' "
                        + "AND surgeryId='" + srgId + "'";
            } else {
                //patient doesn't have this surgery. Add it
                query2 = "INSERT INTO patientDetails "
                        + "(patientId, surgeryId, pathologyId, "
                        + "doctorId, teamLeaderId, admissionDate, "
                        + "scheduled, scheduledDate, completed) "
                        + "VALUES ('" + patientId + "', '"
                        + srgId + "', '"
                        + ptgId + "', '"
                        + doctorId + "', '"
                        + coordinatorId + "', '"
                        + admision.toString() + "', "
                        + "'" + scheduled + "', ";
                if (scheduledDate != null) {
                    query2 += "'" + scheduledDate.toString() + "', ";
                } else {
                    query2 += null + ", ";
                }
                query2 += "'N') ";
            }

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean updateSurgeryScheduledStatus(String patientId, String srgId, String ptgId,
            String doctorId, String coordinatorId, String scheduled, Date scheduledDate) {

        String query2;
        try {
            query2 = "UPDATE patientDetails SET "
                    + "pathologyId='" + ptgId + "', "
                    + "doctorId='" + doctorId + "', "
                    + "teamLeaderId='" + coordinatorId + "', "
                    + "scheduled='" + scheduled + "'";
            if (scheduledDate != null) {
                query2 += ", scheduledDate='" + scheduledDate.toString() + "' ";
            } else {
                query2 += ", scheduledDate=" + null + " ";
            }
            query2 += " WHERE patientId='" + patientId + "' "
                    + "AND surgeryId='" + srgId + "'";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean schedulePatient(String patientId, String srgId, String scheduledDate) {
        String query2;
        try {
            query2 = "UPDATE patientDetails SET "
                    + "scheduled='Y', "
                    + "scheduledDate='" + scheduledDate + "' "
                    + "WHERE patientId='" + patientId + "' "
                    + "AND surgeryId='" + srgId + "' "
                    + "AND scheduled='N'";

            int rowsEffected = stmt.executeUpdate(query2);
            if (rowsEffected != 1) {
                // not added succeccfully
                return false;
            }

            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public boolean deleteSurgeryFromPatientHistory(String patientId, String srgId) {
        String query;

        try {
            query = "DELETE FROM patientDetails WHERE "
                    + "patientId='" + patientId + "' "
                    + "AND surgeryId='" + srgId + "' "
                    + "AND completed='N'";

            int rowsEffected = stmt.executeUpdate(query);
            if (rowsEffected != 1) {
                // not deleted succeccfully
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public void updateDoctorInChargeForAssignedPatients(String oldId, String newId) {
        String query;
        try {
            query = "UPDATE patientDetails SET "
                    + "doctorId='" + newId + "' WHERE "
                    + "doctorId='" + oldId + "'";
            stmt.executeUpdate(query);

            return;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }

    public void updateTeamLeaderForAssignedPatients(String oldId, String newId) {
        String query;
        try {
            query = "UPDATE patientDetails SET "
                    + "teamLeaderId='" + newId + "' WHERE "
                    + "teamLeaderId='" + oldId + "'";
            stmt.executeUpdate(query);

            return;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }

    /**
     * The result vector will contain vectors with the following template:      <code>patientId, firstName, lastName, gender("M", "F" or "O"),
     * birthDate, remarks, surgeryName, pathologyName, doctorId, teamLeaderId,
     * admissionDate, scheduled, completed, scheduledDate<code/>. If any of the
     * date elents (birthDate, admissionDate, scheduledDate) or remarks is null,
     * then in their place will be an empty string.
     *
     * @param id "-1" to get all patients from the list or the id of doctor in
     * charge / teamLeader
     * @param team (available if is != "-1") if is 1, the id will be considered
     * for teamLeader. If is 0, the id will be considered for doctor in charge.
     * @param includeCompleted true for including patients with completed
     * surgery
     *
     * @return the result vector if patients found or null otherwise
     */
    public Vector<Vector<String>> getPatientsDetailsForDoctorOrTeam(String id, boolean team, boolean includeCompleted) {
        String query;
        Vector<Vector<String>> result = new Vector<>();
        try {
            query = "SELECT\n"
                    + "patients.patientId AS patientId, \n"
                    + "patients.firstName AS firstName,\n"
                    + "patients.lastName AS lastName,\n"
                    + "patients.sex AS gender,\n"
                    + "patients.dob AS dob,\n"
                    + "patients.remarks AS remarks,\n"
                    + "surgeries.surgeryName AS surgeryName,\n"
                    + "pathologies.pathologyName AS pathologyName,\n"
                    + "patientDetails.doctorId AS doctorId,\n"
                    + "patientDetails.teamLeaderId AS teamLeaderId,\n"
                    + "patientDetails.admissionDate AS admissionDate,\n"
                    + "patientDetails.scheduled AS scheduled,\n"
                    + "patientDetails.completed AS completed,\n"
                    + "patientDetails.scheduledDate AS scheduledDate\n"
                    + "FROM patients, surgeries, pathologies, patientDetails\n"
                    + "WHERE patients.patientId = patientDetails.patientId "
                    + "AND patientDetails.surgeryId = surgeries.surgeryId "
                    + "AND patientDetails.pathologyId = pathologies.pathologyId "
                    + "AND (patientDetails.scheduledDate >= CURRENT_DATE() \n"
                    + "OR patientDetails.scheduled = 'N' ) ";
            if (!includeCompleted) {
                query += "AND patientDetails.completed = 'N' ";
            }
            if (!id.equals("-1") && team) {
                query += "AND patientDetails.teamLeaderId = '" + id + "' ";
            } else if (!id.equals("-1") && !team) {
                query += "AND patientDetails.doctorId = '" + id + "' ";
            }
            query += "ORDER BY patientDetails.admissionDate";
            rs = stmt.executeQuery(query);

            String patientId = null;
            String firstName = null;
            String lastName = null;
            String sex = null;
            Date dob = null;
            String remarks = null;
            String surgeryName = null;
            String pathologyName = null;
            String doctorId = null;
            String teamLeaderId = null;
            Date admissionDate = null;
            String scheduled = null;
            String completed = null;
            Date scheduledDate = null;

            while (rs.next()) {
                patientId = rs.getString("patientId");
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                sex = rs.getString("gender");
                dob = rs.getDate("dob");
                remarks = rs.getString("remarks");
                surgeryName = rs.getString("surgeryName");
                pathologyName = rs.getString("pathologyName");
                doctorId = rs.getString("doctorId");
                teamLeaderId = rs.getString("teamLeaderId");
                admissionDate = rs.getDate("admissionDate");
                scheduled = rs.getString("scheduled");
                completed = rs.getString("completed");
                scheduledDate = rs.getDate("scheduledDate");
                if (patientId != null && firstName != null
                        && lastName != null) {
                    Vector<String> entry = new Vector<>();
                    entry.add(patientId);
                    entry.add(firstName);
                    entry.add(lastName);
                    entry.add(sex);
                    if (dob == null) {
                        entry.add("");
                    } else {
                        entry.add(dob.toString());
                    }
                    if (remarks == null) {
                        entry.add("");
                    } else {
                        entry.add(remarks);
                    }
                    entry.add(surgeryName);
                    entry.add(pathologyName);
                    entry.add(doctorId);
                    entry.add(teamLeaderId);
                    if (admissionDate == null) {
                        entry.add("");
                    } else {
                        entry.add(admissionDate.toString());
                    }
                    entry.add(scheduled);
                    entry.add(completed);
                    if (scheduledDate == null) {
                        entry.add("");
                    } else {
                        entry.add(scheduledDate.toString());
                    }

                    result.add(entry);
                }
            }

            if (result.isEmpty()) {
                return null;
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * The result vector will contain vectors with the following template:
     *
     * <code>patientId, firstName, lastName, surgeryName, pathologyName,
     * surgeryAvdDuration, doctorName, admissionDate<code/>. If any of the date
     * elents (birthDate, admissionDate, scheduledDate) or remarks is null, then
     * in their place will be an empty string.
     *
     * @param id teamLeaderId that has the room scheduled
     * @param date scheduled in this date
     *
     * @return the result vector if patients found or null otherwise
     */
    public Vector<Vector<String>> getScheduledPatientsDetailsForTeam(String id, String date) {
        String query;
        Vector<Vector<String>> result = new Vector<>();
        try {
            query = "SELECT\n"
                    + "patients.patientId AS patientId,\n"
                    + "patients.firstName AS firstName, \n"
                    + "patients.lastName AS lastName, \n"
                    + "surgeries.surgeryName AS surgeryName, \n"
                    + "pathologies.pathologyName AS pathologyName, \n"
                    + "surgeryDetails.avgDuration AS avgDuration,\n"
                    + "CONCAT(doctors.lastName,' ',doctors.firstName) AS doctorName, \n"
                    + "patientDetails.admissionDate AS admissionDate\n"
                    + "FROM patients, surgeries, pathologies, patientDetails, surgeryDetails, doctors\n"
                    + "WHERE patients.patientId = patientDetails.patientId\n"
                    + "AND patientDetails.surgeryId = surgeries.surgeryId\n"
                    + "AND surgeryDetails.surgeryId = surgeries.surgeryId\n"
                    + "AND patientDetails.doctorId = doctors.doctorId\n"
                    + "AND patientDetails.pathologyId = pathologies.pathologyId\n"
                    + "AND patientDetails.teamLeaderId = '" + id + "'\n"
                    + "AND patientDetails.scheduledDate = '" + date + "'\n"
                    + "ORDER BY patientDetails.admissionDate";
            rs = stmt.executeQuery(query);

            String patientId = null;
            String firstName = null;
            String lastName = null;
            String surgeryName = null;
            String pathologyName = null;
            String avgDuration = null;
            String doctorName = null;
            Date admissionDate = null;

            while (rs.next()) {
                patientId = rs.getString("patientId");
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                surgeryName = rs.getString("surgeryName");
                pathologyName = rs.getString("pathologyName");
                avgDuration = rs.getString("avgDuration");
                doctorName = rs.getString("doctorName");
                admissionDate = rs.getDate("admissionDate");
                if (patientId != null && firstName != null
                        && lastName != null) {
                    Vector<String> entry = new Vector<>();
                    entry.add(patientId);
                    entry.add(firstName);
                    entry.add(lastName);
                    entry.add(surgeryName);
                    entry.add(pathologyName);
                    entry.add(avgDuration);
                    entry.add(doctorName);
                    if (admissionDate == null) {
                        entry.add("");
                    } else {
                        entry.add(admissionDate.toString());
                    }

                    result.add(entry);
                }
            }

            if (result.isEmpty()) {
                return null;
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * The result vector will contain vectors with the following template:
     *
     * <code>patientId, firstName, lastName, surgeryName, pathologyName,
     * doctorName, teamName, orName, admissionDate, scheduledDate, completed<code/>.
     * If any of the date elents (admissionDate, scheduledDate) is null, then in
     * their place will be an empty string.
     *
     * @param date scheduled in this date
     *
     * @return the result vector if patients found or null otherwise
     */
    public Vector<Vector<String>> getScheduledPatientsDetailsByDate(String date) {
        String query;
        Vector<Vector<String>> result = new Vector<>();
        try {
            query = "SELECT\n"
                    + "patients.patientId AS patientId,\n"
                    + "patients.firstName AS firstName,\n"
                    + "patients.lastName AS lastName, \n"
                    + "surgeries.surgeryName AS surgeryName, \n"
                    + "pathologies.pathologyName AS pathologyName, \n"
                    + "CONCAT(doctors.lastName,' ',doctors.firstName) AS doctorName,\n"
                    + "medicalTeams.teamName, \n"
                    + "operationTheater.orName,\n"
                    + "patientDetails.admissionDate AS admissionDate, \n"
                    + "patientDetails.scheduledDate,\n"
                    + "patientDetails.completed\n"
                    + "FROM patientDetails\n"
                    + "JOIN patients ON patients.patientId = patientDetails.patientId\n"
                    + "JOIN doctors ON patientDetails.doctorId = doctors.doctorId\n"
                    + "JOIN surgeries ON patientDetails.surgeryId = surgeries.surgeryId\n"
                    + "JOIN surgeryDetails ON surgeryDetails.surgeryId = surgeries.surgeryId\n"
                    + "JOIN pathologies ON patientDetails.pathologyId = pathologies.pathologyId\n"
                    + "JOIN medicalTeams ON patientDetails.teamLeaderId = medicalTeams.teamLeaderId\n"
                    + "JOIN operatingRoomDetails ON operatingRoomDetails.teamId = medicalTeams.teamId\n"
                    + "AND operatingRoomDetails.scheduleDate = patientDetails.scheduledDate\n"
                    + "JOIN operationTheater ON operationTheater.orId = operatingRoomDetails.orId\n"
                    + "WHERE\n"
                    + "patientDetails.scheduledDate = '" + date + "'\n"
                    + "ORDER BY patientDetails.admissionDate";
            rs = stmt.executeQuery(query);

            String patientId = null;
            String firstName = null;
            String lastName = null;
            String surgeryName = null;
            String pathologyName = null;
            String doctorName = null;
            String teamName = null;
            String orName = null;
            Date admissionDate = null;
            Date scheduledDate = null;
            String completed = null;

            while (rs.next()) {
                patientId = rs.getString("patientId");
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                surgeryName = rs.getString("surgeryName");
                pathologyName = rs.getString("pathologyName");
                doctorName = rs.getString("doctorName");
                teamName = rs.getString("teamName");
                orName = rs.getString("orName");
                admissionDate = rs.getDate("admissionDate");
                scheduledDate = rs.getDate("scheduledDate");
                completed = rs.getString("completed");
                if (patientId != null && firstName != null
                        && lastName != null) {
                    Vector<String> entry = new Vector<>();
                    entry.add(patientId);
                    entry.add(firstName);
                    entry.add(lastName);
                    entry.add(surgeryName);
                    entry.add(pathologyName);
                    entry.add(doctorName);
                    entry.add(teamName);
                    entry.add(orName);
                    if (admissionDate == null) {
                        entry.add("");
                    } else {
                        entry.add(admissionDate.toString());
                    }
                    if (scheduledDate == null) {
                        entry.add("");
                    } else {
                        entry.add(scheduledDate.toString());
                    }
                    entry.add(completed);

                    result.add(entry);
                }
            }

            if (result.isEmpty()) {
                return null;
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void setSurgeryCompleted(String patientId, String surgeryId) {
        String query;
        try {
            query = "UPDATE patientDetails SET "
                    + "completed='Y' WHERE "
                    + "patientId='" + patientId + "' "
                    + "AND surgeryId = '" + surgeryId + "'";
            stmt.executeUpdate(query);

            return;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }

    public PatientIdentifiers getPatientDetails(String id, boolean withCompleted) {
        String query1, query2;
        PatientIdentifiers patient = null;
        try {
            String patientId = null;
            String firstName = null;
            String lastName = null;
            String sex = null;
            Date dob = null;
            String remarks = null;

            query1 = "SELECT "
                    + "patients.patientId AS patientId, \n"
                    + "patients.firstName AS firstName,\n"
                    + "patients.lastName AS lastName,\n"
                    + "patients.sex AS gender,\n"
                    + "patients.dob AS dob,\n"
                    + "patients.remarks AS remarks "
                    + "FROM patients "
                    + "WHERE patients.patientId = '" + id + "'\n";
            rs = stmt.executeQuery(query1);
            while (rs.next()) {
                patientId = rs.getString("patientId");
                firstName = rs.getString("firstName");
                lastName = rs.getString("lastName");
                sex = rs.getString("gender");
                dob = rs.getDate("dob");
                remarks = rs.getString("remarks");

                if (patientId != null && firstName != null
                        && lastName != null) {
                    if (dob == null) {
                        patient = new PatientIdentifiers(Integer.parseInt(patientId.replace("P", "")),
                                lastName, firstName, sex.equals("M") ? Utils.PatientGender.MALE
                                : sex.equals("F") ? Utils.PatientGender.FEMALE
                                : Utils.PatientGender.OTHER, null, remarks);
                    } else {
                        java.util.Date d;
                        java.util.Calendar c = Calendar.getInstance();
                        String[] dateElements = dob.toString().split("-");
                        c.set(Calendar.YEAR, Integer.parseInt(dateElements[0]));
                        c.set(Calendar.MONTH, Integer.parseInt(dateElements[1]) - 1); // 0-11
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateElements[2]));
                        d = c.getTime();
                        patient = new PatientIdentifiers(Integer.parseInt(patientId.replace("P", "")),
                                lastName, firstName, sex.equals("M") ? Utils.PatientGender.MALE
                                : sex.equals("F") ? Utils.PatientGender.FEMALE
                                : Utils.PatientGender.OTHER, d, remarks);
                    }
                }
            }
            if (patient == null) {
                return null;
            }

            query2 = "SELECT "
                    + "surgeries.surgeryName AS surgeryName,\n"
                    + "pathologies.pathologyName AS pathologyName,\n"
                    + "patientDetails.doctorId AS doctorId,\n"
                    + "patientDetails.teamLeaderId AS teamLeaderId,\n"
                    + "patientDetails.admissionDate AS admissionDate,\n"
                    + "patientDetails.scheduled AS scheduled,\n"
                    + "patientDetails.completed AS completed,\n"
                    + "patientDetails.scheduledDate AS scheduledDate\n"
                    + "FROM surgeries, pathologies, patientDetails\n"
                    + "WHERE patientDetails.patientId ='" + id + "' "
                    + "AND patientDetails.surgeryId = surgeries.surgeryId "
                    + "AND patientDetails.pathologyId = pathologies.pathologyId \n";
            if (!withCompleted) {
                query2 += "AND patientDetails.completed='N' \n"
                        + "AND patientDetails.scheduledDate >= CURRENT_DATE()\n";
            }
            query2 += "ORDER BY patientDetails.admissionDate";

            rs = stmt.executeQuery(query2);

            String surgeryName = null;
            String pathologyName = null;
            String doctorId = null;
            String teamLeaderId = null;
            Date admissionDate = null;
            String scheduled = null;
            String completed = null;
            Date scheduledDate = null;

            while (rs.next()) {
                surgeryName = rs.getString("surgeryName");
                pathologyName = rs.getString("pathologyName");
                doctorId = rs.getString("doctorId");
                teamLeaderId = rs.getString("teamLeaderId");
                admissionDate = rs.getDate("admissionDate");
                scheduled = rs.getString("scheduled");
                completed = rs.getString("completed");
                scheduledDate = rs.getDate("scheduledDate");
                if (surgeryName != null && pathologyName != null
                        && doctorId != null && teamLeaderId != null) {
                    patient.medicalHistory.surgery.add(surgeryName);
                    patient.medicalHistory.pathology.add(pathologyName);
                    Vector<String> doctorDetails = getDoctorDetailsById(doctorId);
                    MedicIdentifiers doctor = new MedicIdentifiers(doctorDetails.get(1), doctorDetails.get(2),
                            doctorDetails.get(4), Integer.parseInt(doctorDetails.get(0).replace("D", "")),
                            Integer.parseInt(doctorDetails.get(3).replace("D", "")));
                    Vector<String> leaderDetails = getDoctorDetailsById(teamLeaderId);
                    MedicIdentifiers leader = new MedicIdentifiers(leaderDetails.get(1), leaderDetails.get(2),
                            leaderDetails.get(4), Integer.parseInt(leaderDetails.get(0).replace("D", "")),
                            Integer.parseInt(leaderDetails.get(3).replace("D", "")));

                    patient.medicalHistory.doctorInCharge.add(doctor);
                    patient.medicalHistory.doctorTeamLeader.add(leader);
                    patient.medicalHistory.scheduled.add(scheduled.equals("Y"));
                    patient.medicalHistory.complited.add(completed.equals("Y"));
                    if (admissionDate != null) {
                        String[] dateElements = admissionDate.toString().split("-");
                        java.util.Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, Integer.parseInt(dateElements[0]));
                        c.set(Calendar.MONTH, Integer.parseInt(dateElements[1]) - 1); // 0-11
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateElements[2]));
                        patient.medicalHistory.admissionDate.add(c.getTime());
                    } else {
                        patient.medicalHistory.admissionDate.add(new java.util.Date());
                    }
                    if (scheduledDate != null) {
                        String[] dateElements = scheduledDate.toString().split("-");
                        java.util.Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, Integer.parseInt(dateElements[0]));
                        c.set(Calendar.MONTH, Integer.parseInt(dateElements[1]) - 1); // 0-11
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateElements[2]));
                        patient.medicalHistory.scheduledDate.add(c.getTime());
                    } else {
                        patient.medicalHistory.scheduledDate.add(null);
                    }
                }
            }

            return patient;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    // </editor-fold>
}
