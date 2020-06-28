/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
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
        "SELECT m.month_timestamp as yearMonth, Case WHEN AVG(br.date_first_closed - br.date_created) IS NULL THEN 0 \n" +
        "ELSE EXTRACT(epoch from AVG(br.date_first_closed - br.date_created))/(3600*24) END as timeInterval \n" +
        "FROM\n" +
        "	(SELECT to_char(date_trunc('month', (current_date - days)), 'YYYY-MM-DD') AS month_timestamp\n" +
        "	FROM generate_series(0, 365, 1) AS days) m\n" +
        "LEFT JOIN BugReports br on (date_trunc('month', (br.date_first_closed))) = cast(m.month_timestamp as timestamp)\n" +
        "GROUP BY m.month_timestamp\n" +
        "ORDER BY m.month_timestamp")
public class LineChartDataWithInterval implements Serializable {

    private static final long serialVersionUID = 22L;
    
    @Id
    private String yearMonth;
    
    private int timeInterval;

    public String getYearMonth() {
        return yearMonth;
    }

    public void getYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
    
    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

}
