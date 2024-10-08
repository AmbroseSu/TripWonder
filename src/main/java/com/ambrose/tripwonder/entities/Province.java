package com.ambrose.tripwonder.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Entity
@Table(name = "tbl_province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean status;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PackageTour> packages;


}
