package es.aragon.midas.util;

import es.aragon.midas.logging.Logger;

/**
 * Clase que encapsula funciones útiles del CIA
 * @author carlos
 *
 */
public class CIA  {
    private Logger log;
    
    public CIA() {
        log = new Logger();
    }
    
    /**
     * Dice si una cadena es un CIA valido
     * @parameter cia Cadena a evaluar
     * @return True si la cadena es un CIA valido
     */
    public boolean isCIA(String cia) {
        char [] charArray = cia.toCharArray();
        if (charArray[0] != 'A' || charArray[1] != 'R') return false;
        if (charArray.length != 12) return false;
        String letraCIA = this.getLetraCIA(new String(charArray, 2, 9));
        if (letraCIA == null || !letraCIA.equals(String.valueOf(charArray[11]))) return false;
        
        return true;
    }
    
    /**
     * Devuelve la letra de control asociada a un número
     * @param numero en formato String
     * @return la letra del cia
     */
    public String getLetraCIA(String numero) {
        char [] charArray = numero.toCharArray();
        int res = -1;
        try {
            if (charArray.length != 9) throw new ArrayIndexOutOfBoundsException();
            res = Integer.parseInt(new Character(charArray[0]).toString()) * 5 + 
                  Integer.parseInt(new Character(charArray[1]).toString()) * 3 +
                  Integer.parseInt(new Character(charArray[2]).toString()) * 2 +
                  Integer.parseInt(new Character(charArray[3]).toString()) * 1 +
                  Integer.parseInt(new Character(charArray[4]).toString()) * 5 +
                  Integer.parseInt(new Character(charArray[5]).toString()) * 3 +
                  Integer.parseInt(new Character(charArray[6]).toString()) * 2 +
                  Integer.parseInt(new Character(charArray[7]).toString()) * 1 +
                  Integer.parseInt(new Character(charArray[8]).toString()) * 5;
            res = res % 17;
        } catch (NumberFormatException nfe) {
            log.error("El dato introducido no es un numero");
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            log.error("El dato introducido no tiene 9 dígitos");
        }
        
        return "PCAFNVBDREKXLSTZJ".substring(res,res+1);
/*
        switch (res) {
            case 0: return "P";
            case 1: return "C";
            case 2: return "A";
            case 3: return "F";
            case 4: return "N";
            case 5: return "V";
            case 6: return "B";
            case 7: return "D";
            case 8: return "R";
            case 9: return "E";
            case 10: return "K";
            case 11: return "X";
            case 12: return "L";
            case 13: return "S";
            case 14: return "T";
            case 15: return "Z";
            case 16: return "J";
            default: return null; 
        }*/
    }
    
    /**
     * Devuelve la letra de control CIA asociada a un número
     * @param numero
     * @return la letra del CIA
     */
    public String getLetraCIA(int numero) {
        return this.getLetraCIA(new Integer(numero).toString());
    }
    
    /**
     * Construye el CIA completo a partir de un número en formato String
     * @param numero en formato String
     * @return el CIA completo
     */
    public String getCIA(String numero) {
        return "AR"+numero+this.getLetraCIA(numero);
    }
    
    /**
     * Construye el CIA completo a partir de un número
     * @param numero
     * @return el CIA completo
     */
    public String getCIA(int numero) {
        return this.getCIA(new Integer(numero).toString());
    }
    
    /**
     * Función de ofuscado de un CIA
     * @param CIA
     * @return el código ofuscado
     * TODO implementar el método obfuscateCIA
     */
    String obfuscateCIA(String CIA) {
    	return "";
    }
}