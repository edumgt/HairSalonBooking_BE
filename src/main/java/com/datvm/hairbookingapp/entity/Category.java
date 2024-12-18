package com.datvm.hairbookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;


import java.util.List;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id
    private String categoryId;
    @Column(nullable = false)
    private String categoryName;
    @Column(nullable = false)
    private String categoryDescription;

    @OneToMany(mappedBy = "categories", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    @ToString.Exclude
    @JsonIgnore
    private List<Services> service;
}
