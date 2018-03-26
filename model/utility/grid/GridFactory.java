package edu.utep.cs.cs4330.sudoku.model.utility.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Jesus Chavez
 * @macuser: aex on 3/25/18.
 */

public class GridFactory {
    private final static int DIFFICULTY_EASY = 0;
    private final static int DIFFICULTY_MEDIUM = 1;
    private final static int DIFFICULTY_HARD = 2;
    private final static List<Integer> DIFICULTY_SET = new ArrayList<>();{
        DIFICULTY_SET.add(DIFFICULTY_EASY);
        DIFICULTY_SET.add(DIFFICULTY_MEDIUM);
        DIFICULTY_SET.add(DIFFICULTY_HARD);
    }
    private final static int REGION_FOUR_BY_FOUR = 4;
    private final static int REGION_THREE_BY_THREE = 3;
    private final static List<Integer> LENGTH_SET = new ArrayList<>();{
        LENGTH_SET.add(REGION_FOUR_BY_FOUR);
        LENGTH_SET.add(REGION_THREE_BY_THREE);
    }
    private final static Tuple GRID_EASY_THREE = new Tuple(DIFFICULTY_EASY, REGION_THREE_BY_THREE);
    private final static Tuple GRID_EASY_FOUR = new Tuple(DIFFICULTY_EASY, REGION_FOUR_BY_FOUR);
    private final static Tuple GRID_MEDIUM_THREE = new Tuple(DIFFICULTY_MEDIUM, REGION_THREE_BY_THREE);
    private final static Tuple GRID_MEDIUM_FOUR = new Tuple(DIFFICULTY_MEDIUM, REGION_FOUR_BY_FOUR);
    private final static Tuple GRID_HARD_THREE = new Tuple(DIFFICULTY_HARD, REGION_THREE_BY_THREE);
    private final static Tuple GRID_HARD_FOUR = new Tuple(DIFFICULTY_HARD, REGION_FOUR_BY_FOUR);
    private final static Map<Tuple, List<Square>> GRID_COMBINATIONS = Collections.synchronizedMap(new HashMap<>());{
        try {
            GRID_COMBINATIONS.put(GRID_EASY_THREE, new SimpleGrid<>(GRID_EASY_THREE.len()));
            GRID_COMBINATIONS.put(GRID_EASY_FOUR, new SimpleGrid<>(GRID_EASY_FOUR.len()));
            GRID_COMBINATIONS.put(GRID_MEDIUM_THREE, new NormalGrid(GRID_MEDIUM_THREE.len()));
            GRID_COMBINATIONS.put(GRID_MEDIUM_FOUR, new NormalGrid(GRID_MEDIUM_FOUR.len()));
            GRID_COMBINATIONS.put(GRID_HARD_THREE, new HardGrid<>(GRID_EASY_THREE.len()));
            GRID_COMBINATIONS.put(GRID_HARD_FOUR, new HardGrid<>(GRID_EASY_FOUR.len()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Tuple getSelectorTuple(int difficulty, int len) throws Exception {
        if(verifyDifficulty(difficulty) && verifyLen(len)){
            return new Tuple(difficulty, len);
        }
        else throw new Exception("wrong selector");
    }

    private static boolean verifyDifficulty(int difficulty) throws Exception {
        if(DIFICULTY_SET.contains(difficulty))
            return true;
        else throw new Exception("wrong selector");
    }
    private static boolean verifyLen(int len) throws Exception {
        if(LENGTH_SET.contains(len))
            return true;
        else throw new Exception("wrong selector");
    }
    public List<Square> get(int difficulty, int len) throws Exception {
        Tuple difficultySizeCombination = getSelectorTuple(difficulty, len);
        return GRID_COMBINATIONS.get(difficultySizeCombination);
    }

}
