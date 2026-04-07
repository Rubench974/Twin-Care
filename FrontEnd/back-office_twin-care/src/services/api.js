import axios from 'axios';

const api = axios.create({
  // baseURL DOIT être vide maintenant !
  baseURL: '', 
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;