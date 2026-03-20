package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.RoleRequest;
import com.charter.tech.keycloakopa.dto.RoleResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/role")
public class RoleController extends BaseController {
    private final RoleService roleService;
    private static List<RoleResponse> leakList = new ArrayList<>();
    @Operation(summary = "messages.get.roles.summary", description = "messages.get.roles.desc")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<RoleResponse>>> getListRole() {
        //testOutOfMemory();
        return execute(roleService.findAll());
    }

    public void testOutOfMemory() {
        System.out.println("Bắt đầu nạp dữ liệu vào RAM...");
        int count = 0;

        try {
            while (true) {
                // Tạo một chuỗi dài và đưa vào list
                RoleResponse roleResponse =new RoleResponse(1L,"ABCDÂSASASSSSSSSSSSSSSSSSSSSSSSSSS","ACVSÂSASSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                leakList.add(roleResponse);
                count++;

                if (count % 10 == 0) {
                    System.out.println("Đã nạp: " + count + " MB (ước tính)");
                }
            }
        } catch (OutOfMemoryError e) {
            System.err.println("THÀNH CÔNG! Đã gây ra lỗi OutOfMemoryError.");
            // Lưu ý: Đừng bắt lỗi này trong ứng dụng thực tế, hãy để nó sập để có Heap Dump
            throw e;
        }
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<RoleResponse>> findRoleById(@PathVariable Long id) {
        return execute(roleService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(roleService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<RoleResponse>> create(@RequestBody @Valid RoleRequest roleRequest) {
        return execute(roleService.create(roleRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<RoleResponse>> update(@PathVariable Long id, @RequestBody @Valid RoleRequest roleRequest) {
        return execute(roleService.update(id, roleRequest));
    }
}
