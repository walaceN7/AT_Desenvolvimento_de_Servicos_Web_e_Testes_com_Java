package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carro {
    private Long id;
    private String modelo;
    private String placa;
    private String chassi;
}
