package com.example.spring.rest.api.service;


import com.example.core.entity.LuckyResult;
import com.example.core.entity.Prizes;
import com.example.core.enums.SocketType;
import com.example.core.model.ResultData;
import com.example.core.repository.LuckyResultRepository;
import com.example.core.repository.PrizeGroupRepository;
import com.example.core.repository.PrizeRepository;
import com.example.core.utils.WebSocketKey;
import com.example.spring.rest.api.model.LuckyWheelModel;
import com.example.spring.rest.api.model.PrizeDto;
import com.example.spring.rest.api.model.PrizeGroupDto;
import com.example.spring.rest.api.model.SocketModel;
import com.example.spring.rest.api.security.SecurityUtils;
import com.example.spring.rest.api.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class LuckyWheelService {

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    private PrizeGroupRepository prizeGroupRepository;

    @Autowired
    private LuckyResultRepository luckyResultRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public ResultData findFirstByCurrentDateTime() {

        ResultData resultData = new ResultData();
        try {
            ModelMapper modelMapper = new ModelMapper();
            var prizeGroup = prizeGroupRepository.findFirstByCurrentDateTime();
            if (prizeGroup != null) {
                var prizeGroupDto = modelMapper.map(prizeGroup, PrizeGroupDto.class);
                List<PrizeDto> prizeDto = modelMapper.map(prizeRepository.getAllPrizeByPrizeGroupId(), new TypeToken<List<PrizeDto>>() {
                }.getType());

                prizeGroupDto.setPrizeList(prizeDto);
                if (prizeGroupDto.getPrizeList().size() < 6) {
                    int addCount = 6 - prizeGroupDto.getPrizeList().size();
                    prizeGroupDto.getPrizeList().addAll(modelMapper.map(prizeRepository.getAllPrizeByPrizeGroupIdDefault(addCount), new TypeToken<List<PrizeDto>>() {
                    }.getType()));
                }
                Long i = 0l;
                for (var obj : prizeGroupDto.getPrizeList()) {
                    obj.setDisplayNumber(++i);
                }
                resultData.setData(prizeGroupDto);
            }
            return resultData;
        } catch (Exception e) {
            log.error("", e);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    private Random rand = new Random();

    @Transactional
    public ResultData spinWheel(Long prizeGroupId) {
        ResultData resultData = new ResultData();
        try {
            LuckyWheelModel luckyWheelModel = new LuckyWheelModel();
            UserPrincipal user = SecurityUtils.getCurrentIdLogin();
            if (prizeGroupRepository.countByIdAndDateTime(prizeGroupId) < 1) {
                luckyWheelModel.setMessage("Giải thưởng không tồn tại hoặc hết hạn");
                resultData.setData(luckyWheelModel);
                return resultData;
            }
            Long spinNumber = (long) (rand.nextInt(100) + 1);
            Prizes prizes = prizeRepository.getPrizeGroupIdAndLuckNumber(prizeGroupId, spinNumber);
            if (prizes == null || prizes.getQuantity() == 0) {
                prizes = prizeRepository.selectOneRandomNotLucky(prizeGroupId);
                luckyWheelModel.setSpinNumber(spinNumber);
                luckyWheelModel.setPrizeId(prizes.getId());
                luckyWheelModel.setMessage("Chúc bạn may mắn lần sau!");
                resultData.setData(luckyWheelModel);
                return resultData;
            }
            prizes.setQuantity(prizes.getQuantity() - 1);
            prizeRepository.save(prizes);

            LuckyResult luckyResult = new LuckyResult();
            luckyResult.setLuckNumber(spinNumber);
            luckyResult.setPrizeId(prizes.getId());
            luckyResult.setUserId(user.getId());
            luckyResult.setLastUpdateBy("SYSTEM");
            luckyResult.setLastApproveBy("SYSTEM");
            luckyResult.setStatus("A");
            luckyResult.setLastUpdateDate(new Date());
            luckyResult.setLastApproveDate(new Date());
            luckyResultRepository.save(luckyResult);
            luckyWheelModel.setPrizeId(prizes.getId());
            String messagePrize = prizes.getDescription();
            luckyWheelModel.setMessage("Chúc mừng bạn đã trúng giải : " + messagePrize);
            luckyWheelModel.setLuckyNumber(spinNumber);
            LuckyWheelModel model = luckyWheelModel;
            model.setMessage(user.getName() + " đã trúng giải : " + messagePrize);
            simpMessagingTemplate.convertAndSend(WebSocketKey.LUCKY_WHEEL, new SocketModel<>(SocketType.NOTIFICATIONS_LUCKY_WHEEL, model));
            resultData.setData(luckyWheelModel);
            return resultData;
        } catch (Exception e) {
            log.error("", e);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }

    }
}
