package br.com.davimonteiro.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor")
    private Sensor sensor;

    public Metric() {
    }

    public Metric(MetricBuilder metricBuilder) {
        this.id = metricBuilder.id;
        this.name = metricBuilder.name;
        this.value = metricBuilder.value;
        this.unit = metricBuilder.unit;
        this.creationDate = metricBuilder.creationDate;
        this.sensor = metricBuilder.sensor;
    }

    public static class MetricBuilder {
        private Long id;
        private String name;
        private Double value;
        private String unit;
        private LocalDate creationDate;

        private Sensor sensor;

        public MetricBuilder() {
        }

        public MetricBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MetricBuilder name(String name) {
            this.name = name;
            return this;
        }

        public MetricBuilder value(Double value) {
            this.value = value;
            return this;
        }

        public MetricBuilder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public MetricBuilder creationDate(LocalDate creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public MetricBuilder sensor(Sensor sensor) {
            this.sensor = sensor;
            return this;
        }

        public Metric build() {
            return new Metric(this);
        }

    }

}
