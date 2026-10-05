/**
 * Símbolo tímido: alterna entre visible e invisible cada vez que es
 * seleccionado en una rueda. Empieza visible. Se dibuja como un cuadrado y
 * lleva una marca cian en la esquina de la rueda (la marca se ve aunque el
 * símbolo esté oculto, para saber que ahí hay un símbolo tímido).
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class ShySymbol extends Symbol
{
    private boolean shown;

    /**
     * Crea un símbolo tímido del color indicado, inicialmente visible.
     *
     * @param color el color del símbolo
     */
    public ShySymbol(String color)
    {
        super(color);
        shown = true;
    }
    
    /**
     * {@inheritDoc}
     *
     * @return "shy"
     */
    // {@inheritDoc} fue una idea dada por la IA generativa para heredar la documentacion
    public String getType()
    {
        return "shy";
    }

    /**
     * {@inheritDoc}
     *
     * @return true si el símbolo está visible en este momento
     */
    public boolean isShown()
    {
        return shown;
    }

    /**
     * {@inheritDoc}
     *
     * @return SQUARE
     */
    public String getShape()
    {
        return SQUARE;
    }

    /**
     * {@inheritDoc}
     *
     * @return "cyan"
     */
    public String getMarkerColor()
    {
        return "cyan";
    }

    /**
     * Alterna el estado del símbolo: si estaba visible pasa a invisible y
     * viceversa.
     */
    public void select()
    {
        shown = !shown;
    }
}
