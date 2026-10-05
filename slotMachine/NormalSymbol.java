/**
 * Símbolo normal: el que ya tenía la máquina. Se dibuja como un círculo de
 * tamaño fijo, siempre visible y sin marca de tipo.
 *
 * @author César Daniel Morales Sandoval
 * @version 1.0 (september 2026)
 */
public class NormalSymbol extends Symbol
{
    /**
     * Crea un símbolo normal del color indicado.
     *
     * @param color el color del símbolo
     */
    public NormalSymbol(String color)
    {
        super(color);
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
}
