import React, { useState } from 'react'
import styles from '../../styles/register.module.scss'

const LogIn=()=>{
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
            console.log("Invalid email");
            alert("That is not a valid email!!");
            return;
        }
        setFormData({
            email:'',
            password:'',
        })
        console.log(formData) 
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
            <h1 className={styles.h1}>Log in</h1>
            <form onSubmit={handleSubmit} className={styles.form}>

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
                    <button type="submit" className={styles.registerButton}>Log in</button>
                </div>

            </form>
        </div>
    )

}
export default LogIn