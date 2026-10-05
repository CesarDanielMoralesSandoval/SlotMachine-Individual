/**
 * Representa un símbolo de la máquina tragamonedas. Es la clase base de
 * todos los tipos de símbolos (normal, efímero, tímido): define lo que todos
 * tienen en común (un color) y los puntos de extensión que cada tipo
 * redefine (tamaño, forma, visibilidad, marca de tipo y qué ocurre cuando
 * el símbolo es seleccionado en una rueda).
 *
 * El símbolo no se dibuja a sí mismo: solo describe cómo debe verse. Quien
 * lo dibuja es Wheel, porque el mismo símbolo puede estar mostrado en
 * varias ruedas al mismo tiempo.
 *
 * @author César Daniel Morales Sandoval
 * @version 2.0 (september 2026)
 */
public abstract class Symbol
{
    /** Tamaño (diámetro o lado, en pixeles) con el que se dibuja un símbolo. */
    public static final int DEFAULT_SIZE = 30;
    /** Forma redonda. */
    public static final String CIRCLE = "circle";
    /** Forma cuadrada. */
    public static final String SQUARE = "square";

    private String color;

    /**
     * Crea un símbolo del color indicado.
     *
     * @param color el color del símbolo (ej. "red", "blue", "green")
     */
    protected Symbol(String color)
    {
        this.color = color;
    }

    /**
     * Devuelve el color del símbolo. El color es la identidad del símbolo
     * dentro de la máquina: no puede haber dos símbolos con el mismo color.
     *
     * @return el color del símbolo
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Devuelve el nombre del tipo de símbolo, el mismo que se usa en
     * SlotMachine.addSymbol(type, pos, color).
     *
     * @return "normal", "ephemeral" o "shy"
     */
    public abstract String getType();

    /**
     * Devuelve el tamaño con el que se debe dibujar el símbolo. Por defecto
     * es DEFAULT_SIZE; los tipos que cambian de tamaño lo redefinen.
     *
     * @return el tamaño en pixeles
     */
    public int getSize()
    {
        return DEFAULT_SIZE;
    }

    /**
     * Indica si el símbolo está visible en este momento. Por defecto siempre
     * lo está; los tipos que se ocultan lo redefinen. Es solo un estado
     * gráfico: un símbolo invisible sigue existiendo en la rueda y sigue
     * contando para la configuración y para el jackpot.
     *
     * @return true si se debe dibujar, false si debe permanecer oculto
     */
    public boolean isShown()
    {
        return true;
    }

    /**
     * Devuelve la forma con la que se dibuja el símbolo.
     *
     * @return CIRCLE o SQUARE
     */
    public String getShape()
    {
        return CIRCLE;
    }

    /**
     * Devuelve el color de la marca que identifica el tipo del símbolo
     * (un punto pequeño en la esquina de la rueda). Los símbolos normales no
     * llevan marca.
     *
     * @return el color de la marca, o null si el tipo no lleva marca
     */
    public String getMarkerColor()
    {
        return null;
    }

    /**
     * Se invoca cada vez que el símbolo es seleccionado en una rueda (porque
     * la rueda se giró o porque se colocó el símbolo en ella). Por defecto
     * no hace nada; los tipos con comportamiento propio lo redefinen.
     */
    public void select()
    {
    }
}
