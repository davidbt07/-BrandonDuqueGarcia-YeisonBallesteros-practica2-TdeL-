
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import javax.swing.JTextField;

/**
 * En esta clase se recopilan todos los conjuntos de la gramática
 *
 * @author Brandon Duque Y Yeison Ballesteros
 * @version 08/05/2020/A
 */
public class Conjuntos {

    protected ArrayList<String> anulablesN, anulablesP, noTerminales, terminales, terminalesEnAyB;
    protected ArrayList<ArrayList<String>> primerosN, primerosP, siguientes, seleccion;

    public Conjuntos() {
        anulablesN = new ArrayList();
        anulablesP = new ArrayList();
        primerosN = new ArrayList();
        primerosP = new ArrayList();
        siguientes = new ArrayList();
        seleccion = new ArrayList();
        terminalesEnAyB = new ArrayList();
    }

    /**
     *
     * Método para encontrar los terminales en hileras Alpha y Beta del lado derecho de la gramática.
     * @param derecho Es un vector de objetos de la clase JTextField, donde están registrados todo el lado derecho de la gramática.
     */
    public void terminalesAyB(JTextField[] derecho) {
        
        for (int i = 0; i < derecho.length; i++) {
            String cadena = derecho[i].getText();
            if (cadena.equalsIgnoreCase("lambda")) {
                continue;
            }else if(cadena.charAt(0) != '<'){
                cadena = cadena.substring(1);
                sacaTerminales(cadena);
            }else{
                sacaTerminales(cadena);
            }           
        }

    }

    /**
     *
     * Método que auxilia al método terminalesAyB, este método encuentra los terminales en Alpha y Beta de una hilera.
     * @param cadena es una hilera de uno de los lados derechos.
     */
    private void sacaTerminales(String cadena) {
        Stack pila = new Stack();
        int controlador = cadena.length();
        for (int k = 0; k < controlador; k++) {
            String sub = cadena.substring(0, 1);
            if (sub.equals(">")) {
                int cont2 = pila.size();
                for (int e = 0; e < cont2; e++) {
                    pila.pop();
                }
                cadena = cadena.substring(1);
            } else if (sub.equals("<") || !pila.empty()) {
                pila.push(sub);
                cadena = cadena.substring(1);
            } else {
                if (!terminalesEnAyB.contains(sub)) {
                    terminalesEnAyB.add(sub);
                }
                cadena = cadena.substring(1);
            }
        }
    }

