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

        // Valores iniciales de 4 dígitos
        fibonacci[0] = 1234;
        fibonacci[1] = 2345;

        // Generación Fibonacci ajustada a 4 dígitos
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
        saveResultToDatabase(TOTAL_NUMBERS, product);
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

    private static void saveResultToDatabase(int totalNumbers, BigInteger result) {

        String sql = "{ call INSERT_FIBONACCI_PRODUCT(?,?) }";

        try (Connection conn = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, totalNumbers);
            cs.setBigDecimal(2, new java.math.BigDecimal(result));
            cs.execute();

            System.out.println("\nResultado registrado correctamente en oracle.");

        } catch (Exception e) {
            System.err.println("Error al registrar en la base de datos:");
            e.printStackTrace();
        }
    }
}