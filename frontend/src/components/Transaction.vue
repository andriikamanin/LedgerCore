<template>
  <div>
    <h1>Transaction</h1>
    <form @submit.prevent="addTransaction">
      <div>
        <label for="sender">Sender</label>
        <input v-model="transaction.sender" type="text" id="sender" required />
      </div>
      <div>
        <label for="receiver">Receiver</label>
        <input v-model="transaction.receiver" type="text" id="receiver" required />
      </div>
      <div>
        <label for="amount">Amount</label>
        <input v-model="transaction.amount" type="number" id="amount" required />
      </div>
      <button type="submit">Add Transaction</button>
    </form>
    <p>{{ responseMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      transaction: {
        sender: '',
        receiver: '',
        amount: 0
      },
      responseMessage: ''
    };
  },
  methods: {
    async addTransaction() {
      try {
        const response = await axios.post('http://localhost:8080/api/transaction', this.transaction);
        this.responseMessage = response.data;
      } catch (error) {
        console.error('Error adding transaction:', error);
        this.responseMessage = 'Error adding transaction';
      }
    }
  }
};
</script>
