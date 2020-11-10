import React, { useState } from 'react'

import styles from '../styles/add_product.module.scss'


const AddProduct = () => {



    return (
        <div className={styles.content}>
            <div className={styles.title}>
                <h1>Adding Product</h1>
            </div>
            <div className="grid">
                <div className="col-3">
                    <div className={styles.main_div}>
                        <div className={styles.inputsForm}>
                            <h3>Product Name</h3>
                            <input type="text" placeholder="Product Name"></input>
                            <h3>Product Location</h3>
                            <input type="text" placeholder="Product Location"></input>
                            <h3>Product Description</h3>
                            <input type="text" placeholder="Product Description"></input>
                            <h3>Product Category</h3>
                            <input type="text" placeholder="Product Category"></input>
                            <h3>Product Availability</h3>
                            <input type="text" placeholder="Product Availability"></input>
                            <h3>Product Price per day</h3>
                            <input type="number" placeholder="Product Availability"></input>
                        </div>
                        <div>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default AddProduct