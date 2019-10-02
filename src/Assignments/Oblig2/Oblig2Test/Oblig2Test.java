package Assignments.Oblig2.Oblig2Test;

import Assignments.Oblig2.DobbeltLenketListe;
import Assignments.Oblig2.Liste;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Oblig2Test {

    @Test
    void oppgave1Test() {
        String[] s = {"Ole", null, "Per", "Kari", null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() + " " + liste.tom());
    }

    // Tester konstruktør med et array som argument
    @Test
    void instansTest() {
        int antallFeil = 0;

        int[] a = {1, 2, 3, 4, 5};

        Integer[] aInteger = new Integer[a.length];
        for(int i = 0; i < a.length; i++) {
            aInteger[i] = Integer.valueOf(a[i]);
        }

        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(aInteger);

        //Tester basistilfellet
        if(liste.antall() != 5){
            antallFeil++;
            System.out.println("Feil i antall variabelen, Expected: " + 5 + " Actual: " + liste.antall());
        }

        Integer[] bInteger = new Integer[] {1, null, null, 2, 3};
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>(bInteger);

        //Tester at det blir lagd riktig antall noder når listen inneholder null-verdier
        if(liste2.antall() != 3){
            antallFeil++;
            System.out.println("Feil i antall variabelen, Expected: " + 3 + " Actual: " + liste2.antall());
        }

        //Tester om hode sin forrige-peker == null
        if(liste2.getHodeForrige() != null) {
            antallFeil++;
            System.out.println("Feil: Hode sin forrige-peker skal være null");
        }


        //Tester om hale sin neste-peker == null
        if(liste2.getHaleNeste() != null) {
            antallFeil++;
            System.out.println("Feil: Hale sin forrige-peker skal være null");
        }


        Integer[] cInteger = new Integer[] {1};
        DobbeltLenketListe<Integer> liste3 = new DobbeltLenketListe<>(cInteger);

        //Tester med 1 verdi som ikke er null
        if(liste3.getHodeNeste() != liste3.getHaleForrige()){
            antallFeil++;
            System.out.println("Feil: hodet sin neste-peker og hale sin forrige-peker er ikke like");
        }

        Integer[] dInteger = new Integer[] {};
        DobbeltLenketListe<Integer> liste4 = new DobbeltLenketListe<>(dInteger);


        assertEquals(0, antallFeil, "Testen har: " + antallFeil + " feil");
    }

    @Test
    void fjernNullVerdierTest() {

        Integer[] bInteger = new Integer[] {1, null, null, 2, 3};
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>(bInteger);

    }


    @Test
    void StringTest() {
        String[] s1 = {}, s2 = {"A"}, s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);

        System.out.println(l1.toString() + "​ ​" + l2.toString()
                + "​ ​"+ l3.toString() +"​ ​"+ l1.omvendtString() +"​ ​"
                + l2.omvendtString() + "​ ​"+ l3.omvendtString());
    }


    @Test
    void leggInnTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        liste.leggInn(4);

        // Tester om hale sin verdi er lik det som blir lagt til
        assertEquals(4, liste.getHaleVerdi());

        // Tester om hale sin forrige verdi er lik nest siste node
        assertEquals(3, liste.getHaleForrigeVerdi());

        // Tester om hale sin neste verdi er null
        assertEquals(null, liste.getHaleNeste());


        Integer[] liste3Integer = new Integer[] {};
        DobbeltLenketListe<Integer> liste3 = new DobbeltLenketListe<>(liste3Integer);
        liste3.leggInn(1);

        // Tester om hode og hale peker på samme node når det bare er en node
        assertEquals(1, liste3.getHaleVerdi());
        assertEquals(1, liste3.getHodeVerdi());


        DobbeltLenketListe<Integer> liste4 = new DobbeltLenketListe<>();
        System.out.println(liste4.toString() + " " + liste4.omvendtString());
        for (int i = 1; i <= 3; i++) {
            liste4.leggInn(i);
            System.out.println(liste4.toString() + " " + liste4.omvendtString());
        }

    }

    //Oppgave 3a

    @Test
    void finnNodeTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3, 4, 5, 6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        assertEquals(liste.getHodeNeste(), liste.finnNodeTest(1));
        System.out.println(liste.getHaleForrigeVerdi());
        System.out.println(liste.getHaleForrigeVerdi());
        assertEquals(liste.getHaleForrige(), liste.finnNodeTest(4));
    }

    @Test
    void hentTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3, 4, 5, 6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        assertEquals(3, liste.hent(2), "hente index 2");
        assertEquals(1, liste.hent(0), "hente index 0");
        assertEquals(6, liste.hent(5), "hente index 5");
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            liste.hent(-3);
        });
    }

    @Test
    void oppdaterTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3, 4, 5, 6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        assertEquals(2, liste.oppdater(1,3));
        assertEquals(1, liste.oppdater(0, 2));
        assertEquals(6, liste.oppdater(5,3));
        assertEquals("[2, 3, 3, 4, 5, 3]", liste.toString());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            liste.oppdater(-3, 2);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            liste.oppdater(3, null);
        });
    }

    //Oppgave 3b

    @Test
    void sublisteTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3, 4, 5, 6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        assertEquals("[2, 3, 4]", liste.subliste(1,4).toString());
        assertEquals("[1]" ,liste.subliste(0, 1).toString());
        assertEquals("[]", liste.subliste(0,0).toString());
        assertEquals("[4, 5, 6]", liste.subliste(3,6).toString());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            liste.subliste(-2,5);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            liste.subliste(3,9);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            liste.subliste(4,3);
        });
    }


    @Test
    void hastighet() {
        DobbeltLenketListe liste = new DobbeltLenketListe<>(new Integer[]{null, 1, null, 2, null});
        for (int i = 0; i < 20000; i++) liste.leggInn(i);
        long tid = System.currentTimeMillis();  // Setter starttidspunkt
        liste.toString();                       // Kjører toString-metoden
        tid = System.currentTimeMillis() - tid; // Finner diferansen mellom start- og slutt-tidspunkt

        if (tid > 20) {
            System.out.println("Dette inntreffer evt på MacOS");
            System.out.println("Oppgave 2n: Dette (" + tid + " ms) gikk altfor sakte!");
            System.out.println(" Har du kodet toString slik det ble bedt om?");
        }
        System.out.println("Tid: " + tid);
    }

    @Test
    void indeksTilTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3, 4, 5, 6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        assertEquals(2, liste.indeksTil(3));
        System.out.println("Posisjon: "+ liste.indeksTil(3));
        //Siste posisjon
        assertEquals(5, liste.indeksTil(6));
        System.out.println( "Posisjon: "+ liste.indeksTil(6));
        //Første posisjon
        assertEquals(0, liste.indeksTil(1));
        System.out.println( "Posisjon: "+ liste.indeksTil(1));

        //Lister med duplikater
        Integer[] listeInteger2 = new Integer[] {10, 3, 2, 3, 4, 5, 3, 6};
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>(listeInteger2);
        //første posisjon
        assertEquals(0, liste2.indeksTil(10));
        System.out.println("Posisjon: "+ liste2.indeksTil(10));
        //nok en unik verdi
        assertEquals(2, liste2.indeksTil(2));
        System.out.println("Posisjon: "+  liste2.indeksTil(2));
        //Duplikatverdi
        assertEquals(1, liste2.indeksTil(3));
        System.out.println("Posisjon: "+ liste2.indeksTil(3));
    }

    @Test
    void inneholderTest() {
        Integer[] listeInteger = new Integer[] {1, 2, 3, 4, 5, 6, 7};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);

        assertEquals(true, liste.inneholder(3));
        System.out.println("inneholder: "+ liste.inneholder(3));


        assertEquals(false, liste.inneholder(10));
        System.out.println("inneholder: "+ liste.inneholder(10));

        assertEquals(false, liste.inneholder(null));
        System.out.println("inneholder: "+ liste.inneholder(null));


    }

    //Oppgave 8

    @Test
    void nextTest() {
        Integer[] listeInteger = new Integer[] {1,2,3,4,5,6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        Iterator<Integer> i = liste.iterator();
        assertTrue(i.hasNext());
        i.next();
        assertEquals(2, i.next());
        Integer[] listeIntegerTom = new Integer[] {};
        DobbeltLenketListe<Integer> listeTom = new DobbeltLenketListe<>(listeIntegerTom);
        Iterator<Integer> ii = listeTom.iterator();
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            ii.next();
        });
        liste.oppdater(3,2);
        Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            i.next();
        });
    }

    @Test
    void IteratorTest() {
        Integer[] listeInteger = new Integer[] {1,2,3,4,5,6};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>(listeInteger);
        Iterator<Integer> i = liste.iterator(2);
        assertEquals(true, i.hasNext());
        assertEquals(3, i.next());
    }

    @Test
    void sorterTest() {
        String [] navn = {"Lars","Anders","Bodil","Kari","Per","Berit"};
        Liste<String> liste1 = new DobbeltLenketListe<>(navn);
        DobbeltLenketListe.sorter(liste1, Comparator.naturalOrder());
        System.out.println("----------------------------");
        Integer [] tall = {2,6,4,7,9,16,16};
        Liste<Integer> liste2 = new DobbeltLenketListe<>(tall);
        DobbeltLenketListe.sorter(liste2, Comparator.naturalOrder());
    }

}
