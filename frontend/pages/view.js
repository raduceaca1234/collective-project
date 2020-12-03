import styles from '../styles/post.module.scss'
import { useRouter } from 'next/router'
import { useState, useEffect } from 'react'

const Post = () => {
    const router = useRouter()
    const [post, setPost] = useState()

    const { p } = router.query 

    useEffect(() => {
        if(p) {
            setPost('loaded')
        }
    }, [p])

    return (
        <div className={styles.content}>
            {post}
            {p}
        </div>
    )
}

export default Post