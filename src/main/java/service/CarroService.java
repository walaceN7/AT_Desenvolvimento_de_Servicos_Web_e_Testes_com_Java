package service;

import dto.CarroDTOInput;
import dto.CarroDTOOutput;
import model.Carro;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class CarroService {
    private final List<Carro> listaCarros = new ArrayList<>();
    private final ModelMapper mapper = new ModelMapper();

    public List<CarroDTOOutput> listar(){
        List<CarroDTOOutput> output = new ArrayList<>();
        for (Carro carro : listaCarros){
            output.add(mapper.map(carro, CarroDTOOutput.class));
        }
        return output;
    }

    public void inserir(CarroDTOInput input){
        Carro carro = mapper.map(input, Carro.class);
        listaCarros.add(carro);
    }

    public void alterar(CarroDTOInput input){
        Carro carro = mapper.map(input, Carro.class);
        listaCarros.removeIf(c -> c.getId() == carro.getId());
        listaCarros.add(carro);
    }

    public CarroDTOOutput buscar(int id){
        Carro carro = listaCarros.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
        return carro != null ? mapper.map(carro, CarroDTOOutput.class) : null;
    }

    public void excluir(int id){
        listaCarros.removeIf(c -> c.getId() == id);
    }
}
