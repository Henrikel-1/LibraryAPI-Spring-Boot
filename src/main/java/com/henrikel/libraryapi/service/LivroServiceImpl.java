package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.core.exception.BusinessException;
import com.henrikel.libraryapi.dto.livroDTOS.LivroPatchDTO;
import com.henrikel.libraryapi.dto.livroDTOS.LivroRequestDTO;
import com.henrikel.libraryapi.dto.livroDTOS.LivroResponseDTO;
import com.henrikel.libraryapi.model.Livro;
import com.henrikel.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static com.henrikel.libraryapi.core.exception.TipoErro.*;

import java.util.List;



@Service
@AllArgsConstructor
public class LivroServiceImpl implements LivroService{

    private final LivroRepository livroRepository;

    public Page<LivroResponseDTO> listarTodos(Pageable page){
        return livroRepository.findAll(page).map(this::toResponse);
    }

    public LivroResponseDTO salvar(LivroRequestDTO livro){
        if (livroRepository.existsByTituloIgnoreCase(livro.titulo())){
            throw new BusinessException(CONFLITO, "Já existe um livro cadastrado com esse título");
        }
        Livro livro2 = new Livro();
        livro2.setTitulo(livro.titulo());
        livro2.setAnoPubli(livro.anoPubli());
        livro2.setEditora(livro.editora());
        livro2.setEscritor(livro.escritor());
        livroRepository.save(livro2);
        return toResponse(livro2);
    }

    public void deletar(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        livroRepository.delete(livro);
    }
    public LivroResponseDTO buscarLivro(Long id){
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        return toResponse(livro);
    }

    public List<LivroResponseDTO> buscarLivroQuery(String titulo){
        return livroRepository.findByTituloIgnoreCase(titulo).stream().map(livro -> new LivroResponseDTO(livro.getTitulo(), livro.getAnoPubli(), livro.getEditora(), livro.getEscritor())).toList();
    }

    public LivroResponseDTO alterarAtributo(Long id, LivroPatchDTO dto) {
        Livro livro = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        if (dto.titulo() != null) {
            if (livroRepository.existsByTituloIgnoreCaseAndIdNot(dto.titulo(), id)) {
                throw new BusinessException(CONFLITO, "Já existe outro livro cadastrado com esse título");
            }
            livro.setTitulo(dto.titulo());
        }
        if (dto.anoPubli() != null) {
            livro.setAnoPubli(dto.anoPubli());
        }
        if (dto.editora() != null) {
            livro.setEditora(dto.editora());
        }
        if (dto.escritor() != null) {
            livro.setEscritor(dto.escritor());
        }
        livroRepository.save(livro);
        return toResponse(livro);
    }

    public LivroResponseDTO alterarLivro(Long id, LivroRequestDTO dto){
        if (livroRepository.existsByTituloIgnoreCaseAndIdNot(dto.titulo(), id)){
            throw new BusinessException(CONFLITO, "Já existe outro livro cadastrado com esse título");
        }
        Livro livro2 = livroRepository.findById(id).orElseThrow(() -> new BusinessException(RECURSO_NAO_ENCONTRADO, "Livro não encontrado"));
        livro2.setTitulo(dto.titulo());
        livro2.setAnoPubli(dto.anoPubli());
        livro2.setEditora(dto.editora());
        livro2.setEscritor(dto.escritor());
        livroRepository.save(livro2);
        return toResponse(livro2);
    }
    public LivroResponseDTO toResponse (Livro livro){
        return new LivroResponseDTO(livro.getTitulo(), livro.getAnoPubli(), livro.getEditora(), livro.getEscritor());
    }

}
