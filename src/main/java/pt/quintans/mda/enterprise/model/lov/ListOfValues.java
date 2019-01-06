package pt.quintans.mda.enterprise.model.lov;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import pt.quintans.mda.enterprise.model.entity.Element;

public class ListOfValues extends Element {
    protected List<Item> items = new ArrayList<Item>();
    private int keylen;
    private boolean numeric;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getKeylen() {
        return keylen;
    }

    public void setKeylen(int keyLength) {
        this.keylen = keyLength;
    }

    public boolean isNumeric() {
        return numeric;
    }

    public void setNumeric(boolean numeric) {
        this.numeric = numeric;
    }

    @Override
    public void pushWeight(Set<Object> set) {
        // TODO Auto-generated method stub

    }

    public static class Block {
        private int min;
        private int max;
        
        public Block(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
        
        public boolean isSingle(){
            return min == max;
        }
    }

    public List<Block> getNumericBlocks() {
        SortedSet<Integer> sorted = new TreeSet<Integer>();
        for (Item it : items) {
            int val = Integer.parseInt(it.getKey());
            sorted.add(val);
        }
        Block block = null;
        List<Block> blocks = new ArrayList<Block>();
        for (Integer val : sorted) {
            if(block == null || val != block.getMax() + 1) { // null or if it is NOT the next one, create block
                block = new Block(val, val);
                blocks.add(block);
            } else { // if it is the next one, update max boundary
                block.setMax(val);
            }
        }
        
        return blocks;
    }
}
