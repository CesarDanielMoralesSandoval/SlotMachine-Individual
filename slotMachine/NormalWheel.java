/**
 * Rueda normal: la que ya tenía la máquina. Gira, se bloquea, se intercambia
 * y se elimina sin ninguna restricción. Su etiqueta es blanca.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class NormalWheel extends Wheel
{
    /**
     * Crea una rueda normal con un símbolo visible aleatorio.
     *
     * @param cantSymbols número total de símbolos de la máquina
     */
    public NormalWheel(int cantSymbols)
    {
        super(cantSymbols);
    }

    /**
     * {@inheritDoc}
     *
     * @return "normal"
     */
    // {@inheritDoc} fue una idea dada por la IA generativa para heredar la documentacion
    public String getType()
    {
        return "normal";
    }

    /**
     * {@inheritDoc}
     *
     * @return "white"
     */
    public String getTagColor()
    {
        return "white";
    }
}
