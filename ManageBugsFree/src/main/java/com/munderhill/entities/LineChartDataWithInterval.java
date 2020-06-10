/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findAvgDaysToClosedPerMonth", resultClass = LineChartDataWithInterval.class, 
        query = "--to char/date_part technique from Marcel Chastain https://stackoverflow.com/questions/15691127/postgresql-query-to-count-group-by-day-and-display-days-with-no-data/22394921\n" +
        "--::text technique from pnorton https://stackoverflow.com/questions/36869703/extract-month-from-date-field\n" +
        "SELECT CONCAT(m.month,m.year) as monthYear, AVG(br.date_first_closed - br.date_created) as interval \n" +
        "FROM\n" +
        "	(SELECT to_char(to_timestamp \n" +
        "	(date_part('month', (current_date - days))::text, 'MM'), 'Month') AS month,\n" +
        "	to_char(to_timestamp \n" +
        "	(date_part('year', (current_date - days))::text, 'YYYY'), 'YYYY') AS year,\n" +
        "	to_char(date_trunc('month', (current_date - days)), 'YYYY-MM-DD') AS month_timestamp\n" +
        "	FROM generate_series(0, 365, 1) AS days) m\n" +
        "LEFT JOIN BugReports br on (date_trunc('month', (br.date_first_closed))) = cast(m.month_timestamp as timestamp)\n" +
        "GROUP BY m.month,m.year\n" +
        "ORDER BY m.year, m.month")
public class LineChartDataWithInterval implements Serializable {

    private static final long serialVersionUID = 22L;
    
    @Id
    private String monthYear;
    
    private float timeInterval;

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }
    
    public float getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(float timeInterval) {
        this.timeInterval = timeInterval;
    }

}
