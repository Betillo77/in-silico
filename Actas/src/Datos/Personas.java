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
@Table(name = "PERSONAS")
@NamedQueries({@NamedQuery(name = "Personas.findByCodigo", query = "SELECT p FROM Personas p WHERE p.codigo = :codigo"), @NamedQuery(name = "Personas.findByNombre", query = "SELECT p FROM Personas p WHERE p.nombre = :nombre"), @NamedQuery(name = "Personas.findByCorreo", query = "SELECT p FROM Personas p WHERE p.correo = :correo"), @NamedQuery(name = "Personas.findByPrograma", query = "SELECT p FROM Personas p WHERE p.programa = :programa"), @NamedQuery(name = "Personas.findByTalla", query = "SELECT p FROM Personas p WHERE p.talla = :talla"), @NamedQuery(name = "Personas.findByFechanac", query = "SELECT p FROM Personas p WHERE p.fechanac = :fechanac"), @NamedQuery(name = "Personas.findByFechaingreso", query = "SELECT p FROM Personas p WHERE p.fechaingreso = :fechaingreso"), @NamedQuery(name = "Personas.findByTelefonos", query = "SELECT p FROM Personas p WHERE p.telefonos = :telefonos"), @NamedQuery(name = "Personas.findByUniversidad", query = "SELECT p FROM Personas p WHERE p.universidad = :universidad"), @NamedQuery(name = "Personas.findByCargo", query = "SELECT p FROM Personas p WHERE p.cargo = :cargo")})
public class Personas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "PROGRAMA")
    private String programa;
    @Column(name = "TALLA")
    private String talla;
    @Column(name = "FECHANAC")
    @Temporal(TemporalType.DATE)
    private Date fechanac;
    @Column(name = "FECHAINGRESO")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Column(name = "TELEFONOS")
    private String telefonos;
    @Column(name = "UNIVERSIDAD")
    private String universidad;
    @Column(name = "CARGO")
    private String cargo;

    public Personas() {
    }

    public Personas(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public Date getFechanac() {
        return fechanac;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Datos.Personas[codigo=" + codigo + "]";
    }

}
