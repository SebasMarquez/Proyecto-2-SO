/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2so;

/**
 *
 * @author SebasBD
 */
public class Admin {
    public static int contadorRondas = 0;
    public static Cola<Character> cnCola1; // Colas para CartoonNetwork 
    public static Cola<Character> cnCola2;
    public static Cola<Character> cnCola3;
    public static Cola<Character> nickCola1; // Colas para Nickelodeon 
    public static Cola<Character> nickCola2;
    public static Cola<Character> nickCola3;
    public static Cola<Character> colaRefuerzoCN; // Cola de refuerzo para CartoonNetwork
    public static Cola<Character> colaRefuerzoNick; // Cola de refuerzo para Nickelodeon
    
    public Admin(){
        cnCola1 = new Cola<>();
        cnCola2 = new Cola<>();
        cnCola3 = new Cola<>();
        
        nickCola1 = new Cola<>();
        nickCola2 = new Cola<>();
        nickCola3 = new Cola<>();
        
        colaRefuerzoCN = new Cola<>();
        colaRefuerzoNick = new Cola<>();
    }
}
