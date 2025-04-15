package com.dhl.demo.repository;

import com.dhl.demo.entity.Postcodelatlng;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostcodeLattitudeLongitudeRepository extends JpaRepository<Postcodelatlng, Long> {

    Postcodelatlng findByPostcode(String postcode);
}
