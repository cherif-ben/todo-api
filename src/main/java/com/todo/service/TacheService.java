package com.todo.service;

import com.todo.dto.TacheRequestDTO;
import com.todo.dto.TacheResponseDTO;
import com.todo.dto.TacheUpdateDTO;
import com.todo.entity.StatutTache;

import java.util.List;

public interface TacheService {
    TacheResponseDTO creer(TacheRequestDTO dto);
    List<TacheResponseDTO> listerToutes();
    List<TacheResponseDTO> filtrerParStatut(StatutTache statut);
    TacheResponseDTO trouverParId(Long id);
    TacheResponseDTO modifier(Long id, TacheUpdateDTO dto);
    void supprimer(Long id);
}
