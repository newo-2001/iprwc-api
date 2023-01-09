package me.newo2001.webshop.orders;

import me.newo2001.webshop.common.NotFoundException;
import me.newo2001.webshop.common.pagination.Paginated;
import me.newo2001.webshop.common.pagination.PaginationRequest;
import me.newo2001.webshop.users.IUserService;
import me.newo2001.webshop.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;
    private final IUserService userService;

    @Autowired
    public OrderController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id) {
        Order order = orderService.findOrderById(id)
                .orElseThrow(NotFoundException::new);

        User authenticated = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("User authenticated without account"));

        if (order.getUserId().equals(authenticated.getId())) {
            throw new NotFoundException();
        }

        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Paginated<Order>> getOrderPage(@RequestParam(required = false, defaultValue = "1") @Min(1) int page,
                                                         @RequestParam(required = false, defaultValue = "20") @Min(1) @Max(Paginated.MAX_PAGE_SIZE) int pageSize) {
        User authenticated = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("User authenticated without account"));

        PaginationRequest pageRequest = new PaginationRequest(page, pageSize);
        return ResponseEntity.ok(orderService.getOrderPageByUser(authenticated, pageRequest));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid CreateOrderDto orderData) {
        User authenticated = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("User authenticated without account"));

        if (orderData.items().stream().anyMatch(item -> item == null || item.getProductId() == null)) {
            throw new IllegalArgumentException("Item id can't be null");
        }

        return ResponseEntity.ok(orderService.createOrder(authenticated.getId(), orderData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
        User authenticated = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("User authenticated without account"));

        Order order = orderService.findOrderById(id)
                .orElseThrow(NotFoundException::new);
        if (order.getUserId() != authenticated.getId()) {
            throw new NotFoundException();
        }

        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
