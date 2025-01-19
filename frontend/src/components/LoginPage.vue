<template>
  <div>
    <h2>Регистрация пользователя</h2>
    <form @submit.prevent="registerUser">
      <div>
        <label for="username">Имя пользователя:</label>
        <input 
          id="username" 
          type="text" 
          v-model="username" 
          placeholder="Введите имя" 
          required 
        />
      </div>
      <button type="submit">Зарегистрироваться</button>
    </form>

    <!-- Показываем сгенерированный адрес после регистрации -->
    <div v-if="generatedAddress">
      <p>Ваш адрес: {{ generatedAddress }}</p>
    </div>

    <!-- Сообщение о статусе регистрации -->
    <div v-if="statusMessage">
      <p :class="statusClass">{{ statusMessage }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',            // Имя пользователя, которое вводит пользователь
      generatedAddress: '',    // Сгенерированный адрес, который вернет сервер
      statusMessage: '',       // Статус сообщения об успешной регистрации или ошибке
      statusClass: '',         // Класс для окраски статуса (успех или ошибка)
    };
  },
  methods: {
    // Метод для отправки данных на сервер
    registerUser() {
      if (!this.username) {
        alert('Пожалуйста, введите имя пользователя!');
        return;
      }

      // Отправляем имя пользователя на сервер, чтобы получить сгенерированный адрес
      fetch('http://localhost:8080/api/createUser', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          // Отправляем только имя пользователя, адрес генерируется на сервере
          username: this.username,
        })
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('Ошибка при регистрации');
          }
          return response.json();
        })
        .then(data => {
          this.generatedAddress = data.address;  // Получаем сгенерированный адрес
          this.statusMessage = 'Пользователь успешно зарегистрирован!';
          this.statusClass = 'success';  // Успешная регистрация
        })
        .catch(error => {
          console.error('Ошибка при регистрации пользователя:', error);
          this.statusMessage = 'Ошибка при регистрации. Попробуйте снова.';
          this.statusClass = 'error';  // Ошибка
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
