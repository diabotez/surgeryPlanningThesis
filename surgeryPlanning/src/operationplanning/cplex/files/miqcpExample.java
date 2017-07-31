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
public class miqcpExample {

    /* Cplex object */
    private static IloCplex cplex;

    public static void __main(String[] args) {
        int dayTime = (int) (7 * 60); //7 hours in minutes

        /* Value of confidence level */
//        double desiredConfidenceLevel = 80; // out of 100 (in percentage)
        double Vcl = 0.8416; //  for desiredConfidenceLevel = 80% -> Vcl = 0.8416 (from tables)
        double beta = 2;//0.333;

        int daysToPlan = 130;// 52[weeks/year]/2 = 26[weeks/halfOfYear] => 130[workingDays/halfOfYear]
        int planningDays = 2; //days to plann in each iteration
        boolean solved = true;

        int cleaningAverageDuration = 20; // minutes
        int cleaningStandardDeviation = 10; // minutes

        int[] averageValues = {112, 110, 133, 103, 97, 133, 58, 150, 45, 45, 81, 45, 45, 104, 88, 81, 153, 103, 83, 45,
            97, 121, 203, 184, 97, 97, 97, 163, 97, 81, 97, 121, 58, 121, 133, 202, 97, 153, 45, 121,
            110, 155, 55, 83, 150, 104, 85, 97, 97, 97, 58, 97, 97, 81, 133, 95, 45, 91, 111, 145,
            97, 75, 145, 97, 55, 45, 156, 133, 81, 75, 192, 75, 126, 50, 103, 137, 71, 75, 90, 113,
            105, 63, 111, 156, 103, 153, 97, 97, 155, 111, 121, 121, 149, 83, 184, 133, 111, 94, 182, 91};//100
//        int[] averageValues = {100, 194, 77, 184, 121, 81, 97, 97, 97, 85, 150, 153, 97, 150, 97, 150, 86, 97, 83, 121,
//            103, 105, 104, 110, 85, 153, 145, 145, 143, 83, 97, 155, 99, 149, 105, 45, 68, 156, 97, 111,
//            145, 153, 86, 145, 170, 71, 145, 81, 145, 110, 153, 103, 133, 150, 110, 45, 110, 133, 75, 150};//60

        int[] standardValues = {24, 23, 24, 40, 21, 24, 17, 55, 12, 12, 21, 12, 12, 30, 23, 21, 58, 40, 22, 12,
            21, 21, 74, 26, 21, 21, 21, 44, 21, 21, 21, 21, 17, 21, 24, 45, 21, 23, 12, 21,
            43, 48, 15, 22, 42, 30, 35, 21, 21, 21, 17, 21, 21, 21, 24, 21, 12, 22, 23, 27,
            21, 23, 38, 21, 23, 12, 42, 24, 21, 23, 55, 23, 29, 24, 40, 25, 25, 23, 19, 30,
            21, 17, 23, 42, 40, 23, 21, 21, 48, 23, 21, 21, 38, 22, 26, 24, 23, 20, 56, 22};//100
//        int[] standardValues = {28, 56, 29, 26, 21, 21, 21, 21, 21, 35, 55, 23, 21, 42, 21, 42, 32, 21, 22, 21,
//            40, 24, 30, 43, 35, 23, 38, 38, 24, 22, 21, 48, 20, 38, 21, 12, 19, 42, 21, 23,
//            27, 23, 20, 27, 40, 25, 27, 21, 27, 43, 23, 40, 41, 42, 23, 12, 43, 24, 23, 37};//60

        Vector<Double> alfaValues = new Vector<>();
        Vector<Double> scheduledDaysValues = new Vector<>();
        Vector<Integer> orderNumber = new Vector<>();
        Vector<Integer> averageDurations = new Vector<>();
        Vector<Integer> stdDeviation = new Vector<>();
        Vector<Integer> indexesToRemove = new Vector<>();
        Vector<Vector<Integer>> scheduledPatients = new Vector<>();

        double solutionCost = 0;
        double solutionTime = 0;
        double averageOccupationRate = 0;

//            int[] averageValues = {112, 110, 133, 103, 97, 133, 58, 150, 45, 45, 81, 45, 45, 104, 88, 81, 153, 103, 83, 45, 97, 121, 203, 184, 97, 97, 97, 163, 97, 81}; //30
//            int[] standardValues = {24, 23, 24, 40, 21, 24, 17, 55, 12, 12, 21, 12, 12, 30, 23, 21, 58, 40, 22, 12, 21, 21, 74, 26, 21, 21, 21, 44, 21, 21}; //30
        for (int averageValue : averageValues) {
            averageDurations.add(averageValue);
            orderNumber.add(averageDurations.size());
        }
        for (int std : standardValues) {
            stdDeviation.add(std);
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

                /**
                 * Create an instance of the cplex object and the model.
                 */
                cplex = new IloCplex();

                /**
                 * Variables.
                 */
                int m;
                if (planningDays <= daysToPlan) {
                    m = planningDays;
                } else {
                    m = daysToPlan;
                }

                int n = averageDurations.size();
                int[] values = new int[n];
                int[] order = new int[n];
                int[] standardDeviation = new int[n];
                for (int i = 0; i < n; i++) {
                    values[i] = averageDurations.get(i);
                    order[i] = orderNumber.get(i);
                    standardDeviation[i] = stdDeviation.get(i);
                }

                IloIntVar[][] S = new IloIntVar[m][n];
                for (int i = 0; i < m; i++) {
                    S[i] = cplex.boolVarArray(n);
                }

                /* alfa vector */
                IloIntVar[] alfa = cplex.intVarArray(m, 0, dayTime);

                /**
                 * Objective.
                 */
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

                /**
                 * Constrains.
                 */
                int[] A = new int[n];
                double[] B = new double[n];
                double[] K = new double[n];
                double correctionTime = (Vcl * Vcl * 12 * 12) - (Vcl * Vcl * 10 * 10);

                for (int i = 0; i < n; i++) {
                    A[i] = values[i] + cleaningAverageDuration;
                    B[i] = Math.sqrt(standardDeviation[i] * standardDeviation[i] + cleaningStandardDeviation * cleaningStandardDeviation);
                    K[i] = Vcl * Vcl * B[i] + 2 * (dayTime + 10) * A[i];
                }

                for (int i = 0; i < m; i++) {
                    cplex.addEq(cplex.diff(dayTime, cplex.scalProd(values, S[i])), alfa[i]); // first condition

                    IloNumExpr cuadraticExpr = cplex.scalProd(A, S[i]); // (a1*s1 + a2*s2 + ... + an*sn)
                    cplex.addLe(cplex.sum(cplex.diff(cplex.scalProd(K, S[i]), cplex.prod(cuadraticExpr, cuadraticExpr)), correctionTime), (dayTime + 10) * (dayTime + 10)); // third condition

                    cplex.addGe(cplex.diff(dayTime, cplex.scalProd(A, S[i])), 0); // forth condition
                }

                //second constraint: sum on each column is <= 1
                for (int j = 0; j < n; j++) {
                    IloIntExpr Ssum = cplex.sum(S[0][j], 0);
                    for (int i = 1; i < m; i++) {
                        Ssum = cplex.sum(Ssum, S[i][j]);
                    }
                    cplex.addLe(Ssum, 1);
                }

                /**
                 * Show current model.
                 */
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
                    for (int i = 0; i < m; i++) {
                        double valDay_i = 0;
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
                        alfaValues.add(valAlfa[i]);
                    }
                    indexesToRemove.sort(null);
                    while (!indexesToRemove.isEmpty()) {
                        int idx = indexesToRemove.lastElement();
                        averageDurations.remove(idx);
                        orderNumber.remove(idx);
                        stdDeviation.remove(idx);
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
                cplex.output().println("day[" + i + "]= " + scheduledDaysValues.get(i) + " ( " + (scheduledDaysValues.get(i) / dayTime * 100) + " %)");
                averageOccupationRate += scheduledDaysValues.get(i) / dayTime * 100;
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
