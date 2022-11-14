package lotto.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DrawLotto {
    List<Lotto> lottoList;
    List<Integer> winner;
    Ranking rank;
    int bonus;
    public DrawLotto(List<Lotto> lottoList, List<Integer> winner, int bonus) {
        this.lottoList = lottoList;
        this.winner = winner;
        this.bonus = bonus;
    }
    public int checkLotto(Lotto lotto) {
        int matchCounts = 0;
        for (int value : winner) {
            if (lotto.contains(value)) {
                matchCounts++;
            }
        }
        return matchCounts;
    }
    public boolean checkBonus(Lotto lotto) {
        if (checkLotto(lotto)==5 && lotto.contains(bonus)) {
            return true;
        }
        return false;
    }
    private Ranking getRank(Lotto lotto) {
        int match = checkLotto(lotto);
        boolean bonusMatch = checkBonus(lotto);
        return  Arrays.stream(Ranking.values())
                .filter(l -> l.getMatch()==(match))
                .filter(l -> l.getBonusMatch()==(bonusMatch))
                .findFirst()
                .get();
    }
    public Map<Ranking, Integer> checkListOfLottos() {
        Map<Ranking, Integer> comparison = setupComparison();
        Ranking rank;
        for (Lotto lotto : lottoList) {
            rank = getRank(lotto);
            comparison.put(rank, comparison.get(rank) + 1);
        }
        return comparison;
    }
    private Map<Ranking, Integer> setupComparison() {
        Map<Ranking, Integer> comparison = new HashMap<Ranking, Integer>();
        for (Ranking rank : Ranking.values()) {
            comparison.put(rank, 0);
        }
        return comparison;
    }
    private double calculateProfit() {
        Map<Ranking, Integer> comparison = checkListOfLottos();
        double profit = 0.0;
        for (Ranking rank : Ranking.values()) {
            profit += rank.getPrize()*comparison.get(rank);
        }
        return profit;
    }
    public double calculateProfitRate(float money) {
        double profitRate = calculateProfit()/money*100;
        return Math.round(profitRate*100)/100.0;
    }
}
