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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import operationplanning.commonFiles.MedicIdentifiers;
import operationplanning.commonFiles.MedicalTeams;
import operationplanning.commonFiles.ORData;
import operationplanning.commonFiles.MyUneditableTableModel;
import operationplanning.commonFiles.ORClass;
import operationplanning.commonFiles.OperationRooms;
import operationplanning.commonFiles.TimeTableModels;
import operationplanning.commonFiles.Utils;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class DoctorTeamPanel_EN extends javax.swing.JPanel {

    private MyUneditableTableModel doctorTeamTableModelEN;
    private MyUneditableTableModel teamPlaningTableModelEN;

    private DefaultComboBoxModel morningStartTimeTableModel;
    private DefaultComboBoxModel morningEndTimeTableModel;

    private JComboBox morningStartTimeTableComboBox = new JComboBox();
    private JComboBox morningEndTimeTableComboBox = new JComboBox();

    protected int teamId;
    protected String teamName;
    private MedicalTeams medicalInst = new MedicalTeams();

    /**
     * Creates new form mydoctorTeamPanel
     *
     * @param userType
     * @param id
     * @param teamName
     */
    public DoctorTeamPanel_EN(String id, String teamName) {
        teamId = Integer.parseInt(id.replace("TM", ""));
        this.teamName = teamName;

        //<editor-fold defaultstate="collapsed" desc="init table models">
        morningStartTimeTableModel = TimeTableModels.getMorningTimeTableModel();
        morningEndTimeTableModel = TimeTableModels.getMorningTimeTableModel();

        doctorTeamTableModelEN = new MyUneditableTableModel(
                new String[]{"ID", "Last name", "First Name", "Department"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                if (col == 0) {
                    return java.lang.Integer.class;
                }
                return java.lang.String.class;
            }
        } //</editor-fold>
                ;
        teamPlaningTableModelEN = new MyUneditableTableModel(
                new String[]{"Date", "Operation room", "Starting hour", "Ending hour"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                if (col == 0) {
                    return java.util.Date.class;
                } else {
                    return java.lang.String.class;
                }
            }
        } //</editor-fold>           
                ;

        morningStartTimeTableComboBox.setModel(morningStartTimeTableModel);
        morningEndTimeTableComboBox.setModel(morningEndTimeTableModel);
        //</editor-fold>

        /*Call initComponents method*/
        initComponents();

        MedicIdentifiers coordinator = medicalInst.getCoordinator(teamId);
        if (null != coordinator) {
            doctoresLabel.setText("Coordinator: " + coordinator.lastName + " "
                    + coordinator.firstName);
        }

        //<editor-fold defaultstate="collapsed" desc="update tables">
        doctorTeamTable.getColumn("ID").setMaxWidth(80);

        /*Add the team medics int the table*/
        updateMedicalTeamTable();
        updateMedicalTeamORtimetable();

        TableRowSorter<MyUneditableTableModel> doctorTeamTableSorter = new TableRowSorter<>(doctorTeamTableModelEN);
        doctorTeamTable.setRowSorter(doctorTeamTableSorter);
        doctorTeamTableSorter.setSortable(0, false);
        doctorTeamTableSorter.sort();

        TableRowSorter<MyUneditableTableModel> teamPlaningTableSorter = new TableRowSorter<>(teamPlaningTableModelEN);
        teamPlaningTable.setRowSorter(teamPlaningTableSorter);
        teamPlaningTableSorter.sort();

        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="set enabled features for user">
        if (Planning_EN.currentUserType.equals(Utils.UserType.MEDIC)
                || Planning_EN.currentUserType.equals(Utils.UserType.ASSISTANT)) {
            deleteTeamButton.setEnabled(false);
            changeTeamNameButton.setEnabled(false);
        } else if (Planning_EN.currentUserType.equals(Utils.UserType.COORDINATOR)) {
            deleteTeamButton.setEnabled(false);
            // he can change the name of any other team, not only to his team...
        }
        //</editor-fold>
    }

    public void updateMedicalTeamTable() {
        MedicIdentifiers coordinator = medicalInst.getCoordinator(teamId);
        Vector<MedicIdentifiers> members = medicalInst.getMedicalTeamByTeamId(teamId);

        while (doctorTeamTableModelEN.getRowCount() > 0) {
            doctorTeamTableModelEN.removeRow(0);
        }
        if (coordinator != null) {
            doctorTeamTableModelEN.addRow(new Object[]{coordinator.IDnumber, coordinator.lastName, coordinator.firstName, coordinator.department});
            doctoresLabel.setText("Coordinator: " + coordinator.lastName + " "
                    + coordinator.firstName);
            for (int i = 0; i < members.size(); i++) {
                doctorTeamTableModelEN.addRow(new Object[]{members.get(i).IDnumber, members.get(i).lastName, members.get(i).firstName, members.get(i).department});
            }
        }
    }

    public void updateMedicalTeamORtimetable() {
        OperationRooms ORInst = new OperationRooms();

        MedicIdentifiers coordinator = medicalInst.getCoordinator(teamId);
        if (coordinator == null) {
            return;
        }

        Vector<ORClass> allocatedORs = ORInst.getORsForTeam(medicalInst.getTeamNameByTeamLeaderId(coordinator.IDnumber));

        while (teamPlaningTableModelEN.getRowCount() > 0) {
            teamPlaningTableModelEN.removeRow(0);
        }

        for (ORClass room : allocatedORs) {
            Vector<ORData> bookings = room.getAvailableDates();
            for (ORData booking : bookings) {
                teamPlaningTableModelEN.addRow(new Object[]{booking.date, ORInst.getORName(room.orID),
                    booking.startingHour, booking.endingHour});
            }
        }

    }

    /**
     * @return the teamId
     */
    public int getTeamIndex() {
        return teamId;
    }

    /**
     * Sets the new index for the team.
     *
     * @param aTeamIndex the teamId to set
     */
    public void setTeamIndex(int aTeamIndex) {
        teamId = aTeamIndex;
        medicalInst.setMedicalTeamName(teamId, teamName);
    }

    /**
     * @return the team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the team name.
     *
     * @param aTeamName the new name for the team
     */
    public void setTeamName(String aTeamName) {
        teamName = aTeamName;
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
        doctorTeamScrollPanel = new javax.swing.JScrollPane();
        doctorTeamTable = new javax.swing.JTable();
        doctoresLabel = new javax.swing.JLabel();
        lowerPannel = new javax.swing.JPanel();
        doctorTeamsubPanel = new javax.swing.JPanel();
        deleteTeamButton = new javax.swing.JButton();
        changeTeamNameButton = new javax.swing.JButton();
        quirofanosUtilisadoLabel = new javax.swing.JLabel();
        teamPlaningScrollPane = new javax.swing.JScrollPane();
        teamPlaningTable = new javax.swing.JTable();

        setMinimumSize(new java.awt.Dimension(640, 480));
        setPreferredSize(new java.awt.Dimension(640, 480));

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(15);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setContinuousLayout(true);

        doctorTeamTable.setModel(doctorTeamTableModelEN);
        doctorTeamTable.setToolTipText("");
        doctorTeamTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        doctorTeamTable.setName(""); // NOI18N
        doctorTeamTable.setSelectionBackground(new java.awt.Color(162, 164, 196));
        doctorTeamTable.setSelectionForeground(new java.awt.Color(75, 70, 70));
        doctorTeamTable.getTableHeader().setReorderingAllowed(false);
        doctorTeamScrollPanel.setViewportView(doctorTeamTable);

        doctoresLabel.setText("Medics");

        javax.swing.GroupLayout upperPannelLayout = new javax.swing.GroupLayout(upperPannel);
        upperPannel.setLayout(upperPannelLayout);
        upperPannelLayout.setHorizontalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doctorTeamScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addComponent(doctoresLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        upperPannelLayout.setVerticalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upperPannelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doctoresLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doctorTeamScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(upperPannel);

        deleteTeamButton.setText("Delete this team");
        deleteTeamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTeamButtonActionPerformed(evt);
            }
        });

        changeTeamNameButton.setText("Change team's name");
        changeTeamNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTeamNameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout doctorTeamsubPanelLayout = new javax.swing.GroupLayout(doctorTeamsubPanel);
        doctorTeamsubPanel.setLayout(doctorTeamsubPanelLayout);
        doctorTeamsubPanelLayout.setHorizontalGroup(
            doctorTeamsubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorTeamsubPanelLayout.createSequentialGroup()
                .addComponent(deleteTeamButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(changeTeamNameButton))
        );
        doctorTeamsubPanelLayout.setVerticalGroup(
            doctorTeamsubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doctorTeamsubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(deleteTeamButton)
                .addComponent(changeTeamNameButton))
        );

        quirofanosUtilisadoLabel.setText("Operation rooms resarved for this team");

        teamPlaningTable.setModel(teamPlaningTableModelEN);
        teamPlaningScrollPane.setViewportView(teamPlaningTable);

        javax.swing.GroupLayout lowerPannelLayout = new javax.swing.GroupLayout(lowerPannel);
        lowerPannel.setLayout(lowerPannelLayout);
        lowerPannelLayout.setHorizontalGroup(
            lowerPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doctorTeamsubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(teamPlaningScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lowerPannelLayout.createSequentialGroup()
                .addComponent(quirofanosUtilisadoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        lowerPannelLayout.setVerticalGroup(
            lowerPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lowerPannelLayout.createSequentialGroup()
                .addComponent(doctorTeamsubPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(quirofanosUtilisadoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teamPlaningScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(lowerPannel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Delete this medical team only if there are no doctors. Otherwise, ask to
     * remove doctors or move them to other team first.
     *
     * @param evt
     */
    private void deleteTeamButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteTeamButtonActionPerformed
    {//GEN-HEADEREND:event_deleteTeamButtonActionPerformed
        if (doctorTeamTableModelEN.getRowCount() != 0) {
            //this team has doctors in the table
            //Do another check for the doctors in the dataBase
            int idx = 0;

            //for each doctor in the table check in dataBase
            while (idx < doctorTeamTableModelEN.getRowCount()) {
                int doctorID = (int) doctorTeamTableModelEN.getValueAt(idx, 0); //get doctor ID
                MedicIdentifiers doctor = medicalInst.getDoctorByID(doctorID); //get the medic identifiers

                if (doctor != null) {
                    // doctor exists in this team. Do not delete it
                    idx++;
                    continue;
                }

                //doctor == null => remove the row from the table. Doctor does not exist in the dataBase.
                doctorTeamTableModelEN.removeRow(idx);
                //do not increase idx, otherwise it will jump over one doctor
            }

            if (doctorTeamTableModelEN.getRowCount() != 0) {
                //there still are doctors in the team
                //cannot delete the team
                JOptionPane.showMessageDialog(null, "There still are doctors in this team. "
                        + "It can be removed after all the doctors have been transfered to another team "
                        + "or deleted from the data base.", "This team cannot be deleted", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // There are no doctors in the team.
        Object[] o = {"Remove", "Cancel"};
        int option = JOptionPane.showOptionDialog(this, "You are about to delete this medical team."
                + "\n\n Are you sure you want to remove the team permanently form the data base?",
                "Warning! Medical team removing",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, o, o[1]);

        if (option == 1) {
            return; // "Cancel" pressed
        }

        //remove team
        Planning_EN.removeMedicalTeamByTeamId(teamId);

        SeePatientListPanel_EN.updateMedicList();
        CreateSchedulePanel_EN.updateMedicList();
        AddNewMedicWindow_EN.updateMedicalLeadComboBox();
        Planning_EN.updateOperationRoomTabbedPanne();
        UpdatePatientPanel_EN.refreshData();
    }//GEN-LAST:event_deleteTeamButtonActionPerformed

    private void changeTeamNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTeamNameButtonActionPerformed
        String newName = JOptionPane.showInputDialog(null, "Please enter the new name for this team.", "Changing team's name", JOptionPane.PLAIN_MESSAGE);
        if (null == newName || newName.equals("")) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "The new name for this team is: " + newName, 
                "Confirm new team's name", 
                JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            medicalInst.changeMedicalTeamName(teamName, newName);
            setTeamName(newName);
            Planning_EN.refreshMedicalTeamTabbedPannel();
            Planning_EN.updateOperationRoomTabbedPanne();
        }
    }//GEN-LAST:event_changeTeamNameButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changeTeamNameButton;
    private javax.swing.JButton deleteTeamButton;
    private javax.swing.JScrollPane doctorTeamScrollPanel;
    private javax.swing.JTable doctorTeamTable;
    private javax.swing.JPanel doctorTeamsubPanel;
    private javax.swing.JLabel doctoresLabel;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel lowerPannel;
    private javax.swing.JLabel quirofanosUtilisadoLabel;
    private javax.swing.JScrollPane teamPlaningScrollPane;
    private javax.swing.JTable teamPlaningTable;
    private javax.swing.JPanel upperPannel;
    // End of variables declaration//GEN-END:variables

}
