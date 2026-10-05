import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias del ciclo 4 de SlotMachine. Verifica los
 * distintos tipos de ruedas (normal, lefty, rebel y mirror) y de símbolos
 * (normal, ephemeral y shy), las sobrecargas addWheel(type, pos) y
 * addSymbol(type, pos, color), y que los tipos nuevos no rompan el
 * comportamiento de los ciclos anteriores.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class SlotMachineC4Test
{
    private SlotMachine machine;

    /**
     * Constructor por defecto de la clase de pruebas SlotMachineC4Test.
     * No realiza ninguna inicialización.
     */
    public SlotMachineC4Test()
    {
    }

    /**
     * Prepara el escenario de cada prueba. Se ejecuta antes de cada método
     * de prueba, creando una máquina nueva e invisible para que cada prueba
     * comience con un estado limpio e independiente.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    /**
     * Agrega a la máquina los símbolos normales red, blue y green (en ese
     * orden). Evita repetir las mismas líneas en cada prueba.
     */
    private void agregarSimbolosNormales()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
    }

    // addWheel(type, pos): tipos de rueda

    /**
     * Verifica que addWheel(type, pos) agregue una rueda de cada uno de los
     * cuatro tipos disponibles, en el orden en que se pidieron.
     */
    @Test
    public void shouldAddOneWheelOfEachType()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("rebel", 3);
        machine.addWheel("mirror", 4);

        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"normal", "lefty", "rebel", "mirror"},
                          machine.wheelTypes());
    }

    /**
     * Verifica que addWheel(int pos), la versión de los ciclos anteriores,
     * siga creando una rueda normal.
     */
    @Test
    public void shouldCreateNormalWheelWithOldAddWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel(1);

        assertArrayEquals(new String[]{"normal"}, machine.wheelTypes());
    }

    /**
     * Verifica que el tipo de rueda se reconozca sin importar mayúsculas
     * o minúsculas.
     */
    @Test
    public void shouldIgnoreCaseWhenChoosingWheelType()
    {
        agregarSimbolosNormales();
        machine.addWheel("LEFTY", 1);
        machine.addWheel("Rebel", 2);

        assertArrayEquals(new String[]{"lefty", "rebel"}, machine.wheelTypes());
    }

    /**
     * Verifica que un tipo de rueda que no existe no agregue nada y deje
     * ok() en false.
     */
    @Test
    public void shouldNotAddWheelOfUnknownType()
    {
        agregarSimbolosNormales();
        machine.addWheel("magic", 1);

        assertFalse(machine.ok());
        assertEquals(0, machine.wheelTypes().length);
        assertEquals("Accion no permitida: El tipo de rueda no esta disponible.",
                     machine.lastMessage());
    }

    /**
     * Verifica que un tipo null tampoco agregue una rueda ni lance error.
     */
    @Test
    public void shouldNotAddWheelWhenTypeIsNull()
    {
        agregarSimbolosNormales();
        machine.addWheel(null, 1);

        assertFalse(machine.ok());
        assertEquals(0, machine.wheelTypes().length);
    }

    /**
     * Verifica que addWheel(type, pos) respete la posición: una posición
     * menor que uno agrega al principio y una mayor que el tamaño al final.
     */
    @Test
    public void shouldAddTypedWheelsAtTheGivenPosition()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("rebel", 0);
        machine.addWheel("lefty", 50);
        machine.addWheel("mirror", 2);

        assertArrayEquals(new String[]{"rebel", "mirror", "normal", "lefty"},
                          machine.wheelTypes());
    }

    /**
     * Verifica que una máquina cerrada con exit() no acepte ruedas nuevas.
     */
    @Test
    public void shouldNotAddTypedWheelWhenMachineIsClosed()
    {
        agregarSimbolosNormales();
        machine.exit();
        machine.addWheel("lefty", 1);

        assertFalse(machine.ok());
        assertNull(machine.wheelTypes());
    }

    // Rueda lefty

    /**
     * Verifica que una rueda lefty, al girarla con spin(int), copie el
     * símbolo de la rueda que tiene a su izquierda.
     */
    @Test
    public void shouldLeftyCopyLeftWheelWhenSpinning()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.spin(2);

        assertEquals("red", machine.configuration()[1]);
        assertTrue(machine.ok());
    }

    /**
     * Verifica que la rueda lefty copie a su izquierda en cualquier giro
     * aleatorio, aunque la rueda de la izquierda cambie de símbolo.
     */
    @Test
    public void shouldLeftyCopyLeftWheelAfterEverySpin()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);

        for (int i = 0; i < 30; i++){
            machine.spin(1);
            machine.spin(2);
            assertEquals(machine.configuration()[0], machine.configuration()[1]);
        }
    }

    /**
     * Verifica que spin(), que gira todas las ruedas de izquierda a derecha,
     * deje a la rueda lefty igual a la rueda de su izquierda.
     */
    @Test
    public void shouldLeftyMatchLeftWheelWhenSpinningAllWheels()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("lefty", 3);

        for (int i = 0; i < 30; i++){
            machine.spin();
            String[] config = machine.configuration();
            assertEquals(config[0], config[1]);
            assertEquals(config[1], config[2]);
        }
    }

    /**
     * Verifica que spin(wheel, steps) sobre una rueda lefty también copie a
     * su izquierda, sin importar cuántos pasos se pidieron.
     */
    @Test
    public void shouldLeftyCopyLeftWheelWhenSpinningBySteps()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "green");
        machine.placeSymbol(2, "red");

        machine.spin(2, 1);

        assertEquals("green", machine.configuration()[1]);
    }

    /**
     * Verifica que una rueda lefty que está en la primera posición (sin
     * nada a su izquierda) gire como una rueda normal.
     */
    @Test
    public void shouldLeftyBehaveAsNormalWhenItIsTheFirstWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel("lefty", 1);
        machine.placeSymbol(1, "red");

        machine.spin(1, 1);

        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Verifica que una rueda lefty bloqueada no copie a su izquierda, porque
     * una rueda bloqueada no gira.
     */
    @Test
    public void shouldNotCopyWhenLeftyWheelIsLocked()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.lock(2);

        machine.spin(2);
        machine.spin(2, 1);

        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * Verifica que colocar un símbolo con placeSymbol en una rueda lefty
     * funcione igual que en una normal: colocar no es girar, así que no
     * copia a la izquierda.
     */
    @Test
    public void shouldPlaceSymbolOnLeftyWithoutCopying()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");

        machine.placeSymbol(2, "green");

        assertEquals("green", machine.configuration()[1]);
    }

    /**
     * Verifica que una rueda lefty sí pueda bloquearse, intercambiarse y
     * eliminarse: solo la rebel tiene esas restricciones.
     */
    @Test
    public void shouldLeftyAllowLockSwapAndDelete()
    {
        agregarSimbolosNormales();
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);

        machine.lock(2);
        assertTrue(machine.ok());

        machine.unlock(2);
        machine.delWheel(2);
        assertTrue(machine.ok());
        assertEquals(1, machine.wheelTypes().length);
    }

    // Rueda rebelde

    /**
     * Verifica que una rueda rebel no se deje bloquear: lock la rechaza, la
     * rueda no queda bloqueada y ok() queda en false.
     */
    @Test
    public void shouldNotLockRebelWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);

        machine.lock(1);

        assertFalse(machine.ok());
        assertFalse(machine.wheelAt(1).isLocked());
        assertEquals("Accion no permitida: La rueda 1 es rebelde y no se deja bloquear.",
                     machine.lastMessage());
    }

    /**
     * Verifica que, como la rueda rebel nunca se bloquea, siga girando
     * después de intentar bloquearla.
     */
    @Test
    public void shouldRebelWheelKeepSpinningAfterLockAttempt()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);
        machine.placeSymbol(1, "red");

        machine.lock(1);
        machine.spin(1, 1);

        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Verifica que desbloquear una rueda rebel no genere ningún error: la
     * operación se completa aunque la rueda nunca estuvo bloqueada.
     */
    @Test
    public void shouldUnlockRebelWheelWithoutError()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);

        machine.unlock(1);

        assertTrue(machine.ok());
    }

    /**
     * Verifica que una rueda rebel no se deje intercambiar, ya sea que esté
     * en el primer o en el segundo argumento de swap, y que las ruedas
     * queden con los símbolos que tenían.
     */
    @Test
    public void shouldNotSwapRebelWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);

        machine.swap(2, 1);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
        assertEquals("Accion no permitida: Una de las ruedas es rebelde y no se deja intercambiar.",
                     machine.lastMessage());
    }

    /**
     * Verifica que una rueda rebel no se deje eliminar: delWheel no la
     * quita y ok() queda en false.
     */
    @Test
    public void shouldNotDeleteRebelWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);

        machine.delWheel(1);

        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"rebel"}, machine.wheelTypes());
        assertEquals("Accion no permitida: La rueda 1 es rebelde y no se deja eliminar.",
                     machine.lastMessage());
    }

    /**
     * Verifica que la rueda rebel tampoco se elimine cuando delWheel recibe
     * una posición fuera de rango que se ajusta a ella (primera o última).
     */
    @Test
    public void shouldNotDeleteRebelWheelWhenPositionIsAdjusted()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);
        machine.addWheel("rebel", 3);

        machine.delWheel(0);
        assertFalse(machine.ok());
        machine.delWheel(99);
        assertFalse(machine.ok());

        assertEquals(3, machine.wheelTypes().length);
    }

    /**
     * Verifica que eliminar una rueda normal vecina de una rebel funcione y
     * deje intacta a la rebel.
     */
    @Test
    public void shouldDeleteNormalWheelNextToRebelWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);

        machine.delWheel(2);

        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"rebel"}, machine.wheelTypes());
    }

    /**
     * Verifica que spin(String[]) no se vea afectado por una rueda rebel:
     * la acomoda igual que a cualquier otra.
     */
    @Test
    public void shouldSetConfigurationOnRebelWheel()
    {
        agregarSimbolosNormales();
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);

        machine.spin(new String[]{"green", "blue"});

        assertEquals("green", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    // Rueda mirror (nuevo tipo propuesto)

    /**
     * Verifica que una rueda mirror gire al revés: pedir pasos hacia
     * adelante la hace retroceder.
     */
    @Test
    public void shouldMirrorWheelSpinBackwards()
    {
        agregarSimbolosNormales();
        machine.addWheel("mirror", 1);
        machine.placeSymbol(1, "blue");

        machine.spin(1, 1);

        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Verifica que una rueda mirror avance cuando se le piden pasos
     * negativos, y que dé la vuelta de forma circular.
     */
    @Test
    public void shouldMirrorWheelSpinForwardWithNegativeSteps()
    {
        agregarSimbolosNormales();
        machine.addWheel("mirror", 1);
        machine.placeSymbol(1, "green");

        machine.spin(1, -1);
        assertEquals("red", machine.configuration()[0]);

        machine.spin(1, 2);
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Verifica que la rueda mirror funcione como una normal en todo lo
     * demás: se puede bloquear, intercambiar y eliminar.
     */
    @Test
    public void shouldMirrorWheelAllowLockSwapAndDelete()
    {
        agregarSimbolosNormales();
        machine.addWheel("mirror", 1);
        machine.addWheel("normal", 2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);
        assertTrue(machine.ok());
        machine.lock(1);
        assertTrue(machine.ok());
        machine.unlock(1);
        machine.delWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.wheelTypes().length);
    }

    // addSymbol(type, pos, color): tipos de símbolo

    /**
     * Verifica que addSymbol(type, pos, color) agregue un símbolo de cada
     * tipo disponible, en el orden pedido.
     */
    @Test
    public void shouldAddOneSymbolOfEachType()
    {
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "blue");
        machine.addSymbol("shy", 3, "green");

        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "green"}, machine.symbols());
        assertArrayEquals(new String[]{"normal", "ephemeral", "shy"}, machine.symbolTypes());
    }

    /**
     * Verifica que addSymbol(int, String), la versión de los ciclos
     * anteriores, siga creando un símbolo normal.
     */
    @Test
    public void shouldCreateNormalSymbolWithOldAddSymbol()
    {
        machine.addSymbol(1, "red");

        assertArrayEquals(new String[]{"normal"}, machine.symbolTypes());
    }

    /**
     * Verifica que un tipo de símbolo que no existe no agregue nada y deje
     * ok() en false.
     */
    @Test
    public void shouldNotAddSymbolOfUnknownType()
    {
        machine.addSymbol("golden", 1, "red");

        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
        assertEquals("Accion no permitida: El tipo de simbolo no esta disponible.",
                     machine.lastMessage());
    }

    /**
     * Verifica que un tipo de símbolo null no agregue nada ni lance error.
     */
    @Test
    public void shouldNotAddSymbolWhenTypeIsNull()
    {
        machine.addSymbol(null, 1, "red");

        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Verifica que un color no disponible se rechace aunque el tipo sea
     * válido.
     */
    @Test
    public void shouldNotAddTypedSymbolWithUnavailableColor()
    {
        machine.addSymbol("shy", 1, "purple");

        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Verifica que no se pueda repetir un color aunque el segundo símbolo
     * sea de otro tipo: el color identifica al símbolo.
     */
    @Test
    public void shouldNotAddSymbolWithRepeatedColorOfAnotherType()
    {
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("shy", 2, "red");

        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"normal"}, machine.symbolTypes());
    }

    /**
     * Verifica que addSymbol(type, pos, color) respete la posición: menor o
     * igual a uno agrega al principio y mayor que el tamaño al final.
     */
    @Test
    public void shouldAddTypedSymbolsAtTheGivenPosition()
    {
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("shy", 0, "blue");
        machine.addSymbol("ephemeral", 50, "green");

        assertArrayEquals(new String[]{"blue", "red", "green"}, machine.symbols());
        assertArrayEquals(new String[]{"shy", "normal", "ephemeral"}, machine.symbolTypes());
    }

    /**
     * Verifica que una máquina cerrada con exit() no acepte símbolos nuevos
     * y que symbolTypes() devuelva null.
     */
    @Test
    public void shouldNotAddTypedSymbolWhenMachineIsClosed()
    {
        machine.addSymbol("normal", 1, "red");
        machine.exit();
        machine.addSymbol("shy", 2, "blue");

        assertFalse(machine.ok());
        assertNull(machine.symbolTypes());
    }

    // Símbolo ephemeral

    /**
     * Verifica que un símbolo efímero empiece con el tamaño completo, igual
     * que uno normal.
     */
    @Test
    public void shouldEphemeralSymbolStartWithFullSize()
    {
        machine.addSymbol("ephemeral", 1, "red");

        assertEquals(Symbol.DEFAULT_SIZE, machine.symbolOf("red").getSize());
    }

    /**
     * Verifica que cada vez que el símbolo efímero es seleccionado con
     * placeSymbol su tamaño disminuya, hasta quedar como un punto y
     * quedarse ahí (nunca llega a cero ni a negativo).
     */
    @Test
    public void shouldEphemeralSymbolShrinkUntilItIsAPoint()
    {
        machine.addSymbol("ephemeral", 1, "red");
        machine.addWheel(1);
        Symbol symbol = machine.symbolOf("red");
        int anterior = symbol.getSize();

        for (int i = 0; i < 10; i++){
            machine.placeSymbol(1, "red");
            assertTrue(symbol.getSize() <= anterior);
            assertTrue(symbol.getSize() >= EphemeralSymbol.MIN_SIZE);
            anterior = symbol.getSize();
        }

        assertEquals(EphemeralSymbol.MIN_SIZE, symbol.getSize());
    }

    /**
     * Verifica que el símbolo efímero se encoja cuando una rueda queda
     * mostrándolo por un giro con spin(wheel, steps).
     */
    @Test
    public void shouldEphemeralSymbolShrinkWhenWheelSpinsOntoIt()
    {
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");

        machine.spin(1, 1);

        assertEquals("blue", machine.configuration()[0]);
        assertEquals(Symbol.DEFAULT_SIZE - EphemeralSymbol.STEP,
                     machine.symbolOf("blue").getSize());
    }

    /**
     * Verifica que un giro de 0 pasos no es un giro: el símbolo efímero
     * conserva su tamaño.
     */
    @Test
    public void shouldNotShrinkEphemeralSymbolWhenSpinningZeroSteps()
    {
        machine.addSymbol("ephemeral", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "blue");

        machine.spin(1, 0);
        machine.placeSymbol(1, "blue");

        assertEquals(Symbol.DEFAULT_SIZE, machine.symbolOf("red").getSize());
    }

    /**
     * Verifica que el símbolo efímero se encoja una vez por cada rueda
     * que lo selecciona con spin(String[]), pero no por las ruedas
     * bloqueadas, que no cambian.
     */
    @Test
    public void shouldShrinkEphemeralSymbolOncePerSelectedWheel()
    {
        machine.addSymbol("ephemeral", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(2, "blue");
        machine.placeSymbol(3, "blue");
        machine.lock(3);

        machine.spin(new String[]{"red", "red", "red"});

        assertEquals(Symbol.DEFAULT_SIZE - 2 * EphemeralSymbol.STEP,
                     machine.symbolOf("red").getSize());
    }

    /**
     * Verifica que encogerse no cambie lo que muestra la máquina: la
     * configuración sigue indicando el color del símbolo efímero.
     */
    @Test
    public void shouldEphemeralSymbolKeepItsColorInConfiguration()
    {
        machine.addSymbol("ephemeral", 1, "red");
        machine.addWheel(1);
        for (int i = 0; i < 10; i++){
            machine.placeSymbol(1, "red");
        }

        assertEquals("red", machine.configuration()[0]);
    }

    // Símbolo shy

    /**
     * Verifica que un símbolo tímido empiece visible.
     */
    @Test
    public void shouldShySymbolStartVisible()
    {
        machine.addSymbol("shy", 1, "red");

        assertTrue(machine.symbolOf("red").isShown());
    }

    /**
     * Verifica que el símbolo tímido alterne entre invisible y visible cada
     * vez que es seleccionado en una rueda.
     */
    @Test
    public void shouldShySymbolAlternateVisibilityEachTimeItIsSelected()
    {
        machine.addSymbol("shy", 1, "red");
        machine.addWheel(1);
        Symbol symbol = machine.symbolOf("red");

        machine.placeSymbol(1, "red");
        assertFalse(symbol.isShown());
        machine.placeSymbol(1, "red");
        assertTrue(symbol.isShown());
        machine.placeSymbol(1, "red");
        assertFalse(symbol.isShown());
    }

    /**
     * Verifica que el símbolo tímido también alterne cuando una rueda queda
     * mostrándolo por un giro con spin(wheel, steps).
     */
    @Test
    public void shouldShySymbolAlternateWhenWheelSpinsOntoIt()
    {
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("shy", 2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");

        machine.spin(1, 1);

        assertEquals("blue", machine.configuration()[0]);
        assertFalse(machine.symbolOf("blue").isShown());
    }

    /**
     * Verifica que un símbolo tímido oculto siga contando en la
     * configuración y en el jackpot: ocultarse es solo un efecto gráfico.
     */
    @Test
    public void shouldHiddenShySymbolStillCountForJackpot()
    {
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol("normal", 2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);

        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        machine.placeSymbol(1, "red");

        assertFalse(machine.symbolOf("red").isShown());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
        assertEquals(1, machine.distinctSymbols());
        assertTrue(machine.isJackpot());
    }
    // Clases de los tipos (símbolos y ruedas) por separado
    /**
     * Verifica que el símbolo normal no cambie nunca: select() no altera su
     * tamaño, su visibilidad ni su forma, y no lleva marca de tipo.
     */
    @Test
    public void shouldNormalSymbolNeverChange()
    {
        Symbol symbol = new NormalSymbol("red");

        for (int i = 0; i < 5; i++){
            symbol.select();
        }

        assertEquals("normal", symbol.getType());
        assertEquals("red", symbol.getColor());
        assertEquals(Symbol.DEFAULT_SIZE, symbol.getSize());
        assertTrue(symbol.isShown());
        assertEquals(Symbol.CIRCLE, symbol.getShape());
        assertNull(symbol.getMarkerColor());
    }

    /**
     * Verifica la secuencia exacta de tamaños del símbolo efímero:
     * 30, 25, 20, 15, 10, 5 y por último 1 (un punto).
     */
    @Test
    public void shouldEphemeralSymbolFollowExpectedSizes()
    {
        EphemeralSymbol symbol = new EphemeralSymbol("blue");
        int[] esperados = {30, 25, 20, 15, 10, 5, 1, 1};

        assertFalse(symbol.isPoint());
        for (int i = 1; i < esperados.length; i++){
            symbol.select();
            assertEquals(esperados[i], symbol.getSize());
        }
        assertTrue(symbol.isPoint());
        assertEquals("ephemeral", symbol.getType());
        assertEquals(Symbol.CIRCLE, symbol.getShape());
        assertEquals("yellow", symbol.getMarkerColor());
    }

    /**
     * Verifica que el símbolo tímido sea cuadrado, lleve marca cian y
     * conserve su tamaño mientras alterna su visibilidad.
     */
    @Test
    public void shouldShySymbolBeSquareAndKeepItsSize()
    {
        Symbol symbol = new ShySymbol("green");

        symbol.select();

        assertEquals("shy", symbol.getType());
        assertEquals(Symbol.SQUARE, symbol.getShape());
        assertEquals("cyan", symbol.getMarkerColor());
        assertEquals(Symbol.DEFAULT_SIZE, symbol.getSize());
        assertFalse(symbol.isShown());
    }

    /**
     * Verifica que cada tipo de rueda tenga una etiqueta de color distinta,
     * para que se puedan distinguir a simple vista.
     */
    @Test
    public void shouldEachWheelTypeHaveADifferentTagColor()
    {
        Wheel[] ruedas = {new NormalWheel(3), new LeftyWheel(3),
                          new RebelWheel(3), new MirrorWheel(3)};

        for (int i = 0; i < ruedas.length; i++){
            for (int j = i + 1; j < ruedas.length; j++){
                assertNotEquals(ruedas[i].getTagColor(), ruedas[j].getTagColor());
            }
        }
    }

    /**
     * Verifica que cada tipo de símbolo se distinga de los demás por su
     * forma o por su marca de tipo.
     */
    @Test
    public void shouldEachSymbolTypeLookDifferent()
    {
        Symbol normal = new NormalSymbol("red");
        Symbol ephemeral = new EphemeralSymbol("red");
        Symbol shy = new ShySymbol("red");

        assertNull(normal.getMarkerColor());
        assertNotNull(ephemeral.getMarkerColor());
        assertNotNull(shy.getMarkerColor());
        assertNotEquals(ephemeral.getMarkerColor(), shy.getMarkerColor());
        assertNotEquals(normal.getShape(), shy.getShape());
    }

    /**
     * Verifica los permisos de cada tipo de rueda: solo la rebel rechaza
     * bloquearse, intercambiarse y eliminarse, y su setLocked se ignora.
     */
    @Test
    public void shouldOnlyRebelWheelRefuseLockSwapAndDelete()
    {
        Wheel[] permitidas = {new NormalWheel(3), new LeftyWheel(3), new MirrorWheel(3)};
        for (int i = 0; i < permitidas.length; i++){
            assertTrue(permitidas[i].canBeLocked());
            assertTrue(permitidas[i].canBeSwapped());
            assertTrue(permitidas[i].canBeDeleted());
        }

        Wheel rebel = new RebelWheel(3);
        rebel.setLocked(true);

        assertFalse(rebel.canBeLocked());
        assertFalse(rebel.canBeSwapped());
        assertFalse(rebel.canBeDeleted());
        assertFalse(rebel.isLocked());
    }

    /**
     * Verifica a nivel de la clase Wheel que la lefty copie el índice de
     * su vecina izquierda y que sin vecina rote como una rueda normal.
     */
    @Test
    public void shouldLeftyWheelCopyIndexOfLeftNeighbour()
    {
        Wheel izquierda = new NormalWheel(3);
        izquierda.setVisibleIndex(2);
        Wheel lefty = new LeftyWheel(3);
        lefty.setVisibleIndex(0);

        lefty.turn(1, izquierda);
        assertEquals(2, lefty.getVisibleIndex());

        lefty.setVisibleIndex(0);
        lefty.rotate(1, 3, izquierda);
        assertEquals(2, lefty.getVisibleIndex());

        lefty.setVisibleIndex(0);
        lefty.rotate(1, 3, null);
        assertEquals(1, lefty.getVisibleIndex());
    }

    /**
     * Verifica a nivel de la clase Wheel que la mirror invierta el sentido
     * de rotación y que la rueda normal no lo haga.
     */
    @Test
    public void shouldMirrorWheelInvertRotationDirection()
    {
        Wheel normal = new NormalWheel(5);
        Wheel mirror = new MirrorWheel(5);
        normal.setVisibleIndex(2);
        mirror.setVisibleIndex(2);

        normal.rotate(1, 5);
        mirror.rotate(1, 5);

        assertEquals(3, normal.getVisibleIndex());
        assertEquals(1, mirror.getVisibleIndex());
    }

    /**
     * Verifica que SlotMachine(n), que usa los tipos normales, siga creando
     * n ruedas normales y n símbolos normales.
     */
    @Test
    public void shouldNConstructorCreateOnlyNormalTypes()
    {
        SlotMachine nueva = new SlotMachine(4);

        assertArrayEquals(new String[]{"normal", "normal", "normal", "normal"},
                          nueva.wheelTypes());
        assertArrayEquals(new String[]{"normal", "normal", "normal", "normal"},
                          nueva.symbolTypes());
    }

    /**
     * Verifica que la solución de la maratón (solve) siga encontrando el
     * jackpot con la máquina refactorizada, incluso con el máximo de ruedas.
     */
    @Test
    public void shouldSolveStillFindTheJackpot()
    {
        for (int n = 2; n <= 8; n++){
            SlotMachineContest contest = new SlotMachineContest();
            contest.solve(n);
            assertTrue(contest.ultimaMaquina().isJackpot());
        }
    }

    /**
     * Verifica que mezclar todos los tipos de ruedas y de símbolos en una
     * misma máquina funcione: todas las ruedas giran sin errores y cada una
     * muestra un color que existe.
     */
    @Test
    public void shouldWorkWithAllTypesTogether()
    {
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 2, "blue");
        machine.addSymbol("shy", 3, "green");
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("rebel", 3);
        machine.addWheel("mirror", 4);

        for (int i = 0; i < 20; i++){
            machine.spin();
            machine.spin(4, 3);
            assertTrue(machine.ok());
        }

        String[] config = machine.configuration();
        assertEquals(4, config.length);
        for (int i = 0; i < config.length; i++){
            assertTrue(config[i].equals("red") || config[i].equals("blue")
                       || config[i].equals("green"));
        }
    }

    /**
     * Libera el escenario de pruebas. Se ejecuta después de cada método de
     * prueba, eliminando la referencia a la máquina utilizada.
     */
    @AfterEach
    public void tearDown()
    {
        machine = null;
    }
}
