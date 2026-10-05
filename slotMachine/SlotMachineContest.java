import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Resuelve el Problem (Slot Machine) del ICPC World Finals 2025
 * usando SlotMachine unicamente como testing tool.
 *
 * De la maquina solo se usan SlotMachine(n), spin(wheel, steps) y
 * distinctSymbols() para resolver, y makeVisible() para simular. Usando
 * como pista para la solucion la cantidad de simbolos distintos que
 * se ven en este momento.
 *
 * @author Cesar Daniel Morales Sandoval
 * @version 2.1 (September 2026)
 */
public class SlotMachineContest
{   private static final int MAX_RUEDAS = 8;
    private SlotMachine ultimaMaquina;
    /**
     * Resuelve una maquina de n ruedas y n simbolos, inicializada
     * aleatoriamente, sin mostrarla en pantalla. Guarda la maquina creada
     * para poder consultarla despues con ultimaMaquina().
     *
     * @param n el numero de ruedas y de simbolos de la maquina
     * @return la secuencia de acciones {rueda, pasos} aplicadas hasta
     * alcanzar el jackpot
     */
    public int[][] solve(int n){
        int tamaño = Math.min(n, MAX_RUEDAS);
        SlotMachine maquina = new SlotMachine(tamaño);
        ultimaMaquina = maquina;
        return resolver(maquina, tamaño);
    }
    
    /**
     * Simula la solucion sobre una maquina nueva de n ruedas y n simbolos,
     * inicializada aleatoriamente, mostrando cada accion en pantalla. Esta
     * maquina es distinta a la usada en solve(n).
     *
     * @param n el numero de ruedas y de simbolos de la maquina
     */
    public void simulate(int n){
        int tamaño = Math.min(n, MAX_RUEDAS);
        SlotMachine maquina = new SlotMachine(tamaño);
        ultimaMaquina = maquina; 
        maquina.makeVisible();
        int[][] acciones = resolver(maquina, tamaño);
        if (acciones.length == 0) {
            JOptionPane.showMessageDialog(null, "Con menos de dos ruedas no se puede hacer jackpot.");
        }
    }
    
    /**
     * Ejecuta el algoritmo de solucion sobre la maquina dada, en tres fases:
     * (1) ajusta cada rueda para alcanzar que todos los simbolos visibles sean 
     * distintos, (2) identifica la posicion relativa de cada rueda respecto a la rueda 1
     * girandola paso a paso, y (3) alinea todas las ruedas con la rueda 1.
     * Solo usa spin(wheel, steps) y distinctSymbols(). Se detiene en cuanto
     * detecta el jackpot.
     *
     * @param maquina la maquina que se va a resolver
     * @param n el numero de ruedas y de simbolos de la maquina
     * @return la secuencia de acciones {rueda, pasos}
     */
    //Idea de IA generativa para porder tener todo el algoritmo de solucion
    private int[][] resolver(SlotMachine maquina, int n){
        List<int[]> acciones = new ArrayList<int[]>();
        if (n < 2){
            return new int[0][0];
        }
    
        for (int t = 2; t <= n; t++){
            int mejorValor = maquina.distinctSymbols();
            int mejorPaso = 0;
            for (int p = 1; p <= n; p++){
                maquina.spin(t, 1);
                acciones.add(new int[]{t, 1});
                if (yaGano(maquina)){
                    return acciones.toArray(new int[acciones.size()][]);
                }
                int actual = maquina.distinctSymbols();
                if (actual > mejorValor){
                    mejorValor = actual;
                    mejorPaso = p;
                }
            }
            if (mejorPaso != 0){
                maquina.spin(t, mejorPaso);
                acciones.add(new int[]{t, mejorPaso});
                if (yaGano(maquina)){
                    return acciones.toArray(new int[acciones.size()][]);
                }
            }
        }
        
        //Fase 2: Implementada con la asistencia de Inteligencia Artificial Generativa.
        int[] posicion = new int[n + 1];
        boolean[] identificada = new boolean[n + 1];
        identificada[1] = true;
        int offsetRueda1 = 0;
        for (int s = 1; s <= n - 1; s++){
            maquina.spin(1, 1);
            acciones.add(new int[]{1, 1});
            if (yaGano(maquina)){
                return acciones.toArray(new int[acciones.size()][]);
            }
            offsetRueda1 = s;
            posicion[1] = s;
            for (int w = 2; w <= n; w++){
                if (identificada[w]){
                    continue; //Ia generativa para poder no salir del ciclo pero saltar la iteracion actual
                }
                maquina.spin(w, -1);
                acciones.add(new int[]{w, -1});
                if (yaGano(maquina)){
                    return acciones.toArray(new int[acciones.size()][]);
                }
                if (maquina.distinctSymbols() == n){
                    posicion[w] = s - 1;
                    identificada[w] = true;
                    break;
                }
                maquina.spin(w, 1);
                acciones.add(new int[]{w, 1});
                if (yaGano(maquina)){
                    return acciones.toArray(new int[acciones.size()][]);
                }
            }
        }
    
        for (int w = 2; w <= n; w++){
            int pasos = (((offsetRueda1 - posicion[w]) % n) + n) % n;
            if (pasos != 0){
                maquina.spin(w, pasos);
                acciones.add(new int[]{w, pasos});
                if (yaGano(maquina)){
                    return acciones.toArray(new int[acciones.size()][]);
                }
            }
        }
        return acciones.toArray(new int[acciones.size()][]);
    }
    
    /**
     * Indica si la maquina ya alcanzo el jackpot usando unicamente
     * distinctSymbols().
     * 
     * @param maquina la maquina que se esta resolviendo
     * @return true si distinctSymbols() es 1 maquina cerrada por jackpot
     */
    private boolean yaGano(SlotMachine maquina){
        return maquina.distinctSymbols() == 1;
    }
    
    /**
     * Devuelve la maquina creada en la ultima llamada a solve(n), para poder
     * verificarla en las pruebas (por ejemplo, con isJackpot()).
     *
     * @return la ultima maquina resuelta, o null si aun no se ha llamado
     * a solve(n)
     */
    // Idea de la IA para poder usar la maquina, probarla y saber si si llego a la respuesta
    public SlotMachine ultimaMaquina(){
        return ultimaMaquina;
    }
}