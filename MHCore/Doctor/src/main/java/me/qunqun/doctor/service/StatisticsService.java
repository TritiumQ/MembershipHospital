package me.qunqun.doctor.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.doctor.entity.dto.RequiredDataDTO;
import me.qunqun.doctor.entity.vo.FlowVO;
import me.qunqun.doctor.entity.vo.StatisticsDataVO;
import me.qunqun.doctor.repo.OrderRepository;
import me.qunqun.doctor.repo.PackageRepository;
import me.qunqun.shared.entity.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StatisticsService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PackageRepository packageRepository;

    public StatisticsDataVO getDataStatisticsT1(LocalDate start, LocalDate end, String granularity, List<RequiredDataDTO> requiredData) {
        Set<Integer> packageIds = requiredData.stream()
                .filter(RequiredDataDTO::getIsRequired) // 筛选 isRequired 为 true 的元素
                .map(RequiredDataDTO::getId) // 映射到 id
                .collect(Collectors.toSet()); // 收集到 Set 中
        OrderFlow orderFlow = getOrdersIncludePackages(start, end, packageIds);
        List<Order> orders = orderFlow.getOrders();

        List<String> xAxis = generateTimeAxis(start, end, granularity);

        List<FlowVO> flowData = requiredData.stream()
                .filter(RequiredDataDTO::getIsRequired)
                .map(reqData -> {
                    Map<Object, Long> statistics = orders.stream()
                            .filter(order -> order.getPackageField().getId().equals(reqData.getId()))
                            .collect(Collectors.groupingBy(order -> getGroupedDate(order.getDate(), granularity), Collectors.counting()));
                    List<String> flowDataList = xAxis.stream()
                            .map(time -> statistics.getOrDefault(time, 0L).toString())
                            .collect(Collectors.toList());
                    return new FlowVO(reqData.getName(), flowDataList);
                })
                .collect(Collectors.toList());

        // 添加时间轴的 FlowVO
        flowData.add(0, new FlowVO("product", xAxis));

        StatisticsDataVO statisticsDataVO = new StatisticsDataVO();
        statisticsDataVO.setStsType(1);  // 根据需求设置
        statisticsDataVO.setStartMonth(start);
        statisticsDataVO.setEndMonth(end);
        statisticsDataVO.setGranularity(granularity);
        statisticsDataVO.setData(flowData);
        statisticsDataVO.setFlowNum(flowData.size());
        return statisticsDataVO;
    }

    private Object getGroupedDate(LocalDate date, String granularity) {
        return switch (granularity) {
            case "Y" -> date.getYear();
            case "M" -> date.getYear() + "-" + date.getMonthValue();
            case "D" -> date.toString();
            default -> throw new IllegalArgumentException("Invalid granularity: " + granularity);
        };
    }

    private List<String> generateTimeAxis(LocalDate start, LocalDate end, String granularity) {
        List<String> timeAxis = new ArrayList<>();
        LocalDate current = start;

        while (!current.isAfter(end)) {
            timeAxis.add(getGroupedDate(current, granularity).toString());
            current = switch (granularity) {
                case "Y" -> current.plusYears(1);
                case "M" -> current.plusMonths(1);
                case "D" -> current.plusDays(1);
                default -> throw new IllegalArgumentException("Invalid granularity: " + granularity);
            };
        }

        return timeAxis;
    }

    @RedisCacheable(keyPrefix = "statistics")
    private OrderFlow getOrdersIncludePackages(LocalDate start, LocalDate end, Set<Integer> packageIds) {
        List<Order> orders = orderRepository.findByPackageIdsAndDateRange(packageIds, start, end);
        OrderFlow orderFlow = new OrderFlow();
        orderFlow.setOrders(orders);
        return orderFlow;
    }

    @Data
    class OrderFlow {
        List<Order> orders;
    }
}
