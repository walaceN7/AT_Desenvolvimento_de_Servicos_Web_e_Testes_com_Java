package controller;

import dto.CarroDTOInput;
import org.codehaus.jackson.map.ObjectMapper;
import service.CarroService;

import static spark.Spark.*;

public class CarroController {
    private final CarroService carroService = new CarroService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void respostasRequisicoes(){
        get("/carros", (request, response) -> {
            response.type("application/json");
            response.status(200);
            return objectMapper.writeValueAsString(carroService.listar());
        });

        get("/carros/:id", (request, response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params("id"));
            response.status(200);
            return objectMapper.writeValueAsString(carroService.buscar(id));
        });

        delete("/carros/:id", (request, response) -> {
            response.type("application/json");
            int id = Integer.parseInt(request.params("id"));
            carroService.excluir(id);
            response.status(200);
            return "Carro excluído com sucesso";
        });

        post("/carros", (request, response) -> {
            CarroDTOInput carroInput = objectMapper.readValue(request.body(), CarroDTOInput.class);
            carroService.inserir(carroInput);
            response.type("application/json");
            response.status(201);
            return "Produto inserido com sucesso";
        });

        put("carros", (request, response) -> {
            response.type("application/json");
            CarroDTOInput carroInput = objectMapper.readValue(request.body(), CarroDTOInput.class);
            carroService.alterar(carroInput);
            response.status(200);
            return "Produto alterado com sucesso";
        });
    }

}
