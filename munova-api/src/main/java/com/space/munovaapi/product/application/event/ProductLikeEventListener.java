package com.space.munovaapi.product.application.event;

import com.space.munovaapi.product.application.ProductLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ProductLikeEventListener {

    private final ProductLikeService productLikeService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleLikeDelete(ProductDeleteEvenForLikeDto event) {
        if(event.isDeleted()) {
            productLikeService.deleteProductLikeByProductIds(event.productId());
        }
    }
}
