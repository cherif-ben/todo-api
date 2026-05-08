package com.todo.dto;

import com.todo.entity.StatutTache;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TacheUpdateDTO {

    @Size(min = 2, max = 200, message = "Le titre doit contenir entre 2 et 200 caractères")
    private String titre; // optionnel pour la mise à jour

    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description; // optionnel

    private StatutTache statut; // optionnel
}
