/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import pl.service.Service;

/**
 *
 * @author Maciej
 */
@Entity
public class Round implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int nrRound;
    @OneToMany
    private List<Match> matchesOnRound = new ArrayList <>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Round)) {
            return false;
        }
        Round other = (Round) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.domain.Round[ id=" + id + " ]";
    }

    public List<Match> getMatchesOnRound() {
        return matchesOnRound;
    }
    
    public void setMatchesOnRound(List<Match> matchesOnRound) {
        this.matchesOnRound = matchesOnRound;
    }

    public void addMatchesOnRound(Match match) {
        matchesOnRound.add(match);
    }
    public int getNrRound() {
        return nrRound;
    }

    public void setNrRound(int nrRound) {
        this.nrRound = nrRound;
    }
    
    

    
    
}
