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

import java.awt.Component;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import operationplanning.commonFiles.DatabaseQueries;
import operationplanning.commonFiles.MyUneditableTableModel;
import operationplanning.commonFiles.SurgeriesList;
import operationplanning.commonFiles.Utils;

/**
 * @abstract
 *
 * @author Diana Botez
 */
public class SurgeriesListPanel_ES extends javax.swing.JPanel {

//    private static SurgeriesList surgeriesInst = SurgeriesList.getInstance();
    private static MyUneditableTableModel surgeriesTableModelES;

    /**
     * Creates new form ModifySchedulePanel_ES
     * @param userType
     */
    public SurgeriesListPanel_ES() {
        //<editor-fold defaultstate="collapsed" desc="Initialize variables">
        this.surgeriesTableModelES = new MyUneditableTableModel(
                new String[]{"Cirugía", "Patología", "Duración media (min)", "Desviación estándar (min)"},
                0) //<editor-fold defaultstate="collapsed" desc="set column classes">
        {
            @Override
            public Class getColumnClass(int col) {
                String name = surgeriesTableModelES.getColumnName(col);

                if (name.equals("Patología") || name.equals("Cirugía")) {
                    return java.lang.String.class;
                } else {
                    return java.lang.Integer.class;
                }
            }
        } //</editor-fold> //</editor-fold> //</editor-fold> //</editor-fold> //</editor-fold> //</editor-fold> //</editor-fold> //</editor-fold>
                ;
        //</editor-fold>

        initComponents();

        // <editor-fold defaultstate="collapsed" desc="Load data from data base">
        updateSurgeriesTable();

        TableRowSorter<MyUneditableTableModel> sorter = new TableRowSorter<>(surgeriesTableModelES);
        surgeryTable.setRowSorter(sorter);
        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        //</editor-fold>
        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
                Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
                int align = DefaultTableCellRenderer.CENTER;
                ((DefaultTableCellRenderer) tableCellRendererComponent).setHorizontalAlignment(align);
                return tableCellRendererComponent;
            }
        };
        surgeryTable.getColumnModel().getColumn(0).setCellRenderer(centerRender);
        surgeryTable.getColumnModel().getColumn(1).setCellRenderer(centerRender);
        surgeryTable.getColumnModel().getColumn(2).setCellRenderer(centerRender);
        surgeryTable.getColumnModel().getColumn(3).setCellRenderer(centerRender);

        if (Planning_ES.currentUserType.equals(Utils.UserType.ASSISTANT)) {
            addButton.setEnabled(false);
            updateRemoveButton.setEnabled(false);
        }
    }

    public static void addNewSurgeryEntry(String srg, String ptg, String avg, String std) {
        int added = new SurgeriesList().addNewSurgery(srg, ptg, avg, std);

        if (0 == added) {
            surgeriesTableModelES.addRow(new Object[]{srg, ptg, avg, std});
            UpdatePatientPanel_ES.refreshData();
        }
    }

    /**
     * Updates the surgery entry in the table and data base.
     *
     * @param srg the name of the surgery
     * @param ptg the name of the pathology
     * @param avg the average duration of the given surgery name
     * @param std the standard deviation of the given surgery name
     */
    public static void updateSurgeryEntry(String srg, String ptg, String avg, String std) {
        //update the surgery in the data base
        new SurgeriesList().updateSurgery(srg, ptg, avg, std);
        updateSurgeriesTable();
    }

    public static void updateSurgeriesTable() {
        //remove all the rows from the table
        while (surgeriesTableModelES.getRowCount() > 0) {
            surgeriesTableModelES.removeRow(0);
        }

        //get all surgeries details
        Vector<Vector<String>> surgeriesDetails = new DatabaseQueries().getAllSurgeriesDetails();

        for (Vector<String> surgeriesDetail : surgeriesDetails) {
            surgeriesTableModelES.addRow(new Object[]{surgeriesDetail.get(0), surgeriesDetail.get(1), surgeriesDetail.get(2), surgeriesDetail.get(3)});
        }
    }

    /**
     * Removes the given surgery from the table and data base, if it exists.
     *
     * @param srg the name of the surgery to be deleted
     */
    public static boolean removeSurgeryEntry(String srg) {
        boolean result = new SurgeriesList().removeSurgery(srg);
        if (result) {
            updateSurgeriesTable();
        }
        return result;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pathologyListScrollPane = new javax.swing.JScrollPane();
        surgeryTable = new javax.swing.JTable();
        buttonsPanel = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
        updateRemoveButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(640, 480));

        surgeryTable.setModel(surgeriesTableModelES);
        pathologyListScrollPane.setViewportView(surgeryTable);

        addButton.setText("Añadir cirugía");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        updateRemoveButton.setText("Actualizar o quitar cirugía");
        updateRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRemoveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsPanelLayout = new javax.swing.GroupLayout(buttonsPanel);
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(updateRemoveButton)
                .addContainerGap())
        );
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addGroup(buttonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(updateRemoveButton))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pathologyListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addComponent(buttonsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pathologyListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        AddNewSurgeryWindow_ES window = new AddNewSurgeryWindow_ES();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setAlwaysOnTop(true);
    }//GEN-LAST:event_addButtonActionPerformed

    private void updateRemoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateRemoveButtonActionPerformed
        UpdateRemoveSurgeryWindow_ES window = new UpdateRemoveSurgeryWindow_ES();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setAlwaysOnTop(true);
    }//GEN-LAST:event_updateRemoveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JScrollPane pathologyListScrollPane;
    private static javax.swing.JTable surgeryTable;
    private javax.swing.JButton updateRemoveButton;
    // End of variables declaration//GEN-END:variables

}
