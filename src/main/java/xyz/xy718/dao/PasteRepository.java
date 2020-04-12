package xyz.xy718.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.xy718.model.Paste;

public interface PasteRepository extends JpaRepository<Paste, Long>{
	Paste findByLink(String link);
}
