import React, { useEffect, useState } from 'react'
import {
    getGroups,
    createGroup,
    getTasks,
    createTask,
    updateTask,
    deleteTask,
    addUserToGroup,
    removeUserFromGroup,
} from '../api'
import TaskRow from './TaskRow'

const Groups = ({ currentUser }) => {
    const [groups, setGroups] = useState([])
    const [selected, setSelected] = useState(null)
    const [tasks, setTasks] = useState([])
    const [newGroupName, setNewGroupName] = useState('')
    const [newGroupDescription, setNewGroupDescription] = useState('')
    const [taskForm, setTaskForm] = useState({ title: '', description: '', deadline: '' })
    const [memberUserId, setMemberUserId] = useState('')

    useEffect(() => {
        fetchGroups()
    }, [])

    async function fetchGroups() {
        const res = await getGroups()
        // показываем только группы, которые создал текущий юзер
        const myGroups = res.data.filter(g => g.createdBy === currentUser.email)
        setGroups(myGroups)

        // если выбранная группа больше не в списке — сбросить
        if (selected && !myGroups.find(g => g.id === selected.id)) {
            setSelected(null)
            setTasks([])
        }
    }

    async function openGroup(g) {
        setSelected(g)
        const res = await getTasks(g.id)
        setTasks(res.data)
    }

    async function handleCreateGroup(e) {
        e.preventDefault()
        await createGroup(newGroupName, newGroupDescription)
        setNewGroupName('')
        setNewGroupDescription('')
        fetchGroups()
    }

    function normalizeDeadline(value) {
        if (!value) return null
        // datetime-local даёт 'YYYY-MM-DDTHH:mm'
        if (value.length === 16) {
            return value + ':00'
        }
        return value
    }

    async function handleCreateTask(e) {
        e.preventDefault()
        if (!selected) return
        const payload = {
            title: taskForm.title,
            description: taskForm.description,
            deadline: normalizeDeadline(taskForm.deadline),
        }
        await createTask(selected.id, payload)
        setTaskForm({ title: '', description: '', deadline: '' })
        const res = await getTasks(selected.id)
        setTasks(res.data)
    }

    async function handleEditTask(t) {
        const title = prompt('Title', t.title)
        if (!title) return

        const description = prompt('Description', t.description || '') || ''
        const status = prompt('Status', t.status || 'pending') || t.status

        let deadlineLocal = ''
        if (t.deadline) {
            try {
                const d = new Date(t.deadline)
                const iso = d.toISOString().slice(0, 16) // YYYY-MM-DDTHH:mm
                deadlineLocal = iso
            } catch {
                deadlineLocal = ''
            }
        }

        const newDeadlineLocal =
            prompt('Deadline (YYYY-MM-DDTHH:mm)', deadlineLocal) || ''
        const deadline = normalizeDeadline(newDeadlineLocal || null)

        await updateTask(selected.id, t.id, { title, description, status, deadline })
        const res = await getTasks(selected.id)
        setTasks(res.data)
    }

    async function handleDeleteTask(t) {
        if (!selected) return
        await deleteTask(selected.id, t.id)
        const res = await getTasks(selected.id)
        setTasks(res.data)
    }

    async function handleDeleteGroup(id) {
        if (!window.confirm('Delete this group?')) return
        await deleteGroup(id)
        fetchGroups()
    }

    async function handleAddUser() {
        if (!selected || !memberUserId) return
        await addUserToGroup(selected.id, memberUserId)
        setMemberUserId('')
        alert('User added to group (by id).')
    }

    async function handleRemoveUser() {
        if (!selected || !memberUserId) return
        await removeUserFromGroup(selected.id, memberUserId)
        setMemberUserId('')
        alert('User removed from group (by id).')
    }

    return (
        <div className="container">
            <div className="layout">

                {/* LEFT: GROUPS */}
                <div className="sidebar">
                    <div className="card">
                        <div className="header">My groups</div>

                        {groups.length === 0 && (
                            <div className="muted">
                                You have no groups yet. Create one below.
                            </div>
                        )}

                        {groups.map(g => (
                            <div key={g.id} className="card group-card">
                                <div className="row">
                                    <div>
                                        <strong>{g.name}</strong>
                                        <div className="muted small">{g.description}</div>
                                    </div>
                                    <div className="row" style={{ gap: 4 }}>
                                        <button
                                            className="button secondary"
                                            onClick={() => openGroup(g)}
                                        >
                                            Open
                                        </button>
                                        <button
                                            className="button danger"
                                            onClick={() => handleDeleteGroup(g.id)}
                                        >
                                            ✕
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div className="card">
                        <div className="header">Create group</div>
                        <form onSubmit={handleCreateGroup} className="form-compact">
                            <input
                                className="input"
                                placeholder="Name"
                                value={newGroupName}
                                onChange={e => setNewGroupName(e.target.value)}
                                required
                            />
                            <input
                                className="input"
                                placeholder="Description"
                                value={newGroupDescription}
                                onChange={e => setNewGroupDescription(e.target.value)}
                            />
                            <button className="button primary" type="submit">
                                Create
                            </button>
                        </form>
                    </div>
                </div>

                {/* RIGHT: TASKS */}
                <div className="content">
                    {selected ? (
                        <>
                            <div className="card">
                                <div className="header">
                                    Group: {selected.name}
                                    <span className="muted small" style={{ marginLeft: 8 }}>
                    (created by {selected.createdBy})
                  </span>
                                </div>

                                {tasks.length === 0 && (
                                    <div className="muted">No tasks yet</div>
                                )}

                                {tasks.map(t => (
                                    <TaskRow
                                        key={t.id}
                                        t={t}
                                        onEdit={handleEditTask}
                                        onDelete={handleDeleteTask}
                                    />
                                ))}
                            </div>

                            <div className="card">
                                <div className="header">Create task</div>
                                <form onSubmit={handleCreateTask} className="form-compact">
                                    <input
                                        className="input"
                                        placeholder="Title"
                                        value={taskForm.title}
                                        onChange={e =>
                                            setTaskForm({ ...taskForm, title: e.target.value })
                                        }
                                        required
                                    />
                                    <input
                                        className="input"
                                        placeholder="Description"
                                        value={taskForm.description}
                                        onChange={e =>
                                            setTaskForm({ ...taskForm, description: e.target.value })
                                        }
                                    />
                                    <label className="label small">
                                        Deadline
                                        <input
                                            className="input"
                                            type="datetime-local"
                                            value={taskForm.deadline}
                                            onChange={e =>
                                                setTaskForm({ ...taskForm, deadline: e.target.value })
                                            }
                                        />
                                    </label>
                                    <button className="button primary" type="submit">
                                        Create task
                                    </button>
                                </form>
                            </div>

                            <div className="card">
                                <div className="header">Manage group members (by user id)</div>
                                <div className="form-compact">
                                    <input
                                        className="input"
                                        placeholder="User ID"
                                        value={memberUserId}
                                        onChange={e => setMemberUserId(e.target.value)}
                                    />
                                    <div className="row" style={{ gap: 8 }}>
                                        <button className="button secondary" onClick={handleAddUser}>
                                            Add
                                        </button>
                                        <button className="button danger" onClick={handleRemoveUser}>
                                            Remove
                                        </button>
                                    </div>
                                    <div className="muted small">
                                        (Да, пока по id. Для продакшена здесь был бы поиск по email.)
                                    </div>
                                </div>
                            </div>
                        </>
                    ) : (
                        <div className="card muted">
                            Select a group on the left to see its tasks.
                        </div>
                    )}
                </div>

            </div>
        </div>
    )
}

export default Groups
