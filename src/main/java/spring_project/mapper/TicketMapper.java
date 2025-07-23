package spring_project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring_project.dto.TicketRequests;
import spring_project.entity.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "tripCar.tripCarId" , target = "tripCarId")
    @Mapping(source = "tripCar.tripName" , target = "tripName")
    @Mapping(source = "tripCar.departureDate" , target = "departureDate")
    @Mapping(source = "tripCar.departureTime" , target = "departureTime")
    @Mapping(source = "tripCar.departureEndTime" , target = "departureEndTime")
    @Mapping(source = "tripCar.pickupPoint" , target = "pickupPoint")
    @Mapping(source = "tripCar.payPonit" , target = "payPonit")

    @Mapping(source = "user.id" , target = "id")
    @Mapping(source = "user.email" , target = "email")
    @Mapping(source = "user.numberphone" , target = "numberphone")
    @Mapping(source = "user.username" , target = "username")
    @Mapping(source = "user.name" , target = "name")
    TicketRequests toDto(Ticket ticket);

    @Mapping(source = "id" , target = "user.id")
    @Mapping(source = "tripCarId" , target = "tripCar.tripCarId")
    Ticket toEntity(TicketRequests ticketRequests);
}
