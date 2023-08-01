package com.example.spring.rest.api.service;


import com.example.core.entity.LuckyResult;
import com.example.core.entity.PrizeGroup;
import com.example.core.entity.Prizes;
import com.example.core.enums.SocketType;
import com.example.core.repository.LuckyResultRepository;
import com.example.core.repository.PrizeGroupRepository;
import com.example.core.repository.PrizeRepository;
import com.example.core.utils.WebSocketKey;
import com.example.spring.rest.api.model.LuckyWheelModel;
import com.example.spring.rest.api.model.SocketModel;
import com.example.spring.rest.api.security.SecurityUtils;
import com.example.spring.rest.api.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    public PrizeGroup findFirstByCurrentDateTime() {
        var prizeGroup = prizeGroupRepository.findFirstByCurrentDateTime();
        prizeGroup.setPrizeList(prizeRepository.getAllPrizeByPrizeGroupId());
        return prizeGroup;
    }

    private Random rand = new Random();

    @Transactional(timeout = 5)
    public LuckyWheelModel spinWheel(Long prizeGroupId) {
        LuckyWheelModel luckyWheelModel = new LuckyWheelModel();
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        if (prizeGroupRepository.countByIdAndDateTime(prizeGroupId) < 1) {
            luckyWheelModel.setMessage("Giải thưởng không tồn tại hoặc hết hạn");
            return luckyWheelModel;
        }
        Long luckyNumber = (long) (rand.nextInt(100) + 1);
        Prizes prizes = prizeRepository.getPrizeGroupIdAndLuckNumber(prizeGroupId, luckyNumber);
        if (prizes == null || prizes.getQuantity() == 0) {
            luckyWheelModel.setMessage("Chúc bạn may mắn lần sau!");
            luckyWheelModel.setLuckyNumber(luckyNumber);
            return luckyWheelModel;
        }
        prizes.setQuantity(prizes.getQuantity() - 1);
        prizeRepository.save(prizes);

        LuckyResult luckyResult = new LuckyResult();
        luckyResult.setLuckNumber(luckyNumber);
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
        luckyWheelModel.setLuckyNumber(luckyNumber);
        Thread thread = new Thread(() -> {
            LuckyWheelModel model = luckyWheelModel;
            model.setLuckyNumber(null);
            model.setMessage(user.getName() + " đã trúng giải : " + messagePrize);
            simpMessagingTemplate.convertAndSend(WebSocketKey.LUCKY_WHEEL, new SocketModel<>(SocketType.NOTIFICATIONS_LUCKY_WHEEL, model));
        });
        thread.start();
        return luckyWheelModel;
    }
}
