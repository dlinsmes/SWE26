import java.util.Scanner;
public class HeyLookFreeCodeThanksMrLin {

    public static Scanner s = new Scanner (System.in);

    public static void main(String[] args) {
        System.out.println("enter an int");
        int uNum = getNum();
        System.out.println("uNum: " + uNum);

        System.out.println("enter a letter");
        String letter = s.nextLine();
        while (let2Num(letter) == -1) {
            System.out.println("not a letter dummy");
            System.out.println("enter a letter");
            letter = s.nextLine();
        }

        System.out.println(letter + " is #" + let2Num(letter) + " in the alphabet (A is 0)");

        System.out.println("Enter a number to convert to letter (0 is A)");
        System.out.println(num2Let(getNum()));

    }

    public static int getNum() {
        try {
            return Integer.parseInt(s.nextLine());
        } catch (Exception e) {
            System.out.println("not a number you dumdum. try again");
            return getNum();
        }
    }

    public static int let2Num(String let) {
        if (let.length() == 1)
            if (let.toUpperCase().charAt(0) >= 'A' && let.toUpperCase().charAt(0) <= 'Z')
                return let.toUpperCase().charAt(0) - 'A';
        return -1;
    }

    public static String num2Let(int num) {
        if (num >=0 && num < 26)
            return (char)('A' + num) + "";
        return "nope";
    }

}
