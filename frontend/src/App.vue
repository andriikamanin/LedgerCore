<template>
  <div id="app">
    <div v-if="!isLoggedIn">
      <h2>Введите ваше имя для входа</h2>
      <input type="text" v-model="username" placeholder="Ваше имя" />
      <button @click="login">Войти</button>
    </div>
    <div v-else>
      <h1>Добро пожаловать, {{ username }}</h1>
      <Deposit :username="username" @transaction-added="updateTransactions" />
      <TransactionTable :transactions="transactions" />
    </div>
  </div>
</template>

<script>
import Deposit from './components/TransactionDeposit.vue';
import TransactionTable from './components/BlockchainTable.vue';

export default {
  data() {
    return {
      username: '', // Имя пользователя
      isLoggedIn: false, // Состояние авторизации
      transactions: [] // Хранение списка операций
    };
  },
  components: {
    Deposit,
    TransactionTable
  },
  methods: {
    login() {
      if (this.username.trim() !== '') {
        this.isLoggedIn = true; // Устанавливаем авторизацию
        this.fetchTransactions(); // Загружаем текущие операции
      } else {
        alert('Введите корректное имя!');
      }
    },
    fetchTransactions() {
      fetch('http://localhost:8080/api/chain')
        .then(response => response.json())
        .then(data => {
          // Здесь мы парсим блоки и операции для таблицы
          this.transactions = this.parseBlockchain(data);
        })
        .catch(error => console.error('Ошибка при загрузке блокчейна:', error));
    },
    parseBlockchain(blockchainData) {
      // Парсим цепочку блоков в операции
      const operations = [];
      blockchainData.split('Цепочка блоков:')[1]
        .split('Block')
        .slice(1)
        .forEach(block => {
          const lines = block.split('\n');
          const blockIndex = lines[0].match(/index=(\d+)/)[1];
          const transactions = block.match(/Transaction\{sender='(.*?)', receiver='(.*?)', amount=(.*?)\}/g) || [];
          transactions.forEach(tx => {
            const [, sender, receiver, amount] = tx.match(/Transaction\{sender='(.*?)', receiver='(.*?)', amount=(.*?)\}/);
            operations.push({
              hash: blockIndex, // Условно номер блока
              sender,
              receiver,
              amount,
              currency: 'USD' // Фиксируем валюту как доллар
            });
          });
        });
      return operations;
    },
    updateTransactions() {
      this.fetchTransactions(); // Обновляем список операций
    }
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
