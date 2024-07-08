package org.mtz;

import java.util.ArrayList;
import java.util.Scanner;

public class Tools {
    public static int Input() {
        //防止玩家输入其他字符的输入数字方法
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println("请输入数字！");
            System.out.print("请输入：");
            return Input();
        }
    }

    public static int DealSum(ArrayList<Object> cards) {
        //处理手牌数据
        int Acount = 0;
        int playerSum = 0;

        for (Object o : cards) {
            if (o.equals("J") || o.equals("Q") || o.equals("K")) {
                playerSum += 10;
            } else if (!(o.equals("A"))) {
                playerSum += Integer.parseInt((String) o);
            } else if (o.equals("A")) {
                Acount++;
            }
        }
        if (Acount == 1) {
            if (playerSum < 11) {
                playerSum += 11;
            } else {
                playerSum++;
            }
        } else {
            playerSum += Acount;
        }

        return playerSum;
    }

    public static void PrintCards(ArrayList<Object> cards) {
        //输出玩家当前手牌
        System.out.println("现在的手牌是：");
        for (Object o : cards) {
            System.out.print(o + " ");
        }
        System.out.println();
    }
}
