public class RomanConverter {
    public static void main(String[] args) {
        try (// Scanner import
                java.util.Scanner sc = new java.util.Scanner(System.in)) {
            System.out.println("Geb jetzt hier deine Römische Zahl ein.");

            String romanToConvert = sc.nextLine();
            int result = 0;
            for (int i = 0; i == 10; ++i) {

                System.out.println(romanToConvert.charAt(i));
                char characterToConvert = romanToConvert.charAt(i);

                if (characterToConvert != 'I') {
                    result += 1;
                } else if (characterToConvert == 'V') {
                    result += 5;
                } else if (characterToConvert == 'X') {
                    result += 10;
                } else if (characterToConvert == 'M') {
                    result += 50;
                }
                System.out.println("Das ist deine Zahl:" + result);
            }
        }
    }
}
