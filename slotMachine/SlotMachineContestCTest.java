import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase de pruebas compartida entre los grupos, Ciclo 3 del proyecto
 * SlotMachine.
 * @author César Morales Sandoval
 */
public class SlotMachineContestCTest
{
    private SlotMachineContest contest;
    /**
     * Constructor por defecto de la clase de pruebas SlotMachineContestCTest.
     * No realiza ninguna inicialización.
     */
    public SlotMachineContestCTest()
    {}

    /**
     * Prepara el escenario de cada prueba. Se ejecuta antes de cada método
     * de prueba, creando una nueva instancia de SlotMachineContest para
     * garantizar que cada prueba comience con un estado limpio e
     * independiente.
     */
    @BeforeEach
    public void setUp()
    {
        contest = new SlotMachineContest();
    }

    // Grupo: CañonA-PaezP
    /**
     * Verifica que solve(n) devuelva una lista de movimientos no nula y que
     * cada movimiento tenga exactamente dos valores: {rueda, pasos}.
     */
    @Test
    public void accordingCaPpshouldReturnValidMovesStructure() {
        int n = 3;

        int[][] moves = contest.solve(n);

        assertNotNull(moves);
        for (int[] move : moves) {
            assertEquals(2, move.length);
        }
    }

    // Grupo: Cuervo-Infante
    /**
     * Verifica que solve(1) devuelva un plan vacío: una máquina con una sola
     * rueda no puede hacer jackpot, por lo que no hay acciones por realizar.
     */
    @Test
    public void accordingCcIcsolveShouldReturnAnEmptyPlanForOneWheel() {
        int[][] result = contest.solve(1);

        assertNotNull(result);
        assertEquals(0, result.length);
    }
    
    // Grupo: CarreñoG-CastañoP
    /**
     * solve nunca debe proponer una acción sobre una rueda que no existe:
     * toda rueda indicada debe estar entre 1 y n.
     * Adaptación: solve se invoca sobre una instancia, no de forma estática.
     */
    @Test
    public void ShouldOnlyUseExistingWheels() {
        int n = 8;

        for (int[] action : contest.solve(n)) {
            assertTrue(action[0] >= 1 && action[0] <= n);
        }
    }

    // Grupo: CarreñoG-CastañoP
    /**
     * Una acción que gira 0 pasos no mueve nada, por lo que no debería
     * aparecer en la solución.
     * Adaptación: solve se invoca sobre una instancia, no de forma estática.
     */
    @Test
    public void ShouldNotIncludeZeroStepActions() {
        for (int[] action : contest.solve(8)) {
            assertNotEquals(0, action[1]);
        }
    }

    /**
     * Libera el escenario de pruebas. Se ejecuta después de cada método de
     * prueba, eliminando la referencia a la instancia de SlotMachineContest
     * utilizada.
     */
    @AfterEach
    public void tearDown()
    {
        contest = null;
    }
}