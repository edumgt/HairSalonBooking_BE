package com.datvm.hairbookingapp.entity;

import com.datvm.hairbookingapp.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "code can't be null")
    @Column(unique = true)
    private String code;

    @NotNull(message = "First Name can't be null")
    private String firstName;

    @NotNull(message = "Last Name can't be null")
    private String lastName;

    @NotNull(message = "Gender can't be null")
    private String gender;

    @NotNull(message = "Yob can't be null")
    private int yob;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    @NotNull(message = "Join in can't be null")
    private LocalDate joinIn;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String image;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;
}
