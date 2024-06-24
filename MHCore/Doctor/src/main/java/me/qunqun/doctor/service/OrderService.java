package me.qunqun.doctor.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.Order;
import me.qunqun.doctor.entity.dto.OrderQueryDTO;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.repo.OrderRepository;
import me.qunqun.doctor.specification.OrderSpecification;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.shared.manager.redis.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Resource
    private RedisManager redisManager;

    public Result<List<OrderVO>> getOrders(OrderQueryDTO orderQueryDTO, int page, int size) {
        try {
            Page<Order> orderPage;
            if (orderQueryDTO == null) {
                PageRequest pageRequest = PageRequest.of(page, size);
                // 查询deprecated为0的数据
                orderPage = orderRepository.findAllByDeprecated(false, pageRequest);
//                orderPage = orderRepository.findAll(pageRequest);
            }else {
                PageRequest pageRequest = PageRequest.of(page, size);
                orderPage = orderRepository.findAll(OrderSpecification.getOrders(orderQueryDTO), pageRequest);
            }
            List<OrderVO> orders = orderPage.stream().map(order -> {
                OrderVO orderVO = new OrderVO();
                orderVO.setOrderId(order.getId());
                orderVO.setUserId(order.getUser().getId());
                orderVO.setUserName(order.getUser().getRealName());
                orderVO.setUserSex(order.getUser().getSex());
                orderVO.setOrderDate(order.getDate());
                orderVO.setHospitalId(order.getHospital().getId());
                orderVO.setHospitalName(order.getHospital().getName());
                orderVO.setPackageId(order.getPackageField().getId());
                orderVO.setPackageName(order.getPackageField().getName());
                orderVO.setStatus(order.getState());
                return orderVO;
            }).toList();
            return Result.success(orders);
        } catch (Exception e) {
            log.error("getOrders error: {}", e.getMessage());
            return Result.error(500, "服务器错误");
        }
    }

    public List<OrderVO> getOrderList(OrderQueryDTO orderQueryDTO, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(OrderSpecification.getOrders(orderQueryDTO), pageRequest);

        return orderPage.stream().map(order -> {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrderId(order.getId());
            orderVO.setUserId(order.getUser().getId());
            orderVO.setUserName(order.getUser().getRealName());
            orderVO.setUserSex(order.getUser().getSex());
            orderVO.setOrderDate(order.getDate());
            orderVO.setHospitalId(order.getHospital().getId());
            orderVO.setHospitalName(order.getHospital().getName());
            orderVO.setPackageId(order.getPackageField().getId());
            orderVO.setPackageName(order.getPackageField().getName());
            orderVO.setStatus(order.getState());
            return orderVO;
        }).collect(Collectors.toList());
    }

    public Integer getOrderCountByHospitalId(Integer hospitalId) {
        return orderRepository.countByHospital_IdAndDeprecated(hospitalId, false);
    }

    public Integer getOrderCountByHospitalIdAndDate(Integer hospitalId, LocalDate date) {
        return orderRepository.countByHospital_IdAndDateAndDeprecated(hospitalId, date, false);
    }

    public Integer getOrderCountByHospitalIdAndDateBeforeAndState(Integer hospitalId, LocalDate date, Integer state) {
        return orderRepository.countByHospital_IdAndDateBeforeAndDeprecatedAndState(hospitalId, date, false, state);
    }

    public OrderVO getOrderVO(int orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        return new OrderVO().fromOrder(order);
    }

    public List<Order> getOrdersByDateAndDeprecated(LocalDate tomorrow) {
        return orderRepository.findByDateAndDeprecated(tomorrow);
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
}
