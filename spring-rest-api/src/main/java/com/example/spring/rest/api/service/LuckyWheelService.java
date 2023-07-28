package com.example.spring.rest.api.service;


import com.example.core.entity.LuckyResult;
import com.example.core.entity.Prizes;
import com.example.core.repository.LuckyResultRepository;
import com.example.core.repository.PrizeGroupRepository;
import com.example.core.repository.PrizeRepository;
import com.example.spring.rest.api.model.ResultData;
import com.example.spring.rest.api.security.SecurityUtils;
import com.example.spring.rest.api.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Prizes> getAllPrizeByCurrentDate() {
        return prizeRepository.getAllPrizeByCurrentDate();
    }

    private Random rand = new Random();

    @Transactional(timeout = 5)
    public ResultData spinWheel(Long prizeGroupId) {
        ResultData resultData = new ResultData();
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        if (prizeGroupRepository.countByIdAndDateTime(prizeGroupId) < 1) {
            resultData.setCode("ERROR");
            resultData.setCode("Giải thưởng không tồn tại hoặc hết hạn");
            return resultData;
        }
        Long luckyNumber = (long) (rand.nextInt(100) + 1);
        Prizes prizes = prizeRepository.getPrizeGroupIdAndLuckNumber(prizeGroupId, luckyNumber);
        if (prizes == null || prizes.getQuantity() == 0) {
            resultData.setCode("");
            resultData.setMessage("Chúc bạn may mắn lần sau!");
            resultData.setData(luckyNumber);
            return resultData;
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
        resultData.setData(prizes.getId());
        return resultData;
    }
}
