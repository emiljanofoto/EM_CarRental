package Project.EM_CarRental.Service;


import Project.EM_CarRental.Entities.CarPackage;
import Project.EM_CarRental.Entities.Order;
import Project.EM_CarRental.Repository.CarPackageRepository;
import Project.EM_CarRental.Repository.OrderRepository;
import Project.EM_CarRental.Security.UserSecurity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final Long ID = null;
    private final CarPackageRepository carPackageRepository;
    private final OrderRepository orderRepository;
    private final UserSecurity userSecurity;

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
    public Order addOrder(String carPackage,Integer days) {
        return (Order) orderRepository.findAll();
    }


}
