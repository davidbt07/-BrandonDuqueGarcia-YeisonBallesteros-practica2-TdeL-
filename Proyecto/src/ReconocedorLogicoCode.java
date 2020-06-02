
import java.util.ArrayList;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brandon Duque Y Yeison Ballesteros
 */
public class ReconocedorLogicoCode {

    ReconocedorLogico vista;
    AutomataDePila automata;

    public ReconocedorLogicoCode(ReconocedorLogico vista, AutomataDePila automata) {
        this.vista = vista;
        this.automata = automata;
    }

    /**
     * Método para verificar si la hilera ingresada es generada por la gramática.
     */
    public boolean Reconocer() {
        String hileraDeEntrada = vista.getTxtHilera().getText();
        hileraDeEntrada += "¬";
        ArrayList<String> simbolosDeEntrada = automata.getSimbolosDeEntrada();
        ArrayList<String> simbolosDeLaPila = automata.getSimbolosEnPila();
        String simbolo = "";
        Stack pila = new Stack();
        pila.push("▲");
        pila.push(simbolosDeLaPila.get(0));
        String linea = "";
        String numeral = "";
        String parentesis = "";
        String orden = "";
        simbolo = hileraDeEntrada.substring(0, 1);
        hileraDeEntrada = hileraDeEntrada.substring(1);
        String area = automata.getArea().getText();
        boolean aceptar = false;
        do {

            for (int i = 0; i < simbolosDeLaPila.size(); i++) {
                if (simbolosDeLaPila.get(i).equals(pila.peek())) {
                    for (int j = 0; j < simbolosDeEntrada.size(); j++) {
                        if (simbolo.equals(simbolosDeEntrada.get(j))) {
                            numeral = automata.getTransicion()[i][j].getText();
                            if(numeral.length() == 3){
                                return true;
                            }else if(numeral.equals("")){
                                return false;
                            }
                            numeral = numeral.substring(numeral.indexOf("#"));
                            linea = area.substring(area.indexOf(numeral));
                            linea = linea.substring(0, linea.indexOf(".")+1);
                            parentesis = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")"));
                            orden = linea.substring(linea.indexOf(",") + 2, linea.indexOf("."));
                            aceptar = true;
                            break;
                        }
                    }
                    if(!aceptar){
                        return false;
                    }
                    break;
                }
            }
            if (parentesis.equals("")) {
                pila.pop();
            } else {
                pila.pop();
                while (!parentesis.equals("")) {
                    if (parentesis.substring(0, 1).equals("<")) {
                        pila.push(parentesis.substring(0, parentesis.indexOf(">") + 1));
                        parentesis = parentesis.substring(parentesis.indexOf(">") + 1);
                    } else {
                        pila.push(parentesis.substring(0, 1));
                        parentesis = parentesis.substring(1);
                    }
                }
            }

            if (orden.equals("Avance")) {
                simbolo = hileraDeEntrada.substring(0, 1);
                hileraDeEntrada = hileraDeEntrada.substring(1);
            }
            aceptar = false;
        } while (!simbolo.equals(""));
        return false;
    }

}
