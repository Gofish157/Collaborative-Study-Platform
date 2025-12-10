import React, { useState } from 'react'
import { login } from '../api'

export default function Login({ onLogin }) {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [err, setErr] = useState(null)

    async function submit(e) {
        e.preventDefault()
        try {
            await login(email, password)
            setErr(null)
            if (onLogin) {
                await onLogin()
            } else {
                window.location.reload()
            }
        } catch (e) {
            setErr(e.response?.data || e.message)
        }
    }

    return (
        <form onSubmit={submit} className="form">
            <label className="label">
                Email
                <input
                    className="input"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    required
                />
            </label>

            <label className="label">
                Password
                <input
                    className="input"
                    type="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    required
                />
            </label>

            <button className="button primary" type="submit">
                Login
            </button>

            {err && <div className="error">{String(err)}</div>}
        </form>
    )
}
