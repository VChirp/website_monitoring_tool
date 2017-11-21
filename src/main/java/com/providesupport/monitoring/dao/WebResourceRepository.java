package com.providesupport.monitoring.dao;

import com.providesupport.monitoring.entity.WebResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebResourceRepository extends JpaRepository<WebResource, Long> {

    List<WebResource> findAllByUrl(String url);
}
