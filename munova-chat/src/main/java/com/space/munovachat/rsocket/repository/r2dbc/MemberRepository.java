package com.space.munovachat.rsocket.repository.r2dbc;

import com.space.munovachat.rsocket.entity.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MemberRepository extends ReactiveCrudRepository<Member, Long> {
}
