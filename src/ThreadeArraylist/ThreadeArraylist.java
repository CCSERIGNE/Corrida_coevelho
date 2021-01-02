/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadeArraylist;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SERGIO_HACKER
 */

class listaChegada{
    ArrayList<String> listaChegada;
	public listaChegada() {
		listaChegada =  new ArrayList();
	}
	public void setChegada(String NomeCoelho) {
		this.listaChegada.add(NomeCoelho);
	}
	public ArrayList<String> getListaChegad() {
		return this.listaChegada;
	}
}

class coelho extends Thread{
    Random ran = new Random();
   listaChegada chegada ;
   public coelho(listaChegada chego) {
	this.chegada = chego;
    }
    @Override
    public void run(){  
	for(int x=0; x< 10; x++) {

            try {
		Thread.sleep(ran.nextInt(300));
            } catch (InterruptedException e) {
		e.printStackTrace();
            }
        }
	System.out.println(" Chegei - : " + Thread.currentThread().getName());
        
        try {
            Thread.sleep(ran.nextInt(1000));
            chegada.setChegada(Thread.currentThread().getName());
        } catch (InterruptedException ex) {
            Logger.getLogger(coelho.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}

public class ThreadeArraylist {

    static listaChegada Listcompartilhado = new listaChegada();
    static ArrayList<Thread> listaCoelhos = new ArrayList();

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        int nroCoelhos = 5;
        
        for (int i = 0; i < nroCoelhos; i++) {
            Thread t = new Thread(new coelho(Listcompartilhado));
            t.setName("Coelho"+Integer.toString(i+1));
            listaCoelhos.add(t);
        }
        
        for(int m = 0 ; m < listaCoelhos.size() ; m++){
             listaCoelhos.get(m).start();
        }
        
        for(int p = 0 ; p < listaCoelhos.size() ; p++){
            listaCoelhos.get(p).join();
        }
        System.out.println("  ");
        System.out.println(" ************ ");
        System.out.println(" COMPARAR ");
        System.out.println(" ");
        for(int r = 0 ; r < Listcompartilhado.getListaChegad().size() ; r++){
            System.out.println("Chego ArrayList - : "+Listcompartilhado.getListaChegad().get(r));
        }
        System.out.println("  ");
        System.out.println(" ************ ");
        System.out.println(" ");
        System.out.println("Distança final para corer: ");
    }
}
