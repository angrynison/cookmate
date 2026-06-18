package pantry.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pantry.PantryRepository;
import pantry.dto.PantryRequestDto;
import pantry.dto.PantryResponseDto;
import pantry.service.PantryService;

import java.time.LocalDate;

@Service
@Transactional
public class PantryServiceImpl implements PantryService {

    private final PantryRepository pantryRepository;

    public PantryServiceImpl(PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    // DashBoard 화면 구성 데이터 반환 메소드
    @Override
    public PantryResponseDto.SummaryResponse getPantrySummary(Long memberId) {

        LocalDate soonDate = LocalDate.now().plusDays(3);

        // 전체 count
        Long totalItems = pantryRepository.countByMemberId(memberId);

        // 유통기한 임박 count
        Long oldItems = pantryRepository.countBysoonDate(memberId, soonDate);

        // 신선한 재료 count
        Long freshItems = totalItems - oldItems;

        return new PantryResponseDto.SummaryResponse(totalItems,oldItems,freshItems);
    }

    @Override
    public void addPantry(PantryRequestDto pantryRequestDto) {

    }


}
