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

import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import operationplanning.commonFiles.MedicIdentifiers;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.MyUneditableTableModel;
import operationplanning.commonFiles.PatientIdentifiers;
import operationplanning.commonFiles.PatientsList;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class SeePatientListPanel_EN extends javax.swing.JPanel {

    private static MyUneditableTableModel patientListTableModel_EN;
    private static MyUneditableTableModel patientDetailsTableModel_EN;

    private static MedicalTeams medicalInst = new MedicalTeams();

    /**
     * Creates new form AddPatientListPanelES
     */
    public SeePatientListPanel_EN() {
        // <editor-fold defaultstate="collapsed" desc="Initialize variables">
        patientListTableModel_EN = new MyUneditableTableModel(
                new String[]{"Patient ID", "Last name", "First name"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                return java.lang.String.class;
            }
        } //</editor-fold>
                ;

        patientDetailsTableModel_EN = new MyUneditableTableModel(
                new String[]{"Index", "Surgery", "Pathology", "Doctor", "Medical Leader", "Admission date", "Scheduled date", "Scheduled", "Completed"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                String name = patientDetailsTableModel_EN.getColumnName(col);

                if (name.equals("Admission date") || name.equals("Scheduled date")) {
                    return Date.class;
                } else {
                    return java.lang.String.class;
                }
            }
        } //</editor-fold>
                ;

        //</editor-fold>
        initComponents();

        // <editor-fold defaultstate="collapsed" desc="Update the datas on combo box">
        updateMedicList();
        medicComboBox.setSelectedIndex(0);

        TableRowSorter<MyUneditableTableModel> sorter = new TableRowSorter<>(patientListTableModel_EN);
        patientListTable.setRowSorter(sorter);
        sorter.setSortable(0, false);
        //</editor-fold>
    }

    public static void updateMedicList() {
//        medicalInst.updateMedicComboBox_EN(medicComboBox);
        if (medicComboBox == null) {
            return;
        }

        medicComboBox.removeAllItems();
//        medicComboBox.addItem("All patients");
        MedicalTeams medicalInst = new MedicalTeams();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            MedicIdentifiers coordinator = medicalInst.getCoordinator(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            if (coordinator == null) {
                continue;
            }
            medicComboBox.addItem("Team " + coordinator.lastName + " " + coordinator.firstName);
        }
        
        for (Vector<String> teamDetail : teamDetails) {
            MedicIdentifiers coordinator = medicalInst.getCoordinator(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            if (coordinator == null) {
                continue;
            }
            Vector<MedicIdentifiers> team = medicalInst.getMedicalTeamByTeamId(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            medicComboBox.addItem(coordinator.lastName + " " + coordinator.firstName);
            for (MedicIdentifiers member : team) {
                medicComboBox.addItem(member.lastName + " " + member.firstName);
            }
        }
        medicComboBox.setSelectedIndex(-1);
    }

    /**
     * Updates the information in the patient table. Use with doctorID == -1 if
     * the table needs to be filled with all the patients or a doctor's ID for
     * selecting a certain waiting list
     *
     * @param doctorID
     * @param teamList true if the list should have the patients from the
     * doctor's team and false if the list should have only the patients for the
     * given doctorID
     */
    public static void updatePatientListTable(int doctorID, boolean teamList) {
        PatientsList patientInst = new PatientsList();
        Vector<PatientIdentifiers> patients;
        if (doctorID == -1) {
            JOptionPane.showConfirmDialog(null, "Doctor not found", "Unknown doctor", JOptionPane.DEFAULT_OPTION);
            return;
        } else {
            patients = patientInst.getDoctorOrTeamPatientList(doctorID, teamList, true);
        }

        while (patientListTableModel_EN.getRowCount() > 0) {
            patientListTableModel_EN.removeRow(0);
        }

        for (PatientIdentifiers patient : patients) {
            patientListTableModel_EN.addRow(new Object[]{patient.patientID, patient.lastName, patient.firstName});
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

        jSplitPane1 = new javax.swing.JSplitPane();
        upperPannel = new javax.swing.JPanel();
        patientNameTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        patientListTable = new javax.swing.JTable();
        medicLabel = new javax.swing.JLabel();
        medicComboBox = new javax.swing.JComboBox<>();
        patientNameLabel = new javax.swing.JLabel();
        patientIDLabel = new javax.swing.JLabel();
        patientIDTextField = new javax.swing.JTextField();
        observationLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        observationsTextArea = new javax.swing.JTextArea();
        lowerPannel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        patientDetailsTable = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(820, 540));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(820, 540));

        jSplitPane1.setDividerLocation(288);
        jSplitPane1.setDividerSize(15);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSplitPane1.setPreferredSize(new java.awt.Dimension(820, 480));

        upperPannel.setMinimumSize(new java.awt.Dimension(820, 100));
        upperPannel.setPreferredSize(new java.awt.Dimension(820, 250));

        patientNameTextField.setEditable(false);

        patientListTable.setModel(patientListTableModel_EN);
        patientListTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                patientListTableMouseClicked(evt);
            }
        });
        patientListTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                patientListTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(patientListTable);

        medicLabel.setText("Medic");

        medicComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicComboBoxActionPerformed(evt);
            }
        });

        patientNameLabel.setText("Patient's name");

        patientIDLabel.setText("Patient's ID");

        patientIDTextField.setEditable(false);

        observationLabel.setText("Observations");

        observationsTextArea.setEditable(false);
        observationsTextArea.setColumns(20);
        observationsTextArea.setRows(5);
        jScrollPane3.setViewportView(observationsTextArea);

        javax.swing.GroupLayout upperPannelLayout = new javax.swing.GroupLayout(upperPannel);
        upperPannel.setLayout(upperPannelLayout);
        upperPannelLayout.setHorizontalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upperPannelLayout.createSequentialGroup()
                .addComponent(medicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(medicComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(upperPannelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(patientNameLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(upperPannelLayout.createSequentialGroup()
                        .addComponent(patientIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(upperPannelLayout.createSequentialGroup()
                        .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientIDTextField)
                            .addComponent(patientNameTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(upperPannelLayout.createSequentialGroup()
                                .addComponent(observationLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane3))
                        .addContainerGap())))
        );
        upperPannelLayout.setVerticalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upperPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(medicLabel))
                .addGap(18, 18, 18)
                .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(upperPannelLayout.createSequentialGroup()
                        .addComponent(patientIDLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(patientIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(patientNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(patientNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(observationLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
        );

        jSplitPane1.setLeftComponent(upperPannel);

        lowerPannel.setMinimumSize(new java.awt.Dimension(820, 100));
        lowerPannel.setPreferredSize(new java.awt.Dimension(820, 250));

        patientDetailsTable.setModel(patientDetailsTableModel_EN);
        jScrollPane2.setViewportView(patientDetailsTable);

        javax.swing.GroupLayout lowerPannelLayout = new javax.swing.GroupLayout(lowerPannel);
        lowerPannel.setLayout(lowerPannelLayout);
        lowerPannelLayout.setHorizontalGroup(
            lowerPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowerPannelLayout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        lowerPannelLayout.setVerticalGroup(
            lowerPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(lowerPannel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void medicComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicComboBoxActionPerformed
        if (null == medicComboBox.getSelectedItem()) {
            return;
        }

        String item = medicComboBox.getSelectedItem().toString();

        MedicIdentifiers doctor = medicalInst.getDoctorByFullName(item);

        // if the doctor is not found, don't show anything
        if (doctor == null) {
            JOptionPane.showConfirmDialog(null, "Doctor not found", "Unknown doctor", JOptionPane.DEFAULT_OPTION);
        } else {
            updatePatientListTable(doctor.IDnumber, item.contains("Team"));
        }
        patientListTableMouseClicked(null);
    }//GEN-LAST:event_medicComboBoxActionPerformed

    private void patientListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patientListTableMouseClicked
        int row = patientListTable.getSelectedRow();
        if (-1 == row){
            patientNameTextField.setText("");
            patientIDTextField.setText("");
            observationsTextArea.setText("");
            
            while (patientDetailsTableModel_EN.getRowCount() > 0) {
                patientDetailsTableModel_EN.removeRow(0);
            }
            return;
        }
        
        int columnID = 0;
        for (int i = 0; i < patientListTableModel_EN.getColumnCount(); i++) {
            if ("Patient ID".equals(patientListTableModel_EN.getColumnName(i))) {
                columnID = i;
                break;
            }
        }

        try {
            int patientID = (int)patientListTableModel_EN.getValueAt(row, columnID);
            PatientsList patientInst = new PatientsList();
            PatientIdentifiers patient = patientInst.getPatientWithID(patientID, true);
            if (patient == null) {
                //patient not found
                return;
            }

            patientNameTextField.setText(patient.lastName + " " + patient.firstName);
            patientIDTextField.setText("" + patient.patientID);
            observationsTextArea.setText("" + patient.observations);

            while (patientDetailsTableModel_EN.getRowCount() > 0) {
                patientDetailsTableModel_EN.removeRow(0);
            }
            int surgeriesNo = patient.medicalHistory.surgery.size();

            for (int i = 0; i < surgeriesNo; i++) {
                MedicIdentifiers doctorInCharge = patient.medicalHistory.doctorInCharge.get(i);
                MedicIdentifiers doctorTeamLeader = patient.medicalHistory.doctorTeamLeader.get(i);

                if (patient.medicalHistory.scheduled.get(i)) {
                    patientDetailsTableModel_EN.addRow(new Object[]{i + 1,
                        patient.medicalHistory.surgery.get(i),
                        patient.medicalHistory.pathology.get(i),
                        doctorInCharge.lastName + " " + doctorInCharge.firstName,
                        doctorTeamLeader.lastName + " " + doctorTeamLeader.firstName,
                        patient.medicalHistory.admissionDate.get(i),
                        patient.medicalHistory.scheduledDate.get(i),
                        (patient.medicalHistory.scheduled.get(i) ? "YES" : "NO"),
                        (patient.medicalHistory.complited.get(i) ? "YES" : "NO")});
                } else {
                    patientDetailsTableModel_EN.addRow(new Object[]{i + 1,
                        patient.medicalHistory.surgery.get(i),
                        patient.medicalHistory.pathology.get(i),
                        doctorInCharge.lastName + " " + doctorInCharge.firstName,
                        doctorTeamLeader.lastName + " " + doctorTeamLeader.firstName,
                        patient.medicalHistory.admissionDate.get(i),
                        null,
                        "NO",
                        "NO"});
                }
            }

        } catch (NumberFormatException e) {
            //selected column (with columnID) does not contain the ID for the patient
        }
    }//GEN-LAST:event_patientListTableMouseClicked

    private void patientListTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_patientListTableKeyReleased
        patientListTableMouseClicked(null);
    }//GEN-LAST:event_patientListTableKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel lowerPannel;
    private static javax.swing.JComboBox<String> medicComboBox;
    private javax.swing.JLabel medicLabel;
    private javax.swing.JLabel observationLabel;
    private javax.swing.JTextArea observationsTextArea;
    private javax.swing.JTable patientDetailsTable;
    private javax.swing.JLabel patientIDLabel;
    private javax.swing.JTextField patientIDTextField;
    private static javax.swing.JTable patientListTable;
    private javax.swing.JLabel patientNameLabel;
    private javax.swing.JTextField patientNameTextField;
    private javax.swing.JPanel upperPannel;
    // End of variables declaration//GEN-END:variables
}
