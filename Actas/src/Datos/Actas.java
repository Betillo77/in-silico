/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author sebastian
 */
@Entity
@Table(name = "ACTAS")
@NamedQueries({@NamedQuery(name = "Actas.findByNumacta", query = "SELECT a FROM Actas a WHERE a.numacta = :numacta"), @NamedQuery(name = "Actas.findByFecha", query = "SELECT a FROM Actas a WHERE a.fecha = :fecha"), @NamedQuery(name = "Actas.findByDescripcion", query = "SELECT a FROM Actas a WHERE a.descripcion = :descripcion"), @NamedQuery(name = "Actas.findByFirmante", query = "SELECT a FROM Actas a WHERE a.firmante = :firmante"), @NamedQuery(name = "Actas.findByTareas", query = "SELECT a FROM Actas a WHERE a.tareas = :tareas")})
public class Actas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "NUMACTA", nullable = false)
    private Integer numacta;
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;
    @Column(name = "FIRMANTE")
    private String firmante;
    @Column(name = "TAREAS")
    private String tareas;

    public Actas() {
    }

    public Actas(Integer numacta) {
        this.numacta = numacta;
    }

    public Actas(Integer numacta, Date fecha, String descripcion) {
        this.numacta = numacta;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public Integer getNumacta() {
        return numacta;
    }

    public void setNumacta(Integer numacta) {
        this.numacta = numacta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFirmante() {
        return firmante;
    }

    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    public String getTareas() {
        return tareas;
    }

    public void setTareas(String tareas) {
        this.tareas = tareas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numacta != null ? numacta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actas)) {
            return false;
        }
        Actas other = (Actas) object;
        if ((this.numacta == null && other.numacta != null) || (this.numacta != null && !this.numacta.equals(other.numacta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datos.Actas[numacta=" + numacta + "]";
    }

}
