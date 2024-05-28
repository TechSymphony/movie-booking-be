package com.tech_symfony.movie_booking.api.movie;


import org.springframework.stereotype.Service;


public interface PublicMovieService {
//	List<Order> findAllOrders();


}

@Service
class DefaultPublicMovieService implements PublicMovieService {
	private final MovieRepository movieRepository;
	private final PublicMovieModelAssembler assembler;

	public DefaultPublicMovieService(MovieRepository movieRepository, PublicMovieModelAssembler assembler) {
		this.movieRepository = movieRepository;
		this.assembler = assembler;
	}

//	public List<Movie> findAllOrders() {
//		return movieRepository.findAll();
//
//	}
//
//	public Order getOrderById(Long id) {
//		Order order = movieRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
//
//		return order;
//	}
//
//	public Order saveOrder(Order order) {
//		order.setStatus(OrderStatus.IN_PROGRESS);
//		return movieRepository.save(order);
//	}
//
//	public Order cancelOrder(Long id) {
//		Order order = getOrderById(id);
//		if (order.getStatus() == OrderStatus.IN_PROGRESS) {
//			order.setStatus(OrderStatus.CANCELLED);
//			return movieRepository.save(order);
//		}
//
//		throw new ForbidenMethodControllerException("Order can't be cancelled in " + order.getStatus() + " status");
//	}
//
//	public Order completeOrder(Long id) {
//		Order order = getOrderById(id);
//		if (order.getStatus() == OrderStatus.IN_PROGRESS) {
//			order.setStatus(OrderStatus.COMPLETED);
//			return movieRepository.save(order);
//		}
//		throw new ForbidenMethodControllerException("Order can't be completed in " + order.getStatus() + " status");
//	}
}
