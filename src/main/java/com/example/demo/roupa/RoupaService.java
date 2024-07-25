package com.example.demo.roupa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoupaService {

    private final RoupaRepository roupaRepository;

    @Autowired
    public RoupaService(RoupaRepository roupaRepository) {

        this.roupaRepository = roupaRepository;
    }

    public List<RoupaDTO> getRoupas() {
        return roupaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<RoupaDTO> getRoupasAtivas() {
        return roupaRepository.findAllActiveRoupas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RoupaDTO convertToDTO(Roupa roupa) {
        RoupaDTO dto = new RoupaDTO();
        dto.setId(roupa.getId());
        dto.setName(roupa.getName());
        dto.setPrice(roupa.getPrice());
        dto.setTime(roupa.getTime());
        return dto;
    }

    @Transactional
    public void addNewRoupa(RoupaDTO roupaDTO) {
        Roupa roupa = defineRoupa(roupaDTO);
        roupaRepository.save(roupa);
    }

    @Transactional
    public void deleteRoupa(Long id) {
        Roupa roupa = roupaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Roupa com id " + id + " não encontrada."));
        roupa.setActive(false); // Marca a roupa como inativa em vez de excluir
        roupaRepository.save(roupa);
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
    public void updateRoupa(Long id, RoupaDTO roupaDTO) {
        //Oculta roupa antiga
        Roupa roupaAntiga = roupaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Roupa com id " + id + " não encontrada."));
        roupaAntiga.setActive(false);
        roupaRepository.save(roupaAntiga);

        // Cria uma nova roupa com os dados atualizados
        Roupa novaRoupa = new Roupa();
        novaRoupa.setName(roupaDTO.getName());
        novaRoupa.setPrice(roupaDTO.getPrice());
        novaRoupa.setTime(roupaDTO.getTime());
        novaRoupa.setQuantity(roupaAntiga.getQuantity());
        roupaRepository.save(novaRoupa);

    }

    public Roupa defineRoupa(RoupaDTO roupaDTO){
        Roupa roupa = new Roupa();
        roupa.setName(roupaDTO.getName());
        roupa.setPrice(roupaDTO.getPrice());
        roupa.setTime(roupaDTO.getTime());
        roupa.setQuantity(roupa.getQuantity());
        roupa.setActive(true);
        return roupa;

    }



}