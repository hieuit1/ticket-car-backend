package spring_project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_project.dto.CoachRequets;
import spring_project.entity.Coach;
import spring_project.mapper.CoachMapper;
import spring_project.repository.CoachRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoachSevice {

    private final CoachRepository coachRepository;
    private final Cloudinary cloudinary;

    @Autowired
    private CoachMapper coachMapper;

    public List<CoachRequets> getAllCoaches() {
        List<Coach> listCoachs = coachRepository.findAll();
        return listCoachs.stream()
                .map(coachMapper::toDto).collect(Collectors.toList());
    }

    public CoachRequets getCoacheById(Long coachId) {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        return coachMapper.toDto(coach);
    }

    public Map<String , Object> createCoach(MultipartFile file , CoachRequets coachRequets) {
        try {
            Map<String , Object> dataCoach = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            Coach coach = new Coach();
            coach.setUrl((String) dataCoach.get("url"));
            coach.setPublicId((String) dataCoach.get("public_id"));
            coach.setCoachName(coachRequets.getCoachName());
            coach.setLicensePlateNumberCoach(coachRequets.getLicensePlateNumberCoach());
            coachRepository.save(coach);
            return dataCoach;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    public void updateCoach(Long coachId , MultipartFile file , CoachRequets coachRequets) throws IOException {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        if (file != null && !file.isEmpty()) {
            cloudinary.uploader().destroy(coach.getPublicId(), ObjectUtils.emptyMap());
            Map<String,Object> newDataCoach = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            coach.setUrl((String) newDataCoach.get("url"));
            coach.setPublicId((String) newDataCoach.get("public_id"));
        }

        if (coachRequets.getCoachName() != null) {
            coach.setCoachName(coachRequets.getCoachName());
        }

        if (coachRequets.getLicensePlateNumberCoach() != null) {
            coach.setLicensePlateNumberCoach(coachRequets.getLicensePlateNumberCoach());
        }

        coachRepository.save(coach);
    }

    public void deleteCoach(Long coachId) throws IOException {
        Coach coach = coachRepository.findById(coachId).orElse(null);
        cloudinary.uploader().destroy(coach.getPublicId(), ObjectUtils.emptyMap());
        coachRepository.delete(coach);
    }

    public Coach findCoachById(Long coachId) {
        Optional<Coach> coach = coachRepository.findById(coachId);
        return coach.orElse(null);
    }
}
