package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupWithPicturesOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.services.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserLeanOutputDto>> getUsersByGroupId(@PathVariable Long id) {
        List<UserLeanOutputDto> userLeanOutput = groupService.getUsersByGroupId(id);
        return ResponseEntity.ok(userLeanOutput);
    }

    @GetMapping("/users/{userId}/group")
    public ResponseEntity<GroupWithPicturesOutputDto> getMyGroup(@PathVariable Long userId) {
        GroupWithPicturesOutputDto groupPicturesOutputDto = groupService.getMyGroup(userId);
        return ResponseEntity.ok(groupPicturesOutputDto);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupWithPicturesOutputDto> getGroup(@PathVariable Long groupId) {
        GroupWithPicturesOutputDto groupPicturesOutputDto = groupService.getGroup(groupId);
        return ResponseEntity.ok(groupPicturesOutputDto);
    }


    @GetMapping("/admin/all")
    public ResponseEntity<List<GroupOutputDto>> getMyActiveGroups() {
        List<GroupOutputDto> groupOutputDtos = groupService.getMyActiveGroups();
        return ResponseEntity.ok(groupOutputDtos);
    }

    @GetMapping("/admin/{groupId}")
    public ResponseEntity<GroupWithPicturesOutputDto> getSpecificGroup(@PathVariable Long groupId) {
        GroupWithPicturesOutputDto groupPicturesOutputDto = groupService.getSpecificGroup(groupId);
        return ResponseEntity.ok(groupPicturesOutputDto);
    }

    @PostMapping("/admin")
    public ResponseEntity<GroupOutputDto> createGroup(@Valid @RequestBody GroupInputDto groupInputDto) {
        GroupOutputDto groupOutputDto = groupService.createGroup(groupInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + groupOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(groupOutputDto);
    }
}



