package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
    public static int[][] merge(int[][] intervals) {
        // sorted based on lower time
//        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0],b[0]));
        Arrays.sort(intervals, (a,b) -> a[0]-b[0]);
        int[] prev_interval = null, interval;
//        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < intervals.length; i++){
            interval = intervals[i];
            if(prev_interval == null){
                prev_interval = interval;
            }
            if (prev_interval[0] <= interval[0] &&  interval[0] <= prev_interval[1]){
                prev_interval[1] = Math.max(prev_interval[1], interval[1]);
            }
            else{
                result.add(Arrays.asList(prev_interval[0], prev_interval[1]));
                prev_interval = interval;
            }
        }
        if(prev_interval!=null) {
            result.add(Arrays.asList(prev_interval[0], prev_interval[1]));
        }
//        for(int i=0; i<result.size(); i++){
//            List<Integer> tmp = result.get(i);
//            for(int j=0; j< tmp.size(); j++){
//                ans[i][j] = tmp.get(j);
//            }
//        }

//        int[][] ans = result.toArray(new int[result.size()][2]);
        int[][] ans = result.stream().map(l -> l.stream().mapToInt(x -> x).toArray())
                        .toArray(int[][]::new);

        return ans;

    }

    public static void main(String[] args){
        int[][] intervals = {
                {1, 3},
                {8, 10},
                {15, 18},
                {2, 6}

        };
        // Sorting the intervals based on the first element
//        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
//
        intervals = merge(intervals);
        // Printing the sorted intervals
        System.out.println("Sorted intervals:");
        for(int[] interval: intervals){
            System.out.println(Arrays.toString(interval));
        }
//        List<List<int[]>> testList = new ArrayList<>();
//        List<int[]> internalList = new ArrayList<>();
//        internalList.add(Arrays.asList(1,2).toArray());

    }
}
