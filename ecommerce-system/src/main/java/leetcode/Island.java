package leetcode;

import java.util.Arrays;
import java.util.List;

class Solution {
    public int numIslands(char[][] grid) {
        int count = 0;
        List<Integer> source = getSourceNode(grid);
        Integer x,y;
        x = source.get(0);
        y = source.get(1);
        return 0;
    }

    public static List<Integer> getSourceNode(char[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                if(grid[i][j] == '1'){
                    return Arrays.asList(i,j);
                }
            }
        }
        return null;
    }
}

public class Island {
        public static void main(String[] args){
            System.out.println("test");
        }

}
