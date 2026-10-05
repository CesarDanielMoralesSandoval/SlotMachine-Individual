import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas compartida entre los grupos
 * Ciclo 4 del proyecto SlotMachine.
 * Solo usa los métodos del diseño publicado en el enunciado del ciclo
 * (addWheel(type, pos), addSymbol(type, pos, color), placeSymbol, lock, swap,
 * delWheel, configuration y ok), de modo que cualquier grupo pueda ejecutarla
 * sobre su propia implementación.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (octubre 2026)
 */
public class SlotMachineCC4Test
{
    private SlotMachine slotMachine;

    /**
     * Constructor por defecto de la clase de pruebas SlotMachineCC4Test.
     * No realiza ninguna inicialización.
     */
    public SlotMachineCC4Test()
    {
    }

    /**
     * Prepara el escenario de cada prueba. Se ejecuta antes de cada
     * método de prueba, creando una nueva instancia de SlotMachine vacía y en
     * modo invisible, para garantizar que cada prueba comience con un estado
     * limpio e independiente y sin abrir ningún elemento gráfico ni diálogo.
     */
    @BeforeEach
    public void setUp()
    {
        slotMachine = new SlotMachine();
        slotMachine.makeInvisible();
    }
    // pruebas de RojasH
    /**
     * Verifica que se puedan agregar símbolos de los tres tipos
     * (normal, ephemeral y shy) y que los tres cuenten como símbolos de la
     * máquina.
     */
    @Test
    public void accordingMsRhShouldAddSymbolsOfEveryType()
    {
        slotMachine.addSymbol("normal", 1, "red");
        slotMachine.addSymbol("ephemeral", 2, "blue");
        slotMachine.addSymbol("shy", 3, "green");
        assertTrue(slotMachine.ok());
        assertEquals(3, slotMachine.symbols().length);
    }

    /**
     * Verifica que la rueda rebel no se deje bloquear, intercambiar
     * ni eliminar, y que la máquina quede igual después de intentarlo.
     */
    @Test
    public void accordingMsRhShouldRebelWheelRefuseLockSwapAndDelete()
    {
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.addWheel("rebel", 1);
        slotMachine.addWheel(2);
        slotMachine.placeSymbol(1, "red");
        slotMachine.placeSymbol(2, "blue");

        slotMachine.lock(1);
        assertFalse(slotMachine.ok());
        slotMachine.swap(1, 2);
        assertFalse(slotMachine.ok());
        slotMachine.delWheel(1);
        assertFalse(slotMachine.ok());

        assertEquals(2, slotMachine.configuration().length);
        assertEquals("red", slotMachine.configuration()[0]);
        assertEquals("blue", slotMachine.configuration()[1]);
    }

    /**
     * Libera el escenario de pruebas. Se ejecuta después de cada
     * método de prueba, eliminando la referencia a la instancia de
     * SlotMachine utilizada.
     */
    @AfterEach
    public void tearDown()
    {
        slotMachine = null;
    }
}