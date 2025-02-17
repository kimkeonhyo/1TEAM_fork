package com.example.backend.entity;

// import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "POPULAR_TB")
public class Popular {

    @Id
    // @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rankingId;

    @NotNull(message = "null error")
    @Size(max=6)
    @Column(name = "stock_id", nullable = false)
    private String stockId;

    @NotNull(message = "null error")
    @Max(value = 30)
    @Column(name = "ranking")
    private Integer ranking;

    @NotNull(message = "null error")
    @Column(name = "stock_name")
    private String stockName;

    @NotNull(message = "null error")
    @Column(name = "acmlvol")
    private Integer acmlvol;

    // Getters and Setters
    public Popular(Integer ranking, String stockId, String stockName, Integer acmlvol) {
        this.ranking = ranking;
        this.stockId = stockId;
        this.stockName = stockName;
        this.acmlvol = acmlvol;
    }

    // @PrePersist
    // @PreUpdate
    // public void validate() {
    //     if (this.stockId == null || this.stockName == null || this.acmlvol == null) {
    //         throw new ValidationException("Null values are not allowed for stockId, stockName, or acmlvol");
    //     }
    // }
}    