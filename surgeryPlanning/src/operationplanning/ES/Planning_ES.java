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
package operationplanning.ES;

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
public class Planning_ES extends javax.swing.JFrame {

    private static Planning_ES currentFrame;

    private static Vector<DoctorTeamPanel_ES> doctorTeamPanels;
    private static Vector<OperationRoomPanel_ES> operationRoomPanel;

    private static ManageMedicalTeamsPanel_ES manageMedicalTeamsPannel;
    private static ManageDoctorsPanel_ES manageDoctorsPanel;
    private static CurrentUserPanel_ES manageUserPanel;
    private static CreateUserPanel_ES createUserPanel;
    private static DeleteUserPanel_ES deleteUserPanel;
    private static LogoutPanel_ES logoutPanel;

    private static UpdatePatientPanel_ES updatePatientPanel;
    private static AddNewPatientPanel_ES addPatientPanel;
//    private static AddPatientListPanel_ES addPatientListPanel;
    private static SeePatientListPanel_ES seePatientListPanel;
    private static SeePatientDetailsPannel_ES seePatientDetailsPanel;

    private static CreateSchedulePanel_ES createSchedulePanel;
    private static SeeSchedulePanel_ES seeSchedulePanel;
    private static SetSurgeryCompleted_ES setSurgeryCopleted_ES;

    private static SurgeriesListPanel_ES surgeriesListPannel;

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
    public Planning_ES(Utils.UserType userType, String userName, WelcomeWindow welcomeFormWindow) {
        // <editor-fold defaultstate="collapsed" desc="Initialize variables">
        currentUserType = userType;
        currentUserName = userName;
        welcomeWindow = welcomeFormWindow;

        doctorTeamPanels = new Vector<>();
        operationRoomPanel = new Vector<>();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            doctorTeamPanels.add(new DoctorTeamPanel_ES(teamDetail.get(0), teamDetail.get(1)));
        }

        Vector<ORClass> ORs = operationRoomsInst.getORs();
        for (ORClass room : ORs) {
            operationRoomPanel.add(new OperationRoomPanel_ES(room.orID, room.name, room.isMorningOR));
        }

        surgeriesListPannel = new SurgeriesListPanel_ES();

        manageMedicalTeamsPannel = new ManageMedicalTeamsPanel_ES();
        manageDoctorsPanel = new ManageDoctorsPanel_ES();
        manageUserPanel = new CurrentUserPanel_ES();
        createUserPanel = new CreateUserPanel_ES();
        deleteUserPanel = new DeleteUserPanel_ES();
        logoutPanel = new LogoutPanel_ES();

        updatePatientPanel = new UpdatePatientPanel_ES();
        addPatientPanel = new AddNewPatientPanel_ES();
//        addPatientListPanel = new AddPatientListPanel_ES();
        seePatientListPanel = new SeePatientListPanel_ES();
        seePatientDetailsPanel = new SeePatientDetailsPannel_ES();

        createSchedulePanel = new CreateSchedulePanel_ES();
        seeSchedulePanel = new SeeSchedulePanel_ES();
        setSurgeryCopleted_ES = new SetSurgeryCompleted_ES();
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
        for (OperationRoomPanel_ES roomPanel : operationRoomPanel) {
            operationRoomTabbedPanel.addTab(roomPanel.roomName, roomPanel);
        }

        if (currentUserType == Utils.UserType.HEAD_OF_DEPARTMENT) {
            doctorTeamTabbedPanel.addTab("<html><i>Gestionar equipo médico</i></html>", manageMedicalTeamsPannel);
        } else {
            manageMedicalTeamsPannel = null;
        }
        doctorTeamTabbedPanel.addTab("<html><i>Gestionar doctores</i></html>", manageDoctorsPanel);

        addPatientTabbedPanel.addTab("Ver lista de pacientes", seePatientListPanel);
        addPatientTabbedPanel.addTab("Busca paciente", seePatientDetailsPanel);
        addPatientTabbedPanel.addTab("Añadir nuevo paciente", addPatientPanel);
        addPatientTabbedPanel.addTab("Modificar paciente", updatePatientPanel);

        if (currentUserType == Utils.UserType.HEAD_OF_DEPARTMENT
                || currentUserType == Utils.UserType.COORDINATOR) {
            scheduleTabbedPanel.addTab("Crear un horario de operación", createSchedulePanel);
        } else {
            createSchedulePanel = null;
        }
        scheduleTabbedPanel.addTab("Ver el horario de operación", seeSchedulePanel);
        scheduleTabbedPanel.addTab("Definir la cirugía como completada", setSurgeryCopleted_ES);
        
        pathologiesTabbedPanel.addTab("Lista", surgeriesListPannel);

        if (currentUserType == Utils.UserType.HEAD_OF_DEPARTMENT) {
            userTabbedPane.add("Crear usuario", createUserPanel);
            userTabbedPane.add("Borrar usuario", deleteUserPanel);
        }
        userTabbedPane.add("Administrar usuario", manageUserPanel);
        userTabbedPane.add("Cerrar la sesión", logoutPanel);
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
        DoctorTeamPanel_ES pannelToRemove = null;
        for (DoctorTeamPanel_ES pannel : doctorTeamPanels) {
            if (pannel.teamId == teamId) {
                pannelToRemove = pannel;
                break;
            }
        }
        if (pannelToRemove != null) {
            doctorTeamPanels.remove(pannelToRemove);
        }

