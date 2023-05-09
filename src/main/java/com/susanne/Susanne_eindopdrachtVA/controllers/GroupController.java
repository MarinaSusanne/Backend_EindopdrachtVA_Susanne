package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.input.UserInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.User;
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

    @GetMapping("/{groupName}/users")
    public ResponseEntity<List<UserLeanOutputDto>> getUsersByGroupName(@PathVariable String groupName) {
        List<UserLeanOutputDto> userLeanOutput = groupService.getUsersByGroupName(groupName);
        return ResponseEntity.ok(userLeanOutput);
    }

    @GetMapping ("/{id}/users")
    public ResponseEntity<List<UserLeanOutputDto>> getUsersByGroupId(@PathVariable Long id) {
       List<UserLeanOutputDto> userLeanOutput = groupService.getUsersByGroupId (id);
        return ResponseEntity.ok(userLeanOutput);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity <GroupOutputDto> getMyGroup (@PathVariable Long id) {
        GroupOutputDto groupOutputDto = groupService.getMyGroup(id);
        return ResponseEntity.ok(groupOutputDto);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<GroupOutputDto>> getMyActiveGroups () {
        List<GroupOutputDto> groupOutputDtos = groupService.getMyActiveGroups();
        return ResponseEntity.ok(groupOutputDtos);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<GroupOutputDto> getSpecificGroup (@PathVariable Long id) {
        GroupOutputDto groupOutputDto = groupService.getSpecificGroup(id);
        return ResponseEntity.ok(groupOutputDto);
    }
    @PostMapping("/admin")
    public ResponseEntity<GroupOutputDto> createGroup (@Valid @RequestBody GroupInputDto groupInputDto) {
        GroupOutputDto groupOutputDto = groupService.createGroup(GroupInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + groupOutputDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(groupOutputDto);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<GroupOutputDto> updateGroup(@PathVariable Long id, @Valid @RequestBody GroupInputDto upGroup) {
         GroupOutputDto groupOutputDto = groupService.updateGroup(id, upGroup);
         return ResponseEntity.ok().body(groupOutputDto);
    }

}



