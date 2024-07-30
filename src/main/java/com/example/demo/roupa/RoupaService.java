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

    // Método para obter todas as roupas
    public List<RoupaDTO> getRoupas() {
        return roupaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para obter todas as roupas ativas
    public List<RoupaDTO> getRoupasAtivas() {
        return roupaRepository.findAllActiveRoupas().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para obter uma roupa específica por ID
    public Roupa getRoupaById(Long id) {
        Roupa roupa = roupaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Roupa com id " + id + " não encontrada."));
        return roupa;
    }

    // Método para adicionar uma nova roupa
    @Transactional
    public void addNewRoupa(RoupaDTO roupaDTO) {
        Roupa roupa = defineRoupa(roupaDTO);
        roupaRepository.save(roupa);
    }

    // Método para atualizar uma roupa existente
    @Transactional
    public void updateRoupa(Long id, RoupaDTO roupaDTO) {
        Roupa roupaAntiga = roupaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Roupa com id " + id + " não encontrada."));
        roupaAntiga.setActive(false);
        roupaRepository.save(roupaAntiga);

        Roupa novaRoupa = new Roupa();
        novaRoupa.setName(roupaDTO.getName());
        novaRoupa.setPrice(roupaDTO.getPrice());
        novaRoupa.setTime(roupaDTO.getTime());
        novaRoupa.setQuantity(roupaDTO.getQuantity());
        novaRoupa.setActive(true);
        roupaRepository.save(novaRoupa);
    }

    // Método para deletar uma roupa
    @Transactional
    public void deleteRoupa(Long id) {
        Roupa roupa = roupaRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Roupa com id " + id + " não encontrada."));
        roupa.setActive(false);
        roupaRepository.save(roupa);
    }

    // Método auxiliar para converter uma entidade Roupa para um DTO
    private RoupaDTO convertToDTO(Roupa roupa) {
        RoupaDTO dto = new RoupaDTO();
        dto.setId(roupa.getId());
        dto.setName(roupa.getName());
        dto.setPrice(roupa.getPrice());
        dto.setTime(roupa.getTime());
        dto.setQuantity(roupa.getQuantity());
        return dto;
    }

    // Método auxiliar para criar uma entidade Roupa a partir de um DTO
    private Roupa defineRoupa(RoupaDTO roupaDTO) {
        Roupa roupa = new Roupa();
        roupa.setName(roupaDTO.getName());
        roupa.setPrice(roupaDTO.getPrice());
        roupa.setTime(roupaDTO.getTime());
        roupa.setQuantity(roupaDTO.getQuantity());
        roupa.setActive(true);
        return roupa;
    }
}