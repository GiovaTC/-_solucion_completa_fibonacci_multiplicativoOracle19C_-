import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;


public class FibonacciMultiplicationApp {

    // ajustar segun su entorno

    private static final String DB_URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "Tapiero123";

    public static void main(String[] args) {

        final int TOTAL_NUMBERS = 15;
        int[] fibonacci = new int[TOTAL_NUMBERS];

        // valores iniciales de 4 digitos .
        for (int i = 2; i < TOTAL_NUMBERS; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
            fibonacci[i] = normalizeToFourDigits(fibonacci[i]);
        }

        // multiplicacion total (BigInteger para evitar overflow)
        BigInteger product = BigInteger.ONE;

        System.out.println("Serie Fibonacci (4 digitos):");

        for (int value : fibonacci) {
            System.out.println(value + " ");
            product = product.multiply(BigInteger.valueOf(value));
        }

        System.out.println("\n\nResultado de la multiplicacion:");
        System.out.println(product);

        //  registro en oracle .
    }

    private static int normalizeToFourDigits(int value) {
        while (value > 9999) {
            value /= 10;
        }
        if (value < 1000) {
            value += 1000;
        }
        return value;
    }
}