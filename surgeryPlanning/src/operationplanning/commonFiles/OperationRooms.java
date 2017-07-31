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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * @abstract This class is an interface with the database for managing operating
 * rooms.
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class OperationRooms {

    private int averageOccupancyRate = 80;

    /**
     * @return the averageOccupancyRate
     */
    public int getAverageOccupancyRate() {
        return averageOccupancyRate;
    }

    /**
     * @param averageOccupancyRate the averageOccupancyRate to set
     */
    public void setAverageOccupancyRate(int averageOccupancyRate) {
        this.averageOccupancyRate = averageOccupancyRate;
    }

    ///////////////////////////////////////////////////////
    
    /**
     * Constructor of this class. Loads data from a XML file.
     */
    public OperationRooms() {
    }
    
    public int getNumberOfOrAvailableDatesAssigned(String teamId){
        return new DatabaseQueries().getNumberOfAvailableDaysAssigned(teamId);
    }

    /**
     * Returns the name of the OR.
     *
     * @param roomID the Id of the OR
     *
     * @return the name of the OR or null if no Or was found with the specified
     * ID
     */
    public String getORName(int roomID) {
        return new DatabaseQueries().getOrNameById("OR" + roomID);
    }

    /**
     * Changes the name of the specified OR.
     *
     * @param roomID the ID of the OR to change the name
     * @param newName the new name for the OR
     */
    public void setORNameById(int roomID, String newName) {
        new DatabaseQueries().setOrNameById("OR" + roomID, newName);
    }

    /**
     * Adds a new booking or updates an existing one if the roomID is found.
     *
     * @param roomID the ID of the OR
     * @param entryIndex the index where the next info need to be added or
     * updated
     * @param teamName the name of the medical team that has the booking
     * @param newDate the date of the booking
     * @param newStart the starting hour
     * @param newEnd the ending hour
     *
     * @return true if the adding/updating was successful and false if there is
     * no OR with he specified ID
     */
    public boolean addOrEntry(int roomID, String teamName, Date newDate, String newStart, String newEnd) {
        Time startTime = Time.valueOf(newStart + ":00");
        Time endTime = Time.valueOf(newEnd + ":00");
        
        DatabaseQueries db = new DatabaseQueries();
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = outputFormatter.format(newDate);
        java.sql.Date d = java.sql.Date.valueOf(dateString);

        return db.addOrSchedule("OR" + roomID, d, startTime, endTime, db.getTeamIdByTeamName(teamName));
    }

    /**
     * Adds a new OR if the ID already set does not exist.
     *
     * @param newOR the instance of the new OR to be added
     *
     * @return true if the adding was successful and false if the ID already
     * exists
     */
    public boolean addNewOR(ORClass newOR) {
        String orId = new DatabaseQueries().getOrId(newOR.name, newOR.isMorningOR ? "M" : "E");
        newOR.orID = Integer.parseInt(orId.replace("OR", ""));

        return true;
    }

    /**
     * Get the ORClass instance with all the data available about the specified
     * OR.
     *
     * @param roomID the ID of the desired OR's info
     *
     * @return the instance of ORClass or null if there is no OR with the
     * specified ID
     */
    public ORClass getDataFor(int roomID) {
        DatabaseQueries db = new DatabaseQueries();
        String orName = db.getOrNameById("OR" + roomID);
        if (orName == null) {
            return null;
        }
        String orType = db.getOrTypeById("OR" + roomID);
        ORClass room = new ORClass(roomID, orType.equals("M"), orName);
        Vector<ORData> scheduledDetails = db.getRoomScheduledDetails("OR" + roomID);

        if (scheduledDetails != null && !scheduledDetails.isEmpty()) {
            room.availableDates = scheduledDetails;
        }

        return room;
    }

    public Vector<ORClass> getORs() {
        DatabaseQueries db = new DatabaseQueries();
        Vector<String> orIds = db.getIdForAllOrs();
        Vector<ORClass> rooms = new Vector<>();

        for (String orId : orIds) {
            ORClass room = getDataFor(Integer.parseInt(orId.replace("OR", "")));
            if (room != null) {
                rooms.add(room);
            }
        }

        return rooms;
    }

    /**
     * This method looks in all the current operating rooms and takes all the
     * elements where the leaderId is the same with the given one.
     *
     * @param teamName the name of the team
     *
     * @return a vector with the available ORs that have at least one booking
     * for the specified leader, or an empty vector if no element was found
     */
    public Vector<ORClass> getORsForTeam(String teamName) {
        Vector<ORClass> orEntryForTeam = new Vector<>();
        Vector<ORClass> availableOrs = getORs();

        for (ORClass room : availableOrs) {
            ORClass aux = new ORClass(room.orID, room.isMorningOR, room.name);
            Vector<ORData> dates = new DatabaseQueries().getRoomScheduledDetails("OR" + room.orID);

            if (dates == null || dates.isEmpty()) {
                continue;
            }

            for (ORData entry : dates) {
                if (entry.teamName.equals(teamName)) {
                    aux.addNewDate(entry);
                }
            }
            if (aux.availableDates.isEmpty()) {
                continue;
            }
            orEntryForTeam.add(aux);
        }

        return orEntryForTeam;
    }

    /**
     * Checks if the specified medical leader has already a booking in the same
     * date in one of the available ORs.
     *
     * @param teamName
     * @param date the date for checking the bookings
     *
     * @return true if the team is available and false if not or if the date is
     * null
     */
    public boolean isTeamAvailable(String teamName, Date date) {
        if (null == date) {
            return false;
        }

        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = outputFormatter.format(date);

        java.sql.Date d = java.sql.Date.valueOf(dateString);
        DatabaseQueries db = new DatabaseQueries();
        return db.isTeamAvailable(db.getTeamIdByTeamName(teamName), d);
    }

    public boolean removeRecordsForTeam(int teamId) {
        return new DatabaseQueries().removeRecordsForTeam("TM" + teamId);
    }

    /**
     * .
     * If the date is null or roomID is lessThan(0), the return will be false.
     *
     * @param roomId
     * @param date
     * @return
     */
    public boolean isRoomAvailable(int roomId, Date date) {
        if (null == date) {
            return false;
        }
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = outputFormatter.format(date);
        java.sql.Date d = java.sql.Date.valueOf(dateString);
        return new DatabaseQueries().isOrFree("OR" + roomId, d);
    }

    public boolean removeBookingDateForOr(int roomId, java.sql.Date date) {
        return new DatabaseQueries().removeBookingDateForRoom("OR" + roomId, date);
    }

    /**
     * Removes a OR from the data base.
     *
     * @param roomID the ID of the room to be removed
     *
     * @return false if the given ID was not found and true if the deleting was
     * successful
     */
    public boolean removeOR(int roomID) {
        DatabaseQueries db = new DatabaseQueries();

        if (db.getTotalNumberOfOrs() == 1) {
            //it's just one room left and it cannot be deleted
            return false;
        }

        return db.deleteOr("OR" + roomID);
    }
}
