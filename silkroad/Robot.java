/**
 * Clase que representa un robot en la ruta de seda.
 * Cada robot tiene una posición 
 */
public class Robot {
    private int initialLocation; // posinicial
    private int currentLocation; // pos actual
    private Triangle shape;    

    /**
     * Constructor de la clase Robot.
     * @param location posición inicial en la ruta
     */
    public Robot(int location) {
        this.initialLocation = location;
        this.currentLocation = location;
        this.shape = new Triangle();
        this.shape.changeColor(randomColor());
        this.shape.moveHorizontal(location * 20); // separación
        this.shape.moveVertical(100);             
        this.shape.changeSize(20, 20);            // tamaño
        this.shape.makeVisible();
    }

    /** Devuelve la pos actual */
    public int getLocation() {
        return currentLocation;
    }

    /** Devuelve la pos inicial */
    public int getInitialLocation() {
        return initialLocation;
    }

    /** Mueve el robot una cantidad de pasos */
    public void move(int meters) {
        shape.moveHorizontal(meters * 20);
        currentLocation += meters;
    }

    /** Regresa el robot a su posición inicial */
    public void returnToInitial() {
        int steps = initialLocation - currentLocation;
        move(steps);
    }

    /** Borra la representación*/
    public void erase() {
        shape.makeInvisible();
    }

    /** Genera un color aleatorio */
    private String randomColor() {
        String[] colors = {"red","blue","green","yellow","magenta","black"};
        int pos = (int)(Math.random() * colors.length);
        return colors[pos];
    }
}
