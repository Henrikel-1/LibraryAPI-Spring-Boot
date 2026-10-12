package com.henrikel.libraryapi.service;

import com.henrikel.libraryapi.dto.emprestimoDTOS.EmprestimoRequestDto;
import com.henrikel.libraryapi.dto.emprestimoDTOS.EmprestimoResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmprestimoServiceImpl {
    EmprestimoResponseDto criarEmprestimo(EmprestimoRequestDto dto);
    EmprestimoResponseDto buscarEmprestimo(long id);
    Page<EmprestimoResponseDto> listarTodosEmprestimos(Pageable pageable);

}
