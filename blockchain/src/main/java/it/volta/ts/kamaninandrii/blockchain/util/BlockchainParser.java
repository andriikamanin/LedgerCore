package it.volta.ts.kamaninandrii.blockchain.util;


import java.util.ArrayList;
import java.util.List;

public class BlockchainParser {

    public static class Transaction {
        private String hash;
        private String blockNumber;
        private String age;
        private String sender;
        private String receiver;
        private String amount;
        private String currency;

        public Transaction(String hash, String blockNumber, String age, String sender, String receiver, String amount, String currency) {
            this.hash = hash;
            this.blockNumber = blockNumber;
            this.age = age;
            this.sender = sender;
            this.receiver = receiver;
            this.amount = amount;
            this.currency = currency;
        }

        // Getters and Setters
        public String getHash() {
            return hash;
        }

        public String getBlockNumber() {
            return blockNumber;
        }

        public String getAge() {
            return age;
        }

        public String getSender() {
            return sender;
        }

        public String getReceiver() {
            return receiver;
        }

        public String getAmount() {
            return amount;
        }

        public String getCurrency() {
            return currency;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "hash='" + hash + '\'' +
                    ", blockNumber='" + blockNumber + '\'' +
                    ", age='" + age + '\'' +
                    ", sender='" + sender + '\'' +
                    ", receiver='" + receiver + '\'' +
                    ", amount='" + amount + '\'' +
                    ", currency='" + currency + '\'' +
                    '}';
        }
    }

    public static List<Transaction> parseBlockchainData(String rawData) {
        List<Transaction> transactions = new ArrayList<>();
        String[] blocks = rawData.split("Цепочка блоков:");

        if (blocks.length < 2) {
            return transactions; // Нет данных блокчейна
        }

        String[] blockList = blocks[1].split("Block");
        for (String block : blockList) {
            String blockNumber = extractValue(block, "index=(\\d+)");
            String hash = extractValue(block, "hash='([a-f0-9]+)'");

            String[] transactionList = block.split("Transaction\\{");
            for (int i = 1; i < transactionList.length; i++) {
                String tx = transactionList[i];
                String sender = extractValue(tx, "sender='(.+?)'");
                String receiver = extractValue(tx, "receiver='(.+?)'");
                String amount = extractValue(tx, "amount=(\\d+(\\.\\d+)?)");

                if (sender != null && receiver != null && amount != null) {
                    transactions.add(new Transaction(
                            hash,
                            blockNumber,
                            "Just now", // Пример возраста
                            sender,
                            receiver,
                            amount,
                            "USD" // Валюта по умолчанию
                    ));
                }
            }
        }

        return transactions;
    }

    private static String extractValue(String text, String regex) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(regex).matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}

