/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.ejb;

import com.munderhill.entities.AggregateStatistics;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.postgresql.util.PGInterval;

/**
 *
 * @author mason
 */
@Stateless
public class StatisticsEJB {

    @PersistenceContext
    private EntityManager em;
    
    public List<AggregateStatistics> getActiveBRs(String team) {
        TypedQuery<AggregateStatistics> query = em.createNamedQuery("findActiveBRs", AggregateStatistics.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        List<AggregateStatistics> result = query.getResultList();
        return result;
    }
    
    public int getActiveLBTs(String team) {
        TypedQuery<Integer> query = em.createNamedQuery("findActiveLBTs", Integer.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        Integer result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    
    public List<AggregateStatistics> getCriticalActiveBRs(String team) {
        TypedQuery<AggregateStatistics> query = em.createNamedQuery("findCriticalActiveBRs", AggregateStatistics.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        List<AggregateStatistics> result = query.getResultList();
        return result;
    }
    
    public int getCriticalActiveLBTs(String team) {
        TypedQuery<Integer> query = em.createNamedQuery("findCriticalActiveLBTs", Integer.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        Integer result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    
    public int getBRsCompletedCurrentMonth(String team) {
        TypedQuery<Integer> query = em.createNamedQuery("findBRsCompletedCurrentMonth", Integer.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        Integer result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    
    public int getLBTsCompletedCurrentMonth(String team) {
        TypedQuery<Integer> query = em.createNamedQuery("findLBTsCompletedCurrentMonth", Integer.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        Integer result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    
    public int getBRsCompletedYTD (String team) {
        TypedQuery<Integer> query = em.createNamedQuery("findBRsCompletedYTD", Integer.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        Integer result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    
    public int getLBTsCompletedYTD (String team) {
        TypedQuery<Integer> query = em.createNamedQuery("findLBTsCompletedYTD", Integer.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        Integer result = ((Number) query.getSingleResult()).intValue();
        return result;
    }
    
    public String getBRTimeCompletedCurrentMonth(String team) {
        TypedQuery<PGInterval> query = em.createNamedQuery("findBRTimeCompletedCurrentMonth", PGInterval.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        PGInterval resultPG = (PGInterval) query.getSingleResult();
        String result = pgIntervalToStringConverter(resultPG);
        return result;
    }
    
    public String getLBTTimeCompletedCurrentMonth(String team) {
        TypedQuery<PGInterval> query = em.createNamedQuery("findLBTTimeCompletedCurrentMonth", PGInterval.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
       PGInterval resultPG = (PGInterval) query.getSingleResult();
        String result = pgIntervalToStringConverter(resultPG);
        return result;
    }
    
    public String getBRTimeCompletedYTD(String team) {
        TypedQuery<PGInterval> query = em.createNamedQuery("findBRTimeCompletedYTD", PGInterval.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        PGInterval resultPG = (PGInterval) query.getSingleResult();
        String result = pgIntervalToStringConverter(resultPG);
        return result;
    }
    
    public String getLBTTimeCompletedYTD(String team) {
        TypedQuery<PGInterval> query = em.createNamedQuery("findLBTTimeCompletedYTD", PGInterval.class);
        if(team.equals("all")) {
            query.setParameter("team", '%');
        }
        else {
            query.setParameter("team", team);
        }
        PGInterval resultPG = (PGInterval) query.getSingleResult();
        String result = pgIntervalToStringConverter(resultPG);
        return result;
    }
    
    public String pgIntervalToStringConverter(PGInterval resultPG) {
        if(resultPG == null) {
            return "no time interval available";
        }
        else if (resultPG.getYears() > 0) {
            return resultPG.getYears() + " years " + resultPG.getMonths() + " months " +
            resultPG.getDays() + " days " + resultPG.getHours() + " hours";
        }
        else if (resultPG.getMonths() > 0) {
            return resultPG.getMonths() + " months " +
            resultPG.getDays() + " days " + resultPG.getHours() + " hours";
        }
        else {
            return resultPG.getDays() + " days " + resultPG.getHours() + " hours";
        }
    }
    
}
