package it.volta.ts.kamaninandrii.blockchain;

import it.volta.ts.kamaninandrii.blockchain.bean.Block;
import it.volta.ts.kamaninandrii.blockchain.bean.Transaction;
import it.volta.ts.kamaninandrii.blockchain.bean.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@Scope("singleton")
public class BlockchainManager {
    private final Blockchain blockchain;
    private final ArrayList<Transaction> pendingTransactions;
    private final HashMap<String, User> users;
    private final int MAX_TRANSACTIONS = 5; // Настраиваемый лимит транзакций в блоке
    private static final double miningReward = 50.0;

    public BlockchainManager() {
        blockchain = new Blockchain();
        pendingTransactions = new ArrayList<>();
        users = new HashMap<>();
        users.put("system", new User("system", 100000.0));  // Пользователь "system" с большим балансом для транзакций
    }

    public void createUser(String address) {
        if (!users.containsKey(address)) {
            users.put(address, new User(address, 0.0));
            System.out.println("Пользователь " + address + " создан.");
        } else {
            System.out.println("Пользователь с таким адресом уже существует.");
        }
        System.out.println("Текущие пользователи: " + users.keySet());
    }

    public User getUser(String address) {
        return users.get(address);
    }

    public Map<String, User> getAllUsers() {
        return users;
    }

    public void createBlock() {
        if (pendingTransactions.size() < MAX_TRANSACTIONS) {
            System.out.println("Недостаточно транзакций для создания блока.");
            return;
        }

        Block lastBlock = blockchain.getLastBlock();

        // Создаем новый блок
        Block newBlock = new Block(
                lastBlock != null ? lastBlock.getIndex() + 1 : 0,  // Индекс нового блока
                lastBlock != null ? lastBlock.getHash() : "0",     // Хеш предыдущего блока
                new ArrayList<>(pendingTransactions)               // Список транзакций для нового блока
        );

        // Добавляем транзакцию майнинга (вознаграждение)
        Transaction miningTransaction = new Transaction("system", "miner", miningReward);
        newBlock.getTransactions().add(miningTransaction);

        // Пересчитываем хеш блока после добавления всех транзакций
        newBlock.setHash(newBlock.calculateHash());

        // Добавляем новый блок в блокчейн
        blockchain.addBlock(newBlock);

        // Обновляем балансы пользователей, учитывая все транзакции в блоке
        for (Transaction transaction : newBlock.getTransactions()) {
            updateBalance(transaction);
        }

        // Очистка пула транзакций после добавления блока
        pendingTransactions.clear();

        System.out.println("Новый блок создан с " + newBlock.getTransactions().size() + " транзакциями.");
    }



    // Метод для обновления баланса пользователей
    private void updateBalance(Transaction transaction) {
        String sender = transaction.getSender();
        String receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        // Вычитаем сумму у отправителя
        if (users.containsKey(sender)) {
            User senderUser = users.get(sender);
            senderUser.setBalance(senderUser.getBalance() - amount);
        }

        // Добавляем сумму получателю
        if (users.containsKey(receiver)) {
            User receiverUser = users.get(receiver);
            receiverUser.setBalance(receiverUser.getBalance() + amount);
        }

        System.out.println("Баланс обновлен: " + sender + " -> " + receiver);
    }



    // Метод для добавления транзакции
    public void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
        System.out.println("Добавлена транзакция: " + transaction);
        System.out.println("Текущее количество неподтвержденных транзакций: " + pendingTransactions.size());

        // Если количество транзакций достигло лимита, создается новый блок
        if (pendingTransactions.size() >= MAX_TRANSACTIONS) {
            System.out.println("Лимит транзакций достигнут. Создается новый блок...");
            createBlock();
        }
    }

    // Обработка транзакции
    public void processTransaction(Transaction transaction) {
        String sender = transaction.getSender();
        String receiver = transaction.getReceiver();
        double amount = transaction.getAmount();

        if (!users.containsKey(sender) || !users.containsKey(receiver)) {
            System.out.println("Ошибка: один из пользователей не существует.");
            return;
        }

        if (getBalanceWithPending(sender) < amount) {
            System.out.println("Ошибка: недостаточно средств (учитывая неподтвержденные транзакции).");
            return;
        }

        addTransactionToPending(transaction); // Добавляем транзакцию в пул
        System.out.println("Транзакция добавлена в пул неподтвержденных транзакций.");

    }

    public void addTransactionToPending(Transaction transaction) {
        if (transaction != null) {
            pendingTransactions.add(transaction);
            System.out.println("Транзакция добавлена в пул неподтвержденных: " + transaction);
        } else {
            System.out.println("Ошибка: передана пустая транзакция!");
        }
    }

    public double getBalance(String user) {
        User u = users.get(user);
        return u != null ? u.getBalance() : 0.0;
    }

    // Метод для расчета баланса пользователя, учитывая неподтвержденные транзакции
    public double getBalanceWithPending(String user) {
        double balance = getBalance(user);
        for (Transaction transaction : pendingTransactions) {
            if (transaction.getSender().equals(user)) {
                balance -= transaction.getAmount();
            }
            if (transaction.getReceiver().equals(user)) {
                balance += transaction.getAmount();
            }
        }
        return balance;
    }

    public boolean isBlockchainValid() {
        return blockchain.isValid();
    }

    // Метод для вывода состояния блокчейна
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Баланс пользователей:\n");
        for (Map.Entry<String, User> entry : users.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue().getBalance()).append("\n");
        }

        sb.append("\nНеподтвержденные транзакции:\n");
        for (Transaction transaction : pendingTransactions) {
            sb.append(transaction).append("\n");
        }

        sb.append("\nИнформация о блоках:\n");
        sb.append(getAllBlocks()).append("\n");

        return sb.toString();
    }

    // Метод для получения информации о всех блоках
    public String getAllBlocks() {
        StringBuilder sb = new StringBuilder();
        sb.append("Информация о блоках:\n");
        for (Block block : blockchain.getChain()) { // Проходим по каждому блоку в цепочке
            sb.append("Блок ").append(block.getIndex()).append(":\n");

            sb.append("Previous Hash: ").append(block.getPreviousHash()).append("\n");
            sb.append("Hash: ").append(block.getHash()).append("\n");
            sb.append("Транзакции:\n");
            for (Transaction transaction : block.getTransactions()) { // Проходим по каждой транзакции в блоке
                sb.append(transaction).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}