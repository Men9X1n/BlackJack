package org.mtz;

import java.util.ArrayList;
import java.util.Random;

import static org.mtz.Tools.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("规则介绍：\n" +
                "每局开始时，庄家会先抽一张牌。\n" +
                "然后玩家会被自动分配两张牌，之后可以选择继续抽牌或停牌。\n" +
                "花牌都视作10点，A可以视作1点或者11点。\n" +
                "如果抽到21点以上则视为爆牌，会自动停牌。\n" +
                "庄家抽牌总和达到17点或以上时会停牌。\n" +
                "最后比较大小，点数更大并且没有爆牌的一方获胜。\n" +
                "若玩家连续抽到第7张牌并且没有爆牌，那么玩家获胜。\n" +
                "自动发牌阶段达到21点为黑杰克，会获得1.5倍奖励。");

        //赌注
        int totalMoney = 10000;
        System.out.println("您的初始筹码为：" + totalMoney);
        int zeroCount = 0;//玩家输入0赌注计数器

        int round = 0;
        while (true) {
            if (totalMoney == 0) {
                System.out.println("您破产了，请下辈子再来");
                Thread.sleep(4000);
                return;
            }

            round++;
            System.out.println("第" + round + "局");

            //下注
            System.out.println("您当前剩余筹码：" + totalMoney);
            System.out.println("请输入您想下注的数额：");
            int money;
            while ((money = Input()) == 0) {
                zeroCount++;
                if (zeroCount == 3) {//如果玩家输入3次0赌注，游戏直接结束
                    System.out.println("不想玩就滚！");
                    Thread.sleep(1000);
                    return;
                }
                System.out.println("请下注！");
            }
            if (money > totalMoney) {
                System.out.println("你没有这么多钱，滚出我们赌场！");
                Thread.sleep(4000);
                return;
            } else {
                System.out.println("您本局下注：" + money);
            }
            totalMoney -= money;

            Random random = new Random();
            String[] model = new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

            //庄家先抽一张牌
            ArrayList<Object> dealerCards = new ArrayList<>();
            int firstNum = random.nextInt(12);
            dealerCards.add(model[firstNum]);
            System.out.println("庄家当前手牌：");
            System.out.println(model[firstNum]);

            //玩家
            ArrayList<Object> cards = new ArrayList<>();
            boolean isSeven = false;//是否抽到7次的指示器
            boolean isBlackJack = false;//是否为BlackJack的指示器

            System.out.println("自动发牌中···");
            for (int j = 0; j < 2; j++) {
                int twice = random.nextInt(12);
                cards.add(model[twice]);
            }
            Thread.sleep(1500);

            //处理玩家手牌数据
            int playerSum = DealSum(cards);
            System.out.println("现在的手牌是：");
            PrintCards(cards);

            if (playerSum == 21) {
                isBlackJack = true;
            }

            if (isBlackJack) {
                System.out.println("Black Jack!");
            } else {
                System.out.println("1.抽牌\n2.停牌\n请输入：");
                int input = Input();
                int i = 2;//抽牌次数

                while (input != 2) {
                    i++;
                    int num = random.nextInt(12);
                    cards.add(model[num]);
                    PrintCards(cards);

                    playerSum = DealSum(cards);

                    if (playerSum > 21) {
                        System.out.println("爆牌");
                        playerSum = 0;
                        Thread.sleep(2000);
                        break;
                    } else if (playerSum == 21) {//玩家21点自动停牌
                        System.out.println("21点！");
                        Thread.sleep(2000);
                        break;
                    } else if (i == 7) {
                        isSeven = true;
                        break;
                    }

                    System.out.println("1.抽牌\n2.停牌\n请输入：");
                    input = Input();
                }
            }
            System.out.println("庄家开始抽牌");
            Thread.sleep(2000);

            //庄家
            int dealerSum = 0;
            while (true) {
                if (isSeven) {
                    System.out.println("玩家已抽到7张牌");
                    break;
                }

                int dealerNum = random.nextInt(12);
                dealerCards.add(model[dealerNum]);

                System.out.println("庄家当前手牌：");
                for (Object o : dealerCards) {
                    System.out.print(o + " ");
                }
                System.out.println();

                //处理庄家手牌数据
                dealerSum = DealSum(dealerCards);

                if (dealerSum >= 17) {
                    System.out.println("庄家停止抽牌");
                    if (dealerSum > 21) {
                        System.out.println("庄家爆牌");
                        dealerSum = 0;
                    } else {
                        System.out.println("庄家的手牌总和为：" + dealerSum);
                    }
                    break;
                }
                Thread.sleep(2000);
            }
            Thread.sleep(2000);

            //开始比较
            if (isBlackJack) {
                System.out.println("\n玩家获胜，黑杰克1.5倍奖励！\n");
                totalMoney += money * 3;
            } else if (isSeven) {
                System.out.println("\n玩家获胜\n");
                totalMoney += money * 2;
            } else if (playerSum > dealerSum) {
                System.out.println("\n玩家获胜\n");
                totalMoney += money * 2;
            } else if (playerSum == dealerSum) {
                System.out.println("\n平局\n");
                totalMoney += money;
            } else {
                System.out.println("\n庄家获胜\n");
            }
        }

    }
}