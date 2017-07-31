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

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class milp {

    private static IloCplex cplex;

    public static void __main(String[] args) {

        int m = 25;// number of days

        try {
            cplex = new IloCplex();// create model and  solve it

            double percentage = 80;
            int dayTime = 7 * 60; //7 hours in minutes

            int effectiveTime = (int) (percentage * dayTime / 100);
            int tollerance = (int) (10 * dayTime / 100);//deviation

            int n = 100; // strlen(values)
//            int[] values = {111, 133, 145, 81, 150, 72, 121, 137, 97, 150, 137, 121, 111, 111, 150, 97, 150, 149, 133, 97, 81, 111};//30
//            int[] values = {100, 194, 77, 184, 121, 81, 97, 97, 97, 85, 150, 153, 97, 150, 97, 150, 86, 97, 83, 121,
//                103, 105, 104, 110, 85, 153, 145, 145, 143, 83, 97, 155, 99, 149, 105, 45, 68, 156, 97, 111, 145, 153, 86, 145, 170, 71, 145, 81, 145, 110, 153, 103, 133, 150, 110, 45, 110, 133, 75, 150};//60
            int[] values = {112, 110, 133, 103, 97, 133, 58, 150, 45, 45, 81, 45, 45, 104, 88, 81, 153, 103, 83, 45, 97, 121, 203, 184, 97, 97, 97, 163, 97, 81, 97, 121, 58, 121, 133, 202, 97, 153, 45, 121, 110, 155, 55, 83, 150, 104, 85, 97, 97, 97, 58, 97, 97, 81, 133, 95, 45, 91, 111, 145, 97, 75, 145, 97, 55, 45, 156, 133, 81, 75, 192, 75, 126, 50, 103, 137, 71, 75, 90, 113, 105, 63, 111, 156, 103, 153, 97, 97, 155, 111, 121, 121, 149, 83, 184, 133, 111, 94, 182, 91};//100
            
            //define vectors for lower bound and upper bound
            int[] lb = new int[n];
            int[] ub = new int[n];
            for (int i = 0; i < n; i++) {
                lb[i] = 0;
                ub[i] = 1;
            }

            IloIntVar[][] S = new IloIntVar[m][n];
            for (int i = 0; i < m; i++) {
                S[i] = cplex.intVarArray(n, lb, ub);
            }

            // alfa vector
            IloIntVar[] alfa = cplex.intVarArray(m, 0, tollerance);
            
            /**/  //objective
            double beta = 2;

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
//                cplex.addRange(0, cplex.scalProd(values, S[i]), dayTime);
                cplex.addLe(cplex.abs(cplex.diff(cplex.scalProd(values, S[i]), effectiveTime)), alfa[i]);
//                cplex.addRange(0, cplex.abs(cplex.diff(cplex.scalProd(values, S[i]), effectiveTime)), alfa[i].getUB());
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
            if (cplex.solve()) {
                cplex.output().println("\nSolution status = " + cplex.getStatus());
                cplex.output().println("Solution value = " + cplex.getObjValue());

                double[] valAlfa = cplex.getValues(alfa);
                for (int j = 0; j < valAlfa.length; j++) {
                    cplex.output().println("alfa " + j + " = " + valAlfa[j]);
                }

                double[] valDays = new double[m];
                for (int i = 0; i < m; i++) {
                    valDays[i] = 0;
                    double[] valS = cplex.getValues(S[i]);
                    cplex.output().print("Patients in day[" + i + "]: ");
                    for (int j = 0; j < valS.length; j++) {
                        if (valS[j] > 0) {
                            valDays[i] += values[j];
                            cplex.output().print(j + "  ");
                        }
                    }
                    cplex.output().println();
                }

                for (int j = 0; j < valDays.length; j++) {
                    cplex.output().println("day " + j + " = " + valDays[j] + " ( " + (valDays[j] / dayTime * 100) + " %)");
                }

                cplex.end();
            }
        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }
}
