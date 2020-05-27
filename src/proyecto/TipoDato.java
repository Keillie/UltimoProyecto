
package proyecto;

public enum TipoDato { //enum(enumerado) una clase especial que limita la creacion de objetos a los especificados explicitamente.
    INT(1), LONG(2), STRING(3), DOUBLE(4), FLOAT(5), DATE(6), CHAR(7);
    
    private final int value; //indica que a esa variable solo se le puede asignar un valor u objeto una única vez. 
    private TipoDato(int value){
        this.value = value;
    }
     public int getValue(){
         return value;
     }
}
