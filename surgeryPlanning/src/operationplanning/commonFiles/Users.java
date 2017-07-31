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

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is the interface with the database for managing users.
 *
 * @author Diana Botez
 */
public class Users {

    private static Users instance = null;

    /**
     * The passphrase for encrypting passwords.
     */
    private static final String passphrase = "90eeebf00e3f2da97a1a3cc31b2a65e4";

    /**
     * The constructor of this class.
     */
    private Users() {
    }

    /**
     * This method returns the singleton instance of the class Users.
     *
     * @return the singleton instance of the class
     */
    public static Users getInstance() {
        if (instance == null) {
            instance = new Users();
        }
        return instance;
    }

    /**
     * This method adds a new user in the data base, along with it's password
     * and type.
     *
     * @param userName - the given user name for the new user
     * @param password - the given password for the new user
     * @param userType - the given type for the new user
     *
     * @return true if the user was added successfully or false otherwise
     */
    public boolean addNewUser(String userName, String password, Utils.UserType userType) {
        if (userName == null || password == null || userType == null) {
            return false;
        }
        String encryptedNewPass = encryptPasswordString(userName, password);
        return new DatabaseQueries().addNewUser(userName, encryptedNewPass, userType);
    }

    /**
     * This method sets a new password for the given user name.
     *
     * @param userName - the given user name for the password to be change
     * @param newPassword - the new password for the given user name
     *
     * @return true if the password was changed successfully or false otherwise
     */
    public boolean changePasswordForUser(String userName, String newPassword) {
        String encryptedNewPass = encryptPasswordString(userName, newPassword);
        return new DatabaseQueries().updateUserPassword(userName, encryptedNewPass);
    }

    /**
     * Checks if the given username and encrypted password are in the database
     * for a user.
     *
     * @param userName the given user name for the type to be change
     * @param password the plain text password for the given username
     *
     * @return true if found the given user with the given password or false
     * otherwise
     */
    public boolean checkUserCredentials(String userName, String password) {
        String encryptedPassword = encryptPasswordString(userName, password);
        return new DatabaseQueries().checkUserCredentials(userName, encryptedPassword);
    }

    public boolean deleteUser(String username) {
        return new DatabaseQueries().deleteUser(username);
    }

    public static String encryptPasswordString(String username, String password) {
        try {
            // Get the Key
            byte[] key = (passphrase + username).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit

            // Generate the secret key specs.
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            // Instantiate the cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedBytes = cipher.doFinal((password).getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