        for (DoctorTeamPanel_ES team : doctorTeamPanels) {
            String medicalTeamName = team.getTeamName();
            doctorTeamTabbedPanel.addTab(medicalTeamName, team);
        }

        doctorTeamTabbedPanel.addTab("<html><i>Gestionar equipo médico</i></html>", manageMedicalTeamsPannel);
        doctorTeamTabbedPanel.addTab("<html><i>Gestionar doctores</i></html>", manageDoctorsPanel);
    }

    public static void addNewMedicalTeamPannel() {
        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();

        doctorTeamPanels.removeAllElements();
        doctorTeamTabbedPanel.removeAll();
        for (Vector<String> teamDetail : teamDetails) {
            doctorTeamPanels.add(new DoctorTeamPanel_ES(teamDetail.get(0), teamDetail.get(1)));
        }

        for (DoctorTeamPanel_ES team : doctorTeamPanels) {
            String medicalTeamName = team.getTeamName();
            doctorTeamTabbedPanel.addTab(medicalTeamName, team);
        }

        doctorTeamTabbedPanel.addTab("<html><i>Gestionar equipo médico</i></html>", manageMedicalTeamsPannel);
        doctorTeamTabbedPanel.addTab("<html><i>Gestionar doctores</i></html>", manageDoctorsPanel);
    }

    public static void removeOperatingRoom(OperationRoomPanel_ES pannel) {
        if (!operationRoomsInst.removeOR(pannel.roomID)) {
            //there was nothing removed
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Este quirofano no puede ser borrada porque "
                    + "podría ser yo la única izquierda \no tiene equipos programados."
                    + "Añada otro quirofano y elimine ésta.",
                    "Error al eliminar quirofano",
                    JOptionPane.OK_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        Vector<ORClass> ORs = operationRoomsInst.getORs();
        operationRoomPanel.removeAllElements();
        for (ORClass room : ORs) {
            operationRoomPanel.add(new OperationRoomPanel_ES(room.orID, room.name, room.isMorningOR));
        }

        operationRoomTabbedPanel.removeAll();
        for (OperationRoomPanel_ES roomPanel : operationRoomPanel) {
            operationRoomTabbedPanel.addTab(roomPanel.roomName, roomPanel);
        }
    }

    public static void updateOperationRoomTabbedPanne() {
        operationRoomTabbedPanel.removeAll();
        for (OperationRoomPanel_ES room : operationRoomPanel) {
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
            operationRoomPanel.add(new OperationRoomPanel_ES(room.orID, room.name, room.isMorningOR));
        }

        operationRoomTabbedPanel.removeAll();
        for (OperationRoomPanel_ES roomPanel : operationRoomPanel) {
            operationRoomTabbedPanel.addTab(roomPanel.roomName, roomPanel);
        }
    }

    public static void refreshMedicalTeamTabbedPannel() {
        if (doctorTeamTabbedPanel == null) {
            return;
        }

        doctorTeamTabbedPanel.removeAll();

        for (DoctorTeamPanel_ES doctorTeamPanel : doctorTeamPanels) {
            doctorTeamPanel.updateMedicalTeamTable();
            doctorTeamPanel.updateMedicalTeamORtimetable();
            String medicalTeamName = doctorTeamPanel.teamName;
            doctorTeamTabbedPanel.addTab(medicalTeamName, doctorTeamPanel);
        }

        doctorTeamTabbedPanel.addTab("<html><i>Gestionar equipo médico</i></html>", manageMedicalTeamsPannel);
        doctorTeamTabbedPanel.addTab("<html><i>Gestionar doctores</i></html>", manageDoctorsPanel);
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
        pathologiesTabbedPanel = new javax.swing.JTabbedPane();
        operationRoomTabbedPanel = new javax.swing.JTabbedPane();
        scheduleTabbedPanel = new javax.swing.JTabbedPane();
        userTabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Surgery planning [ES]");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainTabbedPanel.setMinimumSize(new java.awt.Dimension(640, 480));
        mainTabbedPanel.setPreferredSize(new java.awt.Dimension(640, 480));

        doctorTeamTabbedPanel.setAutoscrolls(true);
        mainTabbedPanel.addTab("Equipos médicos", null, doctorTeamTabbedPanel, "");
        mainTabbedPanel.addTab("Pacientes", addPatientTabbedPanel);
        mainTabbedPanel.addTab("Cirugías", pathologiesTabbedPanel);
        mainTabbedPanel.addTab("Horario de quirófano", operationRoomTabbedPanel);
        mainTabbedPanel.addTab("Programar", scheduleTabbedPanel);
        mainTabbedPanel.addTab("Usuario", userTabbedPane);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainTabbedPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainTabbedPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JOptionPane.showConfirmDialog(null, "¡Cierre la sesión antes de cerrar la ventana!", 
                "No se puede cerrar la ventana",
                JOptionPane.DEFAULT_OPTION);
    }//GEN-LAST:event_formWindowClosing

    public static void closeSesion() {
        currentFrame.setVisible(false);
        currentFrame.dispose();

        welcomeWindow.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane addPatientTabbedPanel;
    private static javax.swing.JTabbedPane doctorTeamTabbedPanel;
    private static javax.swing.JTabbedPane mainTabbedPanel;
    private static javax.swing.JTabbedPane operationRoomTabbedPanel;
    private javax.swing.JTabbedPane pathologiesTabbedPanel;
    private javax.swing.JTabbedPane scheduleTabbedPanel;
    private javax.swing.JTabbedPane userTabbedPane;
    // End of variables declaration//GEN-END:variables

}
