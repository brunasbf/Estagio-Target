import java.text.DecimalFormat;

public class CalculoPercentualFaturamento {

    public static void main(String[] args) {
        // Faturamento mensal por estado
        double[] faturamentos = {67836.43, 36678.66, 29229.88, 27165.48, 19849.53};

        // Calcula o valor total mensal
        double valorTotalMensal = calcularValorTotalMensal(faturamentos);

        // Calcula e exibe os percentuais de representação por estado
        exibirPercentuais(faturamentos, valorTotalMensal);
    }

    private static double calcularValorTotalMensal(double[] faturamentos) {
        double valorTotal = 0;
        for (double faturamento : faturamentos) {
            valorTotal += faturamento;
        }
        return valorTotal;
    }

    private static void exibirPercentuais(double[] faturamentos, double valorTotalMensal) {
        // Formatação para duas casas decimais
        DecimalFormat formato = new DecimalFormat("0.00");

        // Exibe os resultados
        System.out.println("Percentual de Representação por Estado:");

        for (int i = 0; i < faturamentos.length; i++) {
            double percentual = (faturamentos[i] / valorTotalMensal) * 100;
            System.out.println(obterNomeEstado(i) + ": " + formato.format(percentual) + "%");
        }
    }

    private static String obterNomeEstado(int indice) {
        return switch (indice) {
            case 0 -> "SP";
            case 1 -> "RJ";
            case 2 -> "MG";
            case 3 -> "ES";
            case 4 -> "Outros";
            default -> "Desconhecido";
        };
    }
}