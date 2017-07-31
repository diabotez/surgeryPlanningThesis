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

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class ORClass {

    public Vector<ORData> availableDates;
    public int orID;
    public boolean isMorningOR;
    public String name;

    public ORClass(int id, boolean morning, String name) {
        availableDates = new Vector<>();
        orID = id;
        isMorningOR = morning;
        this.name = name;
    }

    public void addNewDate(ORData newEntry) {
        Calendar calendar = Calendar.getInstance();

        String[] startTimeStrings = newEntry.startingHour.split(":");
        String[] endTimeStrings = newEntry.endingHour.split(":");

        calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(startTimeStrings[0]));
        calendar.set(calendar.MINUTE, Integer.parseInt(startTimeStrings[1]));
        calendar.set(calendar.SECOND, 0);
        Time startTime = new Time(calendar.getTime().getTime());

        calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(endTimeStrings[0]));
        calendar.set(calendar.MINUTE, Integer.parseInt(endTimeStrings[1]));
        calendar.set(calendar.SECOND, 0);
        Time endTime = new Time(calendar.getTime().getTime());

        DatabaseQueries db = new DatabaseQueries();
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = outputFormatter.format(newEntry.date);
        java.sql.Date d = java.sql.Date.valueOf(dateString);
        boolean ans = db.addOrSchedule("OR" + orID, d, startTime, endTime, db.getTeamIdByTeamName(newEntry.teamName));

        availableDates.add(newEntry);
    }

    public Vector<ORData> getAvailableDates() {

        return availableDates;
    }

    public void setAvailableDates(Vector<ORData> dates) {
        availableDates = dates;
    }
}
