package com.space.munovaapi.chat.entity;

import com.space.munovaapi.product.domain.Category;
import com.space.munovaapi.product.domain.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Table(name = "chat_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_tag_id")
    private Long chatTagId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private Category productCategoryId;

    @Enumerated(EnumType.STRING)
    private ProductCategory categoryType;

    public static ChatTag createChatTag(Chat chat, Category productCategory) {
        return ChatTag.builder()
                .chat(chat)
                .productCategoryId(productCategory)
                .categoryType(productCategory.getCategoryType())
                .build();
    }
}
