package com.charter.tech.keycloakopa.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "path", "icon", "name", "parentId", "children"})
public class MenuResponse {
    private Long id;
    private Long parentId;
    private String path;
    private String icon;
    private String name;
    private List<MenuResponse> children = new ArrayList<>();

    public MenuResponse(Long id, Long parentId, String path, String icon, String name) {
        this.id = id;
        this.parentId = parentId;
        this.path = path;
        this.icon = icon;
        this.name = name;
    }
}
