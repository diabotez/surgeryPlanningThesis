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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import operationplanning.commonFiles.DatabaseQueries;
import operationplanning.commonFiles.MyUneditableTableModel;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class SeeSchedulePanel_ES extends javax.swing.JPanel {

    private static MyUneditableTableModel patientDetailsTableModel_ES;

    /**
     * Creates new form ModifySchedulePanel_ES
     */
    public SeeSchedulePanel_ES() {
        patientDetailsTableModel_ES = new MyUneditableTableModel(
                new String[]{"Equipo", "Quirófano", "Id del Pacinete", "Nombre", "Apellido", "Cirugía",
                    "Patología", "Duración estimada [min]", "Nombre del médico", "Fecha de admisión"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                String name = patientDetailsTableModel_ES.getColumnName(col);

                if (name.equals("Fecha de admisión")) {
                    return Date.class;
                } else {
                    return java.lang.String.class;
                }
            }
        } //</editor-fold>
                ;

        initComponents();

        updateRoomOrTeamNameComboBox();

    }

    public void updateRoomOrTeamNameComboBox() {
        Vector<String> roomNames = new DatabaseQueries().getAllOrNames();
        Vector<String> teamNames = new DatabaseQueries().getAllTeamNames();

        if (roomNames == null) {
            operatingRoomNameComboBox.removeAllItems();
            operatingRoomNameComboBox.setSelectedIndex(-1);
        } else if (teamNames == null) {
            teamNameComboBox.removeAllItems();
            teamNameComboBox.setSelectedIndex(-1);
        } else {
            for (String name : roomNames) {
                operatingRoomNameComboBox.addItem(name);
            }
            operatingRoomNameComboBox.setSelectedIndex(-1);

            for (String name : teamNames) {
                teamNameComboBox.addItem(name);
            }
            teamNameComboBox.setSelectedIndex(-1);
        }

        while (patientDetailsTableModel_ES.getRowCount() > 0) {
            patientDetailsTableModel_ES.removeRow(0);
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

        scheduleDatePicker = new org.jdesktop.swingx.JXDatePicker();
        selectScheduleDateLabel = new javax.swing.JLabel();
        selectScheduleDateLabel1 = new javax.swing.JLabel();
        operatingRoomNameComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        ScheduleDetailsTable = new javax.swing.JTable();
        selectScheduleDateLabel2 = new javax.swing.JLabel();
        teamNameComboBox = new javax.swing.JComboBox<>();
        totalEstimatedTimePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalEstimatedTimeTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        totalDayTimeTextField = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(700, 530));

        scheduleDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleDatePickerActionPerformed(evt);
            }
        });

        selectScheduleDateLabel.setText("Seleccione la fecha programada:");

        selectScheduleDateLabel1.setText("Seleccionar quirófano:");

        operatingRoomNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operatingRoomNameComboBoxActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 300));

        ScheduleDetailsTable.setModel(patientDetailsTableModel_ES);
        jScrollPane1.setViewportView(ScheduleDetailsTable);

        selectScheduleDateLabel2.setText("OR Equipo:");

        teamNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teamNameComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Total estimated time :");

        totalEstimatedTimeTextField.setEditable(false);

        jLabel2.setText("Total day time :");

        totalDayTimeTextField.setEditable(false);

        javax.swing.GroupLayout totalEstimatedTimePanelLayout = new javax.swing.GroupLayout(totalEstimatedTimePanel);
        totalEstimatedTimePanel.setLayout(totalEstimatedTimePanelLayout);
        totalEstimatedTimePanelLayout.setHorizontalGroup(
            totalEstimatedTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalEstimatedTimePanelLayout.createSequentialGroup()
                .addGroup(totalEstimatedTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(totalEstimatedTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalDayTimeTextField)
                    .addComponent(totalEstimatedTimeTextField)))
        );
        totalEstimatedTimePanelLayout.setVerticalGroup(
            totalEstimatedTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, totalEstimatedTimePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(totalEstimatedTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalEstimatedTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(totalEstimatedTimePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalDayTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(selectScheduleDateLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(selectScheduleDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(scheduleDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(operatingRoomNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(selectScheduleDateLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(teamNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalEstimatedTimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectScheduleDateLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(operatingRoomNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectScheduleDateLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(teamNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scheduleDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectScheduleDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalEstimatedTimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scheduleDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheduleDatePickerActionPerformed
        if (operatingRoomNameComboBox == null || scheduleDatePicker == null
                || teamNameComboBox == null || scheduleDatePicker.getDate() == null) {
            return;
        }

        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DatabaseQueries db = new DatabaseQueries();
        Date selectedDate = scheduleDatePicker.getDate();
        String selectedDateString = outputFormatter.format(selectedDate);

        String teamId = null;
        String roomName = (String) operatingRoomNameComboBox.getSelectedItem();
        String teamName = (String) teamNameComboBox.getSelectedItem();
        if (roomName != null) {
            String roomId = db.getOrIdByName(roomName);
            teamId = db.getTeamIdForScheduledRoom(roomId, selectedDateString);
        } else if (teamName != null) {
            teamId = db.getTeamIdByTeamName(teamName);
        }
        if (teamId == null) {
            return;
        }

        Calendar calendar = new GregorianCalendar();
        String teamLeaderId = db.getTeamLeaderIdByTeamId(teamId);
        Vector<Vector<String>> scheduledPatientsDetails = db.getScheduledPatientsDetailsForTeam(teamLeaderId, selectedDateString);
        int totalDayTime = db.getTotalTimeForScheduledTeam(teamId, selectedDateString);

        while (patientDetailsTableModel_ES.getRowCount() > 0) {
            patientDetailsTableModel_ES.removeRow(0);
        }
        totalDayTimeTextField.setText("");
        totalEstimatedTimeTextField.setText("");
        if (scheduledPatientsDetails == null) {
            return;
        }

        if (teamName == null) {
            teamName = db.getTeamNameByTeamLeaderId(teamLeaderId);
        }
        if(roomName == null){
            roomName = db.getRoomNameForScheduledTeam(teamId, selectedDateString);
        }


        int estimatedTime = 0;
        for (Vector<String> patientDetails : scheduledPatientsDetails) {
            LocalDate d = LocalDate.parse(patientDetails.get(7));//
            calendar.set(d.getYear(), d.getMonthValue() - 1,
                    d.getDayOfMonth());
            estimatedTime += Integer.parseInt(patientDetails.get(5));
            patientDetailsTableModel_ES.addRow(new Object[]{teamName, roomName, patientDetails.get(0).replace("P", ""), patientDetails.get(1), patientDetails.get(2),
                patientDetails.get(3), patientDetails.get(4), patientDetails.get(5), patientDetails.get(6), calendar.getTime()});
        }
        totalEstimatedTimeTextField.setText("" + estimatedTime / 60 + " Horas, " + (estimatedTime % 60) + " minutos "
                + "(" + estimatedTime * 100 / totalDayTime + "%)");
        totalDayTimeTextField.setText("" + totalDayTime / 60 + " Horas, " + (totalDayTime % 60) + " minutos");
    }//GEN-LAST:event_scheduleDatePickerActionPerformed

    private void operatingRoomNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operatingRoomNameComboBoxActionPerformed
        if (operatingRoomNameComboBox == null || operatingRoomNameComboBox.getSelectedItem() == null) {
            return;
        }
        teamNameComboBox.setSelectedIndex(-1);
        while (patientDetailsTableModel_ES.getRowCount() > 0) {
            patientDetailsTableModel_ES.removeRow(0);
        }
        totalDayTimeTextField.setText("");
        totalEstimatedTimeTextField.setText("");
        scheduleDatePicker.setDate(null);

        DatabaseQueries db = new DatabaseQueries();
        String roomName = (String) operatingRoomNameComboBox.getSelectedItem();
        String roomId = db.getOrIdByName(roomName);
        Vector<String> dates = db.getScheduledDatesForOr(roomId);//from current date forward
        if (dates == null || dates.isEmpty()) {
            Date[] date = {new Date()};
            scheduleDatePicker.getMonthView().setUnselectableDates(date);
            scheduleDatePicker.getMonthView().setUpperBound(date[0]);
            return;
        }

        LocalDate lastLocalDate = LocalDate.parse(dates.lastElement());
        Calendar calendar = new GregorianCalendar();
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Vector<Date> datesToMakeUnselectable = new Vector<>();

        calendar.set(lastLocalDate.getYear(), lastLocalDate.getMonthValue() - 1,
                lastLocalDate.getDayOfMonth());
        Date lastDate = calendar.getTime();

        calendar.setTime(new Date()); // set start date = current date
        while (calendar.getTime().before(lastDate)) {
            Date aux = calendar.getTime();
            String output = outputFormatter.format(aux);
            if (!dates.contains(output)) {
                datesToMakeUnselectable.add(aux);
            }
            calendar.add(Calendar.DATE, 1);
        }

        Date[] datesToRemove = new Date[datesToMakeUnselectable.size()];
        datesToMakeUnselectable.toArray(datesToRemove);

        scheduleDatePicker.getMonthView().setUnselectableDates(datesToRemove);
        scheduleDatePicker.getMonthView().setUpperBound(lastDate);
        scheduleDatePicker.getMonthView().setLowerBound(new Date());
    }//GEN-LAST:event_operatingRoomNameComboBoxActionPerformed

    private void teamNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teamNameComboBoxActionPerformed
        if (teamNameComboBox == null || teamNameComboBox.getSelectedItem() == null) {
            return;
        }
        operatingRoomNameComboBox.setSelectedIndex(-1);
        while (patientDetailsTableModel_ES.getRowCount() > 0) {
            patientDetailsTableModel_ES.removeRow(0);
        }
        totalDayTimeTextField.setText("");
        totalEstimatedTimeTextField.setText("");
        scheduleDatePicker.setDate(null);

        DatabaseQueries db = new DatabaseQueries();
        String teamName = (String) teamNameComboBox.getSelectedItem();
        String teamId = db.getTeamIdByTeamName(teamName);
        Vector<String> dates = db.getScheduledDatesForTeam(teamId);//from current date forward
        if (dates == null || dates.isEmpty()) {
            Date[] date = {new Date()};
            scheduleDatePicker.getMonthView().setUnselectableDates(date);
            scheduleDatePicker.getMonthView().setUpperBound(date[0]);
            return;
        }

        LocalDate lastLocalDate = LocalDate.parse(dates.lastElement());
        Calendar calendar = new GregorianCalendar();
        DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Vector<Date> datesToMakeUnselectable = new Vector<>();

        calendar.set(lastLocalDate.getYear(), lastLocalDate.getMonthValue() - 1,
                lastLocalDate.getDayOfMonth());
        Date lastDate = calendar.getTime();

        calendar.setTime(new Date()); // set start date = current date
        while (calendar.getTime().before(lastDate)) {
            Date aux = calendar.getTime();
            String output = outputFormatter.format(aux);
            if (!dates.contains(output)) {
                datesToMakeUnselectable.add(aux);
            }
            calendar.add(Calendar.DATE, 1);
        }

        Date[] datesToRemove = new Date[datesToMakeUnselectable.size()];
        datesToMakeUnselectable.toArray(datesToRemove);

        scheduleDatePicker.getMonthView().setUnselectableDates(datesToRemove);
        scheduleDatePicker.getMonthView().setUpperBound(lastDate);
        scheduleDatePicker.getMonthView().setLowerBound(new Date());
    }//GEN-LAST:event_teamNameComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ScheduleDetailsTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> operatingRoomNameComboBox;
    private org.jdesktop.swingx.JXDatePicker scheduleDatePicker;
    private javax.swing.JLabel selectScheduleDateLabel;
    private javax.swing.JLabel selectScheduleDateLabel1;
    private javax.swing.JLabel selectScheduleDateLabel2;
    private javax.swing.JComboBox<String> teamNameComboBox;
    private javax.swing.JTextField totalDayTimeTextField;
    private javax.swing.JPanel totalEstimatedTimePanel;
    private javax.swing.JTextField totalEstimatedTimeTextField;
    // End of variables declaration//GEN-END:variables
}
