import java.io.*;
import java.util.Arrays;

public class Main {

    private final static String base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static void main(String[] args) {

        //get hex sequence
        String hex = readHexSequence();
        System.out.println(hex);

        //convert hex to bytes
        byte[] bytes = hextoBytes(hex);

        //convert bytes to base64
        int[] base64 = bytetobase64(bytes);

        //map base64 to base64chars
        for (int i = 0; i < base64.length; i++) {
            System.out.print(base64chars.charAt(base64[i]));
        }

        //padding
        int padding = base64.length % 4;
        while (padding != 0) {
            System.out.print("="); padding--;
        }
    }

    public static String readHexSequence() {
        BufferedReader br = null;
        String s = null;
        try {

            br = new BufferedReader(new FileReader("hexSequence.dat"));
            s = br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static byte[] hextoBytes(String hexStr) {

        //2 hex = 1 byte
        int len = hexStr.length() / 2;

        byte[] bytes = new byte[len];

        for (int i = 0; i < hexStr.length(); i += 2) {
            bytes[i/2] = (byte) ((Character.digit(hexStr.charAt(i), 16) << 4) +
                                  Character.digit(hexStr.charAt(i+1), 16));
        }

        return bytes;
    }

    public static int[] bytetobase64(byte[] bytes) {

        int len = (int) ((bytes.length * 4) / 3);
        int[] base64 = new int[len];
        int j = 0;

        //3 bytes = 4 base64
        for (int i = 0; i < bytes.length; i += 3) {
            int one = 0, two = 0, three = 0;
            one = (bytes[i] << 16);
            if (i + 1 < bytes.length) two = (bytes[i+1] << 8);
            if (i + 2 < bytes.length) three = bytes[i+2];
            int combine = one | two | three;
            base64[j] = (combine >> 18) & 63;
            if (j + 1 < len) base64[j+1] = (combine >> 12) & 63;
            if (j + 2 < len) base64[j+2] = (combine >> 6) & 63;
            if (j + 3 < len) base64[j+3] = (combine & 63);
            j += 4;
        }

        return base64;
    }

}
