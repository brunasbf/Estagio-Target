import java.util.Scanner;
public class VerificaFibonacci {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe um número para verificar se pertence à sequência de Fibonacci:");
        int numero = scanner.nextInt();

        if (verificaFibonacci(numero)) {
            System.out.println(numero + " pertence à sequência de Fibonacci.");
        } else {
            System.out.println(numero + " não pertence à sequência de Fibonacci.");
        }

        scanner.close();
    }

    public static boolean verificaFibonacci(int num) {
        int a = 0, b = 1;

        while (a <= num) {
            if (a == num) {
                return true;
            }

            int temp = a + b;
            a = b;
            b = temp;
        }

        return false;
    }
}
