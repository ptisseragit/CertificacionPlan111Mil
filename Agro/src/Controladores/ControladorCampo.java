/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Entidades.Campo;
import Entidades.Lote;
import Hibernate.HibernateUtil;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Cristian
 */
public class ControladorCampo {
    
    private Campo unCampo;
    
    public ControladorCampo(){
        unCampo=new Campo();
    }
    
    public Campo campoActual(){
        return(unCampo);
    }
    
    public void borrarValoresCampo(){
        unCampo=new Campo();
    }
    
    public void registrarCampo (String nombre, String superficie, String estado){
        unCampo.setNombreCampo(nombre);
        unCampo.setSuperficieCampo(Double.parseDouble(superficie));
        unCampo.setEstadoCampo(estado);
        if (validarCampo(unCampo)) {
            insertarCampo(unCampo);    
        }
    }
    
    public void registrarLote (String numero, String superficie, int tipoSuelo){
        Lote lote = new Lote();
        if (numero.isEmpty()){
            lote.setNumeroLote(-1);
        }    
        else{
            lote.setNumeroLote(Integer.parseInt(numero));
        }
        if (superficie.isEmpty()){
            lote.setSuperficieLote(-1.0);
        }    
        else{
            lote.setSuperficieLote(Double.parseDouble(superficie));
        }
    
        if (!valoresLotesNoVacios(lote)){
            lote.setCampo(unCampo);
            unCampo.getLotes().add(lote);
        }
        else{
           JOptionPane.showMessageDialog(null,"No puede haber campos vacios para un lote","Imposible Registrar",JOptionPane.ERROR_MESSAGE); 
        }           
    }
    
    public Boolean numeroLoteRepetido(String numero){
        if(!numero.isEmpty()){
            int numeroLoteBuscado = Integer.parseInt(numero.trim());
            Iterator it = unCampo.getLotes().iterator();
            while(it.hasNext()){
                if( ((Lote)it.next()).getNumeroLote() == numeroLoteBuscado ){
                    JOptionPane.showMessageDialog(null,"Por favor ingrese un lote con la numeración correcta","Ya existe un Lote con la numeración dada",JOptionPane.ERROR_MESSAGE); 
                    return true;
                }
            }
        }
        return false;
    }
    
    private Boolean valoresLotesNoVacios(Lote lote) {
        Boolean error;
        int er=-1;
        if (lote.getNumeroLote()!= er && lote.getSuperficieLote()!= er)
            error = false;
        else
            error = true;
        return error;
    }

    
    private boolean validarCampo(Campo campo){
        Double tamannoTotal=0.0;
        if(campo.getLotes().size()>0){
            Iterator it = campo.getLotes().iterator();
            while (it.hasNext()) {
                tamannoTotal+= ((Lote)it.next()).getSuperficieLote();
            }
            if (tamannoTotal==campo.getSuperficieCampo()){
                return true;
            }    
        }     
        JOptionPane.showMessageDialog(null,"Un campo debe tener lotes y la suma de sus superficies debe ser igual a la superficie del campo","Imposible Registrar",JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    public void insertarCampo(Campo unCampo){
        SessionFactory factoria = HibernateUtil.getSessionFactory();
        Session sesion = factoria.openSession();
        Transaction transaccion = sesion.beginTransaction();
        sesion.save(unCampo);
        transaccion.commit();
        sesion.close();
        JOptionPane.showMessageDialog(null,"Campo Registrado Exitosamente !!!");
    }
    
    public boolean buscarCampoPorNombre (String nombreCampo){
        
        SessionFactory factoria = HibernateUtil.getSessionFactory();
        Session sesion = factoria.openSession();
        Query query = sesion.createQuery("FROM Campo c WHERE c.nombreCampo = :parametro ");
        query.setParameter("parametro", nombreCampo);
        //System.out.println(query.getQueryString());
        //List<Campo> campos = query.list();
        //if(campos.iterator().hasNext())
        //System.out.println("EL NOMBRE ES:" + ((Campo)campos.iterator().next()).getNombreCampo());
        return query.list().size()>0;
    }
       
}
