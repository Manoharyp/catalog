import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ShamirSecretSharing {
public static int convertToDecimal(int base, String value) {
        return Integer.parseInt(value, base);
    }

    // Function to calculate the constant term
    public static int calculateConstantTerm(String jsonInput) {
        // Parse the JSON input
        JSONObject data = new JSONObject(jsonInput);
        
        // Read the number of roots (n) and the threshold (k)
        int n = data.getJSONObject("keys").getInt("n");
        int k = data.getJSONObject("keys").getInt("k");
        
        // Collect roots
        List<Integer> roots = new ArrayList<>();
        for (String key : data.keySet()) {
            if (key.matches("\\d+")) {  // Check if the key is a root
                int base = data.getJSONObject(key).getInt("base");
                String value = data.getJSONObject(key).getString("value");
                int decimalValue = convertToDecimal(base, value);
                roots.add(decimalValue);
            }
        }
        
        // Check if we have enough roots to compute c
        if (roots.size() < k) {
            throw new IllegalArgumentException("Not enough roots provided to compute the polynomial.");
        }
        
        // Calculate the constant term c using Vieta's formulas
        int c = 1;
        for (int root : roots) {
            c *= root;
        }
        
        // Determine the sign of c based on the degree of the polynomial
        int m = k - 1;  // Degree of the polynomial
        if (m % 2 == 1) {  // If m is odd
            c = -c;  // Multiply by (-1)^m
        }
        
        return c;
    }

    public static void main(String[] args) {
        // Sample JSON input
        String jsonInput = "{ \"keys\": { \"n\": 4, \"k\": 3 }, "
                         + "\"1\": { \"base\": \"10\", \"value\": \"4\" }, "
                         + "\"2\": { \"base\": \"2\", \"value\": \"111\" }, "
                         + "\"3\": { \"base\": \"10\", \"value\": \"12\" }, "
                         + "\"6\": { \"base\": \"4\", \"value\": \"213\" } }";

        // Run the function and print the result
        int result = calculateConstantTerm(jsonInput);
        System.out.println("The constant term c of the polynomial is: " + result);
    }
}
    

