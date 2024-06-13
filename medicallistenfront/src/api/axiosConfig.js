// src/api/axiosConfig.js
import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080', // Adjust this to your backend's URL
});

export default instance;
