package com.todo.service;

import com.todo.dto.TacheRequestDTO;
import com.todo.dto.TacheResponseDTO;
import com.todo.dto.TacheUpdateDTO;
import com.todo.entity.StatutTache;
import com.todo.entity.Tache;
import com.todo.exception.TacheNotFoundException;
import com.todo.repository.TacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepository;

    // --- Mapper Tache → DTO ---
    private TacheResponseDTO toDTO(Tache tache) {
        return TacheResponseDTO.builder()
                .id(tache.getId())
                .titre(tache.getTitre())
                .description(tache.getDescription())
                .statut(tache.getStatut())
                .createdAt(tache.getCreatedAt())
                .updatedAt(tache.getUpdatedAt())
                .build();
    }

    @Override
    public TacheResponseDTO creer(TacheRequestDTO dto) {
        log.info("Création d'une nouvelle tâche : {}", dto.getTitre());

        Tache tache = Tache.builder()
                .titre(dto.getTitre())
                .description(dto.getDescription())
                .statut(dto.getStatut() != null ? dto.getStatut() : StatutTache.A_FAIRE)
                .build();

        Tache sauvegardee = tacheRepository.save(tache);
        log.info("Tâche créée avec l'id : {}", sauvegardee.getId());
        return toDTO(sauvegardee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TacheResponseDTO> listerToutes() {
        log.info("Récupération de toutes les tâches");
        return tacheRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TacheResponseDTO> filtrerParStatut(StatutTache statut) {
        log.info("Filtrage des tâches par statut : {}", statut);
        return tacheRepository.findByStatutOrderByCreatedAtDesc(statut)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TacheResponseDTO trouverParId(Long id) {
        log.info("Recherche de la tâche id : {}", id);
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new TacheNotFoundException(id));
        return toDTO(tache);
    }

    @Override
    public TacheResponseDTO modifier(Long id, TacheUpdateDTO dto) {
        log.info("Modification de la tâche id : {}", id);

        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new TacheNotFoundException(id));

        // Mise à jour uniquement des champs fournis (PATCH-style)
        if (dto.getTitre() != null && !dto.getTitre().isBlank()) {
            tache.setTitre(dto.getTitre());
        }
        if (dto.getDescription() != null) {
            tache.setDescription(dto.getDescription());
        }
        if (dto.getStatut() != null) {
            tache.setStatut(dto.getStatut());
        }

        Tache modifiee = tacheRepository.save(tache);
        log.info("Tâche {} modifiée avec succès", id);
        return toDTO(modifiee);
    }

    @Override
    public void supprimer(Long id) {
        log.info("Suppression de la tâche id : {}", id);
        if (!tacheRepository.existsById(id)) {
            throw new TacheNotFoundException(id);
        }
        tacheRepository.deleteById(id);
        log.info("Tâche {} supprimée avec succès", id);
    }
}
