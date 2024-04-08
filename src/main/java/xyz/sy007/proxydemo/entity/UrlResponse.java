package xyz.sy007.proxydemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class UrlResponse {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    @Lob
    private String responseData;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
