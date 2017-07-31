/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operationplanning.cplex.files;

import ilog.concert.*;
import ilog.cplex.*;
import java.util.Date;
import java.util.Vector;
import operationplanning.commonFiles.DatabaseQueries;
import operationplanning.commonFiles.PatientIdentifiers;
import operationplanning.commonFiles.SurgeriesList;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class Opep {

    private static IloCplex cplex;
    private Vector<PatientIdentifiers> patientsList;

    /**
     * The public constructor for this class which sets the default algorithm to
     * be used as MILP.
     */
    public Opep() {
        patientsList = new Vector<>();
    }

    /**
     * The main method to create a schedule. If the algorithm is not changed
     * before calling this method, the default algorithm called is MILP.
     *
     * @param m number of days to schedule
     * @param dayPercentage
     * @param dayDeviation
     * @param beta
     * @param teamId
     *
     * @return a matrix with 2 rows with the IDs of the scheduled patients and
     * his scheduled surgery index
     */
    public Vector<Vector<Integer>> opep_main(int m, int dayPercentage, int dayDeviation,
            double beta, String teamId) {
        return milpFunction(m, dayPercentage, dayDeviation, beta, teamId);
    }

    public double opep_generateOccupancyRate(int confidenceLevel) {
        return miqcpFunction(confidenceLevel);
    }

    /**
     * This method sets the patient list to be used in the next schedule.
     *
     * @param list the patient list
     */
    public void setPatientList(Vector<PatientIdentifiers> list) {
        patientsList.clear();

        for (PatientIdentifiers patient : list) {
            patientsList.add(patient);
        }
    }

    /**
     * This method creates a MILP schedule for the specified number of days.
     *
     * @param m number of days to schedule
     * @param dayPercentage the desired occupation rate in percentage
     * @param dayDeviation the acceptable deviation from the desired percentage
     * rate
     *
     * @return a vector with the IDs of the scheduled patients
     */
    private Vector<Vector<Integer>> milpFunction(int numberOfDaysToPlan, int dayPercentage, int dayDeviation,
            double beta, String teamId) {
        int planningDays = 7;
        int daysToPlan = numberOfDaysToPlan;
        int selectPlanningDays = 5;
        boolean solved = true;
        double percentage = dayPercentage;
        DatabaseQueries db = new DatabaseQueries();

        //get dates for team
        Vector<Vector<String>> availableDatesDetails = new DatabaseQueries().getAvailableDatesToScheduleForTeam(teamId);

        Vector<Integer> totalDayTime = new Vector<>();//db.getTotalTimeForAllAvailableDatesToScheduleForTeam(teamId);
        Vector<Integer> tollerance = new Vector<>();
        Vector<Integer> effectiveDayTime = new Vector<>();

        for (Vector<String> datesDetails : availableDatesDetails) {
            totalDayTime.add(Integer.parseInt(datesDetails.get(2)));
        }

        if (totalDayTime.isEmpty()) {
            effectiveDayTime.add((int) (percentage * 6.5 / 100));
            tollerance.add((int) (dayDeviation * 6.5 / 100));
        } else {
            for (Integer totalTime : totalDayTime) {
                effectiveDayTime.add((int) (percentage * totalTime / 100));
                tollerance.add((int) (dayDeviation * totalTime / 100));
            }
        }

        Vector<Double> alfaValues = new Vector<>();
        Vector<Double> scheduledDaysValues = new Vector<>();
        Vector<Integer> patientID = new Vector<>();
        Vector<Integer> patientSurgeryId = new Vector<>();
        Vector<Integer> averageDurations = new Vector<>();
        Vector<Integer> indexesToRemove = new Vector<>();
        Vector<Vector<Integer>> scheduledPatientsId = new Vector<>();
        Vector<Vector<Integer>> scheduledSurgeriesId = new Vector<>();

        double solutionCost = 0;
        double solutionTime = 0;
        double averageOccupationRate = 0;

        //get surgery avg for each surgery of each patient
        SurgeriesList surgeriesList = new SurgeriesList();

        for (PatientIdentifiers patient : patientsList) {
            Vector<String> surgery = patient.medicalHistory.surgery;
            Vector<Boolean> scheduled = patient.medicalHistory.scheduled;
            int j = 0;
            for (Boolean sch : scheduled) {
                if (false == sch) {
                    String srg = surgery.get(j);
                    patientID.add(patient.patientID);
                    patientSurgeryId.add(surgeriesList.getSurgeryIdByName(srg));
                    averageDurations.add(surgeriesList.getSurgeryAverageDuration(srg));
                }
                j++;
            }
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
                int[][] patientIdAndSurgeryId = new int[2][n];
                for (int i = 0; i < n; i++) {
                    values[i] = averageDurations.get(i);
                    patientIdAndSurgeryId[0][i] = patientID.get(i);
                    patientIdAndSurgeryId[1][i] = patientSurgeryId.get(i);
                }

                IloIntVar[][] S = new IloIntVar[m][n];
                for (int i = 0; i < m; i++) {
                    S[i] = cplex.boolVarArray(n);
                }

                // alfa vector
                IloIntVar[] alfa = new IloIntVar[m];
                for (int i = 0; i < m; i++) {
                    alfa[i] = cplex.intVar(0, tollerance.get(i));// change this for each day
                }

                /**
                 * Objectives.
                 */
                double[][] c = new double[m][n];
                IloNumExpr cSum = cplex.constant(0);
                for (int i = 0; i < m; i++) {//from 0 to number of days
                    double val = (m - i) * beta;
                    for (int j = 0; j < n; j++) {//from 0 to number of patients
                        c[i][j] = (j + 1) * val;
                    }

                    cSum = cplex.sum(cSum,
                            cplex.prod(alfa[i], cplex.constant(m - i)),
                            cplex.scalProd(c[i], S[i]));
                }
                IloObjective minimizeCost = cplex.addMinimize(cSum);

                // output vector for occupation per days, using alfa vector for boundaries
                for (int i = 0; i < m; i++) {
                    cplex.addEq(cplex.abs(cplex.diff(cplex.scalProd(values, S[i]), effectiveDayTime.get(i))), alfa[i]);
                }

                //constraint: sum on each column is <= 1
                for (int j = 0; j < n; j++) {
                    IloIntExpr Ssum = cplex.sum(S[0][j], 0);
                    for (int i = 1; i < m; i++) {
                        Ssum = cplex.sum(Ssum, S[i][j]);
                    }
                    cplex.addLe(Ssum, 1);
                }

                /**
                 * Solve the model.
                 */
                solved = cplex.solve();

                if (solved) {
                    // get alfa values
                    double[] valAlfa = cplex.getValues(alfa);

                    // get total value of scheduled days
                    int s = (selectPlanningDays <= valAlfa.length ? selectPlanningDays : valAlfa.length); // select only first selectPlanningDays scheduled dates (or less)
                    //decrese the number of days ramained to schedule
                    daysToPlan -= s;
                    for (int i = 0; i < s; i++) {
                        double valDay_i = 0;
                        Vector<Integer> scheduledPatientsId_day_i = new Vector<>();
                        Vector<Integer> scheduledSurgeriesId_day_i = new Vector<>();

                        double[] valS = cplex.getValues(S[i]);
                        for (int j = 0; j < valS.length; j++) {
                            if (valS[j] > 0) {
                                valDay_i += values[j];
                                scheduledPatientsId_day_i.add(patientIdAndSurgeryId[0][j]);
                                scheduledSurgeriesId_day_i.add(patientIdAndSurgeryId[1][j]);
                                indexesToRemove.add(j);
                            }
                        }

                        if (valDay_i == 0) {
                            continue;
                        }

                        scheduledDaysValues.add(valDay_i);
                        scheduledPatientsId.add(scheduledPatientsId_day_i);
                        scheduledSurgeriesId.add(scheduledSurgeriesId_day_i);
                        alfaValues.add(valAlfa[i]);
                    }
                    indexesToRemove.sort(null);
                    while (!indexesToRemove.isEmpty()) {
                        int idx = indexesToRemove.lastElement();
                        averageDurations.remove(idx);
                        patientID.remove(idx);
                        patientSurgeryId.remove(idx);
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
                cplex.output().println("day[" + i + "]= " + scheduledDaysValues.get(i) + " ( " + (scheduledDaysValues.get(i) / totalDayTime.get(i) * 100) + " %)");
                averageOccupationRate += scheduledDaysValues.get(i) / totalDayTime.get(i) * 100;
            }
            for (int i = 0; i < scheduledDaysValues.size(); i++) {
                cplex.output().print("patients[" + i + "]: ");
                for (Integer pt : scheduledPatientsId.get(i)) {
                    cplex.output().print(pt + " ");
                }
                cplex.output().println();
            }

            if (!scheduledDaysValues.isEmpty()) {
                averageOccupationRate = averageOccupationRate / scheduledDaysValues.size();

                cplex.output().println("Solution time = " + solutionTime + " [sec]");
                cplex.output().println("\nSolution cost = " + Double.toString(solutionCost));
                cplex.output().println("Average_Occupation_Rate = " + averageOccupationRate + " [%]\n");
            }
        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
        } finally {
            if (cplex != null) {
                cplex.end();
            }
        }

        if (scheduledPatientsId.isEmpty()) {
            return null;
        }

        int index = 0;
        Date d;

        for (Vector<Integer> dayScheduledPatients : scheduledPatientsId) {
            int element = 0;
            for (Integer patientId : dayScheduledPatients) {
                db.schedulePatient("P" + patientId, "SRG" + scheduledSurgeriesId.get(index).get(element),
                        availableDatesDetails.get(index).get(1));
                element++;
            }
            index++;
        }

        return scheduledPatientsId;
    }

    /**
     * This method creates a MIQCP schedule for the specified number of days
     * with the specified confidence level.
     *
     * @param m the number of days to schedule
     * @param confidenceLevel the confidence level of not overcoming the working
     * hours
     *
     * @return
     */
    private double miqcpFunction(int confidenceLevel) {
        int dayTime = (int) (7 * 60); //7 hours in minutes

        /* Value of confidence level */
//        double desiredConfidenceLevel = 80; // out of 100 (in percentage)
        double Vcl = 0.8416; //  for desiredConfidenceLevel = 80% -> Vcl = 0.8416 (from tables)
        double beta = 1;//0.333;

        int daysToPlan = 130;// 52[weeks/year]/2 = 26[weeks/halfOfYear] => 130[workingDays/halfOfYear]
        int planningDays = 2; //days to plann in each iteration
        boolean solved = true;

        int cleaningAverageDuration = 20; // minutes
        int cleaningStandardDeviation = 10; // minutes

        Vector<Double> scheduledDaysValues = new Vector<>();
        Vector<Integer> averageDurations = new Vector<>();
        Vector<Integer> stdDeviation = new Vector<>();
        Vector<Integer> indexesToRemove = new Vector<>();

        double averageOccupationRate = 0;

        //get surgery avg for each surgery of each patient
        SurgeriesList surgeriesList = new SurgeriesList();

        int inc = 0;
        for (PatientIdentifiers patient : patientsList) {
            Vector<String> surgery = patient.medicalHistory.surgery;
            Vector<Boolean> scheduled = patient.medicalHistory.scheduled;
            int j = 0;
            for (Boolean sch : scheduled) {
                if (false == sch) {
                    String srg = surgery.get(j);
                    averageDurations.add(surgeriesList.getSurgeryAverageDuration(srg));
                    stdDeviation.add(surgeriesList.getSurgeryStandarDeviation(srg));
                }
                j++;
            }
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
                int[] standardDeviation = new int[n];
                for (int i = 0; i < n; i++) {
                    values[i] = averageDurations.get(i);
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
                double[][] c = new double[m][n];
                IloNumExpr cSum = cplex.constant(0);
                for (int i = 0; i < m; i++) {//from 0 to number of days
                    double val = (m - i) * beta;
                    for (int j = 0; j < n; j++) {//from 0 to number of patients
                        c[i][j] = (j + 1) * val;
                    }

                    cSum = cplex.sum(cSum,
                            cplex.prod(alfa[i], cplex.constant(m - i)),
                            cplex.scalProd(c[i], S[i]));
                }
                IloObjective minimizeCost = cplex.addMinimize(cSum);

                /**
                 * Constrains.
                 */
                int[] A = new int[n];
                double[] B = new double[n];
                double[] K = new double[n];
                double correctionTime = (Vcl * Vcl * 12 * 12) - (Vcl * Vcl * 10 * 10);

                for (int i = 0; i < n; i++) {
                    A[i] = values[i] + cleaningAverageDuration;
                    B[i] = (standardDeviation[i] * standardDeviation[i] + cleaningStandardDeviation * cleaningStandardDeviation);
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
                 * Solve the model.
                 */
                solved = cplex.solve();
                if (solved) {
                    //decrese the number of days ramained to schedule
                    daysToPlan -= m;

                    // get alfa values
//                    double[] valAlfa = cplex.getValues(alfa);
                    // get total value of scheduled days
                    for (int i = 0; i < m; i++) {
                        double valDay_i = 0;

                        double[] valS = cplex.getValues(S[i]);
                        for (int j = 0; j < valS.length; j++) {
                            if (valS[j] > 0) {
                                valDay_i += values[j];
                                indexesToRemove.add(j);
                            }
                        }

                        if (valDay_i == 0) {
                            continue;
                        }

                        scheduledDaysValues.add(valDay_i);
                    }
                    indexesToRemove.sort(null);
                    while (!indexesToRemove.isEmpty()) {
                        int idx = indexesToRemove.lastElement();
                        averageDurations.remove(idx);
                        stdDeviation.remove(idx);
                        indexesToRemove.removeElement(idx);
                    }
                }
            } while (solved || daysToPlan > 0);

            for (int i = 0; i < scheduledDaysValues.size(); i++) {
                averageOccupationRate += scheduledDaysValues.get(i) / dayTime * 100;
            }

            averageOccupationRate = averageOccupationRate / scheduledDaysValues.size();

        } catch (IloException e) {
            System.err.println("Concert exception caught: " + e);
            averageOccupationRate = 0;
        } finally {
            cplex.end();
        }

        return averageOccupationRate;
    }
}
