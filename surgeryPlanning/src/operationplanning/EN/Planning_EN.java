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
package operationplanning.EN;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.ORClass;
import operationplanning.commonFiles.OperationRooms;
import operationplanning.commonFiles.Utils;
import operationplanning.mainFiles.WelcomeWindow;

/**
 * @abstract
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class Planning_EN extends javax.swing.JFrame {

    private static Planning_EN currentFrame;

    private static Vector<DoctorTeamPanel_EN> doctorTeamPanels;
    private static Vector<OperationRoomPanel_EN> operationRoomPanel;

    private static ManageMedicalTeamsPanel_EN manageMedicalTeamsPannel;
    private static ManageDoctorsPanel_EN manageDoctorsPanel;
    private static CurrentUserPanel_EN manageUserPanel;
    private static CreateUserPanel_EN createUserPanel;
    private static DeleteUserPanel_EN deleteUserPanel;
    private static LogoutPanel_EN logoutPanel;

    private static UpdatePatientPanel_EN updatePatientPanel;
    private static AddNewPatientPanel_EN addPatientPanel;
//    private static AddPatientListPanel_EN addPatientListPanel;
    private static SeePatientListPanel_EN seePatientListPanel;
    private static SeePatientDetailsPannel_EN seePatientDetailsPanel;

    private static CreateSchedulePanel_EN createSchedulePanel;
    private static SeeSchedulePanel_EN seeSchedulePanel;
    private static SetSurgeryCompleted_EN setSurgeryCopleted_EN;

    private static SurgeriesListPanel_EN surgeriesListPannel;

    private static WelcomeWindow welcomeWindow;

    public static Utils.UserType currentUserType;
    public static String currentUserName;

    private static MedicalTeams medicalInst = new MedicalTeams();
    private static OperationRooms operationRoomsInst = new OperationRooms();

    /**
     * Creates new form Antenna
     *
     * @param userType
     * @param userName
     * @param welcomeFormWindow
     */
    public Planning_EN(Utils.UserType userType, String userName, WelcomeWindow welcomeFormWindow) {
        // <editor-fold defaultstate="collapsed" desc="Initialize variables">
        currentUserType = userType;
        currentUserName = userName;
        welcomeWindow = welcomeFormWindow;

        doctorTeamPanels = new Vector<>();
        operationRoomPanel = new Vector<>();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            doctorTeamPanels.add(new DoctorTeamPanel_EN(teamDetail.get(0), teamDetail.get(1)));
        }

        Vector<ORClass> ORs = operationRoomsInst.getORs();
        for (ORClass room : ORs) {
            operationRoomPanel.add(new OperationRoomPanel_EN(room.orID, room.name, room.isMorningOR));
        }

        surgeriesListPannel = new SurgeriesListPanel_EN();

        manageMedicalTeamsPannel = new ManageMedicalTeamsPanel_EN();
        manageDoctorsPanel = new ManageDoctorsPanel_EN();
        manageUserPanel = new CurrentUserPanel_EN();
        createUserPanel = new CreateUserPanel_EN();
        deleteUserPanel = new DeleteUserPanel_EN();
        logoutPanel = new LogoutPanel_EN();
        
        updatePatientPanel = new UpdatePatientPanel_EN();
        addPatientPanel = new AddNewPatientPanel_EN();
//        addPatientListPanel = new AddPatientListPanel_EN();
        seePatientListPanel = new SeePatientListPanel_EN();
        seePatientDetailsPanel = new SeePatientDetailsPannel_EN();
        setSurgeryCopleted_EN = new SetSurgeryCompleted_EN();

        createSchedulePanel = new CreateSchedulePanel_EN();
        seeSchedulePanel = new SeeSchedulePanel_EN();
        // </editor-fold> 
        
        /*Call initComponents method*/
        initComponents();

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(this.getClass().getResource("/images/Logo.jpg"));
            setIconImage(myPicture);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // <editor-fold defaultstate="collapsed" desc="Add tabs to main panel">
        for (Vector<String> teamDetail : teamDetails) {
            int i = teamDetails.indexOf(teamDetail);
            String medicalTeamName = teamDetail.get(1);
            doctorTeamTabbedPanel.addTab(medicalTeamName, doctorTeamPanels.get(i));
        }
        for (OperationRoomPanel_EN roomPanel : operationRoomPanel) {
            operationRoomTabbedPanel.addTab(roomPanel.roomName, roomPanel);
        }

        if (currentUserType == Utils.UserType.HEAD_OF_DEPARTMENT) {
            doctorTeamTabbedPanel.addTab("<html><i>Manage medical teams</i></html>", manageMedicalTeamsPannel);
        } else {
            manageMedicalTeamsPannel = null;
        }
        doctorTeamTabbedPanel.addTab("<html><i>Manage doctors</i></html>", manageDoctorsPanel);

        addPatientTabbedPanel.addTab("Patient list", seePatientListPanel);
        addPatientTabbedPanel.addTab("Search patient", seePatientDetailsPanel);
        addPatientTabbedPanel.addTab("Add new patient", addPatientPanel);
        addPatientTabbedPanel.addTab("Update patient", updatePatientPanel);
