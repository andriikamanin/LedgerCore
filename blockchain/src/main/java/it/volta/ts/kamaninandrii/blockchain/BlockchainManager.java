package it.volta.ts.kamaninandrii.blockchain;



import java.util.ArrayList;
import java.util.HashMap;

public class BlockchainManager {
    private Blockchain blockchain;
    private ArrayList<Transaction> pendingTransactions; // Список неподтвержденных транзакций
    private HashMap<String, Double> balances;           // Балансы пользователей

    public BlockchainManager() {
        blockchain = new Blockchain();                 // Инициализация блокчейна
        pendingTransactions = new ArrayList<>();       // Инициализация пула транзакций
        balances = new HashMap<>();                    // Инициализация балансов
    }

    /**
     * Регистрирует новую транзакцию в пуле.
     *
     * @param sender   Отправитель.
     * @param receiver Получатель.
     * @param amount   Сумма перевода.
     * @return true, если транзакция успешно добавлена.
     */
    public boolean addTransaction(String sender, String receiver, double amount) {
        // Проверка, хватает ли средств у отправителя
        if (!balances.containsKey(sender) || balances.get(sender) < amount) {
            System.out.println("Ошибка: недостаточно средств у отправителя " + sender);
            return false;
        }
        // Создание и добавление транзакции
        Transaction transaction = new Transaction(sender, receiver, amount);
        pendingTransactions.add(transaction);
        System.out.println("Транзакция добавлена: " + transaction);
        return true;
    }

    /**
     * Создает новый блок с текущими транзакциями и добавляет его в блокчейн.
     */
    public void createBlock() {
        if (pendingTransactions.isEmpty()) {
            System.out.println("Нет неподтвержденных транзакций для включения в блок.");
            return;
        }

        // Создаем новый блок
        Block lastBlock = blockchain.getLastBlock();
        Block newBlock = new Block(
                lastBlock != null ? lastBlock.getIndex() + 1 : 1,
                lastBlock != null ? lastBlock.getHash() : "0"
        );

        // Добавляем все неподтвержденные транзакции в блок
        for (Transaction transaction : pendingTransactions) {
            newBlock.addTransaction(transaction);
            // Обновляем балансы
            processTransaction(transaction);
        }

        // Очищаем пул транзакций
        pendingTransactions.clear();

        // Добавляем блок в блокчейн
        blockchain.addBlock(newBlock);
        System.out.println("Создан и добавлен новый блок: " + newBlock);
    }

    /**
     * Просматривает баланс конкретного пользователя.
     *
     * @param user Имя пользователя.
     * @return Баланс пользователя.
     */
    public double getBalance(String user) {
        return balances.getOrDefault(user, 0.0);
    }

    /**
     * Выводит текущее состояние блокчейна.
     */
    public void printBlockchain() {
        System.out.println("Текущее состояние блокчейна:");
        System.out.println(blockchain);
    }

    /**
     * Проверяет валидность цепочки блоков.
     *
     * @return true, если цепочка валидна, иначе false.
     */
    public boolean isBlockchainValid() {
        return blockchain.isValid();
    }

    /**
     * Выводит все текущие неподтвержденные транзакции.
     */
    public void printPendingTransactions() {
        System.out.println("Неподтвержденные транзакции:");
        for (Transaction transaction : pendingTransactions) {
            System.out.println(transaction);
        }
    }

    // ===== Вспомогательные методы =====

    /**
     * Обрабатывает транзакцию, обновляя балансы пользователей.
     *
     * @param transaction Транзакция для обработки.
     */
    private void processTransaction(Transaction transaction) {
        String sender = transaction.getSender();
        String receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        // Списываем у отправителя
        balances.put(sender, balances.getOrDefault(sender, 0.0) - amount);

        // Добавляем получателю
        balances.put(receiver, balances.getOrDefault(receiver, 0.0) + amount);
    }
}
