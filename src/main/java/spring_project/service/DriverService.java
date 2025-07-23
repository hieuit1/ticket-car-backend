package spring_project.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_project.dto.DriverRequets;
import spring_project.entity.Driver;
import spring_project.mapper.DriverMapper;
import spring_project.repository.DriverRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final Cloudinary cloudinary;

    @Autowired
    private DriverMapper driverMapper;

    public List<DriverRequets> getListDrivers(){
        List<Driver> listDrivers = driverRepository.findAll();
        return listDrivers.stream()
                .map(driverMapper::toDto).collect(Collectors.toList());
    }

    public DriverRequets getDriverById(Long driverId){
        Driver driver = driverRepository.findById(driverId).orElse(null);
        return driverMapper.toDto(driver);
    }

    public Map<String , Object> createDriver(MultipartFile file , DriverRequets driverRequets){
        try {
            Map<String,Object> dataDriver = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            Driver drivers = new Driver();
            drivers.setUrl((String) dataDriver.get("url"));
            drivers.setPublicId((String) dataDriver.get("public_id"));
            drivers.setGender(driverRequets.getGender());
            drivers.setPhoneNumber(driverRequets.getPhoneNumber());
            drivers.setFullName(driverRequets.getFullName());
            drivers.setDescriptions(driverRequets.getDescriptions());
            drivers.setYearOfBirth(driverRequets.getYearOfBirth());
            driverRepository.save(drivers);
            return dataDriver;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage(), e);
        }
    }

    public void updateDriver(Long driverId , MultipartFile file , DriverRequets driverRequets) throws IOException {
        Driver driver = driverRepository.findById(driverId).orElse(null);
        if (file != null && !file.isEmpty()) {
            cloudinary.uploader().destroy(driver.getPublicId(), ObjectUtils.emptyMap());
            Map<String , Object> newDataDriver = this.cloudinary.uploader().upload(file.getBytes(),Map.of());
            driver.setUrl((String) newDataDriver.get("url"));
            driver.setPublicId((String) newDataDriver.get("public_id"));
        }

        if (driverRequets.getFullName() != null) {
            driver.setFullName(driverRequets.getFullName());
        }

        if (driverRequets.getDescriptions() != null) {
            driver.setDescriptions(driverRequets.getDescriptions());
        }

        if (driverRequets.getYearOfBirth() != null) {
            driver.setYearOfBirth(driverRequets.getYearOfBirth());
        }

        if (driverRequets.getPhoneNumber() != null) {
            driver.setPhoneNumber(driverRequets.getPhoneNumber());
        }

        if (driverRequets.getGender() != null) {
            driver.setGender(driverRequets.getGender());
        }

        driverRepository.save(driver);
    }

    public void deleteDriver(Long driverId) throws IOException {
        Driver driver = driverRepository.findById(driverId).orElse(null);
        cloudinary.uploader().destroy(driver.getPublicId(), ObjectUtils.emptyMap());
        driverRepository.delete(driver);
    }

    public Driver findDriverById(Long driverId){
        Optional<Driver> driver = driverRepository.findById(driverId);
        return driver.orElse(null);
    }
}
