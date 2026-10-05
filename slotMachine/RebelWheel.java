/**
 * Rueda rebel (rebelde): no se deja bloquear, ni intercambiar, ni eliminar.
 * Gira como una rueda normal. Su etiqueta es roja.
 *
 * @author Cesar Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class RebelWheel extends Wheel
{
    /**
     * Crea una rueda rebelde con un símbolo visible aleatorio.
     *
     * @param cantSymbols número total de símbolos de la máquina
     */
    public RebelWheel(int cantSymbols)
    {
        super(cantSymbols);
    }

    /**
     * {@inheritDoc}
     *
     * @return "rebel"
     */
    // {@inheritDoc} fue una idea dada por la IA generativa para heredar la documentacion
    public String getType()
    {
        return "rebel";
    }

    /**
     * {@inheritDoc}
     *
     * @return "red"
     */
    public String getTagColor()
    {
        return "red";
    }

    /**
     * {@inheritDoc}
     *
     * @return false: la rueda rebelde no se deja bloquear
     */
    public boolean canBeLocked()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return false: la rueda rebelde no se deja intercambiar
     */
    public boolean canBeSwapped()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @return false: la rueda rebelde no se deja eliminar
     */
    public boolean canBeDeleted()
    {
        return false;
    }

    /**
     * Una rueda rebelde nunca queda bloqueada: ignora la orden.
     *
     * @param value se ignora
     */
    public void setLocked(boolean value)
    {
        // Rebelde: nunca se bloquea.
    }
}
