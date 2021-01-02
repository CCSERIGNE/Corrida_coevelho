/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadImpreora;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author SERGIO_HACKER
 */
class listaImprimesora {

    ArrayList<String> listaImpresora;

    public listaImprimesora() {
        listaImpresora = new ArrayList();
    }

    public void Imprimindo(String NomePc, int tempoImprime, String dadosImprime) {
        String dadoParaImprime = "Nome Pc : " + NomePc + " - Tempo - : " + tempoImprime
                + " - Dados - " + dadosImprime;
        this.listaImpresora.add(dadoParaImprime);
    }

    public ArrayList<String> getListaImprime() {
        return this.listaImpresora;
    }
}

class Watchdog extends Thread {

    listaImprimesora Impresora;
    ArrayList<Computadore> listaComputador;
    private int tamanhoMaxFilaImpressao;
    private int tamanhoFilaImpressao;
    private int tamanhCompt_1 ;
    private int tamanhCompt_2 ;
    private int tamanhCompt_3 ;

    public Watchdog(ArrayList<Computadore> Computus ,listaImprimesora Impresores ,int TamanhoMax) {
        this.Impresora = Impresores;
        this.listaComputador = Computus;
        this.tamanhoMaxFilaImpressao = TamanhoMax;
    }

    
    public void run() {

        while (true) {
            synchronized (Impresora) {
                this.tamanhoFilaImpressao = Impresora.listaImpresora.size();
            }
            
            this.tamanhCompt_1 = this.listaComputador.get(0).Impresora.getListaImprime().size();
            this.tamanhCompt_2 = this.listaComputador.get(1).Impresora.getListaImprime().size();
            this.tamanhCompt_3 = this.listaComputador.get(2).Impresora.getListaImprime().size();
            
            if (this.tamanhoFilaImpressao > this.tamanhoMaxFilaImpressao){
                if((this.tamanhCompt_1 > this.tamanhCompt_2) && (this.tamanhCompt_1 > this.tamanhCompt_3)){
                    this.listaComputador.get(0).setExecutando(false);
                }else if((this.tamanhCompt_2 > this.tamanhCompt_3) && (this.tamanhCompt_2 > this.tamanhCompt_1)){
                    this.listaComputador.get(1).setExecutando(false);
                }else if((this.tamanhCompt_3 > this.tamanhCompt_1) && (this.tamanhCompt_3 > this.tamanhCompt_2)){
                    this.listaComputador.get(2).setExecutando(false);
                }
            }else{
                if(this.tamanhCompt_1 <= 0){
                    this.listaComputador.get(0).setExecutando(true);
                }
                if(this.tamanhCompt_2 <= 0){
                    this.listaComputador.get(1).setExecutando(true);
                }
                if(this.tamanhCompt_3 <= 0){
                    this.listaComputador.get(2).setExecutando(true);
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}

class Impresores extends Thread {

    Random ran = new Random();
    String DadosImprim;
    listaImprimesora Impresora;
    int Tempo;

    public Impresores(listaImprimesora imprime) {
        this.Impresora = imprime;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Impresora) {
                if (Impresora.getListaImprime().isEmpty()) {
                    Tempo = 10;
                } else {
                    DadosImprim = Impresora.getListaImprime().get(0);
                    System.out.println(DadosImprim);
                    String t = DadosImprim.substring(31, 32);
                    Tempo = 50;
                    Impresora.getListaImprime().remove(0);
                }
            }
            try {
                Thread.sleep(Tempo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Computadore extends Thread {

    Random ran = new Random();
    listaImprimesora Impresora;
    private boolean executando;

    public Computadore(listaImprimesora imprime) {
        this.Impresora = imprime;
    }

    @Override
    public void run() {
        while (true) {
            if (executando) {
                synchronized (Impresora) {
                    Impresora.Imprimindo(Thread.currentThread().getName(), ran.nextInt(40) + 10, " Esta funcionando");
                }
            }
            try {
                Thread.sleep(ran.nextInt(900));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void setExecutando(boolean executando) {
        this.executando = executando;
    }

}

public class ThreadImpresora {

    static listaImprimesora Listcompartilhado = new listaImprimesora();
    static ArrayList<Computadore> listaComputadore = new ArrayList();
    static ArrayList<Thread> listaImpresora = new ArrayList();

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        int numeroComputer = 3;
        int numeroImprimant = 2;

        for (int i = 0; i < numeroComputer; i++) {
            Computadore Computer = new Computadore(Listcompartilhado);
            Computer.setName("Computer" + Integer.toString(i + 1));
            Computer.setExecutando(true);
            listaComputadore.add(Computer);
        }

        for (int i = 0; i < numeroImprimant; i++) {
            Thread Imprimante = new Thread(new Impresores(Listcompartilhado));
            Imprimante.setName("Imprimante" + Integer.toString(i + 1));
            listaImpresora.add(Imprimante);
        }

        for (int m = 0; m < listaComputadore.size(); m++) {
            listaComputadore.get(m).start();
        }

        for (int m = 0; m < listaImpresora.size(); m++) {
            listaImpresora.get(m).start();
        }
        
        
        Thread tWatchdog = new Thread(new Watchdog(listaComputadore,Listcompartilhado ,10));
        tWatchdog.start();

    }
}
