import React, { useState } from 'react'
import styles from '../../styles/alert.module.scss'

const Alert = (props) => {
    const onClose = () => {
        props.onClose?.()
    }

    return (  
        <div
            className={styles.container}
            onClick={() => onClose()}
        >
            <div className={styles.board}>
                <h1>{props.data.title || "Oops..."}</h1>
                <p>{props.data.message || "Something went wrong."}</p>
                <button onClick={e => onClose()}>Close</button>
            </div>
        </div>
    )
}

export default Alert
