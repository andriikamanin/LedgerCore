const { defineConfig } = require('@vue/cli-service')

module.exports = {
  devServer: {
    port: 8081,  // Порт, на котором будет работать приложение Vue
    proxy: {
      '/api': {
        target: 'http://localhost:8080',  // Адрес вашего backend API
        changeOrigin: true,
        secure: false,
      }
    }
  }
}

