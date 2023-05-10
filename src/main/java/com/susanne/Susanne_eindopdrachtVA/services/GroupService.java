package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.dtos.input.GroupInputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.GroupOutputDto;
import com.susanne.Susanne_eindopdrachtVA.dtos.output.UserLeanOutputDto;
import com.susanne.Susanne_eindopdrachtVA.model.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GroupService {
        List<UserLeanOutputDto> getUsersByGroupId(@PathVariable Long id);

        GroupOutputDto getMyGroup(@PathVariable Long id);

        List<GroupOutputDto> getMyActiveGroups();

        GroupOutputDto getSpecificGroup(@PathVariable Long id);

        GroupOutputDto createGroup(@Valid @RequestBody GroupInputDto groupInputDto);
}

//        GroupOutputDto updateGroup(@PathVariable Long id, @Valid @RequestBody GroupInputDto upGroup);
//}
