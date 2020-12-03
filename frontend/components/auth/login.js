import React, { useState, useEffect } from 'react'
import { useRouter } from 'next/router'
import { useDispatch, useSelector } from 'react-redux'
import { actions as userActions } from '../../redux/user/actions'
import styles from '../../styles/register.module.scss'

import AuthAPI from '../../api/auth'

const LogIn = (props) =>{
    const router = useRouter()
    const dispatch = useDispatch()
    const user = useSelector(state => state.user)

    useEffect(() => {
        if(user.logged_in === true) {
            router.push('/')
        }
    }, [user.logged_in])

    const [formData, setFormData] = useState()

    const updateInput = e =>{
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        })
    }

    const setLoginSuccessful = (data) => {
        dispatch({
            type: userActions.USER_SET_TOKEN,
            payload: data.token,
        })
        dispatch({
            type: userActions.USER_SET_DATA,
            payload: {
                firstName: data.firstName,
                lastName: data.lastName,
            }
        })
        dispatch({
            type: userActions.USER_SET_AUTH,
            payload: true,
        })
    }

    const handleSubmit = event =>{
        event.preventDefault()

        let form = new FormData(event.target)

        if(!validateEmail(form.get('email'))){
            props.onAlert({
                type: 'error',
                message: 'Please, enter a valid email address.',
            })
            return;
        }
        if(!form.get('password')) {
            props.onAlert({
                type: 'error',
                message: 'Please, enter your password.',
            })
            return;
        }

        AuthAPI.signIn({
            email: form.get('email'),
            password: form.get('password')
        })
        .then(res => {
            setLoginSuccessful(res)

            props.onAlert({
                message: `Welcome, ${res.firstName}!`,
            })

            router.push('/')
        })
        .catch(err => {
            console.log(err)

            if(err.status === 404) {
                props.onAlert({
                    type: 'error',
                    message: 'No account found by that email or password.',
                })
            } else {
                props.onAlert({
                    type: 'error',
                    message: 'Something went wrong. Please, try again.',
                })
            }
        })
    }
    const validateEmail=(email)=>{
        /*
        Source: https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript
        */
        const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(String(email).toLowerCase());
    }

    return(
        <div  className={styles.main_div}>
            <h1 className={styles.h1}>Sign In</h1>
            <form onSubmit={handleSubmit} className={styles.form}>

                <p className={styles.text}>Email</p>
                <input className={styles.input}
                type="text"
                name="email"
                onChange={updateInput}
                value={formData?.email || ''}
                />

                <p className={styles.text}>Password</p>
                <input className={styles.input}
                type="password"
                name="password"
                onChange={updateInput}
                value={formData?.password || ''}
                />

                <div className={styles.buttons}>
                    <button type="submit" className={styles.registerButton}>Log in</button>
                </div>

            </form>
        </div>
    )

}
export default LogIn