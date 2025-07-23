package spring_project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_project.dto.CoachRequets;
import spring_project.dto.RickShawRequets;
import spring_project.entity.Rickshaw;
import spring_project.mapper.RickShawMapper;
import spring_project.repository.RickShawRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RickShawService {

    private final RickShawRepository rickShawRepository;
    private final Cloudinary cloudinary;

    @Autowired
    private RickShawMapper rickShawMapper;

    public List<RickShawRequets> getAllRickShaws() {
        List<Rickshaw> listRickShaws = rickShawRepository.findAll();
        return listRickShaws.stream()
                .map(rickShawMapper::toDTO).collect(Collectors.toList());
    }

    public RickShawRequets getRickShawById(Long rickshawId) {
        Rickshaw rickshaw = rickShawRepository.findById(rickshawId).orElse(null);
        return rickShawMapper.toDTO(rickshaw);
    }

    public Map<String , Object> createRickShaw(MultipartFile file , RickShawRequets rickShawRequets) {
        try {
            Map<String , Object> dataRickShaw = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            Rickshaw rickshaw = new Rickshaw();
            rickshaw.setUrl((String) dataRickShaw.get("url"));
            rickshaw.setPublicId((String) dataRickShaw.get("public_id"));
            rickshaw.setGender(rickShawRequets.getRickShawgender());
            rickshaw.setPhoneNumber(rickShawRequets.getRickShawphoneNumber());
            rickshaw.setFullName(rickShawRequets.getRickShawfullName());
            rickshaw.setDescriptions(rickShawRequets.getRickShawdescriptions());
            rickshaw.setYearOfBirth(rickShawRequets.getRickShawyearOfBirth());
            rickShawRepository.save(rickshaw);
            return dataRickShaw;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRickShaw(Long rickshawId , MultipartFile file , RickShawRequets rickShawRequets) throws IOException {
        Rickshaw rickshaw = rickShawRepository.findById(rickshawId).orElse(null);
        if (file != null && !file.isEmpty()) {
            cloudinary.uploader().destroy(rickshaw.getPublicId() , ObjectUtils.emptyMap());
            Map<String , Object> newDataRickShaw = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            rickshaw.setUrl((String) newDataRickShaw.get("url"));
            rickshaw.setPublicId((String) newDataRickShaw.get("public_id"));
        }

        if (rickShawRequets.getRickShawfullName() != null) {
            rickshaw.setFullName(rickShawRequets.getRickShawfullName());
        }

        if (rickShawRequets.getRickShawdescriptions() != null) {
            rickshaw.setDescriptions(rickShawRequets.getRickShawdescriptions());
        }

        if (rickShawRequets.getRickShawyearOfBirth() != null) {
            rickshaw.setYearOfBirth(rickShawRequets.getRickShawyearOfBirth());
        }

        if (rickShawRequets.getRickShawphoneNumber() != null) {
            rickshaw.setPhoneNumber(rickShawRequets.getRickShawphoneNumber());
        }

        if (rickShawRequets.getRickShawgender() != null) {
            rickshaw.setGender(rickShawRequets.getRickShawgender());
        }

        rickShawRepository.save(rickshaw);
    }

    public void deleteRickShaw(Long rickshawId) throws IOException {
        Rickshaw rickshaw = rickShawRepository.findById(rickshawId).orElse(null);
        cloudinary.uploader().destroy(rickshaw.getPublicId(), ObjectUtils.emptyMap());
        rickShawRepository.delete(rickshaw);
    }

    public Rickshaw getRickShaw(Long rickshawId) {
        Optional<Rickshaw> rickshaw = rickShawRepository.findById(rickshawId);
        return rickshaw.orElse(null);
    }

}
