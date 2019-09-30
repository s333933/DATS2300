package Assignments.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;


/*
    Andreas Hartmann s333759
    Daniel Hansen s333748
    Felix Leypoldt s333743
    Herman Rustad s333769
    Paul Høglend s333733
*/



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }


    // Konstruktør med en liste av elementer som parameter
    public DobbeltLenketListe(T[] a) {
        // Kaster unntak for null tabell
        if(a == null){ throw new NullPointerException("Tabell a er null!"); }

        a = fjernNullVerdier(a);             // Fjerner alle null-verdier
        if(a.length == 0) { return; }         // Hopper ut av metoden hvis listen er tom

        Node<T> aktuell = new Node<>(a[0]);  // Opretter første node, og gir den verdi
        antall++;                            // Opdaterer antall noder
        hode = aktuell;                      // Setter hode lik første node

        for (int i = 1; i < a.length; i++) {
            Node<T> neste = new Node<>(a[i]);    // Opretter ny node
            antall++;                            // Opdaterer antall noder
            aktuell.neste = neste;               // Setter aktuell sin neste peker
            neste.forrige = aktuell;             // Setter neste sin forrige peker
            aktuell = neste;                     // Setter aktuell lik neste
        }
        hale = aktuell;                          // Setter halen lik siste node
    }

    //Hjelpemetode
    private T[] fjernNullVerdier (T[] a) {
        int antallNullverdier = 0;              // Teller for antall nullverdier

        for(T verdi : a){
            if(verdi == null){                  // Tester hvor mange verdier som er null
                antallNullverdier++;
            }
        }

        if(antallNullverdier == 0){ return a; }     // Returnerer oprinnelig liste hvis det ikke er noe nullverdier

        T[] b = (T[]) new Object[a.length - antallNullverdier];     // Opretter returlisten

        int j = 0;  // indeks for returlisten

        // Legger til alle verdiene som ikke er null til returlisten
        for(int i = 0; i < a.length; i++) {
            if(a[i] != null){
                b[j] = a[i];
                j++;
            }
        }

        return b;
    }

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(antall, fra, til);            // Sjekker at fra og til argumentene er innenfor listen sin lengde
        Node<T> aktuell = hode;                      // Lagerer hodet
        for(int i = 0; i < fra; i++) {
            aktuell = aktuell.neste;                 // Flytter aktuell fra hode til fra
        }
        T[] sublisteInput = (T[]) new Object[til-fra]; // Oppretter sublisteInput

        int indeks = 0;

        for(int i = fra; i < til; i++) {
            sublisteInput[indeks] = aktuell.verdi;        // Fyller sublisteInput med verdiene til [fra-til> sine noder
            aktuell = aktuell.neste;
            indeks++;
        }
        DobbeltLenketListe<T> subliste = new DobbeltLenketListe<>(sublisteInput); // Oppretter sublisten, og fyller den
        return subliste;
    }

    //Hjelpemetode
    private void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0) {                                // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }
        if (til > antall) {                        // til er utenfor tabellen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");
        }
        if (fra > til) {                               // fra er større enn til
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if(antall > 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean leggInn(T verdi) {
        verdi = Objects.requireNonNull(verdi, "Null-verdier er ikke tillatt");    // Tester for null-verdi

        //legger inn en ny node bakerst
        if(antall > 0){
            Node<T> ny = new Node<>(verdi);                 // Opretter ny node
            hale.neste = ny;                                // Setter hale sin neste-peker lik ny
            ny.forrige = hale;                              // Setter ny sin forrige-peker lik halen
            hale = ny;                                      // Setter hale lik ny node

        } else {   // Hvis det ikke fins noen noder fra før
            Node<T> ny = new Node<>(verdi);                 // Opretter ny node
            hode = ny;                                      // Setter hode lik ny node
            hale = ny;                                      // Setter hale lik ny node
        }

        // Legger 1 til antall og endringer variablene
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public boolean inneholder(T verdi) {
        int indeks = indeksTil(verdi);
        if(indeks == -1) {
            return false;
        }
        return true;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);      // Sjekker om indeksen er ugyldig
        Node<T> returNode = finnNode(indeks);        // Finner noden til indeks og putter verdien inn i en variabel
        return returNode.verdi;                      // Returnerer noden til indeks

    }

    // Hjelpemetode
    private Node<T> finnNode(int indeks) {
        Node<T> returNode;

        if(indeks < antall/2) {                 // Hvis indeksen er mindre enn antall / 2, søker fra hode
            returNode = hode;
            int i = 0;

            // Setter returnNode lik neste verdi helt til indeksen stemmer
            while (i < indeks) {
                returNode = returNode.neste;
                i++;
            }
        } else {                                // Hvis indeks er >= antall / 2, søker fra hale
            returNode = hale;
            int i = antall-1;

            // Setter returnNode lik forrige verdi helt til indeksen stemmer
            while (i > indeks) {
                returNode = returNode.forrige;
                i--;
            }
        }

        return returNode;
    }

    @Override
    public int indeksTil(T verdi) {
        if(antall == 0 || verdi == null) {
            return -1;
        }
        Node<T> aktuell = hode;
        for(int i = 0; i < antall; i++) {
            if(aktuell.verdi.equals(verdi)) {
                return i;
            }
           aktuell = aktuell.neste;
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        //Om nyverdi er null kastes et unntak
        if(nyverdi == null) { throw new NullPointerException("Nyverdi kan ikke være null"); }
        indeksKontroll(indeks, false);          // Sjekker om indeksen er ugyldig
        Node<T> node = finnNode(indeks);                 // Finner noden til indeks og putter verdien inn i en variabel
        T gammelVerdi = node.verdi;                      // Lagrer nodens nåværende veri
        node.verdi = nyverdi;                            // Oppdaterer noden sin verdi
        endringer++;                                     // Øker antall endringer med 1
        return gammelVerdi;                              // Returnerer nodens verdi før den ble oppdatert
    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public void nullstill() {
        throw new NotImplementedException();
    }

    //TODO: Metoden må gjøres raskere
    @Override
    public String toString() {
        StringBuilder utskrift = new StringBuilder("[");
        Node<T> aktuell  = hode; //starter på hode
        /*Løper gjennom listen, så lenge aktuells nestepekeren er ulik null,
          og legger verdien til aktuell node til utskriftstrengen
         */
        while(aktuell != null) {
            if(aktuell == hale){
                utskrift.append(aktuell.verdi);
            }else{
                utskrift.append(aktuell.verdi + ", ");
            }
            aktuell = aktuell.neste;
        }
        utskrift.append("]");
        return utskrift.toString();
    }

    //TODO: Metoden må gjøres raskere
    public String omvendtString() {
        StringBuilder utskrift = new StringBuilder("[");
        Node<T> aktuell = hale; //starter på halen

        /*Løper gjennom listen, så lenge aktuells forrigepekeren er ulik null,
          og legger verdien til aktuell node til utskriftstrengen
         */
        while(aktuell != null) {
            if(aktuell == hode){
                utskrift.append(aktuell.verdi);
            }else{
                utskrift.append(aktuell.verdi + ", ");
            }
            aktuell = aktuell.forrige;
        }
        utskrift.append("]");
        return utskrift.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // denne starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;  // denne koden skal ikke endres!
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){
            throw new NotImplementedException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }


    //Todo: Fjern før innlevering */
    //Brukes kun for testing
    public Node<T> getHodeForrige() {
        return hode.forrige;
    }

    public Node<T> getHaleNeste() {
        return hale.neste;
    }

    public Node<T> getHodeNeste() {
        return hode.neste;
    }

    public Node<T> getHaleForrige() {
        return hale.forrige;
    }

    public T getHaleForrigeVerdi() {
        return hale.forrige.verdi;
    }

    public T getHodeNesteVerdi() {
        return hode.neste.verdi;
    }

    public T getHaleVerdi() {
        return hale.verdi;
    }

    public T getHodeVerdi() {
        return hale.verdi;
    }

    public Node<T> finnNodeTest(int indeks) {
        return finnNode(indeks);
    }

} // class DobbeltLenketListe


