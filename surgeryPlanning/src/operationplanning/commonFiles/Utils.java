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
 * @abstract
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class Utils {
//    /**/
//    public static int medicalTeamsNumber = 5;

    /**/
    public enum UserType {
        HEAD_OF_DEPARTMENT,//==0
        COORDINATOR, //==1
        MEDIC, // ==2
        ASSISTANT, //==3
        UNKNOWN_TYPE//==4
    }

    public static UserType getUserType(int index) {
        switch (index) {
            case 0:
                return UserType.HEAD_OF_DEPARTMENT;
            case 1:
                return UserType.COORDINATOR;
            case 2:
                return UserType.MEDIC;
            case 3:
                return UserType.ASSISTANT;
            default:
                return UserType.UNKNOWN_TYPE;
        }
    }

    /**
     *      */
    public enum ValidateUser {
        VALIDATION_OK,
        UNKNOWN_USER,
        WRONG_PASSWORD,
        USER_ALREADY_EXISTS,
        INVALID_DATA
    }

    /**
     *      */
    public enum Algorithm {
        MILP,
        MIQCP,
        MIQCP_P
    }

    /**
     *
     */
    public enum PatientGender {
        MALE,
        FEMALE,
        OTHER
    }
}
