/**
 * Rueda mirror (espejo): es el nuevo tipo de rueda propuesto por los
 * estudiantes (requisito 19). Gira en sentido contrario al que se le pide:
 * si se le piden n pasos hacia adelante, retrocede n pasos, y viceversa.
 * Los giros aleatorios, colocar un símbolo, bloquear, intercambiar y eliminar
 * funcionan igual que en la rueda normal. Su etiqueta es naranja.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class MirrorWheel extends Wheel
{
    /**
     * Crea una rueda espejo con un símbolo visible aleatorio.
     *
     * @param cantSymbols número total de símbolos de la máquina
     */
    public MirrorWheel(int cantSymbols)
    {
        super(cantSymbols);
    }

    /**
     * {@inheritDoc}
     *
     * @return "mirror"
     */
    // {@inheritDoc} fue una idea dada por la IA generativa para heredar la documentacion
    public String getType()
    {
        return "mirror";
    }

    /**
     * {@inheritDoc}
     *
     * @return "orange"
     */
    public String getTagColor()
    {
        return "orange";
    }

    /**
     * Rota la rueda en el sentido contrario al pedido.
     *
     * @param steps número de pasos pedidos (se aplican con signo opuesto)
     * @param totalSymbols número total de símbolos disponibles en la máquina
     * @param left se ignora
     */
    public void rotate(int steps, int totalSymbols, Wheel left)
    {
        super.rotate(-steps, totalSymbols, left);
    }
}
