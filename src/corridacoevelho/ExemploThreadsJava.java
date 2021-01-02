/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corridacoevelho;

/**
 *
 * @author SERGIO_HACKER
 */


class Valor{
	int numero;
	public Valor() {
		numero = 0;
	}
	public void setValor(int valor) {
		this.numero = valor;
	}
	public int getValor() {
		return numero;
	}
}

class ThreadExemplo extends Thread{  
	private Valor valor;
	public ThreadExemplo(Valor valor) {
		this.valor = valor;
	}
	public void run(){  
		for(int x=0; x<10; x++) {
			//synchronized(valor) {
				valor.setValor(valor.getValor()+1);
			//}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println( Thread.currentThread().getName() + " - Numero vale: " + valor.getValor());
	}
}

public class ExemploThreadsJava {
	public static void main(String[] args) {
		
		Valor v1 = new Valor();
		v1.setValor(0);
		
		ThreadExemplo te1 = new ThreadExemplo(v1);
		ThreadExemplo te2 = new ThreadExemplo(v1);
		
		Thread t1 = new Thread(te1);
		Thread t2 = new Thread(te2);
		
		t1.start();
		t2.start();
		
		System.out.println("Final de execucao, v1 vale: " + v1.getValor());
	}
}
