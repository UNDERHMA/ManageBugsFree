/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munderhill.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mason
 */
@Entity
@NamedNativeQuery(name = "findAllUsersByType", query = "SELECT u.username FROM Users u \n" +
         "INNER JOIN Teams t on u.team_name = t.team_name WHERE team_type = #teamType ORDER BY username ASC")
@NamedNativeQuery(name = "findUser", query = "SELECT username FROM Users WHERE username like #userName ORDER BY username ASC")
@NamedNativeQuery(name = "findAllUserData", resultClass = Users.class, query = "SELECT * FROM Users WHERE username like #userName ORDER BY username ASC")
public class Users implements Serializable {

    private static final long serialVersionUID = 10L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence"
    )
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence",
                       allocationSize = 1, initialValue=1)
    @OneToMany(mappedBy="entered_by_user_id")
    @Column(name="user_id")
    private Integer userId;
    
    @Column(name="username")
    @NotNull
    private String userName;
    
    @Column(name="team_name")
    private String teamName;
    
    @Column(name="role")
    @NotNull
    private String role;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.userId);
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
        final Users other = (Users) obj;
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Users{" + "userId=" + userId + ", userName=" + userName + ", teamName=" + teamName + ", role=" + role + '}';
    }

}
