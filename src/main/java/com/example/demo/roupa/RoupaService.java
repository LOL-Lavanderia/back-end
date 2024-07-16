package com.example.demo.roupa;
import com.example.demo.endereco.Endereco;
import com.example.demo.endereco.EnderecoDTO;
import com.example.demo.endereco.EnderecoService;
import com.example.demo.utils.PasswordService;
import com.example.demo.utils.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        if(roupaUpdate.getNome().equals(nome)){
            System.out.println("O nome já está atualizado");
        }
        else{
            roupaUpdate.setNome(nome);
        }

        if(roupaUpdate.getValor().equals(valor)){
            System.out.println("Valor já está atualizado.");

        }
        else{
            roupaUpdate.setValor(valor);
        }

        if(roupaUpdate.getPrazo().equals(prazo)){
            System.out.println("Valor já está atualizado.");
        }
        else{
            roupaUpdate.setPrazo(prazo);
        }

    }

    public Roupa defineRoupa(RoupaDTO roupaDTO){
        Roupa roupa = new Roupa();
        roupa.setNome(roupaDTO.getNome());
        roupa.setValor(roupaDTO.getValor());
        roupa.setPrazo(roupaDTO.getPrazo());
        return roupa;

    }


}