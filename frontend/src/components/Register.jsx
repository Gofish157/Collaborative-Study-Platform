import React, { useState } from 'react'
import { register, login } from '../api'

export default function Register({ onRegister }) {
    const [name, setName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [err, setErr] = useState(null)
    const [ok, setOk] = useState(null)

    async function submit(e) {
        e.preventDefault()
        setErr(null)
        setOk(null)

        try {
            await register(name, email, password)
            // сразу логиним юзера теми же данными
            await login(email, password)
            setOk('Registered successfully')
            if (onRegister) {
                await onRegister()
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
                Name
                <input
                    className="input"
                    value={name}
                    onChange={e => setName(e.target.value)}
                    required
                />
            </label>

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
                Register
            </button>

            {err && <div className="error">{String(err)}</div>}
            {ok && <div className="ok">{ok}</div>}
        </form>
    )
}
