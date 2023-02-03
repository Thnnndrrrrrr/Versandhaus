import java.util.Arrays;
import java.util.Random;

public class ApplicationW2 {
    public static void main(String[] args) {
        System.out.println("<----------- Willkommen bei Amazon von Wish bestellt! ------------>");
        System.out.println("********************+* Wir liefern Freude! ************************\n\n");
        // Welcome Page und einlesung der Bestellmenges
        try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
            System.out.println("Bitte geben sie ihre Kundennummer ein, um sich anzumelden.");
            boolean login = false;
            String[] kundenid = new String[] { "12345", "23456", "34567" };
            System.out.println("Wenn sie ein neues Konto erstellen wollen geben sie bitte " + 1 + " ein.");
            do {
                System.out.println("Geben sie jetzt bitte ihre Kundenummer ein.");
                int kundennummer = Integer.parseInt(sc.nextLine());
                login = haslogin(kundennummer, kundenid);
            } while (!login);

            System.out.println("Was möchten sie kaufen?");
            int weinProduktnummer = 1;
            String weinProduktname = "Roter Rheingauer Wein";
            double weinProduktpreis = 25.99;
            double weinMehrwehrtsteuer = 0.19;

            System.out.println(
                    "Sie haben " + weinProduktname + " ausgewählt. Wie viel möchten sie von " + weinProduktname
                            + " kaufen?");
            System.out.println("Bitte bestätigen sie mit Enter und ihr Geld wird sofort von ihrem Konto entfernt.");

            int kaufMenge = Integer.parseInt(sc.nextLine());
            double neueSumme = kaufSumme(weinProduktpreis, kaufMenge);
            double ganzneueSumme = summeSteuer(neueSumme, weinMehrwehrtsteuer);

            System.out.println(
                    "Das ist der Preis des Produkts: " + neueSumme + "€ Das ist der Preis inklusive Mehrwehrsteuer: "
                            + ganzneueSumme + "€");
            float roundedSum = roundFunc(ganzneueSumme);

            System.out.println("Das ist der gerundete Preis: " + roundedSum + "€\n\n");

            rechnung(weinProduktnummer, weinProduktname, kaufMenge, weinProduktpreis, weinMehrwehrtsteuer, roundedSum);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Bitte versuchen sie es erneut oder erstellen sie ein Kundenkonto.");
            main(args);
        }

    }

    /**
     * @param kundennummer
     * @return boolean
     */
    public static boolean haslogin(int kundennummer, String[] kundenid) {
        String newKundennummer = "";
        boolean newlogin = false;
        if (kundennummer == 1) {
            Random ran = randomExt();
            int intKundennummer = ran.nextInt(99999 - 10000 + 1) + 10000;
            newKundennummer = newKundennummer + intKundennummer;
            System.out.println("Ihre neue Kundennummer ist: " + newKundennummer);
            kundenid = addToStringArray(kundenid, newKundennummer);
            newlogin = hasKundennummer(newKundennummer, kundenid);
            return newlogin;
        } else {
            newKundennummer += kundennummer;
            newlogin = hasKundennummer(newKundennummer, kundenid);
            if (!newlogin) {
                System.out.println("Ungültige Kundennummer, bitte versuchen sie es erneut.");
            }
            return newlogin;
        }
    }

    /**
     * @return Random
     */
    private static Random randomExt() {
        return new Random();
    }

    /**
     * @param kundennummer
     * @param kundenid
     * @return boolean
     */
    public static boolean hasKundennummer(String kundennummer, String[] kundenid) {
        String newKundennummer = "";
        newKundennummer += kundennummer;
        boolean login = false;
        for (int i = 0; i < kundenid.length; i++) {
            login = kundenid[i].equals(newKundennummer);
            if (login)
                break;
        }
        return login;
    }

    /**
     * @param preis
     * @param menge
     * @return double
     */
    public static double kaufSumme(double preis, double menge) {
        return preis * menge;
        // Berechnung des Kaufpreises für die Anzahl der ausgewählten Artikel
    }

    /**
     * @param preisSumme
     * @param steuer
     * @return double
     */
    public static double summeSteuer(double preisSumme, double steuer) {
        return preisSumme * steuer + preisSumme;
        // Berechnung des Kaufpreises inklusive der Steuer
    }

    /**
     * @param toRound
     * @return float
     */
    public static float roundFunc(double toRound) {
        return (Math.round(toRound * 100.0) / 100);
        // Rundung des Preises auf zwei Nachkommastellen**
    }

    /**
     * @param artikelnummer
     * @param artikelname
     * @param bestellAnzahl
     * @param artikelPreis
     * @param artikelSteuerklasse
     * @param gesamtPreis
     */
    public static void rechnung(int artikelnummer, String artikelname, int bestellAnzahl, double artikelPreis,
            double artikelSteuerklasse, float gesamtPreis) {
        System.out.println("Hier ist ihre Rechnung für ihre Bestellung:");
        System.out.println(
                "Sie haben " + bestellAnzahl + " einheiten von " + artikelname + " mit der Artikelnummer "
                        + artikelnummer + " bestellt.");
        System.out.println(
                "Jeder Artikel kostet zzgl. Mehrwehrtsteuer von " + artikelSteuerklasse * 100 + "% " + artikelPreis
                        + "€");
        System.out
                .println("Für die gesamte Bestellung kostet das sie inklusive Mehrwertsteuer " + gesamtPreis + "€\n\n");
        System.out.println(
                "Danke für ihren Einkauf bei Amazon von Wish bestellt. Wie hoffen sie entscheiden sich bald wieder für einen Einkauf bei uns.");
        // Ausgabe der Rechnung für die gesamte Bestellung
    }

    /**
     * @param oldArray
     * @param newString
     * @return String[]
     */
    public static String[] addToStringArray(String[] oldArray, String newString) {
        String[] newArray = Arrays.copyOf(oldArray, oldArray.length + 1);
        newArray[oldArray.length] = newString;
        return newArray;
    }
}