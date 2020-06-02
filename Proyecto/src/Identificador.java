
import java.util.ArrayList;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brandon Duque Y Yeison Ballesteros
 */
public class Identificador {
    
    /**
     * Método para verificar si la gramática ingresada no es ni S,Q o LL(1).
     * @param conjuntos es ub objeto de la clase Conjuntos.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public boolean noEsNinguna(Conjuntos conjuntos, JTextField[] izquierdo, JTextField[] derecho){
        if(!esS(conjuntos,izquierdo,derecho) && !esQ(conjuntos,izquierdo,derecho) && !esLL1(conjuntos,izquierdo,derecho)){
            return true;
        }
        return false;
    }

    /**
     * Método para verificar si la gramática ingresada es de tipo S.
     * @param conjuntos es un objeto de la clase Conjuntos.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public boolean esS(Conjuntos conjuntos, JTextField[] izquierdo, JTextField[] derecho) {
        if (disyuntos(conjuntos, izquierdo)) {
            for (int i = 0; i < derecho.length; i++) {
                if (derecho[i].getText().substring(0, 1).equals("<") || derecho[i].getText().equalsIgnoreCase("lambda")) {
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }  
    }
    
    /**
     * Método para verificar si la gramática ingresada es de tipo Q.
     * @param conjuntos es un objeto de la clase Conjuntos.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public boolean esQ(Conjuntos conjuntos, JTextField[] izquierdo, JTextField[] derecho){
        if(!esS(conjuntos,izquierdo,derecho)){
            if(disyuntos(conjuntos, izquierdo)){
                for (int i = 0; i < derecho.length; i++) {
                    if(derecho[i].getText().substring(0, 1).equals("<")){
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Método para verificar si la gramática ingresada es de tipo LL(1).
     * @param conjuntos es un objeto de la clase Conjuntos.
     * @param derecho es un vector de objetos JTextField, contiene todo el lado derecho de la gramática.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    public boolean esLL1(Conjuntos conjuntos, JTextField[] izquierdo, JTextField[] derecho){
        if(!esS(conjuntos,izquierdo, derecho) && !esQ(conjuntos, izquierdo, derecho)){
            if(disyuntos(conjuntos, izquierdo)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método para verificar si los conjuntos de selección de una gramática son disyuntos.
     * @param conjuntos es un objetos de la clase Conjuntos.
     * @param izquierdo es un vector de objetos JTextField, contiene todo el lado izquierdo de la gramática.
     */
    private boolean disyuntos(Conjuntos conjuntos, JTextField[] izquierdo) {
        ArrayList<Integer> posiciones = new ArrayList();
        for (int i = 0; i < conjuntos.noTerminales.size(); i++) {
            String sub = conjuntos.noTerminales.get(i);//<S>
            for (int j = 0; j < conjuntos.primerosP.size(); j++) {
                if (izquierdo[j].getText().equals(sub)) {
                    posiciones.add(j);
                }
            }
            if (posiciones.size() == 1) {
                continue;
            } else {
                for (int e = 0; e < posiciones.size(); e++) {
                    ArrayList<String> cadena = conjuntos.seleccion.get(posiciones.get(e));//{conjuntos,¬}
                    for (int k = e + 1; k < posiciones.size(); k++) {
                        for (int l = 0; l < cadena.size(); l++) {
                            if (conjuntos.seleccion.get(posiciones.get(k)).contains(cadena.get(l))) {
                                return false;
                            }
                        }
                    }
                }
            }
            posiciones.clear();
        }
        return true;
    }
    /**
     * Este método es para colocar de forma textual el tipo de gramática.
     * @param conjuntos
     * @param izquierdo
     * @param derecho
     * @return 
     */
    public String tipoGramatica(Conjuntos conjuntos, JTextField[] izquierdo, JTextField[] derecho){
        if(esS(conjuntos, izquierdo, derecho)){
            return "S";
        }else if(esQ(conjuntos, izquierdo, derecho)){
            return "Q";
        }else {
            return "LL(1)";
        }
    }
}
