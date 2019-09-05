package Assignments.Oblig1;

import java.util.NoSuchElementException;

public class Oblig1 {

    /*
    Andreas Hartmann s333759
    Daniel Hansen s333748
    Felix Leypoldt s333743
    Herman Rustad s333769
    Paul Høglend s333733
     */



    public static int maks(int [] values ) {
        if(values.length <= 0){
            throw new NoSuchElementException("Tabellen er tom");
        }
        for(int i = 0; i < values.length - 1; i++){
            for(int j = i + 1; j < values.length; j++){
                int bytt = values[i];
                if(values[i] > values[j]){
                    values[i] = values[j];
                    values[j] = bytt;
                }
            }
        }
       return values[values.length -1];
    }

    public static int ombyttinger(int [] values ) {
        if(values.length <= 0){
            throw new NoSuchElementException("Tabellen er tom");
        }
        int antall = 0;
        for(int i = 0; i < values.length - 1; i++){
            for(int j = i + 1; j < values.length; j++){
                int bytt = values[i];
                if(values[i] > values[j]){
                    values[i] = values[j];
                    values[j] = bytt;
                    antall++;
                }
            }
        }
        return antall;
    }

    //TODO: implementer metodene
    public static int antallUlikeSortert( int [] values ) {
        return 0;
    }


    /**Itererer gjennom en tabell, og finner ut av hvor mange unike tall det er i den*/

    public static int antallUlikeUsortert(int [] values) {

        int antallUlike = 0;

        //Går gjennom alle verdiene i arrayet values
        for (int i = 0; i < values.length; i++) {

                int j;
                //Om i og j ikke har samme index (j < i) hopper den inn i if-setningen
                for (j = 0; j < i; j++) {
                    //Om verdiene på index i og j har samme verdi går den ut av for-løkken
                    if (values[i] == values[j]) {
                        break;
                    }
                }
                //Om indexen til i og j er like øker antallet ulike tall
                if (i == j) {
                    antallUlike++;
                }
            }
        return antallUlike;
    }

    public static void delsortering(int [] values) { }


    /* Metode som tar inn en liste med 'char'-elementer og roterer alle elementene 1 plass mot høyre
    * eks. char[] a = {'A', 'B', 'C', 'D'}
    * rotasjon(a)
    * a == {'D', 'A', 'B', 'C'} */
    public static void rotasjon(char[] a) {

        // Tester om listen har mer enn 1 element
        if(a.length > 1) {
            int siste_idx = a.length - 1;
            char temp = a[siste_idx];                       //Tar vare på verdien til siste element

            for (int i = siste_idx - 1; i >= 0; i--) {      //Looper gjennom listen fra høyre til venstre
                a[i + 1] = a[i];                            //Flytter elementene en plass til høyre
            }

            a[0] = temp;                                    //Setter første element i listen lik a[siste_idx]
        }
    }

    //TODO: Gjøre metoden mer effektiv
    public static void rotasjon(char [] values, int k) {
        int i = 0;
        if(k > 0) {     // Tester om k er positiv

            while (i < k) {
                rotasjon(values);
                i++;
            }
        }else if(k < 0) {   // Tester om k er negativ

            while (i > k) {
                int siste_idx = values.length - 1;
                char temp = values[0];

                for (int j = 1; j <= siste_idx; j++) {
                    values[j - 1] = values[j];
                }

                values[siste_idx] = temp;
                i--;
            }
        }

    }

    public static String flett(String s1, String s2) {
        return "";
    }

    public static String flett(String... s) {return "";}

    public static int [] indekssortering(int [] values) {
        return null;
    }

    public static int [] tredjeMin(int [] values)  {
        return null;
    }

    public static boolean inneholdt(String s1, String s2) {
        if(s1.length() == 0) { //tomme strenger
            return true;
        }
        String str1 = s1.toUpperCase();
        String str2 = s2.toUpperCase();
        char [] chars1 = str1.toCharArray();
        char [] chars2 = str2.toCharArray();
        
        return false;
    }


}
