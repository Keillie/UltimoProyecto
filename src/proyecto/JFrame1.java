
package proyecto;
//Librerias a utilizar en todo el programa.
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;

public class JFrame1 extends javax.swing.JFrame {
    //variables globales que se usaran en todo el programa.
    Scanner sc = new Scanner(System.in);//Pedir datos
    RandomAccessFile archivo = null, entidades = null, atributos = null;
    private final String rutaBase = "C:\\Users\\Otra (Nueva)\\Desktop\\ProyectoFinal";
    private final String rutaEntidades = "C:\\Users\\Otra (Nueva)\\Desktop\\ProyectoFinal\\entidades.dat";
    private final String rutaAtributos = "C:\\Users\\Otra (Nueva)\\Desktop\\ProyectoFinal\\atributos.dat";
    private final int totalBytes = 83, bytesEntidades = 47, bytesAtributos = 43;
    private final static String formatoFecha = "dd/MM/yyyy";
    static DateFormat format = new SimpleDateFormat(formatoFecha);
    private List<Entidad> listaEntidades = new ArrayList<>(); //Clase entidad para lista.
    
    public JFrame1() {
        initComponents();
        this.setTitle("Simulacion/Base de datos"); //imprimi un titulo en el JFrame1
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        area1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 153, 153))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMenu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMenu.setText("MENU");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 90, 40));

        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 90, 40));

        area1.setEditable(false);
        area1.setBackground(new java.awt.Color(204, 204, 204));
        area1.setColumns(20);
        area1.setRows(5);
        area1.setToolTipText("Entidad/Atributos");
        area1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        area1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(area1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 550, 430));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed

        JFrame1 jf1 = new JFrame1();
        try {
            if (validarDefinicion()) { 
                menuDefinicion(true); //utilizando de forma booleano para abrir el metodo de menuDefinicion
            } else {
                menuDefinicion(false);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null," Error " + ex.getMessage());
        }
    }//GEN-LAST:event_btnMenuActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame1().setVisible(true);
            }
        });
    }
    
    //Metodos para definicion
    boolean validarDefinicion() throws FileNotFoundException, IOException{
        boolean respuesta = false; //variable boolena
        try{
            entidades = new RandomAccessFile(rutaEntidades, "rw"); //rw archivos de escritura y lectura
            atributos = new RandomAccessFile(rutaAtributos, "rw");
            long longitud = entidades.length();//lengh para obtener la logitud de la cadena.
            if (longitud <= 0){
                JOptionPane.showMessageDialog(null," No hay registros.", "Registros vacios ",JOptionPane.WARNING_MESSAGE); //muestra un mensaje de alerta WARNING_MESSAGE
                respuesta = false; //finaliza los procedimientos
            }
            if (longitud  >= bytesEntidades){
                entidades.seek(0);//Coloca el puntero del fichero en una posicion en este caso al principio del archivo.
                Entidad en; //Clase Entidad es igual a en.
                while (longitud >= bytesEntidades){
                    en = new Entidad(); //Instanciando clase Entidad
                    en.setIndice(entidades.readInt());
                    byte[] bNombre = new byte[30];  //leer 30 bytes para el nombre
                    entidades.read(bNombre);
                    en.setBytesNombre(bNombre);
                    en.setCantidad(entidades.readInt());
                    en.setBytes(entidades.readInt());
                    en.setPosicion(entidades.readLong());
                    entidades.readByte(); //leer el cambio de la linea
                    longitud -= bytesEntidades;
                    //leer atributos
                    long longitudAtributos = atributos.length();//length se obtiene la longitud de una cadena
                    if(longitudAtributos <= 0){
                        JOptionPane.showMessageDialog(null," No hay registros.", "Registros vacios ",JOptionPane.WARNING_MESSAGE); //muestra un mensaje de alerta WARNING_MESSAGE
                        respuesta = false; //finalizar el procedimiento.
                        break;
                    }
                    atributos.seek(en.getPosicion());
                    Atributo at; //Clase Atributo at
                    longitudAtributos = en.getCantidad() * bytesAtributos;
                    while(longitudAtributos >= bytesAtributos){
                        at = new Atributo(); //Instanciando clase
                        at.setIndice(atributos.readInt());
                        byte[] bNombreAtributo = new byte [30];
                        atributos.read(bNombreAtributo);
                        at.setBytesNombre(bNombreAtributo);
                        at.setValorTipoDato(atributos.readInt());
                        at.setLongitud(atributos.readInt());
                        at.setNombreTipoDato();
                        atributos.readByte();
                        en.setAtributo(at);
                        longitudAtributos -= bytesAtributos;
                    }
                    listaEntidades.add(en);
                }
                if(listaEntidades.size() > 0){
                    respuesta = true;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return respuesta;
    }
    //Mostrar entidad
    private void mostrarEntidad(Entidad entidad){
        area1.append("\n");
        area1.append(" Entidad:" + "\n"); //area1 imprimira en el jtextArea adjunta o imprimi lo que esta dentro de las comillas
        area1.append(" Indice: " + entidad.getIndice()+"\n");
        area1.append(" Nombre: " + entidad.getNombre()+ " \n");
        area1.append(" Cantidad de atributos: " + entidad.getCantidad()+ " \n");
        area1.append(" \n");
        area1.append(" Atributos: "+ " \n");
        int i = 1;
        for(Atributo atributo : entidad.getAtributos()){
            area1.append(" No." + i + " \n");
            area1.append(" Nombre: " + atributo.getNombre()+ " \n");
            area1.append(" Tipo de dato: " + atributo.getNombreTipoDato()+ " \n");
            if (atributo.isRequiereLongitud()){
                area1.append(" Longitud: " + atributo.getLongitud()+ " \n");
                area1.append(" \n");
            }
            i++;
        }
    }
    //Nueva entidad a agregar
    private boolean agregarEntidad() throws IOException{
        boolean resultado = false;
        try{
            Entidad entidad = new Entidad(); //Instanciando clase.
            entidad.setIndice(listaEntidades.size() + 1);
            String stringNombre = JOptionPane.showInputDialog(null," Ingrese el nombre de la entidad: "," Entidad ", JOptionPane.INFORMATION_MESSAGE);
            int longitud = 0;
            do{
                longitud = stringNombre.length();
                if(longitud < 2 || longitud >30){
                   JOptionPane.showMessageDialog(null, " La longitud del nombre no es valida [3 - 30]"," Error ", JOptionPane.ERROR_MESSAGE);//Mensaje ha habido un error en el ingreso de informacion
                }else{
                    if(stringNombre.contains(" ")){ //stringNombre contiene espacios vacios
                        JOptionPane.showMessageDialog(null, " El nombre no puede contener espacios vacios. \n Sustituya por guion bajo. ", " Error ", JOptionPane.ERROR_MESSAGE);
                        longitud = 0;
                    }
                }
            }while (longitud < 2 || longitud > 30);
            entidad.setNombre(stringNombre);
            JOptionPane.showMessageDialog(null, " Atributos de la entidad.", " Atributo ", JOptionPane.INFORMATION_MESSAGE);
            int bndDetener = 0; //se presionara 0 para detener el proceso.
            do{
                Atributo atributo = new Atributo(); //Instanciando clase Atributo
                atributo.setIndice(entidad.getIndice());
                longitud = 0;
                stringNombre = JOptionPane.showInputDialog(null," Escriba el nombre del atributo no. " + (entidad.getCantidad() + 1), " Atributo ", JOptionPane.INFORMATION_MESSAGE);
                do{
                    longitud = stringNombre.length(); //length se obtiene la longitud de una cadena
                    if(longitud < 2 || longitud > 30){
                        JOptionPane.showMessageDialog(null, " La longitud del nombre no es valida [3 - 30]"," Error ", JOptionPane.ERROR_MESSAGE); //Mensaje ha habido un error en el ingreso de informacion
                    }else{
                        if(stringNombre.contains(" ")){ //contains verifica si String contiene otra subcadena o no.
                            JOptionPane.showMessageDialog(null, " El nombre no puedecontener espacios, sustituya un guien bajo. ");
                            longitud = 0;
                        }
                    }
                }while (longitud < 2 || longitud > 30); //hacer mientras longitud sea menor que 2 o longitud sea mayor que 30
                atributo.setNombre(stringNombre);
                
               atributo.setValorTipoDato(Integer.parseInt(JOptionPane.showInputDialog(null," Seleccione el tipo de dato: " //menu para mostrar el tipo de dato a elegir en el atributo
                       + " \n " + TipoDato.INT.getValue() + " ---------- " + TipoDato.INT.name()
                       + " \n " + TipoDato.LONG.getValue() + " ---------- " + TipoDato.LONG.name()
                       + " \n " + TipoDato.STRING.getValue() + " ---------- " + TipoDato.STRING.name()
                       + " \n " + TipoDato.DOUBLE.getValue() + " ---------- " + TipoDato.DOUBLE.name()
                       + " \n " + TipoDato.FLOAT.getValue() + " ---------- " + TipoDato.FLOAT.name()
                       + " \n " + TipoDato.DATE.getValue() + " ---------- " + TipoDato.DATE.name()
                       + " \n " + TipoDato.CHAR.getValue() + " ---------- " + TipoDato.CHAR.name()," Tipo de Dato ", JOptionPane.INFORMATION_MESSAGE)));
                if(atributo.isRequiereLongitud()){
                    atributo.setLongitud(Integer.parseInt(JOptionPane.showInputDialog(null," Ingrese la longitud que desea. "," Longitud ", JOptionPane.INFORMATION_MESSAGE)));
                }else{
                    atributo.setLongitud(0);
                }
                atributo.setNombreTipoDato();
                entidad.setAtributo(atributo);
                bndDetener = (showConfirmDialog(null, "¿Desea agregar otro atributo?", " Question ", JOptionPane.YES_NO_OPTION));//Permite tomar una desision al usuario
            }while(bndDetener != 1); //se realizara mientras el valor ingresado sea diferente a 1
            JOptionPane.showMessageDialog(null, " Los datos a registrar son: "," Entidad/Atributos ", JOptionPane.INFORMATION_MESSAGE);
	    mostrarEntidad(entidad);
            longitud = (showConfirmDialog(null, "¿Desea guardar datos?", " Question ", JOptionPane.YES_NO_OPTION));//Permite tomar una desision al usuario
            if(longitud == 0){ 
                // primero guardar atributos
		// establecer la posicion inicial donde se va a guardar
                entidad.setPosicion(atributos.length());
		atributos.seek(atributos.length());// calcular la longitud el archivo
                for(Atributo atributo : entidad.getAtributos()){
                    atributos.writeInt(atributo.getIndice());
		    atributos.write(atributo.getBytesNombre());
		    atributos.writeInt(atributo.getValorTipoDato());
		    atributos.writeInt(atributo.getLongitud());
		    atributos.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
                }
                // guardar la entidad
		entidades.writeInt(entidad.getIndice());
		entidades.write(entidad.getBytesNombre());
		entidades.writeInt(entidad.getCantidad());
		entidades.writeInt(entidad.getBytes());
		entidades.writeLong(entidad.getPosicion());
		entidades.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
		listaEntidades.add(entidad);
		resultado = true;
            }else{
                JOptionPane.showMessageDialog(null, " Datos no guardados... ", " No guardado ", JOptionPane.INFORMATION_MESSAGE);
                area1.setText("");
		resultado = false;
            }
        }catch(Exception e){
            resultado = false;
            e.printStackTrace();
        }
        return resultado;
        }
         public boolean modificarEntidad(){
            try{
                int indice = 0; //inicializando indice
		while (indice < 1 || indice > listaEntidades.size()){ //mientras indice sea menor que 1 o indice sea mayor que listaEntidades.tamaño
                    for(Entidad entidad : listaEntidades){ 
                        area1.append(entidad.getIndice() + " --------- " + entidad.getNombre()+ "\n");
                    }
                    indice = Integer.parseInt(JOptionPane.showInputDialog(null," Seleccione el numero de entidad que desea modificar: ", JOptionPane.INFORMATION_MESSAGE));
                }
                Entidad entidad = null;//clase Entidad es igual a entidad inicializada como null
                for(Entidad e : listaEntidades){
                    if(indice == e.getIndice()){
                        entidad = e;
			break;
                    }
                }
                String nombreArchivo = formarNombreArchivo(entidad.getNombre());
		archivo = new RandomAccessFile(rutaBase + nombreArchivo, "rw");
		long longitudDatos = archivo.length(); //length se obtiene la longitud de una cadena
		archivo.close();
                if(longitudDatos > 0){
                    JOptionPane.showMessageDialog(null, " No es posible modificar la entidad debido a que ya tiene datos asociados", " No valido ", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    // bandera para verificar que el registro fue encontrado
		    boolean bndEncontrado = false, bndModificado = false;
		    // posicionarse al principio del archivo
		    entidades.seek(0);
		    long longitud = entidades.length(); //declaracion de variables
		    int registros = 0, salir = 0, i;
		    Entidad en;
		    byte[] temporalBytes;
                    while(longitud > totalBytes){
                       en = new Entidad();//Instanciando clase Entidad
		       en.setIndice(entidades.readInt());
		       temporalBytes = new byte[30];
		       entidades.read(temporalBytes);
		       en.setBytesNombre(temporalBytes);
		       en.setCantidad(entidades.readInt());
		       en.setBytes(entidades.readInt());
		       en.setPosicion(entidades.readLong());
                       if(entidad.getIndice() == en.getIndice()){
                           JOptionPane.showMessageDialog(null, " Si no desea modificar el campo presione enter: ");
                           String temporalString = JOptionPane.showInputDialog(null," Ingrese el nombre: ", JOptionPane.INFORMATION_MESSAGE);
			   //System.out.println(" Ingrese el nombre: ");
			   //String temporalString = "";
			   int longitud2 = 0;
			   long posicion;
                           do{
                               temporalString = sc.nextLine();
			       longitud2 = temporalString.length(); //length se obtiene la longitud de una cadena
                               if(longitud2 == 1 || longitud2 > 30){
                                   JOptionPane.showMessageDialog(null, " La longitud del nombre no es valida [2 - 30]");
                               }
                           }while(longitud2 == 1 || longitud2 > 30);
                           if(longitud2 > 0){
                               en.setNombre(temporalString);
			       posicion = registros * totalBytes;
                               archivo.seek(posicion);//posicionar el archivo
                               archivo.skipBytes(4); // moverse despues del indice (int = 4 bytes)
                               // grabar el cambio
                               archivo.write(en.getBytesNombre());
                               bndModificado = true;
                           }
                           i = 1;
                           for(Atributo at : entidad.getAtributos()){
                               JOptionPane.showMessageDialog(null, " Modificando atributo 1");
                               JOptionPane.showMessageDialog(null, at.getNombre().trim());//trim de clase String elimina los espacios al prinpio y al final de las cadenas.
                           }
                           break;
                       }
                       registros++;
                       longitud -= totalBytes; // restar los bytes del registro leido
                    }
                }
            }catch(Exception e){
                System.out.println(" Error: " + e.getMessage());
                e.printStackTrace();
            }
        return false;
        }
    private void menuDefinicion(boolean mostrarAgregarRegistro) throws IOException{
            int opciones = 1;//int opciones = 1;
            do{
                try{
                    
                    opciones = Integer.parseInt(JOptionPane.showInputDialog(null,
                            " --------------MENU--------------- \n"
                            + " 1.  Agregar entidad \n"
                            + " 2.  Modificar entidad \n"
                            + " 3.  Listar entidades \n"
                            + " 4.  Agregar registros \n"
                            + " 5.  Borrar bases de datos \n"
                            + " 0.  Salir \n"
                            + " Elija su opcion: \n"," Menu Principal ", JOptionPane.INFORMATION_MESSAGE));
                    switch(opciones){
                        case 0:
                            JOptionPane.showMessageDialog(null, " Aplicacion finalizada. ", " Saliendo ",JOptionPane.INFORMATION_MESSAGE );
                            break;
                        case 1:
                            if(agregarEntidad()){
                            JOptionPane.showMessageDialog(null, " ¡Entidad agregada con exito! ",  " Guardando... ",JOptionPane.INFORMATION_MESSAGE );
                            mostrarAgregarRegistro = true;
                        }
                            break;
                        case 2:
                            int continuar = 0;
                            continuar = (showConfirmDialog(null, "¿Esta seguro que desea modificar los datos?", " Question ", JOptionPane.YES_NO_OPTION));//Permite tomar una desision al usuario
                            if(continuar == 0){
                                cerrarArchivo();
                                JOptionPane.showInputDialog(null, " Ingrese el nombre del atributo a modificar: ", " Modificar ",JOptionPane.INFORMATION_MESSAGE );
                                if(modificarEntidad()){
				    listaEntidades = new ArrayList<>();
				    mostrarAgregarRegistro = false;
                                    JOptionPane.showMessageDialog(null, " Modificado. ", " Actualizando... ", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            break;
                        case 3:
                            if(listaEntidades.size() > 0){
                                int temporalInt = 0;
                                temporalInt = (showConfirmDialog(null, "¿Mostrar?", " Question ", JOptionPane.YES_NO_OPTION));//Permite tomar una desision al usuario
                                if(temporalInt == 0){ 
                                    for(Entidad entidad : listaEntidades){
                                        mostrarEntidad(entidad);
                                    }
                                }else{
                                    
                                    for(Entidad entidad : listaEntidades){
                                        area1.append(" Indice: " + entidad.getIndice()+ "\n" );
                                        area1.append(" Nombre: " + entidad.getNombre()+ "\n" );
                                        area1.append(" Cantidad de atributos: " + entidad.getCantidad() + "\n" );
                                    }
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,  " No hay entidades registradas. ", " Arvhivo vacio ",JOptionPane.INFORMATION_MESSAGE );
                            }
                            break;
                        case 4:
                            int indice = 0;
                            while(indice < 1 || indice > listaEntidades.size()){
                                for(Entidad entidad : listaEntidades){
                                    area1.append("\n");
                                    area1.append(entidad.getIndice() + " -------- " + entidad.getNombre());
                                }
                                indice = Integer.parseInt(JOptionPane.showInputDialog(null, " Numero de entidad que desea trabajar: "," Agregar Registros ", JOptionPane.INFORMATION_MESSAGE));
                            }
                            iniciar(indice);
                            break;
                        case 5:
                            int confirmar = 0;
                            confirmar = (showConfirmDialog(null, "¿Esta seguro de borrar los archivos de base de datos?", " Question ", JOptionPane.YES_NO_OPTION));//Permite tomar una desision al usuario
                            if(confirmar == 0){
                                cerrarArchivo();
                                if(borrarArchivos()){
                                    listaEntidades = null;
				    listaEntidades = new ArrayList<>();
				    mostrarAgregarRegistro = false;
                                    area1.setText("");//Borrar texto de jtextarea
                                    JOptionPane.showMessageDialog(null, " Archivos borrados. ", " Actualizando... ",JOptionPane.INFORMATION_MESSAGE );
                                }
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, " Opcion invalida. ", " Error ",JOptionPane.ERROR_MESSAGE );
                    }
                }catch(NumberFormatException nf){
                    JOptionPane.showMessageDialog(null," Error " + nf.getMessage());
                }
            }while(opciones != 0);
        }
        private void cerrarArchivo(){
            if (archivo != null){ 
               try{ //intento de posibles excepciones
                   archivo.close();//se cierra archivo
               } catch(IOException e){
                   e.printStackTrace();
               }
            }
            if(atributos != null){
                try{
                    atributos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(entidades != null){
                try{
                    entidades.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        private boolean borrarArchivos(){
            boolean respuesta = false;
            try{
                File file;
                for(Entidad entidad : listaEntidades){
                  file = new File(rutaBase + entidad.getNombre().trim() + ".dat");//trim de clase String elimina los espacios al prinpio y al final de las cadenas.
                  if(file.exists()){
                      file.delete(); //borrando
                  }
                  file = null;
                }
                file = new File(rutaAtributos);
                if(file.exists()){
                    file.delete();
                }
                file = null;
                file = new File(rutaEntidades);
                if(file.exists()){
                    file.delete();
                }
                file = null;
		respuesta = true;
            }catch(Exception e){
                e.printStackTrace();
            }
            return respuesta;
        }
        
         private String formarNombreArchivo(String nombre){
            return nombre.trim() + ".dat";
        }
        
        // metodos para guardar registros segun la definicion
        private void iniciar(int indice) throws FileNotFoundException{
            int opcion = 0;
	    String nombreArchivo = "";
            try{
                Entidad entidad = null;
                for(Entidad en : listaEntidades){
                    if(indice == en.getIndice()){
                        nombreArchivo = formarNombreArchivo(en.getNombre());
			entidad = en;
			break;
                    }
                }
                archivo = new RandomAccessFile(rutaBase + nombreArchivo, "rw");
                JOptionPane.showMessageDialog(null,  "Bienvenido (a)", "Inicio",JOptionPane.INFORMATION_MESSAGE );
		Atributo at = entidad.getAtributos().get(0);
                do{
                    try{
                        opcion = Integer.parseInt(JOptionPane.showInputDialog(null, 
                                " ----- Menu de Registros ----- \n"
                               + " 1.  Agregar \n" 
                               + " 2.  Listar \n"
                               + " 3.  Buscar \n"
                               + " 4.  Modificar \n"
                               + " 0.  Regresar al menu anterior \n"
                               + " Seleccione su opcion: "," Menu Registros ", JOptionPane.INFORMATION_MESSAGE));
                        switch(opcion){
                            case 0:
                                System.out.println("");
                                break;
                            case 1:
                                grabarRegistro(entidad);
                                break;
                            case 2:
                                listarRegistros(entidad);
                                break;
                            case 3:
                                int carne = Integer.parseInt(JOptionPane.showInputDialog(null, " Se hara la busqueda en la primera columna. \n " + " Ingrese " + at.getNombre().trim() + " a buscar"," Buscar ", JOptionPane.INFORMATION_MESSAGE));
			        //System.out.println(" Ingrese " + at.getNombre().trim() + " a buscar");
				// sc.nextLine();
				encontrarRegistros(carne);
                                break;
                            case 4:
                                carne = Integer.parseInt(JOptionPane.showInputDialog(null, " Ingrese dato a modificar " ," Modificar ", JOptionPane.INFORMATION_MESSAGE));
				modificarRegistro(carne);
                                break;
                            default:
                                JOptionPane.showMessageDialog(null,  " Opcion invalida. :(", "Error ",JOptionPane.ERROR_MESSAGE );
                                break;
                        }
                    }catch(Exception e){  // capturar cualquier excepcion que ocurra
                        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
                    }
                }while(opcion != 0);
                archivo.close();
            }catch(Exception e){ // capturar cualquier excepcion que ocurra
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
                e.printStackTrace();
            }
        }
        
        private boolean grabarRegistro(Entidad entidad) throws IOException{
            boolean resultado = false;
            try{
                // posicionarse al final para grabar
		archivo.seek(archivo.length());
		boolean valido;
		byte[] bytesString;
		String temporalString = "";
                for(Atributo atributo : entidad.getAtributos()){
                    valido = false;
                    JOptionPane.showInputDialog(null, " Ingrese " + atributo.getNombre().trim() ," Guardar Registro ", JOptionPane.INFORMATION_MESSAGE);
                    while(!valido){
                        try{
                            switch(atributo.getTipoDato()){
                                case INT:
                                    int temporalInt = sc.nextInt();
				    archivo.writeInt(temporalInt);
				    sc.nextLine();
                                    break;
                                case LONG:
                                   long temporalLong = sc.nextLong();
				   archivo.writeLong(temporalLong);
				   break;
                                case STRING:
                                    int longitud = 0;
                                    do{
                                        temporalString = sc.nextLine();
					longitud = temporalString.length();
                                        if(longitud <= 1 || longitud > atributo.getLongitud()){
                                            JOptionPane.showMessageDialog(null, " La longitud de " + atributo.getNombre().trim() + " no es valida [1 - " + atributo.getLongitud() + "]", "Error ",JOptionPane.ERROR_MESSAGE );
                                        }
                                    }while(longitud <= 0 || longitud > atributo.getLongitud());
                                    // arreglo de bytes de longitud segun definida
				    bytesString = new byte[atributo.getLongitud()];
				    // convertir caracter por caracter a byte y agregarlo al arreglo
                                    for(int i = 0; i < temporalString.length(); i++){
                                        bytesString[i] = (byte) temporalString.charAt(i); //charAr implementó un método que recibe el índice o posición del carácter deseado en la cadena.
                                    }
                                    archivo.write(bytesString);
				    break;
                                case DOUBLE:
                                    double temporalDouble = sc.nextDouble();
				    archivo.writeDouble(temporalDouble);
				    break;
                                case FLOAT:
                                    float temporalFloat = sc.nextFloat();
				    archivo.writeFloat(temporalFloat);
				    break;
                                case DATE:
                                    Date date = null;
				    temporalString = "";
                                    while(date == null){
                                        temporalString = JOptionPane.showInputDialog(null, "Formato de fecha: " + formatoFecha ," Date ", JOptionPane.INFORMATION_MESSAGE);
					date = stringToDate(temporalString);
                                    }
                                    bytesString = new byte[atributo.getBytes()];
                                    for(int i = 0; i < temporalString.length(); i++){
                                        bytesString[i] = (byte) temporalString.charAt(i);//charAr implementó un método que recibe el índice o posición del carácter deseado en la cadena.
                                    }
                                    archivo.write(bytesString);
                                    break;
                                case CHAR:
                                    do{
                                        temporalString = sc.nextLine();
					longitud = temporalString.length();//length se obtiene la longitud de una cadena
                                        if(longitud < 1 || longitud > 1){
                                            JOptionPane.showMessageDialog(null, " Solo se permite un caracter. ", "Error ",JOptionPane.ERROR_MESSAGE );
                                        }
                                    }while(longitud < 1 || longitud > 1);
                                    byte caracter = (byte) temporalString.charAt(0);//charAr implementó un método que recibe el índice o posición del carácter deseado en la cadena.
				    archivo.writeByte(caracter);
				    break;
                            }
                            valido = true;
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, " Error " + e.getMessage() + " al capturar tipo de dato, vuelva a ingresar el valor: ", "Error ",JOptionPane.ERROR_MESSAGE );
                        }
                    }// end while
                }// end for
                archivo.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
		resultado = true;
            }catch(Exception e){
                resultado = false;
                JOptionPane.showMessageDialog(null, " Error al agregar el registro " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
            }
            return resultado;
        }

        public void listarRegistros(Entidad entidad) throws IOException{
            try{
                long longitud = archivo.length(); //length se obtiene la longitud de una cadena
                if(longitud <= 0){ 
                   JOptionPane.showMessageDialog(null," No hay registros.", "Registros vacios ",JOptionPane.WARNING_MESSAGE); //muestra un mensaje de alerta WARNING_MESSAGE
		   return; // finalizar el procedimiento 
                }
                archivo.seek(0); // posicionarse al principio del archivo
		byte[] temporalArrayByte;
		String linea = "";
                for(Atributo atributo : entidad.getAtributos()){
                    linea += atributo.getNombre().toString().trim() + "\t\t";//toString se utiliza para convertir a String (es decir, a una cadenade texto) cualquier objeto Java. trim de clase String elimina los espacios al prinpio y al final de las cadenas.  
                }
                System.out.println(linea);
                while(longitud >= entidad.getBytes()){
                    linea = "";
                    for(Atributo atributo : entidad.getAtributos()){
                        switch (atributo.getTipoDato()) {
                            case INT:
                                int temporalInt = archivo.readInt();
                                linea += String.valueOf(temporalInt) + "\t\t"; //valuOf, permite realizar conversiones de tipos.
				break;
                            case LONG:
				long temporalLong = archivo.readLong();
				linea += String.valueOf(temporalLong) + "\t\t"; //valuOf, permite realizar conversiones de tipos.
				break;
			    case STRING:
			        temporalArrayByte = new byte[atributo.getLongitud()];
				archivo.read(temporalArrayByte);
				String temporalString = new String(temporalArrayByte);
				linea += temporalString.trim() + "\t\t";
				break;
			    case DOUBLE:
				double temporalDouble = archivo.readDouble();
				linea += String.valueOf(temporalDouble) + "\t\t"; //valuOf, permite realizar conversiones de tipos.
				break;
			    case FLOAT:
				float temporalFloat = archivo.readFloat();
				linea += String.valueOf(temporalFloat) + "\t\t"; //valuOf, permite realizar conversiones de tipos.
				break;
			    case DATE:
				temporalArrayByte = new byte[atributo.getBytes()];
				archivo.read(temporalArrayByte); //archivo leer array temporal
				temporalString = new String(temporalArrayByte);
				linea += temporalString.trim() + "\t\t"; //trim elimina los espacios al prinpio y al final de las cadenas.
				break;
			    case CHAR:
				char temporalChar = (char) archivo.readByte();
				linea += temporalChar + "\t\t";
				break;
                        }
                    }
                    archivo.readByte();// leer el cambio de linea den el archivo
                    // restar los bytes del registro leido
                    longitud -= entidad.getBytes();
		    System.out.println(linea);
                }
            }catch(Exception e){ //encuentra posibles errores
                JOptionPane.showMessageDialog(null, " Error: " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
            }
        }
        
        public void encontrarRegistros(int carne) throws IOException{
            try{
                long longitud = archivo.length();//length se usa para obtener la longitud de una cadena Java
                if(longitud <= 0){
                    JOptionPane.showMessageDialog(null," No hay registros.", "Registros vacios ",JOptionPane.WARNING_MESSAGE); //muestra un mensaje de alerta WARNING_MESSAGE
		    return; // finalizar el procedimiento
                }
                // bandera para verificar que el registro fue encontrado
		boolean bndEncontrado = false;
                // posicionarse al principio del archivo
		archivo.seek(0);
		// solo se instancia una vez y se sobreescriben los datos debido a que se
		// mostrara un unico registro
		Alumno al = new Alumno(); //instanciando clase alumno
                while(longitud >= totalBytes){
                    al.setCarne(archivo.readInt());
		    byte[] bNombre = new byte[50]; // leer 50 bytes para el nombre
		    archivo.read(bNombre);
		    al.setBytesNombre(bNombre);
		    byte[] bFecha = new byte[28]; // 28 bytes para la fecha
		    archivo.read(bFecha);
		    archivo.readByte();// leer el cambio de linea
		    al.setBytesFechaNacimiento(bFecha);
                    if(al.getCarne() == carne){
                        // imprimir los campos del registro
                        area1.append(" Datos: " + al.getCarne()+ " \n ");
                        area1.append(" Nombre: " + al.getNombre()+ " \n ");
                        area1.append(" Fecha de nacimiento: " + dateToString(al.getFechaNacimiento())+ " \n ");//ToString se utiliza para convertir a String (es decir, a una cadena de texto) cualquier objeto Java
			bndEncontrado = true;
			// si el registro se ha encontrado entonces salir del ciclo
			break;
                    }
                    // restar los bytes del registro leido
		    longitud -= totalBytes;
                }
                // solo si el registro no se encontro imprimir un mensaje
                if(!bndEncontrado){  // esto es equivalente a (bndEncontrado == false)
                    JOptionPane.showMessageDialog(null, " No se encontro el carne indicado, por favor verifique. ", "Error ",JOptionPane.ERROR_MESSAGE );
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, " Error: " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
        }
    }
        //Modificando registro
        private void modificarRegistro(int carne) throws IOException, ParseException{
            try{
                // bandera para verificar que el registro fue encontrado
		boolean bndEncontrado = false, bndModificado = false;
		archivo.seek(0);// posicionarse al principio del archivo
		long longitud = archivo.length();//length se usa para obtener la longitud de una cadena 
		int registros = 0;
		// solo se instancia una vez y se sobreescriben los datos debido a que se
		// mostrara un unico registro
		Alumno al = new Alumno(); //Instanciando clase
                while(longitud > totalBytes){
                    al.setCarne(archivo.readInt());
		    byte[] bNombre = new byte[50]; // leer 50 bytes para el nombre
		    archivo.read(bNombre);
		    al.setBytesNombre(bNombre);
		    byte[] bFecha = new byte[28]; // 28 bytes para la fecha
		    archivo.read(bFecha);
		    archivo.readByte();// leer el cambio de linea
		    al.setBytesFechaNacimiento(bFecha);
                    if(al.getCarne() == carne){
                        System.out.println(" Si no desea modificar el campo presione enter");//Revisar ***
			System.out.println(" Ingrese el nombre");
			String tmpStr = "";
			int longitud2 = 0;
			long posicion;
                        do{
                           tmpStr = sc.nextLine();
			   longitud2 = tmpStr.length();//length se usa para obtener la longitud de una cadena 
                           if(longitud2 > 50){
                               JOptionPane.showMessageDialog(null, " La longitud del nombre no es valida [1 - 50]", "Error ",JOptionPane.ERROR_MESSAGE );
                               //System.out.println(" La longitud del nombre no es valida [1 - 50]");
                           }
                        }while(longitud2 > 50);
                        if(longitud2 > 0){
                            al.setNombre(tmpStr);
			    // encontrar la posicion especifica del campo a modificar
			    // primero encontrar la posicion del registro
			    posicion = registros * totalBytes;
			    archivo.seek(posicion);
			    // sumar el tamanio del campo llave
			    archivo.skipBytes(4); // moverse despues del carne (int = 4 bytes)
			    // grabar el cambio
			    archivo.write(al.getBytesNombre());
			    bndModificado = true;
                        }
                        JOptionPane.showInputDialog(null, " Ingrese la fecha" + (" + formatoFecha + ")," Date ", JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println(" Ingrese la fecha (" + formatoFecha + ")");
			tmpStr = sc.nextLine();
                        if(tmpStr.length() > 0){
                            Date date = null;
                            while(date == null){
                                date = stringToDate(tmpStr);
                            }
                            al.setFechaNacimiento(date);
			    posicion = registros * totalBytes;
			    archivo.seek(posicion);
			    archivo.skipBytes(4 + 50); // moverse despues del carne + el nombre (int = 4 bytes, nombre = 50 bytes)
			    archivo.write(al.getBytesFechaNacimiento());
			    bndModificado = true;
                        }
                        // imprimir los campos del registro
			if (bndModificado){ // equivalente a (bndModificado == true)
                            JOptionPane.showMessageDialog(null, " El registro fue modificado correctamente, los nuevos datos son:", " Actualizacion ",JOptionPane.INFORMATION_MESSAGE );
                        }
                        area1.append(" Datos: " + al.getCarne()+ " \n ");
                        area1.append(" Nombre: " + al.getNombre()+ " \n ");
                        area1.append(" Fecha de nacimiento: " + dateToString(al.getFechaNacimiento())+ " \n ");
			bndEncontrado = true; // si el registro se ha encontrado entonces salir del ciclo
			break;
                    }
                    registros++;
		    // restar los bytes del registro leido
	            longitud -= totalBytes;
                }
                // solo si el registro no se encontro imprimir un mensaje
		if (!bndEncontrado){// esto es equivalente a (bndEncontrado == false)
                    JOptionPane.showMessageDialog(null," No se encontro, por favor verifique.", "Error ",JOptionPane.ERROR_MESSAGE );
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Error: " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
            }
        }
        public Date stringToDate (String stringFecha){
            Date date = null;
            try{
                date = format.parse(stringFecha);
            }catch(Exception e){
                date = null;
                JOptionPane.showMessageDialog(null," Error en fecha: " + e.getMessage(), "Error ",JOptionPane.ERROR_MESSAGE );
            }
	    return date;
        }
        
        public String dateToString(Date date) {
	    String stringFecha;
	    stringFecha = format.format(date);
	    return stringFecha;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea area1;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

}
