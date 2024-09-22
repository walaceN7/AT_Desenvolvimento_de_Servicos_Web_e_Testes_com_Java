import dto.CarroDTOInput;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.CarroService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ServiceTest {
    //Questão 17
    @Test
    public void testeInsercao(){
        CarroService carroService = new CarroService();
        CarroDTOInput carroDTOInput = new CarroDTOInput();

        carroDTOInput.setId(1);
        carroDTOInput.setModelo("Fusca");
        carroDTOInput.setPlaca("ABC-123");
        carroDTOInput.setChassi("XYZ123");

        carroService.inserir(carroDTOInput);
        Assertions.assertEquals(1, carroService.listar().size());
    }

    //Questão 18
    @Test
    public void testeListarCarros() throws IOException {
        URL url = new URL("http://localhost:4567/carros");

        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        int responseCode = conexao.getResponseCode();
        conexao.disconnect();

        Assertions.assertEquals(200, responseCode);
    }

    //Questão 19
    @Test
    public void testeInserirCarroAPIJSONObject() throws IOException, JSONException {
        String carroAletorio = Integer.toString(new Random().nextInt(1, 33));
        URL urlAPIPublica = new URL("https://freetestapi.com/api/v1/cars/" + carroAletorio);
        HttpURLConnection conexao = (HttpURLConnection) urlAPIPublica.openConnection();
        conexao.setRequestMethod("GET");
        int responseCode = conexao.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());

            String chassi = "Chassi" + new Random().nextInt(10000);
            String placa = "XYZ" + "-" + new Random().nextInt(1000, 10000);

            JSONObject novoCarro = new JSONObject();
            novoCarro.put("id", jsonObject.getInt("id"));
            novoCarro.put("modelo", jsonObject.getString("model"));
            novoCarro.put("placa", placa);
            novoCarro.put("chassi", chassi);

            URL urlAPI = new URL("http://localhost:4567/carros");
            HttpURLConnection conexaoAPI = (HttpURLConnection) urlAPI.openConnection();
            conexaoAPI.setRequestMethod("POST");
            conexaoAPI.setRequestProperty("Content-Type", "application/json");
            conexaoAPI.setDoOutput(true);

            OutputStream os = conexaoAPI.getOutputStream();
            os.write(novoCarro.toString().getBytes());
            os.flush();
            os.close();

            responseCode = conexaoAPI.getResponseCode();
            conexao.disconnect();
            conexaoAPI.disconnect();

            Assertions.assertEquals(201, responseCode);
        }
    }

    //Questão 20
    @Test
    public void testeInserirCarroAPIObjectMapper() throws IOException {
        String carroAletorio = Integer.toString(new Random().nextInt(1, 33));

        URL urlAPIPublica = new URL("https://freetestapi.com/api/v1/cars/" + carroAletorio);
        HttpURLConnection conexao = (HttpURLConnection) urlAPIPublica.openConnection();
        conexao.setRequestMethod("GET");
        int responseCode = conexao.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            ObjectMapper objectMapper = new ObjectMapper();

            String chassi = "Chassi" + new Random().nextInt(10000);
            String placa = "XYZ" + "-" + new Random().nextInt(1000, 10000);

            JsonNode node = objectMapper.readTree(response.toString());

            CarroDTOInput novoCarro = new CarroDTOInput();
            novoCarro.setId(node.path("id").asInt());
            novoCarro.setModelo(node.path("model").asText());
            novoCarro.setPlaca(placa);
            novoCarro.setChassi(chassi);

            URL urlAPI = new URL("http://localhost:4567/carros");
            HttpURLConnection conexaoAPI = (HttpURLConnection) urlAPI.openConnection();
            conexaoAPI.setRequestMethod("POST");
            conexaoAPI.setRequestProperty("Content-Type", "application/json");
            conexaoAPI.setDoOutput(true);

            String json = objectMapper.writeValueAsString(novoCarro);
            OutputStream os = conexaoAPI.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            responseCode = conexaoAPI.getResponseCode();
            conexao.disconnect();
            conexaoAPI.disconnect();

            Assertions.assertEquals(201, responseCode);
        }
    }
}
