package com.example.demo.roupa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoupaService {

    private final RoupaRepository roupaRepository;

    @Autowired
    public RoupaService(RoupaRepository roupaRepository) {

        this.roupaRepository = roupaRepository;
    }

    public List<Roupa> getRoupas() {
        return roupaRepository.findAll();
    }

    @Transactional
    public void addNewRoupa(RoupaDTO roupaDTO) {
        Roupa roupa = defineRoupa(roupaDTO);
        roupaRepository.save(roupa);
    }

    public void deleteRoupa(Long id) {
        roupaRepository.deleteById(id);
    }

    public Roupa getRoupa(Long id) {
        System.out.println("Tentando buscar Roupa com ID:{" + id + "}");

        Optional<Roupa> roupaOptional = roupaRepository.findById(id);

        if (roupaOptional.isPresent()) {
            Roupa roupa = roupaOptional.get();
            System.out.println("Roupa encontrado: {}" + roupa);
            return roupa;
        } else {
            System.out.println("Roupa não encontrado com ID: {}" + id);
            return null;
        }
    }
    @Transactional
    public void updateRoupa(Long id, String nome, String valor, String prazo) {
        Roupa roupaUpdate = roupaRepository.findById(id).orElseThrow(() -> new IllegalStateException("Roupa with id" + id + "not found."));
        if(roupaUpdate.getName().equals(nome)){
            System.out.println("O nome já está atualizado");
        }
        else{
            roupaUpdate.setName(nome);
        }

        if(roupaUpdate.getPrice().equals(valor)){
            System.out.println("Valor já está atualizado.");

        }
        else{
            roupaUpdate.setPrice(valor);
        }

        if(roupaUpdate.getTime().equals(prazo)){
            System.out.println("Valor já está atualizado.");
        }
        else{
            roupaUpdate.setTime(prazo);
        }

    }

    public Roupa defineRoupa(RoupaDTO roupaDTO){
        Roupa roupa = new Roupa();
        roupa.setName(roupaDTO.getName());
        roupa.setPrice(roupaDTO.getPrice());
        roupa.setTime(roupaDTO.getTime());
        roupa.setQuantity(roupa.getQuantity());
        return roupa;

    }


}