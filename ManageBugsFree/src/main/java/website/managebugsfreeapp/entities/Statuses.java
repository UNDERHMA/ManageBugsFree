/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@NamedNativeQuery(name = "findAllStatuses", query = "SELECT status_description FROM Statuses ORDER BY status_id")
public class Statuses implements Serializable {

    private static final long serialVersionUID = 8L;
    
    @Column(name="status_id")
    @Id
    private Long statusId;
    
    @Column(name="status_description")
    @NotNull
    private String statusDescription;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.statusId);
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
        final Statuses other = (Statuses) obj;
        if (!Objects.equals(this.statusId, other.statusId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Statuses{" + "statusId=" + statusId + ", statusDescription=" + statusDescription + '}';
    }

    
    
}
