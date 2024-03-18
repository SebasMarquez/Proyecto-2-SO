/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2so;

import interfaz.Interfaz;

/**
 *
 * @author SebasBD
 */
public class IA {
    public static int tiempo = 10;
    private static final double probabilidadGanar = 0.4;
    private static final double probabilidadEmpate = 0.27;
    private static final double probabilidadNoCombate = 0.33;
    public static int ganadoresUSM = 0;
    public static int ganadoresLLDA = 0;
    
    public void procesarPelea(Personaje USMPersonaje, Personaje LLDAPersonaje) {

        Interfaz.setDefStats();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double resultado = Math.random();

        // Utilizamos los puntos únicos para determinar el resultado
        int USMPuntos = USMPersonaje.getPuntosUnicos();
        int LLDAPuntos = LLDAPersonaje.getPuntosUnicos();

        // Setear el nombre imagen y puntos unicos de los personajes a pelear.
        Interfaz.setUSMIcon(USMPersonaje.getNombre(), USMPersonaje.getPuntosUnicos());
        Interfaz.setLLDAIcon(LLDAPersonaje.getNombre(), LLDAPersonaje.getPuntosUnicos());
        try {
            if (Admin.contadorRondas < 1) {

            } else {
                Interfaz.EstadoIA.setText("¡PELEANDO!");
            }
            Thread.sleep(1000 * tiempo); //DECIDIENDO GANADOR
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (resultado < probabilidadGanar) {
            if (USMPuntos > LLDAPuntos) {
                ganadorUSM(USMPersonaje, LLDAPersonaje, USMPuntos, LLDAPuntos);
                Interfaz.setCoronaPosicionUSM(Interfaz.USMIMG);
                ganadoresUSM++;
                Interfaz.marcadorUSM(ganadoresUSM);
            } else {
                ganadorLLDA(LLDAPersonaje, USMPersonaje, LLDAPuntos, USMPuntos);
                Interfaz.setCoronaPosicionLLDA(Interfaz.LLDAIMG);
                ganadoresLLDA++;
                Interfaz.marcadorLLDA(ganadoresLLDA);
            }
        } else if (resultado < probabilidadGanar + probabilidadEmpate) {
            empate(USMPersonaje, LLDAPersonaje);
            Interfaz.setEmpateVisible();
        } else {
            noCombate(USMPersonaje, LLDAPersonaje);
            Interfaz.setCancelVisible();
        }
        // Después del combate, probabilidad del 40% de mover un personaje de refuerzo a la cola de prioridad 1
        if (Admin.deberiaMoverAPrioridad1()) {
            Admin.moverPersonajeAPrioridad(Admin.colaRefuerzoCN, Admin.cnCola1);
        }

        if (Admin.deberiaMoverAPrioridad1()) {
            Admin.moverPersonajeAPrioridad(Admin.colaRefuerzoNick, Admin.nickCola1);
        }
        Admin.incrementarContadorRondas(); // Incrementa los contadores de todas las colas
        Personaje.removerDeColaCN(Admin.cnCola2, Admin.cnCola3);
        Personaje.removerDeColaN(Admin.nickCola2, Admin.nickCola3);
        
        //Generador de personajes (80% de probabilidad)
        Admin.contadorRondas++;
        if (Admin.contadorRondas % 2 == 0) { // Se han completado dos rondas
            if (Admin.deberiaGenerarPersonaje()) {
                Admin.generarPersonajes();
                System.out.println("SE GENERARON 2 PERSONAJES NUEVOS!");
            }
        }

        try {
            Interfaz.EstadoIA.setText("Resultado");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Interfaz.EstadoIA.setText("ESPERANDO");
        Interfaz.coronaLabelUSM.setVisible(false);
        Interfaz.coronaLabelLLDA.setVisible(false);
        Interfaz.empateLabel.setVisible(false);
        Interfaz.cancelLabel.setVisible(false);
    }
    
    private void ganadorUSM(Personaje ganador, Personaje perdedor, int puntosGanador, int puntosPerdedor) {
        System.out.println("¡Combate terminado! El ganador es: " + ganador.getNombre()
                + " (ID: " + ganador.getId() + ") con " + puntosGanador + " puntos únicos.");
        Interfaz.agregarGanador(ganador.getNombre()+ ganador.getId());
    }

    private void ganadorLLDA(Personaje ganador, Personaje perdedor, int puntosGanador, int puntosPerdedor) {
        System.out.println("¡Combate terminado! El ganador es: " + ganador.getNombre()
                + " (ID: " + ganador.getId() + ") con " +  puntosGanador + " puntos únicos.");
        Interfaz.agregarGanador(ganador.getNombre()+ganador.getId());
    }

    private void empate(Personaje personaje1, Personaje personaje2) {
        System.out.println("¡Combate empatado! Ambos personajes vuelven a la cola de prioridad 1.");
        Admin.cnCola1.encolar(personaje1);
        Admin.nickCola1.encolar(personaje2);
    }

    private void noCombate(Personaje personaje1, Personaje personaje2) {
        System.out.println("No puede llevarse a cabo el combate. Ambos personajes van a la cola de refuerzo.");
        Admin.colaRefuerzoCN.encolar(personaje1);
        Admin.colaRefuerzoNick.encolar(personaje2);
    }
}
