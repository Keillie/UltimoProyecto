
package proyecto;

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

public class ManejoArchivo {
    //variables globales que se usaran en todo el programa.
    Scanner sc = new Scanner(System.in);
    RandomAccessFile archivo = null, entidades = null, atributos = null;
    private final String rutaBase = "C:\\Users\\Otra (Nueva)\\Desktop\\ProyectoFinal";
    
    private final String rutaEntidades = "C:\\Users\\Otra (Nueva)\\Desktop\\ProyectoFinal\\entidades.dat";
    
    private final String rutaAtributos = "C:\\Users\\Otra (Nueva)\\Desktop\\ProyectoFinal\\atributos.dat";
    
    private final int totalBytes = 83, bytesEntidades = 47, bytesAtributos = 43;
    private final static String formatoFecha = "dd/MM/yyyy";
    static DateFormat format = new SimpleDateFormat(formatoFecha);
    
    private List<Entidad> listaEntidades = new ArrayList<>(); //Clase entidad para lista.
    
    public static void main (String [] args) throws IOException{
        ManejoArchivo ma = new ManejoArchivo(); //Instanciando ManejoArchivo
        if(ma.validarDefinicion()){
            ma.menuDefinicion(true);
        }else{
            ma.menuDefinicion(false);
        }
        System.exit(0); //finaliza aplicacion
    }
    //Metodos para definicion
    private boolean validarDefinicion() throws FileNotFoundException, IOException{
        boolean respuesta = false;
        try{
            entidades = new RandomAccessFile(rutaEntidades, "rw");
            atributos = new RandomAccessFile(rutaAtributos, "rw");
            long longitud = entidades.length();//lengh para obtener la logitud de la cadena.
            if (longitud <= 0){
                System.out.println("No hay registros.");
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
                        System.out.println("No hay registros. ");
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
        System.out.println(" Indice: " + entidad.getIndice());
        System.out.println(" Nombre: " + entidad.getNombre());
        System.out.println(" Cantidad de atributos: " + entidad.getCantidad());
        System.out.println(" Atributos: ");
        int i = 1;
        for(Atributo atributo : entidad.getAtributos()){
            System.out.println(" No." + i);
            System.out.println(" Nombre: " + atributo.getNombre());
            System.out.println(" Tipo de dato: " + atributo.getNombreTipoDato());
            if (atributo.isRequiereLongitud()){
                System.out.println(" Longitud: " + atributo.getLongitud());
            }
            i++;
        }
    }
    
    private boolean agregarEntidad() throws IOException{
        boolean resultado = false;
        try{
            Entidad entidad = new Entidad(); //Instanciando clase.
            entidad.setIndice(listaEntidades.size() + 1);
            System.out.println(" Ingrese el nombre de la entidad: ");
            String stringNombre = "";
            int longitud = 0;
            do{
                stringNombre = sc.nextLine();
                longitud = stringNombre.length();
                if(longitud < 2 || longitud >30){
                    System.out.println(" La logitud del nombre no es valida [3 - 30]");
                }else{
                    if(stringNombre.contains(" ")){ //stringNombre contiene espacios vacios
                        System.out.println(" El nombre no puede contener espacios vacios, sustituya por guion bajo. ");
                        longitud = 0;
                    }
                }
            }while (longitud < 2 || longitud > 30);
            entidad.setNombre(stringNombre);
            System.out.println(" Atributos de la entidad.");
            int bndDetener = 0; //se presionara 0 para detener el proceso.
            do{
                Atributo atributo = new Atributo(); //Instanciando clase Atributo
                atributo.setIndice(entidad.getIndice());
                longitud = 0;
                System.out.println(" Escriba el nombre del atributo no. " + (entidad.getCantidad() + 1));
                do{
                    stringNombre = sc.nextLine();
                    longitud = stringNombre.length(); //length se obtiene la longitud de una cadena
                    if(longitud < 2 || longitud > 30){
                        System.out.println(" La longitud del nombre no es valida [3 - 30]");
                    }else{
                        if(stringNombre.contains(" ")){ //contains verifica si String contiene otra subcadena o no.
                            System.out.println(" El nombre no puedecontener espacios, sustituya un guien bajo. ");
                            longitud = 0;
                        }
                    }
                }while (longitud < 2 || longitud > 30); //hacer mientras longitud sea menor que 2 o longitud sea mayor que 30
                atributo.setNombre(stringNombre);
                System.out.println(" Seleccione el tipo de dato. ");
                System.out.println(TipoDato.INT.getValue() + "----------" + TipoDato.INT.name());
                System.out.println(TipoDato.LONG.getValue() + "----------" + TipoDato.LONG.name());
                System.out.println(TipoDato.STRING.getValue() + "----------" + TipoDato.STRING.name());
                System.out.println(TipoDato.DOUBLE.getValue() + "----------" + TipoDato.DOUBLE.name());
                System.out.println(TipoDato.FLOAT.getValue() + "----------" + TipoDato.FLOAT.name());
                System.out.println(TipoDato.DATE.getValue() + "----------" + TipoDato.DATE.name());
                System.out.println(TipoDato.CHAR.getValue() + "----------" + TipoDato.CHAR.name());
                atributo.setValorTipoDato(sc.nextInt());
                if(atributo.isRequiereLongitud()){
                    System.out.println(" Ingrese la longitud que desea. ");
                    atributo.setLongitud(sc.nextInt());
                }else{
                    atributo.setLongitud(0);
                }
                atributo.setNombreTipoDato();
                entidad.setAtributo(atributo);
                System.out.println(" Desea agregar otro atributo presione cualquier numero, de lo contrario presione 0");
                bndDetener = sc.nextInt();
            }while(bndDetener != 0); //se realizara mientras el valor ingresado sea diferente a 0
            System.out.println(" Los datos a registrar son: ");
	    mostrarEntidad(entidad);
	    System.out.println(" Presione 1 para guardar y 0 para cancelar el proceso.");
	    longitud = sc.nextInt();
            if(longitud == 1){
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
                System.out.println(" No se guardo la entidad debido a que el usuario decidio cancelarlo. ");
		resultado = false;
            }
            System.out.println(" Presione una tecla para continuar...");
	    System.in.read(); //lee exactamente un byte del flujo de entrada (teclado en este caso).
        }catch(Exception e){
            resultado = false;
            e.printStackTrace();
        }
        return resultado;
        }
        private void modificarEntidad(){
            try{
                int indice = 0; //inicializando indice
		while (indice < 1 || indice > listaEntidades.size()){ //mientras indice sea menor que 1 o indice sea mayor que listaEntidades.tamaño
                    for(Entidad entidad : listaEntidades){ 
                        System.out.println(entidad.getIndice() + " --------- " + entidad.getNombre());
                    }
                    System.out.println(" Seleccione la entidad que desea modificar: ");
		    indice = sc.nextInt();
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
                    System.out.println(" No es posible modificar la entidad debido a que ya tiene datos asociados");
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
                           System.out.println(" Si no desea modificar el campo presione enter");
			   System.out.println(" Ingrese el nombre: ");
			   String temporalString = "";
			   int longitud2 = 0;
			   long posicion;
                           do{
                               temporalString = sc.nextLine();
			       longitud2 = temporalString.length(); //length se obtiene la longitud de una cadena
                               if(longitud2 == 1 || longitud2 > 30){
                                   System.out.println(" La longitud del nombre no es valida [2 - 30]");
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
                               System.out.println(" Modificando atributo 1");
			       System.out.println(at.getNombre().trim());//trim de clase String elimina los espacios al prinpio y al final de las cadenas.
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
        }
        
        private void menuDefinicion(boolean mostrarAgregarRegistro) throws IOException{
            int opciones = 1;
            while(opciones != 0){
                System.out.println("------------MENU-------------");
	        System.out.println(" 1. --------- Agregar entidad");
	        System.out.println(" 2. ------- Modificar entidad");
		System.out.println(" 3. -------- Listar entidades");
                if(mostrarAgregarRegistro){
                    System.out.println(" 4. ------- Agregar registros");
                }
                System.out.println(" 5. --- Borrar bases de datos");
		System.out.println(" 0 -------------------- Salir");
                System.out.println(" Elija su opcion: ");
                opciones = sc.nextInt();
                switch(opciones){
                    case 0:
                        System.out.println(" ---------Saliendo--------");
                        break;
                    case 1:
                        if(agregarEntidad()){
                            System.out.println(" Entidad agregada con exito");
                            mostrarAgregarRegistro = true;
                        }
                        break;
                    case 2:
                        int continuar = 0;
                        System.out.println(" ¿Esta seguro de modificar los archivos de base de datos? ");
                        System.out.println(" Presione 1 para continuar de lo contrario cualquier numero para cancelar. Esta accion no es reversible. ");
                        continuar = sc.nextInt();
                        if(continuar == 1){
                            cerrarArchivo();
                            System.out.println(" Ingrese el nombre de la entidad a modificar ");
                            if(borrarArchivos()){
                                listaEntidades = null;
				listaEntidades = new ArrayList<>();
				mostrarAgregarRegistro = false;
				System.out.println(" Archivos borrados. ");
                                System.out.println(" Actualizando :D ");
                            }
                        }
                        break;
                    case 3:
                        if(listaEntidades.size() > 0){
                            int temporalInt = 0;
                            System.out.println(" Desea imprimir los detalles: ");
                            System.out.println(" Si, presione 1. No, presione 0.");
			    temporalInt = sc.nextInt();
                            if(temporalInt == 1){
                                for(Entidad entidad : listaEntidades){
                                    mostrarEntidad(entidad);
                                }
                            }else{
                                for(Entidad entidad : listaEntidades){
                                    System.out.println(" Indice: " + entidad.getIndice());
				    System.out.println(" Nombre: " + entidad.getNombre());
				    System.out.println(" Cantidad de atributos: " + entidad.getCantidad());
                                }
                            }
                        }else{
                            System.out.println(" No hay entidades registradas. ");
                        }
                        break;
                    case 4:
                        int indice = 0;
                        while(indice < 1 || indice > listaEntidades.size()){
                            for(Entidad entidad : listaEntidades){
                                System.out.println(entidad.getIndice() + " -------- " + entidad.getNombre());
                            }
                            System.out.println(" Seleccione la entidad que desea trabajar. ");
			    indice = sc.nextInt();
                        }
                        iniciar(indice);
                        break;
                    case 5:
                        int confirmar = 0;
                        System.out.println(" ¿Esta seguro de borrar los archivos de base de datos? ");
                        System.out.println(" Presione 1 para continuar de lo contrario cualquier numero para cancelar. Esta accion no es reversible. ");
                        confirmar = sc.nextInt();
                        if(confirmar == 1){
                            cerrarArchivo();
                            if(borrarArchivos()){
                                listaEntidades = null;
				listaEntidades = new ArrayList<>();
				mostrarAgregarRegistro = false;
				System.out.println(" Archivos borrados. ");
                                System.out.println(" Actualizando :D ");
                            }
                        }
                        break;
                    default:
                        System.out.println(" Opcion invalidad. :(");
                }
            }
        }
        
        private void cerrarArchivo(){
            if (archivo != null){
               try{
                   archivo.close();
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
		System.out.println("Bienvenido (a)");
		Atributo at = entidad.getAtributos().get(0);
                do{
                    try{
			System.out.println(" 1. ---------------------\t\tAgregar");
			System.out.println(" 2. ----------------------\t\tListar");
			System.out.println(" 3. ----------------------\t\tBuscar");
			System.out.println(" 4. -------------------\t\tModificar");
			System.out.println(" 0. ---\t\tRegresar al menu anterior");
                        System.out.println(" Seleccione su opcion: ");
			opcion = sc.nextInt();
                        
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
                                System.out.println(" Se hara la busqueda en la primera columna ");
			        System.out.println(" Ingrese " + at.getNombre().trim() + " a buscar");
				// sc.nextLine();
				// encontrarRegistro(carne);
                                break;
                            case 4:
                                System.out.println(" Ingrese el carne a modificar: ");
				// carne = sc.nextInt();
				// sc.nextLine();
				// modificarRegistro(carne);
                                break;
                            default:
                                System.out.println(" Opcion invalida. :(");
                                break;
                        }
                    }catch(Exception e){  // capturar cualquier excepcion que ocurra
                        System.out.println("Error: " + e.getMessage());
                    }
                }while(opcion != 0);
                archivo.close();
            }catch(Exception e){ // capturar cualquier excepcion que ocurra
                System.out.println("Error: " + e.getMessage());
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
		    System.out.println(" Ingrese " + atributo.getNombre().trim());
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
                                            System.out.println(" La longitud de " + atributo.getNombre().trim() + " no es valida [1 - " + atributo.getLongitud() + "]");
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
                                        System.out.println("Formato de fecha: " + formatoFecha);
					temporalString = sc.nextLine();
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
                                            System.out.println(" Solo se permite un caracter. ");
                                        }
                                    }while(longitud < 1 || longitud > 1);
                                    byte caracter = (byte) temporalString.charAt(0);//charAr implementó un método que recibe el índice o posición del carácter deseado en la cadena.
				    archivo.writeByte(caracter);
				    break;
                            }
                            valido = true;
                        }catch(Exception e){
                            System.out.println(" Error " + e.getMessage() + " al capturar tipo de dato, vuelva a ingresar el valor: ");
                            sc.nextLine();
                        }
                    }// end while
                }// end for
                archivo.write("\n".getBytes()); // cambio de linea para que el siguiente registro se agregue abajo
		resultado = true;
            }catch(Exception e){
                resultado = false;
		System.out.println(" Error al agregar el registro " + e.getMessage());
            }
            return resultado;
        }
        
