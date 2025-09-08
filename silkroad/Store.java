import java.awt.Color;

/**
 * Clase que representa la tienda en la ruta de seda.
 * Cada tienda tiene una localización y un monto de dinero
 */
public class Store {
    private int location;     // pos tienda en la ruta
    private int tenges;       // dinero tienda
    private Rectangle shape; 

    /**
     * Constructor de Store.
     */
    public Store(int location, int tenges) {
        this.location = location;
        this.tenges = tenges;
        this.shape = new Rectangle();
        this.shape.changeColor(randomColor());
        this.shape.moveHorizontal(location * 20); 
        this.shape.moveVertical(50);              
        this.shape.changeSize(20, 20);            
        this.shape.makeVisible();
    }

    /** 
     * Devuelve la posición de la tienda 
    */
    public int getLocation() {
        return location;
    }

    /** * Devuelve el dinero actual de la tienda*/
    public int getTenges() {
        return tenges;
    }

    /**Reabastece la tienda */
    public void resupply() {
        tenges += 100;
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
