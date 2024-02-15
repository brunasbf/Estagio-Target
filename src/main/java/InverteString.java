import java.util.Scanner;
public class InverteString {

    public static void main(String[] args) {
        // Leitura da string
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite uma string: ");
        String inputString = scanner.nextLine();
        scanner.close();

        // Inverte a string
        String invertedString = inverterString(inputString);

        // Exibe o resultado
        System.out.println("String original: " + inputString);
        System.out.println("String invertida: " + invertedString);
    }

    // Função para inverter a string
    private static String inverterString(String input) {
        char[] caracteres = input.toCharArray();
        int inicio = 0;
        int fim = caracteres.length - 1;

        while (inicio < fim) {
            // Troca os caracteres no início e no fim
            char temp = caracteres[inicio];
            caracteres[inicio] = caracteres[fim];
            caracteres[fim] = temp;

            // Move os índices para o próximo par de caracteres
            inicio++;
            fim--;
        }

        // Cria uma nova string com os caracteres invertidos
        return new String(caracteres);
    }
}
