package com.space.munovachat.rsocket.entity;

import com.space.munovachat.rsocket.core.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table("member")
public class Member extends BaseEntity {
    @Id
    @Column("member_id")
    private Long id;

    private String username;

    private String password;

    private String address;

    private MemberRole role;
}
