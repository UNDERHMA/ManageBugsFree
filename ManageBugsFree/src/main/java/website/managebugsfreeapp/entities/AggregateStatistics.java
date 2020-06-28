/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package website.managebugsfreeapp.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findActiveBRs", resultClass = AggregateStatistics.class, query = "SELECT status, count(*) as count \n" +
                "FROM BugReports WHERE team_name like #team GROUP BY status ORDER BY status ASC ")
@NamedNativeQuery(name = "findCriticalActiveBRs", resultClass = AggregateStatistics.class, query = "SELECT status, count(*) as count \n" +
                "FROM BugReports WHERE team_name like #team AND priority like '5' GROUP BY status ORDER BY status ASC ")
public class AggregateStatistics implements Serializable {

    private static final long serialVersionUID = 20L;
    
    @Id
    private String status = "";
    
    @Column(name="count")
    private int numberInEachStatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberInEachStatus() {
        return numberInEachStatus;
    }

    public void setNumberInEachStatus(int numberInEachStatus) {
        this.numberInEachStatus = numberInEachStatus;
    }

}
