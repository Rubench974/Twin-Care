import axios from 'axios'

// Création de l'instance Axios pointant vers ton serveur Render
const api = axios.create({
  baseURL: 'https://twincare-t2xu.onrender.com', 
  headers: {
    'Content-Type': 'application/json'
  }
})

// Intercepteur : Ajoute automatiquement le "badge" (Token) à chaque requête
// C'est indispensable car tes collègues ont sécurisé les routes (TEST-03 du rapport !)
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

export default api