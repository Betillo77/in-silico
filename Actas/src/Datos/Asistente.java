/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sebastian
 */
@Entity
@Table(name = "ASISTENTE")
@NamedQueries({@NamedQuery(name = "Asistente.findByPersona", query = "SELECT a FROM Asistente a WHERE a.asistentePK.persona = :persona"), @NamedQuery(name = "Asistente.findByActa", query = "SELECT a FROM Asistente a WHERE a.asistentePK.acta = :acta")})
public class Asistente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AsistentePK asistentePK;

    public Asistente() {
    }

    public Asistente(AsistentePK asistentePK) {
        this.asistentePK = asistentePK;
    }

    public Asistente(String persona, int acta) {
        this.asistentePK = new AsistentePK(persona, acta);
    }

    public AsistentePK getAsistentePK() {
        return asistentePK;
    }

    public void setAsistentePK(AsistentePK asistentePK) {
        this.asistentePK = asistentePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (asistentePK != null ? asistentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistente)) {
            return false;
        }
        Asistente other = (Asistente) object;
        if ((this.asistentePK == null && other.asistentePK != null) || (this.asistentePK != null && !this.asistentePK.equals(other.asistentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datos.Asistente[asistentePK=" + asistentePK + "]";
    }

}
