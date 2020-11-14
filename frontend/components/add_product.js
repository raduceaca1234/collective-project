import React, { useState, useEffect } from 'react'

import styles from '../styles/add_product.module.scss'

const fileToDataUri = (file) => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (event) => {
      resolve(event.target.result)
    };
    reader.readAsDataURL(file);
})

const Preview = (props) => {
    const [data, setData] = useState()

    useEffect(() => {
        props.promise.then(e => setData(e)).catch(e => console.log(e))
    })

    return (
        <div className={styles.preview}>
            <img src={data} />
        </div>
    )
}

const FileList = (props) => {

    return (
        <ul className={styles.fileList}>
            {props.photos.map((e, i) => (
            <li key={i}>
                <div className={styles.left}>
                    <Preview promise={e.blobPromise} />
                    <p>{e.name}</p>
                </div>

                <div className={styles.right}>
                    <button onClick={() => props.onRemove(i)}>Remove</button>
                </div>
            </li>))}
        </ul>
    )
}

const AddProduct = () => {
    const [photos, setPhotos] = useState([])

    const addPhotos = (event) => {
        let newPhotos = Array.from(event.target.files)
        let newMembers = []

        console.log(newPhotos)

        newPhotos.forEach(file => {
            newMembers.push({
                name: file.name,
                blobPromise: fileToDataUri(file),
                type: file.type,
                size: file.size,
            })
            console.log(file)
        })

        setPhotos([...photos, ...newMembers])
    }

    const removePhoto = (index) => {
        setPhotos(photos.splice(index, 1))
    }

    React.useEffect(() => {
        console.log(photos)
    }, [photos])


    return (
        <div className={styles.content}>
            <div className={styles.title}>
                <h1>Adding Product</h1>
            </div>
            <div className="grid">
                <div className="col-6">
                    <div className={styles.main_div}>
                        <div className={styles.inputsForm}>
                            <div className={styles.formGroup}>
                                <label>Product Name</label>
                                <input type="text"></input>
                            </div>
                            
                            <div className={styles.formGroup}>
                                <label>Product Location</label>
                                <input type="text"></input>
                            </div>
                            
                            <div className={styles.formGroup}>
                                <label>Product Description</label>
                                <input type="text"></input>
                            </div>
                            
                            <div className={styles.formGroup}>
                                <label>Product Category</label>
                                <input type="text"></input>
                            </div>
                            
                            <div className={styles.formGroup}>
                                <label>Product Availability</label>
                                <input type="text"></input>
                            </div>
                            
                            <div className={styles.formGroup}>
                                <label>Product Price per day</label>
                                <input type="number"></input>
                            </div>
                        </div>
                        <div>
                            
                        </div>
                    </div>
                </div>

                <div className="col-6">
                    <div className={styles.dragArea}>
                        <input className={styles.addPhotos} onChange={addPhotos} type="file" name="files" multiple accept="image/png, image/jpeg"/>
                        <p>Drag &amp; Drop some photos.</p>
                    </div>
                    
                    <FileList photos={photos} onRemove={removePhoto}/>
                </div>
            </div>
        </div>
    )
}

export default AddProduct