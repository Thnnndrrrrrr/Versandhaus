import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class UbayGrossanzeigenTimFork {
    // Klassenvariablen --> Zugriff aus jeder Methode heraus möglich
    static final int PRODUKTNUMMER_MILCH = 1;
    static final float MEHRWERTSTEUERSATZ_MILCH = 0.07f;
    static final float PREIS_MILCH = 2.59f;
    static final String PRODUKTNAME_MILCH = "Milch";
    static final String PRODUKTBESCHREIBUNG_MILCH = "Wer braucht schon eine Beschreibung? Die Milch ist weiß!";
    static int bestellMengeMilch = 0;
    static String placeholder = "";

    public static void main(String[] args) {
        java.util.Scanner scTemp = new java.util.Scanner(System.in);
        boolean isLoggedIn = false;
        int[] kundennummer = { 11111, 22222, 33333, 44444, 55555, 66666, 77777, 88888, 99999 };
        int kdnummerInput = 0;
        int neueKundennummer = 0;
        Random ran = new Random();

        willkommensbildschirm();
        waitEnter("Einloggen? Mit Enter bestätigen", scTemp);

        while (!isLoggedIn) {
            String input = waitEnter("Einloggen (E) oder neu registrieren (R)", scTemp);
            if (input.equals("E")) { /*
                                      * Hinweis: bei input=="E" vergleicht er nicht "E" mit dem Inhalt des Strings
                                      * input, sondern mit der Referenz auf den Speicher/ der Speicheradresse - das
                                      * verhält sich identisch für alle nicht primitiven Datentypen
                                      * Frage: Warum funktioniert das bei case ...?
                                      */
                try {
                    kdnummerInput = Integer.parseInt(waitEnter("Bitte geben Sie ihre Kundennummer ein: ", scTemp));
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
                if (inArray(kdnummerInput, kundennummer)) {
                    System.out.print("Herzlich Willkommen: " + kdnummerInput + "\n\n\n");
                    isLoggedIn = true;
                }
            } else if (input.equals("R")) {
                while (inArray(neueKundennummer = ran.nextInt(90000) + 10000, kundennummer)) {
                    // Durchsucht das alte Array nach der Kundennummer und weist zufällig eine neue
                    // zu
                }
                kundennummer = addToStringArray(kundennummer, neueKundennummer);
                System.out.print("Sie wurden registriert. Ihre Kundennummer lautet: " + neueKundennummer + "\n\n\n"
                        + "Bitte loggen sie sich jetzt ein.\n");
                isLoggedIn = true;
            }

        }
        waitEnter("Mit ENTER die Produktbeschreibung aufrufen", scTemp);
        productdataOutput();

        // Nachfrage nach Menge
        bestellMengeMilch = Integer.parseInt(waitEnter(
                "\nEs gibt nur Milch!\nWie viel möchten Sie bestellen? Zahl eingeben und mit Enter bestätigen",
                scTemp));

        // Preisberechnung
        float gesamtpreisTemp = gesamtpreis(bestellMengeMilch, PREIS_MILCH);
        System.out.println("Der Gesamtpreis ist " + gesamtpreisTemp + "€");
        // Steuerberechnung
        System.out.println(
                "Enthaltene Mehrwertsteuer: " + mehrwertsteuer(gesamtpreisTemp, MEHRWERTSTEUERSATZ_MILCH) + "€");

        // Bestellung auslösen
        waitEnter("Mit ENTER jetzt kostenpflichtig bestellen", scTemp);
        System.out.println("Vielen Dank für Ihren Einkauf " + kdnummerInput);
        scTemp.close();
    }

    /**
     * Berechnet den Gesamtpreis der Bestellung
     * 
     * @param anzahl Bestellmenge angeben
     * @param preis  Preis angeben
     * @return float
     */
    public static float gesamtpreis(int anzahl, float preis) {
        return (Math.round((anzahl * preis) * 100.0) / 100.0f);
    }

    /**
     * Berechnet die im Gesamtpreis enthaltene Mehrwertsteuer
     * 
     * @param gesamtpreis
     * @param Steuersatz
     * @return float
     */
    public static float mehrwertsteuer(float gesamtpreis, float steuersatz) {
        return (Math.round((gesamtpreis * steuersatz) * 100.0) / 100.0f);
    }

    /**
     * Zeigt den Willkommensbildschirm
     */
    public static void willkommensbildschirm() {
        System.out.println(
                "\n----------------------------------------\n    Willkommen bei Ubay Großanzeigen!\n----------------------------------------\n                    n\n                    n\n  \\nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn/\n   \\                                /\n    \\                              /\n     \\                            /\n      \\--------------------------/\n----------------------------------------");
        // 20 Striche --------------------
    }

    /**
     * Gibt einen String auf der Konsole aus.
     * Wartet anschließend auf eine Nutzereingabe und Bestätigung mit ENTER und gibt
     * diese als String zurück.
     * 
     * @param text Hinweistext mit Bezug auf folgende Nutzereingabe
     * @return String
     */
    public static String waitEnter(String text, Scanner scTemp) {
        placeholder = "";
        try {
            System.out.println(text);
            placeholder = scTemp.nextLine();
        } catch (NumberFormatException e) {
            System.out.println("Falsche Eingabe");
        }
        return placeholder;
    }

    /**
     * Prüft, ob ein bestimmter Wert im Array zu finden ist.
     * 
     * @param eingabe Zu prüfender Wert
     * @param array   zu durchsuchender Array
     * @return boolean
     */
    public static boolean inArray(int eingabe, int[] array) {

        for (int e : array) {
            if (e == eingabe) {
                return true;
            }
        }
        return false;
    }

    /**
     * Fügt dem Array einen neuen Wert hinzu, verlängert es um eine Stelle
     * 
     * @param oldArray  Array zum Verlängern
     * @param newString Neuer Wert, der ins Array soll
     * @return int[] Neues Array
     */
    public static int[] addToStringArray(int[] oldArray, int newString) {
        int[] newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
        newArray[oldArray.length] = newString;
        return newArray;
    }

    /**
     * Ausgabe der Produktdaten
     */
    public static void productdataOutput() {
        // Ausgabe Produktdaten Milch
        System.out.println("----------------------------------------");
        System.out.println("Produktnummer: " + PRODUKTNUMMER_MILCH);
        System.out.println("Produktname: " + PRODUKTNAME_MILCH);
        System.out.println("Beschreibung: " + PRODUKTBESCHREIBUNG_MILCH);
        System.out.println("Preis: " + PREIS_MILCH + "€");
        System.out.println("Mehrwertsteuersatz: " + MEHRWERTSTEUERSATZ_MILCH + "%");
    }

}
