package com.example.demo.utils;
import com.example.demo.endereco.Endereco;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {



    public String[] getEnderecoByCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String[] reponse = restTemplate.getForObject(url, String[].class);
       return reponse;
    }

}