//        addPatientTabbedPanel.addTab("Add list of patients", addPatientListPanel);

        if (currentUserType == Utils.UserType.HEAD_OF_DEPARTMENT
                || currentUserType == Utils.UserType.COORDINATOR) {
            scheduleTabbedPanel.addTab("Create schedule", createSchedulePanel);
        } else {
            createSchedulePanel = null;
        }
        scheduleTabbedPanel.addTab("See schedule", seeSchedulePanel);
        scheduleTabbedPanel.addTab("Set surgery as completed", setSurgeryCopleted_EN);

        surgeriesTabbedPanel.addTab("List", surgeriesListPannel);
        
        if (currentUserType == Utils.UserType.HEAD_OF_DEPARTMENT) {
            userTabbedPane.add("Create user", createUserPanel);
            userTabbedPane.add("Delete user", deleteUserPanel);
        }
        userTabbedPane.add("Manage user", manageUserPanel);
        userTabbedPane.add("Logout", logoutPanel);
        // </editor-fold> 

        currentFrame = this;

        pack();
    }

    /**
     *
     * @param pannel
     */
    public static void removeMedicalTeamByTeamId(int teamId) {
        if (!medicalInst.removeEmptyMedicalTeamFromDataBase(teamId)) {
            return;
        }

        doctorTeamTabbedPanel.removeAll();
        DoctorTeamPanel_EN pannelToRemove = null;
        for (DoctorTeamPanel_EN pannel : doctorTeamPanels) {
            if (pannel.teamId == teamId) {
                pannelToRemove = pannel;
                break;
            }
        }
        if (pannelToRemove != null) {
            doctorTeamPanels.remove(pannelToRemove);
        }

        for (DoctorTeamPanel_EN team : doctorTeamPanels) {
            String medicalTeamName = team.getTeamName();
            doctorTeamTabbedPanel.addTab(medicalTeamName, team);
        }

        doctorTeamTabbedPanel.addTab("<html><i>Manage medical teams</i></html>", manageMedicalTeamsPannel);
        doctorTeamTabbedPanel.addTab("<html><i>Manage doctors</i></html>", manageDoctorsPanel);
    }

    public static void addNewMedicalTeamPannel() {
        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();

        doctorTeamPanels.removeAllElements();
        doctorTeamTabbedPanel.removeAll();
        for (Vector<String> teamDetail : teamDetails) {
            doctorTeamPanels.add(new DoctorTeamPanel_EN(teamDetail.get(0), teamDetail.get(1)));
        }

        for (DoctorTeamPanel_EN team : doctorTeamPanels) {
            String medicalTeamName = team.getTeamName();
            doctorTeamTabbedPanel.addTab(medicalTeamName, team);
        }

        doctorTeamTabbedPanel.addTab("<html><i>Manage medical teams</i></html>", manageMedicalTeamsPannel);
        doctorTeamTabbedPanel.addTab("<html><i>Manage doctors</i></html>", manageDoctorsPanel);
    }

    public static void removeOperatingRoom(OperationRoomPanel_EN pannel) {
        if (!operationRoomsInst.removeOR(pannel.roomID)) {
            //there was nothing removed
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "This operation room cannot be deleted because it might me the only one left."
                    + "Add another operating room then delete this one.",
                    "Error deleting operating room",
                    JOptionPane.OK_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        Vector<ORClass> ORs = operationRoomsInst.getORs();
        operationRoomPanel.removeAllElements();
        for (ORClass room : ORs) {
            operationRoomPanel.add(new OperationRoomPanel_EN(room.orID, room.name, room.isMorningOR));
        }

        operationRoomTabbedPanel.removeAll();
        for (OperationRoomPanel_EN roomPanel : operationRoomPanel) {
            operationRoomTabbedPanel.addTab(roomPanel.roomName, roomPanel);
        }
    }

    public static void updateOperationRoomTabbedPanne() {
        operationRoomTabbedPanel.removeAll();
        for (OperationRoomPanel_EN room : operationRoomPanel) {
            room.updateORTimeTable();
            room.updateMedicalLeadComboBox();
            operationRoomTabbedPanel.addTab(room.roomName, room);
        }
    }

    public static void addNewOperationRoom(boolean isMorningRoom, String name) {

        ORClass newOR = new ORClass(0, isMorningRoom, name);
        if (!operationRoomsInst.addNewOR(newOR)) {
            //the adding was not successful
            return;
        }
        Vector<ORClass> ORs = operationRoomsInst.getORs();

        operationRoomPanel.removeAllElements();
        for (ORClass room : ORs) {
            operationRoomPanel.add(new OperationRoomPanel_EN(room.orID, room.name, room.isMorningOR
            ));
        }

        operationRoomTabbedPanel.removeAll();
        for (OperationRoomPanel_EN roomPanel : operationRoomPanel) {
            operationRoomTabbedPanel.addTab(roomPanel.roomName, roomPanel);
        }
    }

    public static void refreshMedicalTeamTabbedPannel() {
        if (doctorTeamTabbedPanel == null) {
            return;
        }

        doctorTeamTabbedPanel.removeAll();
        for (DoctorTeamPanel_EN doctorTeamPanel : doctorTeamPanels) {
            doctorTeamPanel.updateMedicalTeamTable();
            doctorTeamPanel.updateMedicalTeamORtimetable();
            String medicalTeamName = doctorTeamPanel.teamName;
            doctorTeamTabbedPanel.addTab(medicalTeamName, doctorTeamPanel);
        }

        doctorTeamTabbedPanel.addTab("<html><i>Manage medical teams</i></html>", manageMedicalTeamsPannel);
        doctorTeamTabbedPanel.addTab("<html><i>Manage doctors</i></html>", manageDoctorsPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPanel = new javax.swing.JTabbedPane();
        doctorTeamTabbedPanel = new javax.swing.JTabbedPane();
        addPatientTabbedPanel = new javax.swing.JTabbedPane();
        surgeriesTabbedPanel = new javax.swing.JTabbedPane();
        operationRoomTabbedPanel = new javax.swing.JTabbedPane();
        scheduleTabbedPanel = new javax.swing.JTabbedPane();
        userTabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Surgery planning [EN]");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(850, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainTabbedPanel.setMinimumSize(new java.awt.Dimension(640, 480));
        mainTabbedPanel.setPreferredSize(new java.awt.Dimension(640, 480));

        doctorTeamTabbedPanel.setAutoscrolls(true);
        mainTabbedPanel.addTab("Medical teams", null, doctorTeamTabbedPanel, "");
        mainTabbedPanel.addTab("Patients", addPatientTabbedPanel);
        mainTabbedPanel.addTab("Surgeries", surgeriesTabbedPanel);
        mainTabbedPanel.addTab("OR timetable", operationRoomTabbedPanel);
        mainTabbedPanel.addTab("Schedule", scheduleTabbedPanel);
        mainTabbedPanel.addTab("User", userTabbedPane);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainTabbedPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainTabbedPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JOptionPane.showConfirmDialog(null, "Log out before closing the window!", 
                "Cannot close window",
                JOptionPane.DEFAULT_OPTION);
    }//GEN-LAST:event_formWindowClosing

    public static void closeSesion(){
        currentFrame.setVisible(false);
        currentFrame.dispose();

        welcomeWindow.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane addPatientTabbedPanel;
    private static javax.swing.JTabbedPane doctorTeamTabbedPanel;
    private static javax.swing.JTabbedPane mainTabbedPanel;
    private static javax.swing.JTabbedPane operationRoomTabbedPanel;
    private javax.swing.JTabbedPane scheduleTabbedPanel;
    private javax.swing.JTabbedPane surgeriesTabbedPanel;
    private javax.swing.JTabbedPane userTabbedPane;
    // End of variables declaration//GEN-END:variables

}
