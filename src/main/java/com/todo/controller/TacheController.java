package com.todo.controller;

import com.todo.dto.ApiResponseDTO;
import com.todo.dto.TacheRequestDTO;
import com.todo.dto.TacheResponseDTO;
import com.todo.dto.TacheUpdateDTO;
import com.todo.entity.StatutTache;
import com.todo.service.TacheService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Tâches", description = "API de gestion des tâches (To-Do List)")
public class TacheController {

    private final TacheService tacheService;

    // ==================== CRÉER ====================
    @PostMapping
    @Operation(
        summary = "Créer une nouvelle tâche",
        description = "Crée une tâche avec un titre, une description et un statut"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tâche créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<ApiResponseDTO<TacheResponseDTO>> creer(
            @Valid @RequestBody TacheRequestDTO dto) {

        TacheResponseDTO tache = tacheService.creer(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success("Tâche créée avec succès", tache));
    }

    // ==================== LIRE TOUTES ====================
    @GetMapping
    @Operation(
        summary = "Lister toutes les tâches",
        description = "Récupère toutes les tâches. Filtre optionnel par statut : A_FAIRE, EN_COURS, TERMINE"
    )
    public ResponseEntity<ApiResponseDTO<List<TacheResponseDTO>>> lister(
            @Parameter(description = "Filtrer par statut : A_FAIRE | EN_COURS | TERMINE")
            @RequestParam(required = false) StatutTache statut) {

        List<TacheResponseDTO> taches = (statut != null)
                ? tacheService.filtrerParStatut(statut)
                : tacheService.listerToutes();

        String message = (statut != null)
                ? taches.size() + " tâche(s) avec le statut : " + statut
                : taches.size() + " tâche(s) trouvée(s)";

        return ResponseEntity.ok(ApiResponseDTO.success(message, taches));
    }

    // ==================== LIRE PAR ID ====================
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une tâche par son ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tâche trouvée"),
        @ApiResponse(responseCode = "404", description = "Tâche introuvable")
    })
    public ResponseEntity<ApiResponseDTO<TacheResponseDTO>> trouverParId(
            @Parameter(description = "ID de la tâche", required = true)
            @PathVariable Long id) {

        TacheResponseDTO tache = tacheService.trouverParId(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Tâche trouvée", tache));
    }

    // ==================== MODIFIER ====================
    @PutMapping("/{id}")
    @Operation(
        summary = "Modifier une tâche",
        description = "Met à jour le titre, la description ou le statut d'une tâche existante. Seuls les champs fournis sont modifiés."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tâche modifiée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Tâche introuvable")
    })
    public ResponseEntity<ApiResponseDTO<TacheResponseDTO>> modifier(
            @PathVariable Long id,
            @Valid @RequestBody TacheUpdateDTO dto) {

        TacheResponseDTO tache = tacheService.modifier(id, dto);
        return ResponseEntity.ok(ApiResponseDTO.success("Tâche modifiée avec succès", tache));
    }

    // ==================== SUPPRIMER ====================
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tâche supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Tâche introuvable")
    })
    public ResponseEntity<ApiResponseDTO<Void>> supprimer(@PathVariable Long id) {
        tacheService.supprimer(id);
        return ResponseEntity.ok(ApiResponseDTO.success("Tâche supprimée avec succès", null));
    }
}
