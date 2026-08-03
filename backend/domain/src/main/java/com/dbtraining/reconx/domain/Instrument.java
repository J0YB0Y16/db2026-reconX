package com.dbtraining.reconx.domain;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "instruments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String symbol;

    @Column(nullable = false, length = 200)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_class", nullable = false, length = 20)
    private AssetClass assetClass;

    @Column(nullable = false, length = 3)
    private String currency;

    /**
     * JSONB metadata: tick size, lot size, exchange code, etc.
     * On H2 (dev profile) this stores as a CLOB; on Postgres it's true JSONB.
     */
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata = new HashMap<>();
}