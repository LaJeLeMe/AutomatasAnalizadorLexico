package model;

/**
 * Hecho con <3 Por:
 * @author GarciHard
 */

public class LexemaVO {
    private String lexema = "";
    private String nombre = "";
    private String token = "";
    
    public LexemaVO() {};
    
    public String getLexema() {return lexema;}
    public String getNombre() {return nombre;}
    public String getToken() {return token;}
    
    public void setLexema(String lexema) {this.lexema = lexema;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setToken(String token) {this.token = token;}
}