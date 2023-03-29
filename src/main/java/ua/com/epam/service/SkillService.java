package ua.com.epam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.com.epam.entity.Skills;
import ua.com.epam.entity.dto.skills.SkillsDto;
import ua.com.epam.repository.SkillsRepository;
import ua.com.epam.service.mapper.ModelToDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {
    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private ModelToDtoMapper toDtoMapper;

    private Sort.Direction resolveDirection(String order) {
        return Sort.Direction.fromString(order);
    }

    public List<SkillsDto> findAllSkills() {

        List<Skills> skills;

            skills = skillsRepository.getAllSkills();

        return mapToDto(skills);
    }

    public List<SkillsDto> searchForExistedSkills(String searchQuery) {
        List<Skills> skills;

        skills = skillsRepository.getAllSkills().stream()
                .filter(s->s.getSkills().toLowerCase().contains(searchQuery.toLowerCase()))
                .collect(Collectors.toList());

        return mapToDto(skills);
    }

    private List<SkillsDto> mapToDto(List<Skills> skills) {
        return skills.stream()
                .map(toDtoMapper::mapItemToItemDto)
                .collect(Collectors.toList());
    }
}
