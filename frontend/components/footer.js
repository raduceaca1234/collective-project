import styles from '../styles/footer.module.scss'

const Footer = () =>{
    return (
        <div className={styles.footer}>
            <div className={"grid" + ' ' + styles.sitemap} style={{color:'white'}}>
                <div className="col-6" style={{backgroundColor:'black'}}>
                    <h1>App</h1>
                    <h3>We're your friends too!!</h3>
                </div>
                <div className="col-2" style={{backgroundColor:'black'}}>
                    <h2>Legal</h2>
                    <p>How it works</p>
                    <p>Privacy Policy</p>
                    <p>Terms &amp; Conditions</p>
                    <p>Insurance</p>
                </div>
                <div className="col-2" style={{backgroundColor:'black'}}>
                    <h2>Navigate</h2>
                    <p>Inventory</p>
                    <p>Post an object</p>
                    <p>My wishlist</p>
                    <p>My objects</p>
                    <p>Account</p>
                </div>
                <div className="col-2"style={{backgroundColor:'black'}}>
                    <h2>Info</h2>
                    <p>About us</p>
                    <p>Jobs</p>
                    <p>Report a problem</p>
                </div>
            </div>
            <div className={styles.final}>
                <p>2020 App name. Made with scheme mari by Romaneasca</p>
            </div>
        </div>
    )
}

export default Footer