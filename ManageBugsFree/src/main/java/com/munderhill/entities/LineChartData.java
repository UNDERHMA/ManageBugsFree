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
@NamedNativeQuery(name = "findBugsOpenedByMonth", resultClass = LineChartData.class, 
        query = "--to char/date_part technique from Marcel Chastain https://stackoverflow.com/questions/15691127/postgresql-query-to-count-group-by-day-and-display-days-with-no-data/22394921\n" +
        "--::text technique from pnorton https://stackoverflow.com/questions/36869703/extract-month-from-date-field\n" +
        "SELECT m.month_timestamp as yearMonth, count(br.bug_report_id) as count \n" +
        "FROM\n" +
        "	(SELECT \n" +
        "	to_char(date_trunc('month', (current_date - days)), 'YYYY-MM-DD') AS month_timestamp, \n" + 
        "	to_char(date_trunc('day', (current_date - days)), 'YYYY-MM-DD') AS day_timestamp\n" +
        "	FROM generate_series(0, 365, 1) AS days) m\n" +
        "LEFT JOIN BugReports br on (date_trunc('day', (br.date_created))) = cast(m.day_timestamp as timestamp)\n" +
        "GROUP BY m.month_timestamp\n" +
        "ORDER BY m.month_timestamp")
@NamedNativeQuery(name = "findBugsClosedByMonth", resultClass = LineChartData.class, 
        query = "--to char/date_part technique from Marcel Chastain https://stackoverflow.com/questions/15691127/postgresql-query-to-count-group-by-day-and-display-days-with-no-data/22394921\n" +
        "--::text technique from pnorton https://stackoverflow.com/questions/36869703/extract-month-from-date-field\n" +
        "SELECT m.month_timestamp as yearMonth, count(br.bug_report_id) as count \n" +
        "FROM\n" +
        "	(SELECT \n" +
        "	to_char(date_trunc('month', (current_date - days)), 'YYYY-MM-DD') AS month_timestamp, \n" +         
        "	to_char(date_trunc('day', (current_date - days)), 'YYYY-MM-DD') AS day_timestamp\n" +
        "	FROM generate_series(0, 365, 1) AS days) m\n" +
        "LEFT JOIN BugReports br on (date_trunc('day', (br.date_first_closed))) = cast(m.day_timestamp as timestamp)\n" +
        "GROUP BY m.month_timestamp\n" +
        "ORDER BY m.month_timestamp")
@NamedNativeQuery(name = "findAvgBugsOutstandingPerMonth", resultClass = LineChartData.class, 
        query = "--to char/date_part technique from Marcel Chastain https://stackoverflow.com/questions/15691127/postgresql-query-to-count-group-by-day-and-display-days-with-no-data/22394921\n" +
        "--::text technique from pnorton https://stackoverflow.com/questions/36869703/extract-month-from-date-field\n" +
        "SELECT m.month_timestamp as yearMonth, count(br.bug_report_id)/(count(distinct(m.day))) as count \n" +
        "FROM\n" +
        "	(SELECT to_char(to_timestamp \n" +
        "	(date_part('day', (current_date - days))::text, 'DD'), 'Day') AS day,\n" +
        "	to_char(date_trunc('month', (current_date - days)), 'YYYY-MM-DD') AS month_timestamp\n" +
        "	FROM generate_series(0, 365, 1) AS days) m\n" +
        "LEFT JOIN BugReports br on (date_trunc('month', (br.date_first_closed))) <= cast(m.month_timestamp as timestamp)\n" +
        "AND ((date_trunc('month', (br.date_first_closed))) >= cast(m.month_timestamp as timestamp) or br.date_first_closed ISNULL)\n" +
        "GROUP BY m.month_timestamp\n" +
        "ORDER BY m.month_timestamp")
public class LineChartData implements Serializable {

    private static final long serialVersionUID = 21L;
    
    @Id
    private String yearMonth;;
    
    private int count;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
