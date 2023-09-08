package com.example.spring.rest.api.service;

import com.example.core.enums.RunHorseRacingType;
import com.example.core.enums.SocketType;
import com.example.core.model.ResultData;
import com.example.core.repository.HorseRacingRepository;
import com.example.core.repository.HorseRacingResultRepository;
import com.example.core.repository.HorseRacingResultUserRepository;
import com.example.core.utils.WebSocketKey;
import com.example.spring.rest.api.model.HorseRacingDto;
import com.example.spring.rest.api.model.RunEverySecondHR;
import com.example.spring.rest.api.model.SocketModel;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class HorseRacingService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private HorseRacingResultUserRepository horseRacingResultUserRepository;

    @Autowired
    private HorseRacingResultRepository horseRacingResultRepository;

    @Autowired
    private HorseRacingRepository horseRacingRepository;

    public ResultData getAllHorseRacingByHorseRacingGroupId() {
        var resultData = new ResultData();
        try {
            var horseRacingDtoList = new ModelMapper().map(horseRacingRepository.getByHorseRacingGroupIdAndCurrentDateTime(),
                    new TypeToken<List<HorseRacingDto>>() {
                    }.getType());

            resultData.setData(horseRacingDtoList);
            return resultData;
        } catch (Exception e) {
            log.error("", e);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }


    @Scheduled(fixedDelay = 1000)
    public void runEverySeconds() {
        var localDateTime = LocalDateTime.now();
        var seconds = localDateTime.toLocalTime().getSecond();
        String type;
        if (seconds < 30) {
            type = RunHorseRacingType.PREPARE.getDescription();
        } else if (seconds < 40) {
            type = RunHorseRacingType.PROCESS.getDescription();
        } else {
            type = RunHorseRacingType.RESULT.getDescription();
        }

//        var model = new RunEverySecondHR(seconds, type, );
//        simpMessagingTemplate.convertAndSend(WebSocketKey.HORSE_RACING, new SocketModel<>(SocketType.NOTIFICATIONS_LUCKY_WHEEL, model));
    }

}