    /**
     * 
     * Método para encontrar los no terminales anulables de una gramática.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public void NAnulables(JTextField[] izquierdo, JTextField[] derecho) {
        int tAnterior = 0, tActual = 0;
        for (int i = 0; i < izquierdo.length; i++) {
            String cadena = derecho[i].getText();
            if (cadena.equalsIgnoreCase("lambda")) {
                anulablesN.add(izquierdo[i].getText());
                tActual++;
            }
        }
        do {
            tAnterior = tActual;
            for (int j = 0; j < izquierdo.length; j++) {

                if (anulablesN.contains(izquierdo[j].getText())) {
                    continue;
                } else {

                    String cadena = derecho[j].getText();
                    if (cadena.charAt(0) != '<') {
                        continue;
                    } else {
                        String sub = cadena;
                        boolean rompe = false;
                        do {
                            sub = sub.substring(sub.indexOf('>') + 1);
                            if (sub.equals("")) {
                                break;
                            }
                            if (sub.charAt(0) != '<') {
                                rompe = true;
                                break;
                            }
                        } while (!sub.equals(""));
                        if (rompe) {
                            continue;
                        }
                        sub = cadena;
                        int pos = 0;
                        do {
                            sub = cadena.substring(0, cadena.indexOf('>') + 1);
                            cadena = cadena.substring(sub.length());
                            if (sub.equals("")) {
                                anulablesN.add(izquierdo[j].getText());
                                tActual += 1;
                                break;
                            }
                            if (!anulablesN.contains(sub)) {
                                break;
                            }
                        } while (!sub.equals(""));

                    }

                }
            }
            System.out.println("tAnterior" + tAnterior + " tActual: " + tActual);
        } while (tAnterior != tActual);

    }

    /**
     * 
     * Métoo para encontrar las producciones anulables de la gramática.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public void ProduccionesAnulables(JTextField[] izquierdo, JTextField[] derecho) {
        int tAnterior = 0, tActual = 0;
        for (int i = 0; i < izquierdo.length; i++) {
            String cadena = derecho[i].getText();
            if (cadena.equalsIgnoreCase("lambda")) {
                String c = String.valueOf((i) + 1);
                anulablesP.add(c);
                tActual++;
            }
        }
        do {
            tAnterior = tActual;
            for (int j = 0; j < izquierdo.length; j++) {
                if (anulablesP.contains(String.valueOf((j) + 1))) {
                    continue;
                } else {
                    String cadena = derecho[j].getText();
                    if (cadena.charAt(0) != '<') {
                        continue;
                    } else {
                        String sub = cadena;
                        boolean rompe = false;
                        do {
                            sub = sub.substring(sub.indexOf('>') + 1);
                            if (sub.equals("")) {
                                break;
                            }
                            if (sub.charAt(0) != '<') {
                                rompe = true;
                                break;
                            }
                        } while (!sub.equals(""));
                        if (rompe) {
                            continue;
                        }
                        sub = cadena;
                        int pos = 0;
                        do {
                            sub = cadena.substring(0, cadena.indexOf('>') + 1);
                            cadena = cadena.substring(sub.length());
                            if (sub.equals("")) {
                                String c = String.valueOf((j) + 1);
                                anulablesP.add(c);
                                tActual += 1;
                                break;
                            }
                            if (!anulablesN.contains(sub)) {
                                break;
                            }

                        } while (!sub.equals(""));

                    }
                }
            }
        } while (tAnterior != tActual);
    }

    /**
     * 
     * Método para imprimir un ArrayList de ArrayList de String, utilizado para imprimir algunos conjuntos que tienen la misma estructura.
     * @param conjunto es un objeto de la clase Conjuntos.
     */
    public void imprimir(ArrayList<ArrayList<String>> conjunto) {
        for (int j = 0; j < conjunto.size(); j++) {
            for (int i = 0; i < conjunto.get(j).size(); i++) {
                System.out.println(conjunto.get(j).get(i) + " ,");
            }
            System.out.println("\n");
        }
    }

    /**
     * 
     * Método para encontrar los primeros de los No Terminales de la gramática.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public void primerosDeLosN(JTextField[] izquierdo, JTextField[] derecho) {
        boolean hayNoTerminal;
        identificarNoTerminales(izquierdo);
        identificarTerminales(derecho);

        ArrayList<String> especifico;
        ArrayList<ArrayList<String>> general = new ArrayList();

        for (int i = 0; i < noTerminales.size(); i++) {
            especifico = new ArrayList();
            general.add(especifico);
        }
        insertaNoTerminal(general, izquierdo, derecho, noTerminales);
        general = limpiarArreglo(general, noTerminales);
        do {
            hayNoTerminal = false;
            for (int i = 0; i < general.size(); i++) {
                for (int j = 0; j < general.get(i).size(); j++) {
                    String sub = general.get(i).get(j).toString();
                    if (sub.charAt(0) == '<') {
                        int pos = noTerminales.indexOf(sub);
                        general.get(i).remove(j);
                        String cadena = obtenerCadena(general.get(pos));
                        String[] auxiliar = cadena.split(",");
                        for (int k = 0; k < auxiliar.length; k++) {
                            if (!general.get(i).contains(auxiliar[k])) {
                                general.get(i).add(auxiliar[k]);
                            }
                        }
                    }
                }
                general = limpiarArreglo(general, noTerminales);
                for (int j = 0; j < general.get(i).size(); j++) {
                    String cadenita = general.get(i).get(j).toString();
                    if (cadenita.charAt(0) == '<') {
                        hayNoTerminal = true;
                        break;
                    }
                }
            }
        } while (hayNoTerminal);
        primerosN = general;
        for (int i = 0; i < primerosN.size(); i++) {
            Collections.sort(primerosN.get(i));
        }
    }

    /**
     *
     * Método para llenar y separar por comas una cadena con objetos que están en un ArrayList.
     * @param conjunto El parametro conjunto simboliza un conjunto
     * @return Lo que contiene el conjunto conjunto separado por comas
     */
    private String obtenerCadena(ArrayList<String> conjunto) {
        String cadena = "";
        for (int i = 0; i < conjunto.size(); i++) {
            cadena += conjunto.get(i) + ",";
        }
        cadena = cadena.substring(0, cadena.length() - 1);
        return cadena;
    }

