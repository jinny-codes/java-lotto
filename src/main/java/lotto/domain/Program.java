package lotto.domain;

import java.util.List;
import java.util.Map;

public class Program {
    PrintMsg printMsg = new PrintMsg();
    View view = new View();
    LottoGenerator lottoGenerator = new LottoGenerator();
    int numOfLottos, bonus, money;
    List<Lotto> lottoList;
    List<Integer> winner;
    DrawLotto drawLotto;
    Map<Ranking, Integer> result;
    double profitRate;

    public void lottoSetup() {
        printMsg.printMoneyInputMsg();
        money = view.getMoneyInput();
        numOfLottos = money/1000;
        printMsg.printNumberOfLottosMsg(numOfLottos);
        lottoList = lottoGenerator.generateLottos(numOfLottos);
        for (Lotto lotto : lottoList) {
            lotto.printLotto();
        }
    }
    public void winSetup() {
        printMsg.printWinnerInputMsg();
        winner = view.getWinnerInput();
        printMsg.printBonusInputMsg();
        bonus = view.getBonusInput(winner);
    }
    public void result(List<Lotto> lottoList, List<Integer> winner, int bonus) {
        drawLotto = new DrawLotto(lottoList, winner, bonus);
        result = drawLotto.checkListOfLottos();
        printMsg.printResult(result);
        profitRate = drawLotto.calculateProfitRate(money);
        printMsg.printProfitRate(profitRate);
    }
}
