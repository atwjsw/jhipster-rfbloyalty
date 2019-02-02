package org.atwjsw.bootstrap;

import org.atwjsw.domain.RfbEvent;
import org.atwjsw.domain.RfbEventAttendance;
import org.atwjsw.domain.RfbLocation;
import org.atwjsw.domain.RfbUser;
import org.atwjsw.repository.RfbEventAttendanceRepository;
import org.atwjsw.repository.RfbEventRepository;
import org.atwjsw.repository.RfbLocationRepository;
import org.atwjsw.repository.RfbUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class RfbBootstrap implements CommandLineRunner {

    private final RfbLocationRepository rfbLocationRepository;
    private final RfbEventRepository rfbEventRepository;
    private final RfbEventAttendanceRepository rfbEventAttendanceRepository;
    private final RfbUserRepository rfbUserRepository;

    public RfbBootstrap(RfbLocationRepository rfbLocationRepository, RfbEventRepository rfbEventRepository, RfbEventAttendanceRepository rfbEventAttendanceRepository, RfbUserRepository rfbUserRepository) {
        this.rfbLocationRepository = rfbLocationRepository;
        this.rfbEventRepository = rfbEventRepository;
        this.rfbEventAttendanceRepository = rfbEventAttendanceRepository;
        this.rfbUserRepository = rfbUserRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // init EFB Locations

        if (rfbLocationRepository.count() == 0) {
            initData();
        }
    }

    private void initData() {
        RfbUser rfbUser = new RfbUser();
        rfbUser.setUsername("Johnny");
        rfbUserRepository.save(rfbUser);

        // load data
        RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());

        rfbUser.setHomeLocation(aleAndWitch);
        rfbUserRepository.save(rfbUser);

        RfbEvent aleEvent = getRfbEvent(aleAndWitch);
        getRfbEventAttendance(rfbUser, aleEvent);

        RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());
        RfbEvent ratcEvent = getRfbEvent(ratc);
        getRfbEventAttendance(rfbUser, ratcEvent);

        RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());
        RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);
        getRfbEventAttendance(rfbUser, stPeteBrewEvent);

        RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());
        RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);
        getRfbEventAttendance(rfbUser, yardOfAleEvent);
    }

    private void getRfbEventAttendance(RfbUser rfbUser, RfbEvent rfbEvent) {
        RfbEventAttendance rfbAttendance = new RfbEventAttendance();
        rfbAttendance.setRfbEvent(rfbEvent);
        rfbAttendance.setRfbUser(rfbUser);
        rfbAttendance.setAttendanceDate(LocalDate.now());

        System.out.println(rfbAttendance);
        rfbEventAttendanceRepository.save(rfbAttendance);
        rfbEventRepository.save(rfbEvent);
    }

    private RfbEvent getRfbEvent(RfbLocation rfbLocation) {
        RfbEvent rfbEvent = new RfbEvent();
        rfbEvent.setEventCode(UUID.randomUUID().toString());
        rfbEvent.setEventDate(LocalDate.now());
        rfbLocation.addRfbEvent(rfbEvent);
        rfbLocationRepository.save(rfbLocation);
        rfbEventRepository.save(rfbEvent);
        return rfbEvent;
    }

    private RfbLocation getRfbLocation(String locationName, int value) {
        RfbLocation rfbLocation = new RfbLocation();
        rfbLocation.setLocationName(locationName);
        rfbLocation.setRunDayOfWeek(value);
        rfbLocationRepository.save(rfbLocation);
        return rfbLocation;
    }
}
