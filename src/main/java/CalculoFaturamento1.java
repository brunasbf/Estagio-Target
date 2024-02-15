import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculoFaturamento1 {

    public static void main(String[] args) {
        String uriString = "https://drive.google.com/uc?export=download&id=1qXvCPjEL4jerElN-hnScoKkEVmSQ8A2L";
        try {
            JsonNode jsonNode = lerJSONDaURI(uriString);
            List<Double> faturamentoDiario = extrairFaturamentoDiario(jsonNode);

            // Calcula os resultados
            double menorValor = calcularMenorValor(faturamentoDiario);
            double menorValorDiasComFaturamento = calcularMenorValorDiasComFaturamento(faturamentoDiario);
            double maiorValor = calcularMaiorValor(faturamentoDiario);
            double mediaMensal = calcularMediaMensal(faturamentoDiario);
            int diasAcimaMedia = contarDiasAcimaMedia(faturamentoDiario, mediaMensal);

            // Imprime os resultados formatados
            System.out.println("Faturamento mensal da distribuidora (com duas casas decimais)");
            System.out.println("Menor valor de faturamento: " + arredondarParaDuasCasasDecimais(menorValor));
            System.out.println("Menor valor considerando apenas os dias em que houve faturamento: " + arredondarParaDuasCasasDecimais(menorValorDiasComFaturamento));
            System.out.println("Maior valor de faturamento: " + arredondarParaDuasCasasDecimais(maiorValor));
            System.out.println("Média mensal de faturamento: " + arredondarParaDuasCasasDecimais(mediaMensal));
            System.out.println("Dias com faturamento acima da média: " + diasAcimaMedia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonNode lerJSONDaURI(String uriString) throws IOException {
        try {
            URI uri = new URI(uriString);
            URL url = uri.toURL();
            InputStream inputStream = url.openStream();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(inputStream);
        } catch (URISyntaxException e) {
            throw new IOException("Erro ao converter URI para URL", e);
        }
    }

    private static List<Double> extrairFaturamentoDiario(JsonNode jsonNode) {
        List<Double> faturamentoDiario = new ArrayList<>();

        for (JsonNode entry : jsonNode) {
            double valor = entry.get("valor").asDouble();
            faturamentoDiario.add(valor);
        }

        return faturamentoDiario;
    }

    private static double calcularMenorValor(List<Double> faturamentoDiario) {
        return faturamentoDiario.stream().min(Double::compare).orElse(0.0);
    }

    private static double calcularMenorValorDiasComFaturamento(List<Double> faturamentoDiario) {
        return faturamentoDiario.stream().filter(valor -> valor > 0.0).min(Double::compare).orElse(0.0);
    }

    private static double calcularMaiorValor(List<Double> faturamentoDiario) {
        return faturamentoDiario.stream().max(Double::compare).orElse(0.0);
    }

    private static double calcularMediaMensal(List<Double> faturamentoDiario) {
        return faturamentoDiario.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private static int contarDiasAcimaMedia(List<Double> faturamentoDiario, double mediaMensal) {
        return (int) faturamentoDiario.stream().filter(valor -> valor > mediaMensal).count();
    }

    private static String arredondarParaDuasCasasDecimais(double valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(valor);
    }
}
