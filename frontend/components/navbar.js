import styles from '../styles/navbar.module.scss'

const Navbar = () => {
    
    return (
        <div className={styles.container}>
            <div className={styles.side + ' ' + styles.lhs}>
                App name
            </div>

            <div className={styles.side + ' ' + styles.middle}>
                Search bar
            </div>

            <div className={styles.side + ' ' + styles.rhs}>
                Links
            </div>
        </div>
    )
}

export default Navbar