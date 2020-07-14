
package website.managebugsfreeapp.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findAllNotesByLBTId", resultClass = LBTReportAdditionalNotes.class,
        query = "SELECT * FROM LBTReportAdditionalNotes WHERE lbt_report_id = #lbtReportId")
public class LBTReportAdditionalNotes implements Serializable {

    private static final long serialVersionUID = 11L;
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "lbt_reports_additional_notes_sequence")
    @SequenceGenerator(name="lbt_reports_additional_notes_sequence", allocationSize=1, 
            sequenceName="lbt_reports_additional_notes_sequence", initialValue=1)
    @Id
    @Column(name="lbt_note_id")
    private Integer lbtNoteId;
    
    @Column(name="lbt_report_id")
    @NotNull
    private int lbtReportId;
    
    @NotNull
    private String note;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="entered_by_user_id")
    private Users enteredByUserId;
    
    @Column(name="entry_date")
    @NotNull
    private Timestamp entryDate;

    public Integer getLbtNoteId() {
        return lbtNoteId;
    }

    public void setLbtNoteId(Integer lbtNoteId) {
        this.lbtNoteId = lbtNoteId;
    }

    public int getLbtReportId() {
        return lbtReportId;
    }

    public void setLbtReportId(int lbtReportId) {
        this.lbtReportId = lbtReportId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Users getEnteredByUserId() {
        return enteredByUserId;
    }

    public void setEnteredByUserId(Users enteredByUserId) {
        this.enteredByUserId = enteredByUserId;
    }

    public Timestamp getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Timestamp entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.lbtNoteId);
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
        final LBTReportAdditionalNotes other = (LBTReportAdditionalNotes) obj;
        if (!Objects.equals(this.lbtNoteId, other.lbtNoteId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LBTReportAdditionalNotes{" + "lbtNoteId=" + lbtNoteId + ", lbtReportId=" + lbtReportId + ", note=" + note + ", enteredByUserId=" + enteredByUserId + ", entryDate=" + entryDate + '}';
    }

    @PrePersist
	void onCreate() {
		this.setEntryDate(new Timestamp((new Date()).getTime()));
	}
    
}