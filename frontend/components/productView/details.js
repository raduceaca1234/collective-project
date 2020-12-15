import React from 'react'
import styles from '../../styles/details.module.scss'

const Details = () => {
    return (
        <div className={styles.container}>
            <h3>Details</h3>

            <div className={styles.labels}>
                <div className={styles.label +' '+ styles.rate}>
                    <h2>Rate</h2>
                    <h2>$20/h</h2>
                </div>

                <div className={styles.label}>
                    <h2>Location</h2>
                    <p>Cluj-Napoca, Romania</p>
                </div>

                <div className={styles.label}>
                    <h2>Category</h2>
                    <p>Instruments</p>
                </div>

                <div className={styles.label}>
                    <h2>Status</h2>
                    <p>Available today</p>
                </div>
            </div>

            <h3>Review</h3>

            <div className={styles.review_block}>
                <p>95% of 24 people were happy to use this.</p>

                <a href="#">Add your review</a>
            </div>

            <h3>Description</h3>

            <div className={styles.description_block}>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
            </div>
        </div>
    )
}

export default Details
