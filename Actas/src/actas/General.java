/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package actas;

import javax.persistence.*;

/**
 *
 * @author sebastian
 */
public class General {

    private static EntityManager myEm;
    
    static {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ActasPU");
        myEm = emf.createEntityManager();
    }
    
    public static EntityManager getEM() {
        return myEm;
    }
}
