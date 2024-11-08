package ru.prokhorov.limitservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.prokhorov.limitservice.enums.Status;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Сущность "Операции с Лимитами".
 *
 * @author Evgeniy_Prokhorov
 */
@Entity
@Getter
@Setter
@Table(name = "write_downs")
public class WriteDownEntry {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "limit_id", nullable = false)
    private Limit limit;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "create_date")
    private Date createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
}
