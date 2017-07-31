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
package operationplanning.cplex.files;

import ilog.concert.IloException;
import ilog.concert.IloIntExpr;
import ilog.concert.IloIntVar;
import ilog.concert.IloNumExpr;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;
import java.util.Vector;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class milpExample {

    private static IloCplex cplex;

    public static void __main(String[] args) {

        double percentage = 78;
        int dayTime = 7 * 60; //7 hours in minutes

        int effectiveTime = (int) (percentage * dayTime / 100);
        int tollerance = (int) (15 * dayTime / 100);//deviation

        int daysToPlan = 30;// 52[weeks/year]/2 = 26[weeks/halfOfYear] => 130[workingDays/halfOfYear]
        int planningDays = 7;
        int selectPlanningDays = 5;
        boolean solved = true;

        double beta = 2;//0.333;

        Vector<Integer> alfaValues = new Vector<>();
        Vector<Integer> scheduledDaysValues = new Vector<>();
        Vector<Integer> orderNumber = new Vector<>();
        Vector<Integer> averageDurations = new Vector<>();
        Vector<Integer> indexesToRemove = new Vector<>();
        Vector<Vector<Integer>> scheduledPatients = new Vector<>();

        double solutionCost = 0;
        double solutionTime = 0;
        double averageOccupationRate = 0;

//        int[] averageValues = {111, 133, 145, 81, 150, 72, 121, 137, 97, 150,
//            137, 121, 111, 111, 150, 97, 150, 149, 133, 97};//20
//        int[] averageValues = {112, 110, 133, 103, 97, 133, 58, 150, 45, 45, 81, 45, 45, 104, 88, 81, 153, 103, 83, 45, 97, 121, 203, 184, 97, 97, 97, 163, 97, 81, 97, 121, 58, 121, 133, 202, 97, 153, 45, 121, 110, 155, 55, 83, 150, 104, 85, 97, 97, 97, 58, 97, 97, 81, 133, 95, 45, 91, 111, 145, 97, 75, 145, 97, 55, 45, 156, 133, 81, 75, 192, 75, 126, 50, 103, 137, 71, 75, 90, 113, 105, 63, 111, 156, 103, 153, 97, 97, 155, 111, 121, 121, 149, 83, 184, 133, 111, 94, 182, 91};//100
        int[] averageValues = {112, 110, 133, 103, 97, 133, 58, 150, 45, 45, 81, 45, 45, 104, 88, 81, 153, 103, 83, 45,
            97, 121, 203, 184, 97, 97, 97, 163, 97, 81, 97, 121, 58, 121, 133, 202, 97, 153, 45, 121,
            110, 155, 55, 83, 150, 104, 85, 97, 97, 97, 58, 97, 97, 81, 133, 95, 45, 91, 111, 145,
            97, 75, 145, 97, 55, 45, 156, 133, 81, 75, 192, 75, 126, 50, 103, 137, 71, 75, 90, 113,
            105, 63, 111, 156, 103, 153, 97, 97, 155, 111, 121, 121, 149, 83, 184, 133, 111, 94, 182, 91};
//        int[] averageValues = {100, 194, 77, 184, 121, 81, 97, 97, 97, 85, 150, 153, 97, 150, 97, 150, 86, 97, 83, 121,
//            103, 105, 104, 110, 85, 153, 145, 145, 143, 83, 97, 155, 99, 149, 105, 45, 68, 156, 97, 111, 145, 153,
//            86, 145, 170, 71, 145, 81, 145, 110, 153, 103, 133, 150, 110, 45, 110, 133, 75, 150};//60

        for (int averageValue : averageValues) {
            averageDurations.add(averageValue);
            orderNumber.add(averageDurations.size());
        }

        try {
            do {
                if (solved == false && daysToPlan > 0) {
                    daysToPlan--;
                }
                if (daysToPlan == 0 || averageDurations.isEmpty()
                        || averageDurations.size() <= 2) {
                    break;
                }

                cplex = new IloCplex();// create model and  solve it
                int m;
                if (planningDays <= daysToPlan) {
                    m = planningDays;
                } else {
                    m = daysToPlan;
                }

                int n = averageDurations.size();
                int[] values = new int[n];
                int[] order = new int[n];
                for (int i = 0; i < n; i++) {
                    values[i] = averageDurations.get(i);
                    order[i] = orderNumber.get(i);
                }

                //define vectors for lower bound and upper bound
                IloIntVar[][] S = new IloIntVar[m][n];
                for (int i = 0; i < m; i++) {
                    S[i] = cplex.boolVarArray(n);
                }

                // alfa vector
                IloIntVar[] alfa = cplex.intVarArray(m, 0, tollerance);

                /**/  //objective
                System.out.println("*********** objective *************");
                double[][] c = new double[m][n];
                IloNumExpr cSum = cplex.constant(0);
                for (int i = 0; i < m; i++) {//from 0 to number of days
                    double val = (m - i) * beta;
                    System.out.print("c[" + i + "] = ");
                    for (int j = 0; j < n; j++) {//from 0 to number of patients
                        c[i][j] = (j + 1) * val;
                        System.out.print(c[i][j] + "\t");
                    }
                    System.out.println();

                    cSum = cplex.sum(cSum,
                            cplex.prod(alfa[i], cplex.constant(m - i)),
                            cplex.scalProd(c[i], S[i]));
                }
                IloObjective minimizeCost = cplex.addMinimize(cSum);
                System.out.println("************************\n");
                /**/

                // output vector for occupation per days, using alfa vector for boundaries
                for (int i = 0; i < m; i++) {
                    cplex.addEq(cplex.abs(cplex.diff(cplex.scalProd(values, S[i]), effectiveTime)), alfa[i]);
                }

                //constraint: sum on each column is <= 1
                for (int j = 0; j < n; j++) {
                    IloIntExpr Ssum = cplex.sum(S[0][j], 0);
                    for (int i = 1; i < m; i++) {
                        Ssum = cplex.sum(Ssum, S[i][j]);
                    }
                    cplex.addRange(0, Ssum, 1);
                }

                System.out.println("************ model ************");
                System.out.println(cplex.getModel().toString());
                System.out.println("************************");

                /**
                 * Solve the model.
                 */
                solved = cplex.solve();
                if (solved) {
                    //decrese the number of days ramained to schedule
                    daysToPlan -= m;

                    // get alfa values
                    double[] valAlfa = cplex.getValues(alfa);
                    // get total value of scheduled days
                    int s = (selectPlanningDays <= valAlfa.length ? selectPlanningDays : valAlfa.length); // select only first selectPlanningDays scheduled dates (or less)
                    for (int i = 0; i < s; i++) {
                        int valDay_i = 0;
                        Vector<Integer> scheduled_i = new Vector<>();

                        double[] valS = cplex.getValues(S[i]);
                        for (int j = 0; j < valS.length; j++) {
                            if (valS[j] > 0) {
                                valDay_i += values[j];
                                scheduled_i.add(order[j]);
                                indexesToRemove.add(j);
                            }
                        }

                        if (valDay_i == 0) {
                            continue;
                        }

                        scheduledDaysValues.add(valDay_i);
                        scheduledPatients.add(scheduled_i);
                        alfaValues.add((int)Math.round(valAlfa[i]));
                    }
                    indexesToRemove.sort(null);
                    while (!indexesToRemove.isEmpty()) {
                        int idx = indexesToRemove.lastElement();
                        averageDurations.remove(idx);
                        orderNumber.remove(idx);
                        indexesToRemove.removeElement(idx);
                    }
                    solutionCost += cplex.getObjValue();
                    solutionTime += cplex.getDetTime();
                }
            } while (solved || daysToPlan > 0);

            for (int i = 0; i < scheduledDaysValues.size(); i++) {
                cplex.output().println("alfa[" + i + "]= " + alfaValues.get(i));
            }
            for (int i = 0; i < scheduledDaysValues.size(); i++) {
                cplex.output().println("day[" + i + "]= " + scheduledDaysValues.get(i) + " ( " + (scheduledDaysValues.get(i) * 100 / 420.0) + " %)");
                averageOccupationRate += scheduledDaysValues.get(i) * 100 / 420.0;
            }
            for (int i = 0; i < scheduledDaysValues.size(); i++) {
                cplex.output().print("patients[" + i + "]: ");
                for (Integer pt : scheduledPatients.get(i)) {
                    cplex.output().print(pt + " ");
                }
                cplex.output().println();
            }

            averageOccupationRate = averageOccupationRate / scheduledDaysValues.size();

            cplex.output().println("\nSolution cost = " + solutionCost);
            cplex.output().println("Solution time = " + solutionTime + " [sec]");
            cplex.output().println("Average_Occupation_Rate = " + averageOccupationRate + " [%]\n");

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        } finally {
            cplex.end();
        }
    }
}
