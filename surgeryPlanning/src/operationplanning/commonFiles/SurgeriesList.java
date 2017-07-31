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

/**
 * This class is the interface for all surgeries with the database.
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class SurgeriesList {

    /**
     * Gets the average duration of the given surgery name if it exists in the
     * data base.
     *
     * @param surgeryName the given name of the surgery
     *
     * @return the average duration if the surgery exists or -1 if not
     */
    public int getSurgeryStandarDeviation(String surgeryName) {
        DatabaseQueries db = new DatabaseQueries();
        String surgeryId = db.getSurgeryId(surgeryName);
        int std = db.getSurgeryStdDeviation(surgeryId);
        if(std == 0){
            return -1;
        } else {
            return std;
        }
        
//        int idx = surgeriesName.indexOf(surgeryName);
//        if (-1 == idx) {
//            return idx;
//        }
//
//        return stdDeviations.elementAt(idx);
    }

    /**
     * Gets the average duration of the given surgery name if it exists in the
     * data base.
     *
     * @param surgeryName the given name of the surgery
     *
     * @return the average duration if the surgery exists or -1 if not
     */
    public int getSurgeryAverageDuration(String surgeryName) {
//        int idx = surgeriesName.indexOf(surgeryName);
//        if (-1 == idx) {
//            return idx;
//        }
//
//        return avgDurations.elementAt(idx);

        DatabaseQueries db = new DatabaseQueries();
        String surgeryId = db.getSurgeryId(surgeryName);
        int avg = db.getSurgeryAvgDuration(surgeryId);
        if(avg == 0){
            return -1;
        } else {
            return avg;
        }
    }

    /**
     * Checks if the given surgery name exists in the data base.
     *
     * @param srg the name of the surgery to be searched in the data base
     *
     * @return true if the name exists and false if not
     */
    public boolean checkIfSurgeryExists(String srg) {
        DatabaseQueries db = new DatabaseQueries();
        String surgeryId = db.getSurgeryId(srg);
        boolean ans =  db.checkIfSurgeryIdExists("surgeryDetails", surgeryId);
        return ans;
    }
    
    /**
     * Adds the surgery to the database if the surgery name does not exists
     * already.
     *
     * @param srg the surgery name
     * @param ptg
     * @param avg the average time for the surgery
     * @param std the standard deviation for the given surgery
     *
     * @return -1 if the surgery name is already in the data base and 0 if it
     * was successfully added
     */
    public int addNewSurgery(String srg, String ptg, String avg, String std) {
        if (checkIfSurgeryExists(srg)) {
            //do nothing
            return -1;
        }

        new DatabaseQueries().addNewSurgery(srg, ptg, Integer.parseInt(avg), Integer.parseInt(std));
        return 0;
    }

    /**
     * Updates the data of the given surgery name if it exists in the data base.
     *
     * @param srg the name of the surgery to be updated
     * @param ptg
     * @param avg the new average duration of the given surgery name
     * @param std the new standard deviation of the given surgery name
     *
     */
    public void updateSurgery(String srg, String ptg, String avg, String std) {
        new DatabaseQueries().updateSurgery(srg, ptg, Integer.parseInt(avg), Integer.parseInt(std));
    }

    /**
     * Removes the given surgery (if it exists) from the data base and it's
     * associated data.
     *
     * @param srg the name of the surgery to be removed
     */
    public boolean removeSurgery(String srg) {
        return new DatabaseQueries().deleteSurgery(srg);
    }

    public int getSurgeryIdByName(String surgeryName) {
        String id = new DatabaseQueries().getSurgeryId(surgeryName);
        if (id == null) {
            return -1;
        }
        return Integer.parseInt(id.replace("SRG", ""));
    }

    /**
     * The constructor of this class.
     */
    public SurgeriesList() {
    }

}
