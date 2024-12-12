package com.warehouse.WMS.order.services;

import com.warehouse.WMS.Query;
import com.warehouse.WMS.component.model.ComponentDTO;
import com.warehouse.WMS.component.model.ComponentForecastDTO;
import com.warehouse.WMS.order.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DemandAnalysisOrderService implements Query<Void, Map<String, List<ComponentForecastDTO>>> {
    private final GetShipmentOrdersService getShipmentOrdersService;

    private static final Logger logger = LoggerFactory.getLogger(DemandAnalysisOrderService.class);

    public DemandAnalysisOrderService(GetShipmentOrdersService getShipmentOrdersService) {
        this.getShipmentOrdersService = getShipmentOrdersService;
    }

    @Override
    public ResponseEntity<Map<String, List<ComponentForecastDTO>>> execute(Void input) {
        List<OrderDTO> orders = getShipmentOrdersService.execute(null);
//        logger.info(orders.toString());

        LocalDate oneMonth = LocalDate.of(2024,12,1).minusMonths(1);
        LocalDate twoMonth = LocalDate.of(2024,12,1).minusMonths(2);
        LocalDate threeMonths = LocalDate.of(2024,12,1).minusMonths(3);
        LocalDate sixMonths = LocalDate.of(2024,12,1).minusMonths(6);
        LocalDate now = LocalDate.of(2024,12,1);

        List<OrderDTO> oneMonthOrders = filterOrdersByDateRange(orders, now, oneMonth);
//        logger.info("oneMonthOrders: {}", oneMonthOrders);
        List<OrderDTO> twoMonthOrders = filterOrdersByDateRange(orders, oneMonth, twoMonth);
//        logger.info("twoMonthOrders: {}", twoMonthOrders);
        List<OrderDTO> threeMonthsOrders = filterOrdersByDateRange(orders, now, threeMonths);
        List<OrderDTO> sixMonthsOrders = filterOrdersByDateRange(orders, threeMonths, sixMonths);

        Set<ComponentDTO> allComponents = orders.stream()
                .flatMap(order -> order.getComponents().stream())
                .collect(Collectors.toSet());

        Map<String, List<ComponentForecastDTO>> demandMap = new HashMap<>();

        demandMap.put("oneMonth", calculateForecast(allComponents, oneMonthOrders));
        demandMap.put("threeMonth", calculateForecast(allComponents, threeMonthsOrders));

//        demandMap.get("threeMonth").forEach(componentDemandDTO -> componentDemandDTO.setForecasts(componentDemandDTO.getForecasts() / 3));

        calculateTrends(oneMonthOrders, twoMonthOrders, demandMap.get("oneMonth"));
        calculateTrends(threeMonthsOrders, sixMonthsOrders, demandMap.get("threeMonth"));

        return ResponseEntity.ok(demandMap);
    }

    private void calculateTrends(List<OrderDTO> firstOrders, List<OrderDTO> secondOrders, List<ComponentForecastDTO> components) {
        Map<Integer, Integer> trendsForOneMonth = calculateDifference(firstOrders);
        Map<Integer, Integer> trendsForTwoMonth = calculateDifference(secondOrders);

        for (ComponentForecastDTO component : components) {
            Integer componentId = component.getId();
            int firstMonth = trendsForOneMonth.getOrDefault(componentId, 0);
            int secondMonth = trendsForTwoMonth.getOrDefault(componentId, 0);
//            logger.info("firstMonth: {}, secondMonth: {}", firstMonth, secondMonth);
            if (firstMonth == 0 && secondMonth == 0) {
                // Нет данных для обоих месяцев, тренд не определён
                component.setTrend(0);
            } else if (secondMonth == 0) {
                // Если второй месяц равен нулю, тренд устанавливается как 100% роста
                component.setTrend(100);
            } else {
                // Вычисляем тренд как процентное изменение
                int trend = ((firstMonth - secondMonth) * 100) / secondMonth;
                component.setTrend(trend);
            }
        }
    }

    private Map<Integer, Integer> calculateDifference(List<OrderDTO> orders) {
        Map<Integer, Integer> componentIdQuantityMap = new HashMap<>();

        for (OrderDTO order : orders) {
            for (int i = 0; i < order.getComponents().size(); i++) {
                Integer componentId = order.getComponents().get(i).getId();
                componentIdQuantityMap.put(componentId,
                        componentIdQuantityMap.getOrDefault(componentId, 0) + abs(order.getQuantities().get(i)));
            }
        }
//        logger.info("componentIdQuantityMap : {}", componentIdQuantityMap);
        return componentIdQuantityMap;
    }

    private Integer abs(Integer i) {
        return i > 0 ? i : -i;
    }

    private List<ComponentForecastDTO> calculateForecast(Set<ComponentDTO> components, List<OrderDTO> orders) {
        Map<Integer, Integer> componentIdQuantityMap = calculateDifference(orders);

        return components.stream()
                .map(component -> {
                    int demand = componentIdQuantityMap.getOrDefault(component.getId(), 0);
                    return new ComponentForecastDTO(component, demand);
                })
                .toList();
    }

    private List<OrderDTO> filterOrdersByDateRange(List<OrderDTO> orders, LocalDate startDate, LocalDate endDate) {
        return orders.stream()
                .filter(order -> !order.getOrderDate().isAfter(startDate) && order.getOrderDate().isAfter(endDate))
                .toList();
    }

}
