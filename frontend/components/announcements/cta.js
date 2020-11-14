import styles from '../../styles/cta.module.scss'

const Cta = ()=>{
    return (
        <div className={styles.main_div}>
            <div className={styles.left_div}>
                <img className={styles.image} src="/ctaImage.png" alt="CTA Image" />
            </div>
            <div className={styles.right_div}>
                <div className={styles.container}>
                    <div className={styles.title}>
                        <h1>Trade your Objects</h1>
                        <div className={styles.description}>
                            <p>Earn some more by posting your own objects. It takes no more than
                                2 minutes and you will immediately get messages.
                            </p>
                        </div>
                    </div>
                </div>
                <div className={styles.buttons}>
                    <button className={styles.postObjectButton} onClick={()=>{}}>Post an object</button>
                    <button className={styles.howItWorksButton}>How it works</button>
                </div>
            </div>
            
        </div>
    )
}

export default Cta
