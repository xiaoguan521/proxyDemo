package xyz.sy007.proxydemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.sy007.proxydemo.entity.UrlResponse;

public interface UrlResponseRepository extends JpaRepository<UrlResponse, Long> {
}
