import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private final static Scanner in = new Scanner(System.in);
    private final static Map<String, Integer> latinNumberMap = new HashMap<>();
    static {
        latinNumberMap.put("I", 1);
        latinNumberMap.put("II", 2);
        latinNumberMap.put("III", 3);
        latinNumberMap.put("IV", 4);
        latinNumberMap.put("V", 5);
        latinNumberMap.put("VI", 6);
        latinNumberMap.put("VII", 7);
        latinNumberMap.put("VIII", 8);
        latinNumberMap.put("IX", 9);
        latinNumberMap.put("X", 10);
    }
    private final static String[] arrOperand = new String[]{"+","-","/","*"};

    public static String LatinoArabCalculator(String str) throws RuntimeException {
        str = str.replace(" ", "");
        boolean latin;
        if (str.matches("[\\d]{1,2}[+\\-/*][\\d]{1,2}"))
            latin = false;
        else if (str.matches("[IVX]{1,4}[+\\-/*][IVX]{1,4}"))
            latin = true;
        else
            throw new RuntimeException("Неправильный ввод");

        String operand="";
        for (int i = 0; i < arrOperand.length; i++) {
            if(str.contains(arrOperand[i])) {
                operand = arrOperand[i];
                break;
            }
        }
        int first;
        int second;
        String[] strings = str.split("\\" + operand);

        if (!latin){
            first = Integer.parseInt(strings[0]);
            second = Integer.parseInt(strings[1]);
        } else {
            first = latinNumberMap.get(strings[0]);
            second = latinNumberMap.get(strings[1]);
        }
        if (first > 10 || second > 10) throw new RuntimeException("Операнды не должны быть более 10");

        int result = switch (operand) {
            case "+" -> first + second;
            case "-" -> first - second;
            case "/" -> first / second;
            case "*" -> first * second;
            default -> - 1000;
        };
        if (result == -1000) throw new RuntimeException("Недопустимый ввод");
        String res;
        if(latin) {
            if (result < 1) throw new RuntimeException("Недопустимый для Римских чисел результат");
            res = arabToLatin(result);
        }
        else
            res = String.valueOf(result);
        return res;
    }

    private static String arabToLatin(int arabNum) {
        StringBuilder latin = new StringBuilder();
        if (arabNum  == 100)
            return latin.append('C').toString();
        String [] arrDec = {"", "X","XX","XXX", "XL","L", "LX","LXX", "LXXX","LC"};
        String [] arrNum = {"", "I","II","III", "IV","V", "VI","VII", "VIII","IX"};
        int decimal = arabNum / 10;
        latin.append(arrDec[decimal]);
        int current = arabNum % 10;
        latin.append(arrNum[current]);
        return latin.toString();
    }

    public static void main(String[] args) {
        String input = in.nextLine();
        try {
            System.out.println(LatinoArabCalculator(input));
        } catch (Exception e){
            System.out.println(e.getMessage()) ;
        }
    }
}
