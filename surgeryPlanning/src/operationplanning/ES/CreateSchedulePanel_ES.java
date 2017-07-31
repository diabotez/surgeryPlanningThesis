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
import javax.swing.JPanel;
import operationplanning.commonFiles.DatabaseQueries;
import operationplanning.commonFiles.MedicIdentifiers;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.OperationRooms;
import operationplanning.commonFiles.PatientIdentifiers;
import operationplanning.commonFiles.PatientsList;
import operationplanning.commonFiles.Utils;
import operationplanning.cplex.files.Opep;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class CreateSchedulePanel_ES extends JPanel {

    private static MedicalTeams medicalInst = new MedicalTeams();

    /**
     * Creates new form CrateSchedulePanel
     *
     * @param type
     */
    public CreateSchedulePanel_ES() {
        initComponents();

        updateMedicList();

        if (Planning_ES.currentUserType != Utils.UserType.HEAD_OF_DEPARTMENT) {
            generateAverageOccupancyRateButton.setEnabled(false);
            minimumConfidenceLevelFormattedTextField.setEnabled(false);
            minimumConfidenceLevelLabel.setEnabled(false);
        }
    }

    public static void updateMedicList() {
        if (medicComboBox == null) {
            return;
        }

        medicComboBox.removeAllItems();
        MedicalTeams medicalInst = new MedicalTeams();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            MedicIdentifiers coordinator = medicalInst.getCoordinator(Integer.parseInt(teamDetail.get(0).replace("TM", "")));
            if (coordinator == null) {
                continue;
            }
            medicComboBox.addItem(teamDetail.get(1) + " ( " + coordinator.lastName + " " + coordinator.firstName + " )");
        }
        medicComboBox.setSelectedIndex(0);
        dayOccupancyPercentageFormattedTextField1.setValue(new OperationRooms().getAverageOccupancyRate());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        numberOfDaysToScheduleFormattedTextField = new javax.swing.JFormattedTextField();
        scheduleButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        dayOccupancyPercentageFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        dayDeviacionFormattedTextField2 = new javax.swing.JFormattedTextField();
        medicComboBox = new javax.swing.JComboBox<>();
        generateAverageOccupancyRateButton = new javax.swing.JButton();
        minimumConfidenceLevelLabel = new javax.swing.JLabel();
        minimumConfidenceLevelFormattedTextField = new javax.swing.JFormattedTextField();

        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setText("Días para programar (mayor o igual a 1)");

        numberOfDaysToScheduleFormattedTextField.setText("6");

        scheduleButton.setText("Programar pacientes");
        scheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleButtonActionPerformed(evt);
            }
        });

        resultTextArea.setEditable(false);
        resultTextArea.setColumns(20);
        resultTextArea.setLineWrap(true);
        resultTextArea.setRows(5);
        jScrollPane2.setViewportView(resultTextArea);

        jLabel2.setText("Porcentaje ocupación objectivo");

        dayOccupancyPercentageFormattedTextField1.setText("80");

        jLabel3.setText("Porcentaje de desviacion aceptada");

        dayDeviacionFormattedTextField2.setText("15");

        medicComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicComboBoxActionPerformed(evt);
            }
        });

        generateAverageOccupancyRateButton.setText("Generar la tasa media de ocupación");
        generateAverageOccupancyRateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateAverageOccupancyRateButtonActionPerformed(evt);
            }
        });

        minimumConfidenceLevelLabel.setText("Nivel mínimo de confianza (51 - 99)");

        minimumConfidenceLevelFormattedTextField.setText("80");
        minimumConfidenceLevelFormattedTextField.setMinimumSize(new java.awt.Dimension(20, 27));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(scheduleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(medicComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(minimumConfidenceLevelLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(minimumConfidenceLevelFormattedTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(generateAverageOccupancyRateButton))
                            .addComponent(numberOfDaysToScheduleFormattedTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dayDeviacionFormattedTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dayOccupancyPercentageFormattedTextField1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(numberOfDaysToScheduleFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateAverageOccupancyRateButton)
                    .addComponent(minimumConfidenceLevelFormattedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimumConfidenceLevelLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dayOccupancyPercentageFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dayDeviacionFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scheduleButton)
                    .addComponent(medicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheduleButtonActionPerformed
        scheduleButton.setText("Cargando...");
        scheduleButton.setEnabled(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (null == medicComboBox.getSelectedItem()) {
                    return;
                }

                String item = medicComboBox.getSelectedItem().toString();
                String teamName = item.substring(0, item.indexOf('(')).trim();
                String teamId = new DatabaseQueries().getTeamIdByTeamName(teamName);
//
                MedicIdentifiers doctor = medicalInst.getCoordinator(Integer.parseInt(teamId.replace("TM", "")));
                Vector<PatientIdentifiers> patients;
                PatientsList patientInst = new PatientsList();

                if (doctor == null) {
                    JOptionPane.showConfirmDialog(null, "Doctor no se encuentra", "Médico desconocido", JOptionPane.DEFAULT_OPTION);
                    return;
                } else {
                    patients = patientInst.getDoctorOrTeamPatientList(doctor.IDnumber, true, false);
                }

                //set patient list
                Opep cplexObject = new Opep();
                cplexObject.setPatientList(patients);

                int availableDatesNo = new OperationRooms().getNumberOfOrAvailableDatesAssigned(teamId);
                int days = Integer.parseInt(numberOfDaysToScheduleFormattedTextField.getText());
                int dayPercentage = Integer.parseInt(dayOccupancyPercentageFormattedTextField1.getText());
                int dayDeviation = Integer.parseInt(dayDeviacionFormattedTextField2.getText());
                Vector<Vector<String>> availableDatesDetails = new DatabaseQueries().getAvailableDatesToScheduleForTeam(teamId);
                Vector<Vector<Integer>> scheduledPatients;

                if (availableDatesNo == 0) {
                    JOptionPane.showConfirmDialog(null, "No hay días disponibles para programar para el equipo seleccionado.",
                            "Error. No se puede crear el calendario",
                            JOptionPane.DEFAULT_OPTION);
                } else if (days == 0) {
                    JOptionPane.showConfirmDialog(null, "El número de días para programar debe ser mayor o igual que 1.",
                            "Error. No se puede crear el calendario",
                            JOptionPane.DEFAULT_OPTION);
                } else if (days > availableDatesNo) {
                    JOptionPane.showConfirmDialog(null, "El número máximo de fechas disponibles es " + availableDatesNo + ".",
                            "Eror. Número incorrecto de fechas para programar",
                            JOptionPane.DEFAULT_OPTION);
                    numberOfDaysToScheduleFormattedTextField.setText("" + availableDatesNo);
                } else if (days <= availableDatesNo) {
                    scheduledPatients = cplexObject.opep_main(days, dayPercentage, dayDeviation, 1, teamId);//create a new schedule
                    String message;
                    if (scheduledPatients != null && !scheduledPatients.isEmpty()) {
                        int index = 0;
                        message = "Los pacientes siguientes han sido programados: \n";
                        for (Vector<Integer> scheduledPatientsPerDay : scheduledPatients) {
                            message += availableDatesDetails.get(index).get(0) + " (";
                            message += availableDatesDetails.get(index).get(1) + "):\t";
                            for (Integer patientId : scheduledPatientsPerDay) {
                                message += patientId + "\t";
                            }
                            message += "\n";
                            index++;
                        }
                    } else {
                        message = "Ningún paciente ha sido programado\n";
                    }
                    resultTextArea.setText(message);
                }

                scheduleButton.setText("Programar pacientes");
                scheduleButton.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_scheduleButtonActionPerformed

    private void generateAverageOccupancyRateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateAverageOccupancyRateButtonActionPerformed
        int answer = JOptionPane.showOptionDialog(null, "Esta operación puede tardar un tiempo. "
                + "Usted puede hacer otras operaciones hasta que se haga.\n\n"
                + "¿Estás seguro de que quieres empezar a calcular el promedio?",
                "Cargando la tasa media de ocupación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);

        if (JOptionPane.YES_OPTION != answer) {
            return;
        }
        int confidenceLevel = Integer.parseInt(minimumConfidenceLevelFormattedTextField.getText());
        if (confidenceLevel < 51) {
            JOptionPane.showConfirmDialog(null,
                    "Nivel mínimo de confianza est demasiado bajo.",
                    "Eror. Nivel mínimo de confianza",
                    JOptionPane.DEFAULT_OPTION);
            return;
        }

        //ask for confidence level
        generateAverageOccupancyRateButton.setText("Cargando...");
        generateAverageOccupancyRateButton.setEnabled(false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                PatientsList patientsInst = new PatientsList();
                Vector<PatientIdentifiers> patients = patientsInst.getDoctorOrTeamPatientList(-1, false, false);

                //set patient list
                Opep cplexObject = new Opep();
                cplexObject.setPatientList(patients);

                double averageOccupancyRate;
                averageOccupancyRate = cplexObject.opep_generateOccupancyRate(confidenceLevel);//create a new schedule

                if (0 != averageOccupancyRate) {
                    dayOccupancyPercentageFormattedTextField1.setValue((int) averageOccupancyRate);
                    new OperationRooms().setAverageOccupancyRate((int) averageOccupancyRate);

                    Object[] obj = {"OK"};
                    JOptionPane.showOptionDialog(null, "Se generó la tasa media de ocupación."
                            + "Puede encontrarlo en \"Tasa de ocupación deseada\" archivada.",
                            "Average occupancy rate computed",
                            JOptionPane.OK_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, obj, JOptionPane.OK_OPTION);
                } else {
                    JOptionPane.showConfirmDialog(null,
                            "La tasa media de ocupación no se podido generar.",
                            "Eror. Tasa media de ocupación",
                            JOptionPane.DEFAULT_OPTION);
                }

                generateAverageOccupancyRateButton.setText("Generar la tasa media de ocupación");
                generateAverageOccupancyRateButton.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_generateAverageOccupancyRateButtonActionPerformed

    private void medicComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicComboBoxActionPerformed
        if (medicComboBox == null || medicComboBox.getSelectedIndex() == -1) {
            return;
        }

        String item = medicComboBox.getSelectedItem().toString();
        String teamName = item.substring(0, item.indexOf('(')).trim();
        String teamId = new DatabaseQueries().getTeamIdByTeamName(teamName);

        int availableDatesNo = new OperationRooms().getNumberOfOrAvailableDatesAssigned(teamId);

        numberOfDaysToScheduleFormattedTextField.setValue(availableDatesNo);
    }//GEN-LAST:event_medicComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField dayDeviacionFormattedTextField2;
    private static javax.swing.JFormattedTextField dayOccupancyPercentageFormattedTextField1;
    private javax.swing.JButton generateAverageOccupancyRateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JComboBox<String> medicComboBox;
    private static javax.swing.JFormattedTextField minimumConfidenceLevelFormattedTextField;
    private javax.swing.JLabel minimumConfidenceLevelLabel;
    private javax.swing.JFormattedTextField numberOfDaysToScheduleFormattedTextField;
    public static javax.swing.JTextArea resultTextArea;
    private javax.swing.JButton scheduleButton;
    // End of variables declaration//GEN-END:variables
}
