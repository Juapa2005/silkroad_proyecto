import java.util.ArrayList;

/**
 * Clase principal del simulador SilkRoad.
 * gestión de tiendas y robots,
 * reinicio de la ruta, cálculo de ganancias y barra de progreso.
 */
public class SilkRoad {
    private int length;                  // long ruta
    private ArrayList<Store> stores;     // lista de tiendas
    private ArrayList<Robot> robots;     // lista de robots
    private boolean visible;             // se muestra o no el simulador
    private Canvas canvas;              

    // Barra progreso de ganancias
    private Rectangle barraProgreso;     
    private int gananciaMaxima;          // val max de referencia para la barra

    /**
     * Constructor de la clase SilkRoad.
     */
    public SilkRoad(int length) {
        this.length = length;
        this.stores = new ArrayList<Store>();
        this.robots = new ArrayList<Robot>();
        this.visible = false;
        this.canvas = Canvas.getCanvas();
        this.gananciaMaxima = 100; // valor inicial arb
        crearBarraProgreso();
    }

    /**
     * Hace visible el simulador.
     */
    public void makeVisible() {
        visible = true;
        canvas.setVisible(true);
        barraProgreso.makeVisible();
    }

    /**
     * Hace invisible el simulador.
     */
    public void makeInvisible() {
        visible = false;
        canvas.setVisible(false);
    }

    /**
     * Crea la barra de progreso inicial.
     */
    private void crearBarraProgreso() {
        barraProgreso = new Rectangle();
        barraProgreso.changeSize(20, 200); // alto y ancho inicial
        barraProgreso.changeColor("blue");
        barraProgreso.moveVertical(10);
        barraProgreso.moveHorizontal(10);
    }

    /**
     * Actualiza la barra de progreso - ganancia actual.
     */
    private void actualizarBarra() {
        int gananciaActual = profit();
        if (gananciaActual > gananciaMaxima) {
            gananciaMaxima = gananciaActual; // ajusta el máximo din
        }
        int ancho = 1; // ancho mínimo 
        if (gananciaMaxima > 0) {
            ancho = (gananciaActual * 200) / gananciaMaxima;
        }
        barraProgreso.changeSize(20, ancho);
    }

    /**
     * Coloca una tienda en la ruta
     */
    public void placeStore(int location, int tenges) {
        Store s = new Store(location, tenges);
        stores.add(s);
        actualizarBarra();
    }

    /**
     * Elimina una tienda en la ubicación indicada.
     */
    public void removeStore(int location) {
        Store target = null;
        for (Store s : stores) {
            if (s.getLocation() == location) {
                target = s;
                break;
            }
        }
        if (target != null) {
            target.erase();
            stores.remove(target);
            actualizarBarra();
        }
    }

    /**
     * Reabastece todas las tiendas de la rut.
     */
    public void resupplyStores() {
        for (Store s : stores) {
            s.resupply();
        }
        actualizarBarra();
    }

    /**
     * Coloca un robot en la rua.
     */
    public void placeRobot(int location) {
        Robot r = new Robot(location);
        robots.add(r);
    }

    /**
     * Elimina un robot en la ubicación indicada.
     */
    public void removeRobot(int location) {
        Robot target = null;
        for (Robot r : robots) {
            if (r.getLocation() == location) {
                target = r;
                break;
            }
        }
        if (target != null) {
            target.erase();
            robots.remove(target);
        }
    }

    /**
     * Mueve un robot en la pos dada.
     */
    public void moveRobot(int location, int meters) {
        for (Robot r : robots) {
            if (r.getLocation() == location) {
                r.move(meters);
                break;
            }
        }
    }

    /**
     * Regresa todos los robots a su pos inicial
     */
    public void returnRobots() {
        for (Robot r : robots) {
            r.returnToInitial();
        }
    }

    /**
     * Reinicia la ruta: borra tiendas y robots,
     * (los vuelve a colocar como fueron adicionados)
     */
    public void reboot() {
        // borrar la representacion
        for (Store s : stores) {
            s.erase();
        }
        for (Robot r : robots) {
            r.erase();
        }

        // limpia list
        ArrayList<Store> oldStores = new ArrayList<Store>(stores);
        ArrayList<Robot> oldRobots = new ArrayList<Robot>(robots);
        stores.clear();
        robots.clear();

        // vuelv a crearlos en las pos iniciales
        for (Store s : oldStores) {
            placeStore(s.getLocation(), s.getTenges());
        }
        for (Robot r : oldRobots) {
            placeRobot(r.getInitialLocation());
        }
        actualizarBarra();
    }

    /**
     * Devuelve la ganancia obtenida hasta ahora.
     * (solo es la suma de tenges en todas las tiendas)
     */
    public int profit() {
        int sum = 0;
        for (Store s : stores) {
            sum += s.getTenges();
        }
        return sum;
    }

    /**
     * Devuelve la infode todas las tiendas
     * Cada fila es {posición, dinero}.
     */
    public int[][] storesInfo() {
        int[][] info = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            info[i][0] = stores.get(i).getLocation();
            info[i][1] = stores.get(i).getTenges();
        }
        return info;
    }

    /**
     * Devuelve la info de todos los robots
     * Cada fila es {posición actual, posición inicial}.
     */
    public int[][] robotsInfo() {
        int[][] info = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            info[i][0] = robots.get(i).getLocation();
            info[i][1] = robots.get(i).getInitialLocation();
        }
        return info;
    }

    /**
     * Termina el simulador (limpi la pantalla y vacía las listas)
     */
    public void finish() {
        for (Store s : stores) {
            s.erase();
        }
        for (Robot r : robots) {
            r.erase();
        }
        stores.clear();
        robots.clear();
        canvas.setVisible(false);
    }
}
