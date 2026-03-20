package com.charter.tech.keycloakopa.repository;

import com.charter.tech.keycloakopa.dto.MenuResponse;
import com.charter.tech.keycloakopa.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("""
                SELECT new com.charter.tech.keycloakopa.dto.MenuResponse(
                    m.id as id,
                    m.parentId as parentId,
                    m.path as path,
                    m.icon as icon,
                    t.name as name)
                FROM Menu m
                JOIN MenuTranslation t
                  ON m.id = t.menuId
                WHERE t.locale = :locale
                ORDER BY m.orderNo
            """)
    List<MenuResponse> findMenuByLocale(String locale);
}
