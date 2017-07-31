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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class DatabaseConnection {

    private static String dbConnectionString;
    private static String dbUserName;
    private static String dbUserPassword;
    private static Connection dbConnection;

    private static DatabaseConnection instance;

    /**
     * The public constructor of this class.
     *
     * @param language
     */
    private DatabaseConnection() {
        dbConnectionString = "jdbc:mysql://localhost:3306/surgery_planning?zeroDateTimeBehavior=convertToNull";
        dbUserName = "rootUser";
        dbUserPassword = "Z4r4g0Z4";
    }

    /**
     * Gets the instance of this class.
     *
     * @return
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Connecting to dataBase.
     *
     * @return true if the connection succeeded and false otherwise
     */
    public String connectToDataBase() {
        try {
            dbConnection = DriverManager.getConnection(dbConnectionString, dbUserName, dbUserPassword);
            System.out.println("Connected to DB");
            return "Connected";
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
    }

    /**
     * Disconnect from database.
     */
    public void disconnectFromDataBase() {
        try {
            if (dbConnection != null) {
                dbConnection.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

}
