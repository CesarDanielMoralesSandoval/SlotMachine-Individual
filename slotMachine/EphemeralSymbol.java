/**
 * Símbolo efímero: cada vez que es seleccionado en una rueda disminuye su
 * tamaño, hasta quedar reducido a un punto. Se dibuja como un círculo
 * que se va encogiendo y lleva una marca amarilla en la esquina de la rueda.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class EphemeralSymbol extends Symbol
{
    /** Tamaño mínimo: un punto. */
    public static final int MIN_SIZE = 1;
    /** Cuántos pixeles se encoge en cada selección. */
    public static final int STEP = 5;

    private int size;

    /**
     * Crea un símbolo efímero del color indicado, con el tamaño completo.
     *
     * @param color el color del símbolo
     */
    public EphemeralSymbol(String color)
    {
        super(color);
        size = DEFAULT_SIZE;
    }

    /**
     * {@inheritDoc}
     *
     * @return "ephemeral"
     */
    // {@inheritDoc} fue una idea dada por la IA generativa para heredar la documentacion
    public String getType()
    {
        return "ephemeral";
    }

    /**
     * Devuelve el tamaño actual del símbolo, que baja con cada selección.
     *
     * @return el tamaño actual, nunca menor que MIN_SIZE
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Indica si el símbolo ya se redujo a un punto.
     *
     * @return true si su tamaño es MIN_SIZE
     */
    public boolean isPoint()
    {
        return size == MIN_SIZE;
    }

    /**
     * {@inheritDoc}
     *
     * @return "yellow"
     */
    public String getMarkerColor()
    {
        return "yellow";
    }

    /**
     * Reduce el tamaño del símbolo en STEP pixeles, sin bajar de MIN_SIZE.
     */
    public void select()
    {
        size = Math.max(MIN_SIZE, size - STEP);
    }
}
