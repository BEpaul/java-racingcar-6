package racingcar;

import static racingcar.InputReader.*;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RacingGame {

    private List<String> carNameList;
    private List<Car> cars;
    private int trialTimes;


    public void play() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        carNameList = inputCarNames();
        cars = new ArrayList<>();
        for (String car : carNameList) {
            cars.add(new Car(car));
        }
        System.out.println("시도할 회수는 몇회인가요?");
        trialTimes = inputTrialTimes();

        System.out.println("실행 결과");
        for (int i = 0; i < trialTimes; i++) {
            moveForward();
            printCurrentPosition();
        }

        String winner = findWinner();
        System.out.println("최종 우승자 : " + winner);
    }

    private void carNamesValidation(String carNames, List<String> carNameList) {
        checkHasOverFiveChar(carNames, carNameList);
        checkHasEmptyName(carNames, carNameList);
    }

    private void checkHasOverFiveChar(String carNames, List<String> carNameList) {
        if (carNameList.size() != carNames.split(",").length) {
            throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다.");
        }
    }

    private void checkHasEmptyName(String carNames, List<String> carNameList) {
        if (carNameList.size() != countChar(carNames, ',') + 1) {
            throw new IllegalArgumentException("자동차 이름을 빈 문자열로 정할 수 없습니다.");
        }
    }

    private long countChar(String str, char ch) {
        return str.chars()
                .filter(c -> c == ch)
                .count();
    }

    private int checkIsPositiveInteger(String inputTrialTimes) {
        try {
            int trialTimes = Integer.parseInt(inputTrialTimes);
            if (trialTimes <= 0) {
                throw new IllegalArgumentException("시도 회수는 양의 정수여야 합니다.");
            }
            return trialTimes;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시도 회수는 양의 정수여야 합니다.");
        }
    }

    private void moveForward() {
        for (Car car : cars) {
            car.moveOrNot();
        }
    }
    private void printCurrentPosition() {
        for (Car car : cars) {
            System.out.println(car.getName() + " : " + "-".repeat(car.getPosition()));
        }
        System.out.println();
    }

    private String findWinner() {
        int winnerPosition = getWinnerPosition();

        ArrayList<String> winnerList = new ArrayList<>();
        for (Car car : cars) {
            if (winnerPosition == car.getPosition()) {
                winnerList.add(car.getName());
            }
        }
        return winnerListToString(winnerList);
    }

    private int getWinnerPosition() {
        int winnerPosition = -1;
        for (Car car : cars) {
            winnerPosition = Math.max(winnerPosition, car.getPosition());
        }

        return winnerPosition;
    }

    private String winnerListToString(List<String> winnerList) {
        return String.join(", ", winnerList);
    }
}


