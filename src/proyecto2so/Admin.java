/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2so;

import interfaz.Interfaz;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author SebasBD
 */
public class Admin {
    public static int contadorRondas = 0;
    public static Cola<Personaje> cnCola1;  
    public static Cola<Personaje> cnCola2;
    public static Cola<Personaje> cnCola3;
    public static Cola<Personaje> nickCola1;
    public static Cola<Personaje> nickCola2;
    public static Cola<Personaje> nickCola3;
    public static Cola<Personaje> colaRefuerzoCN; 
    public static Cola<Personaje> colaRefuerzoNick; 
    
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
        for (int i = 0; i < 20; i++) {
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
        return null; 
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
    
    public static void generarPersonajes() {
        Personaje USMPersonaje = Personaje.crearUSMPersonaje();
        Personaje LLDAPersonaje = Personaje.crearLLDAPersonaje();
        
        agregarACola(USMPersonaje, cnCola1, cnCola2, cnCola3);
        agregarACola(LLDAPersonaje, nickCola1, nickCola2, nickCola3);

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
        return new Random().nextInt(100) < 80;
    }
    
    public static boolean deberiaMoverAPrioridad1() {
        return new Random().nextInt(100) < 40;
    }
    //Lógica para aumentar contadores de Personajes

    public static void incrementarContadorRondas() {
        Cola<Personaje>[] todasColas = new Cola[]{
            cnCola1, cnCola2, cnCola3,
            nickCola1, nickCola2, nickCola3
        };

        for (Cola<Personaje> cola : todasColas) {
            incrementarColaContadorRondas(cola);
        }
    }
// Lógica para aumentar el nivel de prioridad (Subir de Cola)

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
    
    private static Cola<Personaje> getColaUSM(int index) {
        switch (index) {
            case 1:
                return cnCola1;
            case 2:
                return cnCola2;
            case 3:
                return cnCola3;
            case 4:
                return colaRefuerzoCN;
            default:
                return null;
        }
    }

    private static Cola<Personaje> getColaLLDA(int index) {
        switch (index) {
            case 1:
                return nickCola1;
            case 2:
                return nickCola2;
            case 3:
                return nickCola3;
            case 4:
                return colaRefuerzoNick;
            default:
                return null;
        }
    }
    
    // Método para actualizar las colas en los JLabel
    public static void actualizarColasEnInterfaz() {
        // Para USM
        for (int i = 0; i < 4; i++) {
            StringBuilder resultadoUSM = new StringBuilder();
            JLabel colasUSM = Interfaz.getColasUSM(i + 1);

            if (colasUSM != null) {
                Cola<Personaje> colaActual;

                // Determinar la cola actual según el índice
                if (i < 3) {
                    resultadoUSM.append("Cola USM " + (i + 1) + ":\n");
                    colaActual = getColaUSM(i + 1);
                } else {
                    resultadoUSM.append("Cola de Refuerzon CN:\n");
                    colaActual = colaRefuerzoCN;
                }

                resultadoUSM.append(printColaAString(colaActual));
                colasUSM.setText(resultadoUSM.toString());
            }
        }
        
        // Para LLDA
        for (int i = 0; i < 4; i++) {
            StringBuilder resultadoLLDA = new StringBuilder();
            JLabel colasLLDA = Interfaz.getColasLLDA(i + 1);

            if (colasLLDA != null) {
                Cola<Personaje> colaActual;

                // Determinar la cola actual según el índice
                if (i < 3) {
                    resultadoLLDA.append("Cola Nick " + (i + 1) + ":\n");
                    colaActual = getColaLLDA(i + 1);
                } else {
                    resultadoLLDA.append("Cola de Refuerzo Nick:\n");
                    colaActual = colaRefuerzoNick;
                }

                resultadoLLDA.append(printColaAString(colaActual));
                colasLLDA.setText(resultadoLLDA.toString());
            }
        }
    }
    private static <T> String printColasAString(Cola<T>... colas) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < colas.length; i++) {
            resultado.append("Cola ").append(i + 1).append(": ");
            resultado.append(printColaAString(colas[i]));
        }
        return resultado.toString();
    }
    private static <T> String printColaAString(Cola<T> cola) {
        Cola<T> colaTemporal = new Cola<>();
        StringBuilder resultado = new StringBuilder();
        while (!cola.estaVacia()) {
            T data = cola.desencolar();
            resultado.append(data).append(" ");
            colaTemporal.encolar(data);
        }
        resultado.append("\n");
        while (!colaTemporal.estaVacia()) {
            cola.encolar(colaTemporal.desencolar());
        }
        return resultado.toString();
    }
    
    public void printColas() {
        System.out.println("Colas CartoonNetwork: ");
        printColas(cnCola1, cnCola2, cnCola3);

        System.out.println("\nCola de Refuerzo CartoonNetwork:");
        printColas(colaRefuerzoCN);

        System.out.println("\nColas Nickelodeon:");
        printColas(nickCola1, nickCola2, nickCola3);

        System.out.println("\nCola de Refuerzo CartoonNetwork:");
        printColas(colaRefuerzoNick);
    }
    
    private <T> void printColas(Cola<T>... colas) {
        for (int i = 0; i < colas.length; i++) {
            System.out.print("Colas " + (i + 1) + ": ");
            printCola(colas[i]);
        }
    }
    
    private <T> void printCola(Cola<T> cola) {
        Cola<T> colaTemporal = new Cola<>();
        while (!cola.estaVacia()) {
            T data = cola.desencolar();
            System.out.print(data + " ");
            colaTemporal.encolar(data);
        }
        System.out.println();
        while (!colaTemporal.estaVacia()) {
            cola.encolar(colaTemporal.desencolar());
        }
    }
}
