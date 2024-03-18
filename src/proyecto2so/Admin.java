/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2so;

import java.util.Random;

/**
 *
 * @author SebasBD
 */
public class Admin {
    public static int contadorRondas = 0;
    public static Cola<Personaje> cnCola1; // Colas para CartoonNetwork 
    public static Cola<Personaje> cnCola2;
    public static Cola<Personaje> cnCola3;
    public static Cola<Personaje> nickCola1; // Colas para Nickelodeon 
    public static Cola<Personaje> nickCola2;
    public static Cola<Personaje> nickCola3;
    public static Cola<Personaje> colaRefuerzoCN; // Cola de refuerzo para CartoonNetwork
    public static Cola<Personaje> colaRefuerzoNick; // Cola de refuerzo para Nickelodeon
    
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
    
    public void inicializarPersonajes() {
        for (int i = 0; i < 10; i++) {// probar si es i<10 o i <20
            Personaje cnPersonaje = Personaje.crearUSMPersonaje();
            Personaje nickPersonaje = Personaje.crearLLDAPersonaje();
            
            agregarACola(cnPersonaje, cnCola1, cnCola2, cnCola3);
            agregarACola(nickPersonaje, nickCola1, nickCola2, nickCola3);
        }
    }
    
    public static void agregarACola(Personaje personaje, Cola<Personaje>... colas) {
        int prioridadIndex = personaje.getNivelPrioridad() - 1;
        colas[prioridadIndex].encolar(personaje);
    }
    
    private Personaje seleccionarPersonajeParaSiguenteColaNoVacia(Cola<Personaje>... colas) {
        for (int i = 0; i < colas.length; i++) {
            if (!colas[i].estaVacia()) {
                return colas[i].desencolar();
            }
        }
        return null; // Si todas las colas están vacías
    }
    
    public Personaje[] seleccionarPersonajesParaPelear() {
        Personaje[] personajesParaPelea = new Personaje[2];

        personajesParaPelea[0] = seleccionarPersonajeParaSiguenteColaNoVacia(cnCola1, cnCola2, cnCola3);
        personajesParaPelea[1] = seleccionarPersonajeParaSiguenteColaNoVacia(nickCola1, nickCola2, nickCola3);

        return personajesParaPelea;

    }
    
    public static void mejoraACola(Personaje personaje, Cola<Personaje>... colas) {

        int NewprioridadIndex = personaje.getNivelPrioridad() - 1;
        int prioridadIndex = personaje.getNivelPrioridad();
        // Eliminar el personaje de las colas existentes (si está presente)
        
        colas[NewprioridadIndex].encolar(personaje);
    }
    
    private Personaje seleccionarPersonajeDeCola(int colaIndex, Cola<Personaje>... colas) {
        return colas[colaIndex].estaVacia() ? null : colas[colaIndex].desencolar();
    }

    public static void moverPersonajeAPrioridad(Cola<Personaje> colaRefuerzo, Cola<Personaje> colaPrioridad) {
        if (!colaRefuerzo.estaVacia()) {
            Personaje personaje = colaRefuerzo.desencolar();
            colaPrioridad.encolar(personaje);
        }
    }
    
    // Sube los personajes en la posicion 1 de las colas. 
    public void actualizarColas() {
        for (int i = 1; i < 3; i++) {
            Personaje cnPersonaje = seleccionarPersonajeDeCola(i, cnCola1, cnCola2, cnCola3);
            if (cnPersonaje != null) {
                switch (i) {
                    case 1:
                        cnCola1.encolar(cnPersonaje);
                        break;
                    case 2:
                        cnCola2.encolar(cnPersonaje);
                        break;
                    case 3:
                        cnCola3.encolar(cnPersonaje);
                        break;
                }
            }

            Personaje nickPersonaje = seleccionarPersonajeDeCola(i, nickCola1, nickCola2, nickCola3);
            if (nickPersonaje != null) {
                switch (i) {
                    case 1:
                        nickCola1.encolar(nickPersonaje);
                        break;
                    case 2:
                        nickCola2.encolar(nickPersonaje);
                        break;
                    case 3:
                        nickCola3.encolar(nickPersonaje);
                        break;
                }
            }
        }
    }

    public static boolean deberiaGenerarPersonaje() {
        // Devolver true con una probabilidad del 80% (Para generar personajes)
        return new Random().nextInt(100) < 80;
    }
    
    public static boolean deberiaMoverAPrioridad1() {
        // Devuelve true con un 40% de probabilidad (para sacar de la cola de refuerzo)
        return new Random().nextInt(100) < 40;
    }
    //Lógica para aumentar contadores de Characters

    public static void incrementarContadorRondas() {
        Cola<Personaje>[] todasColas = new Cola[]{
            cnCola1, cnCola2, cnCola3,
            nickCola1, nickCola2, nickCola3
        };

        for (Cola<Personaje> cola : todasColas) {
            incrementarColaContadorRondas(cola);
        }
    }
// Lógica para aumentar el nivel de prioridad (Subir de Queue)

    private static void incrementarColaContadorRondas(Cola<Personaje> cola) {
        Cola<Personaje> colaTemporal = new Cola<>();
        while (!cola.estaVacia()) {
            Personaje personaje = cola.desencolar();
            personaje.aumentarContadorRondas();
            colaTemporal.encolar(personaje);
        }
        while (!colaTemporal.estaVacia()) {
            cola.encolar(colaTemporal.desencolar());
        }
    }

}
