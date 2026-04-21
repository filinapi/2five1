/*package com.web._2five1.repository;

import com.web._2five1.model.JazzStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JazzStandardRepository extends JpaRepository<JazzStandard, Long> {
    List<JazzStandard> findByTitleContainingIgnoreCase(String title);
}*/

package com.web._2five1.repository;

import com.web._2five1.model.JazzStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JazzStandardRepository extends JpaRepository<JazzStandard, Long> {

    // 1. Ricerca base per titolo (questa la teniamo così perché è ancora leggibile)
    List<JazzStandard> findByTitleContainingIgnoreCase(String title);

    // 2. Ricerca per nome del compositore (usiamo JPQL per accorciare il nome del metodo)
    @Query("SELECT s FROM JazzStandard s WHERE LOWER(s.composer.name) LIKE LOWER(concat('%', :name, '%'))")
    List<JazzStandard> searchByComposer(@Param("name") String name);

    // 3. Ricerca incrociata (Titolo + Compositore)
    @Query("SELECT s FROM JazzStandard s WHERE LOWER(s.title) LIKE LOWER(concat('%', :title, '%')) " +
            "AND LOWER(s.composer.name) LIKE LOWER(concat('%', :composer, '%'))")
    List<JazzStandard> searchByBoth(@Param("title") String title, @Param("composer") String composer);
}