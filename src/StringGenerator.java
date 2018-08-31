import java.util.Random;

public class StringGenerator {
 private static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
 private static Random r = new Random();
 private static String chosen = "";

 //Where the random letter is chosen, to modify how many you want to output, change the length number.
 public static String randomString() {
  chosen = "";
  while (chosen.length() != 1) {
   int rPick = r.nextInt(5);
   if (rPick == 0) {
    int spot = r.nextInt(50);
    chosen += alphabet.charAt(spot);
   }
  }
  return chosen;
 }
}
