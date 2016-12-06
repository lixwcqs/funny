package com.cqs.mock;


import com.cqs.serilazable.Strategy;

import java.util.Date;
import java.util.Random;

/**
 * Created by cqs on 16-11-30.
 */
public class StrategyMock {
    private static Random random = new Random();

    public static Strategy randStrategy() {
        Strategy strategy = new Strategy();
        strategy.setUserId(random.nextInt(100));
        strategy.setInTrafficGuarantee(random.nextInt(1000));
        strategy.setInTrafficMax(random.nextInt(1000));
        strategy.setOutTrafficGuarantee(random.nextFloat() * 1000);
        strategy.setOutTrafficMax(random.nextFloat() * 1000);
        strategy.setCreateDate(new Date());
        strategy.setUpdateDate(new Date());
        return strategy;
    }
}
