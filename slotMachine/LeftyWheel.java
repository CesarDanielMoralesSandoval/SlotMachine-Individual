/**
 * Rueda lefty (zurda): si hay una rueda a su izquierda, al girar copia su
 * estado, es decir, pasa a mostrar el mismo símbolo que esa rueda. Si es la
 * primera rueda (no tiene nada a su izquierda) gira como una rueda normal.
 * Su etiqueta es azul.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class LeftyWheel extends Wheel
{
    /**
     * Crea una rueda lefty con un símbolo visible aleatorio.
     *
     * @param cantSymbols número total de símbolos de la máquina
     */
    public LeftyWheel(int cantSymbols)
    {
        super(cantSymbols);
    }

    /**
     * {@inheritDoc}
     *
     * @return "lefty"
     */
    // {@inheritDoc} fue una idea dada por la IA generativa para heredar la documentacion
    public String getType()
    {
        return "lefty";
    }

    /**
     * {@inheritDoc}
     *
     * @return "blue"
     */
    public String getTagColor()
    {
        return "blue";
    }

    /**
     * Si hay una rueda a la izquierda copia su símbolo visible; si no,
     * se comporta como una rueda normal.
     *
     * @param randomIndex índice del símbolo que salió en el giro
     * @param left la rueda de la izquierda, o null si no hay
     */
    public void turn(int randomIndex, Wheel left)
    {
        if (left != null){
            setVisibleIndex(left.getVisibleIndex());
        } else {
            super.turn(randomIndex, left);
        }
    }

    /**
     * Si hay una rueda a la izquierda copia su símbolo visible sin importar
     * los pasos; si no, rota como una rueda normal.
     *
     * @param steps número de pasos que debe avanzar la rueda
     * @param totalSymbols número total de símbolos disponibles en la máquina
     * @param left la rueda de la izquierda, o null si no hay
     */
    public void rotate(int steps, int totalSymbols, Wheel left)
    {
        if (left != null){
            setVisibleIndex(left.getVisibleIndex());
        } else {
            super.rotate(steps, totalSymbols, left);
        }
    }
}
