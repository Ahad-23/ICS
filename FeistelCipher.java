public class FeistelCipher {

    // Simple round function (just XOR with key and reverse bits)
    private static String roundFunction(String input, String key) {
        int inputInt = Integer.parseInt(input, 2);
        int keyInt = Integer.parseInt(key, 2);
        int result = inputInt ^ keyInt;
        String binary = String.format("%8s", Integer.toBinaryString(result)).replace(' ', '0');
        return new StringBuilder(binary).reverse().toString(); // just reversing for demo
    }

    // Feistel encryption for 2 rounds
    public static String encrypt(String plaintext, String key1, String key2) {
        // Split plaintext into left and right
        String left = plaintext.substring(0, 8);
        String right = plaintext.substring(8, 16);

        // Round 1
        String newRight = xor(left, roundFunction(right, key1));
        String newLeft = right;

        // Round 2
        String finalRight = xor(newLeft, roundFunction(newRight, key2));
        String finalLeft = newRight;

        return finalLeft + finalRight; // Concatenate final L and R
    }

    // Feistel decryption for 2 rounds
    public static String decrypt(String ciphertext, String key1, String key2) {
        String left = ciphertext.substring(0, 8);
        String right = ciphertext.substring(8, 16);

        // Round 2 (reverse order of keys)
        String newRight = xor(left, roundFunction(right, key2));
        String newLeft = right;

        // Round 1
        String finalRight = xor(newLeft, roundFunction(newRight, key1));
        String finalLeft = newRight;

        return finalLeft + finalRight;
    }

    // Helper function to XOR two binary strings
    private static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // 16-bit input (8 bits left + 8 bits right)
        String plaintext = "1101011100101000";
        String key1 = "10101100"; // 8-bit key for round 1
        String key2 = "01010101"; // 8-bit key for round 2

        String ciphertext = encrypt(plaintext, key1, key2);
        System.out.println("Ciphertext: " + ciphertext);

        String decrypted = decrypt(ciphertext, key1, key2);
        System.out.println("Decrypted:  " + decrypted);
    }
}
