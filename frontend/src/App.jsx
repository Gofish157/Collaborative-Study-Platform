import React, { useEffect, useState } from 'react'
import { getMe, logout } from './api'
import Login from './components/Login'
import Register from './components/Register'
import Groups from './components/Groups'
import './styles.css'

const App = () => {
    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)
    const [authMode, setAuthMode] = useState('login') // 'login' | 'register'

    useEffect(() => {
        async function fetchMe() {
            try {
                const res = await getMe()
                setUser(res.data)
            } catch (e) {
                setUser(null)
            } finally {
                setLoading(false)
            }
        }
        fetchMe()
    }, [])

    async function handleLogout() {
        logout()
        setUser(null)
    }

    async function reloadUser() {
        const res = await getMe()
        setUser(res.data)
    }

    if (loading) {
        return (
            <div className="full-center">
                <div className="card">Loading...</div>
            </div>
        )
    }

    if (!user) {
        return (
            <div className="full-center">
                <div className="auth-card">
                    <h1 className="title">Study Platform</h1>

                    <div className="auth-tabs">
                        <button
                            className={authMode === 'login' ? 'tab active' : 'tab'}
                            onClick={() => setAuthMode('login')}
                        >
                            Login
                        </button>
                        <button
                            className={authMode === 'register' ? 'tab active' : 'tab'}
                            onClick={() => setAuthMode('register')}
                        >
                            Register
                        </button>
                    </div>

                    {authMode === 'login' ? (
                        <Login onLogin={reloadUser} />
                    ) : (
                        <Register onRegister={reloadUser} />
                    )}
                </div>
            </div>
        )
    }

    return (
        <div>
            <div className="topbar">
                <div>Logged in as <strong>{user.name}</strong> ({user.email})</div>
                <button className="button secondary" onClick={handleLogout}>
                    Logout
                </button>
            </div>

            <Groups currentUser={user} />
        </div>
    )
}

export default App
