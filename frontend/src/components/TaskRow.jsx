import React from 'react'

const TaskRow = ({ t, onEdit, onDelete }) => {
    let deadlineText = ''
    if (t.deadline) {
        try {
            const d = new Date(t.deadline)
            deadlineText = d.toLocaleString()
        } catch {
            deadlineText = t.deadline
        }
    }

    return (
        <div className="task-row">
            <div>
                <strong>{t.title}</strong>{' '}
                <span className="badge">{t.status}</span>
                {t.description && (
                    <div className="muted small">{t.description}</div>
                )}
                {deadlineText && (
                    <div className="muted small">Deadline: {deadlineText}</div>
                )}
            </div>
            <div className="row" style={{ gap: 4 }}>
                <button className="button secondary" onClick={() => onEdit(t)}>
                    Edit
                </button>
                <button className="button danger" onClick={() => onDelete(t)}>
                    Delete
                </button>
            </div>
        </div>
    )
}

export default TaskRow
