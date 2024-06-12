package com.tech_symfony.movie_booking.api.bill;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@RepositoryRestController
@ResponseBody
public class BillController {

	private final BillService billService;
	private final RepositoryEntityLinks entityLinks;
	private final BillModelAssembler billModelAssembler;

	@Operation(
		summary = "Thêm hóa đơn kèm vé trước khi thanh toán",
		description = "API thêm hóa đơn kèm vé trước khi thanh toán, hóa đơn khi vừa thêm sẽ có trạng thái mặc định " +
			"là `Unpaid`, thời hạn thanh toán là 15 phút, nếu sau 15 phút không thực hiện thanh toán, hóa đơn và " +
			"vé sẽ được xóa khỏi cơ sở dữ liệu."
	)
	@PostMapping(value = "/bills")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EntityModel<Bill>> create(
		@Valid @RequestBody BillDTO dataRaw,
		Principal principal
	) {
		Bill newBill = billService.create(dataRaw, principal.getName());
		Link link = entityLinks.linkToItemResource(Bill.class, newBill.getId());
		return ResponseEntity
			.created(link.toUri())
			.body(billModelAssembler.toModel(newBill));
	}


	@Operation(
		summary = "Thực hiện thanh toán",
		description = "API được gọi khi vừa thanh toán và chuyển về trang thông báo, API sẽ trả về kết quả cần thiết " +
			"để hiển thị với khách hàng, kết quả có thể thành công hoặc thất bại. Khi thất bại, lí do " +
			"sẽ được nêu rõ. "
	)
	@PutMapping(value = "/bills/{billId}/payment")
	public EntityModel<Bill> pay(
		@PathVariable UUID billId
	) {

		return billModelAssembler.toModel(billService.pay(billId));
	}


}