import Link from 'next/Link'

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
                    <Link href="/add" ><a className={styles.postObjectButton}>Post an object</a></Link>
                </div>
            </div>
            
        </div>
    )
}

export default Cta
