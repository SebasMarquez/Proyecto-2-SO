/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2so;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author SebasBD
 */
public class Personaje {
    private static int idContador = 1;
    private int personajeRondaContador = 0;
    private int id;
    private String estudio;
    private String nombre;
    private int nivelPrioridad;
    private boolean exceptional;
    private int habilidades;
    private int vida;
    private int fuerza;
    private int agilidad;
    private int puntosUnicos; // Puntos únicos según nivel de prioridad
    private Set<String> habilidadesEspeciales; // Habilidades especiales
    
    private static final String[] UN_SHOW_MAS_NOMBRES = {"Mordecai", "Rigby", "Papaleta", "Skips", "Benson", "Fantasmano","Musculoso", "CJ", "Margarita", "Eileen"};
    private static final String[] LA_LEYENDA_DE_AANG_NOMBRES = {"Aang", "Katara", "Sokka", "Zuko", "Toph", "Iroh", "Azula", "Appa", "Momo", "Bumi"};
    
    private Personaje(String nombre, String estudio){
        this.id = idContador++;
        this.nombre = nombre;
        this.estudio = estudio;
        this.habilidades = generarCalidad(60);
        this.vida = generarCalidad(70);
        this.fuerza = generarCalidad(50);
        this.agilidad = generarCalidad(40); 
        
        if (contarCalidadAtributos() >= 3) {
            this.exceptional = true;
            this.nivelPrioridad = 1;
        } else {
            this.exceptional = false;
            // Calcula el nivel de prioridad utilizando un método simplificado
            if (determinarNumHabilidadesEspeciales() == 1) {
                this.nivelPrioridad = 3; //Al solo tener 1 habilidad se setea como Deficiente (Prioridad 3)
            } else {
                this.nivelPrioridad = 2; //Al tener 2 habilidades se setea como Promedio(Prioridad 2)
            }
        }
        
        this.puntosUnicos = generarPuntosUnicos();
        this.habilidadesEspeciales = generarHabilidadesEspeciales();
    }
    
    public static Personaje crearUSMPersonaje(){
        String nombre = UN_SHOW_MAS_NOMBRES[new Random().nextInt(UN_SHOW_MAS_NOMBRES.length)];
        return new Personaje(nombre,"USM");
    }
    
    public static Personaje crearLLDAPersonaje(){
        String nombre = LA_LEYENDA_DE_AANG_NOMBRES[new Random().nextInt(LA_LEYENDA_DE_AANG_NOMBRES.length)];
        return new Personaje(nombre,"LLDA");
    }
    
    private int generarCalidad(int baseProbability) {
        return new Random().nextInt(100) < baseProbability ? new Random().nextInt(100) + 1 : 0;
    }
    
    private int contarCalidadAtributos() {
        int count = 0;
        if (habilidades > 0) {
            count++;
        }
        if (vida > 0) {
            count++;
        }
        if (fuerza > 0) {
            count++;
        }
        if (agilidad > 0) {
            count++;
        }
        return count;
    }
    
    private Set<String> generarHabilidadesEspeciales() {
        Set<String> habilidades = new HashSet<>();
        int numHabilidades = determinarNumHabilidadesEspeciales();

        for (int i = 0; i < numHabilidades; i++) {
            habilidades.add("SpecialAbility" + (i + 1));
        }

        return habilidades;
    }
    
    private int determinarNumHabilidadesEspeciales() {
        return new Random().nextInt(2) + 1; // Devuelve 1 o 2 aleatoriamente (dependiendo de el numero  de habilidades será Promedio o Deficiente)
    }
    
    private int generarPuntosUnicos() {
        // Genera puntos únicos según nivel de prioridad
        switch (getNivelPrioridad()) {
            case 1:
                return new Random().nextInt(20) + 80; // Rango de 80 a 100 para prioridad 1
            case 2:
                return new Random().nextInt(30) + 50; // Rango de 50 a 80 para prioridad 2
            case 3:
                return new Random().nextInt(40) + 10; // Rango de 10 a 50 para prioridad 3
            default:
                return 0;
        }
    }
    
    //Lógica para contador de rounds y cambio de Queue
    public void aumentarContadorRondas() {

        personajeRondaContador++;
        if(personajeRondaContador == 8 & nivelPrioridad != 1){
//            System.out.println("Promoción por " + characterRoundCounter + " combates para " + name + id);
        }
        if (personajeRondaContador == 8) {
            personajeRondaContador = 0; // Resetea el round counter
            if (nivelPrioridad != 1) {

                aumentarPrioridadYCola(this, Admin.cnCola1, Admin.cnCola2, Admin.cnCola3, Admin.nickCola1, Admin.nickCola2, Admin.nickCola3);
            }
        }
    }

// AQUI SE PASAN A LA COLA DE MAYOR PRIORIDAD (AUN NO SE BORRA EL ANTERIOR POR LO QUE QUEDAN DUPLICADOS)
    private static Map<String, Integer> personajesARemoverCN = new HashMap<>();
    private static Map<String, Integer> personajesARemoverN = new HashMap<>();

