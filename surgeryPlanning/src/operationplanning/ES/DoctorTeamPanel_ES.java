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
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
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
public class DoctorTeamPanel_ES extends javax.swing.JPanel {

    private MyUneditableTableModel doctorTeamTableModelES;
    private MyUneditableTableModel teamPlaningTableModelES;

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
    public DoctorTeamPanel_ES(String id, String teamName) {
        teamId = Integer.parseInt(id.replace("TM", ""));
        this.teamName = teamName;

        //<editor-fold defaultstate="collapsed" desc="init table models">
        morningStartTimeTableModel = TimeTableModels.getMorningTimeTableModel();
        morningEndTimeTableModel = TimeTableModels.getMorningTimeTableModel();

        doctorTeamTableModelES = new MyUneditableTableModel(
                new String[]{"ID", "Apellidos", "Nombre", "Departamento"},
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
        teamPlaningTableModelES = new MyUneditableTableModel(
                new String[]{"Fecha", "Quirófano", "Hora de inicio", "Hora de finalización"},
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
            doctoresLabel.setText("Coordinador: " + coordinator.lastName + " "
                    + coordinator.firstName);
        }
        //<editor-fold defaultstate="collapsed" desc="update tables">
        doctorTeamTable.getColumn("ID").setMaxWidth(80);

        /*Update column types with morningStartTimeTableComboBox menu*/
        TableColumn startTimeColumn = teamPlaningTable.getColumnModel().getColumn(2);
        startTimeColumn.setCellEditor(new DefaultCellEditor(morningStartTimeTableComboBox));
        TableColumn endTimeColumn = teamPlaningTable.getColumnModel().getColumn(3);
        endTimeColumn.setCellEditor(new DefaultCellEditor(morningEndTimeTableComboBox));

        /*Add the team medics int the table*/
        updateMedicalTeamTable();
        updateMedicalTeamORtimetable();

        TableRowSorter<MyUneditableTableModel> doctorTeamTableSorter = new TableRowSorter<>(doctorTeamTableModelES);
        doctorTeamTable.setRowSorter(doctorTeamTableSorter);
        doctorTeamTableSorter.setSortable(0, false);
        doctorTeamTableSorter.sort();

        TableRowSorter<MyUneditableTableModel> teamPlaningTableSorter = new TableRowSorter<>(teamPlaningTableModelES);
        teamPlaningTable.setRowSorter(teamPlaningTableSorter);
        teamPlaningTableSorter.sort();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="set enabled features for user">
        if (Planning_ES.currentUserType.equals(Utils.UserType.MEDIC)
                || Planning_ES.currentUserType.equals(Utils.UserType.ASSISTANT)) {
            deleteTeamButton.setEnabled(false);
            changeTeamNameButton.setEnabled(false);
        } else if (Planning_ES.currentUserType.equals(Utils.UserType.COORDINATOR)) {
            deleteTeamButton.setEnabled(false);
            // he can change the name of any other team, not only to his team...
        }
        //</editor-fold>
    }

    public void updateMedicalTeamTable() {
        MedicIdentifiers coordinator = medicalInst.getCoordinator(teamId);
        Vector<MedicIdentifiers> members = medicalInst.getMedicalTeamByTeamId(teamId);

        while (doctorTeamTableModelES.getRowCount() > 0) {
            doctorTeamTableModelES.removeRow(0);
        }
        if (coordinator != null) {
            doctorTeamTableModelES.addRow(new Object[]{coordinator.IDnumber, coordinator.lastName, coordinator.firstName, coordinator.department});
            doctoresLabel.setText("Coordinador: " + coordinator.lastName + " "
                    + coordinator.firstName);

            for (MedicIdentifiers member : members) {
                doctorTeamTableModelES.addRow(new Object[]{member.IDnumber, member.lastName, member.firstName, member.department});
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

        while (teamPlaningTableModelES.getRowCount() > 0) {
            teamPlaningTableModelES.removeRow(0);
        }

        for (ORClass room : allocatedORs) {
            Vector<ORData> bookings = room.getAvailableDates();
            for (ORData booking : bookings) {
                teamPlaningTableModelES.addRow(new Object[]{booking.date, ORInst.getORName(room.orID),
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
        medicalInst.setMedicalTeamName(teamId, teamName);
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

        setPreferredSize(new java.awt.Dimension(640, 480));

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(15);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setContinuousLayout(true);

        doctorTeamTable.setModel(doctorTeamTableModelES);
        doctorTeamTable.setToolTipText("");
        doctorTeamTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        doctorTeamTable.setName(""); // NOI18N
        doctorTeamTable.setSelectionBackground(new java.awt.Color(162, 164, 196));
        doctorTeamTable.setSelectionForeground(new java.awt.Color(75, 70, 70));
        doctorTeamTable.getTableHeader().setReorderingAllowed(false);
        doctorTeamScrollPanel.setViewportView(doctorTeamTable);

        doctoresLabel.setText("Médicos");

        javax.swing.GroupLayout upperPannelLayout = new javax.swing.GroupLayout(upperPannel);
        upperPannel.setLayout(upperPannelLayout);
        upperPannelLayout.setHorizontalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doctorTeamScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
            .addComponent(doctoresLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        upperPannelLayout.setVerticalGroup(
            upperPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upperPannelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(doctoresLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(doctorTeamScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(upperPannel);

        deleteTeamButton.setText("Borrar equipo");
        deleteTeamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTeamButtonActionPerformed(evt);
            }
        });

        changeTeamNameButton.setText("Cambiar nombre de equipo");
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

        quirofanosUtilisadoLabel.setText("Quirófanos reservados para este equipo");

        teamPlaningTable.setModel(teamPlaningTableModelES);
        teamPlaningScrollPane.setViewportView(teamPlaningTable);

        javax.swing.GroupLayout lowerPannelLayout = new javax.swing.GroupLayout(lowerPannel);
        lowerPannel.setLayout(lowerPannelLayout);
        lowerPannelLayout.setHorizontalGroup(
            lowerPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(doctorTeamsubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(teamPlaningScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
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
                .addComponent(teamPlaningScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
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

    private void deleteTeamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTeamButtonActionPerformed
        if (doctorTeamTableModelES.getRowCount() != 0) {
            //this team has doctors in the table
            //Do another check for the doctors in the dataBase
            int idx = 0;

            //for each doctor in the table check in dataBase
            while (idx < doctorTeamTableModelES.getRowCount()) {
                int doctorID = (int) doctorTeamTableModelES.getValueAt(idx, 0); //get doctor ID
                MedicIdentifiers doctor = medicalInst.getDoctorByID(doctorID); //get the medic identifiers

                if (doctor != null) {
                    // doctor exists in this team. Do not delete it
                    idx++;
                    continue;
                }

                //doctor == null => remove the row from the table. Doctor does not exist in the dataBase.
                doctorTeamTableModelES.removeRow(idx);
                //do not increase idx, otherwise it will jump over one doctor
            }

            if (doctorTeamTableModelES.getRowCount() != 0) {
                //there still are doctors in the team
                //cannot delete the team
                JOptionPane.showMessageDialog(null, "Todavía hay médicos en este equipo. "
                        + "Podrá ser eliminado después de que todos los médicos se hayan transferido a otro equipo "
                        + "o se hayan eliminado de la base de datos.", 
                        "Este equipo no se puede eliminar", 
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // There are no doctors in the team.
        Object[] o = {"Retirar", "Cancelar"};
        int option = JOptionPane.showOptionDialog(this, "Está a punto de eliminar este equipo médico."
                + "\n\n ¿Está seguro de que desea eliminar el equipo permanentemente de la base de datos?",
                "¡Advertencia! Eliminación del equipo médico",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, o, o[1]);

        if (option == 1) {
            return; // "Cancel" pressed
        }

        //remove team
        Planning_ES.removeMedicalTeamByTeamId(teamId);

        SeePatientListPanel_ES.updateMedicList();
        CreateSchedulePanel_ES.updateMedicList();
        AddNewMedicWindow_ES.updateMedicalLeadComboBox();
        UpdatePatientPanel_ES.refreshData();
        Planning_ES.updateOperationRoomTabbedPanne();
    }//GEN-LAST:event_deleteTeamButtonActionPerformed

    private void changeTeamNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTeamNameButtonActionPerformed
        String newName = JOptionPane.showInputDialog(null, "Por favor, ingrese el nuevo nombre para este equipo.", " Cambiar el nombre del equipo", JOptionPane.PLAIN_MESSAGE);
        if (null == newName || newName.equals("")) {
            return;
        }

        int answer = JOptionPane.showConfirmDialog(null, "El nuevo nombre de este equipo es: " + newName, "Confirmar nuevo nombre de equipo", JOptionPane.OK_CANCEL_OPTION);
        if (answer == JOptionPane.OK_OPTION) {
            medicalInst.changeMedicalTeamName(teamName, newName);
            setTeamName(newName);
            Planning_ES.refreshMedicalTeamTabbedPannel();
            Planning_ES.updateOperationRoomTabbedPanne();
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
