import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CalculoFaturamento1 {
    private static final Logger logger = LoggerFactory.getLogger(CalculoFaturamento1.class);

    public static void main(String[] args) {
        try {
            JsonNode jsonNode = lerJSONDaURI(Constantes.URI_STRING);
            List<Double> faturamentoDiario = extrairFaturamentoDiario(jsonNode);

            // Calcula os resultados
            double menorValor = calcularMenorValor(faturamentoDiario);
            double menorValorDiasComFaturamento = calcularMenorValorDiasComFaturamento(faturamentoDiario);
            double maiorValor = calcularMaiorValor(faturamentoDiario);
            double mediaMensal = calcularMediaMensal(faturamentoDiario);
            long diasAcimaMedia = contarDiasAcimaMedia(faturamentoDiario, mediaMensal);

            // Imprime os resultados formatados
            imprimirResultados(menorValor, menorValorDiasComFaturamento, maiorValor, mediaMensal, diasAcimaMedia);

        } catch (IOException e) {
            logger.error("Erro ao ler JSON da URI", e);
        }
    }

    private static JsonNode lerJSONDaURI(String uriString) throws IOException {
        try {
            URI uri = new URI(uriString);
            URL url = uri.toURL();

            try (InputStream inputStream = url.openStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readTree(inputStream);
            }

        } catch (URISyntaxException e) {
            throw new IOException("Erro ao converter URI para URL", e);
        }
    }

    private static List<Double> extrairFaturamentoDiario(JsonNode jsonNode) {
        return StreamSupport.stream(jsonNode.spliterator(), false)
                .map(entry -> entry.get("valor").asDouble())
                .collect(Collectors.toList());
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

    private static long contarDiasAcimaMedia(List<Double> faturamentoDiario, double mediaMensal) {
        return faturamentoDiario.stream().filter(valor -> valor > mediaMensal).count();
    }

    private static void imprimirResultados(double menorValor, double menorValorDiasComFaturamento,
                                           double maiorValor, double mediaMensal, long diasAcimaMedia) {
        System.out.println(Constantes.FATURAMENTO_MENSAL_MSG);
        System.out.println(Constantes.MENOR_VALOR_MSG + arredondarParaDuasCasasDecimais(menorValor));
        System.out.println(Constantes.MENOR_VALOR_DIAS_COM_FATURAMENTO_MSG + arredondarParaDuasCasasDecimais(menorValorDiasComFaturamento));
        System.out.println(Constantes.MAIOR_VALOR_MSG + arredondarParaDuasCasasDecimais(maiorValor));
        System.out.println(Constantes.MEDIA_MENSAL_MSG + arredondarParaDuasCasasDecimais(mediaMensal));
        System.out.println(Constantes.DIAS_ACIMA_MEDIA_MSG + diasAcimaMedia);
    }

    private static String arredondarParaDuasCasasDecimais(double valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(valor);
    }
}

class Constantes {
    public static final String URI_STRING = "https://drive.google.com/uc?export=download&id=1qXvCPjEL4jerElN-hnScoKkEVmSQ8A2L";
    public static final String FATURAMENTO_MENSAL_MSG = "Faturamento mensal da distribuidora (com duas casas decimais)";
    public static final String MENOR_VALOR_MSG = "Menor valor de faturamento: ";
    public static final String MENOR_VALOR_DIAS_COM_FATURAMENTO_MSG = "Menor valor considerando apenas os dias em que houve faturamento: ";
    public static final String MAIOR_VALOR_MSG = "Maior valor de faturamento: ";
    public static final String MEDIA_MENSAL_MSG = "Média mensal de faturamento: ";
    public static final String DIAS_ACIMA_MEDIA_MSG = "Dias com faturamento acima da média: ";
}