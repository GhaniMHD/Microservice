package com.ghani.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idPelanggan;
    private Long idProduk;
    private int jumlah;
    private Double harga;

/*    @PrePersist
    @PreUpdate
    private void calculateTotalHarga() {
        this.totalHarga = this.Harga * this.Jumlah;
    }
    
}
*/
    // Override Lombok's getter for totalHarga
    public Double getTotalHarga() {
        if (this.harga == null) {
            return 0.0;
        }
        return this.harga * this.jumlah;
    }
}
