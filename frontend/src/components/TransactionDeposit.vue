<template>
  <div>
    <h2>Отправка средств</h2>
    <form @submit.prevent="sendTransaction">
      <div>
        <label for="receiver">Получатель:</label>
        <input 
          id="receiver" 
          type="text" 
          v-model="receiver" 
          placeholder="Получатель" 
          required 
        />
      </div>
      <div>
        <label for="amount">Сумма:</label>
        <input 
          id="amount" 
          type="number" 
          v-model="amount" 
          placeholder="Сумма" 
          required 
          min="0.01" 
        />
      </div>
      <button type="submit">Отправить</button>
    </form>

    <!-- Показываем статус транзакции -->
    <div v-if="transactionStatus">
      <p :class="transactionStatus.success ? 'success' : 'error'">
        {{ transactionStatus.message }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  props: ['username'],
  data() {
    return {
      receiver: '',            // Получатель транзакции
      amount: 0,               // Сумма для транзакции
      transactionStatus: null, // Статус транзакции
    };
  },
  methods: {
    // Метод для отправки транзакции на сервер
    sendTransaction() {
      if (!this.username) {
        alert('Пожалуйста, войдите, указав имя пользователя!');
        return;
      }

      if (this.amount <= 0) {
        alert('Сумма должна быть больше нуля!');
        return;
      }

      // Отправляем транзакцию на сервер
      fetch('http://localhost:8080/api/transaction', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          sender: this.username, // Имя отправителя
          receiver: this.receiver, // Имя получателя
          amount: this.amount,    // Сумма транзакции
        })
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Ошибка при отправке транзакции');
          }
          return response.text();
        })
        .then(response => {
          if (!response.ok) {
            throw new Error('Ошибка при отправке транзакции');
          }
          return response.text();
        })      
        .then(() => {
          this.transactionStatus = {
            success: true,
            message: 'Транзакция выполнена успешно!',
          };
        })

        .catch(error => {
          console.error('Ошибка при отправке транзакции:', error);
          this.transactionStatus = {
            success: false,
            message: 'Ошибка при отправке транзакции. Попробуйте снова.',
          };
        });
    }
  }
};
</script>

<style scoped>
.success {
  color: green;
}

.error {
  color: red;
}
</style>
