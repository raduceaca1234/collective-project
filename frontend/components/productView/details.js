import React from 'react'
import styles from '../../styles/details.module.scss'

const Details = (props) => {
    return (
        <div className={styles.container}>
            <h3>Details</h3>

            <div className={styles.labels}>
                <div className={styles.label +' '+ styles.rate}>
                    <h2>Rate</h2>
                    <h2>{`$${props.data?.pricePerDay}/h`}</h2>
                </div>

                <div className={styles.label}>
                    <h2>Location</h2>
                    <p>{`${props.data?.location}`}</p>
                </div>

                <div className={styles.label}>
                    <h2>Category</h2>
                    <p>{props.data?.category}</p>
                </div>

                <div className={styles.label}>
                    <h2>Status</h2>
                    <p>{props.data?.status}</p>
                </div>
            </div>

            <h3>Review</h3>

            <div className={styles.review_block}>
                <p>95% of 24 people were happy to use this.</p>

                <a href="#">Add your review</a>
            </div>

            <h3>Description</h3>

            <div className={styles.description_block}>
                <p>{props.data?.description}</p>
            </div>
        </div>
    )
}

export default Details
