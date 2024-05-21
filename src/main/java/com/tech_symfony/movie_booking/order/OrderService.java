package com.tech_symfony.movie_booking.order;

import com.tech_symfony.movie_booking.system.exception.ForbidenMethodControllerException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
	List<Order> findAllOrders();

	Order getOrderById(Long id);

	Order saveOrder(Order order);

	Order cancelOrder(Long id);

	Order completeOrder(Long id);
}

@Service
class DefaultOrderService implements OrderService {
	private final OrderRepository orderRepository;
	private final OrderModelAssembler assembler;

	public DefaultOrderService(OrderRepository orderRepository, OrderModelAssembler assembler) {
		this.orderRepository = orderRepository;
		this.assembler = assembler;
	}

	public List<Order> findAllOrders() {
		return orderRepository.findAll();

	}

	public Order getOrderById(Long id) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

		return order;
	}

	public Order saveOrder(Order order) {
		order.setStatus(OrderStatus.IN_PROGRESS);
		return orderRepository.save(order);
	}

	public Order cancelOrder(Long id) {
		Order order = getOrderById(id);
		if (order.getStatus() == OrderStatus.IN_PROGRESS) {
			order.setStatus(OrderStatus.CANCELLED);
			return orderRepository.save(order);
		}

		throw new ForbidenMethodControllerException("Order can't be cancelled in " + order.getStatus() + " status");
	}

	public Order completeOrder(Long id) {
		Order order = getOrderById(id);
		if (order.getStatus() == OrderStatus.IN_PROGRESS) {
			order.setStatus(OrderStatus.COMPLETED);
			return orderRepository.save(order);
		}
		throw new ForbidenMethodControllerException("Order can't be completed in " + order.getStatus() + " status");
	}
}
