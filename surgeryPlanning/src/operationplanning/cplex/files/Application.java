/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operationplanning.cplex.files;

import ilog.concert.*;
import ilog.cplex.*;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class Application {

    private static void _main(String[] args) {
        try {
            IloCplex cplex = new IloCplex();
            // create model and  solve it

            int[] lb = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int[] ub = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            String[] str = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4"};
            IloIntVar[] x = cplex.intVarArray(12, lb, ub, str);
            int[] objvals = {6, 7, 9, 11, 12, 15, 5, 8, 12, 16, 19, 20};

//            double[] lb = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
//            double[] ub = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
//            String[] str = {"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "C1", "C2", "C3", "C4"};
//            IloNumVar[] x = cplex.numVarArray(12, lb, ub, str);
//            double[] objvals = {6.0, 7.0, 9.0, 11.0, 12.0, 15.0, 5.0, 8.0, 12.0, 16.0, 19.0, 20.0};

            //objective
            cplex.addMaximize(cplex.scalProd(x, objvals));

            IloIntExpr s1 = cplex.sum(cplex.prod(13, x[0]),
                    cplex.prod(20, x[1]),
                    cplex.prod(24, x[2]),
                    cplex.prod(30, x[3]),
                    cplex.prod(39, x[4]),
                    cplex.prod(45, x[5]));
//            IloNumExpr s1 = cplex.sum(cplex.prod(13.0, x[0]),
//                    cplex.prod(20.0, x[1]),
//                    cplex.prod(24.0, x[2]),
//                    cplex.prod(30.0, x[3]),
//                    cplex.prod(39.0, x[4]),
//                    cplex.prod(45.0, x[5]));
            IloIntExpr s2 = cplex.sum(cplex.prod(12, x[6]),
                    cplex.prod(20, x[7]),
                    cplex.prod(30, x[8]),
                    cplex.prod(44, x[9]),
                    cplex.prod(48, x[10]),
                    cplex.prod(55, x[11]));
//            IloNumExpr s2 = cplex.sum(cplex.prod(12, x[6]),
//                    cplex.prod(20.0, x[7]),
//                    cplex.prod(30.0, x[8]),
//                    cplex.prod(44.0, x[9]),
//                    cplex.prod(48.0, x[10]),
//                    cplex.prod(55.0, x[11]));

            cplex.addLe(cplex.sum(s1, s2), 100);

            /**/
            //add more constrains
            cplex.addLe(cplex.sum(x[0], x[4], x[8]), 1);
            cplex.addEq(cplex.sum(x[1], x[5], x[9]), 1);
            cplex.addLe(cplex.sum(x[2], x[6], x[10]), 1);
            cplex.addLe(cplex.sum(x[3], x[7], x[11]), 1);
            /**/

            if (cplex.solve()) {
                cplex.output().println("Solution status = " + cplex.getStatus());
                cplex.output().println("Solution value = " + cplex.getObjValue());

                double[] val = cplex.getValues(x);
                int ncol = cplex.getNcols();
                for (int j = 0; j < ncol; j++) {
                    cplex.output().println(str[j] + " = " + val[j]
                    );
                }
                cplex.end();
            }

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        }
    }
}
