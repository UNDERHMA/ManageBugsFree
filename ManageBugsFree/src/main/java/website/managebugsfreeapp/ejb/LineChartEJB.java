
package website.managebugsfreeapp.ejb;

import website.managebugsfreeapp.entities.LineChartData;
import website.managebugsfreeapp.entities.LineChartDataWithInterval;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author mason
 */
@Stateless
public class LineChartEJB {

    @PersistenceContext
    private EntityManager em;
    
    public List<LineChartData> getBugsOpenedByDay() {
        TypedQuery<LineChartData> query = em.createNamedQuery("findBugsOpenedByMonth", LineChartData.class);
        List<LineChartData> result = query.getResultList();
        return result;
    }
    
    public List<LineChartData> getBugsClosedByDay() {
        TypedQuery<LineChartData> query = em.createNamedQuery("findBugsClosedByMonth", LineChartData.class);
        List<LineChartData> result = query.getResultList();
        return result;
    }
    
    public List<LineChartData> getBugsOutstandingOverTime() {
        TypedQuery<LineChartData> query = em.createNamedQuery("findAvgBugsOutstandingPerMonth", LineChartData.class);
        List<LineChartData> result = query.getResultList();
        return result;
    }
    
    public List<LineChartDataWithInterval> getAvgDaysToClosedOverTime() {
        TypedQuery<LineChartDataWithInterval> query = em.createNamedQuery("findAvgDaysToClosedPerMonth", LineChartDataWithInterval.class);
        List<LineChartDataWithInterval> result = query.getResultList();
        return result;
    }
    

}
