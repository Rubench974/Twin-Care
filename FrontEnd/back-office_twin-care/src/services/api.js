import axios from 'axios';

const api = axios.create({
  baseURL: '', // <-- DOIT ÊTRE VIDE (juste deux guillemets simples)
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;