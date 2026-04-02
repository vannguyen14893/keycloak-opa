package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.dto.UserDto;
import com.charter.tech.keycloakopa.exception.BusinessExceptionHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.charter.tech.keycloakopa.constans.MessageConstants.BIZ_USER_NOT_FOUND;
import static com.charter.tech.keycloakopa.constans.ResponseCodeConstants.BIZ_USER_001;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {


    @GetMapping()
    public List<UserDto> getUserInfo() {
        return List.of(new UserDto("admin", "admin"));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<SuccessResultResponse<UserDto>> getUser(@PathVariable Long id) {
        if (id == 1) {
            String messages = dbMessageSourceConfig.getMessages(BIZ_USER_NOT_FOUND, new Object[]{id}, httpServletRequest.getLocale());
            throw new BusinessExceptionHandler(BIZ_USER_001, messages);
        }
        return execute(new UserDto("admin", "admin"));
    }

    //@OPAAuthorize(resource = "users", action = "create")
    @PostMapping("users")
    public ResponseEntity<SuccessResultResponse<List<UserDto>>> admin(@RequestBody @Valid UserDto user) {
        log.info("cardNumber: {}, email: {}, password: {}, phone: {} ", "4111-1111-1111-1111", "ducvan14893@gmail.com", "123456789", "0123456789");
        return execute(List.of(user));
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
