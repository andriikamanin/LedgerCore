import axios from "axios";

const API_URL = "http://localhost:8080/api"; // URL вашего API

export const getStatus = async () => {
  const response = await axios.get(`${API_URL}/status`);
  return response.data;
};

export const addTransaction = async (transaction) => {
  const response = await axios.post(`${API_URL}/transaction`, transaction);
  return response.data;
};

export const createBlock = async () => {
  const response = await axios.post(`${API_URL}/block`);
  return response.data;
};

export const getBalance = async (user) => {
  const response = await axios.get(`${API_URL}/balance`, { params: { user } });
  return response.data;
};

export const validateBlockchain = async () => {
  const response = await axios.get(`${API_URL}/validate`);
  return response.data;
};

export const getBlockchain = async () => {
  const response = await axios.get(`${API_URL}/chain`);
  return response.data;
};

export const createUser = async (address) => {
  const response = await axios.post(`${API_URL}/createUser`, { address });
  return response.data;
};

export const getTransactions = async () => {
  const response = await axios.get(`${API_URL}/transactions`);
  return response.data;
};