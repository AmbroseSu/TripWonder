package com.ambrose.tripwonder.ServiceTest;

import com.ambrose.tripwonder.converter.GenericConverter;
import com.ambrose.tripwonder.dto.PackageOfficialDTO;
import com.ambrose.tripwonder.entities.PackageOfficial;
import com.ambrose.tripwonder.entities.enums.SortBy;
import com.ambrose.tripwonder.repository.PackageOfficialRepository;
import com.ambrose.tripwonder.services.PackageOfficialService;
import com.ambrose.tripwonder.services.impl.PackageOfficialServiceImpl;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PackageOfficialServiceTest {

    @Mock
    private PackageOfficialRepository packageOfficialRepository;
    
    private GenericConverter<PackageOfficialDTO> mapperToDto; // Thêm mock cho GenericConverter


    private PackageOfficialService packageOfficialService;

    private Faker faker;
    List<PackageOfficial> packageOfficials = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mapperToDto = new GenericConverter<>(new ModelMapper());
        MockitoAnnotations.openMocks(this);
        packageOfficialService = new PackageOfficialServiceImpl(packageOfficialRepository, mapperToDto);
        faker = new Faker();
        int n = 30;

        for (int i = 0; i < n; i++) {
            packageOfficials.add(PackageOfficial.builder()
                    .id((long) i)
                    .date(Date.from(faker.timeAndDate().past(30, java.util.concurrent.TimeUnit.DAYS)))
                    .price((float) faker.number().randomDouble(2, 1000000, 1000000000))
                    .numberAttendance(faker.number().numberBetween(1, 7))
                    .isDeleted(false)
                    .build());
        }
    }

    @Test
    void testDateASC() {
        
        when(packageOfficialRepository.findAll()).thenReturn(packageOfficials);
        List<PackageOfficialDTO> packageOfficialDTOS = packageOfficialService.findAll(SortBy.SORT_BY_DATE_ASC);
        for(int i = 0; i < packageOfficialDTOS.size(); i++) {
            if(i == packageOfficialDTOS.size() - 1) return;
            final PackageOfficialDTO packageOfficialDTO = packageOfficialDTOS.get(i);
            final PackageOfficialDTO packageOfficialDTO2 = packageOfficialDTOS.get(i+1);
            assertTrue(packageOfficialDTO.getDate().toString() +" "+packageOfficialDTO2.getDate().toString(),
                    packageOfficialDTO.getDate().compareTo(packageOfficialDTO2.getDate()) <= 0);
        }
    }
    @Test
    void testDateDESC() {
        when(packageOfficialRepository.findAll()).thenReturn(packageOfficials);
        List<PackageOfficialDTO> packageOfficialDTOS = packageOfficialService.findAll(SortBy.SORT_BY_DATE_DESC);
        for(int i = 0; i < packageOfficialDTOS.size(); i++) {
            if(i == packageOfficialDTOS.size() - 1) return;
            final PackageOfficialDTO packageOfficialDTO = packageOfficialDTOS.get(i);
            final PackageOfficialDTO packageOfficialDTO2 = packageOfficialDTOS.get(i+1);
            assertTrue(packageOfficialDTO.getDate().toString() +" "+packageOfficialDTO2.getDate().toString(),packageOfficialDTO.getDate().compareTo(packageOfficialDTO2.getDate()) >= 0);
        }
    }
    @Test
    void testPriceASC() {

        when(packageOfficialRepository.findAll()).thenReturn(packageOfficials);
        List<PackageOfficialDTO> packageOfficialDTOS = packageOfficialService.findAll(SortBy.SORT_BY_PRICE_ASC);
        for(int i = 0; i < packageOfficialDTOS.size(); i++) {
            if(i == packageOfficialDTOS.size() - 1) return;
            final PackageOfficialDTO packageOfficialDTO = packageOfficialDTOS.get(i);
            final PackageOfficialDTO packageOfficialDTO2 = packageOfficialDTOS.get(i+1);
            assertTrue(packageOfficialDTO.getDate().toString() +" "+packageOfficialDTO2.getDate().toString(),
                    Float.compare(packageOfficialDTO.getPrice(),packageOfficialDTO2.getPrice()) <= 0);
        }
    }
    @Test
    void testPriceDESC() {
        when(packageOfficialRepository.findAll()).thenReturn(packageOfficials);
        List<PackageOfficialDTO> packageOfficialDTOS = packageOfficialService.findAll(SortBy.SORT_BY_PRICE_DESC);
        for(int i = 0; i < packageOfficialDTOS.size(); i++) {
            if(i == packageOfficialDTOS.size() - 1) return;
            final PackageOfficialDTO packageOfficialDTO = packageOfficialDTOS.get(i);
            final PackageOfficialDTO packageOfficialDTO2 = packageOfficialDTOS.get(i+1);
            assertTrue(packageOfficialDTO.getDate().toString() +" "+packageOfficialDTO2.getDate().toString(),
                    Float.compare(packageOfficialDTO.getPrice(),packageOfficialDTO2.getPrice()) >= 0);
        }
    }
    @Test
    void testAttendanceASC() {

        when(packageOfficialRepository.findAll()).thenReturn(packageOfficials);
        List<PackageOfficialDTO> packageOfficialDTOS = packageOfficialService.findAll(SortBy.SORT_BY_NUM_ATTENDANCE_ASC);
        for(int i = 0; i < packageOfficialDTOS.size(); i++) {
            if(i == packageOfficialDTOS.size() - 1) return;
            final PackageOfficialDTO packageOfficialDTO = packageOfficialDTOS.get(i);
            final PackageOfficialDTO packageOfficialDTO2 = packageOfficialDTOS.get(i+1);
            assertTrue(packageOfficialDTO.getDate().toString() +" "+packageOfficialDTO2.getDate().toString(),
                    packageOfficialDTO.getNumberAttendance() <= packageOfficialDTO2.getNumberAttendance());
        }
    }
    @Test
    void testAttendanceDESC() {
        when(packageOfficialRepository.findAll()).thenReturn(packageOfficials);
        List<PackageOfficialDTO> packageOfficialDTOS = packageOfficialService.findAll(SortBy.SORT_BY_NUM_ATTENDANCE_DESC);
        for(int i = 0; i < packageOfficialDTOS.size(); i++) {
            if(i == packageOfficialDTOS.size() - 1) return;
            final PackageOfficialDTO packageOfficialDTO = packageOfficialDTOS.get(i);
            final PackageOfficialDTO packageOfficialDTO2 = packageOfficialDTOS.get(i+1);
            assertTrue(packageOfficialDTO.getDate().toString() +" "+packageOfficialDTO2.getDate().toString(),
                    packageOfficialDTO.getNumberAttendance() >= packageOfficialDTO2.getNumberAttendance());
        }
    }
}
