package gui;

import model.LexemaVO;
import control.ExArchivo;
import control.ExAutomata;
import control.ExTablaTransicion;

import com.alee.laf.WebLookAndFeel;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Hecho con <3 Por:
 * @author GarciHard
 */

public class DamaIDE extends JFrame implements ActionListener{
    
    private JPanel panBackground;
    private JPanel panBotonesEjeY;
    private JPanel panAreaTexto;
    private JPanel panSeparadorNorte;
    private JPanel panSeparadorEste;
    
    private JButton btnAnalisisLexico;
    private JTextArea txaEditorCodigo;
    private JScrollPane scpEditorCodigo;
    
    private JMenuBar mnbPrincipal;
    private JMenu mnuArchivo;
    private JMenuItem mniNuevo;
    private JMenuItem mniAbrir;
    private JMenuItem mniGuardar;
    private JMenuItem mniSalir;
    private JMenu mnuHerramientas;
    private JMenu mnuCambiarTemaEditor;
    private JMenuItem mniTemaDefault;
    private JMenuItem mniTemaOscuro;
    private JMenuItem mniTemaClaro;
    
    private JPanel panTablaLexemas;
    private JTable tblLexema;
    private DefaultTableModel dtmTablaLexema;
    private JScrollPane scpTablaLexema;
    private String columnName[];
    private Object tableContent[][];
    
    private ArrayList<LexemaVO> lexemas;
    
    public DamaIDE() {
        initFrame();
    }
    
