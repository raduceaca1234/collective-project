import React, { useRef, useState, useEffect } from 'react'
import { useRouter } from 'next/router'
import { Alert } from 'react'

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
    const [name, setName] = useState()
    const [files, setFiles] = useState([])
    const ref = useRef()
    const router = useRouter()

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
        event.target.files && setFiles([...files, ...event.target.files])
    }

    const removePhoto = (index) => {
        setPhotos(photos.splice(index, 1))
    }

    const sendDataToServer = () => {
        let data = new FormData(ref.current)
        data.append("ownerId", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjA1NjQxOTYxLCJleHAiOjE2MDU3MjgzNjF9.MXsI9mlu_AX2IJo4UI4NTYlm83a2wuO6ip0YYkYiBvU")
        // data.append("name", "obj1")
        // data.append("description", "t1")
        // data.append("location", "cluj")
        // data.append("category", "SPORT")
        // data.append("duration", 10)
        // data.append("pricePerDay", 50)
        files.length > 0 && files.forEach(file => data.append("images", file))
        
        fetch('http://localhost:8080/api/announcement', {
            method: 'POST',
            cache: 'no-cache',
            headers: {
                // 'Content-Type': 'multipart/form-data'
            },
            body: data
        }).then(response => {
            alert('Announcement was added')
            router.push('/')
        });
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
                        <form ref={ref} className={styles.inputsForm}>
                            <div className={styles.formGroup}>
                                <label>Product Name</label>
                                <input name="name" type="text"></input>
                            </div>

                            <div className={styles.formGroup}>
                                <label>Product Location</label>
                                <input name="description" type="text"></input>
                            </div>

                            <div className={styles.formGroup}>
                                <label>Product Description</label>
                                <input name="location" type="text"></input>
                            </div>

                            <div className={styles.formGroup}>
                                <label>Product Category</label>
                                <input name="category" type="text"></input>
                            </div>

                            <div className={styles.formGroup}>
                                <label>Product Availability</label>
                                <input name="duration" type="text"></input>
                            </div>

                            <div className={styles.formGroup}>
                                <label>Product Price per day</label>
                                <input name="pricePerDay" type="number"></input>
                            </div>
                        </form>
                        <div>

                        </div>
                    </div>
                </div>

                <div className="col-6">
                    <div className={styles.dragArea}>
                        <input className={styles.addPhotos} onChange={addPhotos} type="file" name="files" multiple accept="image/png, image/jpeg" />
                        <p>Drag &amp; Drop some photos.</p>
                    </div>

                    <FileList photos={photos} onRemove={removePhoto} />
                    {/* <div className={styles.buttons}>
                        <button className={styles.inventoryButton} onClick={() => { }}>Add Product</button>
                    </div> */}

                </div>
            </div>
            <div className={styles.buttons}>
                <button className={styles.addButton} onClick={() => sendDataToServer()}>Add Product</button>
            </div>
        </div>
    )
}

export default AddProduct