
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brandon Duque Y Yeison Ballesteros
 */
public class ReconocedorVisual {

    private IGramatica iGramatica;
    private AutomataDePila automata;
    ArrayList<Integer> posiciones;
    ArrayList<String> seleccionEspecifico;

    ReconocedorVisual(IGramatica iGrama, AutomataDePila auto) {
        iGramatica = iGrama;
        automata = auto;
    }

    /**
     * Método para llenar la matriz de JTextField que representa el autómata de pila de forma visual y el textArea con las operaciones en la pila.
     */
    public void llenaReconocedor() {
        automata.getTransicion()[automata.getSimbolosEnPila().size() - 1][automata.getSimbolosDeEntrada().size() - 1].setText("  A");
        String area = "#0 --> Desapile(), Avance.\n";
        boolean repite = false;
        for (int i = 0; i < iGramatica.a.noTerminales.size(); i++) {
            String sub = iGramatica.a.noTerminales.get(i);
            posiciones = new ArrayList();
            for (int j = 0; j < iGramatica.getLadoIzquierdo().length; j++) {
                if (sub.equals(iGramatica.getLadoIzquierdo()[j].getText())) {
                    posiciones.add(j);
                }
            }
            for (int j = 0; j < posiciones.size(); j++) {
                seleccionEspecifico = iGramatica.a.seleccion.get(posiciones.get(j));//{a,b}
                for (int k = 0; k < seleccionEspecifico.size(); k++) {
                    String a = seleccionEspecifico.get(k).toString();//a
                    for (int m = 0; m < automata.getSimbolosDeEntrada().size(); m++) {
                        if (a.equals(automata.getSimbolosDeEntrada().get(m).toString())) {
                            automata.getTransicion()[i][m].setText("  #" + (posiciones.get(j) + 1));
                        }
                    }
                }
                String cadena = iGramatica.getLadoDerecho()[posiciones.get(j)].getText();//a
                if (cadena.charAt(0) == '<') {
                    String s = "";
                    for (int d = cadena.length() - 1; d >= 0; d--) {
                        if (cadena.charAt(d) == '<') {
                            s += ">";
                            continue;
                        } else if (cadena.charAt(d) == '>') {
                            s += "<";
                            continue;
                        }
                        s += cadena.charAt(d);
                    }
                    area += "#" + (posiciones.get(j) + 1) + " --> Replace(" + s + "), Retenga.\n";
                } else if (cadena.equalsIgnoreCase("lambda")) {
                    area += "#" + (posiciones.get(j) + 1) + " --> Desapile(), Retenga.\n";
                } else {
                    String s = "";
                    for (int d = cadena.length() - 1; d >= 1; d--) {
                        if (cadena.charAt(d) == '<') {
                            s += ">";
                            continue;
                        } else if (cadena.charAt(d) == '>') {
                            s += "<";
                            continue;
                        }
                        s += cadena.charAt(d);
                    }
                    if (s.equals("")) {
                        area += "#" + (posiciones.get(j) + 1) + " --> Desapile(), Avance.\n";
                    } else {
                        area += "#" + (posiciones.get(j) + 1) + " --> Replace(" + s + "), Avance.\n";
                    }
                }
            }
        }
        if (iGramatica.a.terminalesEnAyB.size() >= 1) {
            for (int i = 0; i < iGramatica.a.terminalesEnAyB.size(); i++) {
                String sub = iGramatica.a.terminalesEnAyB.get(i);
                for (int j = 0; j < automata.getSimbolosDeEntrada().size(); j++) {
                    if (sub.equals(automata.getSimbolosDeEntrada().get(j))) {
                        automata.getTransicion()[iGramatica.a.noTerminales.size() + i][j].setText("  #0");
                    }
                }
            }
        }
        area += "A: Acepte.";
        automata.getArea().setText(area);
    }
}
