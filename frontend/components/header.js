import React, { useState, useEffect } from 'react'
import { useRouter } from 'next/router'
import styles from '../styles/header.module.scss'
// import header_image from '../public/headerImage.png'

const Header = () => {
    const router = useRouter()

    const [option, setOption] = useState(0);
    const data = ['Overview', 'Profile Info']

    const tabClick = (event) => {
        let option = Array.prototype.indexOf.call(event.target.parentElement.children, event.target)
        setOption(option)
    }

    return (
        <div className={'grid' + ' ' + styles.main_div}>
            <div className={'col-6' + ' ' + styles.right_div}>
                <div className={styles.container}>
                    <div className={styles.title}>
                        <h1>Ask us instead of them!</h1>
                        <div className={styles.description}>
                            <p>Borrow items for a period of time over a good price. No more asking a friend!</p>
                        </div>
                    </div>
                </div>
                <div className={styles.buttons}>
                    <button className={styles.inventoryButton} onClick={() => { router.push('/inventory') }}>See inventory</button>

                </div>
            </div>
            <div className={'col-6' + ' ' + styles.left_div}>
                <img src="/headerImage.png" alt="Header Image" />
            </div>
        </div>
    )
}

export default Header