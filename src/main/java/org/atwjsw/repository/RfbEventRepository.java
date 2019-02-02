package org.atwjsw.repository;

import org.atwjsw.domain.RfbEvent;
import org.atwjsw.domain.RfbLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


/**
 * Spring Data  repository for the RfbEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfbEventRepository extends JpaRepository<RfbEvent, Long> {

    RfbEvent findByRfbLocationAndEventDate(RfbLocation location, LocalDate now);
}