        public void listarRegistros(Entidad entidad) throws IOException{
            try{
                long longitud = archivo.length(); //length se obtiene la longitud de una cadena
                if(longitud <= 0){
                   System.out.println(" No hay registros.");
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
                System.out.println(" Error: " + e.getMessage());
            }
        }
        
        public void encontrarRegistros(int carne) throws IOException{
            try{
                long longitud = archivo.length();//length se usa para obtener la longitud de una cadena Java
                if(longitud <= 0){
                    System.out.println("No hay registros");
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
			System.out.println(" Carne: " + al.getCarne());
			System.out.println(" Nombre: " + al.getNombre());
			System.out.println(" Fecha de nacimiento: " + dateToString(al.getFechaNacimiento())); //ToString se utiliza para convertir a String (es decir, a una cadena de texto) cualquier objeto Java.

			bndEncontrado = true;
			// si el registro se ha encontrado entonces salir del ciclo
			break;
                    }
                    // restar los bytes del registro leido
		    longitud -= totalBytes;
                }
                // solo si el registro no se encontro imprimir un mensaje
                if(!bndEncontrado){  // esto es equivalente a (bndEncontrado == false)
                    System.out.println(" No se encontro el carne indicado, por favor verifique. ");
                }
        }catch(Exception e){
            System.out.println(" Error: " + e.getMessage());
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
                        System.out.println(" Si no desea modificar el campo presione enter");
			System.out.println(" Ingrese el nombre");
			String tmpStr = "";
			int longitud2 = 0;
			long posicion;
                        do{
                           tmpStr = sc.nextLine();
			   longitud2 = tmpStr.length();//length se usa para obtener la longitud de una cadena 
                           if(longitud2 > 50){
                               System.out.println(" La longitud del nombre no es valida [1 - 50]");
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
                        System.out.println(" Ingrese la fecha (" + formatoFecha + ")");
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
                            System.out.println(" El registro fue modificado correctamente, los nuevos datos son:");
                        }
                        System.out.println(" Carne: " + al.getCarne());
			System.out.println(" Nombre: " + al.getNombre());
			System.out.println(" Fecha de nacimiento: " + dateToString(al.getFechaNacimiento()));
			bndEncontrado = true; // si el registro se ha encontrado entonces salir del ciclo
			break;
                    }
                    registros++;
		    // restar los bytes del registro leido
	            longitud -= totalBytes;
                }
                // solo si el registro no se encontro imprimir un mensaje
		if (!bndEncontrado){// esto es equivalente a (bndEncontrado == false)
                    System.out.println(" No se encontro el carne indicado, por favor verifique.");
                }
            }catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        public Date stringToDate (String stringFecha){
            Date date = null;
            try{
                date = format.parse(stringFecha);
            }catch(Exception e){
                date = null;
		System.out.println(" Error en fecha: " + e.getMessage());
            }
	    return date;
        }
        
        public String dateToString(Date date) {
	    String stringFecha;
	    stringFecha = format.format(date);
	    return stringFecha;
	}
        
        
}
