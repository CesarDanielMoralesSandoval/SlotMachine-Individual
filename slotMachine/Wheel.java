import java.util.Random;
/**
 * La clase Wheel representa una rueda que contiene un símbolo circular en su interior.
 * Se utiliza para simular ruedas con símbolos visibles como en una máquina tragamonedas
 * @author Juan David Rojas and César Morales
 * @version 1.0 (22 agosto 2026)
 */
public abstract class Wheel
{
    private static final int SIZE = 70;
    private static final int TAG_HEIGHT = 10;
    private static final int TAG_OFFSET = 72;
    private static final int SYMBOL_OFFSET = 20;
    private static final int MARKER_SIZE = 12;
    private static final int MARKER_X = 54;
    private static final int MARKER_Y = 4;
    
    private int visibleIndex;
    private Rectangle wheelFigure;
    private Rectangle tagFigure;
    private Circle symbolCircle;
    private Rectangle symbolSquare;
    private Circle markerFigure;
    private boolean locked;
    private boolean figurasCreadas;
    private int currentX;
    private int currentY;
    private int symbolSize;
    private int symbolOffset;
    private boolean squareShape;
    private boolean symbolShown;
    private boolean markerShown;
     /**
     * Constructor de la clase Wheel.
     * Inicializa la rueda con un número de símbolos y asigna un índice visible aleatorio.
     * También crea las figuras gráficas (rectángulo y círculo) y las posiciona.
     *
     * @param cantSymbols número total de símbolos posibles en la rueda.
     *  Si es mayor que 0, se selecciona un índice aleatorio.
     *  Si es 0 o negativo, el índice visible será 0.
     */
    public Wheel(int cantSymbols)
    {
        Random random = new Random();
        if (cantSymbols > 0){
            visibleIndex = random.nextInt(cantSymbols);
        } else {
            visibleIndex = 0;
        }
        locked = false;
        currentX = 0;
        currentY = 0;
        figurasCreadas = false;
        symbolSize = Symbol.DEFAULT_SIZE;
        symbolOffset = SYMBOL_OFFSET;
        squareShape = false;
        symbolShown = true;
        markerShown = false;
    }
    
    /**
     * Devuelve el nombre del tipo de rueda, el mismo que se usa en
     * SlotMachine.addWheel(type, pos).
     *
     * @return "normal", "lefty", "rebel" o "mirror"
     */
    public abstract String getType();

    /**
     * Devuelve el color de la etiqueta que identifica el tipo de la rueda.
     *
     * @return el color de la etiqueta
     */
    public abstract String getTagColor();

    /**
     * Indica si la rueda se deja bloquear. Por defecto sí.
     *
     * @return true si se puede bloquear
     */
    public boolean canBeLocked()
    {
        return true;
    }

    /**
     * Indica si la rueda se deja intercambiar con otra. Por defecto sí.
     *
     * @return true si se puede intercambiar
     */
    public boolean canBeSwapped()
    {
        return true;
    }

    /**
     * Indica si la rueda se deja eliminar. Por defecto sí.
     *
     * @return true si se puede eliminar
     */
    public boolean canBeDeleted()
    {
        return true;
    }

    /**
     * Crea las figuras gráficas de la rueda (el rectángulo y el círculo
     * del símbolo) la primera vez que se necesitan mostrar o modificar.
     * Si ya fueron creadas, no hace nada.
     */
    private void crearFiguras()
    {
        if (!figurasCreadas){
            wheelFigure = new Rectangle();
            wheelFigure.changeSize(SIZE, SIZE);

            tagFigure = new Rectangle();
            tagFigure.changeSize(TAG_HEIGHT, SIZE);
            tagFigure.moveVertical(TAG_OFFSET);
            tagFigure.changeColor(getTagColor());

            symbolCircle = new Circle();
            symbolCircle.moveHorizontal(SYMBOL_OFFSET);
            symbolCircle.moveVertical(SYMBOL_OFFSET);

            symbolSquare = new Rectangle();
            symbolSquare.changeSize(Symbol.DEFAULT_SIZE, Symbol.DEFAULT_SIZE);
            symbolSquare.moveHorizontal(SYMBOL_OFFSET);
            symbolSquare.moveVertical(SYMBOL_OFFSET);

            markerFigure = new Circle();
            markerFigure.changeSize(MARKER_SIZE);
            markerFigure.moveHorizontal(MARKER_X);
            markerFigure.moveVertical(MARKER_Y);

            figurasCreadas = true;
        }
    }

    /**
     * Devuelve el índice del símbolo actualmente visible.
     *
     * @return índice del símbolo visible.
     */
    public int getVisibleIndex()
    {
        return visibleIndex;
    }
    
     /**
     * Establece manualmente el índice del símbolo visible.
     *
     * @param index nuevo índice del símbolo visible.
     */
    public void setVisibleIndex(int index)
    {
        visibleIndex = index;
    }
    
    /**
     * Configura cómo se ve el símbolo dentro de la rueda según lo que el
     * propio símbolo indica: color, tamaño, forma, si está visible y
     * marca de tipo. Crea las figuras si aún no existían.
     *
     * @param symbol el símbolo que la rueda está mostrando
     */
    public void showSymbol(Symbol symbol)
    {
        crearFiguras();
        String color = symbol.getColor();
        symbolCircle.changeColor(color);
        symbolSquare.changeColor(color);
        ajustarTamaño(symbol.getSize());
        squareShape = symbol.getShape().equals(Symbol.SQUARE);
        symbolShown = symbol.isShown();
        String marker = symbol.getMarkerColor();
        markerShown = (marker != null);
        if (markerShown){
            markerFigure.changeColor(marker);
        }
    }
    
