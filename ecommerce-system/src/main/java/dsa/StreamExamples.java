package dsa;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamExamples {

    public static void main(String[] args) {
        int[] numArr = {4, 3, 1, -3, 7};
        int sumAll;
        sumAll = Arrays.stream(numArr).filter(x -> (x > 0)).reduce(0, (sum, cnum) -> sum + cnum);
        System.out.println(sumAll);
        List<Integer> numList = Arrays.asList(1, 2, 5, 4, -9, 0);
        sumAll = numList.stream().map(num -> (int) num).filter(num -> (num > 0))
                .reduce(0, (previousSum, currNum) -> previousSum + currNum);
        System.out.println(sumAll);
        List<String> fruitList = Arrays.asList("apple", "banana", "apple");
        Map<String, Integer> fruitMap = fruitList.stream()
                .collect(Collectors.toMap(fruit -> fruit,
                        fruit -> 1,
                        (existing, newValue) -> existing + newValue
                ));
        System.out.println(fruitMap);
        System.out.println(fruitMap.getClass().getName());
    }
}
