package com.charter.tech.keycloakopa.dto;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@ApiModule("MENU")
public record MenuRequest(@NotNull(message = "{parentId.required}") Long parentId,
                          @NotBlank(message = "{path.required}") String path,
                          @NotBlank(message = "{icon.required}") String icon,
                          @NotNull(message = "{orderNo.required}") Integer orderNo) {
}
