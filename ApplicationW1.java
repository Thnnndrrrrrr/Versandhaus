public class ApplicationW1 {
    public static void main(String[] args) {
        System.out.println("<----------- Willkommen bei Amazon von Wish bestellt! ------------>");
        System.out.println("********************+* Wir liefern Freude! ************************\n\n");
        // Welcome Page und einlesung der Bestellmenges
        try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
            System.out.println("Bitte geben sie ihre Kundennummer ein, um sich anzumelden.");

            int kundennummer = Integer.parseInt(sc.nextLine());

            System.out.println("Willkommen zurück! Ihre Kundenummmer ist:" + kundennummer);

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
            System.out.println("Kundennummer nicht im System gefunden, bitte geben sie eine gültige Kundennummer ein.");
        }

        System.out.println(
                "Danke für ihren Einkauf bei Amazon von Wish bestellt. Wie hoffen sie entscheiden sich bald wieder für einen Einkauf bei uns.");
    }

    public static double kaufSumme(double preis, double menge) {
        return preis * menge;
        // Berechnung des Kaufpreises für die Anzahl der ausgewählten Artikel
    }

    public static double summeSteuer(double preisSumme, double steuer) {
        return preisSumme * steuer + preisSumme;
        // Berechnung des Kaufpreises inklusive der Steuer
    }

    public static float roundFunc(double toRound) {
        return (Math.round(toRound * 100.0) / 100);
        // Rundung des Preises auf zwei Nachkommastellen**
    }

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
        // Ausgabe der Rechnung für die gesamte Bestellung
    }
}
