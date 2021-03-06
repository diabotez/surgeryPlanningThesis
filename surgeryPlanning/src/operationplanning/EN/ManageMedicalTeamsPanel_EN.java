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

import java.util.Vector;
import javax.swing.JOptionPane;
import operationplanning.commonFiles.MedicIdentifiers;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.Utils;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class ManageMedicalTeamsPanel_EN extends javax.swing.JPanel {

    /**
     * Creates new form addNewMedicalTeamPanel
     *
     * @param userType
     */
    public ManageMedicalTeamsPanel_EN() {

        initComponents();

        if (Planning_EN.currentUserType.equals(Utils.UserType.HEAD_OF_DEPARTMENT)) {
            addNewTeamCheckBox.setSelected(false);
            addNewTeamCheckBoxActionPerformed(null);
        } else {
            addNewTeamCheckBox.setSelected(false);
            addNewTeamCheckBoxActionPerformed(null);
            changeMedicalLeadButton.setEnabled(false);
            addNewMedicButton.setEnabled(false);
            addNewTeamCheckBox.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddNewTeamButton = new javax.swing.JButton();
        coordinatorLastNameLabel = new javax.swing.JLabel();
        coordinatorFirstNameLabel = new javax.swing.JLabel();
        coordinatorDepartmentLabel = new javax.swing.JLabel();
        coordinatorLastNameTextField = new javax.swing.JTextField();
        coordinatorFirstNameTextField = new javax.swing.JTextField();
        coordinatorDepartmentTextField = new javax.swing.JTextField();
        resetButton = new javax.swing.JButton();
        changeMedicalLeadButton = new javax.swing.JButton();
        addNewMedicButton = new javax.swing.JButton();
        teamNameLabel = new javax.swing.JLabel();
        teamNameTextField = new javax.swing.JTextField();
        addNewTeamCheckBox = new javax.swing.JCheckBox();

        AddNewTeamButton.setText("Add new team");
        AddNewTeamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewTeamButtonActionPerformed(evt);
            }
        });

        coordinatorLastNameLabel.setText("Coordinator's last name");

        coordinatorFirstNameLabel.setText("Coordinator's first name");

        coordinatorDepartmentLabel.setText("Coordinator's department");

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        changeMedicalLeadButton.setText("Change medical lead");
        changeMedicalLeadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeMedicalLeadButtonActionPerformed(evt);
            }
        });

        addNewMedicButton.setText("Add new medic to existing team");
        addNewMedicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewMedicButtonActionPerformed(evt);
            }
        });

        teamNameLabel.setText("Team name");

        addNewTeamCheckBox.setText("Add new medical team");
        addNewTeamCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewTeamCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(teamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(coordinatorDepartmentLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(coordinatorFirstNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(coordinatorLastNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AddNewTeamButton))
                            .addComponent(coordinatorLastNameTextField)
                            .addComponent(coordinatorFirstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                            .addComponent(coordinatorDepartmentTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                            .addComponent(teamNameTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(changeMedicalLeadButton)
                                .addGap(100, 100, 100)
                                .addComponent(addNewMedicButton))
                            .addComponent(addNewTeamCheckBox))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addNewMedicButton)
                    .addComponent(changeMedicalLeadButton))
                .addGap(18, 18, 18)
                .addComponent(addNewTeamCheckBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordinatorLastNameLabel)
                    .addComponent(coordinatorLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordinatorFirstNameLabel)
                    .addComponent(coordinatorFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordinatorDepartmentLabel)
                    .addComponent(coordinatorDepartmentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teamNameLabel)
                    .addComponent(teamNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(AddNewTeamButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AddNewTeamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewTeamButtonActionPerformed
        String coordinatorLastName = coordinatorLastNameTextField.getText();
        String coordinatorFirstName = coordinatorFirstNameTextField.getText();
        String coordinatorDepartment = coordinatorDepartmentTextField.getText();
        String teamName = teamNameTextField.getText();

        if ("".equals(coordinatorLastName) || "".equals(coordinatorFirstName)
                || "".equals(coordinatorDepartment) || "".equals(teamName)) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(this, "One or more fields are empty. Fill them and try again.",
                    "Error adding a new medical team",
                    JOptionPane.OK_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }
            
        MedicalTeams medicalInst = new MedicalTeams();
        int coordinatorID = Integer.parseInt(medicalInst.generateDoctorId().replace("D", ""));
        MedicIdentifiers coordinator = new MedicIdentifiers(coordinatorLastName, coordinatorFirstName, coordinatorDepartment, coordinatorID, coordinatorID);

        //check data
        if (-1 != medicalInst.getTeamIndexWithThisCoordinator(coordinator)) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(this, "This coordinator already exists as a coordinator. He cannot form another team.",
                    "Error - Coordinator",
                    JOptionPane.OK_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        } else if (medicalInst.existsTeamWithThisMember(coordinator)) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(this, "This coordinator already exists as a as a member in a team. He cannot be in another team.",
                    "Error - Coordinator",
                    JOptionPane.OK_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        Object[] o = {"Add", "Cancel"};
        int option = JOptionPane.showOptionDialog(this, "You are about to add a new medical team with the following data:\n"
                + "\ncoordinator's last name - " + coordinatorLastName
                + "\ncoordinator's first name - " + coordinatorFirstName
                + "\ncoordinator's department - " + coordinatorDepartment
                + "\nteam name - " + teamName
                + "\n\nAre you sure you want to add a new medical team with these members?",
                "Adding a new medical team",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, o, o[1]);

        if (option == 1 || option == JOptionPane.CLOSED_OPTION) {
            return;
        }

        // add the new team in the data base
        medicalInst.addNewMedicalTeam(coordinator, teamName);

        //add new medical team
        Planning_EN.addNewMedicalTeamPannel();
        ManageDoctorsPanel_EN.updateMedicList();
        SeePatientListPanel_EN.updateMedicList();
        CreateSchedulePanel_EN.updateMedicList();
        UpdatePatientPanel_EN.refreshData();
        AddNewMedicWindow_EN.updateMedicalLeadComboBox();
        Planning_EN.updateOperationRoomTabbedPanne();

        resetButtonActionPerformed(null);
    }//GEN-LAST:event_AddNewTeamButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        coordinatorLastNameTextField.setText("");
        coordinatorFirstNameTextField.setText("");
        coordinatorDepartmentTextField.setText("");
        teamNameTextField.setText("");
    }//GEN-LAST:event_resetButtonActionPerformed

    private void changeMedicalLeadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeMedicalLeadButtonActionPerformed
        MedicalTeams medicalInst = new MedicalTeams();
        Vector<MedicIdentifiers> medicalLeaders = medicalInst.getAllTeamLeaders();

        StringBuilder strBuildLeaders = new StringBuilder(255);
        for (MedicIdentifiers medicalLeader : medicalLeaders) {
            strBuildLeaders.append("\n");
            strBuildLeaders.append(medicalLeader.IDnumber);
            strBuildLeaders.append(" - ");
            strBuildLeaders.append(medicalLeader.lastName);
            strBuildLeaders.append(" ");
            strBuildLeaders.append(medicalLeader.firstName);
        }

        String answer = JOptionPane.showInputDialog(null, "Write the ID of the medical leader to be changed \n" + strBuildLeaders);
        if (answer == null || answer.equals("")) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "No coordinator selected.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        MedicIdentifiers currentLeader = null;
        for (MedicIdentifiers medicalLeader : medicalLeaders) {
            if (answer.equals("" + medicalLeader.IDnumber)) {
                currentLeader = medicalLeader;
                break;
            }
        }
        if (currentLeader == null) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Invalid response for the coordinator's ID.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        Vector<MedicIdentifiers> medicalTeam = medicalInst.getMedicalTeamByTeamId(medicalInst.getTeamIndexWithThisCoordinator(currentLeader));
        strBuildLeaders = new StringBuilder(255);
        for (MedicIdentifiers member : medicalTeam) {
            strBuildLeaders.append("\n");
            strBuildLeaders.append(member.IDnumber);
            strBuildLeaders.append(" - ");
            strBuildLeaders.append(member.lastName);
            strBuildLeaders.append(" ");
            strBuildLeaders.append(member.firstName);
        }

        answer = JOptionPane.showInputDialog(null, "Write the ID of the new medical leader \n" + strBuildLeaders);
        if (answer == null || answer.equals("")) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "No medic selected.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        MedicIdentifiers newLeader = null;
        for (MedicIdentifiers member : medicalTeam) {
            if (answer.equals("" + member.IDnumber)) {
                newLeader = member;
                break;
            }
        }
        if (newLeader == null) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Invalid ID for the new coordinator.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        medicalInst.changeTeamCoordinator(newLeader, currentLeader);

        Planning_EN.refreshMedicalTeamTabbedPannel();
        Planning_EN.updateOperationRoomTabbedPanne();
        SeePatientListPanel_EN.updateMedicList();
        CreateSchedulePanel_EN.updateMedicList();
        AddNewMedicWindow_EN.updateMedicalLeadComboBox();
        ManageDoctorsPanel_EN.updateMedicList();

        Object[] obj = {"OK"};
        JOptionPane.showOptionDialog(null, newLeader.lastName + " " + newLeader.firstName + " is now the leader of "
                + medicalInst.getTeamNameByTeamLeaderId(newLeader.IDnumber),
                "Coordinator changed",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, obj, JOptionPane.OK_OPTION);
    }//GEN-LAST:event_changeMedicalLeadButtonActionPerformed

    private void addNewMedicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewMedicButtonActionPerformed
        AddNewMedicWindow_EN window = new AddNewMedicWindow_EN();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }//GEN-LAST:event_addNewMedicButtonActionPerformed

    private void addNewTeamCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewTeamCheckBoxActionPerformed
        if (addNewTeamCheckBox.isSelected()) {
            coordinatorDepartmentLabel.setEnabled(true);
            coordinatorDepartmentTextField.setEnabled(true);
            coordinatorFirstNameLabel.setEnabled(true);
            coordinatorFirstNameTextField.setEnabled(true);
            coordinatorLastNameLabel.setEnabled(true);
            coordinatorLastNameTextField.setEnabled(true);
            teamNameLabel.setEnabled(true);
            teamNameTextField.setEnabled(true);
            resetButton.setEnabled(true);
            AddNewTeamButton.setEnabled(true);
            changeMedicalLeadButton.setEnabled(false);
            addNewMedicButton.setEnabled(false);
        } else {
            coordinatorDepartmentLabel.setEnabled(false);
            coordinatorDepartmentTextField.setEnabled(false);
            coordinatorFirstNameLabel.setEnabled(false);
            coordinatorFirstNameTextField.setEnabled(false);
            coordinatorLastNameLabel.setEnabled(false);
            coordinatorLastNameTextField.setEnabled(false);
            teamNameLabel.setEnabled(false);
            teamNameTextField.setEnabled(false);
            resetButton.setEnabled(false);
            AddNewTeamButton.setEnabled(false);
            changeMedicalLeadButton.setEnabled(true);
            addNewMedicButton.setEnabled(true);
        }
    }//GEN-LAST:event_addNewTeamCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddNewTeamButton;
    private javax.swing.JButton addNewMedicButton;
    private javax.swing.JCheckBox addNewTeamCheckBox;
    private javax.swing.JButton changeMedicalLeadButton;
    private javax.swing.JLabel coordinatorDepartmentLabel;
    private javax.swing.JTextField coordinatorDepartmentTextField;
    private javax.swing.JLabel coordinatorFirstNameLabel;
    private javax.swing.JTextField coordinatorFirstNameTextField;
    private javax.swing.JLabel coordinatorLastNameLabel;
    private javax.swing.JTextField coordinatorLastNameTextField;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel teamNameLabel;
    private javax.swing.JTextField teamNameTextField;
    // End of variables declaration//GEN-END:variables
}
