package com.dongvelog.domain.session.repository;

import com.dongvelog.domain.session.entity.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
