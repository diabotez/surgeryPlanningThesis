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
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class MedicIdentifiers {
    public String lastName;
    public String firstName;
    public String department;
    public int IDnumber;
    public int coordinatorID;

    public MedicIdentifiers() {
        lastName = new String();
        firstName = new String();
        department = new String();
        IDnumber = 0;
        coordinatorID = 0;
    }

    public MedicIdentifiers(String lastName, String firstName, String department, int ID, int coordinatorID) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.department = department;
        this.IDnumber = ID;
        this.coordinatorID = coordinatorID;
    }
}
