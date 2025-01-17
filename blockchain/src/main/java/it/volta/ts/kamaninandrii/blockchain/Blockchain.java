package it.volta.ts.kamaninandrii.blockchain;

import java.util.ArrayList;

public class Blockchain {
    private ArrayList<Block> chain;
    public Blockchain() {
        chain = new ArrayList<>();
        // Создаём генезис-блок (самый первый блок в цепи)
        chain.add(new Block(0, "0"));
    }

    public Block getLastBlock() {
        return chain.getLast();
    }

    public void addBlock(Block block) {
        block.addTransaction(new Transaction("SYSTEM", "User1", 1000)); // Пример транзакции
        chain.add(block);
    }

    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            // Проверка текущего хеша
            if (!current.getHash().equals(current.calculateHash())) {
                return false;
            }

            // Проверка предыдущего хеша
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }
        return true;
    }


    public String toString() {

        return chain.toString();
    }




}
