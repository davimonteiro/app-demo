package br.com.davimonteiro.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Metric {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(name = "value_metric")
    private Double value;
    private String unit;

    private LocalDate creationDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sensor")
    private Sensor sensor;

}
