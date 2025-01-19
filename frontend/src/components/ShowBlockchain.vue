<template>
  <div>
    <h2>Blockchain Data</h2>
    <pre>{{ formattedBlockchain }}</pre> <!-- отображаем отформатированные данные -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      blockchain: null
    };
  },
  computed: {
    formattedBlockchain() {
      if (this.blockchain) {
        // Форматируем данные перед выводом, например, разделяем строки по блокам
        return this.blockchain
          .replace(/Block/g, "\n\nBlock")  // Разбиваем по блокам для лучшей читаемости
          .replace(/\{index/g, "\n{index") // Новая строка перед каждой новой записью
          .replace(/Transaction/g, "\nTransaction"); // Разделяем транзакции для удобства
      }
      return '';
    }
  },
  mounted() {
    this.fetchBlockchain();
    setInterval(this.fetchBlockchain, 30000); // Запрос каждые 30 секунд
  },
  methods: {
    fetchBlockchain() {
      fetch('http://localhost:8080/api/chain')
        .then(response => response.text())  // Получаем текст
        .then(data => {
          this.blockchain = data;  // Обновляем данные как строку
        })
        .catch(error => {
          console.error('Error fetching blockchain:', error);
        });
    }
  }
};
</script>

<style scoped>
h2 {
  color: #2c3e50;
}
pre {
  text-align: left;
  background-color: #f4f4f4;
  padding: 20px;
  border-radius: 8px;
  white-space: pre-wrap;
  word-wrap: break-word;
  font-family: 'Courier New', Courier, monospace;
}
</style>
