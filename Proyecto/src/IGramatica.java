/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brandon Duque Y Yeison Ballesteros
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;

public class IGramatica extends JFrame implements ActionListener {

    //private JTextField textfield1, textfield2;
    private JButton botonRegistrar, btnOtraG, btnSalir;
    private JLabel label1, label2, label3;
    private int producciones;
    private JTextField ladoIzquierdo[], ladoDerecho[];
    private JScrollPane scroll;
    Conjuntos a;
    private Identificador b;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonRegistrar && validar()) {

            a = new Conjuntos();
            a.NAnulables(ladoIzquierdo, ladoDerecho);
            a.ProduccionesAnulables(ladoIzquierdo, ladoDerecho);
            a.primerosDeLosN(ladoIzquierdo, ladoDerecho);
            a.primerosDeLasProducciones(ladoIzquierdo, ladoDerecho);
            a.siguientes(ladoIzquierdo, ladoDerecho);
            a.seleccion(ladoIzquierdo, ladoDerecho);
            a.terminalesAyB(ladoDerecho);
            b = new Identificador();
            if (b.noEsNinguna(a, ladoIzquierdo, ladoDerecho)) {
                JOptionPane.showMessageDialog(null, "La gramática dada no es disyunta, por lo tanto no es ni S, Q y ni LL(1).");
            } else {
                setVisible(false);
                AutomataDePila automata = new AutomataDePila(a, this);
            }
        } else if (e.getSource() == btnOtraG) {
            dispose();
            IGramatica formulario = new IGramatica();
            formulario.validacion();
            formulario.inicio();
            formulario.setVisible(true);
            formulario.setResizable(false);
            formulario.setLocationRelativeTo(null);
            formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }

    public static void main(String[] args) {

        IGramatica formulario = new IGramatica();
        formulario.validacion();
        formulario.inicio();
        formulario.setVisible(true);
        formulario.setResizable(false);
        formulario.setLocationRelativeTo(null);
        formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método para validar la gramática antes de poder operar en ella.
     */
    public boolean validar() {
        for (int i = 0; i < ladoIzquierdo.length; i++) {
            if (ladoIzquierdo[i].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "La producción " + (i + 1) + " está vacía en su lado izquierdo.");
                return false;
            }
        }
        for (int i = 0; i < ladoDerecho.length; i++) {
            if (ladoDerecho[i].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "La producción " + (i + 1) + " está vacía en su lado derecho.");
                return false;
            }
        }
        boolean izq = false;
        boolean der = true;
        Stack pila = new Stack();
        String cadena;
        for (int i = 0; i < ladoIzquierdo.length; i++) {
            cadena = ladoIzquierdo[i].getText();
            char primero = cadena.charAt(0);
            char ultimo = cadena.charAt(cadena.length() - 1);
            if (primero == '<' && ultimo == '>' && cadena.charAt(cadena.length() - 2) != '<') {
                izq = true;
            } else {
                int indice = i + 1;
                JOptionPane.showMessageDialog(null, "Lado izquierdo en la producción " + indice + " incorrecta");
                izq = false;
                break;
            }
        }

        for (int j = 0; j < ladoDerecho.length; j++) {
            int indice;
            cadena = ladoDerecho[j].getText();
            for (int k = 0; k < cadena.length(); k++) {
                if (cadena.charAt(k) == '<') {
                    if (pila.isEmpty()) {
                        pila.push(cadena.charAt(k));
                    } else {
                        indice = j + 1;
                        JOptionPane.showMessageDialog(null, "Lado Derecho en la producción " + indice + " incorrecto");
                        der = false;
                        break;
                    }
                } else if (cadena.charAt(k) == '>') {
                    if (pila.isEmpty()) {
                        indice = j + 1;
                        JOptionPane.showMessageDialog(null, "Lado Derecho en la producción " + indice + " incorrecto");
                        der = false;
                        break;
                    }
                    if (cadena.charAt(k - 1) == '<') {
                        indice = j + 1;
                        JOptionPane.showMessageDialog(null, "Lado Derecho en la producción " + indice + " incorrecto");
                        der = false;
                        break;
                    } else {
                        pila.pop();
                        der = true;
                    }
                }
                if (k == cadena.length() - 1 && !pila.isEmpty()) {
                    System.out.println("holaaaa");
                    indice = j + 1;
                    JOptionPane.showMessageDialog(null, "Lado Derecho en la producción " + indice + " incorrecto");
                    der = false;
                    break;
                }
            }
            if (der == false) {
                break;
            }
        }

        System.out.println(der && izq && pila.isEmpty());
        return (der && izq && pila.isEmpty());
    }

    /**
     * Método para validar la entrada en el JOptionPane de la cantidad de
     * producciones.
     *
     * @return cantidad de producciones.
     */
    public void validacion() {
        producciones = 0;
        boolean bandera = false;
        do {
            String prod = JOptionPane.showInputDialog("Ingrese Numero producciones: ");
            if (prod == null) {
                System.exit(0);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            try {
                producciones = Integer.parseInt(prod);
                bandera = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Solo se permiten numeros");
                bandera = false;
            }
        } while (!bandera);
    }

    public void inicio() {
        this.producciones = producciones;
        setLayout(null);

        ladoIzquierdo = new JTextField[producciones];
        ladoDerecho = new JTextField[producciones];
        int y = 40;

        label1 = new JLabel("Gramatica: ");
        label1.setBounds(250, 1, 150, 30);
        add(label1);

        for (int i = 0; i < ladoIzquierdo.length; i++) {
            label3 = new JLabel(i + 1 + ". ");
            label3.setBounds(78, y, 30, 30);//x, y, largo y ancho
            add(label3);

            ladoIzquierdo[i] = new JTextField();
            ladoIzquierdo[i].setBounds(110, y, 60, 30);
            add(ladoIzquierdo[i]);

            label2 = new JLabel("->");
            label2.setBounds(175, y, 30, 30);
            add(label2);

            ladoDerecho[i] = new JTextField();
            ladoDerecho[i].setBounds(206, y, 250, 30);
            add(ladoDerecho[i]);

            y += 30;
        }

        botonRegistrar = new JButton("Registrar");
        botonRegistrar.setBounds(206, y + 15, 100, 30);
        add(botonRegistrar);
        botonRegistrar.addActionListener(this);

        btnOtraG = new JButton("Otra Gramática");
        btnOtraG.setBounds(310, y + 15, 146, 30);
        add(btnOtraG);
        btnOtraG.addActionListener(this);
        setBounds(0, 0, 528, y + 110);
    }

    public JTextField[] getLadoIzquierdo() {
        return ladoIzquierdo;
    }

    public void setLadoIzquierdo(JTextField[] ladoIzquierdo) {
        this.ladoIzquierdo = ladoIzquierdo;
    }

    public JTextField[] getLadoDerecho() {
        return ladoDerecho;
    }

    public Identificador getB() {
        return b;
    }

}
