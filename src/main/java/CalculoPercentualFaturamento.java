import java.text.DecimalFormat;
public class CalculoPercentualFaturamento {

    public static void main(String[] args) {
        // Faturamento mensal por estado
        double faturamentoSP = 67836.43;
        double faturamentoRJ = 36678.66;
        double faturamentoMG = 29229.88;
        double faturamentoES = 27165.48;
        double faturamentoOutros = 19849.53;

        // Calcula o valor total mensal
        double valorTotalMensal = faturamentoSP + faturamentoRJ + faturamentoMG + faturamentoES + faturamentoOutros;

        // Calcula o percentual de representação de cada estado
        double percentualSP = (faturamentoSP / valorTotalMensal) * 100;
        double percentualRJ = (faturamentoRJ / valorTotalMensal) * 100;
        double percentualMG = (faturamentoMG / valorTotalMensal) * 100;
        double percentualES = (faturamentoES / valorTotalMensal) * 100;
        double percentualOutros = (faturamentoOutros / valorTotalMensal) * 100;

        // Formatação para duas casas decimais
        DecimalFormat formato = new DecimalFormat("0.00");

        // Exibe os resultados
        System.out.println("Percentual de Representação por Estado:");
        System.out.println("SP: " + formato.format(percentualSP) + "%");
        System.out.println("RJ: " + formato.format(percentualRJ) + "%");
        System.out.println("MG: " + formato.format(percentualMG) + "%");
        System.out.println("ES: " + formato.format(percentualES) + "%");
        System.out.println("Outros: " + formato.format(percentualOutros) + "%");
    }
}
