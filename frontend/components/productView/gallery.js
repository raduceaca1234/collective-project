import next from 'next'
import { useEffect, useState } from 'react'
import styles from '../../styles/gallery.module.scss'

const data = [
    {
        id : 0, 
        url : "https://s1.cel.ro/images/mari/chitara-clasica-wood-style-96-cm-lemn-dx-s-dielisi.jpg",
    },
    {
        id : 2, 
        url : "https://www.fly-music.ro/24014-large/calida-benita-78-chitara-clasica-.jpg",
    },
    {
        id : 3, 
        url : "https://www.mcmusic.ro/2220-large_default/ibanez-pf15-bk-chitara-acustica.jpg",
    },
    {
        id : 4, 
        url : "https://www.mcmusic.ro/9334-large_default/pulse-hw41-101rds-chitara-acustica.jpg",
    },
    {
        id : 1, 
        url : "https://gomagcdn.ro/domains/ideallstore.com/files/product/original/chitara-clasica-din-lemn-95-cm-albastru-marin-1128-8737.jpg",
    },
    {
        id : 5, 
        url : "https://noriel.ro/media/catalog/product/cache/72176f270c29d981b21700393082943d/3/3/3373013_rosu_jucarie_bebelusi_fisher_price_chitara_rosu_1_.jpg",
    },
]



const PhotoCarousel = (props) => {
    return(
        <div className = {styles.littlePhotosDiv}>      
            <div className = {styles.wrap}>
                {props.photos && props.photos.map((item, index) => (
                    <div className = {styles.frame + (props.activePhoto===index? " " + styles.active : "")} key = {item.id} onClick = {() => props.itemClick(index)}>
                        <img src = {item.url}/>
                    </div>
                ))}
            </div>
        </div>
    )
}

const Gallery = (props) => {
    const [activePhoto, setActivePhoto] = useState(0)

    const frameClick = (frame_id) => {
        setActivePhoto (frame_id)
    } 

    const next = () => {
        if(activePhoto === data.length - 1){
            setActivePhoto(0)
        }
        else{
            setActivePhoto(activePhoto + 1)
        }
    }

    const prev = () => {
        if(activePhoto === 0 ){
            setActivePhoto(data.length - 1)
        }
        else{
            setActivePhoto(activePhoto - 1)
        }
    }

    return(
        <div className = {styles.gallery}>
            <div className = {styles.bigPhotoDiv + (data.length > 0 && (" " + styles.noPhotos))}>
                {
                    data.length > 0 ? <img className = {styles.bigPhoto} src = {data[activePhoto].url} /> : 
                    <p>
                        No photos.
                    </p>
                }
                <div className = {styles.controller}>
                    <div className={styles.control} onClick = {() => prev()}>
                        Prev
                    </div>
                    <div className={styles.control} onClick = {() => next()}>
                        Next
                    </div>
                </div>
            </div>
            <PhotoCarousel
                itemClick = {frameClick}
                activePhoto = {activePhoto}
                photos = {data}
            />
        </div>
    )
}

export default Gallery