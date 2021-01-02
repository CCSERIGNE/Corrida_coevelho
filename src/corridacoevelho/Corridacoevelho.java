/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corridacoevelho;
import java.util.Random;

/**
 *
 * @author SERGIO_HACKER
 */

class distanca{
    int dista;
	public distanca() {
		dista = 0;
	}
	public void setdist(int valor) {
		this.dista = valor;
	}
	public int getdistanca() {
		return dista;
	}
}

class coelho extends Thread{
    private final distanca valordista;
    Random ran = new Random();
    public coelho(distanca valor) {
	this.valordista = valor;
    }
    
    @Override
    public void run(){  
	for(int x=0; x< valordista.getdistanca(); x++) {

            try {
		Thread.sleep(ran.nextInt(300));
            } catch (InterruptedException e) {
		e.printStackTrace();
            }
        }
	   System.out.println(" Chegei - : " + Thread.currentThread().getName());
    }
}

public class Corridacoevelho {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        distanca distcor = new distanca();
	distcor.setdist(50);
		
	coelho coel_1 = new coelho(distcor);
        coelho coel_2 = new coelho(distcor);
	coelho coel_3 = new coelho(distcor);
	coelho coel_4 = new coelho(distcor);
        coelho coel_5 = new coelho(distcor);
		
	Thread Coelha_1 = new Thread(coel_1);
        Thread Coelha_2 = new Thread(coel_2);
        Thread Coelha_3 = new Thread(coel_3);
        Thread Coelha_4 = new Thread(coel_4);
        Thread Coelha_5 = new Thread(coel_5);
	
        Coelha_1.setName("Coelha_1"); Coelha_2.setName("Coelha_2"); Coelha_3.setName("Coelha_3");
        Coelha_4.setName("Coelha_4"); Coelha_5.setName("Coelha_5");
        
	Coelha_1.start();
	Coelha_2.start();
        Coelha_3.start();
        Coelha_4.start();
        Coelha_5.start();
        
        Coelha_1.join();
        Coelha_2.join();
        Coelha_3.join();
	Coelha_4.join();
        Coelha_5.join();
        System.out.println("Distança final para corer: " + distcor.getdistanca());
    }  
}
