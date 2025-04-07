package com.example.SchoolLunchReport.domain.statistics;

import com.example.SchoolLunchReport.statistics.domain.type.PeriodType;
import com.example.SchoolLunchReport.statistics.support.RankScheduler;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RankSchedulerTest {

    @Autowired
    private RankScheduler rankScheduler;

    @Test
    public void 주간_랭크_계산_테스트() {
        // 테스트용 날짜 설정
        LocalDate registerDate = LocalDate.of(2025, 4, 6); // 일요일
        LocalDate preDate = registerDate.minusWeeks(1);

        rankScheduler.calculateAndSaveRank(PeriodType.WEEKLY, registerDate, preDate);

        // 결과 확인은 DB 확인 or 로그 확인
    }

    @Test
    public void 월간_랭크_계산_테스트() {
        LocalDate registerDate = LocalDate.of(2025, 4, 1); // 4월 1일
        LocalDate preDate = registerDate.minusMonths(1);

        rankScheduler.calculateAndSaveRank(PeriodType.MONTHLY, registerDate, preDate);
    }
}