    /**
     * Cambia el tamaño del símbolo manteniéndolo centrado en la rueda.
     * La posición se calcula siempre a partir del tamaño (no acumulando
     * desplazamientos) para que no se desvíe por redondeos.
     *
     * @param newSize el nuevo tamaño del símbolo en pixeles
     */
    private void ajustarTamaño(int newSize)
    {
        if (newSize != symbolSize){
            int newOffset = SYMBOL_OFFSET + (Symbol.DEFAULT_SIZE - newSize) / 2;
            int delta = newOffset - symbolOffset;
            symbolCircle.changeSize(newSize);
            symbolSquare.changeSize(newSize, newSize);
            symbolCircle.moveHorizontal(delta);
            symbolCircle.moveVertical(delta);
            symbolSquare.moveHorizontal(delta);
            symbolSquare.moveVertical(delta);
            symbolSize = newSize;
            symbolOffset = newOffset;
        }
    }
    
    /**
     * Establece la posición de la rueda en coordenadas (x, y).
     * Mueve tanto el rectángulo como el círculo a la nueva ubicación.
     * Crea las figuras si aún no existían.
     *
     * @param x nueva posición en el eje X.
     * @param y nueva posición en el eje Y.
     */
    public void setPosition(int x, int y)
    {
        crearFiguras();
        int dx = x - currentX;
        int dy = y - currentY;
        wheelFigure.moveHorizontal(dx);
        wheelFigure.moveVertical(dy);
        tagFigure.moveHorizontal(dx);
        tagFigure.moveVertical(dy);
        symbolCircle.moveHorizontal(dx);
        symbolCircle.moveVertical(dy);
        symbolSquare.moveHorizontal(dx);
        symbolSquare.moveVertical(dy);
        markerFigure.moveHorizontal(dx);
        markerFigure.moveVertical(dy);
        currentX = x;
        currentY = y;
    }
    
    /**
     * Permite modificar el estado de las ruedas para que esten 
     * bloquadas o desbloqueadas
     *
     * @param value true para bloquear la rueda, false para permitir que gire.
     */
    public void setLocked(boolean value){
        locked=value;
    }
    
    /**
     * Indica si la rueda se encuentra actualmente bloqueada.
     *
     * @return true si la rueda está bloqueada, false en caso contrario.
     */
    public boolean isLocked(){
        return locked;
    }
    
    /**
     * Gira la rueda hasta el símbolo que indica el índice aleatorio recibido.
     * Los tipos de rueda pueden redefinirlo (por ejemplo, la lefty copia a
     * la rueda de su izquierda).
     *
     * @param randomIndex índice del símbolo que salió en el giro
     * @param left la rueda que está inmediatamente a la izquierda, o null
     * si esta es la primera rueda
     */
    public void turn(int randomIndex, Wheel left)
    {
        visibleIndex = randomIndex;
    }
    
    /**
     * Rota la rueda un número de pasos (hacia adelante o hacia atras), avanzando el índice
     * visible circulatmente según el total de símbolos disponibles
     *
     * @param steps número de pasos que debe avanzar la rueda
     * @param totalSymbols número total de símbolos disponibles en la máquina
     * @param left la rueda que está inmediatamente a la izquierda, o null
     * si esta es la primera rueda
     */
    public void rotate(int steps, int totalSymbols, Wheel left){
        if (totalSymbols <= 0){
            return;
        }
        visibleIndex = ((visibleIndex + steps) % totalSymbols + totalSymbols) % totalSymbols;
    }
    
    /**
     * Rota la rueda sin tener en cuenta a ninguna rueda vecina.
     *
     * @param steps número de pasos que debe avanzar la rueda
     * @param totalSymbols número total de símbolos disponibles en la máquina
     */
    public void rotate(int steps, int totalSymbols)
    {
        rotate(steps, totalSymbols, null);
    }
    
    /**
     * Muestra la rueda en pantalla. Si el parámetro es verdadero,
     * también muestra el símbolo dentro de la rueda.
     * Crea las figuras si aún no existían.
     * 
     * @param flag true para mostrar el símbolo, false para ocultarlo.
     */
    public void makeVisible(boolean flag){
        crearFiguras();
        wheelFigure.makeVisible();
        tagFigure.makeVisible();
        boolean mostrar = flag && symbolShown;
        if (mostrar && !squareShape){
            symbolCircle.makeVisible();
        } else {
            symbolCircle.makeInvisible();
        }
        if (mostrar && squareShape){
            symbolSquare.makeVisible();
        } else {
            symbolSquare.makeInvisible();
        }
        if (flag && markerShown){
            markerFigure.makeVisible();
        } else {
            markerFigure.makeInvisible();
        }
    }
    
    /**
     * Oculta la rueda y el símbolo de la pantalla. Si las figuras nunca
     * se llegaron a crear no hace nada.
     */
    public void makeInvisible(){
        if (figurasCreadas){
            wheelFigure.makeInvisible();
            tagFigure.makeInvisible();
            symbolCircle.makeInvisible();
            symbolSquare.makeInvisible();
            markerFigure.makeInvisible();
        }
    }
}