import axios from 'axios'

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080'

const api = axios.create({
    baseURL: API_BASE,
})

// подставляем JWT, если есть
api.interceptors.request.use((cfg) => {
    const token = localStorage.getItem('jwt')
    if (token) cfg.headers.Authorization = `Bearer ${token}`
    return cfg
})

// --- AUTH ---

export async function login(email, password) {
    const res = await api.post('/api/auth/login', { email, password })
    const token =
        res.data?.token || res.data?.accessToken || res.data?.jwt || res.data
    if (token) localStorage.setItem('jwt', token)
    return res.data
}

export async function register(name, email, password) {
    return api.post('/api/auth/register', { name, email, password })
}

export function logout() {
    localStorage.removeItem('jwt')
}

// --- USER ---

export function getMe() {
    return api.get('/api/users/me')
}
export function updateMe(payload) {
    return api.put('/api/users/me', payload)
}
export function deleteMe() {
    return api.delete('/api/users/me')
}

// --- GROUPS ---

export function getGroups() {
    return api.get('/api/groups')
}

// backend ждёт @RequestParam name/description
export function createGroup(name, description) {
    return api.post('/api/groups', null, { params: { name, description } })
}

export function updateGroup(id, name, description) {
    return api.put(`/api/groups/${id}`, null, { params: { name, description } })
}

export function deleteGroup(id) {
    return api.delete(`/api/groups/${id}`)
}

// управление участниками по userId
export function addUserToGroup(groupId, userId) {
    return api.post(`/api/groups/${groupId}/users/${userId}`)
}

export function removeUserFromGroup(groupId, userId) {
    return api.delete(`/api/groups/${groupId}/users/${userId}`)
}

// --- TASKS ---

export function getTasks(groupId) {
    return api.get(`/api/groups/${groupId}/tasks`)
}

export function createTask(groupId, payload) {
    return api.post(`/api/groups/${groupId}/tasks`, payload)
}

export function updateTask(groupId, taskId, payload) {
    return api.put(`/api/groups/${groupId}/tasks/${taskId}`, payload)
}

export function deleteTask(groupId, taskId) {
    return api.delete(`/api/groups/${groupId}/tasks/${taskId}`)
}

export default api
