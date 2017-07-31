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
public class SeePatientListPanel_ES extends javax.swing.JPanel {

    private static MyUneditableTableModel patientListTableModel_ES;
    private static MyUneditableTableModel patientDetailsTableModel_ES;

    private static MedicalTeams medicalInst = new MedicalTeams();

    /**
     * Creates new form AddPatientListPanelES
     */
    public SeePatientListPanel_ES() {
        // <editor-fold defaultstate="collapsed" desc="Initialize variables">
        patientListTableModel_ES = new MyUneditableTableModel(
                new String[]{"ID del paciente", "Apellido", "Nombre"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                return java.lang.String.class;
            }
        } //</editor-fold> //</editor-fold>
                ;

        patientDetailsTableModel_ES = new MyUneditableTableModel(
                new String[]{"Índice", "Cirugía", "Patología", "Doctor", "Coordinador", "Fecha de admisión", "Fecha programado", "Programado", "Terminado"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                String name = patientDetailsTableModel_ES.getColumnName(col);

                if (name.equals("Fecha de admisión") || name.equals("Fecha programado")) {
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

        TableRowSorter<MyUneditableTableModel> sorter = new TableRowSorter<>(patientListTableModel_ES);
        patientListTable.setRowSorter(sorter);
        sorter.setSortable(0, false);
        //</editor-fold>
    }

    public static void updateMedicList() {
        if (medicComboBox == null) {
            return;
        }

        medicComboBox.removeAllItems();
//        medicComboBox.addItem("Todos los pacientes");
        MedicalTeams medicalInst = new MedicalTeams();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            MedicIdentifiers coordinator = medicalInst.getCoordinator(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            if (coordinator == null) {
                continue;
            }
            medicComboBox.addItem("Equipo " + coordinator.lastName + " " + coordinator.firstName);
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
            JOptionPane.showConfirmDialog(null, "Doctor no se encuentra", "Médico desconocido", JOptionPane.DEFAULT_OPTION);
            return;
        } else {
            patients = patientInst.getDoctorOrTeamPatientList(doctorID, teamList, true);
        }
        while (patientListTableModel_ES.getRowCount() > 0) {
            patientListTableModel_ES.removeRow(0);
        }

        for (PatientIdentifiers patient : patients) {
            patientListTableModel_ES.addRow(new Object[]{patient.patientID, patient.lastName, patient.firstName});
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

        patientListTable.setModel(patientListTableModel_ES);
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

        medicLabel.setText("Medico");

        medicComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicComboBoxActionPerformed(evt);
            }
        });

        patientNameLabel.setText("Nombre del paciente");

        patientIDLabel.setText("ID del paciente");

        patientIDTextField.setEditable(false);

        observationLabel.setText("Observaciones");

        observationsTextArea.setEditable(false);
        observationsTextArea.setColumns(20);
        observationsTextArea.setRows(5);
        jScrollPane3.setViewportView(observationsTextArea);

        javax.swing.GroupLayout upperPannelLayout = new javax.swing.GroupLayout(upperPannel);
        upperPannel.setLayout(upperPannelLayout);
        upperPannelLayout.setHorizontalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upperPannelLayout.createSequentialGroup()
                .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(upperPannelLayout.createSequentialGroup()
                        .addComponent(medicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(medicComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(upperPannelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(patientNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(patientNameTextField)
                            .addComponent(jScrollPane3)
                            .addComponent(patientIDTextField)
                            .addGroup(upperPannelLayout.createSequentialGroup()
                                .addGroup(upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(patientIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(observationLabel))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
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
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(upperPannel);

        lowerPannel.setMinimumSize(new java.awt.Dimension(820, 100));
        lowerPannel.setPreferredSize(new java.awt.Dimension(820, 250));

        patientDetailsTable.setModel(patientDetailsTableModel_ES);
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

    private void patientListTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_patientListTableMouseClicked
        int row = patientListTable.getSelectedRow();
        if (-1 == row) {
            patientNameTextField.setText("");
            patientIDTextField.setText("");
            observationsTextArea.setText("");

            while (patientDetailsTableModel_ES.getRowCount() > 0) {
                patientDetailsTableModel_ES.removeRow(0);
            }
            return;
        }

        int columnID = 0;
        for (int i = 0; i < patientListTableModel_ES.getColumnCount(); i++) {
            if ("ID del paciente".equals(patientListTableModel_ES.getColumnName(i))) {
                columnID = i;
                break;
            }
        }

        try {
            int patientID = (int) patientListTableModel_ES.getValueAt(row, columnID);
            PatientsList patientInst = new PatientsList();
            PatientIdentifiers patient = patientInst.getPatientWithID(patientID, true);
            if (patient == null) {
                //patient not found
                return;
            }

            patientNameTextField.setText(patient.lastName + " " + patient.firstName);
            patientIDTextField.setText("" + patient.patientID);
            observationsTextArea.setText(patient.observations);

            while (patientDetailsTableModel_ES.getRowCount() > 0) {
                patientDetailsTableModel_ES.removeRow(0);
            }
            int surgeriesNo = patient.medicalHistory.surgery.size();

            for (int i = 0; i < surgeriesNo; i++) {
                MedicIdentifiers doctorInCharge = patient.medicalHistory.doctorInCharge.get(i);
                MedicIdentifiers doctorTeamLeader = patient.medicalHistory.doctorTeamLeader.get(i);

                if (patient.medicalHistory.scheduled.get(i)) {
                    patientDetailsTableModel_ES.addRow(new Object[]{i + 1,
                        patient.medicalHistory.surgery.get(i),
                        patient.medicalHistory.pathology.get(i),
                        doctorInCharge.lastName + " " + doctorInCharge.firstName,
                        doctorTeamLeader.lastName + " " + doctorTeamLeader.firstName,
                        patient.medicalHistory.admissionDate.get(i),
                        patient.medicalHistory.scheduledDate.get(i),
                        (patient.medicalHistory.scheduled.get(i) ? "SI" : "NO"),
                        (patient.medicalHistory.complited.get(i) ? "SI" : "NO")});
                } else {
                    patientDetailsTableModel_ES.addRow(new Object[]{i + 1,
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

    private void medicComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicComboBoxActionPerformed
        if (null == medicComboBox.getSelectedItem()) {
            return;
        }

        String item = medicComboBox.getSelectedItem().toString();

        MedicIdentifiers doctor = medicalInst.getDoctorByFullName(item);

        // if the doctor is not found, show the whole list of patients
        if (doctor == null) {
            JOptionPane.showConfirmDialog(null, "Doctor no se encuentra", "Médico desconocido", JOptionPane.DEFAULT_OPTION);
            return;
//            updatePatientListTable(-1, false);
        } else {
            updatePatientListTable(doctor.IDnumber, item.contains("Equipo"));
        }
        patientListTableMouseClicked(null);
    }//GEN-LAST:event_medicComboBoxActionPerformed

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
