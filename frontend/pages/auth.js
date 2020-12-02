import React, { useState, useEffect } from 'react'
import { useRouter } from 'next/router'
import Head from 'next/head'
import { useSelector } from 'react-redux'
import styles from '../styles/auth.module.scss'

import Login from '../components/auth/login'
import Register from '../components/auth/register'

const Alert = (props) => {
    const [show, setShow] = useState(false)

    useEffect(() => {
        if(props.data) {
            setShow(true)
            setTimeout(() => {
                setShow(false)
            }, props.data?.lifespan || 3000)
        }
    }, [props.data])

    return (
        <>
        {
            show && 
            <div className={styles.alert + (props.data?.type === 'error'? ' ' + styles.error:'')}>
                <p>{props.data?.message || 'Something went wrong...'}</p>
            </div>
        }
        </>
    )
}

const Auth = () => {
    const user = useSelector(state => state.user)
    const router = useRouter()
    const [page, setPage] = useState('login')
    const [error, setError] = useState()

    console.log(user)

    const { active } = router.query

    const switchForm = e => {
        e.preventDefault()

        setPage(page === 'login' ? 'register' : 'login')
    }

    const alertCallback = (data) => {
        setError({
            type: data.type || 'none',
            message: data.message,
            lifespan: data.lifespan || 3000,
        })
    }

    useEffect(() => {
        if(active === 'register') {
            setPage('register')
        } else {
            setPage('login')
        }
    }, [active])

    return (
        <>
            <Alert data={error}/>
            <Head>
                <title>{page === 'login'? 'Sign in':'Sign up'}</title>
            </Head>
            <div className={styles.content}>
                <div className={styles.board}>
                    <div className={styles.carousel + (page === 'register' ? ' '+styles.active : '')}>
                        <div className={styles.page + (page === 'login' ? ' '+styles.active : '')}>
                            <Login onAlert={alertCallback} />
                        </div>

                        <div className={styles.page + (page === 'register' ? ' '+styles.active : '')}>
                            <Register onAlert={alertCallback} />
                        </div>
                    </div>

                    <div className={styles.bottom_links}>
                        <a href="#" onClick={e => switchForm(e)}>
                            {
                                page === 'login'
                                ? 'Don\'t have an account?'
                                : 'Already have an account?'
                            }
                        </a>
                    </div>

                </div>
            </div>
        </>
    )
}

export default Auth