package com.example.demo.cliente;

import com.example.demo.endereco.EnderecoDTO;

public class ClienteComEnderecoDTO {

    private ClienteDTO clienteDTO;

    private EnderecoDTO enderecoDTO;


    public ClienteComEnderecoDTO(ClienteDTO clienteDTO, EnderecoDTO enderecoDTO) {
        this.clienteDTO = clienteDTO;
        this.enderecoDTO = enderecoDTO;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public EnderecoDTO getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }

    @Override
    public String toString() {
        return "ClienteEnderecoDTO{" +
                "cliente=" + clienteDTO.toString() +
                ", endereco=" + enderecoDTO.toString() +
                '}';
    }

}
