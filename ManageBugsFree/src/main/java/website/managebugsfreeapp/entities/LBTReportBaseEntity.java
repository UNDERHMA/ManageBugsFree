package website.managebugsfreeapp.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author mason
 */

@MappedSuperclass
public abstract class LBTReportBaseEntity {
        
        @Column(name = "last_updated", insertable = true, updatable = true)
        private Timestamp lastUpdated;
        
        @Column(name = "updated_by", insertable = true, updatable = true)
        private String updatedBy = "NA";
        
        @Column(name = "date_created", insertable = true, updatable = true)
	private Timestamp dateCreated;
        
        @Column(name = "date_all_linked_brs_closed", insertable = true, updatable = true)
	private Timestamp dateAllLinkedBRsClosed;

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }
        
	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

        public Timestamp getDateAllLinkedBRsClosed() {
            return dateAllLinkedBRsClosed;
        }

        public void setDateAllLinkedBRsClosed(Timestamp dateAllLinkedBRsClosed) {
            this.dateAllLinkedBRsClosed = dateAllLinkedBRsClosed;
        }

	@PrePersist
	void onCreate() {
            // MIT License for Mkyong.com in package folder
            // Timestamp code for dates from https://mkyong.com/java/how-to-get-current-timestamps-in-java//
		this.setDateCreated(new Timestamp(System.currentTimeMillis()));
	}

	@PreUpdate
	void onPersistLastUpdated() {
            // MIT License for Mkyong.com in package folder
            // Timestamp code for dates from https://mkyong.com/java/how-to-get-current-timestamps-in-java//
		this.setLastUpdated(new Timestamp(System.currentTimeMillis()));
	}
        
}
