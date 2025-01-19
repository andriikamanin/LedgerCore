<template>
  <div>
    <h2>Таблица операций</h2>
    <table>
      <thead>
        <tr>
          <th>Hash</th>
          <th>Отправитель</th>
          <th>Получатель</th>
          <th>Сумма</th>
          <th>Валюта</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(transaction, index) in transactions" :key="index">
          <td>{{ transaction.hash }}</td>
          <td>{{ transaction.sender }}</td>
          <td>{{ transaction.receiver }}</td>
          <td>{{ transaction.amount }}</td>
          <td>{{ transaction.currency }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    transactions: {
      type: Array,
      required: true
    }
  }
};
</script>

<style>
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

th {
  background-color: #f2f2f2;
  font-weight: bold;
}
</style>
