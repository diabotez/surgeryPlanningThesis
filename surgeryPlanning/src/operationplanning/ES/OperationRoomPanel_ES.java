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

import java.awt.Color;
import java.util.Date;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.MyUneditableTableModel;
import operationplanning.commonFiles.ORClass;
import operationplanning.commonFiles.ORData;
import operationplanning.commonFiles.OperationRooms;
import operationplanning.commonFiles.TimeTableModels;
import operationplanning.commonFiles.Utils;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class OperationRoomPanel_ES extends javax.swing.JPanel {

    private MyUneditableTableModel operationRoomTableModelES;

    protected int roomID;
    protected String roomName;

    /**
     * Creates new form OperationRoomPanelES
     *
     * @param userType
     * @param index
     * @param name
     * @param morningOR
     */
    public OperationRoomPanel_ES(int index, String name, boolean morningOR) {
        roomID = index;
        roomName = name;

        // <editor-fold defaultstate="collapsed" desc="Initialize variables">
        operationRoomTableModelES = new MyUneditableTableModel(
                new String[]{"Nombre del equipó", "Fecha", "Hora de inicio", "Hora de finalización"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                String name = operationRoomTableModelES.getColumnName(col);

                if (name.equals("Fecha")) {
                    return java.util.Date.class;
                } else {
                    return java.lang.String.class;
                }
            }
        } //</editor-fold>  
                ;
        //</editor-fold>

        /*Call initComponents method*/
        initComponents();

        if (morningOR == true) {
            startTimeComboBox.setModel(TimeTableModels.getMorningTimeTableModel());
            endTimeComboBox.setModel(TimeTableModels.getMorningTimeTableModel());
        } else {
            startTimeComboBox.setModel(TimeTableModels.getAfternoonTimeTableModel());
            endTimeComboBox.setModel(TimeTableModels.getAfternoonTimeTableModel());
        }

        // <editor-fold defaultstate="collapsed" desc="Load data from database">
        updateORTimeTable();
        updateMedicalLeadComboBox();
        //</editor-fold>

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(operationRoomTableModelES);
        operationRoomTable.setRowSorter(sorter);

        //<editor-fold defaultstate="collapsed" desc="set enabled features for user">
        if (!Planning_ES.currentUserType.equals(Utils.UserType.HEAD_OF_DEPARTMENT)) {
            medicLeadLabel.setEnabled(false);
            medicalTeamComboBox.setEnabled(false);
            datePickerLabel.setEnabled(false);
            datePicker.setEnabled(false);
            startTimeLabel.setEnabled(false);
            startTimeComboBox.setEnabled(false);
            endTimeLabel.setEnabled(false);
            endTimeComboBox.setEnabled(false);
            bookingButton.setEnabled(false);
            addORButton.setEnabled(false);
            deleteORButton.setEnabled(false);
            changeRoomNameButton.setEnabled(false);
            deleteBookingButton.setEnabled(false);
        }
        //</editor-fold>
    }

    /**
     * This method updates the timetable of the current OR in interface.
     */
    public void updateORTimeTable() {
        OperationRooms operationRooms_inst = new OperationRooms();
        ORClass room = operationRooms_inst.getDataFor(roomID);
        Vector<ORData> bookings = room.getAvailableDates();

        while (operationRoomTableModelES.getRowCount() > 0) {
            operationRoomTableModelES.removeRow(0);
        }

        for (ORData booking : bookings) {
            operationRoomTableModelES.addRow(new Object[]{booking.teamName, booking.date,
                booking.startingHour, booking.endingHour});
        }

        Planning_ES.refreshMedicalTeamTabbedPannel();
    }

    public void updateMedicalLeadComboBox() {
        if (medicalTeamComboBox == null) {
            return;
        }
        MedicalTeams medicalInst = new MedicalTeams();
        medicalTeamComboBox.removeAllItems();

        Vector<Vector<String>> teamDetails = medicalInst.getAllTeamDetails();
        for (Vector<String> teamDetail : teamDetails) {
            medicalTeamComboBox.addItem(teamDetail.get(1));
        }
        medicalTeamComboBox.setSelectedIndex(-1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        operationRoomScrollPane = new javax.swing.JScrollPane();
        operationRoomTable = new javax.swing.JTable();
        buttonsPanel = new javax.swing.JPanel();
        addORButton = new javax.swing.JButton();
        deleteORButton = new javax.swing.JButton();
        changeRoomNameButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        bookingButton = new javax.swing.JButton();
        medicLeadLabel = new javax.swing.JLabel();
        medicalTeamComboBox = new javax.swing.JComboBox<>();
        datePicker = new org.jdesktop.swingx.JXDatePicker();
        datePickerLabel = new javax.swing.JLabel();
        startTimeComboBox = new javax.swing.JComboBox<>();
        endTimeComboBox = new javax.swing.JComboBox<>();
        startTimeLabel = new javax.swing.JLabel();
        endTimeLabel = new javax.swing.JLabel();
        deleteBookingButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setMinimumSize(new java.awt.Dimension(810, 480));
        setPreferredSize(new java.awt.Dimension(810, 480));

        operationRoomTable.setModel(operationRoomTableModelES);
        operationRoomScrollPane.setViewportView(operationRoomTable);

        addORButton.setText("Añadir nueva sala de operaciones");
        addORButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addORButtonActionPerformed(evt);
            }
        });

        deleteORButton.setText("Borrar esta sala de operaciones");
        deleteORButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteORButtonActionPerformed(evt);
            }
        });

        changeRoomNameButton.setText("Cambiar nombre");
        changeRoomNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeRoomNameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addORButton)
                .addGap(18, 18, 18)
                .addComponent(changeRoomNameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteORButton)
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(addORButton)
                .addComponent(deleteORButton)
                .addComponent(changeRoomNameButton))
        );

        bookingButton.setText("Reservar");
        bookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingButtonActionPerformed(evt);
            }
        });

        medicLeadLabel.setText("Equipo");

        medicalTeamComboBox.setToolTipText("The medical team leader");
        medicalTeamComboBox.setMaximumSize(new java.awt.Dimension(32767, 40));
        medicalTeamComboBox.setMinimumSize(new java.awt.Dimension(350, 30));
        medicalTeamComboBox.setPreferredSize(new java.awt.Dimension(350, 30));
        medicalTeamComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicalTeamComboBoxActionPerformed(evt);
            }
        });

        datePicker.getMonthView().setLowerBound(new Date());
        datePicker.getMonthView().setDayForeground(1, Color.red);
        datePicker.getMonthView().setDayForeground(7, Color.red);
        datePicker.setDate(null);

        datePickerLabel.setText("Fecha");

        startTimeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        endTimeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        startTimeLabel.setText("Inicio");

        endTimeLabel.setText("Finalización");

        deleteBookingButton.setText("Borrar reservation");
        deleteBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(medicLeadLabel)
                    .addComponent(medicalTeamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePickerLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(endTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bookingButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(deleteBookingButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(endTimeLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicLeadLabel)
                    .addComponent(datePickerLabel)
                    .addComponent(startTimeLabel)
                    .addComponent(endTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicalTeamComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookingButton)
                    .addComponent(deleteBookingButton))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(operationRoomScrollPane)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(operationRoomScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addORButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addORButtonActionPerformed
        Object[] o = {"Por la mañana", "Por la tarde", "Cancelar"};
        int opt = JOptionPane.showOptionDialog(this, "¿Quieres añadir un nuevo quirofano? "
                + "En caso afirmativo, ¿es un quirofano de mañanas o tardes? ",
                "Añadir nuevo quirofano",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, o, o[2]);

        String ansName = JOptionPane.showInputDialog(null, "Escriba el nombre de la nuevo quirofano",
                "Nombre de la nuevo quirofano",
                JOptionPane.PLAIN_MESSAGE);

        if (ansName == null || ansName.equals("")) {
            return;
        }

        //opt is the index in the o vector for the selected option
        switch (opt) {
            case 0:
                Planning_ES.addNewOperationRoom(true, ansName);
                break;
            case 1:
                Planning_ES.addNewOperationRoom(false, ansName);
                break;
            default:
        }
    }//GEN-LAST:event_addORButtonActionPerformed

    private void deleteORButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteORButtonActionPerformed
        Object[] o = {"Si", "No", "Cancelar"};
        int opt = JOptionPane.showOptionDialog(this, "Está a punto de eliminar este quirófano.\n "
                + "¿Está seguro de que desea eliminar todos los datos de este quirofano de la basa de datos?",
                "¡Advertencia! Eliminar quirófano",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE, null, o, o[2]);

        if (opt == 0) {
            Planning_ES.removeOperatingRoom(this);
        }
    }//GEN-LAST:event_deleteORButtonActionPerformed

    private void bookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingButtonActionPerformed
        Date newDate = (Date) datePicker.getDate();
        if (newDate == null || medicalTeamComboBox.getSelectedIndex() == -1
                || startTimeComboBox.getSelectedIndex() == -1
                || endTimeComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Uno o más campos están vacíos.");
            return;
        }
        if (newDate.before(new Date())) {
            JOptionPane.showMessageDialog(null, "No se puede seleccionar una fecha anterior o una fecha actual.");
            return;
        }
        OperationRooms ORinst = new OperationRooms();
        if (!ORinst.isRoomAvailable(roomID, newDate)) {
            JOptionPane.showMessageDialog(null, "Esta fecha ya está programada por otro equipo.");
            datePicker.setDate(null);
            return;
        }

        String teamName = (String) medicalTeamComboBox.getSelectedItem();
        String startHour = (String) startTimeComboBox.getSelectedItem();
        String endHour = (String) endTimeComboBox.getSelectedItem();

        if (!ORinst.isTeamAvailable(teamName, newDate)) {
            JOptionPane.showMessageDialog(null, "Este equipo ya esta planificado en esta fecha.",
                    "Eror",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[] start = startHour.split(":");
            String[] finish = endHour.split(":");
            if (Integer.parseInt(start[0]) > Integer.parseInt(finish[0])
                    || Integer.parseInt(start[0]) == Integer.parseInt(finish[0])
                    && Integer.parseInt(start[1]) >= Integer.parseInt(finish[1])) {
                //error. start hour bigger then finish hour
                JOptionPane.showConfirmDialog(null, "La hora de inicio no puede ser mayor o igual que la hora de finalización.",
                        "Error. Intervalo de tiempo incorrecto", JOptionPane.DEFAULT_OPTION);
                return;
            }
            
            ORinst.addOrEntry(roomID, teamName, newDate, startHour, endHour);
            JOptionPane.showMessageDialog(null, "Se añadió nueva fila a la sala de operaciones actual.");
            datePicker.setDate(null);
            medicalTeamComboBox.setSelectedIndex(-1);
        }

        updateORTimeTable();
        Planning_ES.refreshMedicalTeamTabbedPannel();
    }//GEN-LAST:event_bookingButtonActionPerformed

    private void medicalTeamComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicalTeamComboBoxActionPerformed
        if (medicalTeamComboBox == null || medicalTeamComboBox.getSelectedIndex() == -1) {
            return;
        }

        String teamName = (String) medicalTeamComboBox.getSelectedItem();
        datePicker.getMonthView().setLowerBound(new Date());

        Vector<Date> teamScheduledDates = new Vector<>();//dates available for this team
        Vector<ORClass> scheduleORDates = new OperationRooms().getORsForTeam(teamName);

        for (ORClass availableOR : scheduleORDates) {
            Vector<ORData> bookings = availableOR.getAvailableDates();
            for (ORData booking : bookings) {
                teamScheduledDates.add(booking.date);
            }
        }//Get all the scheduled dates

        if (teamScheduledDates.isEmpty()) {
            return;
        }

        teamScheduledDates.add(new Date());
        Date[] datesToRemove = new Date[teamScheduledDates.size()];
        teamScheduledDates.toArray(datesToRemove);
        datePicker.getMonthView().setUnselectableDates(datesToRemove);
        datePicker.setDate(null);
    }//GEN-LAST:event_medicalTeamComboBoxActionPerformed

    private void changeRoomNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeRoomNameButtonActionPerformed
        String newName = JOptionPane.showInputDialog(null, "Por favor, ingrese el nuevo nombre para este quirofano.",
                " Cambiar el nombre del quirofano", JOptionPane.PLAIN_MESSAGE);
        if (null == newName || newName.equals("")) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "El nuevo nombre de este quirofano es: " + newName,
                "Nombre de quirofano cambiado",
                JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            roomName = newName;
            new OperationRooms().setORNameById(roomID, newName);
            Planning_ES.updateOperationRoomTabbedPanne();
        }
    }//GEN-LAST:event_changeRoomNameButtonActionPerformed

    private void deleteBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookingButtonActionPerformed
        String dateString = JOptionPane.showInputDialog(null, new JLabel("<html> Escriba la fecha en la que desea eliminar la reserva de esta sala de operaciones"
                + "En el siguiente formato: <br><br><b> aaaa-mm-dd </b> (por ejemplo 2017-06-20) </html>"),
                "Eliminar reserva",
                JOptionPane.PLAIN_MESSAGE);
        if (null == dateString || dateString.equals("")) {
            return;
        }

        java.sql.Date d;
        try {
            d = java.sql.Date.valueOf(dateString);
            int answer = JOptionPane.showConfirmDialog(null, new JLabel("<html>Es <b>" + d.toString() + "</b> la fecha que desea eliminar?</html>"),
                    "Confirmación de eliminar la reserva",
                    JOptionPane.OK_CANCEL_OPTION);

            if (answer == JOptionPane.OK_OPTION) {
                Object[] obj = {"OK"};
                if (new OperationRooms().removeBookingDateForOr(roomID, d)) {
                    JOptionPane.showOptionDialog(null, "La reserva para " + d.toString() + " se han eliminado.",
                            "Reserva eliminada correctamente",
                            JOptionPane.OK_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, obj, obj[0]);

                    updateORTimeTable();
                    Planning_ES.refreshMedicalTeamTabbedPannel();
                } else {
                    JOptionPane.showOptionDialog(null, "La reserva para " + d.toString() + " NO se han eliminado.",
                            "No se ha retirado la reserva",
                            JOptionPane.OK_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null, obj, obj[0]);
                }
            }
        } catch (java.lang.IllegalArgumentException e) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "El formato de fecha no corresponde con la entrada esperada.",
                    "Formato de fecha desconocido",
                    JOptionPane.OK_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null, obj, obj[0]);
        }
    }//GEN-LAST:event_deleteBookingButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addORButton;
    private javax.swing.JButton bookingButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton changeRoomNameButton;
    private org.jdesktop.swingx.JXDatePicker datePicker;
    private javax.swing.JLabel datePickerLabel;
    private javax.swing.JButton deleteBookingButton;
    private javax.swing.JButton deleteORButton;
    private javax.swing.JComboBox<String> endTimeComboBox;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel medicLeadLabel;
    private javax.swing.JComboBox<String> medicalTeamComboBox;
    private javax.swing.JScrollPane operationRoomScrollPane;
    private javax.swing.JTable operationRoomTable;
    private javax.swing.JComboBox<String> startTimeComboBox;
    private javax.swing.JLabel startTimeLabel;
    // End of variables declaration//GEN-END:variables

}
