import React, { useState } from 'react'
import styles from '../../styles/register.module.scss'

import AuthAPI from '../../api/auth'

const SignUp = (props) =>{
    const [formData,setFormData] = useState({})

    const updateInput = e =>{
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        })
    }

    const handleSubmit = event =>{
        event.preventDefault()
        if(!validateEmail(formData.email)){
            props.onAlert({
                type: 'error',
                message: 'Please, enter a valid email address.',
            })
            return;
        }

        let data = {
            firstName: formData.firstName,
            lastName: formData.lastName,
            email: formData.email,
            phoneNumber: formData.phone_number,
            password: formData.password,
        }

        console.log(data)

        AuthAPI.signUp(data)
        .then(res => {
            if(res.ok) {
                props.onAlert({
                    message: `Welcome, ${formData.firstName}, to the game! You may sign in now.`,
                })
            } else {
                props.onAlert({
                    type: 'error',
                    message: 'This email address is already being used.',
                })
            }
        })
        .catch(err => {
            props.onAlert({
                type: 'error',
                message: 'Something went wrong. Please, try again.',
            })
        })


        // setFormData({
        //     lastName:'',
        //     firstName:'',
        //     phone_number: '',
        //     email:'',
        //     password:'',
        // })
        
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
            <h1 className={styles.h1}>Sign up</h1>
            <form onSubmit={handleSubmit} className={styles.form}>
                <p className={styles.text}>Last name</p>
                <input className={styles.input}
                type="text"
                name="lastName"
                onChange={updateInput}
                value={formData.lastName || ''}
                />

                <p className={styles.text}>First name</p>
                <input className={styles.input}
                type="text"
                name="firstName"
                onChange={updateInput}
                value={formData.firstName || ''}
                />  

                <p className={styles.text}>Phone number</p>
                <input className={styles.input}
                type="text"
                name="phone_number"
                onChange={updateInput}
                value={formData.phone_number}
                />

                <p className={styles.text}>Email</p>
                <input className={styles.input}
                type="text"
                name="email"
                onChange={updateInput}
                value={formData.email || ''}
                />

                <p className={styles.text}>Password</p>
                <input className={styles.input}
                type="password"
                name="password"
                onChange={updateInput}
                value={formData.password || ''}
                />

                <div className={styles.buttons}>
                    <button type="submit" className={styles.registerButton}>Register</button>
                </div>

            </form>
        </div>
    )

}
export default SignUp