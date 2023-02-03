import java.util.Arrays;

public class ArrayUebungen {
    public static void main(String[] args) {
        int[] intArray = new int[101];
        for (int i = 1; i < intArray.length; i++) {
            intArray[i] = i;
            System.out.println(intArray[i]);
        }
        int[] newintArray = Arrays.copyOf(intArray, intArray.length);
        for (int i = newintArray.length - 1; i > 0; i--) {
            System.out.println(newintArray[i]);
        }
        int multiplier = 5000;
        String stringToInsert = "A";
        String[] bigArray = new String[500];
        for (int n = 0; n < bigArray.length; n++) {
            if (multiplier <= 500) {
                bigArray[n] = stringToInsert;
            } else if (multiplier >= 500) {
                multiplier = 500;
                bigArray[n] = stringToInsert;
            } else {
                System.out.print("Falscher Wert eingegeben, bitte Zahl über Null eingeben.");
                break;
            }
            System.out.println(bigArray[n]);
        }
    }
}
