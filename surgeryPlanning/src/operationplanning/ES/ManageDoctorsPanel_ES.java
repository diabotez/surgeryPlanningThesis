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

import java.util.Vector;
import javax.swing.JOptionPane;
import operationplanning.commonFiles.MedicIdentifiers;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.OperationRooms;
import operationplanning.commonFiles.Utils;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class ManageDoctorsPanel_ES extends javax.swing.JPanel {

    private static MedicalTeams medicalInst = new MedicalTeams();

    /**
     * Creates new form AddPatientListPanelES
     *
     * @param userType
     */
    public ManageDoctorsPanel_ES() {
        initComponents();

        // <editor-fold defaultstate="collapsed" desc="Update the datas on combo box">
        updateMedicList();
        //</editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Enable/Disable actions for current user">
        if (Planning_ES.currentUserType != Utils.UserType.HEAD_OF_DEPARTMENT) {
            removeDoctorButton.setEnabled(false);
            moveMedicButton.setEnabled(false);
        }
        //</editor-fold>
    }

    public static void updateMedicList() {
        medicComboBox.removeAllItems();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            MedicIdentifiers coordinator = medicalInst.getCoordinator(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            if (coordinator == null) {
                continue;
            }
            medicComboBox.addItem(coordinator.lastName + " " + coordinator.firstName);

            Vector<MedicIdentifiers> team = medicalInst.getMedicalTeamByTeamId(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            for (MedicIdentifiers doctor : team) {
                medicComboBox.addItem(doctor.lastName + " " + doctor.firstName);
            }
        }

        medicComboBox.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        medicLabel = new javax.swing.JLabel();
        medicComboBox = new javax.swing.JComboBox<>();
        medicLastNameLabel = new javax.swing.JLabel();
        medicLastNameTextField = new javax.swing.JTextField();
        medicFirstNameLabel = new javax.swing.JLabel();
        medicFirstNameTextField = new javax.swing.JTextField();
        medicDepartmentLabel = new javax.swing.JLabel();
        medicDepartmentTextField = new javax.swing.JTextField();
        medicIDLabel = new javax.swing.JLabel();
        medicIDFormattedTextField = new javax.swing.JFormattedTextField();
        coordinatorIDLabel = new javax.swing.JLabel();
        coordinatorIDFormattedTextField = new javax.swing.JFormattedTextField();
        coordinatorFirstNameLabel = new javax.swing.JLabel();
        coordinatorLastNameLabel = new javax.swing.JLabel();
        coordinatorLastNameTextField = new javax.swing.JTextField();
        coordinatorFirstNameTextField = new javax.swing.JTextField();
        removeDoctorButton = new javax.swing.JButton();
        moveMedicButton = new javax.swing.JButton();
        coordinatorTeamNameLabel = new javax.swing.JLabel();
        coordinatorTeamNameTextField = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(700, 500));

        medicLabel.setText("Médico");

        medicComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicComboBoxActionPerformed(evt);
            }
        });

        medicLastNameLabel.setText("Apellido del médico");

        medicLastNameTextField.setEditable(false);

        medicFirstNameLabel.setText("Nombre del médico");

        medicFirstNameTextField.setEditable(false);

        medicDepartmentLabel.setText("Departamento del médico");

        medicDepartmentTextField.setEditable(false);

        medicIDLabel.setText("ID del médico");

        medicIDFormattedTextField.setEditable(false);
        medicIDFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        coordinatorIDLabel.setText("ID del coordinador");

        coordinatorIDFormattedTextField.setEditable(false);
        coordinatorIDFormattedTextField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        coordinatorFirstNameLabel.setText("Nombre del coordinador");

        coordinatorLastNameLabel.setText("Apellido del coordinador");

        coordinatorLastNameTextField.setEditable(false);

        coordinatorFirstNameTextField.setEditable(false);

        removeDoctorButton.setText("Borrar este médico");
        removeDoctorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDoctorButtonActionPerformed(evt);
            }
        });

        moveMedicButton.setText("Mover este médico en otro equipo");
        moveMedicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveMedicButtonActionPerformed(evt);
            }
        });

        coordinatorTeamNameLabel.setText("Equipó del coordinador");

        coordinatorTeamNameTextField.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(medicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(medicComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(coordinatorTeamNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(coordinatorIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(medicIDLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(medicDepartmentLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(medicFirstNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(medicLastNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(coordinatorLastNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(coordinatorFirstNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(removeDoctorButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                .addComponent(moveMedicButton))
                            .addComponent(medicLastNameTextField)
                            .addComponent(medicDepartmentTextField)
                            .addComponent(medicFirstNameTextField)
                            .addComponent(medicIDFormattedTextField)
                            .addComponent(coordinatorIDFormattedTextField)
                            .addComponent(coordinatorLastNameTextField)
                            .addComponent(coordinatorFirstNameTextField)
                            .addComponent(coordinatorTeamNameTextField))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(medicLabel))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicIDLabel)
                    .addComponent(medicIDFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicLastNameLabel)
                    .addComponent(medicLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicFirstNameLabel)
                    .addComponent(medicFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicDepartmentLabel)
                    .addComponent(medicDepartmentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordinatorIDLabel)
                    .addComponent(coordinatorIDFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(coordinatorTeamNameLabel)
                    .addComponent(coordinatorTeamNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(removeDoctorButton)
                    .addComponent(moveMedicButton))
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void medicComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicComboBoxActionPerformed
        if (null == medicComboBox.getSelectedItem()) {
            return;
        }

        String item = medicComboBox.getSelectedItem().toString();
        MedicIdentifiers medic = medicalInst.getDoctorByFullName(item);
        if (null == medic) {
            return;
        }

        MedicIdentifiers coordinator = medicalInst.getDoctorByID(medic.coordinatorID);

        medicIDFormattedTextField.setValue(medic.IDnumber);
        medicLastNameTextField.setText(medic.lastName);
        medicFirstNameTextField.setText(medic.firstName);
        medicDepartmentTextField.setText(medic.department);

        coordinatorIDFormattedTextField.setValue(medic.coordinatorID);
        coordinatorLastNameTextField.setText(coordinator.lastName);
        coordinatorFirstNameTextField.setText(coordinator.firstName);
        coordinatorTeamNameTextField.setText(medicalInst.getTeamNameByTeamLeaderId(coordinator.IDnumber));

    }//GEN-LAST:event_medicComboBoxActionPerformed

    private void removeDoctorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDoctorButtonActionPerformed
        if (null == medicComboBox.getSelectedItem()) {
            return;
        }

        String item = medicComboBox.getSelectedItem().toString();
        MedicIdentifiers medic = medicalInst.getDoctorByFullName(item);
        if (null == medic) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Medic no encontrado.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        if (medicIDFormattedTextField.getText().equals(coordinatorIDFormattedTextField.getText())) {
            /* This medic is a coordinator*/
//            int teamIndex = medicalInst.getTeamIndexWithThisCoordinator(medic);
//            Vector<MedicIdentifiers> team = medicalInst.getMedicalTeamByTeamId(teamIndex);

//            if (!team.isEmpty()) {
            /*This coordinator has members in his team. He cannot be deleted*/
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Este médico es el coordinador de un equipo. "
                    + "No puede ser removido a menos que otro médico tome su lugar como coordinador para el equipo."
                    + "\n\nPara establecer otro coordinador para su equipo presione el botón \"Cambiar el plomo médico\".",
                    "Error. El médico no puede ser removido.",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
//            } 
//        else {
//                /* This coordinator is the only member of his team. Removing him means removing the medical team also.*/
//                int answer = JOptionPane.showOptionDialog(null, medic.lastName + " " + medic.firstName + " with ID: " + medic.IDnumber
//                        + "\nes el coordinador de " + medicalInst.getTeamNameByTeamLeaderId(medic.IDnumber)
//                        + "\n\nSi elimina este médico, también elimina el equipo y todas las fechas de reserva del equipo del quirófano."
//                        + "\n¿Seguro que quieres borrar a este médico y su equipo de la base de datos?",
//                        "Confirmación de retirada de médico y equipo",
//                        JOptionPane.YES_NO_OPTION,
//                        JOptionPane.WARNING_MESSAGE, null, null, JOptionPane.NO_OPTION);
//
//                if (answer == JOptionPane.OK_OPTION) {
//                    /* Remove medic and team from dataBase */
//                    medicalInst.removeMedicFromDatabase(medic);
//                    Planning_ES.removeMedicalTeamByTeamId(teamIndex);
//                    new OperationRooms().removeRecordsForTeam(teamIndex); //free OT records for team
//                    Planning_ES.updateOperationRoomTabbedPanne();
//                    SeePatientListPanel_ES.updateMedicList();
//                    CreateSchedulePanel_ES.updateMedicList();
//                    updateMedicList();
//                }
//            }
        } else {
            /* This is a medic in a team. He can be removed with no condition*/
            Object[] obj = {"Si", "No"};
            int answer = JOptionPane.showOptionDialog(null, "¿Está seguro de que desea eliminar el médico: "
                    + medic.lastName + " " + medic.firstName + " con ID: " + medic.IDnumber + "?"
                    + "\n\nSi este médico tiene algún paciente, todos sus pacientes serán transferidos a su coordinador !!",
                    "Confirmación de eliminación de médico",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, obj, obj[1]);
            if (answer == 0) {
                //remove medic from dataBase
                medicalInst.removeMedicFromDatabase(medic);
                SeePatientListPanel_ES.updateMedicList();
                CreateSchedulePanel_ES.updateMedicList();
                updateMedicList();
                Planning_ES.refreshMedicalTeamTabbedPannel();
                Planning_ES.updateOperationRoomTabbedPanne();
            }
        }
    }//GEN-LAST:event_removeDoctorButtonActionPerformed

    private void moveMedicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveMedicButtonActionPerformed
        if (null == medicComboBox.getSelectedItem()) {
            return;
        }

        String item = medicComboBox.getSelectedItem().toString();
        MedicIdentifiers medic = medicalInst.getDoctorByFullName(item);
        if (null == medic) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Este médico no se encuentra en la base de datos.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        int teamIndex = -1;
        if (medicIDFormattedTextField.getText().equals(coordinatorIDFormattedTextField.getText())) {
            teamIndex = medicalInst.getTeamIndexWithThisCoordinator(medic);
            Vector<MedicIdentifiers> team = medicalInst.getMedicalTeamByTeamId(teamIndex);

            if (!team.isEmpty()) {
                //this medic is a coordinator. He cannot be removed
                Object[] obj = {"OK"};
                JOptionPane.showOptionDialog(null, "Este médico es el coordinador de un equipo y no se puede moverse."
                        + "\n\nPara establecer otro coordinador vaya a la pestaña \"Gestionar equipo médico\".",
                        "Error. El médico no se puede moverse a otro equipo.",
                        JOptionPane.OK_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, obj, JOptionPane.OK_OPTION);
                return;
            } else {
                /* This coordinator is the only member of his team. Removing him means removing the medical team also.*/
                int answer = JOptionPane.showOptionDialog(null, medic.lastName + " " + medic.firstName + " with ID: " + medic.IDnumber
                        + "\nes el coordinador de " + medicalInst.getTeamNameByTeamLeaderId(medic.IDnumber)
                        + "\n\nSi mover este médico, estas elimindo el equipo y todas las fechas de reserva del equipo del quirófano."
                        + "\n¿Seguro que quieres mover a este médico y borrar su equipo de la base de datos?",
                        "Confirmación de mover de médico y retirada equipo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, null, JOptionPane.NO_OPTION);

                if (answer != JOptionPane.OK_OPTION) {
                    return;
                }
            }
        }

        Vector<MedicIdentifiers> medicalLeaders = medicalInst.getAllTeamLeaders();

        StringBuilder strBuildLeaders = new StringBuilder(255);
        for (MedicIdentifiers medicalLeader : medicalLeaders) {
            if (medicalLeader.IDnumber == medic.IDnumber) {
                continue;
            }
            strBuildLeaders.append("\n");
            strBuildLeaders.append(medicalLeader.IDnumber);
            strBuildLeaders.append(" - ");
            strBuildLeaders.append(medicalLeader.lastName);
            strBuildLeaders.append(" ");
            strBuildLeaders.append(medicalLeader.firstName);
        }

        String answer = JOptionPane.showInputDialog(null, "Escriba el ID del nuevo coordinador \n" + strBuildLeaders);
        if (answer == null || answer.equals("")) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "No se ha seleccionado ningún nuevo coordinador.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }
        MedicIdentifiers newLeader = null;
        for (MedicIdentifiers medicalLeader : medicalLeaders) {
            if (answer.equals("" + medicalLeader.IDnumber)) {
                newLeader = medicalLeader;
                break;
            }
        }
        if (newLeader == null) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Respuesta inválido para la nueva ID del coordinador.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        if (newLeader.IDnumber == medic.coordinatorID) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "Este médico ya está en el equipo del coordinador.",
                    "Error",
                    JOptionPane.OK_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, obj, JOptionPane.OK_OPTION);
            return;
        }

        medicalInst.moveMemberToOtherTeam(newLeader, medic);//Move medic
        if (teamIndex != -1) {
            /* Delete team from dataBase */
            Planning_ES.removeMedicalTeamByTeamId(teamIndex);
            new OperationRooms().removeRecordsForTeam(teamIndex); //delete OT records for team
        }

        Planning_ES.refreshMedicalTeamTabbedPannel();
        Planning_ES.updateOperationRoomTabbedPanne();
        SeePatientListPanel_ES.updateMedicList();
        CreateSchedulePanel_ES.updateMedicList();
        updateMedicList();

        Object[] obj = {"OK"};
        JOptionPane.showOptionDialog(null, medic.lastName + " " + medic.firstName + " ahora apartenese al equipo de "
                + "médico " + newLeader.lastName + " " + newLeader.firstName,
                "Médico trasladado",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, obj, JOptionPane.OK_OPTION);
    }//GEN-LAST:event_moveMedicButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel coordinatorFirstNameLabel;
    private javax.swing.JTextField coordinatorFirstNameTextField;
    private javax.swing.JFormattedTextField coordinatorIDFormattedTextField;
    private javax.swing.JLabel coordinatorIDLabel;
    private javax.swing.JLabel coordinatorLastNameLabel;
    private javax.swing.JTextField coordinatorLastNameTextField;
    private javax.swing.JLabel coordinatorTeamNameLabel;
    private javax.swing.JTextField coordinatorTeamNameTextField;
    private static javax.swing.JComboBox<String> medicComboBox;
    private javax.swing.JLabel medicDepartmentLabel;
    private javax.swing.JTextField medicDepartmentTextField;
    private javax.swing.JLabel medicFirstNameLabel;
    private javax.swing.JTextField medicFirstNameTextField;
    private javax.swing.JFormattedTextField medicIDFormattedTextField;
    private javax.swing.JLabel medicIDLabel;
    private javax.swing.JLabel medicLabel;
    private javax.swing.JLabel medicLastNameLabel;
    private javax.swing.JTextField medicLastNameTextField;
    private javax.swing.JButton moveMedicButton;
    private javax.swing.JButton removeDoctorButton;
    // End of variables declaration//GEN-END:variables
}
