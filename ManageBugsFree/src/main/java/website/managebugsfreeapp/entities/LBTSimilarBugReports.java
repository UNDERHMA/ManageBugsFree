
package website.managebugsfreeapp.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mason
 */
@Entity
@Cacheable(false)
@Table(name = "lbtsimilarbugreports")
public class LBTSimilarBugReports implements Serializable {

    private static final long serialVersionUID = 12L;
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "lbt_similar_bug_reports_sequence")
    @SequenceGenerator(name="lbt_similar_bug_reports_sequence", allocationSize=1, 
            sequenceName="lbt_similar_bug_reports_sequence", initialValue=1)
    @Column(name="lbt_similar_bug_reports_id")
    @Id
    private Integer lbtSimilarBugReportsId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="lbt_report_id", nullable=false)
    private LBTReport lbtReport;
    
    @Column(name="similar_bug_report_id")
    private int similarBugReportId;

    public Integer getLbtSimilarBugReportsId() {
        return lbtSimilarBugReportsId;
    }

    public void setLbtSimilarBugReportsId(Integer lbtSimilarBugReportsId) {
        this.lbtSimilarBugReportsId = lbtSimilarBugReportsId;
    }

    public LBTReport getLbtReport() {
        return lbtReport;
    }

    public void setLbtReport(LBTReport lbtReport) {
        this.lbtReport = lbtReport;
    }

    public int getSimilarBugReportId() {
        return similarBugReportId;
    }

    public void setSimilarBugReportId(int similarBugReportId) {
        this.similarBugReportId = similarBugReportId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.lbtSimilarBugReportsId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LBTSimilarBugReports other = (LBTSimilarBugReports) obj;
        if (!Objects.equals(this.lbtSimilarBugReportsId, other.lbtSimilarBugReportsId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LBTSimilarBugReports{" + "lbtSimilarBugReportsId=" + lbtSimilarBugReportsId + ", lbtReport=" + lbtReport.getLbtReportId() + ", similarBugReportId=" + similarBugReportId + '}';
    }
    
}
