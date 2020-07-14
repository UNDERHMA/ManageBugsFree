
package website.managebugsfreeapp.controllers;

import website.managebugsfreeapp.ejb.StatisticsEJB;
import website.managebugsfreeapp.ejb.TeamsEJB;
import website.managebugsfreeapp.pojos.StatisticSummaryLBTReports;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import javax.inject.Named;

/**
 *
 * @author mason
 */
@FacesConfig(
        // Activates CDI build-in beans
        // CC BY-SA 4.0 License, available in package folder. Code snippet not changed in any way.
        // Tadas B. https://stackoverflow.com/questions/45682309/changing-faces-config-xml-from-2-2-to-2-3-causes-javax-el-propertynotfoundexcept
        version = JSF_2_3
)

@Named
@RequestScoped
public class TeamListControllerAllCSR {
    @EJB
    private TeamsEJB teamsEJB;
    @EJB
    private StatisticsEJB statisticsEJB;
    private List<String> teams;
    private List<StatisticSummaryLBTReports> statisticSummaryList = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        teams = teamsEJB.teamsByType("Customer Support");
        for (int i = 0; i < teams.size(); i++) {
            StatisticSummaryLBTReports statisticSummary = new StatisticSummaryLBTReports();
            statisticSummary.setTitle(teams.get(i));
            statisticSummary.setActiveLBTs(statisticsEJB.getActiveLBTs(teams.get(i)));
            statisticSummary.setActiveCriticalLBTs(statisticsEJB.getCriticalActiveLBTs(teams.get(i)));
            statisticSummary.setLbtsCompletedCurrentMonth(statisticsEJB.getLBTsCompletedCurrentMonth(teams.get(i)));
            statisticSummary.setLbtsCompletedYTD(statisticsEJB.getLBTsCompletedYTD(teams.get(i)));
            statisticSummary.setAvgCompletionTimeCurrentMonth(statisticsEJB.getLBTTimeCompletedCurrentMonth(teams.get(i)));
            statisticSummary.setAvgCompletionTimeYTD(statisticsEJB.getLBTTimeCompletedYTD(teams.get(i)));
            statisticSummaryList.add(statisticSummary);
        }
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<StatisticSummaryLBTReports> getStatisticSummaryList() {
        return statisticSummaryList;
    }

    public void setStatisticSummaryList(List<StatisticSummaryLBTReports> statisticSummaryList) {
        this.statisticSummaryList = statisticSummaryList;
    }

}
