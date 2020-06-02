
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Brandon Duque y Yeison Ballesteros
 */
public class AutomataDePila extends JFrame implements ActionListener {

    private ReconocedorVisual identificadorV;
    private ArrayList<String> simbolosDeEntrada, simbolosEnPila;
    private ArrayList<ArrayList<String>> seleccion;
    private JButton btnOtraG, btnReconocedor, btnAtras;
    private JTextField transicion[][];
    private JLabel lblSEntrada, lblSPila, lblTipoGramatica;
    private JTextArea area;
    private JScrollPane scroll;
    private IGramatica iGrama;
    private ReconocedorLogico reconocedorLogico;

    /**
     * 
     * @Constructor parametrizado,.
     * @param conjuntos es un objeto de la clase Conjuntos donde están registrados todos los conjunto de la gramática.
     * @param iGramatica es un objeto de la clase IGramatica, la cual es un JFrame form donde se registra la gramática.
     */
    AutomataDePila(Conjuntos conjuntos, IGramatica iGramatica) {

        setLayout(null);
        lblTipoGramatica = new JLabel();
        simbolosEnPila = new ArrayList();
        simbolosDeEntrada = new ArrayList();
        iGrama = iGramatica;
        seleccion = conjuntos.seleccion;
        for (int i = 0; i < conjuntos.terminales.size(); i++) {
            simbolosDeEntrada.add(conjuntos.terminales.get(i));
        }
        simbolosDeEntrada.add("¬");

        for (int i = 0; i < conjuntos.noTerminales.size(); i++) {
            simbolosEnPila.add(conjuntos.noTerminales.get(i));
        }
        for (int i = 0; i < conjuntos.terminalesEnAyB.size(); i++) {
            simbolosEnPila.add(conjuntos.terminalesEnAyB.get(i));
        }
        simbolosEnPila.add("▲");
        transicion = new JTextField[simbolosEnPila.size()][simbolosDeEntrada.size()];
        int x = 100;
        int y = 80;
        int xmax = 0;
        for (int i = 0; i < simbolosEnPila.size(); i++) {
            lblSPila = new JLabel(simbolosEnPila.get(i));
            lblSPila.setBounds(70, y, 30, 30);
            add(lblSPila);
            for (int j = 0; j < simbolosDeEntrada.size(); j++) {
                if (i == 0) {
                    lblSEntrada = new JLabel(simbolosDeEntrada.get(j));
                    lblSEntrada.setBounds(x + 17, 50, 30, 30);
                    add(lblSEntrada);
                }
                transicion[i][j] = new JTextField();
                transicion[i][j].setBounds(x, y, 35, 35);//x,y,largo,ancho
                transicion[i][j].setEditable(false);
                add(transicion[i][j]);

                x += 35;
            }
            if (i == 0) {
                xmax = x;
            }
            x = 100;
            y += 35;
        }

        scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        area = new JTextArea();
        area.setBounds(70, y + 25, 280, 120);
        area.setBorder(new LineBorder(Color.BLACK));
        area.setEditable(false);
        scroll.setBounds(70, y + 25, 280, 120);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.getViewport().add(area);
        add(scroll);
        
        btnAtras = new JButton();
        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back.png")));
        btnAtras.setBounds(5, 5, 64, 64);
        btnAtras.setBackground(new Color(214, 214, 214));
        btnAtras.addActionListener(this);
        add(btnAtras);
        
        btnOtraG = new JButton("Otra Gramática");
        btnOtraG.setBounds(70, y + 150, 146, 30);
        btnOtraG.addActionListener(this);
        add(btnOtraG);
        
        
        btnReconocedor = new JButton("Reconocedor");
        btnReconocedor.setBounds(220, y + 150, 130, 30);
        btnReconocedor.addActionListener(this);
        add(btnReconocedor);
        
        lblTipoGramatica.setText("La gramática es " + iGramatica.getB().tipoGramatica(iGramatica.a, iGramatica.getLadoIzquierdo(), iGramatica.getLadoDerecho()));
        lblTipoGramatica.setBounds(70, 5, 250, 30);
        add(lblTipoGramatica);
        
        setTitle("Automata de Pila");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        if (350 > xmax) {
            setBounds(0, 0, 420, y + 220);//x,y, largo y ancho
        } else {
            setBounds(0, 0, xmax + 70, y + 220);//x,y, largo y ancho
        }
        setResizable(false);
        setLocationRelativeTo(null);
        
        identificadorV = new ReconocedorVisual(iGrama, this);
        identificadorV.llenaReconocedor();
        
        reconocedorLogico = new ReconocedorLogico(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnOtraG) {
            dispose();
            IGramatica formulario = new IGramatica();
            formulario.validacion();
            formulario.inicio();
            formulario.setVisible(true);
            formulario.setResizable(false);
            formulario.setLocationRelativeTo(null);
            formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (ae.getSource() == btnAtras) {
            iGrama.setVisible(true);
            dispose();
        } else if(ae.getSource() == btnReconocedor){
            setVisible(false);
            reconocedorLogico.setVisible(true);
        }
    }

    public ArrayList<String> getSimbolosDeEntrada() {
        return simbolosDeEntrada;
    }

    public void setSimbolosDeEntrada(ArrayList<String> simbolosDeEntrada) {
        this.simbolosDeEntrada = simbolosDeEntrada;
    }

    public ArrayList<String> getSimbolosEnPila() {
        return simbolosEnPila;
    }

    public void setSimbolosEnPila(ArrayList<String> simbolosEnPila) {
        this.simbolosEnPila = simbolosEnPila;
    }

    public ArrayList<ArrayList<String>> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(ArrayList<ArrayList<String>> seleccion) {
        this.seleccion = seleccion;
    }

    public JTextField[][] getTransicion() {
        return transicion;
    }

    public void setTransicion(JTextField[][] transicion) {
        this.transicion = transicion;
    }
    
     public JTextArea getArea() {
        return area;
    }

}
