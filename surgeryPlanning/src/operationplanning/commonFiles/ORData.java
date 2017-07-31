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
package operationplanning.commonFiles;

import java.util.Date;

/**
 *
 * @author Diana Botez <dia.botez at gmail.com>
 */
public class ORData {

    public Date date;
    public String startingHour;
    public String endingHour;
    public String teamName;

    public ORData(Date aDate, String start, String end, String teamName) {
        this.date = aDate;
        this.startingHour = start;
        this.endingHour = end;
        this.teamName = teamName;
    }
}
