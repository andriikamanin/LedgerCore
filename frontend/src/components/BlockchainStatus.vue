<template>
  <div>
    <h1>Blockchain Service Status</h1>
    <p>{{ status }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      status: ''
    };
  },
  mounted() {
    this.getStatus();
  },
  methods: {
    async getStatus() {
      try {
        const response = await fetch('http://localhost:8080/api/status');
        const data = await response.text();
        this.status = data;
      } catch (error) {
        this.status = 'Error fetching status';
      }
    }
  }
};
</script>

<style scoped>
h1 {
  font-size: 2em;
}
</style>
