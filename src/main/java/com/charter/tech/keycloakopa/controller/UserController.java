package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
import com.charter.tech.keycloakopa.constans.ErrorConstants;
import com.charter.tech.keycloakopa.dto.ResultResponse;
import com.charter.tech.keycloakopa.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {

    @GetMapping()
    public List<UserDto> getUserInfo() {
        return List.of(new UserDto("admin", "admin"));
    }

    // @OPAAuthorize(resource = "users", action = "read")
    @PostMapping("users")
    public ResponseEntity<ResultResponse<List<UserDto>>> admin(@RequestBody UserDto user) {
        log.info("cardNumber: {}, email: {}, password: {}, phone: {} ", "4111-1111-1111-1111", "ducvan14893@gmail.com", "123456789", "0123456789");
        return execute(List.of(user), ErrorConstants.MESSAGE_SUCCESS, "200");
    }

    @OPAAuthorize(resource = "categories", action = "read")
    @GetMapping("categories")
    public List<UserDto> categories() {
        return List.of(new UserDto("categories", "categories"));
    }

    @OPAAuthorize(resource = "orders", action = "read")
    @GetMapping("orders")
    public List<UserDto> orders() {
        return List.of(new UserDto("orders", "orders"));
    }

    @OPAAuthorize(resource = "warehouse", action = "read")
    @GetMapping("warehouse")
    public List<UserDto> warehouse() {
        return List.of(new UserDto("warehouse", "warehouse"));
    }

    @OPAAuthorize(resource = "inventory", action = "read")
    @GetMapping("inventory")
    public List<UserDto> inventory() {
        return List.of(new UserDto("inventory", "inventory"));
    }

    @OPAAuthorize(resource = "settings", action = "read")
    @GetMapping("settings")
    public List<UserDto> settings() {
        return List.of(new UserDto("settings", "settings"));
    }
}
