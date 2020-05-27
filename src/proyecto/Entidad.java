
package proyecto;

import java.util.ArrayList;
import java.util.List;

public class Entidad {
    private int indice;
    private String nombre;
    private int cantidad;
    private long posicion; //posicion donde inician sus atributos
    private byte[] bytesNombre;
    private int bytes = 1; //inicia en uno que representa el cambio de linea
    
    private List<Atributo> atributos;   

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        bytesNombre = new byte[30]; //arreglo de bytes de longitud 30
	//convertir caracter por caracter a byte y agregarlo al arreglo
	for (int i = 0; i < nombre.length(); i++){//length se usa para obtener la longitud de una cadena Java
            bytesNombre[i] = (byte)nombre.charAt(i);//charAt se implementó un método que recibe el índice o posición del carácter deseado en la cadena.
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public long getPosicion() {
        return posicion;
    }

    public void setPosicion(long posicion) {
        this.posicion = posicion;
    }

    public byte[] getBytesNombre() {
        return bytesNombre;
    }

    public void setBytesNombre(byte[] bytesNombre) {
        this.bytesNombre = bytesNombre;
        nombre = new String(bytesNombre);
    }
    
    public void removeAtributo(Atributo atributo) {
        if (this.atributos != null) {
            if (this.atributos.size() > 0) {
                this.atributos.remove(atributo);
                this.cantidad = this.atributos.size();
	    }
        }
    }

    public int getBytes() {
        bytes = 1;
        for (Atributo atributo : atributos){
            bytes += atributo.getBytes();
        }
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
    }
    
    public void setAtributo(Atributo atributo) {
        if (this.atributos == null) {
            this.atributos = new ArrayList<>();
        }
        this.atributos.add(atributo);
        this.cantidad = this.atributos.size();
    }
}
