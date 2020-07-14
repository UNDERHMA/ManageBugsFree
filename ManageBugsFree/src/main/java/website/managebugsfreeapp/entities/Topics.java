
package website.managebugsfreeapp.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findAllTopics", query = "SELECT topic_name FROM Topics ORDER BY topic_name ASC")
public class Topics implements Serializable {

    private static final long serialVersionUID = 18L;
    
    @Column(name="topic_id")
    @Id
    private Long topicId;

    @Column(name="topic_name")
    @NotNull
    private String topicName;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.topicId);
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
        final Topics other = (Topics) obj;
        if (!Objects.equals(this.topicId, other.topicId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Topics{" + "topicId=" + topicId + ", topicName=" + topicName + '}';
    }
}
