package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "chitietoto")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ChiTietOto{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private Long id;

    private String urlImage;

    @JsonBackReference(value = "oto-chitietoto")
    @ManyToOne
    @JoinColumn(name = "id_oto")
    private Oto oto;

}
