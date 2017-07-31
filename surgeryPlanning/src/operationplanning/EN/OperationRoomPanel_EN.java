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

import java.awt.Color;
import java.awt.Font;
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
public class OperationRoomPanel_EN extends javax.swing.JPanel {

    private MyUneditableTableModel operationRoomTableModelEN;

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
    public OperationRoomPanel_EN(int index, String name, boolean morningOR) {
        roomID = index;
        roomName = name;

        // <editor-fold defaultstate="collapsed" desc="Initialize variables">
        operationRoomTableModelEN = new MyUneditableTableModel(
                new String[]{"Medical Team", "Date", "Starting hour", "Ending hour"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                String name = operationRoomTableModelEN.getColumnName(col);

                if (name.equals("Date")) {
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

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(operationRoomTableModelEN);
        operationRoomTable.setRowSorter(sorter);

        //<editor-fold defaultstate="collapsed" desc="set enabled features for user">
        if (!Planning_EN.currentUserType.equals(Utils.UserType.HEAD_OF_DEPARTMENT)) {
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

        while (operationRoomTableModelEN.getRowCount() > 0) {
            operationRoomTableModelEN.removeRow(0);
        }

        for (ORData booking : bookings) {
            operationRoomTableModelEN.addRow(new Object[]{booking.teamName, booking.date,
                booking.startingHour, booking.endingHour});
        }

        Planning_EN.refreshMedicalTeamTabbedPannel();
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
        jSeparator1 = new javax.swing.JSeparator();
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

        setMinimumSize(new java.awt.Dimension(770, 480));
        setPreferredSize(new java.awt.Dimension(770, 480));

        operationRoomTable.setModel(operationRoomTableModelEN);
        operationRoomScrollPane.setViewportView(operationRoomTable);

        addORButton.setText("Add new operation room");
        addORButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addORButtonActionPerformed(evt);
            }
        });

        deleteORButton.setText("Delete this operation room");
        deleteORButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteORButtonActionPerformed(evt);
            }
        });

        changeRoomNameButton.setText("Change room's name");
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
                .addComponent(addORButton, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(changeRoomNameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteORButton, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addORButton)
                    .addComponent(deleteORButton)
                    .addComponent(changeRoomNameButton))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bookingButton.setText("Book");
        bookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingButtonActionPerformed(evt);
            }
        });

        medicLeadLabel.setText("Team");

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

        datePickerLabel.setText("Date");

        startTimeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        endTimeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        startTimeLabel.setText("Start");

        endTimeLabel.setText("End");

        deleteBookingButton.setText("Delete booking");
        deleteBookingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBookingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTimeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(endTimeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bookingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteBookingButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(endTimeLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(operationRoomScrollPane)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(operationRoomScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addORButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addORButtonActionPerformed
        Object[] o = {"Add morning OR", "Add afternoon OR", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, "Do you want to add a new operation room? If yes, is it a morning or afternoon Operation Room? ", "Add new Operation Room", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, o, o[2]);

        String ansName = JOptionPane.showInputDialog(null, "Please write the name of the new operating room", "New operating room name", JOptionPane.PLAIN_MESSAGE);

        if (ansName == null || ansName.equals("")) {
            return;
        }

        //opt is the index in the o vector for the selected option
        switch (opt) {
            case 0:
                Planning_EN.addNewOperationRoom(true, ansName);
                break;
            case 1:
                Planning_EN.addNewOperationRoom(false, ansName);
                break;
            default:
        }
    }//GEN-LAST:event_addORButtonActionPerformed

    private void deleteORButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteORButtonActionPerformed
        Object[] o = {"Yes", "No", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, "You are about to delete this operation room. "
                + "Are you sure you want to delete all the data about this operation room from the data base?",
                "Warning! Delete operation room",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE, null, o, o[2]);

        if (opt == 0) {
            Planning_EN.removeOperatingRoom(this);
        }
    }//GEN-LAST:event_deleteORButtonActionPerformed

    private void bookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingButtonActionPerformed
        Date newDate = (Date) datePicker.getDate();
        if (newDate == null || medicalTeamComboBox.getSelectedIndex() == -1
                || startTimeComboBox.getSelectedIndex() == -1
                || endTimeComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "One or more fields are empty.");
            return;
        }
        if (newDate.before(new Date())) {
            JOptionPane.showMessageDialog(null, "Cannot select a previous date or current date.");
            return;
        }
        OperationRooms ORinst = new OperationRooms();
        if (!ORinst.isRoomAvailable(roomID, newDate)) {
            JOptionPane.showMessageDialog(null, "This date is already scheduled by another team.");
            return;
        }

        String teamName = (String) medicalTeamComboBox.getSelectedItem();
        String startHour = (String) startTimeComboBox.getSelectedItem();
        String endHour = (String) endTimeComboBox.getSelectedItem();

        if (!ORinst.isTeamAvailable(teamName, newDate)) {
            JOptionPane.showMessageDialog(null, "This team is already scheduled in this date",
                    "Eror",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            String[] start = startHour.split(":");
            String[] finish = endHour.split(":");
            if (Integer.parseInt(start[0]) > Integer.parseInt(finish[0])
                    || Integer.parseInt(start[0]) == Integer.parseInt(finish[0])
                    && Integer.parseInt(start[1]) >= Integer.parseInt(finish[1])) {
                //error. start hour bigger then finish hour
                JOptionPane.showConfirmDialog(null, "Starting hour cannot be greater or equal than ending hour.",
                        "Error. Wrong time interval", JOptionPane.DEFAULT_OPTION);
                return;
            }

            ORinst.addOrEntry(roomID, teamName, newDate, startHour, endHour);
            JOptionPane.showMessageDialog(null, "New entry was added to current operating room.");
            datePicker.setDate(null);
            medicalTeamComboBox.setSelectedIndex(-1);
        }

        updateORTimeTable();
        Planning_EN.refreshMedicalTeamTabbedPannel();
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
        String newName = JOptionPane.showInputDialog(null, "Please enter the new name for this room.",
                "Changing room's name",
                JOptionPane.PLAIN_MESSAGE);
        if (null == newName || newName.equals("")) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "The new name for this room is: " + newName,
                "Confirm new room's name",
                JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            roomName = newName;
            new OperationRooms().setORNameById(roomID, newName);
            Planning_EN.updateOperationRoomTabbedPanne();
        }
    }//GEN-LAST:event_changeRoomNameButtonActionPerformed

    private void deleteBookingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBookingButtonActionPerformed
        String dateString = JOptionPane.showInputDialog(null, new JLabel("<html>Write the date you want to remove the booking for this OR"
                + " in the following format:<br><br><b>yyyy-mm-dd</b> (e.g. 2017-06-20)</html>"),
                "Removing booking",
                JOptionPane.PLAIN_MESSAGE);
        if (null == dateString || dateString.equals("")) {
            return;
        }

        java.sql.Date d;
        try {
            d = java.sql.Date.valueOf(dateString);
            int answer = JOptionPane.showConfirmDialog(null, new JLabel("<html>Is <b>" + d.toString() + "</b> the date you want to remove?</html>"),
                    "Removing booking confirmation",
                    JOptionPane.OK_CANCEL_OPTION);

            if (answer == JOptionPane.OK_OPTION) {
                Object[] obj = {"OK"};
                if (new OperationRooms().removeBookingDateForOr(roomID, d)) {
                    JOptionPane.showOptionDialog(null, "The booking for " + d.toString() + " have been removed successfully.",
                            "Booking removed successfully",
                            JOptionPane.OK_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null, obj, obj[0]);

                    updateORTimeTable();
                    Planning_EN.refreshMedicalTeamTabbedPannel();
                } else {
                    JOptionPane.showOptionDialog(null, "The booking for " + d.toString() + " have NOT been removed.",
                            "Booking NOT removed",
                            JOptionPane.OK_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null, obj, obj[0]);
                }
            }
        } catch (java.lang.IllegalArgumentException e) {
            Object[] obj = {"OK"};
            JOptionPane.showOptionDialog(null, "The date format is not corresponding with the expected input.",
                    "Unknown date format",
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
