package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "seq_roles_id", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private List<MyUser> users = new ArrayList<>();

}
