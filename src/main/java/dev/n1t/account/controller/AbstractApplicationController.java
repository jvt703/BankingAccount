package dev.n1t.account.controller;

import dev.n1t.account.dto.OutgoingAbstractApplicationPrefillDto;
import dev.n1t.account.service.AbstractApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractApplicationController {

    private final AbstractApplicationService abstractApplicationService;

    @Autowired
    public AbstractApplicationController(
            AbstractApplicationService abstractApplicationService
    ){
        this.abstractApplicationService = abstractApplicationService;
    }

    @GetMapping("/user/{userId}/applicationPrefill")
    @PreAuthorize("(#Role.equals('User') and #userId == #requestId) or (#Role.equals('admin'))")
    public OutgoingAbstractApplicationPrefillDto getApplicationDataPrefill(
            @PathVariable(value = "userId") long userId,
            @RequestHeader("Role") String Role,
            @RequestHeader("id") Long requestId
    ){
        return abstractApplicationService.getApplicationDataPrefill(userId);
    }
}
