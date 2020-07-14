
package website.managebugsfreeapp.ejb;

import website.managebugsfreeapp.entities.Users;
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
public class TeamsEJB {

    @PersistenceContext
    private EntityManager em;

    public List<String> teamsByType(String teamType){
        TypedQuery<String> query = em.createNamedQuery("findTeamsByType", String.class);
        if(teamType.equals("Admin")) {
            query.setParameter("teamType", "%");
            return query.getResultList();
        }
        query.setParameter("teamType", teamType);
        return query.getResultList();
    }
    
    public void saveSettings(Users user){
        em.merge(user);
    } 
}
