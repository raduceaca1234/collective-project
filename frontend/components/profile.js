import React from 'react'
import { useSelector } from 'react-redux';

import styles from '../styles/me.module.scss';

const Profile = () => {

    const user = useSelector(state => (state.user))
    console.log(user)
    return (
        <div className={styles.content}>
            <h2>User data</h2>
            <h3 className={styles.h31}>First Name: {user.data.firstName}</h3>
            <h3 className={styles.h32}>Second Name: {user.data.lastName}</h3>
        </div>
    )
}

export default Profile
