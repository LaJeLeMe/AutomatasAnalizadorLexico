package control;

import model.LexemaVO;

/**
 * Hecho con <3 Por:
 * @author GarciHard
 */

public class ExAutomata {
    
    public ExAutomata() {}
    
    public static boolean EsValido(String s, char[][] tabla, LexemaVO lex) {
        int estado = 0 + 1;
        char[] array = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            boolean encuentra = false;
            for (int j = 0; j < tabla.length - 2; j++) {//no toma en cuenta el caracter 'P' ni N
                if (array[i] == tabla[j][0]) {
                    if (tabla[j][estado] != 'x') {
                        estado = Integer.parseInt(tabla[j][estado] + "") + 1;
                        encuentra = true;
                        break;
                    }
                }
            }
            boolean aux = tabla[tabla.length - 2][estado] != 'v' & i == array.length - 1;
            if (!encuentra || aux) {
                TokensError(estado, lex);
                return false;
            }
        }
        //si todo esta vien mandara el numero de token
        switch (tabla[tabla.length - 1][estado]) {
            case '3':
                lex.setNombre("Comentario");
                lex.setToken("54");
                break;
            case '4':
                lex.setNombre("Variable");
                lex.setToken("50");
                break;
            case '5':
                lex.setNombre("Numero");
                lex.setToken("51");
                break;
            case '8':
                lex.setNombre("Cadena");
                lex.setToken("53");
                break;
            case '9':
                lex.setNombre("Doble");
                lex.setToken("52");
                break;
        }
        return true;
    }

    private static void TokensError(int estado, LexemaVO lex) {
        switch (estado) {
            case 1:
            case 4:
                lex.setNombre("Variable no valida");
                lex.setToken("Error 100: Variable no valida");
                break;
            case 2:
                lex.setNombre("Comentario no valido");
                lex.setToken("Error 101: Comentario no valido");
                break;
            case 6:
                lex.setNombre("Numero no valido");
                lex.setToken("Error 102: Numero no valido");
                break;
            case 8:
                lex.setNombre("Cadena no valida");
                lex.setToken("Error 103: Cadena no valida");
                break;
            default:
                lex.setNombre("Error no identificado");
                lex.setToken("Error 104: Error no identificado");
                break;
        }
    }
}