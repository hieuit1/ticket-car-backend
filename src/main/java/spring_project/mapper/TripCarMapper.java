package spring_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring_project.dto.TripCarRequets;
import spring_project.entity.TripCar;

@Mapper(componentModel = "spring")
public interface TripCarMapper {
    //Hãng xe
    @Mapping(source = "coach.coachId" , target = "coachId")
    @Mapping(source = "coach.licensePlateNumberCoach" , target = "licensePlateNumberCoach")
    @Mapping(source = "coach.coachName" , target = "coachName")
    @Mapping(source = "coach.url" ,target = "url")
    @Mapping(source = "coach.publicId" , target = "publicId")
    // Tài xế
    @Mapping(source = "driver.driverId" , target = "driverId")
    @Mapping(source = "driver.fullName" , target = "fullName")
    @Mapping(source = "driver.phoneNumber" , target = "phoneNumber")
    @Mapping(source = "driver.yearOfBirth" , target = "yearOfBirth")
    @Mapping(source = "driver.gender" , target = "gender")
    // Phụ xe
    @Mapping(source = "rickshaw.rickshawId" , target = "rickshawId")
    @Mapping(source = "rickshaw.fullName" , target = "rickShawfullName")
    @Mapping(source = "rickshaw.phoneNumber" , target = "rickShawphoneNumber")
    @Mapping(source = "rickshaw.yearOfBirth" , target = "rickShawyearOfBirth")
    @Mapping(source = "rickshaw.gender" , target = "rickShawgender")
    TripCarRequets toDTO(TripCar tripCar);

    @Mapping(source = "coachId" , target = "coach.coachId")
    @Mapping(source = "driverId" , target = "driver.driverId")
    @Mapping(source = "rickshawId" , target = "rickshaw.rickshawId")
    TripCar toEntity(TripCarRequets tripCarRequets);
}
