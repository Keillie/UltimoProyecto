
package proyecto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alumno {
    private String nombre;
    private byte[] bytesNombre;
    //private final formato para la fecha.
    private final DateFormat formatoFecha = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy"); 
    private Date fechaNacimiento;
    private byte[] bytesFechaNacimiento;
    private int carne;
    private String telefono;
    private byte[] bytesTelefono;
    private double peso;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        bytesNombre = new byte[50];//Arreglo de bytes con una longitud de 50.
        //convierte cada caracter a byte y lo agrega al arreglo.
        for(int i = 0; i < nombre.length(); i++){ //length se obtiene la longitud de una cadena
            bytesNombre[i] = (byte)nombre.charAt(i); //charAt se implento un metodo que recibe el indice o posicion
        }
    }

    public byte[] getBytesNombre() {
        return bytesNombre;
    }

    public void setBytesNombre(byte[] bytesNombre) {
        this.bytesNombre = bytesNombre;
        nombre = new String(bytesNombre);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        String stringFecha = formatoFecha.format(fechaNacimiento);
        bytesFechaNacimiento = stringFecha.getBytes();
    }

    public byte[] getBytesFechaNacimiento() {
        return bytesFechaNacimiento;
    }

    public void setBytesFechaNacimiento(byte[] bytesFechaNacimiento) throws ParseException {
        this.bytesFechaNacimiento = bytesFechaNacimiento;
        String stringFecha = new String(bytesFechaNacimiento);
        this.fechaNacimiento = formatoFecha.parse(stringFecha);
    }

    public int getCarne() {
        return carne;
    }

    public void setCarne(int carne) {
        this.carne = carne;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        bytesTelefono = new byte[15];//Arreglo de bytes con una longitud de 15.
        //convierte cada caracter a byte y lo agrega al arreglo.
        for(int i = 0; i < telefono.length(); i++){ //length se obtiene la longitud de una cadena
            bytesTelefono[i] = (byte)telefono.charAt(i); //charAt se implento un metodo que recibe el indice o posicion
        }
    }

    public byte[] getBytesTelefono() {
        return bytesTelefono;
    }

    public void setBytesTelefono(byte[] bytesTelefono) {
        this.bytesTelefono = bytesTelefono;
        telefono = new String(bytesTelefono);
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
    
      
}
