/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author sebastian
 */
@Embeddable
public class AsistentePK implements Serializable {
    @Column(name = "PERSONA", nullable = false)
    private String persona;
    @Column(name = "ACTA", nullable = false)
    private int acta;

    public AsistentePK() {
    }

    public AsistentePK(String persona, int acta) {
        this.persona = persona;
        this.acta = acta;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public int getActa() {
        return acta;
    }

    public void setActa(int acta) {
        this.acta = acta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (persona != null ? persona.hashCode() : 0);
        hash += (int) acta;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistentePK)) {
            return false;
        }
        AsistentePK other = (AsistentePK) object;
        if ((this.persona == null && other.persona != null) || (this.persona != null && !this.persona.equals(other.persona))) {
            return false;
        }
        if (this.acta != other.acta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datos.AsistentePK[persona=" + persona + ", acta=" + acta + "]";
    }

}
