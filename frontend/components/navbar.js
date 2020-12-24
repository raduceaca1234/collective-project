import { useState, useEffect, useRef } from 'react'
import styles from '../styles/navbar.module.scss'
import { useRouter } from 'next/router'

import { useDispatch, useSelector } from 'react-redux'
import { actions as userActions } from '../redux/user/actions'
import Link from 'next/Link'

const AuthControlls = () => {

    return (
        <div className={styles.auth}>
            <Link href="/auth">Sign in</Link>
            <Link href="/auth?active=register">Sign up</Link>
        </div>
    )
}

const UserTab = () => {
    const [options, toggleOptions] = useState(false)
    const user = useSelector(state => (state.user))
    const router = useRouter()
    const dispatch = useDispatch()

    const logout = () => {
        dispatch({ type: userActions.USER_LOGOUT })

    }

    return (
        <div className={styles.userTab}>
            <h3
                onClick={(e) => {e.target.focus(); toggleOptions(!options)}}
                onBlur={() => toggleOptions(false)}
                className={styles.userName}>{`${user.data.firstName} ${user.data.lastName[0]}.`}</h3>
            <svg xmlns="http://www.w3.org/2000/svg" width="19.89" height="20" viewBox="0 0 19.89 20"><path d="M15.7,12.7a6,6,0,1,0-7.416,0A9.994,9.994,0,0,0,2.07,20.879a1.005,1.005,0,0,0,2,.22,8,8,0,0,1,15.891,0,1,1,0,0,0,1,.889h.11a1,1,0,0,0,.879-1.1A9.994,9.994,0,0,0,15.7,12.7Zm-3.708-.71a4,4,0,1,1,4-4A4,4,0,0,1,11.994,11.994Z" transform="translate(-2.064 -1.995)"/></svg>

            { options && <ul className={styles.options}>
                <li onClick={() => router.push('/me')}>Profile</li>
                <li onClick={() => logout()}>Logout</li>
            </ul>}
        </div>
    )
}

const SavedItems = () => {

    return (
        <div className={styles.savedItems}>
            <i className={styles.bubble}>0</i>
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="20.004" viewBox="0 0 14 20.004"><path d="M16,2H8A3,3,0,0,0,5,5V21a1,1,0,0,0,1.5.87L12,18.69l5.5,3.18A1,1,0,0,0,19,21V5A3,3,0,0,0,16,2Zm1,17.27-4.5-2.6a1,1,0,0,0-1,0L7,19.27V5A1,1,0,0,1,8,4h8a1,1,0,0,1,1,1Z" transform="translate(-5 -2)"/></svg>
        </div>
    )
}

const Bag = () => {

    return (
        <div className={styles.shoppingBag}>
            <i className={styles.bubble}>0</i>
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="20" viewBox="0 0 16 20"><path d="M19,7H16V6A4,4,0,0,0,8,6V7H5A1,1,0,0,0,4,8V19a3,3,0,0,0,3,3H17a3,3,0,0,0,3-3V8A1,1,0,0,0,19,7ZM10,6a2,2,0,0,1,4,0V7H10Zm8,13a1,1,0,0,1-1,1H7a1,1,0,0,1-1-1V9H8v1a1,1,0,0,0,2,0V9h4v1a1,1,0,0,0,2,0V9h2Z" transform="translate(-4 -2)"/></svg>
        </div>
    )
}

const SearchBar = () => {
    const [focus, setFocus] = useState(false)
    const [search, setSearch] = useState("")

    const input = useRef()

    const handleText = (e) => {
        setSearch(e.target.value || "")
    }

    const handleForm = event => {
        event.preventDefault();

        if(!focus && search) {
            console.log("fetch time")
        }

        input.current.focus()
    }

    return (
        <form className={styles.formContainer} onSubmit={(event) => handleForm(event)}>
            <svg xmlns="http://www.w3.org/2000/svg" width="16.012" height="16" viewBox="0 0 16.012 16"><path id="search" d="M17.766,16.63,14.8,13.686A7.2,7.2,0,1,0,13.686,14.8l2.944,2.944a.795.795,0,1,0,1.136-1.112ZM9.2,14.8a5.6,5.6,0,1,1,5.6-5.6,5.6,5.6,0,0,1-5.6,5.6Z" transform="translate(-1.979 -1.979)" fill="#222"/></svg>
            
            <input value={search} onChange={e => handleText(e)} className={styles.searchInput + (!focus && !search ? (" " + styles.hide) : '')} ref={input} type="text" name="search" placeholder="Guitar, car..." onFocus={() => setFocus(true)} onBlur={() => setFocus(false)}/>

            <input className={styles.button + (!focus && !search ? '' : (" " + styles.active))} type="submit" value="Search"/>
        </form>
    )
}

const Navbar = () => {
    const user = useSelector(state => (state.user))
    const router = useRouter()

    return (
        <div className={styles.container}>
            <div 
                className={styles.side + ' ' + styles.lhs}
                onClick={() => router.push('/')}
            >
                App name
            </div>

            <div className={styles.side + ' ' + styles.middle}>
                <SearchBar />
            </div>

            <div className={styles.side + ' ' + styles.rhs}>
                <Bag />
                <SavedItems />
                {(!!user.logged_in) 
                    ? <UserTab />
                    : <AuthControlls />
                }
            </div>
        </div>
    )
}

export default Navbar