    /**
     * 
     * Método para Auxiliar al método primerosDeLosN, 
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática. 
     * @param general es un ArrayList de ArrayList de String, estructura de algunos conjuntos. 
     * @param noTerminales es ArrayList de String qeu guarda los no terminales de la gramática.
     */
    private void insertaNoTerminal(ArrayList<ArrayList<String>> general, JTextField[] izquierdo, JTextField[] derecho, ArrayList noTerminales) {
        for (int i = 0; i < noTerminales.size(); i++) {
            for (int j = 0; j < izquierdo.length; j++) {
                String izq = izquierdo[j].getText();
                if (izq.equals(noTerminales.get(i))) {
                    String der = derecho[j].getText();
                    String sub = der.substring(0, 1);
                    if (!der.equalsIgnoreCase("lambda")) {
                        if (!sub.equals("<")) {
                            general.get(i).add(sub);
                        } else {
                            sub = der.substring(0, der.indexOf(">") + 1);
                            der = der.substring(der.indexOf(">") + 1);
                            general.get(i).add(sub);
                            while (anulablesN.contains(sub) && !der.equals("")) {
                                sub = der.substring(0, 1);
                                if (!sub.equals("<")) {
                                    general.get(i).add(sub);
                                    break;
                                }
                                sub = der.substring(0, der.indexOf(">") + 1);
                                der = der.substring(der.indexOf(">") + 1);
                                general.get(i).add(sub);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * Método para identificar los No Terminales de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    private void identificarNoTerminales(JTextField[] izquierdo) {
        noTerminales = new ArrayList();
        for (int j = 0; j < izquierdo.length; j++) {
            if (!noTerminales.contains(izquierdo[j].getText())) {
                noTerminales.add(izquierdo[j].getText());
            }
        }
    }

    /**
     * 
     * Método para identificar los Terminales de la gramática.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     */
    private void identificarTerminales(JTextField[] derecho) {
        terminales = new ArrayList<>();
        Stack pila = new Stack();
        for (int i = 0; i < derecho.length; i++) {
            if (derecho[i].getText().equalsIgnoreCase("lambda")) {
                continue;
            }
            String cadena = derecho[i].getText();
            int controlador = cadena.length();
            for (int k = 0; k < controlador; k++) {
                String sub = cadena.substring(0, 1);
                if (sub.equals(">")) {
                    int cont2 = pila.size();
                    for (int e = 0; e < cont2; e++) {
                        pila.pop();
                    }
                    cadena = cadena.substring(1);
                } else if (sub.equals("<") || !pila.empty()) {
                    pila.push(sub);
                    cadena = cadena.substring(1);
                } else {
                    if (!terminales.contains(sub)) {
                        terminales.add(sub);
                    }
                    cadena = cadena.substring(1);
                }
            }
        }
    }

    /**
     * Método para quitar de un conjunto X el No terminal X
     * @param general Es el arrayList sucio
     * @param noTerminales
     * @return EL mismo arrayList general pero limpio
     */
    private ArrayList limpiarArreglo(ArrayList<ArrayList<String>> general, ArrayList<String> noTerminales) {
        for (int i = 0; i < noTerminales.size(); i++) {
            for (int j = 0; j < general.get(i).size(); j++) {
                if (general.get(i).get(j).equalsIgnoreCase(noTerminales.get(i))) {
                    general.get(i).remove(j);
                }
            }
        }
        return general;
    }

    /**
     * Método para sacar los primeros de cada producción
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática. 
     */
    public void primerosDeLasProducciones(JTextField[] izquierdo, JTextField[] derecho) {
        for (int i = 0; i < izquierdo.length; i++) {
            ArrayList<String> especifico = new ArrayList<>();
            primerosP.add(especifico);
        }
        for (int i = 0; i < izquierdo.length; i++) {
            String indice = String.valueOf(i + 1);
            //Preguntamos si la produccion es anulable o no.
            if (anulablesP.contains(indice)) {
                //Preguntamos si es lambda o no.
                if (derecho[i].getText().equalsIgnoreCase("lambda")) {
                    primerosP.get(i).add("");
                    continue;
                } else {
                    //Al no ser lambda se procede a agregar todos los primeros de los No Terminales de la produccion.
                    String cadena = derecho[i].getText();
                    String sub = cadena.substring(0, cadena.indexOf('>') + 1);
                    while (!sub.equals("")) {
                        int pos = noTerminales.indexOf(sub);
                        String cadenaAux = obtenerCadena(primerosN.get(pos));
                        String[] auxiliar = cadenaAux.split(",");
                        for (int k = 0; k < auxiliar.length; k++) {
                            if (!primerosP.get(i).contains(auxiliar[k])) {
                                primerosP.get(i).add(auxiliar[k]);
                            }
                        }
                        cadena = cadena.substring(sub.length());
                        sub = cadena.substring(0, cadena.indexOf('>') + 1);
                    }
                }
            } else {
                //Al no ser la produccion anulable entonces se pregunta si es un terminal o no
                String cadena = derecho[i].getText();

                if (cadena.charAt(0) == '<') {
                    boolean seguir = true;
                    String sub = cadena.substring(0, cadena.indexOf(">") + 1);
                    while (seguir && !sub.equals("")) {
                        int pos = noTerminales.indexOf(sub);
                        String cadenaAux = obtenerCadena(primerosN.get(pos));
                        String[] auxiliar = cadenaAux.split(",");
                        for (int k = 0; k < auxiliar.length; k++) {
                            if (!primerosP.get(i).contains(auxiliar[k])) {
                                primerosP.get(i).add(auxiliar[k]);
                            }
                        }
                        cadena = cadena.substring(sub.length());
                        if (cadena.equals("")) {
                            break;
                        }
                        if (anulablesN.contains(sub)) {
                            if (!cadena.substring(0, 1).equals("<")) {
                                if (!primerosP.get(i).contains(cadena.substring(0, 1))) {
                                    primerosP.get(i).add(cadena.substring(0, 1));
                                }
                                break;
                            }
                        } else {
                            break;
                        }
                        sub = cadena.substring(0, cadena.indexOf(">") + 1);
                    }
                } else {
                    //Si es terminal se agrega y pasamos a la siguiente produccion
                    if (!primerosP.get(i).contains(cadena.substring(0, 1))) {
                        primerosP.get(i).add(cadena.substring(0, 1));
                    }
                }
            }
        }
    }

    /**
     * Metodo para sacar los siguientes de cada No terminal
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática. 
     */
    public void siguientes(JTextField[] izquierdo, JTextField[] derecho) {
        boolean hayNoTerminal;

        ArrayList<String> especifico;
        siguientes = new ArrayList();

        for (int i = 0; i < noTerminales.size(); i++) {
            especifico = new ArrayList();
            if (i == 0) {
                especifico.add("¬");
            }
            siguientes.add(especifico);
        }

        insertarSiguientes(izquierdo, derecho);
        siguientes = limpiarArreglo(siguientes, noTerminales);

        do {
            hayNoTerminal = false;
            for (int i = 0; i < siguientes.size(); i++) {
                for (int j = 0; j < siguientes.get(i).size(); j++) {
                    String sub = siguientes.get(i).get(j).toString();
                    if (sub.charAt(0) == '<') {
                        int pos = noTerminales.indexOf(sub);
                        siguientes.get(i).remove(j);
                        String cadena = obtenerCadena(siguientes.get(pos));
                        String[] auxiliar = cadena.split(",");
                        for (int k = 0; k < auxiliar.length; k++) {
                            if (!siguientes.get(i).contains(auxiliar[k])) {
                                siguientes.get(i).add(auxiliar[k]);
                            }
                        }
                    }
                }
                siguientes = limpiarArreglo(siguientes, noTerminales);
                for (int j = 0; j < siguientes.get(i).size(); j++) {
                    String cadenita = siguientes.get(i).get(j).toString();
                    if (cadenita.charAt(0) == '<') {
                        hayNoTerminal = true;
                        break;
                    }
                }
            }
        } while (hayNoTerminal);
        for (int i = 0; i < siguientes.size(); i++) {
            Collections.sort(siguientes.get(i));
        }
    }

    /**
     * Método que auxilia al método siguientes.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    private void insertarSiguientes(JTextField[] izquierdo, JTextField[] derecho) {
        //For para recorrer los no terminales
        for (int i = 0; i < noTerminales.size(); i++) {
            String sub = noTerminales.get(i);//<S>
            //For para recorrer las producciones
            for (int j = 0; j < derecho.length; j++) {
                String der = derecho[j].getText();//<A><S>

                int posicion = der.indexOf(sub);
                if (posicion == -1) {
                    continue;
                }
                der = der.substring(posicion + sub.length());
                while (posicion != -1) {
                    //Preguntamos si el N buscado es el ultimo y agregamos los siguientes del lado izquierdo
                    if (der.equals("")) {
                        siguientes.get(i).add(izquierdo[j].getText());
                        break;
                    }
                    //Preguntamos si el primer siguiente de  el N es un no terminal o un terminal
                    if (der.substring(0, 1).equals("<")) {
                        String sub2 = der.substring(0, der.indexOf(">") + 1);
                        //Ciclo para seguir añadiendo los primeros de los no terminales anulables
                        while (anulablesN.contains(sub2)) {
                            //Añadimos los primeros del no terminal en los siguientes 
                            int pos = noTerminales.indexOf(sub2);
                            String cadena = obtenerCadena(primerosN.get(pos));
                            String[] auxiliar = cadena.split(",");
                            for (int k = 0; k < auxiliar.length; k++) {
                                if (!siguientes.get(i).contains(auxiliar[k])) {
                                    siguientes.get(i).add(auxiliar[k]);
                                }
                            }
                            //Ahora actualizamos para recorrer el siguiente no terminal
                            der = der.substring(der.indexOf(">") + 1);
                            if (der.equals("")) {
                                siguientes.get(i).add(izquierdo[j].getText());
                                break;
                            }
                            sub2 = der.substring(0, der.indexOf(">") + 1);

                            //Preguntamos si lo que sigue es un terminal
                            if (!der.substring(0, 1).equals("<")) {
                                siguientes.get(i).add(der.substring(0, 1));
                                break;
                            }
                        }

                        if (!anulablesN.contains(sub2) && !sub2.equals("")) {
                            int pos = noTerminales.indexOf(sub2);
                            String cadena = obtenerCadena(primerosN.get(pos));
                            String[] auxiliar = cadena.split(",");
                            for (int k = 0; k < auxiliar.length; k++) {
                                if (!siguientes.get(i).contains(auxiliar[k])) {
                                    siguientes.get(i).add(auxiliar[k]);
                                }
                            }
                        }

                    } else {
                        siguientes.get(i).add(der.substring(0, 1));
                    }
                    posicion = der.indexOf(sub);
                }

            }
        }
    }

    /**
     * Método para identificar el conjunto selección de la gramática.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public void seleccion(JTextField[] izquierdo, JTextField[] derecho) {
        ArrayList<String> especifico;
        seleccion = new ArrayList();
        String[] auxiliar;
        String sub;
        for (int i = 0; i < izquierdo.length; i++) {
            especifico = new ArrayList();
            seleccion.add(especifico);
        }

        for (int i = 0; i < izquierdo.length; i++) {
            if (derecho[i].getText().equalsIgnoreCase("lambda")) {
                //Añadiendo los siguientes del N del lado izquierdo
                int pos = noTerminales.indexOf(izquierdo[i].getText());
                sub = obtenerCadena(siguientes.get(pos));
                auxiliar = sub.split(",");
                for (int k = 0; k < auxiliar.length; k++) {
                    if (!seleccion.get(i).contains(auxiliar[k])) {
                        seleccion.get(i).add(auxiliar[k]);
                    }
                }
            } else {
                //Añadiendo los primeros de la produccion
                sub = obtenerCadena(primerosP.get(i));
                auxiliar = sub.split(",");
                for (int k = 0; k < auxiliar.length; k++) {
                    if (!seleccion.get(i).contains(auxiliar[k])) {
                        seleccion.get(i).add(auxiliar[k]);
                    }
                }
                String indice = String.valueOf(i + 1);
                if (anulablesP.contains(indice)) {
                    int pos = noTerminales.indexOf(izquierdo[i].getText());
                    sub = obtenerCadena(siguientes.get(pos));
                    auxiliar = sub.split(",");
                    for (int k = 0; k < auxiliar.length; k++) {
                        if (!seleccion.get(i).contains(auxiliar[k])) {
                            seleccion.get(i).add(auxiliar[k]);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < seleccion.size(); i++) {
            Collections.sort(seleccion.get(i));
        }
      
    }
}