    private void initFrame() {
/******************** Creación del frame principal ********************/
        setTitle("Dama IDE v0.1");
        setSize(800,600);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Creacion del menuBar para el frame principal
        mnbPrincipal = new JMenuBar();
        
        mnuArchivo = new JMenu("Archivo");
        mniNuevo = new JMenuItem("Nuevo");
        mniNuevo.addActionListener(this);
        mniAbrir = new JMenuItem("Abrir");
        mniAbrir.addActionListener(this);
        mniGuardar = new JMenuItem("Guardar");
        mniGuardar.addActionListener(this);
        mniSalir = new JMenuItem("Salir");
        mniSalir.addActionListener(this);
        
        mnuArchivo.add(mniNuevo);
        mnuArchivo.add(mniAbrir);
        mnuArchivo.add(mniGuardar);
        mnuArchivo.add(mniSalir);
        
        mnuHerramientas = new JMenu("Herramientas");
        mnuCambiarTemaEditor = new JMenu("Cambiar Tema Editor");
        mniTemaDefault = new JMenuItem("Default Dama");
        mniTemaDefault.addActionListener(this);
        mniTemaOscuro = new JMenuItem("Dark Dama");
        mniTemaOscuro.addActionListener(this);
        mniTemaClaro = new JMenuItem("Christian Dama");
        mniTemaClaro.addActionListener(this);
        
        mnuCambiarTemaEditor.add(mniTemaDefault);
        mnuCambiarTemaEditor.add(mniTemaOscuro);
        mnuCambiarTemaEditor.add(mniTemaClaro);
        
        mnuHerramientas.add(mnuCambiarTemaEditor);
        
        mnbPrincipal.add(mnuArchivo);
        mnbPrincipal.add(mnuHerramientas);
        
        //Creación del panel de fondo
        panBackground = new JPanel();
        panBackground.setSize(getSize());
        panBackground.setLayout(new BorderLayout());
        panBackground.setBackground(new Color(255,255,255));
        
/******************** Creacion del panel separador norte, que estara en el norte ... ********************/
        panSeparadorNorte = new JPanel();
        panSeparadorNorte.setSize(800,50);
        
/******************** Creacion del panel de botones en eje Y, que estara en la parte izquierda ********************/
        panBotonesEjeY = new JPanel();
        panBotonesEjeY.setSize(300,300);
        panBotonesEjeY.setLayout(new BoxLayout(panBotonesEjeY, BoxLayout.Y_AXIS));
        panBotonesEjeY.setBorder(BorderFactory.createTitledBorder("Opciones"));
        
        //Creacion de componentes para el panel de Botones en eje y
        btnAnalisisLexico = new JButton("Análisis Léxico");
        btnAnalisisLexico.addActionListener(this);
        
        //Agregar componentes al panel de botones en eje Y
        panBotonesEjeY.add(btnAnalisisLexico);
        
/******************** Creacion del panel area de texto, estara en la parte derecha(termino en el centro) ********************/
        panAreaTexto = new JPanel();
        panAreaTexto.setSize(500,300);
        panAreaTexto.setLayout(new BorderLayout());
        panAreaTexto.setBorder(BorderFactory.createTitledBorder("Editor de código"));
        
        //Creacion de componentes para el panel area de texto
        txaEditorCodigo = new JTextArea(5,20);
        txaEditorCodigo.setBackground(new Color(220,220,222));
        txaEditorCodigo.setFont(new Font("Monospaced", Font.PLAIN, 16));
        txaEditorCodigo.setForeground(new Color(0,0,0));
        scpEditorCodigo = new JScrollPane(txaEditorCodigo);
        
        //Agregar componentes para el panel area de texto
        panAreaTexto.add(scpEditorCodigo, BorderLayout.CENTER);

/******************** Creacion del panel separador derecho, que estara en la parte derecha ********************/
        panSeparadorEste = new JPanel();
        panSeparadorEste.setSize(50,300);

/******************** Creacion del panel que tendra la tabla de lexema, estara en la parte de abajo (sur) ********************/
        panTablaLexemas = new JPanel();
        panTablaLexemas.setLayout(new BorderLayout());
        panTablaLexemas.setSize(800,200);
        panTablaLexemas.setBorder(BorderFactory.createTitledBorder("Salida"));
        
        columnName = new String[]{"Lexema", "Nombre", "Token"};
        tableContent = new Object[][]{};
        dtmTablaLexema = new DefaultTableModel(tableContent, columnName);
        tblLexema = new JTable(dtmTablaLexema);
        
        /*TableColumn col1 = tblLexema.getColumn("Lexema");
        col1.setMinWidth(266);
        col1.setResizable(false);
        TableColumn col2 = tblLexema.getColumn("Nombre");
        col2.setMinWidth(266);
        col2.setResizable(false);
        TableColumn col3 = tblLexema.getColumn("Token");
        col3.setMinWidth(266);
        col3.setResizable(false);*/
        
        scpTablaLexema = new JScrollPane(tblLexema);
        scpTablaLexema.setPreferredSize(panTablaLexemas.getSize());
        scpTablaLexema.setBackground(new Color(220,220,222));
        
        panTablaLexemas.add(scpTablaLexema, BorderLayout.CENTER);
        
/******************** Agregar Componentes a los paneles y al frame principal ********************/
        
        //Agregar componentes al panel de fondo
        panBackground.add(panSeparadorNorte, BorderLayout.NORTH);
        panBackground.add(panBotonesEjeY, BorderLayout.WEST);
        panBackground.add(panAreaTexto, BorderLayout.CENTER);
        //panBackground.add(panSeparadorEste, BorderLayout.EAST);
        panBackground.add(panTablaLexemas, BorderLayout.SOUTH);
        
        //Agregar componentes al frame principal
        setJMenuBar(mnbPrincipal);
        add(panBackground);
        WebLookAndFeel.install();
    }
/********************  ********************/    
    
 
    
/******************** Creacion de Listener a lo Medina(o sea a lo buey) ********************/
    @Override
    public void actionPerformed(ActionEvent evt) {
        //Acciones de los Menus
        if (evt.getSource() == mniNuevo) {//MenuItem Nuevo Archivo
            txaEditorCodigo.setText("");
            tblLexema.setModel(dtmTablaLexema);
        }
        if (evt.getSource() == mniAbrir) {//MenuItem Abrir Archivo existente
                    String Text = "";
        try {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(this);
            File Abrir = fc.getSelectedFile();
            if (Abrir != null) {
                txaEditorCodigo.setText("");
                FileReader Fichero = new FileReader(Abrir);
                BufferedReader leer = new BufferedReader(Fichero);
                while ((Text = leer.readLine()) != null) {
                    txaEditorCodigo.append(Text + "\n");
                }
                leer.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        }
        if (evt.getSource() == mniGuardar) {//MenuItem Guardar Archivo escrito
            String texto = txaEditorCodigo.getText();
            try {
                JFileChooser NUEVO = new JFileChooser();
                NUEVO.showSaveDialog(this);
                File Guardar = NUEVO.getSelectedFile();
                if (Guardar != null) {
                    texto = NUEVO.getSelectedFile().getName();
                    FileWriter escribir = new FileWriter(Guardar + ".txt", true);
                    escribir.write(txaEditorCodigo.getText());
                    escribir.close();
                }

            } catch (Exception ee) {
                System.out.println("Error al escribir");
            }
        }
        if (evt.getSource() == mniSalir) {//MenuItem Salir, cerrar la aplicación
            System.exit(0);
        }
        
        //Temas de Dama IDE
        if (evt.getSource() == mniTemaDefault) {
            txaEditorCodigo.setBackground(new Color(220,220,222));
            txaEditorCodigo.setForeground(new Color(0,0,0));
            tblLexema.setBackground(new Color(220,220,222));
            tblLexema.setForeground(new Color(0,0,0));
        }
        if (evt.getSource() == mniTemaOscuro) {
            txaEditorCodigo.setBackground(new Color(0,0,0));
            txaEditorCodigo.setForeground(new Color(46, 204, 113));
            tblLexema.setBackground(new Color(0,0,0));
            tblLexema.setForeground(new Color(46, 204, 113));
        }
        if (evt.getSource() == mniTemaClaro) {
            txaEditorCodigo.setBackground(new Color(52, 152, 219));
            txaEditorCodigo.setForeground(new Color(255,255,255));
            tblLexema.setBackground(new Color(52, 152, 219));
            tblLexema.setForeground(new Color(255,255,255));
        }
        
        //Botón para el análisis léxico
        if (evt.getSource() == btnAnalisisLexico) {
            if (!txaEditorCodigo.getText().isEmpty()) {
                lexemas = new ArrayList();
                tblLexema.setModel(new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Lexama", "Nombre", "Token"}
                ));
                Analizar();
            } else {
                JOptionPane.showMessageDialog(this, "Editor de Código Vacio");
            }
        }
    }

/******************** Creacion de unos Métodos ********************/
    private void SepararCodigo(String codigo) {
        codigo = codigo + "\n";
        String[][] aux = ExArchivo.ExtraerCarctaeresDeArchivo();
        boolean arroba = false, comilla = false;
        String lexema = "";
        int linea = 1;
        for (int i = 0; i < codigo.length(); i++) {
            for (int j = 0; j < aux.length; j++) {
                if (codigo.charAt(i) == '\"') {
                    lexema = lexema + codigo.charAt(i);
                    comilla = !comilla;
                    break;
                } else if (comilla == true) {
                    lexema = lexema + codigo.charAt(i);
                    break;
                }
                if (codigo.charAt(i) == '#') {
                    lexema = lexema + codigo.charAt(i);
                    arroba = !arroba;
                    break;
                } else if (arroba == true) {
                    lexema = lexema + codigo.charAt(i);
                    break;
                }
                LexemaVO lex = new LexemaVO();
                LexemaVO lex2 = new LexemaVO();
                if (aux[j][0].length() == 1) {
                    if (aux[j][0].compareTo(codigo.charAt(i) + "") == 0 || codigo.charAt(i) == ' ' || codigo.charAt(i) == '\n' || codigo.charAt(i) == '\t') {
                        if (lexema.compareTo("") != 0) {
                            lex.setLexema(lexema);
                            lexema = "";
                            lexemas.add(lex);
                        }
                        if (codigo.charAt(i) == '\n') {
                            linea++;
                        }
                        if (codigo.charAt(i) != ' ' & codigo.charAt(i) != '\n' & codigo.charAt(i) != '\t') {
                            lex2.setLexema(codigo.charAt(i) + "");
                            lex2.setToken(aux[j][1]);
                            lex2.setNombre(aux[j][2]);
                            lexemas.add(lex2);
                        }
                        break;
                    } else if (j == aux.length - 1) {
                        lexema = lexema + codigo.charAt(i);
                    }
                } else if (i != codigo.length() - 1) {//checar si es <= o algo paresido
                    if (aux[j][0].compareTo(codigo.charAt(i) + "" + codigo.charAt(i + 1)) == 0) {
                        if (lexema.compareTo("") != 0) { //para que no se pasen palabras basias
                            lex.setLexema(lexema);
                            lexema = "";
                            lexemas.add(lex);
                        }
                        lex2.setLexema(aux[j][0]);
                        lex2.setToken(aux[j][1]);
                        lex2.setNombre(aux[j][2]);
                        lexemas.add(lex2);
                        i++;
                        break;
                    }
                }
            }
        }
    }

    private void Analizar() {
        SepararCodigo(txaEditorCodigo.getText());
        String[][] aux = ExArchivo.ExtraerPalabraReservada();
        for (String[] a : aux) {
            for (LexemaVO l : lexemas) {
                if (a[0].compareTo(l.getLexema()) == 0) {
                    l.setToken(a[1]);
                    l.setNombre("Palabra reservada");
                }
            }
        }
        char[][] array = ExTablaTransicion.getTabla();
        for (LexemaVO l : lexemas) {
            if (l.getToken().compareTo("") == 0) {
                ExAutomata.EsValido(l.getLexema(), array, l);
            }
        }
        //finalmente imprimir resultados
        for (LexemaVO l : lexemas) {
            ((DefaultTableModel) tblLexema.getModel()).addRow(
                    new Object[]{l.getLexema(), l.getNombre(), l.getToken()}
            );
        }
    }
    
}//Fin de la aplicación

/*tabla de transicion sin mayusculas

#abcdefghijklmnopqrstuvwxyz0123456789~!@\¿$%^&*()_+¬·|¤€<>?:';{}[] =*-,."PN
1333333333333333333333333334444444444xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx7f1
2111111111111111111111111111111111111111111111111111111111111111111111111f2
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxv3
x333333333333333333333333333333333333xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxv4
xxxxxxxxxxxxxxxxxxxxxxxxxxx4444444444xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx5xv5
xxxxxxxxxxxxxxxxxxxxxxxxxxx6666666666xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxf6
xxxxxxxxxxxxxxxxxxxxxxxxxxx6666666666xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxv9
x777777777777777777777777777777777777777777777777777777777777777777777778f7
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxv8

*/