    private void aumentarPrioridadYCola(Personaje personaje, Cola<Personaje> cnCola1, Cola<Personaje> cnCola2, Cola<Personaje> cnCola3, Cola<Personaje> nickCola1, Cola<Personaje> nickCola2, Cola<Personaje> nickCola3) {
        String nombreId = nombre + id;
        int prioridadARemover = nivelPrioridad;
        // Guardar los datos para su uso posterior

        if (estudio == "CN") {
            personajesARemoverCN.put(nombreId, prioridadARemover);
            nivelPrioridad--;

            Admin.mejoraACola(personaje, cnCola1, cnCola2, cnCola3);
//            System.out.println("SE SUBIO EL NIVEL DE PRIORIDAD DE "+character+"-------------------------");
        } else {
            personajesARemoverN.put(nombreId, prioridadARemover);
            nivelPrioridad--;
            Admin.mejoraACola(personaje, nickCola1, nickCola2, nickCola3);

        }

    }
    
    // Método para utilizar los datos guardados

    public static void removerDeColaCN(Cola<Personaje> cnCola2, Cola<Personaje> cnCola3) {
        for (Map.Entry<String, Integer> entry : personajesARemoverCN.entrySet()) {
            String personajeId = entry.getKey();
            int prioridad = entry.getValue();

            // Realizar operaciones con las colas
            if (prioridad == 2) {
                cnCola2.eliminar(personajeId);
            } else {
                cnCola3.eliminar(personajeId);
            }
        }
    }
       public static void removerDeColaN(Cola<Personaje> nickCola2, Cola<Personaje> nickCola3) {
        for (Map.Entry<String, Integer> entry : personajesARemoverN.entrySet()) {
            String personajeId = entry.getKey();
            int prioridad = entry.getValue();

            // Realizar operaciones con las colas
            if (prioridad == 2) {
                nickCola2.eliminar(personajeId);
            } else {
                nickCola3.eliminar(personajeId);
            }
        }
    }

    
    

    /**
     * @return the personajeRondaContador
     */
    public int getPersonajeRondaContador() {
        return personajeRondaContador;
    }

    /**
     * @param personajeRondaContador the personajeRondaContador to set
     */
    public void setPersonajeRondaContador(int personajeRondaContador) {
        this.personajeRondaContador = personajeRondaContador;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the estudio
     */
    public String getEstudio() {
        return estudio;
    }

    /**
     * @param estudio the estudio to set
     */
    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nivelPrioridad
     */
    public int getNivelPrioridad() {
        return nivelPrioridad;
    }

    /**
     * @param nivelPrioridad the nivelPrioridad to set
     */
    public void setNivelPrioridad(int nivelPrioridad) {
        this.nivelPrioridad = nivelPrioridad;
    }

    /**
     * @return the exceptional
     */
    public boolean isExceptional() {
        return exceptional;
    }

    /**
     * @param exceptional the exceptional to set
     */
    public void setExceptional(boolean exceptional) {
        this.exceptional = exceptional;
    }

    /**
     * @return the habilidades
     */
    public int getHabilidades() {
        return habilidades;
    }

    /**
     * @param habilidades the habilidades to set
     */
    public void setHabilidades(int habilidades) {
        this.habilidades = habilidades;
    }

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param vida the vida to set
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * @return the fuerza
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     * @param fuerza the fuerza to set
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    /**
     * @return the agilidad
     */
    public int getAgilidad() {
        return agilidad;
    }

    /**
     * @param agilidad the agilidad to set
     */
    public void setAgilidad(int agilidad) {
        this.agilidad = agilidad;
    }

    /**
     * @return the puntosUnicos
     */
    public int getPuntosUnicos() {
        return puntosUnicos;
    }

    /**
     * @param puntosUnicos the puntosUnicos to set
     */
    public void setPuntosUnicos(int puntosUnicos) {
        this.puntosUnicos = puntosUnicos;
    }

    /**
     * @return the habilidadesEspeciales
     */
    public Set<String> getHabilidadesEspeciales() {
        return habilidadesEspeciales;
    }

    /**
     * @param habilidadesEspeciales the habilidadesEspeciales to set
     */
    public void setHabilidadesEspeciales(Set<String> habilidadesEspeciales) {
        this.habilidadesEspeciales = habilidadesEspeciales;
    }
}
